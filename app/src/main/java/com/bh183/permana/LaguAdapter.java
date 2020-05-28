package com.bh183.permana;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class LaguAdapter extends RecyclerView.Adapter<LaguAdapter.LaguViewHolder> {

    private Context context;
    private ArrayList<Lagu> dataLagu;

    public LaguAdapter(Context context, ArrayList<Lagu> dataLagu) {
        this.context = context;
        this.dataLagu = dataLagu;
    }

    @NonNull
    @Override
    public LaguViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.list_lagu, parent, false);
        return new LaguViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LaguViewHolder holder, int position) {
        Lagu tempLagu = dataLagu.get(position);
        holder.idLagu = tempLagu.getIdLagu();
        holder.tvJudul.setText(tempLagu.getJudul());
        holder.tvHeadline.setText(tempLagu.getAlbum());
        holder.tvArtist.setText(tempLagu.getArtist());
        holder.lirikLagu = tempLagu.getLirikLagu();
        holder.gambar = tempLagu.getGambar();
        holder.album = tempLagu.getAlbum();
        holder.link = tempLagu.getLink();

        try {
            File file = new File(holder.gambar);
            Bitmap bitmap = BitmapFactory.decodeStream(new FileInputStream(file));
            holder.imgLagu.setImageBitmap(bitmap);
            holder.imgLagu.setContentDescription(holder.gambar);
        } catch (FileNotFoundException er) {
            er.printStackTrace();
            Toast.makeText(context, "Gagal mengambil gambar dari media penyimpanan", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public int getItemCount() {
        return dataLagu.size();
    }

    public class LaguViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

        private ImageView imgLagu;
        private TextView tvJudul, tvHeadline, tvArtist;
        private int idLagu;
        private String gambar, lirikLagu, album, link;

        public LaguViewHolder(@NonNull View itemView) {
            super(itemView);

            imgLagu =  itemView.findViewById(R.id.iv_lagu);
            tvJudul = itemView.findViewById(R.id.tv_judul);
            tvArtist = itemView.findViewById(R.id.tv_artist);
            tvHeadline = itemView.findViewById(R.id.tv_headline);

            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View v) {

            Intent bukaLagu = new Intent(context, TampilActivity.class);
            bukaLagu.putExtra("ID", idLagu);
            bukaLagu.putExtra("JUDUL", tvJudul.getText().toString());
            bukaLagu.putExtra("ARTIST", tvArtist.getText().toString());
            bukaLagu.putExtra("GAMBAR", gambar);
            bukaLagu.putExtra("ALBUM", album);
            bukaLagu.putExtra("LIRIK_LAGU", lirikLagu);
            bukaLagu.putExtra("LINK", link);
            context.startActivity(bukaLagu);
        }

        @Override
        public boolean onLongClick(View v) {

            Intent bukaInput = new Intent(context, InputActivity.class);
            bukaInput.putExtra("OPERASI", "update");
            bukaInput.putExtra("ID", idLagu);
            bukaInput.putExtra("JUDUL", tvJudul.getText().toString());
            bukaInput.putExtra("ARTIST", tvArtist.getText().toString());
            bukaInput.putExtra("GAMBAR", gambar);
            bukaInput.putExtra("ALBUM", album);
            bukaInput.putExtra("LIRIK_LAGU", lirikLagu);
            bukaInput.putExtra("LINK", link);
            context.startActivity(bukaInput);
            return true;
        }
    }
}

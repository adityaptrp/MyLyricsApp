package com.bh183.permana;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class TampilActivity extends AppCompatActivity {

    private ImageView imgLagu;
    private TextView tvJudul, tvArtist, tvAlbum, tvLirikLagu;
    private String linkLagu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tampil);

        imgLagu = findViewById(R.id.iv_lagu);
        tvJudul = findViewById(R.id.tv_judul);
        tvArtist = findViewById(R.id.tv_artist);
        tvAlbum = findViewById(R.id.tv_album);
        tvLirikLagu = findViewById(R.id.tv_lirik_lagu);

        Intent terimaData = getIntent();
        tvJudul.setText(terimaData.getStringExtra("JUDUL"));
        tvArtist.setText(terimaData.getStringExtra("ARTIST"));
        tvAlbum.setText(terimaData.getStringExtra("ALBUM"));
        tvLirikLagu.setText(terimaData.getStringExtra("LIRIK_LAGU"));
        String imgLocation = terimaData.getStringExtra("GAMBAR");

        try {
            File file = new File(imgLocation);
            Bitmap bitmap = BitmapFactory.decodeStream(new FileInputStream(file));
            imgLagu.setImageBitmap(bitmap);
            imgLagu.setContentDescription(imgLocation);
        } catch (FileNotFoundException er) {
            er.printStackTrace();
            Toast.makeText(this, "Gagal mengambil gambar dari media penyimpanan", Toast.LENGTH_SHORT).show();
        }

        linkLagu = terimaData.getStringExtra("LINK");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.tampil_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.item_bagikan)
        {
            Intent bagikanLagu = new Intent(Intent.ACTION_SEND);
            bagikanLagu.putExtra(Intent.EXTRA_SUBJECT, tvJudul.getText().toString());
            bagikanLagu.putExtra(Intent.EXTRA_TEXT, linkLagu);
            bagikanLagu.setType("text/plain");
            startActivity(Intent.createChooser(bagikanLagu, "Bagikan Lagu"));
        }
        return super.onOptionsItemSelected(item);
    }
}

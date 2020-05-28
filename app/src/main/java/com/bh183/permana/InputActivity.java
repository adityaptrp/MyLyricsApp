package com.bh183.permana;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

public class InputActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText editNamaLagu, editNamaArtist, editAlbum, editLirikLagu, editLink;
    private ImageView ivLagu;
    private DatabaseHandler dbHandler;
    private boolean updateData = false;
    private int idLagu = 0;
    private Button btnSimpan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input);

        editNamaLagu = findViewById(R.id.edit_judul);
        editNamaArtist = findViewById(R.id.edit_artist);
        editAlbum = findViewById(R.id.edit_album);
        editLirikLagu = findViewById(R.id.edit_lirik_lagu);
        editLink = findViewById(R.id.edit_link);
        ivLagu = findViewById(R.id.iv_lagu);
        btnSimpan = findViewById(R.id.btn_simpan);

        dbHandler = new DatabaseHandler(this);

        Intent terimaIntent = getIntent();
        Bundle data = terimaIntent.getExtras();
        if (data.getString("OPERASI").equals("insert")) {
            updateData = false;
        } else {
            updateData = true;
            idLagu = data.getInt("ID");
            editNamaLagu.setText(data.getString("JUDUL"));
            editNamaArtist.setText(data.getString("ARTIST"));
            editAlbum.setText(data.getString("ALBUM"));
            editLirikLagu.setText(data.getString("LIRIK_LAGU"));
            editLink.setText(data.getString("LINK"));
            loadImageFromInternalStorage(data.getString("GAMBAR"));
        }

        ivLagu.setOnClickListener(this);
        btnSimpan.setOnClickListener(this);
    }

    private void pickImage() {
        CropImage.activity()
                .setGuidelines(CropImageView.Guidelines.ON)
                .setAspectRatio(3,3)
                .start(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                try {
                    Uri imageUri = result.getUri();
                    InputStream imageStream = getContentResolver().openInputStream(imageUri);
                    Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                    String location = saveImageToInternalStorage(selectedImage, getApplicationContext());
                    loadImageFromInternalStorage(location);
                } catch (FileNotFoundException er) {
                    er.printStackTrace();
                    Toast.makeText(this, "Ada kesalahan dalam pemilihan gambar", Toast.LENGTH_SHORT).show();
                }
            }
        } else {
            Toast.makeText(this, "Anda belum memilih gambar", Toast.LENGTH_SHORT).show();
        }
    }

    public static String saveImageToInternalStorage(Bitmap bitmap, Context ctx) {
        ContextWrapper ctxWrapper = new ContextWrapper(ctx);
        File file = ctxWrapper.getDir("images", MODE_PRIVATE);
        String uniqueID = UUID.randomUUID().toString();
        file = new File(file, "lagu-"+ uniqueID + ".jpg");
        try {
            OutputStream stream = null;
            stream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            stream.flush();
            stream.close();
        } catch (IOException er) {
            er.printStackTrace();
        }

        Uri savedImage = Uri.parse(file.getAbsolutePath());
        return savedImage.toString();
    }

    private void loadImageFromInternalStorage(String imageLocation) {
        try {
            File file = new File(imageLocation);
            Bitmap bitmap = BitmapFactory.decodeStream(new FileInputStream(file));
            ivLagu.setImageBitmap(bitmap);
            ivLagu.setContentDescription(imageLocation);
        } catch (FileNotFoundException er) {
            er.printStackTrace();
            Toast.makeText(this, "Gagal mengambil gambar dari media penyimpanan", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {

        MenuItem item = menu.findItem(R.id.item_menu_hapus);

        if(updateData==true) {
            item.setEnabled(true);
            item.getIcon().setAlpha(255);
        } else {
            item.setEnabled(false);
            item.getIcon().setAlpha(130);
        }

        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.input_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();

        if (id==R.id.item_menu_hapus) {
            hapusData();
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    private void simpanData() {
        String judul, artist, gambar, album, lirikLagu, link;
        judul = editNamaLagu.getText().toString();
        artist = editNamaArtist.getText().toString();
        gambar = ivLagu.getContentDescription().toString();
        album = editAlbum.getText().toString();
        lirikLagu = editLirikLagu.getText().toString();
        link = editLink.getText().toString();


        Lagu tempLagu = new Lagu (
                idLagu, judul, artist, gambar, album, lirikLagu, link
        );

        if (updateData == true) {
            dbHandler.editLagu(tempLagu);
            Toast.makeText(this, "Data lagu diperbaharui", Toast.LENGTH_SHORT).show();
        } else {
            dbHandler.tambahLagu(tempLagu);
            Toast.makeText(this, "Data lagu ditambahkan", Toast.LENGTH_SHORT).show();
        }
        finish();
    }

    private void hapusData() {
        dbHandler.hapusLagu(idLagu);
        Toast.makeText(this, "Data lagu dihapus", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        int idView = v.getId();

        if (idView == R.id.btn_simpan) {
            String namaLagu = editNamaLagu.getText().toString();
            String namaArtists = editNamaArtist.getText().toString();
            String namaAlbum = editAlbum.getText().toString();
            String lirikLagu = editLirikLagu.getText().toString();

            boolean isEmpty = false;
            if (TextUtils.isEmpty(namaLagu)){
                isEmpty = true;
                editNamaLagu.setError("Masukkan Nama Lagu");
                Toast.makeText(InputActivity.this, "Masukkan Nama Lagu", Toast.LENGTH_SHORT).show();
            } else if (TextUtils.isEmpty(namaArtists)){
                isEmpty = true;
                editNamaArtist.setError("Masukkan Artists Lagu");
                Toast.makeText(InputActivity.this, "Masukkan Artist Lagu", Toast.LENGTH_SHORT).show();
            } else if (TextUtils.isEmpty(namaAlbum)){
                isEmpty = true;
                editAlbum.setError("Masukkan Nama Album Lagu");
                Toast.makeText(InputActivity.this, "Masukkan Nama Album Lagu", Toast.LENGTH_SHORT).show();
            } else if (TextUtils.isEmpty(lirikLagu)){
                isEmpty = true;
                editLirikLagu.setError("Masukkan Lirik Lagu");
                Toast.makeText(InputActivity.this, "Masukkan Lirik Lagu", Toast.LENGTH_SHORT).show();
            } else {
                simpanData();
            }
        } else if (idView == R.id.iv_lagu) {
            pickImage();
        }
    }
}

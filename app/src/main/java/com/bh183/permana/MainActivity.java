package com.bh183.permana;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<Lagu> dataLagu = new ArrayList<>();
    private RecyclerView rvLagu;
    private LaguAdapter laguAdapter;
    private DatabaseHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rvLagu = findViewById(R.id.rv_tampil_lagu);
        dbHandler = new DatabaseHandler(this);
        tampilDataLagu();
    }

    private void tampilDataLagu() {
        dataLagu = dbHandler.getAllLagu();
        laguAdapter = new LaguAdapter(this, dataLagu);
        RecyclerView.LayoutManager layManager = new LinearLayoutManager(MainActivity.this);
        rvLagu.setLayoutManager(layManager);
        rvLagu.setAdapter(laguAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();

        if (id==R.id.item_menu_tambah) {
            Intent bukaInput = new Intent(getApplicationContext(), InputActivity.class);
            bukaInput.putExtra("OPERASI", "insert");
            startActivity(bukaInput);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        tampilDataLagu();
    }
}

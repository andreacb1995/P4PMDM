package com.example.andreacarballidop4pmdm.UI;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.andreacarballidop4pmdm.R;
import com.example.andreacarballidop4pmdm.core.Post;

public class ActivityInfoPost extends AppCompatActivity {
    private static final String POST = "post";
    private Post post;
    private long tiempoParaSalir = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_post);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        String titulo = getIntent().getStringExtra("titulo");
        String autor = getIntent().getStringExtra("name");
        String  cuerpo = getIntent().getStringExtra("cuerpo");



        TextView tvTitulo = findViewById(R.id.tvTitulo);
        TextView tvAutor = findViewById(R.id.tvAutor);
        TextView tvCuerpo = findViewById(R.id.tvCuerpo);

        String t= ucFirst(titulo);
        String c= ucFirst(cuerpo);

        tvTitulo.setText(t);
        tvAutor.setText(autor);
        tvCuerpo.setText(c);


    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == android.R.id.home)
            finish();

        return super.onOptionsItemSelected(item);
    }

    public static String ucFirst(String str) {
        if (str == null || str.isEmpty()) return str;
        else return str.substring(0, 1).toUpperCase() + str.substring(1);
    }
}


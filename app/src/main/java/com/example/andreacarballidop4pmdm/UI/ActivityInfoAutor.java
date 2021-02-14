package com.example.andreacarballidop4pmdm.UI;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.andreacarballidop4pmdm.R;
import com.example.andreacarballidop4pmdm.core.Post;

public class ActivityInfoAutor extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_autor);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        String nombre = getIntent().getStringExtra("nombre");
        String  nickname = getIntent().getStringExtra("nickname");
        String  email = getIntent().getStringExtra("email");
        String  teléfono = getIntent().getStringExtra("telefono");
        String  nombreCompany = getIntent().getStringExtra("nombreCompany");




        TextView tvNombre = findViewById(R.id.tv_nombreAutor);
        TextView tvNickname = findViewById(R.id.tv_nicknameAutor);
        TextView tvEmail = findViewById(R.id.tv_emailAutor);
        TextView tvTelefono = findViewById(R.id.tv_telefonoAutor);
        TextView tvNombreCompany = findViewById(R.id.tv_companyAutor);


        tvNombre.setText(nombre);
        tvNickname.setText(nickname);
        tvEmail.setText(email);
        tvTelefono.setText(teléfono);
        tvNombreCompany.setText(nombreCompany);


    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == android.R.id.home)
            finish();

        return super.onOptionsItemSelected(item);
    }



}


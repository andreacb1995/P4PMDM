package com.example.andreacarballidop4pmdm.UI;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.andreacarballidop4pmdm.R;
import com.example.andreacarballidop4pmdm.core.Post;
import com.example.andreacarballidop4pmdm.core.User;
import com.example.andreacarballidop4pmdm.database.LabUP;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class ActivityAñadirPost extends AppCompatActivity {
    private LabUP labUP;
    Spinner spinner;
    String nombreAutor;
    List<User> autoresBD;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_post);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        añadirPost();

    }

    public void añadirPost() {
         labUP =LabUP.get(this);

        final EditText edTitulo = findViewById(R.id.edTitulo);
        final EditText edCuerpo = findViewById(R.id.edCuerpo);
        Button botonguardar = findViewById(R.id.boton_guardar);

        autoresBD = labUP.getUsers();
        List<String> listaAutores = new ArrayList();
        listaAutores.add(" ");
        for (User u : autoresBD){
            listaAutores.add(u.getName());
        }

        spinner= (Spinner)findViewById(R.id.spinner);
        ArrayAdapter adapterSpinner = new ArrayAdapter(this,
                android.R.layout.simple_spinner_dropdown_item, listaAutores);

        spinner.setAdapter(adapterSpinner);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                nombreAutor = listaAutores.get(i);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                //No se ha seleccionado nada.
            }
        });


        botonguardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String t = edTitulo.getText().toString();
                String c =edCuerpo.getText().toString();

                if (nombreAutor.equals(" ")) {
                    Toast.makeText(ActivityAñadirPost.this, "No se permiten campos vacíos", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (t.isEmpty()) {
                    Toast.makeText(ActivityAñadirPost.this, "No se permiten campos vacíos", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (c.isEmpty()) {
                    Toast.makeText(ActivityAñadirPost.this, "No se permiten campos vacíos", Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent i = getIntent();
                User user = labUP.name(nombreAutor);
                Post post = new Post(user.getId(),t,c);
                labUP.insertPosts(post);
                i.putExtra("idPost", String.valueOf(post.getId()));

                Toast.makeText(ActivityAñadirPost.this, "Post añadido correctamente", Toast.LENGTH_SHORT).show();
                setResult(RESULT_OK, i);
                finish();
            }
        });



    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == android.R.id.home)
            finish();

        return super.onOptionsItemSelected(item);
    }
}

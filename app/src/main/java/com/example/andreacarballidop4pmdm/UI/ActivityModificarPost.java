package com.example.andreacarballidop4pmdm.UI;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.andreacarballidop4pmdm.R;
import com.example.andreacarballidop4pmdm.core.Post;
import com.example.andreacarballidop4pmdm.core.User;
import com.example.andreacarballidop4pmdm.database.LabUP;

import java.util.ArrayList;
import java.util.List;

public class ActivityModificarPost extends AppCompatActivity {
    private LabUP labUP;
    String id;
    String autor;
    String titulo;
    String cuerpo;
    Post postModifico;
    Post post;
    Spinner spinner;
    String nombreAutor;
    List<User> autoresBD;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_post);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        labUP =LabUP.get(this);
        id= getIntent().getStringExtra("id");
        autor = getIntent().getStringExtra("autor");
        titulo = getIntent().getStringExtra("titulo");
        cuerpo = getIntent().getStringExtra("cuerpo");

        postModifico=labUP.getPost(Integer.parseInt(id));

        modificarPost();

    }

    public void modificarPost() {


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
        User user= labUP.userId(postModifico.getUserId());

        String inicializarItem= user.getName();

        spinner.setSelection(obtenerPosicionItem(spinner, inicializarItem));

        edTitulo.setText(titulo);
        edCuerpo.setText(cuerpo);


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
                    Toast.makeText(ActivityModificarPost.this, "No se permiten campos vacíos", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (t.isEmpty()) {
                    Toast.makeText(ActivityModificarPost.this, "No se permiten campos vacíos", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (c.isEmpty()) {
                    Toast.makeText(ActivityModificarPost.this, "No se permiten campos vacíos", Toast.LENGTH_SHORT).show();
                    return;
                }

                Intent i = getIntent();
                User user = labUP.name(nombreAutor);
                postModifico.modificarPost(user.getId(),t,c);
                labUP.updatePosts(postModifico);
                i.putExtra("idPost", String.valueOf(postModifico.getId()));
                Toast.makeText(ActivityModificarPost.this, "Post modificado correctamente", Toast.LENGTH_SHORT).show();
                setResult(RESULT_OK, i);
                finish();

            }

        });



    }

    public static int obtenerPosicionItem(Spinner spinner, String nombre) {
        //Creamos la variable posicion y lo inicializamos en 0
        int posicion = 0;
        //Recorre el spinner en busca del ítem que coincida con el parametro `String fruta`
        //que lo pasaremos posteriormente
        for (int i = 0; i < spinner.getCount(); i++) {
            //Almacena la posición del ítem que coincida con la búsqueda
            if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(nombre)) {
                posicion = i;
            }
        }
        //Devuelve un valor entero (si encontro una coincidencia devuelve la
        // posición 0 o N, de lo contrario devuelve 0 = posición inicial)
        return posicion;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == android.R.id.home)
            finish();

        return super.onOptionsItemSelected(item);
    }
}

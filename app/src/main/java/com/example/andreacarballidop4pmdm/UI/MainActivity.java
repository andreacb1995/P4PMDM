package com.example.andreacarballidop4pmdm.UI;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.andreacarballidop4pmdm.R;
import com.example.andreacarballidop4pmdm.core.Post;
import com.example.andreacarballidop4pmdm.core.User;
import com.example.andreacarballidop4pmdm.database.LabUP;
import com.example.andreacarballidop4pmdm.database.UserWithPosts;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity implements AccionesPost{
    LabUP labUP;
    List<Post> listaPosts;
    RecyclerView recyclerView;
    MyRecyclerViewAdapter adapter;

    Post postModifico;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView) findViewById(R.id.rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        actualizarPosts();

        FloatingActionButton btAdd = findViewById(R.id.btAdd);
        btAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onAdd();
            }
        });
    }


    private void onAdd() {
        Intent intent = new Intent(MainActivity.this, ActivityAñadirPost.class);
        startActivityForResult(intent, 0);

    }

    private void modificarP(Post post) {
        Intent intent = new Intent(MainActivity.this, ActivityModificarPost.class);
        if (post != null) {

            User user = labUP.userId(post.getUserId());
            String autor = user.getName();
            String id= String.valueOf(post.getId());
            intent.putExtra("id", id);
            intent.putExtra("autor", autor);
            intent.putExtra("titulo", post.getTitulo());
            intent.putExtra("cuerpo", post.getCuerpo());

        }
        startActivityForResult(intent, 0);

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 0 && resultCode == RESULT_OK) {
            actualizarPosts();
        }

    }

    private void actualizarPosts() {
        labUP = LabUP.get(this);
        List<Post> listaPosts = labUP.getPosts();
        adapter = new MyRecyclerViewAdapter(listaPosts,this,this);
        recyclerView.setAdapter(adapter);
    }


    @Override
    public void modificarPost(Post post) {
        modificarP(post);
    }

    @Override
    public void eliminarPost(Post post) {
        labUP = LabUP.get(this);
       listaPosts = labUP.getPosts();
        User user = labUP.userId(post.getUserId());
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Borrar Elemento");
        builder.setMessage("Está seguro de que desea eliminar este elemento?\n\n" + "Título: " + post.getTitulo() +
                "\nAutor: " + user.getName());
        builder.setPositiveButton("Sí", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                labUP.deletePosts(post);
                actualizarPosts();
                Toast.makeText(MainActivity.this, "Post eliminado correctamente", Toast.LENGTH_SHORT).show();
            }
        });

        builder.setNegativeButton("No", null);
        builder.create().show();
    }




    private void modificar() {
        labUP = LabUP.get(this);
        listaPosts = labUP.getPosts();
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Modificar post");

        final String[] arrayPosts = new String[listaPosts.size()];
        for (int i = 0; i < listaPosts.size(); i++) {
            User user = labUP.userId(listaPosts.get(i).getUserId());
            String autor = user.getName();
            arrayPosts[i] = "Titulo: " + listaPosts.get(i).getTitulo() + "\n Autor: " + autor;
        }

        builder.setSingleChoiceItems(arrayPosts, -1, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int i) {
                postModifico = listaPosts.get(i);
            }
        });
        builder.setPositiveButton("Modificar", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(final DialogInterface dialog, final int i) {
                if (postModifico == null) {
                    Toast.makeText(MainActivity.this, "No ha seleccionado un post", Toast.LENGTH_SHORT).show();
                } else {
                    modificarP(postModifico);
                }
            }

        });
        builder.setNegativeButton("Cancelar", null);
        builder.create().show();

    }

    public void eliminar() {
        labUP = LabUP.get(this);
        listaPosts = labUP.getPosts();
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Eliminar entrenamientos");

        String[] stringPosts = new String[listaPosts.size()];
        final boolean[] postsseleccion = new boolean[listaPosts.size()];
        for (int i = 0; i < listaPosts.size(); i++) {
            User user = labUP.userId(listaPosts.get(i).getUserId());
            String autor = user.getName();
            stringPosts[i] = "Titulo: " + listaPosts.get(i).getTitulo() + "\n Autor: " + autor;
        }
        builder.setMultiChoiceItems(stringPosts, postsseleccion, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i, boolean isChecked) {
                postsseleccion[i] = isChecked;
            }
        });
        builder.setPositiveButton("Sí", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(final DialogInterface dialog, int which) {
                AlertDialog.Builder buildereliminar = new AlertDialog.Builder(MainActivity.this);
                buildereliminar.setMessage("¿Está seguro de que desea eliminar los elementos?");
                buildereliminar.setNegativeButton("No", null);
                buildereliminar.setPositiveButton("Sí", new DialogInterface.OnClickListener() {


                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        for (int i = 0 ; i<listaPosts.size() ; i++){
                            if (postsseleccion[i]){
                                labUP.deletePosts(listaPosts.get(i));
                            }
                        }

                        actualizarPosts();
                        Toast.makeText(MainActivity.this, "Posts eliminados correctamente", Toast.LENGTH_SHORT).show();
                    }

                });
                buildereliminar.create().show();
            }
        });
        builder.setNegativeButton("No", null);
        builder.create().show();
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_principal, menu);
        MenuItem item = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) item.getActionView();
       searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });
//
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        boolean toret = false;

        switch (menuItem.getItemId()) {
            case R.id.crearPostMenuPrincipal:
                onAdd();
                toret = true;
                break;
            case R.id.modificarPostMenuPricipal:
                modificar();
                toret = true;
                break;
            case R.id.eliminarPostMenuPrincipal:
                eliminar();
                toret = true;
                break;
            case R.id.resetear:
                resetear();
                toret = true;
                break;
        }
        return toret;
    }

    private void resetear() {
        Intent intent = new Intent(MainActivity.this, SplashActivity.class);
        MainActivity.this.startActivity(intent);
    }
}
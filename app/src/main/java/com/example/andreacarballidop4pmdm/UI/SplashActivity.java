package com.example.andreacarballidop4pmdm.UI;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.andreacarballidop4pmdm.R;
import com.example.andreacarballidop4pmdm.core.Post;
import com.example.andreacarballidop4pmdm.core.User;
import com.example.andreacarballidop4pmdm.database.LabUP;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class SplashActivity extends Activity {
    RequestQueue queue;
    LabUP labUP;
    List<Post> listaPosts;
    List<User> listaUsers;
    private final int DURACTION_SPLASH= 3000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        queue = Volley.newRequestQueue(this);
        labUP = LabUP.get(this);
        users();
        posts();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.splash_activity);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        },DURACTION_SPLASH);

    }

    private void users() {
        listaPosts= labUP.getPosts();
        for(int i = 0; i < listaPosts.size(); i++){
            labUP.deletePosts(listaPosts.get(i));

        }

        String url = "https://jsonplaceholder.typicode.com/users?_end=5";

        JsonArrayRequest requestUser = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray jsonArray) {

                for (int i = 0; i < 5; i++) {
                    try {
                        String id = jsonArray.getJSONObject(i).getString("id");
                        String name = jsonArray.getJSONObject(i).getString("name");
                        String nickname = jsonArray.getJSONObject(i).getString("username");
                        String email = jsonArray.getJSONObject(i).getString("email");

                        String telefono = jsonArray.getJSONObject(i).getString("phone");
                        JSONObject jsonObjCompany = jsonArray.getJSONObject(i).getJSONObject("company");
                        String nameCompany = jsonObjCompany.getString("name");

                        String company = nameCompany;

                        User u1 = new User(id,name,nickname,email,telefono,company);
                        labUP.insertUsers(u1);



                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }


        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "No se han podido recuperar los datos de la página", Toast.LENGTH_SHORT).show();

            }
        });

        queue.add(requestUser);
    }


    private void posts() {
        listaUsers= labUP.getUsers();
        for(int i = 0; i < listaUsers.size(); i++){
            labUP.deleteUsers(listaUsers.get(i));
        }

        String url = "https://jsonplaceholder.typicode.com/posts?_end=50";

        JsonArrayRequest requestPost = new JsonArrayRequest(Request.Method.GET, url,  null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray jsonArray) {

                for (int i = 0; i < jsonArray.length(); i++) {
                    try {
                        String userId = jsonArray.getJSONObject(i).getString("userId");
                        String id = jsonArray.getJSONObject(i).getString("id");
                        String title = jsonArray.getJSONObject(i).getString("title");
                        String body = jsonArray.getJSONObject(i).getString("body");

                        Post p = new Post(userId,title,body);
                        labUP.insertPosts(p);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }

            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "No se han podido recuperar los datos de la página", Toast.LENGTH_SHORT).show();

            }
        });

        queue.add(requestPost);

    }


}

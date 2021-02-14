package com.example.andreacarballidop4pmdm.database;

import android.annotation.SuppressLint;
import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;

import com.example.andreacarballidop4pmdm.core.Post;
import com.example.andreacarballidop4pmdm.core.User;

import java.util.List;

public class LabUP {

    @SuppressLint("StaticFieldLeak")
    private static LabUP labUP;

    private DaoUP daoUP;

    private LabUP(Context context) {
        Context appContext = context.getApplicationContext();
        MyDataBase.AppDatabase database = Room.databaseBuilder(appContext, MyDataBase.AppDatabase.class, "Data").allowMainThreadQueries().build(); //.addMigrations ( MIGRATION_1_2 )
        daoUP = database.getDaoUP();
    }

    public static LabUP get(Context context) {
        if (labUP == null) {
            labUP = new LabUP(context);
        }
        return labUP;
    }

    public List<Post> getPosts() {
        return daoUP.getPosts();
    }

    public void insertPosts(Post post) {
        daoUP.insertPost(post);
    }

    public void updatePosts(Post post) {

        daoUP.update(post);
    }

    public void deletePosts(Post post) {
        daoUP.delete(post);
    }



    public List<User> getUsers() {
        return daoUP.getUsers();
    }

    public void insertUsers(User user) {
        daoUP.insertUser(user);
    }

    public void updateUsers(User user) {
        daoUP.update(user);
    }

    public void deleteUsers(User user) {
        daoUP.delete(user);
    }

    public User userId(String id) {
        return daoUP.userId(id);
    }
    public User name(String name) {
        return daoUP.name(name);
    }
    public Post getPost(int id) {
        return daoUP.getPost(id);
    }

}


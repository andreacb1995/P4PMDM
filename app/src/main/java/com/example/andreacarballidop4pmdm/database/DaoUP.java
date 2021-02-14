package com.example.andreacarballidop4pmdm.database;


import androidx.room.Dao;
import androidx.room.Database;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.example.andreacarballidop4pmdm.core.Post;
import com.example.andreacarballidop4pmdm.core.User;

import java.util.List;

@Dao
public interface DaoUP {

    @Query("SELECT * FROM Post")
    List<Post> getPosts();


    @Query("SELECT * FROM User")
    List<User> getUsers();

    @Transaction
    @Query("SELECT * FROM User")
    public List<UserWithPosts> getUsersWithPosts();

    @Query("SELECT * FROM Post WHERE id LIKE :uuid")
    Post getPost(int uuid);



    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertPost(Post post);

    @Delete
    void delete(Post post);

    @Update
    void update(Post post);


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertUser(User users);

    @Delete
    void delete(User users);

    @Update
    void update(User users);


    @Query("SELECT * FROM User WHERE id == :id")
    User userId(String id);

    @Query("SELECT *  FROM User WHERE name == :name")
    User name(String name);


}


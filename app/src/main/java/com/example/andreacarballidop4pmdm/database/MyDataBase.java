package com.example.andreacarballidop4pmdm.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.andreacarballidop4pmdm.core.Post;
import com.example.andreacarballidop4pmdm.core.User;

public class MyDataBase {
    @Database(entities = {Post.class,User.class}, version = 1, exportSchema = false)
    public static abstract class AppDatabase extends RoomDatabase {
        public abstract DaoUP getDaoUP();
    }
}

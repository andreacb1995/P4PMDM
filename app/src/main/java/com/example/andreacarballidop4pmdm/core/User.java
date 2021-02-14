package com.example.andreacarballidop4pmdm.core;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import org.json.JSONObject;

import java.io.Serializable;

@Entity(tableName = "User")
public class User implements Serializable{
    @PrimaryKey
    @NonNull
    public String id;
    @ColumnInfo(name="name")
    public String name;

    @ColumnInfo(name="nickname")
    public String nickname;

   @ColumnInfo(name="email")
    public String email;


    @ColumnInfo(name="telefono")
    public String telefono;

     @ColumnInfo(name="company")
    public String company;

    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public User(@NonNull String id, String name, String nickname, String email, String telefono, String company) {
        this.id = id;
        this.name = name;
        this.nickname = nickname;
        this.email = email;
        this.telefono = telefono;
        this.company = company;
    }
}


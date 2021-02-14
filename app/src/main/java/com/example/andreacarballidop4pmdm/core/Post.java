package com.example.andreacarballidop4pmdm.core;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.UUID;

import static androidx.room.ForeignKey.CASCADE;

@Entity(tableName = "Post")
public class Post implements Serializable {
    @ForeignKey(entity = User.class, parentColumns = "id", childColumns = "userId", onDelete = CASCADE)
    private String userId;

    @PrimaryKey(autoGenerate = true)
    @NonNull
    private int id;

    @ColumnInfo(name="titulo")
    private String titulo;

    @ColumnInfo(name="cuerpo")
    private String cuerpo;

    public Post(String userId,String titulo, String cuerpo){
        this.userId = userId;
        this.titulo = titulo;
        this.cuerpo = cuerpo;
    }
//    public Post() {
//        id = UUID.randomUUID().toString();
//    }
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getCuerpo() {
        return cuerpo;
    }

    public void setCuerpo(String cuerpo) {
        this.cuerpo = cuerpo;
    }

    public void modificarPost(String userId, String titulo, String cuerpo){
        this.userId = userId;
        this.titulo = titulo;
        this.cuerpo = cuerpo;
    }
}

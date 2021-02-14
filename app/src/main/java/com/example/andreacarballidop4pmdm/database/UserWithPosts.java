package com.example.andreacarballidop4pmdm.database;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.example.andreacarballidop4pmdm.core.Post;
import com.example.andreacarballidop4pmdm.core.User;

import java.util.List;

public class UserWithPosts {
   @Embedded
   public User user;
   @Relation( parentColumn = "id", entityColumn = "userId" )

   public List<Post> posts;

}



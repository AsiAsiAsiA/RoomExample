package com.example.roomexample.database;


import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Dog.class}, version = 1)
public abstract class DogDatabase extends RoomDatabase {
   public abstract DogDAO dogDao();
}
package com.example.roomexample.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.example.roomexample.database.Dog;
import com.example.roomexample.database.DogDAO;

@Database(entities = {Dog.class}, version = 1)
public abstract class DogDatabase extends RoomDatabase {
   public abstract DogDAO dogDao();
}
package com.example.roomexample.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.roomexample.database.Dog;

import java.util.List;

@Dao
public interface DogDAO {
 
   @Query("SELECT * FROM dog ")
   List<Dog> getAll();
 
   @Query("SELECT * FROM dog WHERE _id = :id")
   Dog getById(int id);
 
   @Insert
   void insert(Dog dog);
 
   @Update
   void update(Dog dog);
 
   @Delete
   void delete(Dog dog);
 
}
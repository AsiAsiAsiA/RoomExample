package com.example.roomexample.database;



import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Single;

@Dao
public interface DogDAO {
    @Query("SELECT * FROM dog ")
    Flowable<List<Dog>> getAll();

    @Query("SELECT * FROM dog WHERE _id = :id")
    Single<Dog> getById(int id);

    @Insert
    Completable insert(Dog dog);

    @Update
    Completable update(Dog dog);

    @Delete
    Completable delete(Dog dog);
}
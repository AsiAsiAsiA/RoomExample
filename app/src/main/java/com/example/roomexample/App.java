package com.example.roomexample;

import android.app.Application;

import com.example.roomexample.database.DogDatabase;

import androidx.room.Room;

public class App extends Application {
 
    public static App instance;
 
    private DogDatabase database;
 
    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        database = Room.databaseBuilder(this, DogDatabase.class, "database")
                //Oперации по работе с базой данных - синхронные, и должны выполняться не в UI потоке
                //это плохая практика, и может добавить ощутимых тормозов вашему приложению
                .allowMainThreadQueries()
                .build();
    }
 
    public static App getInstance() {
        return instance;
    }
 
    public DogDatabase getDatabase() {
        return database;
    }
}
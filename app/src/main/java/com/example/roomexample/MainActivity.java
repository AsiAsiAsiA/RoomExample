package com.example.roomexample;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.roomexample.database.Dog;
import com.example.roomexample.database.DogDAO;
import com.example.roomexample.database.DogDatabase;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity implements DogAdapter.ItemClickListener, DogAdapter.ItemLongClickListener {
    private DogAdapter dogAdapter;
    private Button btnOk;
    private EditText etName, etAge;
    DogDatabase db;
    DogDAO dogDAO;
    private List<Dog> listDogs;
    CompositeDisposable compositeDisposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = App.getInstance().getDatabase();
        dogDAO = db.dogDao();

        compositeDisposable = new CompositeDisposable();

        initViews();
    }

    private void initViews() {
        etName = findViewById(R.id.etName);
        etAge = findViewById(R.id.etAge);
        btnOk = findViewById(R.id.btnCreate);
        btnOk.setOnClickListener((v) ->
                addDog());

        listDogs = new ArrayList<>();
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        dogAdapter = new DogAdapter(listDogs);
        dogAdapter.setItemClickListener(this);
        dogAdapter.setItemLongClickListener(this);
        recyclerView.setAdapter(dogAdapter);

        Disposable disposable = dogDAO.getAll()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(listDogs ->
                        dogAdapter.setDogs(listDogs)
                );
        compositeDisposable.add(disposable);
    }

    private void addDog() {
        String name = etName.getText().toString();
        int age = Integer.parseInt(etAge.getText().toString());
        Dog dog = new Dog(name, age);

        Disposable disposable = dogDAO.insert(dog)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() ->
                        Toast.makeText(this,
                                "Success create " + dog.getName(),
                                Toast.LENGTH_SHORT).show());

        compositeDisposable.add(disposable);
    }


    @Override
    public void onClick(int id) {
        Disposable disposable = dogDAO.getById(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(dog ->
                        Toast.makeText(this, dog.toString(), Toast.LENGTH_SHORT).show());

        compositeDisposable.add(disposable);
    }

    @Override
    public void onLongClick(int id) {
        Intent intent = new Intent(this, EditActivity.class);
        intent.putExtra("_id", id);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        compositeDisposable.dispose();
        super.onDestroy();
    }
}

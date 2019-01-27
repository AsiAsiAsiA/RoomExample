package com.example.roomexample;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.roomexample.database.Dog;
import com.example.roomexample.database.DogDAO;
import com.example.roomexample.database.DogDatabase;

import java.util.List;

public class MainActivity extends AppCompatActivity implements DogAdapter.ItemClickListener, DogAdapter.ItemLongClickListener {
    private RecyclerView recyclerView;
    private DogAdapter dogAdapter;
    private Button btnOk;
    private EditText etName, etAge;
    DogDatabase db;
    DogDAO dogDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = App.getInstance().getDatabase();
        dogDAO = db.dogDao();

        initViews();
    }

    private void initViews() {
        etName = findViewById(R.id.etName);
        etAge = findViewById(R.id.etAge);
        btnOk = findViewById(R.id.btnCreate);
        btnOk.setOnClickListener((v) ->
                addDog());
        recyclerView = findViewById(R.id.recyclerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        dogAdapter = new DogAdapter(dogDAO.getAll());
        dogAdapter.setItemClickListener(this);
        dogAdapter.setItemLongClickListener(this);
        recyclerView.setAdapter(dogAdapter);
    }

    private void addDog() {
        String name = etName.getText().toString();
        int age = Integer.parseInt(etAge.getText().toString());
        Dog dog = new Dog(name, age);
        dogDAO.insert(dog);

        dogAdapter.setDogs(dogDAO.getAll());
        Toast.makeText(this,"Success create " + dog.getName(),Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onClick(int id) {
        String read = dogDAO.getById(id).toString();
        Toast.makeText(this, read, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onLongClick(int id) {
        Intent intent = new Intent(this, EditActivity.class);
        intent.putExtra("_id", id);
        startActivity(intent);
    }
}

package com.example.roomexample;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.roomexample.database.Dog;
import com.example.roomexample.database.DogDAO;
import com.example.roomexample.database.DogDatabase;

public class EditActivity extends AppCompatActivity {
    private int id;
    DogDatabase db;
    DogDAO dogDAO;
    private Button btnUpdate,btnDelete;
    private EditText etName, etAge;
    private TextView tvID;

    private Dog dog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        Intent intent = getIntent();
        id = intent.getIntExtra("_id",0);

        db = App.getInstance().getDatabase();
        dogDAO = db.dogDao();

        initViews();
        updateViews();
    }

    private void initViews() {
        btnUpdate = findViewById(R.id.btnUpdate);
        btnDelete = findViewById(R.id.btnDelete);
        etName = findViewById(R.id.etName);
        etAge = findViewById(R.id.etAge);
        tvID = findViewById(R.id.tvID);

        updateViews();

        btnDelete.setOnClickListener((v)-> {
            dogDAO.delete(dog);
            Toast.makeText(this,"Delete " + dog.getName(),Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this,MainActivity.class));
        });

        btnUpdate.setOnClickListener((v)->
            updateDog()
        );
    }

    private void updateDog() {
        dog.setName(etName.getText().toString());
        dog.setAge(Integer.parseInt(etAge.getText().toString()));
        dogDAO.update(dog);
        Toast.makeText(this,"Update " + dog.getName(),Toast.LENGTH_SHORT).show();
        startActivity(new Intent(this,MainActivity.class));
    }


    private void updateViews() {
        dog = dogDAO.getById(id);
        tvID.setText("ID: " + dog.get_id());
        etName.setText(dog.getName());
        etAge.setText(dog.getAge()+"");
    }
}

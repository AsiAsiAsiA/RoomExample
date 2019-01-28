package com.example.roomexample;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.roomexample.database.Dog;
import com.example.roomexample.database.DogDAO;
import com.example.roomexample.database.DogDatabase;

import androidx.appcompat.app.AppCompatActivity;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class EditActivity extends AppCompatActivity {
    private int id;
    DogDatabase db;
    DogDAO dogDAO;
    private Button btnUpdate, btnDelete;
    private EditText etName, etAge;
    private TextView tvID;
    CompositeDisposable compositeDisposable;

    private Dog dog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        Intent intent = getIntent();
        id = intent.getIntExtra("_id", 0);

        db = App.getInstance().getDatabase();
        dogDAO = db.dogDao();
        compositeDisposable = new CompositeDisposable();

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

        btnDelete.setOnClickListener((v) ->
                compositeDisposable.add(
                        dogDAO.delete(dog)
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(() -> {
                                    Toast.makeText(this,
                                            "Delete " + dog.getName(),
                                            Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(this, MainActivity.class));
                                }))
        );

        btnUpdate.setOnClickListener((v) -> updateDog());
    }

    private void updateDog() {
        dog.setName(etName.getText().toString());
        dog.setAge(Integer.parseInt(etAge.getText().toString()));
        compositeDisposable.add(dogDAO.update(dog)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> {
                    Toast.makeText(this,
                            "Update " + dog.getName(),
                            Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(this, MainActivity.class));
                }));
    }


    private void updateViews() {
        compositeDisposable.add(dogDAO.getById(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(dog -> {
                    this.dog = dog;
                    tvID.setText("ID: " + dog.get_id());
                    etName.setText(dog.getName());
                    etAge.setText(dog.getAge() + "");
                }));
    }

    @Override
    protected void onDestroy() {
        compositeDisposable.dispose();
        super.onDestroy();
    }
}

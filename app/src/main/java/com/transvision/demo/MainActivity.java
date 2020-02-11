package com.transvision.demo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.content.ContentValues;
import android.os.Bundle;

import android.view.View;

import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    EditText FirstName, LastName;
    RadioButton Male, Female;
    String selectedRadioButton;
    CheckBox BigData, Java, Andriod;
    DatabaseHelper db;
    Button submit;
    Button delete;
    Button FetchData;
    Button update;

    List<User> list;
    StringBuilder result;
    private RecyclerView recyclerView;
    private UserAdapter userAdapter;



    String[] skills = {"java", "andriod", "BigData"};
    String spresult = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db = new DatabaseHelper(this);
        db.open();

        list = new ArrayList<>();
        list = db.getAllUser ();

        recyclerView =  findViewById(R.id.UsersList);

        userAdapter= new UserAdapter(list);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator ());
        recyclerView.setAdapter(userAdapter);


        Spinner spinner = findViewById(R.id.simpleSpinner);
        spinner.setOnItemSelectedListener(this);
        ArrayAdapter arrayadapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, skills);
        arrayadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayadapter);

        FirstName = findViewById(R.id.editTextName);
        LastName = findViewById(R.id.et_lastname);
        Male = findViewById(R.id.male);
        Female = findViewById(R.id.female);
        BigData = findViewById(R.id.checkBox1);
        Java = findViewById(R.id.checkBox2);
        Andriod = findViewById(R.id.checkBox3);
        delete = findViewById(R.id.buttondelete);
        update = findViewById(R.id.buttonudate);
        submit = findViewById(R.id.buttonSubmit);
        result = new StringBuilder();


        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                db.deleteUsers(FirstName.getText().toString());
                db.open();
                //Toast.makeText(getApplicationContext(), String.valueOf(db.getUsersCount()), Toast.LENGTH_LONG).show();
            }
        });


        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.updateUser(FirstName.getText().toString(), LastName.getText().toString(),selectedRadioButton,result.toString(),spresult);
            }
        });


        //-----------------------------------------------------------------------------------------------------
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (FirstName.getText().toString().isEmpty() || LastName.getText().toString().isEmpty()
                ) {
                    Toast.makeText(getApplicationContext(), " First Enter Your Data", Toast.LENGTH_SHORT).show();
                } else {

                    if (Male.isChecked()) {
                        selectedRadioButton = Male.getText().toString();
                    }
                    if (Female.isChecked()) {
                        selectedRadioButton = Female.getText().toString();
                    }
                    if (BigData.isChecked()) {
                        result.append("BigData");
                    }
                    if (Java.isChecked()) {
                        result.append("Java");
                    }
                    if (Andriod.isChecked()) {
                        result.append("Andriod");
                    }


                    //insert your values from this class to DatabaseHelper
                    ContentValues contentValues = new ContentValues();
                    contentValues.put("FirstName", FirstName.getText().toString());
                    contentValues.put("LastName", LastName.getText().toString());
                    contentValues.put("Gender", selectedRadioButton);
                    contentValues.put("Language", result.toString());
                    contentValues.put("Skill", spresult);
                    db.insert_user(contentValues);


                    ContentValues contentValues1 = new ContentValues();
                    contentValues1.put("FirstName", FirstName.getText().toString());


                    Toast.makeText(getApplicationContext(), "FirstName -  " + FirstName.getText().toString()
                            + " \n" + "LastName -  " + LastName.getText().toString() + "\n" + "Gender - " + selectedRadioButton +
                            "\n" + "Language - " + result.toString() + "\n" + "Skill - " + spresult, Toast.LENGTH_SHORT).show();
                }

            }
        });
    }


    //-------------------------------------------------------------------------------------------------------
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        Toast.makeText(getApplicationContext(), skills[i], Toast.LENGTH_LONG).show();
        spresult = skills[i];
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}

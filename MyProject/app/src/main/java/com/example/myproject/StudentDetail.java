package com.example.myproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class StudentDetail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_detail);
        
        final String[] local = {"전체","광주","서울","부산"};
        Spinner spinner = (Spinner) findViewById(R.id.spinner4);

        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, local);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }
}
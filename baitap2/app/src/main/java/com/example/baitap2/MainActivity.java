package com.example.baitap2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private EditText numberA;
    private EditText numberB;

    private Button buttonAdd;
    private Button buttonSub;
    private Button buttonMul;
    private Button buttonDiv;

    private ListView listExp;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> arrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        numberA = findViewById(R.id.number_a);
        numberB = findViewById(R.id.number_b);
        listExp = findViewById(R.id.list_exp);
        arrayList = new ArrayList<String>();
        adapter = new ArrayAdapter<String>(getApplicationContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, arrayList);
        listExp.setAdapter(adapter);
        buttonAdd = findViewById(R.id.btn_add);
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer result = Integer.parseInt(numberA.getText().toString()) + Integer.parseInt(numberB.getText().toString());
                String expression = numberA.getText().toString() + " + " + numberB.getText().toString() + " = " + result.toString();
                adapter.add(expression);
                adapter.notifyDataSetChanged();
            }
        });
        buttonSub = findViewById(R.id.btn_sub);
        buttonSub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer result = Integer.parseInt(numberA.getText().toString()) - Integer.parseInt(numberB.getText().toString());
                String expression = numberA.getText().toString() + " - " + numberB.getText().toString() + " = " + result.toString();
                adapter.add(expression);
                adapter.notifyDataSetChanged();
            }
        });
        buttonMul = findViewById(R.id.btn_mul);
        buttonMul.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer result = Integer.parseInt(numberA.getText().toString()) * Integer.parseInt(numberB.getText().toString());
                String expression = numberA.getText().toString() + " * " + numberB.getText().toString() + " = " + result.toString();
                adapter.add(expression);
                adapter.notifyDataSetChanged();
            }
        });
        buttonDiv = findViewById(R.id.btn_div);
        buttonDiv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Double total = Double.parseDouble(numberA.getText().toString()) / Double.parseDouble(numberB.getText().toString());
                String expression = numberA.getText().toString() + " / " + numberB.getText().toString() + " = " + total.toString();
                adapter.add(expression);
                adapter.notifyDataSetChanged();
            }
        });
    }
}
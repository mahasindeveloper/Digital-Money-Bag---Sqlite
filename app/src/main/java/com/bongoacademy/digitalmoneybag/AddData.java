package com.bongoacademy.digitalmoneybag;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class AddData extends AppCompatActivity {

    TextView tvTitle;
    EditText edAmount,edReason;
    Button button;
    DatabaseHelper databaseHelper;

    public static boolean EXPENSE = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_data);

        tvTitle=findViewById(R.id.tvTitle);
        edAmount=findViewById(R.id.edAmount);
        edReason=findViewById(R.id.edReason);
        button=findViewById(R.id.button);
        databaseHelper=new DatabaseHelper(AddData.this);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String Samount =  edAmount.getText().toString();
                String reason= edReason.getText().toString();
                Double amount= Double.parseDouble(Samount);


                if (EXPENSE==true){
                    databaseHelper.addExpense(amount,reason);
                    tvTitle.setText("Expense Added!");

                }else {
                    databaseHelper.addIncome(amount,reason);
                    tvTitle.setText("Income Added!");
                }



            }
        });


    }

    //-----------------------------------------------

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }


    //-----------------------------------------------





}
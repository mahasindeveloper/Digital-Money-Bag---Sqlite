package com.bongoacademy.digitalmoneybag;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

public class ShowData extends AppCompatActivity {
    TextView tvShowdata;
    ListView listView;
    DatabaseHelper databaseHelper;
    HashMap<String,String> hashMap;
    ArrayList<HashMap<String,String>> arrayList;

    public static boolean EXPENSE = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_data);

        tvShowdata=findViewById(R.id.tvShowdata);
        listView=findViewById(R.id.listView);
        databaseHelper =new DatabaseHelper(ShowData.this);


        if (EXPENSE==true){
            tvShowdata.setText("Expenses Data Showing");
            loadData();

        }else {
            tvShowdata.setText("Income Data Showing");
            loadData();

        }





    }
    //=======================================================================================================


    public void loadData(){
        Cursor cursor = null;

        if (EXPENSE==true) {
            cursor = databaseHelper.showDataExpenses();
        } else {
            cursor = databaseHelper.showDataIncome();
        }

        if (cursor != null && cursor.getCount() > 0) {
            arrayList = new ArrayList<>();

            while (cursor.moveToNext()) {
                int id = cursor.getInt(0);
                Double amount = cursor.getDouble(1);
                String reason = cursor.getString(2);
                String time = cursor.getString(3);

                hashMap = new HashMap<>();
                hashMap.put("id", "" + id);
                hashMap.put("amount", String.valueOf(amount));
                hashMap.put("reason", reason);
                hashMap.put("time", time);
                arrayList.add(hashMap);
            }

            listView.setAdapter(new MyAdapter());

        } else {
            Toast.makeText(this, "No Data Found", Toast.LENGTH_SHORT).show();
        }


    }






    //=========================================================================

    public class MyAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return arrayList.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater layoutInflater = getLayoutInflater();
            View myView = layoutInflater.inflate(R.layout.data_item, parent, false);

            TextView tvReason = myView.findViewById(R.id.tvReason);
            TextView tvAmount = myView.findViewById(R.id.tvAmount);
            TextView tvTime = myView.findViewById(R.id.tvTime);
            TextView tvDelete = myView.findViewById(R.id.tvDelete);
            ImageView imageIcon = myView.findViewById(R.id.imageIcon);

            hashMap = arrayList.get(position);

            String reason = hashMap.get("reason");
            String amount = hashMap.get("amount");
            String time = hashMap.get("time");
            String id = hashMap.get("id");

            tvReason.setText(reason);
            tvAmount.setText(amount);
            tvTime.setText(time);

            tvDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (EXPENSE==true) {

                        databaseHelper.DeleteByIDExpenses(id);
                        loadData(); // Reload data after deletion

                    } else {

                        imageIcon.setImageResource(R.drawable.income);
                        databaseHelper.DeleteByIDIncome(id);
                        loadData(); // Reload data after deletion

                    }
                }
            });

            return myView;
        }
    }





}
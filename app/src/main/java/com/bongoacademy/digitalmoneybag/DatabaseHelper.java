package com.bongoacademy.digitalmoneybag;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DatabaseHelper extends SQLiteOpenHelper {



    public DatabaseHelper(Context context) {
        super(context, "digital_moneybag", null, 1);
    }

    @Override
     public void onCreate(SQLiteDatabase db) {
        db.execSQL(" CREATE TABLE expense (id INTEGER primary key autoincrement,amount DOUBLE, reason TEXT, time TEXT) ");
        db.execSQL(" CREATE TABLE income (id INTEGER primary key autoincrement,amount DOUBLE, reason TEXT, time TEXT) ");

     }

     @Override
     public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(" drop table if exists expense ");
        db.execSQL(" drop table if exists income");

     }


     //================================================================================

     public void addExpense(double amount, String reason){

         SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
         String currentTime = sdf.format(new Date());


         SQLiteDatabase db=this.getWritableDatabase();
         ContentValues contentValues=new ContentValues();
         contentValues.put("amount",amount);
         contentValues.put("reason",reason);
         contentValues.put("time",currentTime);
         db.insert("expense",null,contentValues);

     }

     //=========================================================================================


    public void addIncome(double amount, String reason){


        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        String currentTime = sdf.format(new Date());

        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put("amount",amount);
        contentValues.put("reason",reason);
        contentValues.put("time",currentTime);
        db.insert("income",null,contentValues);

    }

    //=========================================================================================



     //=========================================================================================

    public Double calculateTotalExpenses(){

        Double TotalExpense = (double) 0;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor=db.rawQuery("select * from expense",null);

        if (cursor != null && cursor.getCount()>0 ){

            while (cursor.moveToNext()){
                Double amount= cursor.getDouble(1);

                TotalExpense=TotalExpense+amount;

            }

        }

        return TotalExpense;

    }





     //=========================================================================================
     public Double calculateTotalIncome(){

         Double total = (double) 0;
         SQLiteDatabase db = this.getReadableDatabase();
         Cursor cursor=db.rawQuery("select * from income",null);

         if (cursor != null && cursor.getCount()>0 ){

             while (cursor.moveToNext()){
                 Double amount= cursor.getDouble(1);

                 total=total+amount;

             }

         }

         return total;

     }




    public Cursor showDataExpenses(){
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("select * from expense ORDER BY id DESC", null);
    }

    public Cursor showDataIncome(){
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("select * from income ORDER BY id DESC", null);
    }

    public void DeleteByIDExpenses(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM expense WHERE id like "+id);
    }

    public void DeleteByIDIncome(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM income WHERE id like "+id);
    }





}

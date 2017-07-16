package com.example.udit.finalto_do;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by udit on 7/7/2017.
 */

public class databasehelper extends SQLiteOpenHelper {
    public final static String Expense_Table_name="Expense";
    public final static String Expense_Id="_id";
    public final static String Expense_title="title";
    public final static String Expense_Category="category";
    public final static String Expense_desciption="desciption";
    public final static String Expense_date="date";
    public final static String Expense_time="time";
    public final static String Table_name="datetime";
    public final static String Expense_color="color";
    public databasehelper(Context context) {
        super(context, "Expense.db",null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query="create table "+ Expense_Table_name+"("+Expense_Id+" integer primary key autoincrement,"+Expense_date+" text, "+Expense_title+" text,"+Expense_time+" text, "+Expense_desciption+" text,"+Expense_Category+" text);";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}


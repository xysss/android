package com.example.mydapplication;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


/**
 * Created by 44265 on 2016/7/22.
 */
public class FoodBaseHelper extends SQLiteOpenHelper {
    private static final int VERSION=1;
    private static final String DATABASE_NAME="FoodBase.db";
    public FoodBaseHelper(Context context){
        super(context,DATABASE_NAME,null,VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table Foods("+
            FoodDbSchema.FoodTable.Cols.NAME+","+
            FoodDbSchema.FoodTable.Cols.WHETHERBAD+","+
            FoodDbSchema.FoodTable.Cols.Day+","+
            FoodDbSchema.FoodTable.Cols.WHETHERENOUGH+
            ")"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}

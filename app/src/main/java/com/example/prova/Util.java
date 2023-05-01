package com.example.prova;

import android.content.Context;
import android.widget.Toast;

import com.example.prova.model.DBSingleton;
import com.example.prova.model.Database;

public class Util {
    public static Database getInstanceOfDatabase(Context context) {
        DBSingleton object  = DBSingleton.object.getInstance(context);
        return object.db;
    }

    public static void createToast(Context context, String message){
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
}

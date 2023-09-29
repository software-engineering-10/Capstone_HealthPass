package com.example.capstone_healthpass.DB;

import android.content.Context;
import android.content.res.AssetManager;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOError;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class DataBaseHelper extends SQLiteOpenHelper {
    private final static String TAG = "DataBaseHelper";
    private static final int version = 1;
    private static String DB_PATH = "";
    private static String DB_NAME="info.db";
    private SQLiteDatabase mDataBase;
    private Context mContext;
    public DataBaseHelper(Context context){
        super(context, DB_NAME, null, 1);
        DB_PATH = context.getDatabasePath(DB_NAME).getPath(); // 데이터베이스 파일 경로 설정
        this.mContext = context;
        dataBaseCheck();
    }
    private void dataBaseCheck() {
        File dbFile = new File(DB_PATH + DB_NAME);
        if (!dbFile.exists()) {
            dbCopy();
            Log.d(TAG,"Database is copied.");
        }

    }

    private void dbCopy() {
        try {
            InputStream inputStream = mContext.getAssets().open(DB_NAME);
            OutputStream outputStream = new FileOutputStream(DB_PATH);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = inputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, length);
            }
            outputStream.flush();
            outputStream.close();
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
            Log.d(TAG, "dbCopy: IOException 발생함");
        }
    }
    @Override
    public synchronized void close() {
        if (mDataBase != null) {
            mDataBase.close();
        }
        super.close();
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        super.onOpen(db);
        //Toast.makeText(mContext,"onOpen()",Toast.LENGTH_SHORT).show();
        Log.d(TAG,"onOpen() : DB Opening!");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        Log.d(TAG,"onUpgrade() : DB Schema Modified and Excuting onCreate()");
    }
}


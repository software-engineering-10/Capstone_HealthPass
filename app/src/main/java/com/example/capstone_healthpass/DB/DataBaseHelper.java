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
    private static final int version = 8;
    private static String DB_PATH = "";
    private static String DB_NAME="info.db";
    private SQLiteDatabase mDataBase;
    private Context mContext;
    public DataBaseHelper(Context context){
        super(context,  new File(context.getExternalFilesDir(null),
                DB_NAME).toString(),null,version);

        DB_PATH = context.getExternalFilesDir(null).getPath() + File.separator;
        mDataBase = this.getWritableDatabase();
        this.mContext = context;
        dataBaseCheck();
    }
    private void dataBaseCheck() {
        File dbFile = new File(DB_PATH + DB_NAME);
        if (!dbFile.exists()) {
            dbCopy();

        }

    }


    @Override
    public synchronized void close() {
        if (mDataBase != null) {
            mDataBase.close();
        }
        super.close();
    }
    private void dbCopy() {

        try {


            InputStream inputStream = mContext.getAssets().open(DB_NAME);

            String out_filename = DB_PATH + DB_NAME;

            OutputStream outputStream = new FileOutputStream(out_filename);
            byte[] mBuffer = new byte[1024];
            int mLength;
            while ((mLength = inputStream.read(mBuffer)) > 0) {
                outputStream.write(mBuffer,0,mLength);
            }
            outputStream.flush();;
            outputStream.close();
            inputStream.close();

        } catch (IOException e) {
            e.printStackTrace();
            Log.d("dbCopy","IOException 발생함");
        }
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

package com.example.capstone_healthpass;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.capstone_healthpass.server.Reservation;
import com.example.capstone_healthpass.server.RetrofitManager;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ScanQR extends AppCompatActivity {
    RetrofitManager retrofitManager = new RetrofitManager();
    private ArrayList<String> array;
    private String reserveMinute;
    private String reserveDay;
    private String reserveTime;
    private String reserveEx_name;
    private String reserveSeat;
    private String qrData;
    private String[] datas;
    private String strMinute;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_qr);

        new IntentIntegrator(this).initiateScan();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() == null) {
                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();
                // todo
            } else {
                qrData = result.getContents();
                datas = qrData.split(",");
                String url = datas[0];

                if (datas.length != 1) {
                    checkInfo();

                }
                else{
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    startActivity(browserIntent);
                    finish();
                }


            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
    private void checkInfo(){
        retrofitManager.getApiService().reservedInfo(MainActivity.email).enqueue(new Callback<List<Reservation>>() {
            @Override
            public void onResponse(Call<List<Reservation>> call, Response<List<Reservation>> response) {
                if(response.code()==201){
                    List<Reservation> reservationList = response.body();
                    Log.d("responseCheck",response.body().toString());
                    array=new ArrayList<>();
                    for(Reservation reservation:reservationList) {
                        reserveMinute = reservation.getMinute();
                        reserveTime = reservation.getTime();
                        reserveDay = reservation.getDay();
                        reserveSeat = reservation.getSeat();
                        reserveEx_name = reservation.getEx_name();
                        String str = reserveDay + " " + reserveTime + " " + reserveMinute + " " + reserveSeat + " "+reserveEx_name;
                        array.add(str);
                    }
                    Calendar calendar = Calendar.getInstance();
                    Date currentDate = calendar.getTime();
                    // 원하는 날짜 형식을 지정합니다. (예: "yyyy-MM-dd HH:mm")
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                    String formattedDate = dateFormat.format(currentDate);

                    int minute = currentDate.getMinutes();

                    if(minute > 30){
                        strMinute="30분";
                    }
                    else{
                        strMinute="0분";
                    }
                    String checkStr = formattedDate + " "+currentDate.getHours()+"시 "+strMinute+" "+datas[0]+" "+datas[1];
                    Log.d("CheckStr",checkStr);
                    boolean flag = false;
                    for(int i=0;i<array.size();i++){
                        Log.d("checkData",array.get(i));
                        if (checkStr.equals(array.get(i))){
                            flag = true;
                            Log.d("성공","success");
                            canceled(formattedDate,currentDate.getHours()+"시",strMinute,datas[0],datas[1]);
                        }
                    }
                    if(!flag){
                        Toast.makeText(ScanQR.this, "예약 내역이 존재하지 않습니다.", Toast.LENGTH_SHORT).show();
                        finish();
                    }

                }
            }

            @Override
            public void onFailure(Call<List<Reservation>> call, Throwable t) {
                Log.d("myPageTest",t.toString());
            }
        });
    }
    public void canceled(final String day, final String time, final String minute, final String seat, final String ex_name){

        retrofitManager.getApiService().reservedCancel(day,time,minute,seat,ex_name).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.code()==201){

                    Toast.makeText(ScanQR.this, time+" "+strMinute+" 부터 \n30분 이용 가능합니다.", Toast.LENGTH_SHORT).show();

                    finish();

                }


            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d("삭제",t.toString());
            }
        });
    }
}
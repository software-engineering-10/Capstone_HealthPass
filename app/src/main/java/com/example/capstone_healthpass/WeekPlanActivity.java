package com.example.capstone_healthpass;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class WeekPlanActivity extends Activity {
    ArrayList<Plan> planArray;

    final List<Button> weekButtons = new ArrayList<>();
    final List<LinearLayout> weekLayouts = new ArrayList<>();
    Button del;
    Button sav;
    Button back;
    ListView listView;
    ListAdapter listViewAdapter;
    String selectedDay;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weekplane);
        del = findViewById(R.id.del);
        sav = findViewById(R.id.sav);
        back = findViewById(R.id.back);
        planArray = new ArrayList<>();

        weekButtons.addAll(Arrays.asList(
                findViewById(R.id.sun),
                findViewById(R.id.mon),
                findViewById(R.id.tue),
                findViewById(R.id.wed),
                findViewById(R.id.thu),
                findViewById(R.id.fri),
                findViewById(R.id.sat)
        ));



        weekLayouts.addAll(Arrays.asList(
                findViewById(R.id.sundayList),
                findViewById(R.id.mondayList),
                findViewById(R.id.tuesdayList),
                findViewById(R.id.wednesdayList),
                findViewById(R.id.thursdayList),
                findViewById(R.id.fridayList),
                findViewById(R.id.saturdayList)
        ));

        listView = findViewById(R.id.list);


        for (int i = 0; i < planArray.size(); i++) {
            TextView newTextView = new TextView(this);

            //텍스트뷰에 들어갈 내용 설정
            newTextView.setText(planArray.get(i).getExerPartArray());

            newTextView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            newTextView.setBackgroundColor(Color.parseColor("#FC3F88EB"));
            newTextView.setTextColor(Color.parseColor("#FC3F88EB"));

            int index = convertDayToIndex(planArray.get(i).getWeekly());
            if (index < 0) continue;

            weekLayouts.get(index).addView(newTextView);
        }

        //리스트뷰 초기화
        String[] listViewItems = new String[]{"팔", "어깨", "하체", "가슴", "등"};
        listViewAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listViewItems);
        listView.setAdapter(listViewAdapter);
        listView.setOnItemClickListener((adapterView, view, i, l) -> handleListItemClick(i));

        List<String> days = Arrays.asList("일", "월", "화", "수", "목", "금", "토");
        for (int i = 0; i < days.size(); i++) {
            final String day = days.get(i);

            weekButtons.get(i).setOnClickListener(v -> {
                selectedDay = day;
            });
        }

        sav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //db
                Toast.makeText(WeekPlanActivity.this, "저장", Toast.LENGTH_LONG).show();

            }
        });

        back.setOnClickListener(v -> {
            finish();
        });

        del.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            final String[] items = {"확인", "취소"};
            builder.setTitle("확인을 누르시면 스케쥴 내용이 전체 삭제됩니다.");
            builder.setItems(items, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if (which == 0) {
                        // "확인" 버튼 클릭 시 삭제 로직 수행
                        planArray = planArray.stream()
                                .filter(e -> !TextUtils.equals(e.getWeekly(), selectedDay))
                                .collect(Collectors.toCollection(ArrayList::new));

                        // UI에서도 해당 텍스트뷰 삭제
                        int index = convertDayToIndex(selectedDay);
                        if (index < 0) return;

                        weekLayouts.get(index).removeAllViews();
                    }
                }
            });

            builder.show();
        });
    }

    private void handleListItemClick(int position) {
        String strText = (String) listView.getItemAtPosition(position);
        Log.d("Fragment: ", position + ": " + strText);

        //텍스트뷰 객체 생성
        TextView newTextView = new TextView(this);

        //텍스트뷰에 들어갈 내용 설정
        newTextView.setText(strText);

        //텍스트 중앙정렬
        newTextView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        newTextView.setBackgroundColor(Color.parseColor("#FF8B3E"));
        newTextView.setTextColor(Color.parseColor("#FFFFFF"));

        int index = convertDayToIndex(selectedDay);
        if (index < 0) return;

        Plan sunPlan = new Plan(selectedDay, strText);
        planArray.add(sunPlan);
        weekLayouts.get(index).addView(newTextView);
    }

    private int convertDayToIndex(String day) {
        if (day == null)
            return -1;

        switch (day) {
            case "일":
                return 0;
            case "월":
                return 1;
            case "화":
                return 2;
            case "수":
                return 3;
            case "목":
                return 4;
            case "금":
                return 5;
            case "토":
                return 6;
        }

        return -1;
    }
}
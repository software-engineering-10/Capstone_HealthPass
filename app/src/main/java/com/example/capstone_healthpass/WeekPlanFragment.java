package com.example.capstone_healthpass;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.fragment.app.ListFragment;

import java.util.ArrayList;

public class WeekPlanFragment extends ListFragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    ListView listView;
    private ListAdapter listViewAdapter;
    ArrayList<Plan> planArray;
    WeekPlanFragment WeekPlanFragment;

    Button button1;
    Button button2;
    Button button3;
    Button button4;
    Button button5;
    Button button6;
    Button button7;
    Button delbtn;
    Button savbtn;
    Button back;

    private FragmentManager fragmentManager;


    private LinearLayout sList, mList, tList, wList, thList, fList, satList;



    //선택된 요일
    private String selectedDay;

    public static WeekPlanFragment newInstance(String param1, String param2) {
        WeekPlanFragment fragment = new WeekPlanFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.activity_weekplane, container, false);


        planArray=new ArrayList<>();




        button1 = (Button)rootView.findViewById(R.id.sun);
        button2 = (Button)rootView.findViewById(R.id.mon);
        button3 = (Button)rootView.findViewById(R.id.tue);
        button4 = (Button)rootView.findViewById(R.id.wed);
        button5 = (Button)rootView.findViewById(R.id.thu);
        button6 = (Button)rootView.findViewById(R.id.fri);
        button7 = (Button)rootView.findViewById(R.id.sat);
        delbtn = (Button)rootView.findViewById(R.id.del);
        savbtn = (Button)rootView.findViewById(R.id.sav);

        sList=(LinearLayout) rootView.findViewById(R.id.sundayList);
        mList=(LinearLayout) rootView.findViewById(R.id.mondayList);
        tList=(LinearLayout) rootView.findViewById(R.id.tuesdayList);
        wList=(LinearLayout) rootView.findViewById(R.id.wednesdayList);
        thList=(LinearLayout) rootView.findViewById(R.id.thursdayList);
        fList=(LinearLayout) rootView.findViewById(R.id.fridayList);
        satList=(LinearLayout) rootView.findViewById(R.id.saturdayList);

        planArray.clear();


        for(int i=0;i<planArray.size();i++){
            TextView newTextView = new TextView(getContext());

            //텍스트뷰에 들어갈 내용 설정
            newTextView.setText(planArray.get(i).getExerPartArray());

            newTextView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            newTextView.setBackgroundColor(Color.parseColor("#FC3F88EB"));
            newTextView.setTextColor(Color.parseColor("#FC3F88EB"));

            switch (planArray.get(i).getWeekly()){
                case "일":
                    sList.addView(newTextView);
                    break;
                case "월":
                    mList.addView(newTextView);
                    break;
                case "화":
                    tList.addView(newTextView);
                    break;
                case "수":
                    wList.addView(newTextView);
                    break;
                case "목":
                    thList.addView(newTextView);
                    break;
                case "금":
                    fList.addView(newTextView);
                    break;
                case "토":
                    satList.addView(newTextView);
                    break;
                default:
                    Toast.makeText(getActivity(), "운동 추가 에러", Toast.LENGTH_SHORT).show();
            }
        }

        //리스트뷰 초기화
        String[] inivalues = new String[] {};
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, inivalues);
        setListAdapter(adapter2);

        String[] values = new String[] {"팔", "어깨", "하체", "가슴", "등"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, values);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        final String[] items = {"확인", "취소"};
        builder.setTitle("확인을 누르시면 스케쥴 내용이 전체 삭제됩니다.");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == 0) {
                    // "확인" 버튼 클릭 시 삭제 로직 수행
                    for (int i = 0; i < planArray.size(); i++) {
                        if (planArray.get(i).getWeekly().equals(selectedDay)) {
                            // 해당 요일에 해당하는 항목을 삭제
                            planArray.remove(i);
                            // UI에서도 해당 텍스트뷰 삭제
                            switch (selectedDay) {
                                case "일":
                                    sList.removeViewAt(i);
                                    break;
                                case "월":
                                    mList.removeViewAt(i);
                                    break;
                                case "화":
                                    tList.removeViewAt(i);
                                    break;
                                case "수":
                                    wList.removeViewAt(i);
                                    break;
                                case "목":
                                    thList.removeViewAt(i);
                                    break;
                                case "금":
                                    fList.removeViewAt(i);
                                    break;
                                case "토":
                                    satList.removeViewAt(i);
                                    break;
                            }
                            i--; // 리스트의 크기가 변경되었으므로 인덱스 감소
                        }
                    }
                    // UI 업데이트
                    refreshFg();
                }
            }
        });



        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setListAdapter(adapter);
                selectedDay="일";
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setListAdapter(adapter);
                selectedDay="월";
            }
        });
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setListAdapter(adapter);
                selectedDay="화";
            }
        });
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setListAdapter(adapter);
                selectedDay="수";
            }
        });
        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setListAdapter(adapter);
                selectedDay="목";
            }
        });
        button6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setListAdapter(adapter);
                selectedDay="금";
            }
        });
        button7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setListAdapter(adapter);
                selectedDay="토";
            }
        });

        savbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               //weekPlanSystem.initTable(db, ID);
                for(int i=0;i<planArray.size();i++){
//                    System.out.println("요일= "+planArray.get(i).getWeekly()+" 운동부위= "+planArray.get(i).getExerPartArray());
                   // weekPlanSystem.addPlan(db,ID,planArray.get(i));
                }
                String[] values=new String[] {};
                ArrayAdapter<String> adapter=new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1,values);
                setListAdapter(adapter);
//                System.out.println("size="+planArray.size());
            }
        });

        back = (Button) rootView.findViewById(R.id.back);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // "이전" 버튼을 눌렀을 때 메인 액티비티로 이동하는 코드
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
            }
        });

        delbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder.show();
            }
        });

        return rootView;
    }


    //아이템 클릭 이벤트
    @Override
    public void onListItemClick(ListView l, View v, int position, long id){
//        TextView textView1 = (TextView)v.findViewById(R.id.textS);
        String strText = (String) l.getItemAtPosition(position); //이게 운동 목록
           String test="";
            Log.d("Fragment: ", position + ": " +strText);
            Toast.makeText(this.getContext(), "클릭: " + position +" " + strText, Toast.LENGTH_SHORT).show();

        //텍스트뷰 객체 생성
        TextView newTextView = new TextView(getContext());

        //텍스트뷰에 들어갈 내용 설정
        newTextView.setText(strText);

        //텍스트 중앙정렬
        newTextView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        newTextView.setBackgroundColor(Color.parseColor("#FF8B3E"));
        newTextView.setTextColor(Color.parseColor("#FFFFFF"));

        //생성 및 설정된 텍스트뷰를 레이아웃에 적용
        switch (selectedDay){
            case "일":
                sList.addView(newTextView);
                Plan sunPlan=new Plan("일", strText);
                planArray.add(sunPlan);
                break;
            case "월":
                mList.addView(newTextView);
                Plan monPlan=new Plan("월", strText);
                planArray.add(monPlan);
                break;
            case "화":
                tList.addView(newTextView);
                Plan tuePlan=new Plan("화", strText);
                planArray.add(tuePlan);
                break;
            case "수":
                wList.addView(newTextView);
                Plan wedPlan=new Plan("수", strText);
                planArray.add(wedPlan);
                break;
            case "목":
                thList.addView(newTextView);
                Plan thuPlan=new Plan("목", strText);
                planArray.add(thuPlan);
                break;
            case "금":
                fList.addView(newTextView);
                Plan friPlan=new Plan("금", strText);
                planArray.add(friPlan);
                break;
            case "토":
                satList.addView(newTextView);
                Plan satPlan=new Plan("토", strText);
                planArray.add(satPlan);
                break;
            default:
                Toast.makeText(getActivity(), "운동 추가 에러", Toast.LENGTH_SHORT).show();
        }
    }



    public void refreshFg(){
        FragmentTransaction ft=getFragmentManager().beginTransaction();
        ft.detach(this).attach(this).commit();
    }



}

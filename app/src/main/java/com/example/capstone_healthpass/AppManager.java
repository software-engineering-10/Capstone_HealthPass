package com.example.capstone_healthpass;

import android.app.Activity;

import java.util.ArrayList;

// 앱 전체에서 관리할 수 있는 클래스 (예: AppManager)
public class AppManager {
    private static ArrayList<Activity> activityList = new ArrayList<>();

    // 액티비티를 리스트에 추가
    public static void addActivity(Activity activity) {
        activityList.add(activity);
    }

    // 모든 액티비티를 종료
    public static void finishAllActivities() {
        for (Activity activity : activityList) {
            if (!activity.isFinishing()) {
                activity.finish();
            }
        }
        activityList.clear();
    }
}

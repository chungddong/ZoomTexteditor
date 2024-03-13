package com.example.editordel;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import java.util.Arrays;

public class FIleStorageManager {
    private static final String PREFS_NAME = "ZTEPrefs";
    private static final String PREF_URI_LIST = "UriList";


    public void makePreferences()
    {

    }


    public void saveFileUri(Context context, Uri uri) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        // 기존 URI 리스트를 가져와서 새로운 URI를 추가
        String uriList = prefs.getString(PREF_URI_LIST, "");

        // 리스트에 파일이 이미 있는지 확인
        if (!isUriExists(uriList, uri.toString())) {
            // 파일이 리스트에 없으면 추가
            uriList += uri.toString() + ";"; // 구분자를 사용하여 URI를 저장
            editor.putString(PREF_URI_LIST, uriList);
            editor.apply();
        }
        else {
            Toast.makeText(context, "이미 추가된 파일입니다!", Toast.LENGTH_SHORT).show();
        }
    }

    private static boolean isUriExists(String uriList, String uri) {
        String[] uris = uriList.split(";");
        return Arrays.asList(uris).contains(uri);
    }

    public String[] getSavedFileUris(Context context) {

        /*SharedPreferences preferences = context.getSharedPreferences("ZTEPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear(); // SharedPreferences 내용을 모두 삭제
        editor.apply();*/


        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        String uriList = prefs.getString(PREF_URI_LIST, "");
        Log.v("ZTE", ""+ uriList);
        return uriList.split(";");
    }
}

package com.example.android.notepad;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

public class ParseJSONHelper {
    @SuppressWarnings("unused")
    private static final boolean DEBUG = true;
    private static final String TAG = "zyw";
    
    public static FetchBean parseFetchBeanByJSONSingle(String data) {
        boolean jsonOk = true;
        FetchBean fetchBean = null;
        Log.i(TAG, ">>>>>>start parseFetchBeanByJSONSingle="+data);
        if (data == null || data.isEmpty()) return null;
        try {
            JSONObject jsonObject = new JSONObject(data);
            boolean login_result = jsonObject.getBoolean("login_result");
            boolean fetch_result = jsonObject.getBoolean("fetch_result");
            String fetch_cmd = jsonObject.getString("fetch_cmd");
            String error_info = jsonObject.getString("error_info");
            String result_data = jsonObject.getString("result_data");
            //JSONObject noteBeanJSON = jsonObject.getJSONObject("result_data");
            //JSONArray noteBeanJSON = jsonObject.getJSONArray("result_data");
            fetchBean = new FetchBean(login_result, fetch_result, fetch_cmd, error_info, parseNoteBeanByJSON(result_data));
        } catch (JSONException e) {
            e.printStackTrace();
            jsonOk = false;
        }
        if (!jsonOk) return null;
        return fetchBean;
    }
    
    public static FetchBean parseFetchBeanByGsonSingle(String data) {
        boolean jsonOk = true;
        FetchBean fetchBean = null;
        Log.i(TAG, ">>>>>>start parseFetchBeanByGsonSingle="+data);
        if (data == null || data.isEmpty()) return null;
        try {
            Gson gson = new Gson();
            fetchBean = gson.fromJson(data, FetchBean.class);;
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
            jsonOk = false;
        }
        if (!jsonOk) return null;
        return fetchBean;
    }
    
    public static NoteBean parseNoteBeanByJSONSingle(String data) {
        boolean jsonOk = true;
        NoteBean noteBean = null;
        Log.i(TAG, ">>>>>>start parseNoteBeanByJSONSingle="+data);
        if (data == null || data.isEmpty()) return null;
        try {
            JSONObject jsonObject = new JSONObject(data);
            int note_id = jsonObject.getInt("note_id");
            String email = jsonObject.getString("email");
            String note_title = jsonObject.getString("note_title");
            String note_content = jsonObject.getString("note_content");
            String create_date = jsonObject.getString("create_date");
            String modify_date = jsonObject.getString("modify_date");
            noteBean = new NoteBean(note_id, email, note_title, note_content, create_date, modify_date);
        } catch (JSONException e) {
            e.printStackTrace();
            jsonOk = false;
        }
        if (!jsonOk) return null;
        return noteBean;
    }
    
    public static NoteBean parseNoteBeanByGsonSingle(String data) {
        boolean jsonOk = true;
        NoteBean noteBean = null;
        Log.i(TAG, ">>>>>>start parseNoteBeanByGsonSingle="+data);
        if (data == null || data.isEmpty()) return null;
        try {
            Gson gson = new Gson();
            noteBean = gson.fromJson(data, NoteBean.class);;
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
            jsonOk = false;
        }
        if (!jsonOk) return null;
        return noteBean;
    }
    
    public static List<NoteBean> parseNoteBeanByJSON(String data) {
        boolean jsonOk = true;
        Log.i(TAG, ">>>>>>start parseNoteBeanByJSON="+data);
        if (data == null || data.isEmpty()) return null;
        List<NoteBean> noteBeanList = new ArrayList<NoteBean>();
        try {
            JSONArray jsonArray = new JSONArray(data);
            for(int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                int note_id = jsonObject.getInt("note_id");
                String email = jsonObject.getString("email");
                String note_title = jsonObject.getString("note_title");
                String note_content = jsonObject.getString("note_content");
                String create_date = jsonObject.getString("create_date");
                String modify_date = jsonObject.getString("modify_date");
                noteBeanList.add(new NoteBean(note_id, email, note_title, note_content, create_date, modify_date));
            }
        } catch (JSONException e) {
            e.printStackTrace();
            jsonOk = false;
        }
        if (!jsonOk) return null;
        return noteBeanList;
    }
    
    public static List<NoteBean> parseNoteBeanByGson(String data) {
        List<NoteBean> noteBeanList = null;
        boolean jsonOk = true;
        Log.i(TAG, ">>>>>>start parseNoteBeanByGson="+data);
        if (data == null || data.isEmpty()) return null;
        try {
            Gson gson = new Gson();
            Type listType = new TypeToken<List<NoteBean>>(){}.getType();
            noteBeanList = gson.fromJson(data, listType);
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
            jsonOk = false;
        }
        if (!jsonOk) return null;
        return noteBeanList;
    }
}
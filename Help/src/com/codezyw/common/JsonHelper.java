
package com.codezyw.common;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

public class JsonHelper<E> {
    @SuppressWarnings("unused")
    private static final boolean DEBUG = true;
    @SuppressWarnings("unused")
    private static final String TAG = "zyw";

    // HttpDataContentBean
    public static final String NOTE_ID = "note_id";

    public static final String ACCOUNT = "email";

    public static final String PASSWORD = "pass";

    public static final String TITLE = "note_title";

    public static final String CONTENT = "note_content";

    public static final String CMD_KEY = "cmd";

    public static final String FETCH_CMD_DELETE = "delete_note";

    public static final String FETCH_CMD_UPDATE = "update_note";

    public static final String FETCH_CMD_INSERT = "insert_note";

    public static final String FETCH_CMD_FETCH = "fetch_note";

    public static final int MSG_FETCH = 0x1000;

    public static final int MSG_DELETE = 0x1001;

    public static final int MSG_UPDATE = 0x1002;

    public String createJson(Map<String, E> map) {
        return null;
    }

    public static HttpResultBean parseHttpResultBeanByJSONSingle(String data) {
        boolean jsonOk = true;
        HttpResultBean httpResultBean = null;
        if (data == null || data.isEmpty())
            return null;
        try {
            JSONObject jsonObject = new JSONObject(data);
            boolean login_result = jsonObject.getBoolean("login_result");
            boolean fetch_result = jsonObject.getBoolean("fetch_result");
            String fetch_cmd = jsonObject.getString("fetch_cmd");
            String error_info = jsonObject.getString("error_info");
            String result_data = jsonObject.getString("result_data");
            // JSONObject httpDataContentBeanJSON =
            // jsonObject.getJSONObject("result_data");
            // JSONArray httpDataContentBeanJSON =
            // jsonObject.getJSONArray("result_data");
            httpResultBean = new HttpResultBean(login_result, fetch_result, fetch_cmd, error_info, parseHttpDataContentBeanByJSON(result_data));
        } catch (JSONException e) {
            e.printStackTrace();
            jsonOk = false;
        }
        if (!jsonOk)
            return null;
        return httpResultBean;
    }

    public static HttpResultBean parseHttpResultBeanByGsonSingle(String data) {
        boolean jsonOk = true;
        HttpResultBean httpResultBean = null;
        if (data == null || data.isEmpty())
            return null;
        try {
            Gson gson = new Gson();
            httpResultBean = gson.fromJson(data, HttpResultBean.class);
            ;
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
            jsonOk = false;
        }
        if (!jsonOk)
            return null;
        return httpResultBean;
    }

    public static HttpDataContentBean parseHttpDataContentBeanByJSONSingle(String data) {
        boolean jsonOk = true;
        HttpDataContentBean httpDataContentBean = null;
        if (data == null || data.isEmpty())
            return null;
        try {
            JSONObject jsonObject = new JSONObject(data);
            int note_id = jsonObject.getInt("note_id");
            String email = jsonObject.getString("email");
            String note_title = jsonObject.getString("note_title");
            String note_content = jsonObject.getString("note_content");
            String create_date = jsonObject.getString("create_date");
            String modify_date = jsonObject.getString("modify_date");
            httpDataContentBean = new HttpDataContentBean(note_id, email, note_title, note_content, create_date, modify_date);
        } catch (JSONException e) {
            e.printStackTrace();
            jsonOk = false;
        }
        if (!jsonOk)
            return null;
        return httpDataContentBean;
    }

    public static HttpDataContentBean parseHttpDataContentBeanByGsonSingle(String data) {
        boolean jsonOk = true;
        HttpDataContentBean httpDataContentBean = null;
        if (data == null || data.isEmpty())
            return null;
        try {
            Gson gson = new Gson();
            httpDataContentBean = gson.fromJson(data, HttpDataContentBean.class);
            ;
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
            jsonOk = false;
        }
        if (!jsonOk)
            return null;
        return httpDataContentBean;
    }

    public static List<HttpDataContentBean> parseHttpDataContentBeanByJSON(String data) {
        boolean jsonOk = true;
        if (data == null || data.isEmpty())
            return null;
        List<HttpDataContentBean> httpDataContentBeanList = new ArrayList<HttpDataContentBean>();
        try {
            JSONArray jsonArray = new JSONArray(data);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                int note_id = jsonObject.getInt("note_id");
                String email = jsonObject.getString("email");
                String note_title = jsonObject.getString("note_title");
                String note_content = jsonObject.getString("note_content");
                String create_date = jsonObject.getString("create_date");
                String modify_date = jsonObject.getString("modify_date");
                httpDataContentBeanList.add(new HttpDataContentBean(note_id, email, note_title, note_content, create_date, modify_date));
            }
        } catch (JSONException e) {
            e.printStackTrace();
            jsonOk = false;
        }
        if (!jsonOk)
            return null;
        return httpDataContentBeanList;
    }

    public static List<HttpDataContentBean> parseHttpDataContentBeanByGson(String data) {
        List<HttpDataContentBean> httpDataContentBeanList = null;
        boolean jsonOk = true;
        if (data == null || data.isEmpty())
            return null;
        try {
            Gson gson = new Gson();
            Type listType = new TypeToken<List<HttpDataContentBean>>() {
            }.getType();
            httpDataContentBeanList = gson.fromJson(data, listType);
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
            jsonOk = false;
        }
        if (!jsonOk)
            return null;
        return httpDataContentBeanList;
    }
}

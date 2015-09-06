package com.example.android.notepad;

import java.util.ArrayList;
import java.util.List;

import android.os.Parcel;
import android.os.Parcelable;

public class FetchBean implements Parcelable {

    private boolean login_result;

    private boolean fetch_result;
    
    private String fetch_cmd;
    
    private String error_info;
    
    private List<NoteBean> result_data;
    
    public FetchBean() {
        super();
    }
    
    public FetchBean(boolean login_result2, boolean fetch_result2, String cmd, String error,
            List<NoteBean> result_data2) {
        super();
        this.login_result = login_result2;
        this.fetch_result = fetch_result2;
        this.fetch_cmd = cmd;
        this.error_info = error;
        this.result_data = result_data2;
    }

    public boolean getLogin_result() {
        return login_result;
    }
    
    public boolean getFetch_result() {
        return fetch_result;
    }
    
    public String getFetch_cmd() {
        return fetch_cmd;
    }
    
    public String getError_info() {
        return error_info;
    }
    
    public List<NoteBean> getResult_data() {
        return result_data;
    }

    public void setLogin_result(boolean is) {
        this.login_result = is;
    }

    public void setFetch_result(boolean is) {
        this.fetch_result = is;
    }
    
    public void setFetch_cmd(String cmd) {
        this.fetch_cmd = cmd;
    }
    
    public void setError_info(String error) {
        this.error_info = error;
    }
    
    public void setResult_data(List<NoteBean> l) {
        this.result_data = l;
    }

    @Override
    public String toString() {
        return dump();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(result_data);
        dest.writeString(fetch_cmd);
        dest.writeString(error_info);
        dest.writeBooleanArray(new boolean[]{this.login_result, this.fetch_result});
    }

    public static final Parcelable.Creator<FetchBean> CREATOR =
            new Parcelable.Creator<FetchBean>() {
                public FetchBean createFromParcel(Parcel in) {
                    FetchBean notesData = new FetchBean();
                    List<NoteBean> noteBeanList = new ArrayList<NoteBean>();
                    in.readList(noteBeanList, NoteBean.class.getClassLoader());
                    notesData.result_data = noteBeanList;

                    notesData.fetch_cmd = in.readString();
                    notesData.error_info = in.readString();
                    
                    boolean[] booleans = new boolean[2];
                    in.readBooleanArray(booleans);
                    notesData.login_result = booleans[0];
                    notesData.fetch_result = booleans[1];

                    return notesData;
                }

                public FetchBean[] newArray(int size) {
                    return new FetchBean[size];
                }
            };

    public String dump() {
        StringBuilder sb = new StringBuilder();
        sb.append("login_result=").append(login_result).append("\n");
        sb.append("fetch_result=").append(fetch_result).append("\n");
        sb.append("fetch_cmd=").append(fetch_cmd).append("\n");
        sb.append("error_info=").append(error_info).append("\n");
        if (result_data != null) {
            for (NoteBean n : result_data) {
                sb.append(n.dump());
            }
        }
        return sb.toString();
    }
}

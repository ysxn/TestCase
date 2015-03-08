package com.example.testphonenumber;

import java.util.ArrayList;
import java.util.List;

import android.os.Parcel;
import android.os.Parcelable;

public class FetchBean implements Parcelable {

    private boolean login_result;

    private boolean fetch_result;
    
    private List<NoteBean> result_data;
    
    public FetchBean() {
        super();
    }
    
    public FetchBean(boolean login_result2, boolean fetch_result2,
            List<NoteBean> result_data2) {
        super();
        this.login_result = login_result2;
        this.fetch_result = fetch_result2;
        this.result_data = result_data2;
    }

    public boolean getLogin_result() {
        return login_result;
    }
    
    public boolean getFetch_result() {
        return fetch_result;
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
        dest.writeBooleanArray(new boolean[]{this.login_result, this.fetch_result});
    }

    public static final Parcelable.Creator<FetchBean> CREATOR =
            new Parcelable.Creator<FetchBean>() {
                public FetchBean createFromParcel(Parcel in) {
                    FetchBean notesData = new FetchBean();
                    List<NoteBean> noteBeanList = new ArrayList<NoteBean>();
                    in.readList(noteBeanList, NoteBean.class.getClassLoader());
                    notesData.result_data = noteBeanList;

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
        String string = "login_result=" + login_result + "\n" + "fetch_result=" + fetch_result
                + "\n";
        if (result_data != null) {
            for (NoteBean n : result_data) {
                string += n.dump();
            }
        }
        return string;
    }
}

package com.example.testphonenumber;

import android.os.Parcel;
import android.os.Parcelable;

public class NoteBean implements Parcelable {

    private int note_id;

    private String email;

    private String note_title;

    private String note_content;

    private String create_date;

    private String modify_date;

    public NoteBean() {
        super();
    }
    
    public NoteBean(int noteId, String e, String nt, String nc, String cd, String md) {
        super();
        this.note_id = noteId;
        this.email = e;
        this.note_title = nt;
        this.note_content = nc;
        this.create_date = cd;
        this.modify_date = md;
    }

    public int getNote_id() {
        return note_id;
    }

    public String getEmail() {
        return email;
    }

    public String getNote_title() {
        return note_title;
    }

    public String getNote_content() {
        return note_content;
    }

    public String getCreate_date() {
        return create_date;
    }

    public String getModify_date() {
        return modify_date;
    }

    public void setNote_id(int i) {
        this.note_id = i;
    }

    public void setEmail(String s) {
        this.email = s;
    }

    public void setNote_title(String s) {
        this.note_title = s;
    }

    public void setNote_content(String s) {
        this.note_content = s;
    }

    public void setCreate_date(String s) {
        this.create_date = s;
    }

    public void setModify_date(String s) {
        this.modify_date = s;
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
        dest.writeInt(note_id);
        dest.writeString(email);
        dest.writeString(note_title);
        dest.writeString(note_content);
        dest.writeString(create_date);
        dest.writeString(modify_date);
    }

    public static final Parcelable.Creator<NoteBean> CREATOR =
            new Parcelable.Creator<NoteBean>() {
                public NoteBean createFromParcel(Parcel in) {
                    NoteBean notesData = new NoteBean();
                    notesData.note_id = in.readInt();
                    notesData.email = in.readString();
                    notesData.note_title = in.readString();
                    notesData.note_content = in.readString();
                    notesData.create_date = in.readString();
                    notesData.modify_date = in.readString();

                    return notesData;
                }

                public NoteBean[] newArray(int size) {
                    return new NoteBean[size];
                }
            };


    @Override
    public boolean equals(Object o) {
        return o instanceof NoteBean && note_id == (((NoteBean) o).getNote_id());
    }

    @Override
    public int hashCode() {
        return getNote_id();
    }

    public String dump() {
        return "note_id="+note_id+"\n"
                +"email="+email+"\n"
                +"note_title="+note_title+"\n"
                +"note_content="+note_content+"\n"
                +"create_date="+create_date+"\n"
                +"modify_date="+modify_date+"\n";
    }
}


package com.codezyw.common;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * <a href="php.codezyw.com">php.codezyw.com</a>
 */
public class HttpDataContentBean implements Parcelable {

    private int note_id;

    private String email;

    private String note_title;

    private String note_content;

    private String create_date;

    private String modify_date;

    public HttpDataContentBean() {
        super();
    }

    public HttpDataContentBean(int noteId, String e, String nt, String nc, String cd, String md) {
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

    public static final Parcelable.Creator<HttpDataContentBean> CREATOR =
            new Parcelable.Creator<HttpDataContentBean>() {
                public HttpDataContentBean createFromParcel(Parcel in) {
                    HttpDataContentBean notesData = new HttpDataContentBean();
                    notesData.note_id = in.readInt();
                    notesData.email = in.readString();
                    notesData.note_title = in.readString();
                    notesData.note_content = in.readString();
                    notesData.create_date = in.readString();
                    notesData.modify_date = in.readString();

                    return notesData;
                }

                public HttpDataContentBean[] newArray(int size) {
                    return new HttpDataContentBean[size];
                }
            };

    @Override
    public boolean equals(Object o) {
        return o instanceof HttpDataContentBean && note_id == (((HttpDataContentBean) o).getNote_id());
    }

    @Override
    public int hashCode() {
        return getNote_id();
    }

    public String dump() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n");
        sb.append("note_id=").append(note_id).append("\n");
        sb.append("email=").append(email).append("\n");
        sb.append("note_title=").append(note_title).append("\n");
        sb.append("note_content=").append(note_content).append("\n");
        sb.append("create_date=").append(create_date).append("\n");
        sb.append("modify_date=").append(modify_date).append("\n");
        return sb.toString();
    }
}

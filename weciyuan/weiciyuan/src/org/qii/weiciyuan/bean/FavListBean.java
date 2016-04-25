
package org.qii.weiciyuan.bean;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.qii.weiciyuan.support.utils.ObjectToStringUtility;

import android.os.Environment;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

/**
 * User: qii Date: 12-8-18
 */
public class FavListBean extends ListBean<MessageBean, FavListBean> implements Parcelable {
    private List<FavBean> favorites = new ArrayList<FavBean>();
    private List<MessageBean> actualStore = null;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeInt(total_number);
        dest.writeString(previous_cursor);
        dest.writeString(next_cursor);

        dest.writeTypedList(favorites);
        dest.writeTypedList(actualStore);
    }

    public static final Parcelable.Creator<FavListBean> CREATOR =
            new Parcelable.Creator<FavListBean>() {
                public FavListBean createFromParcel(Parcel in) {
                    FavListBean favListBean = new FavListBean();

                    favListBean.total_number = in.readInt();
                    favListBean.previous_cursor = in.readString();
                    favListBean.next_cursor = in.readString();

                    favListBean.favorites = new ArrayList<FavBean>();
                    in.readTypedList(favListBean.favorites, FavBean.CREATOR);

                    favListBean.actualStore = new ArrayList<MessageBean>();
                    in.readTypedList(favListBean.actualStore, MessageBean.CREATOR);

                    return favListBean;
                }

                public FavListBean[] newArray(int size) {
                    return new FavListBean[size];
                }
            };

    public List<FavBean> getFavorites() {
        return favorites;
    }

    public void setFavorites(List<FavBean> favorites) {
        this.favorites = favorites;
    }

    @Override
    public int getSize() {
        return favorites.size();
    }

    @Override
    public MessageBean getItem(int position) {
        return favorites.get(position).getStatus();
    }

    @Override
    public List<MessageBean> getItemList() {
        if (actualStore == null) {
            actualStore = new ArrayList<MessageBean>();
            for (FavBean b : favorites) {
                actualStore.add(b.getStatus());
            }
        }
        return actualStore;
    }

    public void replaceData(FavListBean newValue) {
        if (newValue != null && newValue.getSize() > 0) {

            this.getItemList().clear();
            this.getItemList().addAll(newValue.getItemList());
            this.setTotal_number(newValue.getTotal_number());

            this.favorites.clear();
            List<FavBean> data = newValue.getFavorites();
            if (data != null) {
                for (FavBean b : data) {
                    android.util.Log.i("bean", "" + b.toString());
                    printlns("weibo.txt", b.toString());
                }
            }
            this.favorites.addAll(newValue.getFavorites());
        }
    }

    @Override
    public void addNewData(FavListBean newValue) {
        replaceData(newValue);
    }

    @Override
    public void addOldData(FavListBean oldValue) {
        if (oldValue != null && oldValue.getSize() > 0) {
            getItemList().addAll(oldValue.getItemList());
            setTotal_number(oldValue.getTotal_number());
            List<FavBean> data = oldValue.getFavorites();
            if (data != null) {
                for (FavBean b : data) {
                    android.util.Log.i("bean", "" + b.toString());
                    printlns("weibo.txt", b.toString());
                }
            }
            this.favorites.addAll(oldValue.getFavorites());
        }
    }

    /**
     * 保存日志到sd卡目录下的文件里面
     * 
     * @param filename
     * @param text
     */
    public static void printlns(String filename, String text) {
        try {
            if (Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED)) {
                File f = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + filename);
                // 日志最多50Mb大小
                if (f != null && f.length() > 50 * 1000 * 1000) {
                    f.delete();
                    Log.i("sdb", "f.delete");
                }
                OutputStream out = new FileOutputStream(f, true);
                out.write(text.getBytes());
                out.write('\n');
                out.flush();
                out.close();
            }
        } catch (Exception e) {
        }
    }

    @Override
    public String toString() {
        return ObjectToStringUtility.toString(this);
    }
}

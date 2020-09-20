package com.example.sakura.data.bean;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
//播放页面目录数据
public class ComicInfo implements Parcelable {
    private String num;
    private String numUrl;
    private boolean flag;

    public boolean getFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }
    public void changeFlag(){
        flag = !flag;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getNumUrl() {
        return numUrl;
    }

    public void setNumUrl(String numUrl) {
        this.numUrl = numUrl;
    }

    @NonNull
    @Override
    public String toString() {
        return "num"+num+"$"+
         "numUrl"+numUrl;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.num);
        dest.writeString(this.numUrl);
    }

    public ComicInfo() {
    }

    protected ComicInfo(Parcel in) {
        this.num = in.readString();
        this.numUrl = in.readString();
    }

    public static final Parcelable.Creator<ComicInfo> CREATOR = new Parcelable.Creator<ComicInfo>() {
        @Override
        public ComicInfo createFromParcel(Parcel source) {
            return new ComicInfo(source);
        }

        @Override
        public ComicInfo[] newArray(int size) {
            return new ComicInfo[size];
        }
    };
}

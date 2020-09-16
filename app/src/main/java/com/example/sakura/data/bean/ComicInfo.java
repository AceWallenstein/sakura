package com.example.sakura.data.bean;

import androidx.annotation.NonNull;
//播放页面目录数据
public class ComicInfo  {
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
}

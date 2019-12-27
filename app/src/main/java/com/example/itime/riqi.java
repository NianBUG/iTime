package com.example.itime;

import android.net.Uri;

public class riqi {
    private String title;
    private String endtime;
    private String beizhu;
    private int imageID;
    private String daoshu;
    private Uri uri;
    public riqi(String title, String endtime, String beizhu, int imageID, String daoshu) {
        this.setTitle(title);
        this.setEndtime(endtime);
        this.setBeizhu(beizhu);
        this.setImageID(imageID);
        this.setDaoshu(daoshu);
    }
    public riqi(String title, String endtime, String beizhu, String daoshu, Uri uri) {
        this.setTitle(title);
        this.setEndtime(endtime);
        this.setBeizhu(beizhu);
        this.setDaoshu(daoshu);
        this.setUri(uri);
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getEndtime() {
        return endtime;
    }
    public void setEndtime(String endtime) {
        this.endtime = endtime;
    }
    public String getBeizhu() {
        return beizhu;
    }
    public void setBeizhu(String beizhu) {
        this.beizhu = beizhu;
    }
    public int getImageID() {
        return imageID;
    }
    public void setImageID(int imageID) {
        this.imageID = imageID;
    }
    public String getDaoshu() {
        return daoshu;
    }
    public void setDaoshu(String daoshu) {
        this.daoshu = daoshu;
    }
    public Uri getUri() {
        return uri;
    }
    public void setUri(Uri uri) {
        this.uri = uri;
    }
}

package com.error;

import org.jsoup.Jsoup;

import android.app.Activity;
import android.os.Bundle;

public class Error extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        Jsoup.connect("http://www.baidu.com");
    }
}
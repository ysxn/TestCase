package com.example.testsettingsensorhub;

import com.codezyw.common.DataSource;

public class OrienData extends DataSource {
    
    public OrienData(long l, float f, float g) {
        // TODO Auto-generated constructor stub
        time = l;
        pitch = f;
        roll = g;
    }

    public float pitch = 999;
    public float roll = 999;
}
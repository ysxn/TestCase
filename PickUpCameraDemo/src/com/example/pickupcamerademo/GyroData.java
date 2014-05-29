package com.example.pickupcamerademo;

public class GyroData extends DataSource {
    public GyroData(long l, float f, float g, float h) {
        // TODO Auto-generated constructor stub
        time = l;
        x = f;
        y = g;
        z = h;
    }

    public float x = 999;
    public float y = 999;
    public float z = 999;
}
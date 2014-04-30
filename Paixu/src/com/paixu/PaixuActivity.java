package com.paixu;

import java.util.Arrays;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

public class PaixuActivity extends Activity {
    private String TAG = "PaixuActivity";

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        int tmp;
        int a[] = {2,6,9,4,1,0,7,3};
        //冒泡排序
        for (int j=a.length-1; j>0; j--) {
            for (int i=0; i < j; i++) {
                if (a[i]>a[i+1]) {
                    tmp = a[i];
                    a[i] = a[i+1];
                    a[i+1] = tmp;
                }
            }
        }//从小到大
        Log.i(TAG  ,"i="+Arrays.toString(a));
        for (int j=0; j<a.length-1; j++) {
            for (int i=0; i < a.length-j-1; i++) {
                if (a[i]<a[i+1]) {
                    tmp = a[i];
                    a[i] = a[i+1];
                    a[i+1] = tmp;
                }
            }
        }//从大到小
        Log.i(TAG  ,"i="+Arrays.toString(a));
        for (int j=0; j<a.length-1; j++) {
            for (int i=j+1; i < a.length; i++) {
                if (a[i]<a[j]) {
                    tmp = a[i];
                    a[i] = a[j];
                    a[j] = tmp;
                }
            }
        }//从小到大
        Log.i(TAG  ,"i="+Arrays.toString(a));
        //插入排序
        for (int j=1; j<a.length; j++) {
            for (int i=j; (i>0) && a[i] > a[i-1]; i--) {
                tmp = a[i];
                a[i] = a[i-1];
                a[i-1] = tmp;
            }
        }//从大到小
        Log.i(TAG  ,"i="+Arrays.toString(a));
        
        //递归排序之快速排序
        QuickSort(a,0,a.length);//从小到大
        Log.i(TAG  ,"i="+Arrays.toString(a));
    }

    private int swap;
    private void QuickSort(int[] a, int first, int end) {
        // TODO Auto-generated method stub
        int mid = (first+end)/2;
        SwapData(a,first,mid);
        int scanA = first+1;
        int scanB = end-1;
        while(scanA < scanB) {
            if (a[scanA]>a[first] && a[scanB]<a[first]) {
                SwapData(a, scanA, scanB);
                scanA++;
                scanB--;
            } else if (a[scanA]>=a[first]) {
                scanB--;
            } else if (a[scanB]<=a[first]) {
                scanA++;
            }
        }
        if (a[scanB] <= a[first]) {
            SwapData(a,first,scanB);
            if (first < scanB-1) QuickSort(a,first,scanB);
            if (scanB+1 < end-1) QuickSort(a,scanB+1,end);
        } else {
            SwapData(a,first,scanA-1);
            if (first < scanA-2) QuickSort(a,first,scanA-1);
            if (scanA < end-1) QuickSort(a,scanA,end);
        }
        
    }
    
    private void SwapData(int[] a, int i, int j) {
        // TODO Auto-generated method stub
        swap = a[i];
        a[i] = a[j];
        a[j] = swap;
    }
}
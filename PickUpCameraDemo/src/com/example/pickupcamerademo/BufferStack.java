package com.example.pickupcamerademo;

import java.util.ArrayList;
import java.util.Iterator;

import android.util.Log;

/**
 * 
 * @author zhuyw1
 *
 * @param <E> The element type of this buffer.
 */
public class BufferStack<E> {
    private boolean DEBUG = true;
    private String TAG = "zyw";
    private ArrayList<E> mArrayListSensorAcceDataFront = new ArrayList<E>(100);
    private ArrayList<E> mArrayListSensorAcceDataBack = new ArrayList<E>(100);
    private Object mLockObject = new Object();
    private boolean mLockFlag = false;
    private int mCapacity = 0;
    private int mArrayListCapacity = 0;
    
    /**
     * Constructs a new {@code BufferStack} instance with 100 initial capacity.
     */
    public BufferStack() {
        mCapacity = 100;
        mArrayListCapacity = mCapacity;
        mArrayListSensorAcceDataFront = new ArrayList<E>(100);
        mArrayListSensorAcceDataBack = new ArrayList<E>(100);
    }
    
    /**
     * Constructs a new instance of {@code BufferStack} with the specified
     * initial capacity.
     * @param capacity
     *          the initial capacity of this {@code BufferStack}.
     */
    public BufferStack(int capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("capacity <= 0: " + capacity);
        }
        mCapacity = capacity;
        mArrayListCapacity = mCapacity;
        mArrayListSensorAcceDataFront = new ArrayList<E>(capacity);
        mArrayListSensorAcceDataBack = new ArrayList<E>(capacity);
    }
    
    /**
     * 
     * @param object
     * @return
     */
    public synchronized boolean push(E object) {
        if (mLockFlag) {
            return false;
        }
        if (mArrayListSensorAcceDataFront.size() < mArrayListCapacity) {
            mArrayListSensorAcceDataFront.add(object);
        } else if (mArrayListSensorAcceDataBack.size() < mArrayListCapacity) {
            mArrayListSensorAcceDataBack.add(object);
        } else {
            if (DEBUG) Log.i(TAG, ">>>>>push swap"
                    +", front size="+mArrayListSensorAcceDataFront.size()
                    +", back  size="+mArrayListSensorAcceDataBack.size());
            E objectFront = mArrayListSensorAcceDataFront.get(mArrayListSensorAcceDataFront.size()-1);
            E objectBack = mArrayListSensorAcceDataBack.get(mArrayListSensorAcceDataBack.size()-1);
            DataSource front;
            DataSource back;
            if (objectFront instanceof DataSource && objectBack instanceof DataSource) {
                front = (DataSource) objectFront;
                back = (DataSource) objectBack;
            } else {
                return false;
            }
            if (front == null || back == null) {
                return false;
            }
            if (front.time > back.time) {
                mArrayListSensorAcceDataBack.clear();
                mArrayListSensorAcceDataBack.add(object);
            } else {
                mArrayListSensorAcceDataFront.clear();
                mArrayListSensorAcceDataFront.add(object);
            }
        }
        return true;
    }
    
    /**
     * may be > capacity, because we have double ArrayList
     * @return
     */
    public synchronized int size() {
        return mArrayListSensorAcceDataFront.size() + mArrayListSensorAcceDataBack.size();
    }
    
    /**
     * 
     * @return
     */
    public synchronized boolean lock() {
        mLockFlag = true;
        return true;
    }
    
    /**
     * 
     * @return
     */
    public synchronized boolean unLock() {
        mLockFlag = false;
        return true;
    }
    
    public synchronized boolean dump() {
        if (!mLockFlag) {
            return false;
        }
        ArrayList<E> mArrayListSensorAcceData = new ArrayList<E>(mCapacity * 2);
        Iterator<E> iteratorFront =
                mArrayListSensorAcceDataFront.iterator();
        Iterator<E> iteratorBack =
                mArrayListSensorAcceDataBack.iterator();
        if (mArrayListSensorAcceDataFront.size() > 0
                && mArrayListSensorAcceDataBack.size() <= 0) {
            while (iteratorFront.hasNext()) {
                DataSource data = (DataSource) iteratorFront.next();
                mArrayListSensorAcceData.add(data);
                Log.i("zzzz",
                        ">>>>>>>AcceDataFront time = " + data.time
                                + ", x=" + data.x
                                + ", y=" + data.y
                                + ", z=" + data.z);
            }
        } else if (mArrayListSensorAcceDataFront.size() <= 0
                && mArrayListSensorAcceDataBack.size() > 0) {
            while (iteratorBack.hasNext()) {
                AcceData acceData = (AcceData) iteratorBack.next();
                mArrayListSensorAcceData.add(acceData);
                Log.i("zzzz",
                        ">>>>>>>AcceDataBack  time = " + acceData.time
                                + ", x=" + acceData.x
                                + ", y=" + acceData.y
                                + ", z=" + acceData.z);
            }
        } else if (mArrayListSensorAcceDataFront.size() > 0
                && mArrayListSensorAcceDataBack.size() > 0) {
            if (mArrayListSensorAcceDataFront.get(mArrayListSensorAcceDataFront.size() - 1).time < mArrayListSensorAcceDataBack
                    .get(mArrayListSensorAcceDataBack.size() - 1).time) {
                while (iteratorFront.hasNext()) {
                    AcceData acceData = (AcceData) iteratorFront.next();
                    mArrayListSensorAcceData.add(acceData);
                    Log.i("zzzz",
                            ">>>>>>>AcceDataFront time = " + acceData.time
                                    + ", x=" + acceData.x
                                    + ", y=" + acceData.y
                                    + ", z=" + acceData.z);
                }

                while (iteratorBack.hasNext()) {
                    AcceData acceData = (AcceData) iteratorBack.next();
                    mArrayListSensorAcceData.add(acceData);
                    Log.i("zzzz",
                            ">>>>>>>AcceDataBack  time = " + acceData.time
                                    + ", x=" + acceData.x
                                    + ", y=" + acceData.y
                                    + ", z=" + acceData.z);
                }
            } else {
                while (iteratorBack.hasNext()) {
                    AcceData acceData = (AcceData) iteratorBack.next();
                    mArrayListSensorAcceData.add(acceData);
                    Log.i("zzzz",
                            ">>>>>>>AcceDataBack  time = " + acceData.time
                                    + ", x=" + acceData.x
                                    + ", y=" + acceData.y
                                    + ", z=" + acceData.z);
                }

                while (iteratorFront.hasNext()) {
                    AcceData acceData = (AcceData) iteratorFront.next();
                    mArrayListSensorAcceData.add(acceData);
                    Log.i("zzzz",
                            ">>>>>>>AcceDataFront time = " + acceData.time
                                    + ", x=" + acceData.x
                                    + ", y=" + acceData.y
                                    + ", z=" + acceData.z);
                }
            }
        }
        return true;
    }
}
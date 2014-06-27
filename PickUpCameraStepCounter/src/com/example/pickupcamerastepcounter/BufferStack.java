package com.example.pickupcamerastepcounter;

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
    private String TAG = "zzzz";
    private ArrayList<E> mArrayListDataFront = new ArrayList<E>(100);
    private ArrayList<E> mArrayListDataBack = new ArrayList<E>(100);
    private int mCapacity = 0;
    private int mSize = 0;
    private boolean mCurrentSequenceFront = true;
    
    /**
     * Constructs a new {@code BufferStack} instance with 100 initial capacity.
     */
    public BufferStack() {
        mCapacity = 100;
        mArrayListDataFront = new ArrayList<E>(mCapacity);
        mArrayListDataBack = new ArrayList<E>(mCapacity);
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
        mArrayListDataFront = new ArrayList<E>(capacity);
        mArrayListDataBack = new ArrayList<E>(capacity);
    }
    
    /**
     * 
     * @param object
     * @return
     */
    public synchronized boolean push(E object) {
        if (mArrayListDataFront.size() < mCapacity) {
            mArrayListDataFront.add(object);
            mCurrentSequenceFront = true;
        } else if (mArrayListDataBack.size() < mCapacity) {
            mArrayListDataBack.add(object);
            mCurrentSequenceFront = false;
        } else {
            if (DEBUG) Log.i(TAG, ">>>>>push swap "
                    +", mCurrentSequenceFront="+mCurrentSequenceFront
                    +", front size="+mArrayListDataFront.size()
                    +", back  size="+mArrayListDataBack.size()
                    +"   start");
            E objectFront = mArrayListDataFront.get(mArrayListDataFront.size()-1);
            E objectBack = mArrayListDataBack.get(mArrayListDataBack.size()-1);
            DataSource front;
            DataSource back;
            if (objectFront instanceof DataSource && objectBack instanceof DataSource) {
                front = (DataSource) objectFront;
                back = (DataSource) objectBack;
            } else {
                if (DEBUG) {
                    Log.i(TAG, "push fail, because E type not DataSource");
                }
                return false;
            }
            if (front == null || back == null) {
                if (DEBUG) {
                    Log.i(TAG, "push fail, because NPE");
                }
                return false;
            }
            if (front.time > back.time) {
                mArrayListDataBack.clear();
                mArrayListDataBack.add(object);
                mCurrentSequenceFront = false;
            } else {
                mArrayListDataFront.clear();
                mArrayListDataFront.add(object);
                mCurrentSequenceFront = true;
            }
            if (DEBUG) Log.i(TAG, ">>>>>push swap "
                    +", mCurrentSequenceFront="+mCurrentSequenceFront
                    +", front size="+mArrayListDataFront.size()
                    +", back  size="+mArrayListDataBack.size()
                    +"   end");
        }
        mSize = mArrayListDataFront.size() + mArrayListDataBack.size();
        return true;
    }
    
    /**
     * may be > capacity, because we have double ArrayList
     * @return
     */
    public synchronized int size() {
        mSize = mArrayListDataFront.size() + mArrayListDataBack.size();
        return mSize;
    }
    
    /**
     * may be index > mCapacity, which means index should be fixed to double sequence.
     * @param index
     * @return
     */
    public synchronized E get(int index) {
        mSize = mArrayListDataFront.size() + mArrayListDataBack.size();
        if (index >= mSize || index < 0) {
            throwIndexOutOfBoundsException(index, mSize);
        }
        if (mSize > mCapacity) {
            //In this case there are two data sequence
            if (index >= mCapacity) {
                //index is in current sequence
                index = index - mCapacity;
                return (E) (mCurrentSequenceFront ? mArrayListDataFront.get(index) : mArrayListDataBack.get(index));
            } else {
                //index is in old sequence
                return (E) (mCurrentSequenceFront ? mArrayListDataBack.get(index) : mArrayListDataFront.get(index));
            }
        } else {
            //In this case there is only one data sequence
            return (E) (mCurrentSequenceFront ? mArrayListDataFront.get(index) : mArrayListDataBack.get(index));
        }
    }
    
    /**
     * 
     * @param object
     * @return
     */
    public synchronized int indexOf(Object object) {
        int index = -1;
        mSize = mArrayListDataFront.size() + mArrayListDataBack.size();
        if (mSize > mCapacity) {
            //In this case there are two data sequence
            if (mCurrentSequenceFront) {
                //first search old sequence
                index = mArrayListDataBack.indexOf(object);
                if (index == -1) {
                    //not found in old sequence
                    //try search current sequence
                    index = mArrayListDataFront.indexOf(object);
                    if (index >= 0) {
                        index = index + mCapacity;
                    }
                }
            } else {
              //first search old sequence
                index = mArrayListDataFront.indexOf(object);
                if (index == -1) {
                    //not found in old sequence
                    //try search current sequence
                    index = mArrayListDataBack.indexOf(object);
                    if (index >= 0) {
                        index = index + mCapacity;
                    }
                }
            }
        } else {
            //In this case there is only one data sequence
            if (mCurrentSequenceFront) {
                index = mArrayListDataFront.indexOf(object);
            } else {
                index = mArrayListDataBack.indexOf(object);
            }
        }
        return index;
    }
    
    /**
     * 
     * @return
     */
    public synchronized boolean isEmpty() {
        return mSize == 0;
    }
    
    public synchronized void clear() {
        mArrayListDataBack.clear();
        mArrayListDataFront.clear();
        mSize = 0;
        mCurrentSequenceFront = true;
    }
    
    /**
     * This method was extracted to encourage VM to inline callers.
     * TODO: when we have a VM that can actually inline, move the test in here too!
     */
    static IndexOutOfBoundsException throwIndexOutOfBoundsException(int index, int size) {
        throw new IndexOutOfBoundsException("Invalid index " + index + ", size is " + size);
    }
    
    /**
     * 
     * @return
     */
    public synchronized ArrayList<E> dump() {
        ArrayList<E> mArrayListData = new ArrayList<E>(mCapacity * 2);
        Iterator<E> iteratorFront =
                mArrayListDataFront.iterator();
        Iterator<E> iteratorBack =
                mArrayListDataBack.iterator();
        if (mArrayListDataFront.size() > 0
                && mArrayListDataBack.size() <= 0) {
            while (iteratorFront.hasNext()) {
                E data = (E) iteratorFront.next();
                mArrayListData.add(data);
            }
        } else if (mArrayListDataFront.size() <= 0
                && mArrayListDataBack.size() > 0) {
            while (iteratorBack.hasNext()) {
                E data = (E) iteratorBack.next();
                mArrayListData.add(data);
            }
        } else if (mArrayListDataFront.size() > 0
                && mArrayListDataBack.size() > 0) {
            if (!mCurrentSequenceFront) {
                while (iteratorFront.hasNext()) {
                    E acceData = (E) iteratorFront.next();
                    mArrayListData.add(acceData);
                }

                while (iteratorBack.hasNext()) {
                    E acceData = (E) iteratorBack.next();
                    mArrayListData.add(acceData);
                }
            } else {
                while (iteratorBack.hasNext()) {
                    E acceData = (E) iteratorBack.next();
                    mArrayListData.add(acceData);
                }

                while (iteratorFront.hasNext()) {
                    E acceData = (E) iteratorFront.next();
                    mArrayListData.add(acceData);
                }
            }
        }
        
        
        return mArrayListData;
    }
}



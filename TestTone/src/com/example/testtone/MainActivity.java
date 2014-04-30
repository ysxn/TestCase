
package com.example.testtone;

import com.example.testtone.R;

import junit.framework.Test;
import android.app.Activity;
import android.media.AudioManager;
import android.media.SoundPool;
import android.media.ToneGenerator;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
    private static final String TAG = "zhuyawen";
    private static boolean DEBUG = true;

    /** The DTMF tone volume relative to other sounds in the stream */
    private static final int TONE_RELATIVE_VOLUME = 80;

    /**
     * Stream type used to play the DTMF tones off call, and mapped to the
     * volume control keys
     */
    private static final int DIAL_TONE_STREAM_TYPE = AudioManager.STREAM_DTMF;

    /** The length of DTMF tones in milliseconds */
    private static final int TONE_LENGTH_MS = 150;
    private static final int TONE_LENGTH_INFINITE = -1;

    private ToneGenerator mToneGeneratorTypeDTMF;
    private ToneGenerator mToneGeneratorTypeRing;
    private ToneGenerator mToneGeneratorTypeMusic;
    private final Object mToneGeneratorLock = new Object();
    private final Object mPoolLock = new Object();

    private Button mButtonRing;
    private Button mButtonDTMF;
    private Button mButtonMusic;

    private Button mButtonSoundPoolRing;
    private Button mButtonSoundPoolDTMF;
    private Button mButtonSoundPoolMusic;

    private Button mButtonSetVolumeControlStream;

    private TextView mCurrentVolumeControlStream;

    private boolean mFlagControlMusic = true;

    private static final int[] mTestFiles = new int[] {
            //R.raw.organ441,
            //R.raw.sine441,
            //R.raw.test1,
            R.raw.test2,
            //R.raw.test3,
            //R.raw.test4,
            //R.raw.test5
    };

    private final static float SEMITONE = 1.059463094f;
    private final static float DEFAULT_VOLUME = 0.707f;
    private final static float MAX_VOLUME = 1.0f;
    private final static float MIN_VOLUME = 0.01f;
    private final static int LOW_PRIORITY = 1000;
    private final static int NORMAL_PRIORITY = 2000;
    private final static int HIGH_PRIORITY = 3000;
    private final static int DEFAULT_LOOP = 0;
    private final static int DEFAULT_SRC_QUALITY = 0;
    private final static double PI_OVER_2 = Math.PI / 2.0;
    private SoundPool mSoundPool = null;

    private int mLastSample;
    private int mCurrentOutStreamType = -1;
    private int mLoadStatus;
    private boolean mLoadAllStatus = false;
    private int[] mSounds;
    private float mScale[];
    private int mIndexPool = 0;

    /**
     * Called when a sound has completed loading.
     * 
     * @param soundPool SoundPool object from the load() method
     * @param soundPool the sample ID of the sound loaded.
     * @param status the status of the load operation (0 = success)
     */
    private final class LoadCompleteCallback implements
            android.media.SoundPool.OnLoadCompleteListener {
        public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
            synchronized (mPoolLock) {
                if (DEBUG)
                    Log.d(TAG, "Sample " + sampleId + " load status = " + status + ", ThreadID="
                            + Thread.currentThread().getId());
                if (status != 0) {
                    mLoadStatus = status;
                }
                if (sampleId == mLastSample) {
                    mPoolLock.notify();
                    mLoadAllStatus = true;
                }
            }
        }
    }

    private int loadSound(int resId, int priority) {
        int id = mSoundPool.load(getApplicationContext(), resId, priority);
        if (id == 0) {
            Log.e(TAG, "Unable to open resource");
        }
        return id;
    }

    private int initSoundPool(int currentOutStreamType) throws java.lang.InterruptedException {

        Log.i(TAG, ">>>>>>>initSoundPool currentOutStreamType = " + currentOutStreamType
                + " , mCurrentOutStreamType = " + mCurrentOutStreamType);
        if (mSoundPool != null) {
            // if ((mMaxStreams == numStreams) && (mLoadStatus == 0)) return
            // mLoadStatus;
            if (mCurrentOutStreamType != currentOutStreamType) {
                mSoundPool.release();
                mSoundPool = null;
            } else {
                return mLoadStatus;
            }
        }

        // create sound pool
        mIndexPool = 0;
        mLoadStatus = 0;
        mLoadAllStatus = false;
        mCurrentOutStreamType = currentOutStreamType;
        mSoundPool = new SoundPool(7, mCurrentOutStreamType, 0);
        mSoundPool.setOnLoadCompleteListener(new LoadCompleteCallback());
        int numSounds = mTestFiles.length;
        mSounds = new int[numSounds];

        // load sounds
        // synchronized(mSoundPool) {
        for (int index = 0; index < numSounds; index++) {
            mSounds[index] = loadSound(mTestFiles[index], NORMAL_PRIORITY);
            mLastSample = mSounds[index];
        }
        // mSoundPool.wait();
        // }
        Log.i(TAG, ">>>>>>>initSoundPool numSounds = " + numSounds + " , mLastSample = "
                + mLastSample + ", ThreadID="
                + Thread.currentThread().getId());
        Thread.sleep(450);
        return mLoadStatus;
    }

    private boolean TestSounds() throws java.lang.InterruptedException {
        if (DEBUG)
            Log.d(TAG, "Begin sounds test");
        int count = mSounds.length;
        if (mIndexPool >= count) {
            mIndexPool = 0;
        }
        Log.i(TAG, ">>>>>>>TestSounds mIndexPool = " + mIndexPool);
        // for (int index = 0; index < count; index++) {
        int id = mSoundPool.play(mSounds[mIndexPool], DEFAULT_VOLUME, DEFAULT_VOLUME,
                NORMAL_PRIORITY, DEFAULT_LOOP, 1.0f);
        if (DEBUG)
            Log.d(TAG, "Start note " + id);
        mIndexPool++;
        if (id == 0) {
            Log.e(TAG, "Error occurred starting note");
            return false;
        }
        Thread.sleep(450);
        mSoundPool.stop(id);
        if (DEBUG)
            Log.d(TAG, "Stop note " + id);
        Thread.sleep(50);
        // }
        if (DEBUG)
            Log.d(TAG, "End sounds test");
        return true;
    }

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        // if the mToneGenerator creation fails, just continue without it. It is
        // a local audio signal, and is not as important as the dtmf tone
        // itself.
        synchronized (mToneGeneratorLock) {
            if (mToneGeneratorTypeDTMF == null) {
                try {
                    mToneGeneratorTypeDTMF = new ToneGenerator(DIAL_TONE_STREAM_TYPE,
                            TONE_RELATIVE_VOLUME);
                } catch (RuntimeException e) {
                    Log.w(TAG, "Exception caught while creating local tone generator: " + e);
                    mToneGeneratorTypeDTMF = null;
                }
            }

            if (mToneGeneratorTypeRing == null) {
                try {
                    mToneGeneratorTypeRing = new ToneGenerator(AudioManager.STREAM_RING,
                            TONE_RELATIVE_VOLUME);
                } catch (RuntimeException e) {
                    Log.w(TAG, "Exception caught while creating local tone generator: " + e);
                    mToneGeneratorTypeRing = null;
                }
            }

            if (mToneGeneratorTypeMusic == null) {
                try {
                    mToneGeneratorTypeMusic = new ToneGenerator(AudioManager.STREAM_MUSIC,
                            TONE_RELATIVE_VOLUME);
                } catch (RuntimeException e) {
                    Log.w(TAG, "Exception caught while creating local tone generator: " + e);
                    mToneGeneratorTypeMusic = null;
                }
            }
        }

        mCurrentVolumeControlStream = (TextView) findViewById(R.id.textview_currentVolumeControlStream);

        if (mCurrentVolumeControlStream != null) {
            setVolumeControlStream(AudioManager.STREAM_RING);
            mFlagControlMusic = false;
            mCurrentVolumeControlStream
                    .setText("CurrentVolumeControl-STREAM_RING");
            int currentS = getVolumeControlStream();
            Log.i(TAG, ">>>> onCreate mCurrentVolumeControlStream = " + currentS);
            switch (currentS) {
                case AudioManager.STREAM_MUSIC:
                    mCurrentVolumeControlStream.setText("CurrentVolumeControl-STREAM_MUSIC");
                    break;
                case AudioManager.STREAM_ALARM:
                    mCurrentVolumeControlStream.setText("CurrentVolumeControl-STREAM_ALARM");
                    break;
                case AudioManager.STREAM_DTMF:
                    mCurrentVolumeControlStream.setText("CurrentVolumeControl-STREAM_DTMF");
                    break;
                case AudioManager.STREAM_NOTIFICATION:
                    mCurrentVolumeControlStream.setText("CurrentVolumeControl-STREAM_NOTIFICATION");
                    break;
                case AudioManager.STREAM_RING:
                    mCurrentVolumeControlStream.setText("CurrentVolumeControl-STREAM_RING");
                    break;
                case AudioManager.STREAM_SYSTEM:
                    mCurrentVolumeControlStream.setText("CurrentVolumeControl-STREAM_SYSTEM");
                    break;
                case AudioManager.STREAM_VOICE_CALL:
                    mCurrentVolumeControlStream.setText("CurrentVolumeControl-STREAM_VOICE_CALL");
                    break;
            }
        }

        mButtonRing = (Button) findViewById(R.id.action_button_ring);
        mButtonDTMF = (Button) findViewById(R.id.action_button_dtmf);
        mButtonMusic = (Button) findViewById(R.id.action_button_music);

        mButtonSoundPoolRing = (Button) findViewById(R.id.action_button_ring2);
        mButtonSoundPoolDTMF = (Button) findViewById(R.id.action_button_dtmf2);
        mButtonSoundPoolMusic = (Button) findViewById(R.id.action_button_music2);

        if (mButtonSoundPoolRing != null) {
            mButtonSoundPoolRing.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    synchronized (mToneGeneratorLock) {
                        stopTone();
                        try {
                            initSoundPool(AudioManager.STREAM_RING);
                            new Thread() {
                                @Override
                                public void run() {
                                    try {
                                        synchronized(mPoolLock) {
                                            if (!mLoadAllStatus) mPoolLock.wait();
                                            TestSounds();
                                        }
                                        
                                    } catch (InterruptedException e) {
                                        // TODO Auto-generated catch block
                                        e.printStackTrace();
                                    }
                                }
                            }.start();
                        } catch (InterruptedException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                }
            });
        }

        if (mButtonSoundPoolDTMF != null) {
            mButtonSoundPoolDTMF.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    synchronized (mToneGeneratorLock) {
                        stopTone();
                        try {
                            initSoundPool(AudioManager.STREAM_DTMF);
                            new Thread() {
                                @Override
                                public void run() {
                                    try {
                                        synchronized(mPoolLock) {
                                            if (!mLoadAllStatus) mPoolLock.wait();
                                            TestSounds();
                                        }
                                        
                                    } catch (InterruptedException e) {
                                        // TODO Auto-generated catch block
                                        e.printStackTrace();
                                    }
                                }
                            }.start();
                        } catch (InterruptedException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                }
            });
        }

        if (mButtonSoundPoolMusic != null) {
            mButtonSoundPoolMusic.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    synchronized (mToneGeneratorLock) {
                        stopTone();
                        try {
                            initSoundPool(AudioManager.STREAM_MUSIC);
                            new Thread() {
                                @Override
                                public void run() {
                                    try {
                                        synchronized(mPoolLock) {
                                            if (!mLoadAllStatus) mPoolLock.wait();
                                            TestSounds();
                                        }
                                        
                                    } catch (InterruptedException e) {
                                        // TODO Auto-generated catch block
                                        e.printStackTrace();
                                    }
                                }
                            }.start();
                        } catch (InterruptedException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                }
            });
        }

        mButtonSetVolumeControlStream = (Button) findViewById(R.id.action_button_setVolumeControlStream);

        if (mButtonSetVolumeControlStream != null) {
            mButtonSetVolumeControlStream.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    synchronized (mToneGeneratorLock) {
                        stopTone();
                        if (/*
                             * getVolumeControlStream() ==
                             * AudioManager.STREAM_MUSIC
                             */mFlagControlMusic) {
                            setVolumeControlStream(AudioManager.STREAM_RING);
                            mFlagControlMusic = false;
                            mCurrentVolumeControlStream
                                    .setText("CurrentVolumeControl-STREAM_RING");
                        } else if (/*
                                    * getVolumeControlStream() ==
                                    * AudioManager.STREAM_RING
                                    */!mFlagControlMusic) {
                            setVolumeControlStream(AudioManager.STREAM_MUSIC);
                            mFlagControlMusic = true;
                            mCurrentVolumeControlStream
                                    .setText("CurrentVolumeControl-STREAM_MUSIC");
                        }
                        if (mCurrentVolumeControlStream != null) {
                            int currentS = getVolumeControlStream();
                            Log.i(TAG, ">>>>onClick  mCurrentVolumeControlStream = " + currentS);
                            switch (currentS) {
                                case AudioManager.STREAM_MUSIC:
                                    mCurrentVolumeControlStream
                                            .setText("CurrentVolumeControl-STREAM_MUSIC");
                                    break;
                                case AudioManager.STREAM_ALARM:
                                    mCurrentVolumeControlStream
                                            .setText("CurrentVolumeControl-STREAM_ALARM");
                                    break;
                                case AudioManager.STREAM_DTMF:
                                    mCurrentVolumeControlStream
                                            .setText("CurrentVolumeControl-STREAM_DTMF");
                                    break;
                                case AudioManager.STREAM_NOTIFICATION:
                                    mCurrentVolumeControlStream
                                            .setText("CurrentVolumeControl-STREAM_NOTIFICATION");
                                    break;
                                case AudioManager.STREAM_RING:
                                    mCurrentVolumeControlStream
                                            .setText("CurrentVolumeControl-STREAM_RING");
                                    break;
                                case AudioManager.STREAM_SYSTEM:
                                    mCurrentVolumeControlStream
                                            .setText("CurrentVolumeControl-STREAM_SYSTEM");
                                    break;
                                case AudioManager.STREAM_VOICE_CALL:
                                    mCurrentVolumeControlStream
                                            .setText("CurrentVolumeControl-STREAM_VOICE_CALL");
                                    break;
                            }
                        }
                    }
                }
            });
        }

        if (mButtonRing != null) {
            mButtonRing.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    synchronized (mToneGeneratorLock) {
                        if (mToneGeneratorTypeRing == null) {
                            Log.w(TAG, "playTone: mToneGeneratorTypeRing == null, tone: ");
                            return;
                        }
                        stopTone();

                        // Start the new tone (will stop any playing tone)
                        mToneGeneratorTypeRing.startTone(ToneGenerator.TONE_DTMF_1, TONE_LENGTH_MS);
                    }
                }
            });
        }

        if (mButtonDTMF != null) {
            mButtonDTMF.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    synchronized (mToneGeneratorLock) {
                        if (mToneGeneratorTypeDTMF == null) {
                            Log.w(TAG, "playTone: mToneGeneratorTypeDTMF == null, tone: ");
                            return;
                        }
                        stopTone();

                        // Start the new tone (will stop any playing tone)
                        mToneGeneratorTypeDTMF.startTone(ToneGenerator.TONE_DTMF_1, TONE_LENGTH_MS);
                    }
                }
            });
        }

        if (mButtonMusic != null) {
            mButtonMusic.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    synchronized (mToneGeneratorLock) {
                        if (mToneGeneratorTypeMusic == null) {
                            Log.w(TAG, "playTone: mToneGeneratorTypeMusic == null, tone: ");
                            return;
                        }
                        stopTone();

                        // Start the new tone (will stop any playing tone)
                        mToneGeneratorTypeMusic
                                .startTone(ToneGenerator.TONE_DTMF_1, TONE_LENGTH_MS);
                    }
                }
            });
        }
    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        synchronized (mToneGeneratorLock) {
            if (mToneGeneratorTypeDTMF != null) {
                mToneGeneratorTypeDTMF.release();
                mToneGeneratorTypeDTMF = null;
            }

            if (mToneGeneratorTypeRing != null) {
                mToneGeneratorTypeRing.release();
                mToneGeneratorTypeRing = null;
            }

            if (mToneGeneratorTypeMusic != null) {
                mToneGeneratorTypeMusic.release();
                mToneGeneratorTypeMusic = null;
            }
        }

        // release sound pool
        if (mSoundPool != null) {
            mSoundPool.release();
            mSoundPool = null;
        }
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
    }

    /**
     * Stop the tone if it is played.
     */
    private void stopTone() {
        synchronized (mToneGeneratorLock) {
            if (mToneGeneratorTypeDTMF == null) {
                Log.w(TAG, "stopTone: mToneGeneratorTypeDTMF == null");
            } else {
                mToneGeneratorTypeDTMF.stopTone();
            }

            if (mToneGeneratorTypeRing == null) {
                Log.w(TAG, "stopTone: mToneGeneratorTypeRing == null");
            } else {
                mToneGeneratorTypeRing.stopTone();
            }

            if (mToneGeneratorTypeMusic == null) {
                Log.w(TAG, "stopTone: mToneGeneratorTypeMusic == null");
            } else {
                mToneGeneratorTypeMusic.stopTone();
            }
        }
    }

}


package com.codezyw.common;

import static android.media.AudioManager.ADJUST_LOWER;
import static android.media.AudioManager.ADJUST_RAISE;
import static android.media.AudioManager.ADJUST_SAME;
import static android.media.AudioManager.RINGER_MODE_NORMAL;
import static android.media.AudioManager.STREAM_MUSIC;
import static android.media.AudioManager.USE_DEFAULT_STREAM_TYPE;

import java.io.File;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.media.AudioManager;
import android.media.CamcorderProfile;
import android.media.MediaPlayer;
import android.media.MediaScannerConnection;
import android.media.MediaScannerConnection.MediaScannerConnectionClient;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Log;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class MediaHelper {
    private static final String TAG = "camera";
    public final static int FILECHOOSER_RESULTCODE = 1;
    public final static String IMAGE_MIME_TYPE = "image/*";
    public final static String VIDEO_MIME_TYPE = "video/*";
    public final static String AUDIO_MIME_TYPE = "audio/*";

    public static final String EXTRAS_CAMERA_FACING = "android.intent.extras.CAMERA_FACING";
    public static final int EXTRAS_CAMERA_FACING_BACK = 0;
    public static final int EXTRAS_CAMERA_FACING_FRONT = 1;

    /**
     * 获取camera录像配置
     * 
     * @param cameraId 为0表示后置摄像头，为1表示前置摄像头
     * @param quality 录像质量 quality >= 0 && quality <= 23
     */
    public static CamcorderProfile getMediaProfile(int cameraId, int quality) {
        boolean has = CamcorderProfile.hasProfile(cameraId, quality);
        CamcorderProfile c = null;
        StringBuilder sb = new StringBuilder();
        if (cameraId == 0) {
            sb.append("check hasProfile(后置摄像头, 质量为").append(quality)
                    .append(")==").append(has);
        } else if (cameraId == 1) {
            sb.append("check hasProfile(前置摄像头, 质量为").append(quality)
                    .append(")==").append(has);
        } else {
            sb.append("check hasProfile(摄像头id为").append(cameraId)
                    .append(", 质量为").append(quality)
                    .append(")==").append(has);
        }

        if (has) {
            try {
                c = CamcorderProfile.get(cameraId, quality);
                sb.append("\n");
                sb.append("duration=").append(c.duration);
                sb.append("\n");
                sb.append("quality=").append(c.quality);
                sb.append("\n");
                sb.append("fileFormat=").append(c.fileFormat);
                sb.append("\n");
                sb.append("videoCodec=").append(c.videoCodec);
                sb.append("\n");
                sb.append("videoBitRate=").append(c.videoBitRate);
                sb.append("\n");
                sb.append("videoFrameRate=").append(c.videoFrameRate);
                sb.append("\n");
                sb.append("videoFrameWidth=").append(c.videoFrameWidth);
                sb.append("\n");
                sb.append("videoFrameHeight=").append(c.videoFrameHeight);
                sb.append("\n");
                sb.append("audioCodec=").append(c.audioCodec);
                sb.append("\n");
                sb.append("audioBitRate=").append(c.audioBitRate);
                sb.append("\n");
                sb.append("audioSampleRate=").append(c.audioSampleRate);
                sb.append("\n");
                sb.append("audioChannels=").append(c.audioChannels);
            } catch (Exception e) {
                e.printStackTrace();
                sb.append(" Unsupported quality level ").append(quality);
            }
        }
        Log.i(TAG, sb.toString());
        return c;
    }

    /**
     * 浏览器选择文件
     */
    public static void getChooserOpenIntent(Activity activity) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("*/*");
        activity.startActivityForResult(Intent.createChooser(intent, "File Chooser"),
                FILECHOOSER_RESULTCODE);
    }

    /**
     * 浏览器选择图片
     */
    public static void getChooserImageIntent(Activity activity) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        Uri mCapturedMedia = createTempFileContentUri(".jpg");
        intent.putExtra(MediaStore.EXTRA_OUTPUT, mCapturedMedia);
        intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION |
                Intent.FLAG_GRANT_READ_URI_PERMISSION);
        activity.startActivityForResult(intent, FILECHOOSER_RESULTCODE);
    }

    /**
     * 浏览器选择视频
     */
    public static void getChooserVideoIntent(Activity activity) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        activity.startActivityForResult(intent, FILECHOOSER_RESULTCODE);
    }

    /**
     * 浏览器选择录音
     */
    public static void getChooserSoundIntent(Activity activity) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent = new Intent(MediaStore.Audio.Media.RECORD_SOUND_ACTION);
        activity.startActivityForResult(intent, FILECHOOSER_RESULTCODE);
    }

    @SuppressLint("SdCardPath")
    public static Uri createTempFileContentUri(String suffix) {
        try {
            File mediaPath = new File(Environment.getExternalStorageDirectory(), "captured_media");
            if (!mediaPath.exists() && !mediaPath.mkdir()) {
                mediaPath = new File("/sdcard", "captured_media");
                if (!mediaPath.exists() && !mediaPath.mkdir()) {
                    return null;
                }
            }
            File mediaFile = File.createTempFile(
                    String.valueOf(System.currentTimeMillis()), suffix, mediaPath);
            return Uri.fromFile(mediaFile);
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 调节音量测试CTS
     * 
     * @param context
     * @throws Exception
     */
    public void testVolume(Context context) throws Exception {
        final int MP3_TO_PLAY = 0;// R.raw.testmp3;
        final long TIME_TO_PLAY = 2000;
        AudioManager mAudioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        int[] streams = {
                AudioManager.STREAM_ALARM,
                AudioManager.STREAM_MUSIC,
                AudioManager.STREAM_VOICE_CALL,
                AudioManager.STREAM_RING
        };

        mAudioManager.adjustVolume(ADJUST_RAISE, 0);
        mAudioManager.adjustSuggestedStreamVolume(
                ADJUST_LOWER, USE_DEFAULT_STREAM_TYPE, 0);
        int maxMusicVolume = mAudioManager.getStreamMaxVolume(STREAM_MUSIC);
        Log.i(TAG, ">>>>>>mAudioManager.getStreamMaxVolume(STREAM_MUSIC)=" + maxMusicVolume);

        for (int i = 0; i < streams.length; i++) {
            if (streams[i] == AudioManager.STREAM_ALARM) {
                Log.i(TAG, ">>>>>>start test STREAM_ALARM");
            } else if (streams[i] == AudioManager.STREAM_MUSIC) {
                Log.i(TAG, ">>>>>>start test STREAM_MUSIC");
            } else if (streams[i] == AudioManager.STREAM_VOICE_CALL) {
                Log.i(TAG, ">>>>>>start test STREAM_VOICE_CALL");
            } else if (streams[i] == AudioManager.STREAM_RING) {
                Log.i(TAG, ">>>>>>start test STREAM_RING");
            }
            // set ringer mode to back normal to not interfere with volume tests
            mAudioManager.setRingerMode(RINGER_MODE_NORMAL);

            int maxVolume = mAudioManager.getStreamMaxVolume(streams[i]);
            Log.i(TAG, ">>>>>>mAudioManager.getStreamMaxVolume=" + maxVolume);

            mAudioManager.setStreamVolume(streams[i], 1, 0);
            assertEquals(1, mAudioManager.getStreamVolume(streams[i]));

            if (streams[i] == AudioManager.STREAM_MUSIC && mAudioManager.isWiredHeadsetOn()) {
                // due to new regulations, music sent over a wired headset may
                // be volume limited
                // until the user explicitly increases the limit, so we can't
                // rely on being able
                // to set the volume to getStreamMaxVolume(). Instead, determine
                // the current limit
                // by increasing the volume until it won't go any higher, then
                // use that volume as
                // the maximum for the purposes of this test
                Log.i(TAG, ">>>>>>isWiredHeadsetOn");
                int curvol = 0;
                int prevvol = 0;
                do {
                    prevvol = curvol;
                    mAudioManager.adjustStreamVolume(streams[i], ADJUST_RAISE, 0);
                    curvol = mAudioManager.getStreamVolume(streams[i]);
                } while (curvol != prevvol);
                maxVolume = maxMusicVolume = curvol;
            }
            mAudioManager.setStreamVolume(streams[i], maxVolume, 0);
            mAudioManager.adjustStreamVolume(streams[i], ADJUST_RAISE, 0);
            assertEquals(maxVolume, mAudioManager.getStreamVolume(streams[i]));

            mAudioManager.adjustSuggestedStreamVolume(ADJUST_LOWER, streams[i], 0);
            assertEquals(maxVolume - 1, mAudioManager.getStreamVolume(streams[i]));

            // volume lower
            mAudioManager.setStreamVolume(streams[i], maxVolume, 0);
            for (int k = maxVolume; k > 0; k--) {
                mAudioManager.adjustStreamVolume(streams[i], ADJUST_LOWER, 0);
                assertEquals(k - 1, mAudioManager.getStreamVolume(streams[i]));
            }

            mAudioManager.adjustStreamVolume(streams[i], ADJUST_SAME, 0);
            // volume raise
            mAudioManager.setStreamVolume(streams[i], 1, 0);
            for (int k = 1; k < maxVolume; k++) {
                mAudioManager.adjustStreamVolume(streams[i], ADJUST_RAISE, 0);
                assertEquals(1 + k, mAudioManager.getStreamVolume(streams[i]));
            }

            // volume same
            mAudioManager.setStreamVolume(streams[i], maxVolume, 0);
            for (int k = 0; k < maxVolume; k++) {
                mAudioManager.adjustStreamVolume(streams[i], ADJUST_SAME, 0);
                assertEquals(maxVolume, mAudioManager.getStreamVolume(streams[i]));
            }

            mAudioManager.setStreamVolume(streams[i], maxVolume, 0);
        }

        // adjust volume
        mAudioManager.adjustVolume(ADJUST_RAISE, 0);

        MediaPlayer mp = MediaPlayer.create(context, MP3_TO_PLAY);
        mp.setAudioStreamType(STREAM_MUSIC);
        mp.setLooping(true);
        mp.start();
        Thread.sleep(TIME_TO_PLAY);
        assertTrue(mAudioManager.isMusicActive());

        // adjust volume as ADJUST_SAME
        for (int k = 0; k < maxMusicVolume; k++) {
            mAudioManager.adjustVolume(ADJUST_SAME, 0);
            assertEquals(maxMusicVolume, mAudioManager.getStreamVolume(STREAM_MUSIC));
        }

        // adjust volume as ADJUST_RAISE
        mAudioManager.setStreamVolume(STREAM_MUSIC, 1, 0);
        for (int k = 0; k < maxMusicVolume - 1; k++) {
            mAudioManager.adjustVolume(ADJUST_RAISE, 0);
            assertEquals(2 + k, mAudioManager.getStreamVolume(STREAM_MUSIC));
        }

        // adjust volume as ADJUST_LOWER
        mAudioManager.setStreamVolume(STREAM_MUSIC, maxMusicVolume, 0);
        maxMusicVolume = mAudioManager.getStreamVolume(STREAM_MUSIC);

        mAudioManager.adjustVolume(ADJUST_LOWER, 0);
        assertEquals(maxMusicVolume - 1, mAudioManager.getStreamVolume(STREAM_MUSIC));
        mp.stop();
        mp.release();
        Thread.sleep(TIME_TO_PLAY);
        assertFalse(mAudioManager.isMusicActive());
    }

    private void assertEquals(int volume1, int volume2) throws Exception {
        if (volume1 == volume2) {
            Log.i(TAG, ">>>>>>assertEquals pass! volume1=" + volume1 + ", volume2=" + volume2);
        } else {
            Log.i(TAG, ">>>>>>assertEquals fail! volume1=" + volume1 + ", volume2=" + volume2);
            Thread.dumpStack();
            throw new Exception(">>>>>>assertEquals fail! volume1=" + volume1 + ", volume2=" + volume2);
        }
    }

    private void assertFalse(boolean result) throws Exception {
        if (!result) {
            Log.i(TAG, ">>>>>>assertFalse pass! result=" + result);
        } else {
            Log.i(TAG, ">>>>>>assertFalse fail! result=" + result);
            Thread.dumpStack();
            throw new Exception(">>>>>>assertFalse fail! result=" + result);
        }
    }

    private void assertTrue(boolean result) throws Exception {
        if (result) {
            Log.i(TAG, ">>>>>>assertTrue pass! result=" + result);
        } else {
            Log.i(TAG, ">>>>>>assertTrue fail! result=" + result);
            Thread.dumpStack();
            throw new Exception(">>>>>>assertTrue fail! result=" + result);
        }
    }

    /**
     * 获取系统音量
     * 
     * @param context
     * @param streamType
     * @return
     */
    public static int getAudioVolume(Context context, int streamType) {
        if (streamType < 0) {
            streamType = AudioManager.STREAM_RING;
        }
        AudioManager mAudioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        int i = mAudioManager.getStreamVolume(streamType);
        return i;
    }

    /**
     * 锁屏情况下启动摄像头拍照
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    public static void launchSecureCapture(Context context) {
        Intent intent = new Intent(MediaStore.INTENT_ACTION_STILL_IMAGE_CAMERA_SECURE);
        intent.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    /**
     * 启动后摄像头拍照
     * 
     * @param context
     */
    public static void launchBackCamera(Context context) {
        Intent intent = new Intent();
        // intent.setAction("android.media.action.STILL_IMAGE_CAMERA");
        intent.setAction(MediaStore.INTENT_ACTION_STILL_IMAGE_CAMERA);
        intent.putExtra(EXTRAS_CAMERA_FACING, EXTRAS_CAMERA_FACING_BACK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_ACTIVITY_REORDER_TO_FRONT
                | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        context.startActivity(intent);
    }

    /**
     * 启动前摄像头拍照
     * 
     * @param context
     */
    public static void launchFrontCamera(Context context) {
        Intent intent = new Intent();
        // intent.setAction("android.media.action.STILL_IMAGE_CAMERA");
        intent.setAction(MediaStore.INTENT_ACTION_STILL_IMAGE_CAMERA);
        intent.putExtra(EXTRAS_CAMERA_FACING, EXTRAS_CAMERA_FACING_FRONT);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_ACTIVITY_REORDER_TO_FRONT
                | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        context.startActivity(intent);
    }

    /**
     * 启动后摄像头拍照,指定输出照片保存位置
     * 
     * @param context
     */
    public static void launchBackCameraForResult(Activity context, int id) {
        String imgPath = Environment.getExternalStorageDirectory().getPath() + "/pickuptest/back.jpg";
        File vFile = new File(imgPath);
        if (!vFile.exists()) {
            File vDirPath = vFile.getParentFile();
            if (vDirPath != null) {
                vDirPath.mkdirs();
            }
        }
        Uri uri = Uri.fromFile(vFile);
        Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        i.putExtra(EXTRAS_CAMERA_FACING, EXTRAS_CAMERA_FACING_BACK);
        i.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        context.startActivityForResult(i, id);
    }

    /**
     * 启动前摄像头拍照,指定输出照片保存位置
     * 
     * @param context
     */
    public static void launchFrontCameraForResult(Activity context, int id) {
        String imgPath = Environment.getExternalStorageDirectory().getPath() + "/pickuptest/front.jpg";
        File vFile = new File(imgPath);
        if (!vFile.exists()) {
            File vDirPath = vFile.getParentFile();
            if (vDirPath != null) {
                vDirPath.mkdirs();
            }
        }
        Uri uri = Uri.fromFile(vFile);
        Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        i.putExtra(EXTRAS_CAMERA_FACING, EXTRAS_CAMERA_FACING_FRONT);
        i.putExtra(MediaStore.EXTRA_OUTPUT, uri);//
        context.startActivityForResult(i, id);
    }

    public static void handleOnActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            // iViewPic.setImageURI(Uri.fromFile(new File(imgPath)));
            // 假设不传参数MediaStore.EXTRA_OUTPUT的情况下，onActivityResult函数在resultCode为RESULT_OK的情况下，data返回的参数是经过实际拍摄照片经过缩放的图像数据，可以通过类似如下方法来打印缩放图像的尺寸
            // 比如Android4.2.2里面PhotoModule.java
            /**
             * <pre>
             * int orientation = Exif.getOrientation(data);
             * Bitmap bitmap = Util.makeBitmap(data, 50 * 1024);
             * bitmap = Util.rotate(bitmap, orientation);
             * mActivity.setResultEx(Activity.RESULT_OK, new
             *         Intent(&quot;inline-data&quot;).putExtra(&quot;data&quot;, bitmap));
             * mActivity.finish();
             * </pre>
             */
            Bitmap bmp = null;
            if (data != null && data.getExtras() != null) {
                bmp = (Bitmap) data.getExtras().get("data");
            } else {
                Log.d(TAG, "If had pass MediaStore.EXTRA_OUTPUT, result data is null!");
            }
            if (bmp != null) {
                Log.d(TAG, "bmp width:" + bmp.getWidth() + ", height:" + bmp.getHeight());
            } else {
                Log.d(TAG, "bmp return null");
            }
        } else {
            Log.d(TAG, "resultCode return fail!");
        }
    }

    /**
     * 媒体扫描监听
     */
    public static BroadcastReceiver mScanReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(Intent.ACTION_MEDIA_SCANNER_FINISHED)) {
            } else if (action.equals(Intent.ACTION_MEDIA_SCANNER_STARTED)) {
            }
        }

    };

    /**
     * 监听媒体扫描
     */
    public static void registerScreenEvent(Context context) {
        IntentFilter intentFilter = new IntentFilter(Intent.ACTION_MEDIA_SCANNER_STARTED);
        intentFilter.addAction(Intent.ACTION_MEDIA_SCANNER_FINISHED);
        intentFilter.addDataScheme("file");
        context.registerReceiver(mScanReceiver, intentFilter);
    }

    /**
     * 解除监听媒体扫描
     */
    public static void unregisterScreenEvent(Context context) {
        context.unregisterReceiver(mScanReceiver);
    }

    /**
     * 请求媒体扫描器扫描指定目录
     * 
     * @param context
     */
    public static void scanPathMedia(Context context) {
        /**
         * 老的API
         * 
         * <pre>
         * MainActivity.this.sendBroadcast(new Intent(Intent.ACTION_MEDIA_MOUNTED, Uri.parse(&quot;file://&quot; + Environment.getExternalStorageDirectory())));
         * </pre>
         */
        MediaScannerConnection.scanFile(context, new String[] {
                Environment.getExternalStorageDirectory().getPath()
        }, null, null);
    }

    /**
     * 启动MediaScannerService对下载的文件进行扫描
     * 
     * @param context
     * @see MediaScannerConnection
     * @see MediaScannerConnectionClient
     * @see android.media.MediaScanner
     * @see com.android.providers.media.MediaScannerReceive
     * @see com.android.providers.media.MediaScannerService
     */
    public static void bindMediaScanner(Context context) {
        Intent intent = new Intent();
        intent.setClassName("com.android.providers.media",
                "com.android.providers.media.MediaScannerService");
        context.bindService(intent, new MediaScannerConnection(context, null), Context.BIND_AUTO_CREATE);
    }

    /**
     * 请求媒体扫描器扫描指定目录
     * 
     * @param context
     */
    @Deprecated
    public static void scanSdcard(Context context) throws Exception {
        Intent scanIntent = new Intent(Intent.ACTION_MEDIA_MOUNTED, Uri.parse("file://"
                + Environment.getExternalStorageDirectory()));
        Log.v(TAG, "start the intent");
        IntentFilter intentFilter = new IntentFilter(Intent.ACTION_MEDIA_SCANNER_STARTED);
        intentFilter.addDataScheme("file");
        context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_MOUNTED, Uri.parse("file://"
                + Environment.getExternalStorageDirectory())));
    }

    /**
     * 获取系统铃声文件路径
     * 
     * @param context
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    public static String getSoundPath(Context context) {
        /* Settings.Global.UNLOCK_SOUND */
        String soundPath = Settings.Global.getString(context.getApplicationContext().getContentResolver(), "unlock_sound");
        // soundPath==/system/media/audio/ui/Unlock.ogg
        return soundPath;
    }
}

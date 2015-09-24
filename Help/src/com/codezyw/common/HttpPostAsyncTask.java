
package com.codezyw.common;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.http.client.ClientProtocolException;

import android.os.AsyncTask;
import android.text.TextUtils;

import com.codezyw.common.ProgressMultipartEntity.ProgressListener;

public class HttpPostAsyncTask extends AsyncTask<String, Integer, String> {
    private long mTotalSize;

    private PostListener mPostListener;

    private ProgressMultipartEntity mMultipartEntity;

    private HttpURLConnection mHttpURLConnection;

    private BufferedReader mBuffer;

    public HttpPostAsyncTask(PostListener listener, ProgressMultipartEntity multipartEntity) {
        mPostListener = listener;
        mMultipartEntity = multipartEntity;
    }

    public static interface PostListener {
        void onPreExecute();

        void onProgressUpdate(int progress);

        void onPostExecute(String result);

        void onCancelled(String result);
    }

    @Override
    protected void onPreExecute() {
        if (mPostListener != null) {
            mPostListener.onPreExecute();
        }
    }

    public void abort() {
        if (mMultipartEntity != null) {
            ProgressMultipartEntity.cancel();
        }
        cancel(true);
        if (mBuffer != null) {
            try {
                mBuffer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (mHttpURLConnection != null) {
            mHttpURLConnection.disconnect();
            mHttpURLConnection = null;
        }
    }

    @Override
    protected String doInBackground(String... urls) {
        if (mMultipartEntity == null || isCancelled() || urls == null || urls.length <= 0
                || TextUtils.isEmpty(urls[0])) {
            return null;
        }
        mMultipartEntity.setListener(new ProgressListener() {
            @Override
            public void transferred(long num) {
                if (mTotalSize == 0) {
                    return;
                }
                publishProgress((int) ((num / (float) mTotalSize) * 100));
            }
        });
        mTotalSize = mMultipartEntity.getContentLength();
        String resultData = null;
        HttpURLConnection mHttpURLConnection = null;
        try {
            URL urlc = new URL(urls[0]);
            mHttpURLConnection = (HttpURLConnection) urlc.openConnection();
            mHttpURLConnection.setReadTimeout(30000);
            mHttpURLConnection.setConnectTimeout(15000);
            mHttpURLConnection.setRequestMethod("POST");
            mHttpURLConnection.setDoOutput(true);
            mHttpURLConnection.setDoInput(true);
            mHttpURLConnection.setUseCaches(false);
            mHttpURLConnection.setRequestProperty("Connection", "Keep-Alive");
            mHttpURLConnection.addRequestProperty("Content-length", mMultipartEntity.getContentLength() + "");
            mHttpURLConnection.addRequestProperty(mMultipartEntity.getContentType().getName(), mMultipartEntity.getContentType().getValue());

            mHttpURLConnection.connect();
            OutputStream out = mHttpURLConnection.getOutputStream();
            mMultipartEntity.writeTo(out);
            out.flush();
            out.close();
            mBuffer = new BufferedReader(new InputStreamReader(mHttpURLConnection.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String inputLine = null;
            while (((inputLine = mBuffer.readLine()) != null)) {
                sb.append(inputLine).append("\n");
            }
            resultData = sb.toString();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (mBuffer != null) {
                try {
                    mBuffer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (mHttpURLConnection != null) {
                mHttpURLConnection.disconnect();
            }
        }
        return resultData;
    }

    @Override
    protected void onProgressUpdate(Integer... progress) {
        if (mPostListener != null) {
            mPostListener.onProgressUpdate(progress[0]);
        }
    }

    @Override
    protected void onPostExecute(String result) {
        if (mPostListener != null) {
            mPostListener.onPostExecute(result);
        }
    }

    @Override
    protected void onCancelled(String result) {
        if (mPostListener != null) {
            mPostListener.onCancelled(result);
        }
    }
}

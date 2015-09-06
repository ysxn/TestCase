
package com.codezyw.common;

import com.codezyw.common.ProgressMultipartEntity.ProgressListener;

import android.os.AsyncTask;

public class HttpPostAsyncTask extends AsyncTask<String, Integer, String> {

    private long mTotalSize;

    private PostListener mPostListener;

    private ProgressMultipartEntity mMultipartEntity;

    public HttpPostAsyncTask(PostListener listener, ProgressMultipartEntity multipartEntity) {
        mPostListener = listener;
        mMultipartEntity = multipartEntity;
    }

    public static interface PostListener {
        void onPreExecute();

        void onProgressUpdate(int progress);

        void onPostExecute(String result);

        void onCancelled();
    }

    @Override
    protected void onPreExecute() {
        if (mPostListener != null) {
            mPostListener.onPreExecute();
        }
    }

    @Override
    protected String doInBackground(String... url) {
        if (mMultipartEntity == null || isCancelled() || url == null || url.length <= 0) {
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
        return HttpHelper.httpPost(url[0], mMultipartEntity);
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
    protected void onCancelled() {
        if (mPostListener != null) {
            mPostListener.onCancelled();
        }
    }
}

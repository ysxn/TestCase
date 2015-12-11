
package com.codezyw.common;

import java.util.List;

import org.apache.http.NameValuePair;

import android.text.TextUtils;

/**
 * 支持SSL或者非SSL
 */
public class HttpGetNamedAsyncTask extends HttpPostAsyncTask {
    private PostListener mPostListener;
    private boolean mSSL = false;

    public HttpGetNamedAsyncTask(PostListener listener) {
        mPostListener = listener;
    }

    public HttpGetNamedAsyncTask(PostListener listener, List<NameValuePair> nameValuePairs, boolean ssl) {
        mPostListener = listener;
        mSSL = ssl;
    }

    @Override
    protected void onPreExecute() {
        if (mPostListener != null) {
            mPostListener.onPreExecute();
        }
    }

    public void abort() {
        cancel(true);
    }

    @Override
    protected String doInBackground(String... urls) {
        if (isCancelled() || urls == null || urls.length <= 0
                || TextUtils.isEmpty(urls[0])) {
            return null;
        }
        if (mSSL) {
            return HttpHelper.httpGet(urls[0]);
        } else {
            return HttpHelper.httpGet(urls[0]);
        }
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

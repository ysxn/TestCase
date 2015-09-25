
package com.codezyw.common;

import java.util.List;

import org.apache.http.NameValuePair;

import android.text.TextUtils;

public class HttpPostNamedAsyncTask extends HttpPostAsyncTask {
    private PostListener mPostListener;
    private List<NameValuePair> nameValuePairs;

    public HttpPostNamedAsyncTask(PostListener listener, List<NameValuePair> nameValuePairs) {
        mPostListener = listener;
        this.nameValuePairs = nameValuePairs;
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
                || TextUtils.isEmpty(urls[0])
                || nameValuePairs == null || nameValuePairs.size() <= 0) {
            return null;
        }
        return HttpHelper.httpPost(urls[0], nameValuePairs);
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

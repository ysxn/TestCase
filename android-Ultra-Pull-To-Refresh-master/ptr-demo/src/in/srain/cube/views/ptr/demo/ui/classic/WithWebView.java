
package in.srain.cube.views.ptr.demo.ui.classic;

import in.srain.cube.mints.base.TitleBaseFragment;
import in.srain.cube.views.ptr.DefaultOnCheckPullListener;
import in.srain.cube.views.ptr.DefaultPullWidget;
import in.srain.cube.views.ptr.PullWidget;
import in.srain.cube.views.ptr.PullWidget.OnCheckPullListener;
import in.srain.cube.views.ptr.demo.R;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class WithWebView extends TitleBaseFragment {

    private DefaultPullWidget mPtrFrame;
    private WebView mWebView;

    @Override
    public View createView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        setHeaderTitle(R.string.ptr_demo_block_web_view);

        final View contentView = inflater.inflate(R.layout.fragment_classic_header_with_webview, null);
        mWebView = (WebView) contentView.findViewById(R.id.rotate_header_web_view);
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                mPtrFrame.refreshComplete();
            }
        });
        mPtrFrame = (DefaultPullWidget) contentView.findViewById(R.id.rotate_header_web_view_frame);
        mPtrFrame.setLastUpdateTimeRelateObject(this);
        mPtrFrame.setOnCheckPullListener(new OnCheckPullListener() {
            @Override
            public boolean canPullFromTop(PullWidget frame, View content, View header) {
                return DefaultOnCheckPullListener.checkContentCanBePulledDown(frame, mWebView, header);
            }

            @Override
            public void onRefreshBegin(PullWidget frame) {
                updateData();
            }

            @Override
            public boolean canPullFromBottom(PullWidget frame, View content, View header) {
                return false;
            }
        });
        // the following are default settings
        mPtrFrame.setResistance(1.7f);
        mPtrFrame.setRatioOfHeaderHeightToRefresh(1.2f);
        mPtrFrame.setDurationToClose(200);
        mPtrFrame.setDurationToCloseHeader(1000);
        // default is false
        mPtrFrame.setPullToRefresh(false);
        // default is true
        mPtrFrame.setKeepHeaderWhenRefresh(true);
        mPtrFrame.postDelayed(new Runnable() {
            @Override
            public void run() {
                mPtrFrame.autoRefresh();
            }
        }, 100);
        return contentView;
    }

    private void updateData() {
        mWebView.loadUrl("http://www.liaohuqiu.net/");
    }
}

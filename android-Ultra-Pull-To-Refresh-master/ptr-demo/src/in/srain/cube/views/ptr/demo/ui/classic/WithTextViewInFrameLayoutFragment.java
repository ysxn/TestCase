
package in.srain.cube.views.ptr.demo.ui.classic;

import in.srain.cube.mints.base.TitleBaseFragment;
import in.srain.cube.views.ptr.DefaultPullWidget;
import in.srain.cube.views.ptr.PullWidget;
import in.srain.cube.views.ptr.PullWidget.OnCheckPullListener;
import in.srain.cube.views.ptr.demo.R;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class WithTextViewInFrameLayoutFragment extends TitleBaseFragment {

    @Override
    public View createView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        setHeaderTitle(R.string.ptr_demo_block_frame_layout);

        final View contentView = inflater.inflate(R.layout.fragment_classic_header_with_viewgroup, container, false);

        final DefaultPullWidget ptrFrame = (DefaultPullWidget) contentView.findViewById(R.id.fragment_rotate_header_with_view_group_frame);
        ptrFrame.setOnCheckPullListener(new OnCheckPullListener() {
            @Override
            public void onRefreshBegin(PullWidget frame) {
                frame.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        ptrFrame.refreshComplete();
                    }
                }, 1800);
            }

            @Override
            public boolean canPullFromTop(PullWidget frame, View content, View header) {
                return true;
            }

            @Override
            public boolean canPullFromBottom(PullWidget frame, View content, View header) {
                return false;
            }
        });
        ptrFrame.setLastUpdateTimeRelateObject(this);

        // the following are default settings
        ptrFrame.setResistance(1.7f);
        ptrFrame.setRatioOfHeaderHeightToRefresh(1.2f);
        ptrFrame.setDurationToClose(200);
        ptrFrame.setDurationToCloseHeader(1000);
        // default is false
        ptrFrame.setPullToRefresh(false);
        // default is true
        ptrFrame.setKeepHeaderWhenRefresh(true);

        // scroll then refresh
        // comment in base fragment
        ptrFrame.postDelayed(new Runnable() {
            @Override
            public void run() {
                // ptrFrame.autoRefresh();
            }
        }, 150);

        setupViews(ptrFrame);

        return contentView;
    }

    protected void setupViews(final DefaultPullWidget ptrFrame) {

    }
}

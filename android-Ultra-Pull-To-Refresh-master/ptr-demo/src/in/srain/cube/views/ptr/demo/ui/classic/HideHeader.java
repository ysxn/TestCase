
package in.srain.cube.views.ptr.demo.ui.classic;

import in.srain.cube.views.ptr.DefaultOnCheckPullListener;
import in.srain.cube.views.ptr.DefaultPullWidget;
import in.srain.cube.views.ptr.PullWidget;
import in.srain.cube.views.ptr.demo.R;
import in.srain.cube.views.ptr.demo.ui.Utils;
import android.view.View;

public class HideHeader extends WithTextViewInFrameLayoutFragment {

    @Override
    protected void setupViews(final DefaultPullWidget ptrFrame) {
        setHeaderTitle(R.string.ptr_demo_block_hide_header);
        ptrFrame.setKeepHeaderWhenRefresh(false);

        final View loading = Utils.createSimpleLoadingTip(getContext());
        mTitleHeaderBar.getRightViewContainer().addView(loading);

        ptrFrame.setOnCheckPullListener(new DefaultOnCheckPullListener() {
            @Override
            public void onRefreshBegin(PullWidget frame) {
                loading.setVisibility(View.VISIBLE);
                frame.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        loading.setVisibility(View.INVISIBLE);
                        ptrFrame.refreshComplete();
                    }
                }, 1500);
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
    }
}

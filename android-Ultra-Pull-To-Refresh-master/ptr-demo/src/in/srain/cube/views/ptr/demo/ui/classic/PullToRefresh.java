package in.srain.cube.views.ptr.demo.ui.classic;

import in.srain.cube.views.ptr.PullWidget;
import in.srain.cube.views.ptr.demo.R;

public class PullToRefresh extends WithTextViewInFrameLayoutFragment {

    @Override
    protected void setupViews(PullWidget ptrFrame) {
        setHeaderTitle(R.string.ptr_demo_block_pull_to_refresh);
        ptrFrame.setPullToRefresh(true);
    }
}

package in.srain.cube.views.ptr.demo.ui.classic;

import in.srain.cube.views.ptr.DefaultPullWidget;
import in.srain.cube.views.ptr.demo.R;

public class ReleaseToRefresh extends WithTextViewInFrameLayoutFragment {

    @Override
    protected void setupViews(DefaultPullWidget ptrFrame) {
        setHeaderTitle(R.string.ptr_demo_block_release_to_refresh);
        ptrFrame.setPullToRefresh(false);
    }
}

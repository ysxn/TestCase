package in.srain.cube.views.ptr.demo.ui.classic;

import in.srain.cube.views.ptr.PullWidget;
import in.srain.cube.views.ptr.demo.R;

public class KeepHeader extends WithTextViewInFrameLayoutFragment {

    @Override
    protected void setupViews(PullWidget ptrFrame) {
        setHeaderTitle(R.string.ptr_demo_block_keep_header);
        ptrFrame.setKeepHeaderWhenRefresh(true);
    }
}


package in.srain.cube.views.ptr.demo.ui.classic;

import in.srain.cube.mints.base.TitleBaseFragment;
import in.srain.cube.views.ptr.DefaultOnCheckPullListener;
import in.srain.cube.views.ptr.DefaultPullWidget;
import in.srain.cube.views.ptr.PullWidget;
import in.srain.cube.views.ptr.demo.R;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class EvenOnlyATextView extends TitleBaseFragment {

    public View createView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        setHeaderTitle(R.string.ptr_demo_block_only_text_view);

        final View contentView = inflater.inflate(R.layout.fragment_classic_header_with_textview, container, false);

        final DefaultPullWidget ptrFrame = (DefaultPullWidget) contentView.findViewById(R.id.fragment_rotate_header_with_text_view_frame);
        ptrFrame.setLastUpdateTimeRelateObject(this);
        ptrFrame.setPtrHandler(new DefaultOnCheckPullListener() {
            @Override
            public void onRefreshBegin(PullWidget frame) {
                frame.postDelayed(new Runnable() {
                    @Override
                    public void run() {
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
        return contentView;
    }

}

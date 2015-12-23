
package in.srain.cube.views.ptr.demo.ui.storehouse;

import in.srain.cube.image.CubeImageView;
import in.srain.cube.image.ImageLoader;
import in.srain.cube.image.ImageLoaderFactory;
import in.srain.cube.mints.base.TitleBaseFragment;
import in.srain.cube.util.LocalDisplay;
import in.srain.cube.views.ptr.PullViewManager;
import in.srain.cube.views.ptr.PullWidget;
import in.srain.cube.views.ptr.PullWidget.OnCheckPullListener;
import in.srain.cube.views.ptr.PullWidget.OnPullUIListener;
import in.srain.cube.views.ptr.demo.R;
import in.srain.cube.views.ptr.header.StoreHouseHeader;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class StoreHouseUsingString extends TitleBaseFragment {

    final String[] mStringList = {
            "Alibaba", "TMALL 11-11"
    };
    private String mTitlePre;

    @Override
    protected View createView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_storehouse_header, null);
        mTitlePre = getString(R.string.ptr_demo_storehouse_header_using_string_in_title);

        CubeImageView imageView = (CubeImageView) view.findViewById(R.id.store_house_ptr_image);
        ImageLoader imageLoader = ImageLoaderFactory.create(getContext());
        String pic = "http://img5.duitang.com/uploads/item/201406/28/20140628122218_fLQyP.thumb.jpeg";
        imageView.loadImage(imageLoader, pic);

        final PullWidget frame = (PullWidget) view.findViewById(R.id.store_house_ptr_frame);

        // header
        final StoreHouseHeader header = new StoreHouseHeader(getContext());
        header.setPadding(0, LocalDisplay.dp2px(15), 0, 0);

        /**
         * using a string, support: A-Z 0-9 - . you can add more letters by {@link in.srain.cube.views.ptr.header.StoreHousePath#addChar}
         */
        header.initWithString(mStringList[0]);
        setHeaderTitle(mTitlePre + mStringList[0]);

        // for changing string
        frame.addOnPullUIListener(new OnPullUIListener() {

            private int mLoadTime = 0;

            @Override
            public void onUIReset(PullWidget frame) {
                mLoadTime++;
                String string = mStringList[mLoadTime % mStringList.length];
                header.initWithString(string);
            }

            @Override
            public void onUIRefreshPrepare(PullWidget frame) {
                String string = mStringList[mLoadTime % mStringList.length];
                setHeaderTitle(mTitlePre + string);
            }

            @Override
            public void onUIRefreshBegin(PullWidget frame) {

            }

            @Override
            public void onUIRefreshComplete(PullWidget frame) {

            }

            @Override
            public void onUIPositionChange(PullWidget frame, boolean isUnderTouch, byte status, PullViewManager ptrIndicator) {

            }
        });

        frame.setDurationToCloseHeader(3000);
        frame.setHeaderView(header);
        frame.addOnPullUIListener(header);
        frame.postDelayed(new Runnable() {
            @Override
            public void run() {
                frame.autoRefresh(false);
            }
        }, 100);

        frame.setOnCheckPullListener(new OnCheckPullListener() {
            @Override
            public void onRefreshBegin(final PullWidget frame) {
                frame.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        frame.refreshComplete();
                    }
                }, 2000);
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
        return view;
    }
}

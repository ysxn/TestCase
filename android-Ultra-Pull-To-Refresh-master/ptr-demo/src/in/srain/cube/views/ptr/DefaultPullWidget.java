package in.srain.cube.views.ptr;

import android.content.Context;
import android.util.AttributeSet;

public class DefaultPullWidget extends PullWidget {

    private DefaultHeader mPtrClassicHeader;

    public DefaultPullWidget(Context context) {
        super(context);
        initViews();
    }

    public DefaultPullWidget(Context context, AttributeSet attrs) {
        super(context, attrs);
        initViews();
    }

    public DefaultPullWidget(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initViews();
    }

    private void initViews() {
        mPtrClassicHeader = new DefaultHeader(getContext());
        setHeaderView(mPtrClassicHeader);
        addPtrUIHandler(mPtrClassicHeader);
    }

    public DefaultHeader getHeader() {
        return mPtrClassicHeader;
    }

    /**
     * Specify the last update time by this key string
     *
     * @param key
     */
    public void setLastUpdateTimeKey(String key) {
        if (mPtrClassicHeader != null) {
            mPtrClassicHeader.setLastUpdateTimeKey(key);
        }
    }

    /**
     * Using an object to specify the last update time.
     *
     * @param object
     */
    public void setLastUpdateTimeRelateObject(Object object) {
        if (mPtrClassicHeader != null) {
            mPtrClassicHeader.setLastUpdateTimeRelateObject(object);
        }
    }
}

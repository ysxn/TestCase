
package com.codezyw.widget.banner;

import android.os.Parcelable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.codezyw.common.BasePagerAdapter;

/**
 * A {@link android.support.v4.view.PagerAdapter} which behaves like an
 * {@link android.widget.Adapter} with view types and view recycling.
 */
public abstract class RecyclingPagerAdapter extends BasePagerAdapter {
    private final RecycleBin mRecycleBin;

    public RecyclingPagerAdapter() {
        this(new RecycleBin());
    }

    RecyclingPagerAdapter(RecycleBin recycleBin) {
        this.mRecycleBin = recycleBin;
        recycleBin.setViewTypeCount(getViewTypeCount());
    }

    /**
     * Return the number of views available.
     */
    public abstract int getCount();

    /**
     * Called when a change in the shown pages is going to start being made.
     * 
     * @param container The containing View which is displaying this adapter's
     *            page views.
     */
    public void startUpdate(ViewGroup container) {
        startUpdate((View) container);
    }

    /**
     * Create the page for the given position. The adapter is responsible for
     * adding the view to the container given here, although it only must ensure
     * this is done by the time it returns from {@link #finishUpdate(ViewGroup)}
     * .
     * 
     * @param container The containing View in which the page will be shown.
     * @param position The page position to be instantiated.
     * @return Returns an Object representing the new page. This does not need
     *         to be a View, but can be some other container of the page.
     */
    public Object instantiateItem(ViewGroup container, int position) {
        // return instantiateItem((View) container, position);

        int viewType = getItemViewType(position);
        View view = null;
        if (viewType != AdapterView.ITEM_VIEW_TYPE_IGNORE) {
            view = mRecycleBin.getScrapView(position, viewType);
        }
        view = getView(position, view, container);
        container.addView(view);
        return view;
    }

    /**
     * Remove a page for the given position. The adapter is responsible for
     * removing the view from its container, although it only must ensure this
     * is done by the time it returns from {@link #finishUpdate(ViewGroup)}.
     * 
     * @param container The containing View from which the page will be removed.
     * @param position The page position to be removed.
     * @param object The same object that was returned by
     *            {@link #instantiateItem(View, int)}.
     */
    public void destroyItem(ViewGroup container, int position, Object object) {
        // destroyItem((View) container, position, object);

        View view = (View) object;
        container.removeView(view);
        int viewType = getItemViewType(position);
        if (viewType != AdapterView.ITEM_VIEW_TYPE_IGNORE) {
            mRecycleBin.addScrapView(view, position, viewType);
        }
    }

    /**
     * Called to inform the adapter of which item is currently considered to be
     * the "primary", that is the one show to the user as the current page.
     * 
     * @param container The containing View from which the page will be removed.
     * @param position The page position that is now the primary.
     * @param object The same object that was returned by
     *            {@link #instantiateItem(View, int)}.
     */
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        setPrimaryItem((View) container, position, object);
    }

    /**
     * Called when the a change in the shown pages has been completed. At this
     * point you must ensure that all of the pages have actually been added or
     * removed from the container as appropriate.
     * 
     * @param container The containing View which is displaying this adapter's
     *            page views.
     */
    public void finishUpdate(ViewGroup container) {
        finishUpdate((View) container);
    }

    /**
     * Called when a change in the shown pages is going to start being made.
     * 
     * @param container The containing View which is displaying this adapter's
     *            page views.
     * @deprecated Use {@link #startUpdate(ViewGroup)}
     */
    public void startUpdate(View container) {
    }

    /**
     * Create the page for the given position. The adapter is responsible for
     * adding the view to the container given here, although it only must ensure
     * this is done by the time it returns from {@link #finishUpdate(ViewGroup)}
     * .
     * 
     * @param container The containing View in which the page will be shown.
     * @param position The page position to be instantiated.
     * @return Returns an Object representing the new page. This does not need
     *         to be a View, but can be some other container of the page.
     * @deprecated Use {@link #instantiateItem(ViewGroup, int)}
     */
    public Object instantiateItem(View container, int position) {
        throw new UnsupportedOperationException(
                "Required method instantiateItem was not overridden");
    }

    /**
     * Remove a page for the given position. The adapter is responsible for
     * removing the view from its container, although it only must ensure this
     * is done by the time it returns from {@link #finishUpdate(View)}.
     * 
     * @param container The containing View from which the page will be removed.
     * @param position The page position to be removed.
     * @param object The same object that was returned by
     *            {@link #instantiateItem(View, int)}.
     * @deprecated Use {@link #destroyItem(ViewGroup, int, Object)}
     */
    public void destroyItem(View container, int position, Object object) {
        throw new UnsupportedOperationException("Required method destroyItem was not overridden");
    }

    /**
     * Called to inform the adapter of which item is currently considered to be
     * the "primary", that is the one show to the user as the current page.
     * 
     * @param container The containing View from which the page will be removed.
     * @param position The page position that is now the primary.
     * @param object The same object that was returned by
     *            {@link #instantiateItem(View, int)}.
     * @deprecated Use {@link #setPrimaryItem(ViewGroup, int, Object)}
     */
    public void setPrimaryItem(View container, int position, Object object) {
    }

    /**
     * Called when the a change in the shown pages has been completed. At this
     * point you must ensure that all of the pages have actually been added or
     * removed from the container as appropriate.
     * 
     * @param container The containing View which is displaying this adapter's
     *            page views.
     * @deprecated Use {@link #finishUpdate(ViewGroup)}
     */
    public void finishUpdate(View container) {
    }

    /**
     * Determines whether a page View is associated with a specific key object
     * as returned by {@link #instantiateItem(ViewGroup, int)}. This method is
     * required for a PagerAdapter to function properly.
     * 
     * @param view Page View to check for association with <code>object</code>
     * @param object Object to check for association with <code>view</code>
     * @return true if <code>view</code> is associated with the key object
     *         <code>object</code>
     */
    @Override
    public final boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    /**
     * Save any instance state associated with this adapter and its pages that
     * should be restored if the current UI state needs to be reconstructed.
     * 
     * @return Saved state for this adapter
     */
    public Parcelable saveState() {
        return null;
    }

    /**
     * Restore any instance state associated with this adapter and its pages
     * that was previously saved by {@link #saveState()}.
     * 
     * @param state State previously saved by a call to {@link #saveState()}
     * @param loader A ClassLoader that should be used to instantiate any
     *            restored objects
     */
    public void restoreState(Parcelable state, ClassLoader loader) {
    }

    /**
     * Called when the host view is attempting to determine if an item's
     * position has changed. Returns {@link #POSITION_UNCHANGED} if the position
     * of the given item has not changed or {@link #POSITION_NONE} if the item
     * is no longer present in the adapter.
     * <p>
     * The default implementation assumes that items will never change position
     * and always returns {@link #POSITION_UNCHANGED}.
     * 
     * @param object Object representing an item, previously returned by a call
     *            to {@link #instantiateItem(View, int)}.
     * @return object's new position index from [0, {@link #getCount()}),
     *         {@link #POSITION_UNCHANGED} if the object's position has not
     *         changed, or {@link #POSITION_NONE} if the item is no longer
     *         present.
     */
    public int getItemPosition(Object object) {
        return POSITION_UNCHANGED;
    }

    /**
     * This method may be called by the ViewPager to obtain a title string to
     * describe the specified page. This method may return null indicating no
     * title for this page. The default implementation returns null.
     * 
     * @param position The position of the title requested
     * @return A title for the requested page
     */
    public CharSequence getPageTitle(int position) {
        return null;
    }

    /**
     * Returns the proportional width of a given page as a percentage of the
     * ViewPager's measured width from (0.f-1.f]
     * 
     * @param position The position of the page requested
     * @return Proportional width for the given page position
     */
    public float getPageWidth(int position) {
        return 1.f;
    }

    /**
     * 下面都是从{@link android.widget.AdapterView}
     * 挪过来的函数，利用View的复用来使得ViewPager表现的类似ListView.
     * <p>
     * A {@link android.support.v4.view.PagerAdapter} which behaves like an
     * {@link android.widget.Adapter} with view types and view recycling.
     */
    /**
     * 下面都是从{@link android.widget.AdapterView}
     * 挪过来的函数，利用View的复用来使得ViewPager表现的类似ListView.
     * <p>
     * Move all views remaining in activeViews to scrapViews.
     * <p>
     * This method should be called by the application if the data backing this
     * adapter has changed and associated views should update.
     */
    @Override
    public void notifyDataSetChanged() {
        mRecycleBin.scrapActiveViews();
        super.notifyDataSetChanged();
    }

    /**
     * 下面都是从{@link android.widget.AdapterView}
     * 挪过来的函数，利用View的复用来使得ViewPager表现的类似ListView.
     * <p>
     * <p>
     * Returns the number of types of Views that will be created by
     * {@link #getView}. Each type represents a set of views that can be
     * converted in {@link #getView}. If the adapter always returns the same
     * type of View for all items, this method should return 1.
     * </p>
     * <p>
     * This method will only be called when when the adapter is set on the the
     * {@link android.widget.AdapterView}.
     * </p>
     * 
     * @return The number of types of Views that will be created by this adapter
     */
    public int getViewTypeCount() {
        return 1;
    }

    /**
     * 下面都是从{@link android.widget.AdapterView}
     * 挪过来的函数，利用View的复用来使得ViewPager表现的类似ListView.
     * <p>
     * Get the type of View that will be created by {@link #getView} for the
     * specified item.
     * 
     * @param position The position of the item within the adapter's data set
     *            whose view type we want.
     * @return An integer representing the type of View. Two views should share
     *         the same type if one can be converted to the other in
     *         {@link #getView}. Note: Integers must be in the range 0 to
     *         {@link #getViewTypeCount} - 1. {@link #IGNORE_ITEM_VIEW_TYPE} can
     *         also be returned.
     * @see #IGNORE_ITEM_VIEW_TYPE
     */
    @SuppressWarnings("UnusedParameters")
    // Argument potentially used by subclasses.
    public int getItemViewType(int position) {
        return 0;
    }

    /**
     * 下面都是从{@link android.widget.AdapterView}
     * 挪过来的函数，利用View的复用来使得ViewPager表现的类似ListView.
     * <p>
     * Get a View that displays the data at the specified position in the data
     * set. You can either create a View manually or inflate it from an XML
     * layout file. When the View is inflated, the parent View (GridView,
     * ListView...) will apply default layout parameters unless you use
     * {@link android.view.LayoutInflater#inflate(int, android.view.ViewGroup, boolean)}
     * to specify a root view and to prevent attachment to the root.
     * 
     * @param position The position of the item within the adapter's data set of
     *            the item whose view we want.
     * @param convertView The old view to reuse, if possible. Note: You should
     *            check that this view is non-null and of an appropriate type
     *            before using. If it is not possible to convert this view to
     *            display the correct data, this method can create a new view.
     *            Heterogeneous lists can specify their number of view types, so
     *            that this View is always of the right type (see
     *            {@link #getViewTypeCount()} and {@link #getItemViewType(int)}
     *            ).
     * @param container The parent that this view will eventually be attached to
     * @return A View corresponding to the data at the specified position.
     */
    public abstract View getView(int position, View convertView, ViewGroup container);
}

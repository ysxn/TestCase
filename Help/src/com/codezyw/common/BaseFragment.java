
package com.codezyw.common;

import java.io.FileDescriptor;
import java.io.PrintWriter;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.LoaderManager;
import android.support.v4.app.SharedElementCallback;
import android.util.AttributeSet;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnCreateContextMenuListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.AdapterView;

/**
 * Static library support version of the framework's
 * {@link android.app.Fragment}. Used to write apps that run on platforms prior
 * to Android 3.0. When running on Android 3.0 or above, this implementation is
 * still used; it does not try to switch to the framework's implementation. See
 * the framework {@link android.app.Fragment} documentation for a class
 * overview.
 * <p>
 * The main differences when using this support version instead of the framework
 * version are:
 * <ul>
 * <li>Your activity must extend {@link FragmentActivity}
 * <li>You must call {@link FragmentActivity#getSupportFragmentManager} to get
 * the {@link FragmentManager}
 * </ul>
 */
public class BaseFragment extends Fragment {

    /**
     * 必须有无参构造函数，否则影响状态恢复<br>
     * Default constructor. <strong>Every</strong> fragment must have an empty
     * constructor, so it can be instantiated when restoring its activity's
     * state. It is strongly recommended that subclasses do not have other
     * constructors with parameters, since these constructors will not be called
     * when the fragment is re-instantiated; instead, arguments can be supplied
     * by the caller with {@link #setArguments} and later retrieved by the
     * Fragment with {@link #getArguments}.
     * <p>
     * Applications should generally not implement a constructor. The first
     * place application code an run where the fragment is ready to be used is
     * in {@link #onAttach(Activity)}, the point where the fragment is actually
     * associated with its activity. Some applications may also want to
     * implement {@link #onInflate} to retrieve attributes from a layout
     * resource, though should take care here because this happens for the
     * fragment is attached to its activity.
     */
    public BaseFragment() {
    }

    /**
     * Supply the construction arguments for this fragment. This can only be
     * called before the fragment has been attached to its activity; that is,
     * you should call it immediately after constructing the fragment. The
     * arguments supplied here will be retained across fragment destroy and
     * creation.
     */
    public void setArguments(Bundle args) {
        super.setArguments(args);
    }

    /**
     * Set the initial saved state that this Fragment should restore itself from
     * when first being constructed, as returned by
     * {@link FragmentManager#saveFragmentInstanceState(Fragment)
     * FragmentManager.saveFragmentInstanceState}.
     * 
     * @param state The state the fragment should be restored from.
     */
    public void setInitialSavedState(SavedState state) {
        super.setInitialSavedState(state);
    }

    /**
     * Optional target for this fragment. This may be used, for example, if this
     * fragment is being started by another, and when done wants to give a
     * result back to the first. The target set here is retained across
     * instances via {@link FragmentManager#putFragment
     * FragmentManager.putFragment()}.
     * 
     * @param fragment The fragment that is the target of this one.
     * @param requestCode Optional request code, for convenience if you are
     *            going to call back with
     *            {@link #onActivityResult(int, int, Intent)}.
     */
    public void setTargetFragment(Fragment fragment, int requestCode) {
        super.setTargetFragment(fragment, requestCode);
    }

    /**
     * Called when the hidden state (as returned by {@link #isHidden()} of the
     * fragment has changed. Fragments start out not hidden; this will be called
     * whenever the fragment changes state from that.
     * 
     * @param hidden True if the fragment is now hidden, false if it is not
     *            visible.
     */
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
    }

    /**
     * Control whether a fragment instance is retained across Activity
     * re-creation (such as from a configuration change). This can only be used
     * with fragments not in the back stack. If set, the fragment lifecycle will
     * be slightly different when an activity is recreated:
     * <ul>
     * <li> {@link #onDestroy()} will not be called (but {@link #onDetach()}
     * still will be, because the fragment is being detached from its current
     * activity).
     * <li> {@link #onCreate(Bundle)} will not be called since the fragment is
     * not being re-created.
     * <li> {@link #onAttach(Activity)} and {@link #onActivityCreated(Bundle)}
     * <b>will</b> still be called.
     * </ul>
     */
    public void setRetainInstance(boolean retain) {
        super.setRetainInstance(retain);
    }

    /**
     * Report that this fragment would like to participate in populating the
     * options menu by receiving a call to {@link #onCreateOptionsMenu} and
     * related methods.
     * 
     * @param hasMenu If true, the fragment has menu items to contribute.
     */
    public void setHasOptionsMenu(boolean hasMenu) {
        super.setHasOptionsMenu(hasMenu);
    }

    /**
     * Set a hint for whether this fragment's menu should be visible. This is
     * useful if you know that a fragment has been placed in your view hierarchy
     * so that the user can not currently seen it, so any menu items it has
     * should also not be shown.
     * 
     * @param menuVisible The default is true, meaning the fragment's menu will
     *            be shown as usual. If false, the user will not see the menu.
     */
    public void setMenuVisibility(boolean menuVisible) {
        super.setMenuVisibility(menuVisible);
    }

    /**
     * Set a hint to the system about whether this fragment's UI is currently
     * visible to the user. This hint defaults to true and is persistent across
     * fragment instance state save and restore.
     * <p>
     * An app may set this to false to indicate that the fragment's UI is
     * scrolled out of visibility or is otherwise not directly visible to the
     * user. This may be used by the system to prioritize operations such as
     * fragment lifecycle updates or loader ordering behavior.
     * </p>
     * 
     * @param isVisibleToUser true if this fragment's UI is currently visible to
     *            the user (default), false if it is not.
     */
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
    }

    /**
     * @return The current value of the user-visible hint on this fragment.
     * @see #setUserVisibleHint(boolean)
     */
    public boolean getUserVisibleHint() {
        return super.getUserVisibleHint();
    }

    /**
     * Return the LoaderManager for this fragment, creating it if needed.
     */
    public LoaderManager getLoaderManager() {
        return super.getLoaderManager();
    }

    /**
     * Call {@link Activity#startActivity(Intent)} on the fragment's containing
     * Activity.
     */
    public void startActivity(Intent intent) {
        super.startActivity(intent);
    }

    /**
     * Call {@link Activity#startActivityForResult(Intent, int)} on the
     * fragment's containing Activity.
     */
    public void startActivityForResult(Intent intent, int requestCode) {
        super.startActivityForResult(intent, requestCode);
    }

    /**
     * Receive the result from a previous call to
     * {@link #startActivityForResult(Intent, int)}. This follows the related
     * Activity API as described there in
     * {@link Activity#onActivityResult(int, int, Intent)}.
     * 
     * @param requestCode The integer request code originally supplied to
     *            startActivityForResult(), allowing you to identify who this
     *            result came from.
     * @param resultCode The integer result code returned by the child activity
     *            through its setResult().
     * @param data An Intent, which can return result data to the caller
     *            (various data can be attached to Intent "extras").
     */
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * @hide Hack so that DialogFragment can make its Dialog before creating its
     *       views, and the view construction can use the dialog's context for
     *       inflation. Maybe this should become a public API. Note sure.
     */
    public LayoutInflater getLayoutInflater(Bundle savedInstanceState) {
        return super.getLayoutInflater(savedInstanceState);
    }

    /**
     * Called when a fragment is being created as part of a view layout
     * inflation, typically from setting the content view of an activity. This
     * may be called immediately after the fragment is created from a <fragment>
     * tag in a layout file. Note this is <em>before</em> the fragment's
     * {@link #onAttach(Activity)} has been called; all you should do here is
     * parse the attributes and save them away.
     * <p>
     * This is called every time the fragment is inflated, even if it is being
     * inflated into a new instance with saved state. It typically makes sense
     * to re-parse the parameters each time, to allow them to change with
     * different configurations.
     * </p>
     * <p>
     * Here is a typical implementation of a fragment that can take parameters
     * both through attributes supplied here as well from
     * {@link #getArguments()}:
     * </p>
     * {@sample development/samples/ApiDemos/src/com/example/android/apis/app/
     * FragmentArguments.java fragment}
     * <p>
     * Note that parsing the XML attributes uses a "styleable" resource. The
     * declaration for the styleable used here is:
     * </p>
     * {@sample development/samples/ApiDemos/res/values/attrs.xml
     * fragment_arguments}
     * <p>
     * The fragment can then be declared within its activity's content layout
     * through a tag like this:
     * </p>
     * {@sample development/samples/ApiDemos/res/layout/fragment_arguments.xml
     * from_attributes}
     * <p>
     * This fragment can also be created dynamically from arguments given at
     * runtime in the arguments Bundle; here is an example of doing so at
     * creation of the containing activity:
     * </p>
     * {@sample development/samples/ApiDemos/src/com/example/android/apis/app/
     * FragmentArguments.java create}
     * 
     * @param activity The Activity that is inflating this fragment.
     * @param attrs The attributes at the tag where the fragment is being
     *            created.
     * @param savedInstanceState If the fragment is being re-created from a
     *            previous saved state, this is the state.
     */
    public void onInflate(Activity activity, AttributeSet attrs, Bundle savedInstanceState) {
        super.onInflate(activity, attrs, savedInstanceState);
    }

    /**
     * Called when a fragment is first attached to its activity.
     * {@link #onCreate(Bundle)} will be called after this.
     */
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    /**
     * Called when a fragment loads an animation.
     */
    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
        return super.onCreateAnimation(transit, enter, nextAnim);
    }

    /**
     * Called to do initial creation of a fragment. This is called after
     * {@link #onAttach(Activity)} and before
     * {@link #onCreateView(LayoutInflater, ViewGroup, Bundle)}.
     * <p>
     * Note that this can be called while the fragment's activity is still in
     * the process of being created. As such, you can not rely on things like
     * the activity's content view hierarchy being initialized at this point. If
     * you want to do work once the activity itself is created, see
     * {@link #onActivityCreated(Bundle)}.
     * 
     * @param savedInstanceState If the fragment is being re-created from a
     *            previous saved state, this is the state.
     */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * Called to have the fragment instantiate its user interface view. This is
     * optional, and non-graphical fragments can return null (which is the
     * default implementation). This will be called between
     * {@link #onCreate(Bundle)} and {@link #onActivityCreated(Bundle)}.
     * <p>
     * If you return a View from here, you will later be called in
     * {@link #onDestroyView} when the view is being released.
     * 
     * @param inflater The LayoutInflater object that can be used to inflate any
     *            views in the fragment,
     * @param container If non-null, this is the parent view that the fragment's
     *            UI should be attached to. The fragment should not add the view
     *            itself, but this can be used to generate the LayoutParams of
     *            the view.
     * @param savedInstanceState If non-null, this fragment is being
     *            re-constructed from a previous saved state as given here.
     * @return Return the View for the fragment's UI, or null.
     */
    public View onCreateView(LayoutInflater inflater, @Nullable
    ViewGroup container,
            @Nullable
            Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    /**
     * Called immediately after
     * {@link #onCreateView(LayoutInflater, ViewGroup, Bundle)} has returned,
     * but before any saved state has been restored in to the view. This gives
     * subclasses a chance to initialize themselves once they know their view
     * hierarchy has been completely created. The fragment's view hierarchy is
     * not however attached to its parent at this point.
     * 
     * @param view The View returned by
     *            {@link #onCreateView(LayoutInflater, ViewGroup, Bundle)}.
     * @param savedInstanceState If non-null, this fragment is being
     *            re-constructed from a previous saved state as given here.
     */
    public void onViewCreated(View view, @Nullable
    Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    /**
     * Get the root view for the fragment's layout (the one returned by
     * {@link #onCreateView}), if provided.
     * 
     * @return The fragment's root view, or null if it has no layout.
     */
    @Nullable
    public View getView() {
        return super.getView();
    }

    /**
     * Called when the fragment's activity has been created and this fragment's
     * view hierarchy instantiated. It can be used to do final initialization
     * once these pieces are in place, such as retrieving views or restoring
     * state. It is also useful for fragments that use
     * {@link #setRetainInstance(boolean)} to retain their instance, as this
     * callback tells the fragment when it is fully associated with the new
     * activity instance. This is called after {@link #onCreateView} and before
     * {@link #onViewStateRestored(Bundle)}.
     * 
     * @param savedInstanceState If the fragment is being re-created from a
     *            previous saved state, this is the state.
     */
    public void onActivityCreated(@Nullable
    Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    /**
     * Called when all saved state has been restored into the view hierarchy of
     * the fragment. This can be used to do initialization based on saved state
     * that you are letting the view hierarchy track itself, such as whether
     * check box widgets are currently checked. This is called after
     * {@link #onActivityCreated(Bundle)} and before {@link #onStart()}.
     * 
     * @param savedInstanceState If the fragment is being re-created from a
     *            previous saved state, this is the state.
     */
    public void onViewStateRestored(@Nullable
    Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
    }

    /**
     * Called when the Fragment is visible to the user. This is generally tied
     * to {@link Activity#onStart() Activity.onStart} of the containing
     * Activity's lifecycle.
     */
    public void onStart() {
        super.onStart();
    }

    /**
     * Called when the fragment is visible to the user and actively running.
     * This is generally tied to {@link Activity#onResume() Activity.onResume}
     * of the containing Activity's lifecycle.
     */
    public void onResume() {
        super.onResume();
    }

    /**
     * Called to ask the fragment to save its current dynamic state, so it can
     * later be reconstructed in a new instance of its process is restarted. If
     * a new instance of the fragment later needs to be created, the data you
     * place in the Bundle here will be available in the Bundle given to
     * {@link #onCreate(Bundle)},
     * {@link #onCreateView(LayoutInflater, ViewGroup, Bundle)}, and
     * {@link #onActivityCreated(Bundle)}.
     * <p>
     * This corresponds to {@link Activity#onSaveInstanceState(Bundle)
     * Activity.onSaveInstanceState(Bundle)} and most of the discussion there
     * applies here as well. Note however: <em>this method may be called
     * at any time before {@link #onDestroy()}</em>. There are many situations
     * where a fragment may be mostly torn down (such as when placed on the back
     * stack with no UI showing), but its state will not be saved until its
     * owning activity actually needs to save its state.
     * 
     * @param outState Bundle in which to place your saved state.
     */
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    /**
     * Called when the Fragment is no longer resumed. This is generally tied to
     * {@link Activity#onPause() Activity.onPause} of the containing Activity's
     * lifecycle.
     */
    public void onPause() {
        super.onPause();
    }

    /**
     * Called when the Fragment is no longer started. This is generally tied to
     * {@link Activity#onStop() Activity.onStop} of the containing Activity's
     * lifecycle.
     */
    public void onStop() {
        super.onStop();
    }

    public void onLowMemory() {
        super.onLowMemory();
    }

    /**
     * Called when the view previously created by {@link #onCreateView} has been
     * detached from the fragment. The next time the fragment needs to be
     * displayed, a new view will be created. This is called after
     * {@link #onStop()} and before {@link #onDestroy()}. It is called
     * <em>regardless</em> of whether {@link #onCreateView} returned a non-null
     * view. Internally it is called after the view's state has been saved but
     * before it has been removed from its parent.
     */
    public void onDestroyView() {
        super.onDestroyView();
    }

    /**
     * Called when the fragment is no longer in use. This is called after
     * {@link #onStop()} and before {@link #onDetach()}.
     */
    public void onDestroy() {
        super.onDestroy();
    }

    /**
     * Called when the fragment is no longer attached to its activity. This is
     * called after {@link #onDestroy()}.
     */
    public void onDetach() {
        super.onDetach();
    }

    /**
     * Initialize the contents of the Activity's standard options menu. You
     * should place your menu items in to <var>menu</var>. For this method to be
     * called, you must have first called {@link #setHasOptionsMenu}. See
     * {@link Activity#onCreateOptionsMenu(Menu) Activity.onCreateOptionsMenu}
     * for more information.
     * 
     * @param menu The options menu in which you place your items.
     * @see #setHasOptionsMenu
     * @see #onPrepareOptionsMenu
     * @see #onOptionsItemSelected
     */
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }

    /**
     * Prepare the Screen's standard options menu to be displayed. This is
     * called right before the menu is shown, every time it is shown. You can
     * use this method to efficiently enable/disable items or otherwise
     * dynamically modify the contents. See
     * {@link Activity#onPrepareOptionsMenu(Menu) Activity.onPrepareOptionsMenu}
     * for more information.
     * 
     * @param menu The options menu as last shown or first initialized by
     *            onCreateOptionsMenu().
     * @see #setHasOptionsMenu
     * @see #onCreateOptionsMenu
     */
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
    }

    /**
     * Called when this fragment's option menu items are no longer being
     * included in the overall options menu. Receiving this call means that the
     * menu needed to be rebuilt, but this fragment's items were not included in
     * the newly built menu (its
     * {@link #onCreateOptionsMenu(Menu, MenuInflater)} was not called).
     */
    public void onDestroyOptionsMenu() {
        super.onDestroyOptionsMenu();
    }

    /**
     * This hook is called whenever an item in your options menu is selected.
     * The default implementation simply returns false to have the normal
     * processing happen (calling the item's Runnable or sending a message to
     * its Handler as appropriate). You can use this method for any items for
     * which you would like to do processing without those other facilities.
     * <p>
     * Derived classes should call through to the base class for it to perform
     * the default menu handling.
     * 
     * @param item The menu item that was selected.
     * @return boolean Return false to allow normal menu processing to proceed,
     *         true to consume it here.
     * @see #onCreateOptionsMenu
     */
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    /**
     * This hook is called whenever the options menu is being closed (either by
     * the user canceling the menu with the back/menu button, or when an item is
     * selected).
     * 
     * @param menu The options menu as last shown or first initialized by
     *            onCreateOptionsMenu().
     */
    public void onOptionsMenuClosed(Menu menu) {
        super.onOptionsMenuClosed(menu);
    }

    /**
     * Called when a context menu for the {@code view} is about to be shown.
     * Unlike {@link #onCreateOptionsMenu}, this will be called every time the
     * context menu is about to be shown and should be populated for the view
     * (or item inside the view for {@link AdapterView} subclasses, this can be
     * found in the {@code menuInfo})).
     * <p>
     * Use {@link #onContextItemSelected(android.view.MenuItem)} to know when an
     * item has been selected.
     * <p>
     * The default implementation calls up to
     * {@link Activity#onCreateContextMenu Activity.onCreateContextMenu}, though
     * you can not call this implementation if you don't want that behavior.
     * <p>
     * It is not safe to hold onto the context menu after this method returns.
     * {@inheritDoc}
     */
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    /**
     * Registers a context menu to be shown for the given view (multiple views
     * can show the context menu). This method will set the
     * {@link OnCreateContextMenuListener} on the view to this fragment, so
     * {@link #onCreateContextMenu(ContextMenu, View, ContextMenuInfo)} will be
     * called when it is time to show the context menu.
     * 
     * @see #unregisterForContextMenu(View)
     * @param view The view that should show a context menu.
     */
    public void registerForContextMenu(View view) {
        super.registerForContextMenu(view);
    }

    /**
     * Prevents a context menu to be shown for the given view. This method will
     * remove the {@link OnCreateContextMenuListener} on the view.
     * 
     * @see #registerForContextMenu(View)
     * @param view The view that should stop showing a context menu.
     */
    public void unregisterForContextMenu(View view) {
        super.unregisterForContextMenu(view);
    }

    /**
     * This hook is called whenever an item in a context menu is selected. The
     * default implementation simply returns false to have the normal processing
     * happen (calling the item's Runnable or sending a message to its Handler
     * as appropriate). You can use this method for any items for which you
     * would like to do processing without those other facilities.
     * <p>
     * Use {@link MenuItem#getMenuInfo()} to get extra information set by the
     * View that added this menu item.
     * <p>
     * Derived classes should call through to the base class for it to perform
     * the default menu handling.
     * 
     * @param item The context menu item that was selected.
     * @return boolean Return false to allow normal context menu processing to
     *         proceed, true to consume it here.
     */
    public boolean onContextItemSelected(MenuItem item) {
        return super.onContextItemSelected(item);
    }

    /**
     * When custom transitions are used with Fragments, the enter transition
     * callback is called when this Fragment is attached or detached when not
     * popping the back stack.
     * 
     * @param callback Used to manipulate the shared element transitions on this
     *            Fragment when added not as a pop from the back stack.
     */
    public void setEnterSharedElementCallback(SharedElementCallback callback) {
        super.setEnterSharedElementCallback(callback);
    }

    /**
     * When custom transitions are used with Fragments, the exit transition
     * callback is called when this Fragment is attached or detached when
     * popping the back stack.
     * 
     * @param callback Used to manipulate the shared element transitions on this
     *            Fragment when added as a pop from the back stack.
     */
    public void setExitSharedElementCallback(SharedElementCallback callback) {
        super.setExitSharedElementCallback(callback);
    }

    /**
     * Sets the Transition that will be used to move Views into the initial
     * scene. The entering Views will be those that are regular Views or
     * ViewGroups that have {@link ViewGroup#isTransitionGroup} return true.
     * Typical Transitions will extend {@link android.transition.Visibility} as
     * entering is governed by changing visibility from {@link View#INVISIBLE}
     * to {@link View#VISIBLE}. If <code>transition</code> is null, entering
     * Views will remain unaffected.
     * 
     * @param transition The Transition to use to move Views into the initial
     *            Scene.
     */
    public void setEnterTransition(Object transition) {
        super.setEnterTransition(transition);
    }

    /**
     * Returns the Transition that will be used to move Views into the initial
     * scene. The entering Views will be those that are regular Views or
     * ViewGroups that have {@link ViewGroup#isTransitionGroup} return true.
     * Typical Transitions will extend {@link android.transition.Visibility} as
     * entering is governed by changing visibility from {@link View#INVISIBLE}
     * to {@link View#VISIBLE}.
     * 
     * @return the Transition to use to move Views into the initial Scene.
     */
    public Object getEnterTransition() {
        return super.getEnterTransition();
    }

    /**
     * Sets the Transition that will be used to move Views out of the scene when
     * the Fragment is preparing to be removed, hidden, or detached because of
     * popping the back stack. The exiting Views will be those that are regular
     * Views or ViewGroups that have {@link ViewGroup#isTransitionGroup} return
     * true. Typical Transitions will extend
     * {@link android.transition.Visibility} as entering is governed by changing
     * visibility from {@link View#VISIBLE} to {@link View#INVISIBLE}. If
     * <code>transition</code> is null, entering Views will remain unaffected.
     * If nothing is set, the default will be to use the same value as set in
     * {@link #setEnterTransition(Object)}.
     * 
     * @param transition The Transition to use to move Views out of the Scene
     *            when the Fragment is preparing to close.
     *            <code>transition</code> must be an
     *            android.transition.Transition.
     */
    public void setReturnTransition(Object transition) {
        super.setReturnTransition(transition);
    }

    /**
     * Returns the Transition that will be used to move Views out of the scene
     * when the Fragment is preparing to be removed, hidden, or detached because
     * of popping the back stack. The exiting Views will be those that are
     * regular Views or ViewGroups that have {@link ViewGroup#isTransitionGroup}
     * return true. Typical Transitions will extend
     * {@link android.transition.Visibility} as entering is governed by changing
     * visibility from {@link View#VISIBLE} to {@link View#INVISIBLE}. If
     * <code>transition</code> is null, entering Views will remain unaffected.
     * 
     * @return the Transition to use to move Views out of the Scene when the
     *         Fragment is preparing to close.
     */
    public Object getReturnTransition() {
        return super.getReturnTransition();
    }

    /**
     * Sets the Transition that will be used to move Views out of the scene when
     * the fragment is removed, hidden, or detached when not popping the back
     * stack. The exiting Views will be those that are regular Views or
     * ViewGroups that have {@link ViewGroup#isTransitionGroup} return true.
     * Typical Transitions will extend {@link android.transition.Visibility} as
     * exiting is governed by changing visibility from {@link View#VISIBLE} to
     * {@link View#INVISIBLE}. If transition is null, the views will remain
     * unaffected.
     * 
     * @param transition The Transition to use to move Views out of the Scene
     *            when the Fragment is being closed not due to popping the back
     *            stack. <code>transition</code> must be an
     *            android.transition.Transition.
     */
    public void setExitTransition(Object transition) {
        super.setExitTransition(transition);
    }

    /**
     * Returns the Transition that will be used to move Views out of the scene
     * when the fragment is removed, hidden, or detached when not popping the
     * back stack. The exiting Views will be those that are regular Views or
     * ViewGroups that have {@link ViewGroup#isTransitionGroup} return true.
     * Typical Transitions will extend {@link android.transition.Visibility} as
     * exiting is governed by changing visibility from {@link View#VISIBLE} to
     * {@link View#INVISIBLE}. If transition is null, the views will remain
     * unaffected.
     * 
     * @return the Transition to use to move Views out of the Scene when the
     *         Fragment is being closed not due to popping the back stack.
     */
    public Object getExitTransition() {
        return super.getExitTransition();
    }

    /**
     * Sets the Transition that will be used to move Views in to the scene when
     * returning due to popping a back stack. The entering Views will be those
     * that are regular Views or ViewGroups that have
     * {@link ViewGroup#isTransitionGroup} return true. Typical Transitions will
     * extend {@link android.transition.Visibility} as exiting is governed by
     * changing visibility from {@link View#VISIBLE} to {@link View#INVISIBLE}.
     * If transition is null, the views will remain unaffected. If nothing is
     * set, the default will be to use the same transition as
     * {@link #setExitTransition(Object)}.
     * 
     * @param transition The Transition to use to move Views into the scene when
     *            reentering from a previously-started Activity.
     *            <code>transition</code> must be an
     *            android.transition.Transition.
     */
    public void setReenterTransition(Object transition) {
        super.setReenterTransition(transition);
    }

    /**
     * Returns the Transition that will be used to move Views in to the scene
     * when returning due to popping a back stack. The entering Views will be
     * those that are regular Views or ViewGroups that have
     * {@link ViewGroup#isTransitionGroup} return true. Typical Transitions will
     * extend {@link android.transition.Visibility} as exiting is governed by
     * changing visibility from {@link View#VISIBLE} to {@link View#INVISIBLE}.
     * If transition is null, the views will remain unaffected. If nothing is
     * set, the default will be to use the same transition as
     * {@link #setExitTransition(Object)}.
     * 
     * @return the Transition to use to move Views into the scene when
     *         reentering from a previously-started Activity.
     */
    public Object getReenterTransition() {
        return super.getReenterTransition();
    }

    /**
     * Sets the Transition that will be used for shared elements transferred
     * into the content Scene. Typical Transitions will affect size and
     * location, such as {@link android.transition.ChangeBounds}. A null value
     * will cause transferred shared elements to blink to the final position.
     * 
     * @param transition The Transition to use for shared elements transferred
     *            into the content Scene. <code>transition</code> must be an
     *            android.transition.Transition.
     */
    public void setSharedElementEnterTransition(Object transition) {
        super.setSharedElementEnterTransition(transition);
    }

    /**
     * Returns the Transition that will be used for shared elements transferred
     * into the content Scene. Typical Transitions will affect size and
     * location, such as {@link android.transition.ChangeBounds}. A null value
     * will cause transferred shared elements to blink to the final position.
     * 
     * @return The Transition to use for shared elements transferred into the
     *         content Scene.
     */
    public Object getSharedElementEnterTransition() {
        return super.getSharedElementEnterTransition();
    }

    /**
     * Sets the Transition that will be used for shared elements transferred
     * back during a pop of the back stack. This Transition acts in the leaving
     * Fragment. Typical Transitions will affect size and location, such as
     * {@link android.transition.ChangeBounds}. A null value will cause
     * transferred shared elements to blink to the final position. If no value
     * is set, the default will be to use the same value as
     * {@link #setSharedElementEnterTransition(Object)}.
     * 
     * @param transition The Transition to use for shared elements transferred
     *            out of the content Scene. <code>transition</code> must be an
     *            android.transition.Transition.
     */
    public void setSharedElementReturnTransition(Object transition) {
        super.setSharedElementReturnTransition(transition);
    }

    /**
     * Return the Transition that will be used for shared elements transferred
     * back during a pop of the back stack. This Transition acts in the leaving
     * Fragment. Typical Transitions will affect size and location, such as
     * {@link android.transition.ChangeBounds}. A null value will cause
     * transferred shared elements to blink to the final position. If no value
     * is set, the default will be to use the same value as
     * {@link #setSharedElementEnterTransition(Object)}.
     * 
     * @return The Transition to use for shared elements transferred out of the
     *         content Scene.
     */
    public Object getSharedElementReturnTransition() {
        return super.getSharedElementReturnTransition();
    }

    /**
     * Sets whether the the exit transition and enter transition overlap or not.
     * When true, the enter transition will start as soon as possible. When
     * false, the enter transition will wait until the exit transition completes
     * before starting.
     * 
     * @param allow true to start the enter transition when possible or false to
     *            wait until the exiting transition completes.
     */
    public void setAllowEnterTransitionOverlap(boolean allow) {
        super.setAllowEnterTransitionOverlap(allow);
    }

    /**
     * Returns whether the the exit transition and enter transition overlap or
     * not. When true, the enter transition will start as soon as possible. When
     * false, the enter transition will wait until the exit transition completes
     * before starting.
     * 
     * @return true when the enter transition should start as soon as possible
     *         or false to when it should wait until the exiting transition
     *         completes.
     */
    public boolean getAllowEnterTransitionOverlap() {
        return super.getAllowEnterTransitionOverlap();
    }

    /**
     * Sets whether the the return transition and reenter transition overlap or
     * not. When true, the reenter transition will start as soon as possible.
     * When false, the reenter transition will wait until the return transition
     * completes before starting.
     * 
     * @param allow true to start the reenter transition when possible or false
     *            to wait until the return transition completes.
     */
    public void setAllowReturnTransitionOverlap(boolean allow) {
        super.setAllowReturnTransitionOverlap(allow);
    }

    /**
     * Returns whether the the return transition and reenter transition overlap
     * or not. When true, the reenter transition will start as soon as possible.
     * When false, the reenter transition will wait until the return transition
     * completes before starting.
     * 
     * @return true to start the reenter transition when possible or false to
     *         wait until the return transition completes.
     */
    public boolean getAllowReturnTransitionOverlap() {
        return super.getAllowReturnTransitionOverlap();
    }

    /**
     * Print the Fragments's state into the given stream.
     * 
     * @param prefix Text to print at the front of each line.
     * @param fd The raw file descriptor that the dump is being sent to.
     * @param writer The PrintWriter to which you should dump your state. This
     *            will be closed for you after you return.
     * @param args additional arguments to the dump request.
     */
    public void dump(String prefix, FileDescriptor fd, PrintWriter writer, String[] args) {
        super.dump(prefix, fd, writer, args);
    }

    /**
     * Fragment拦截Activity的按键事件
     * 
     * @param keyCode
     * @param event
     * @return
     */
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return false;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}

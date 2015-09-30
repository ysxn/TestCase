
package com.codezyw.common;

import java.io.FileDescriptor;
import java.io.PrintWriter;

import android.app.Activity;
import android.app.ActivityOptions;
import android.app.Dialog;
import android.app.SearchManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
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
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnCreateContextMenuListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.accessibility.AccessibilityEvent;
import android.widget.AdapterView;

/**
 * Base class for activities that want to use the support-based
 * {@link android.support.v4.app.Fragment} and
 * {@link android.support.v4.content.Loader} APIs.
 * <p>
 * When using this class as opposed to new platform's built-in fragment and
 * loader support, you must use the {@link #getSupportFragmentManager()} and
 * {@link #getSupportLoaderManager()} methods respectively to access those
 * features.
 * <p class="note">
 * <strong>Note:</strong> If you want to implement an activity that includes an
 * <a href="{@docRoot}guide/topics/ui/actionbar.html">action bar</a>, you should
 * instead use the {@link android.support.v7.app.ActionBarActivity} class, which
 * is a subclass of this one, so allows you to use
 * {@link android.support.v4.app.Fragment} APIs on API level 7 and higher.
 * </p>
 * <p>
 * Known limitations:
 * </p>
 * <ul>
 * <li>
 * <p>
 * When using the <code>&lt;fragment></code> tag, this implementation can not
 * use the parent view's ID as the new fragment's ID. You must explicitly
 * specify an ID (or tag) in the <code>&lt;fragment></code>.
 * </p>
 * <li>
 * <p>
 * Prior to Honeycomb (3.0), an activity's state was saved before pausing.
 * Fragments are a significant amount of new state, and dynamic enough that one
 * often wants them to change between pausing and stopping. These classes throw
 * an exception if you try to change the fragment state after it has been saved,
 * to avoid accidental loss of UI state. However this is too restrictive prior
 * to Honeycomb, where the state is saved before pausing. To address this, when
 * running on platforms prior to Honeycomb an exception will not be thrown if
 * you change fragments between the state save and the activity being stopped.
 * This means that in some cases if the activity is restored from its last saved
 * state, this may be a snapshot slightly before what the user last saw.
 * </p>
 * </ul>
 * <p>
 * <a
 * href="http://www.cnblogs.com/chuanstone/p/4672096.html">http://www.cnblogs.
 * com/chuanstone/p/4672096.html</a>
 * <p>
 * 
 * <pre>
 * 自从在Android 3.0引入Fragment以来，它被使用的频率也随之增多。Fragment带来的好处不言而喻，解决了不同屏幕分辨率的动态和灵活UI设计。但是在Activity管理多个Fragment中，通常会遇到这些问题：
 * 1、Fragment的状态保存
 * 2、Fragment的重影
 * 当然，这些问题也一直出现我的开发过程中，虽然有时候通过各种手段也能解决一些问题，但是总是同时完美解决这两个问题。近来因为项目需要，查阅了很多官方资料（Android官方资料也慢慢有中文资料了，我大Google果然是Don't be evil，扯远了~~），终于彻底解决了这些问题。
 * 设备：nexus 5
 * 条件：
 * 1、打开“不保留活动”（开发者选项里，主要用于模拟Activity被及时回收）
 * 2、关闭“不保留活动”（正常状态下）
 * 结果：目前没发现问题，由于设备有限，大家如果发现在其他设备上有问题，请在下方回帖！
 * 首先我先来解释下上面问题出现的原因：
 * 1、有时候，我们需要在多个Fragment间切换，并且保存每个Fragment的状态。官方的方法是使用replace()来替换Fragment，但是replace()的调用会导致Fragment的onCreteView()被调用，所以切换界面时会无法保存当前的状态。因此一般采用add()、hide()与show()配合，来达到保存Fragment的状态。以下为代码片段：
 * private void setTabSelection(int position) {
 *         //记录position
 *         this.position = position;
 *         //更改底部导航栏按钮状态
 *         changeButtonStatus(position);
 *         FragmentTransaction transaction = fragmentManager.beginTransaction();
 *         // 先隐藏掉所有的Fragment，以防止有多个Fragment显示在界面上的情况
 *         hideFragments(transaction);
 *         switch (position) {
 *             case TAB_HOME:
 *                 btnHomePager.setSelected(true);
 *                 btnShoppingCart.setSelected(false);
 *                 btnMine.setSelected(false);
 *                 if (homeFragment == null) {
 *                     homeFragment = new HomePagerFragment();
 *                     transaction.add(R.id.fragment_container, homeFragment);
 *                 } else {
 *                     transaction.show(homeFragment);
 *                 }
 *                 break;
 *             case TAB_SHOP:
 *                 btnHomePager.setSelected(false);
 *                 btnShoppingCart.setSelected(true);
 *                 btnMine.setSelected(false);
 *                 if (shoppingFragment == null) {
 *                     shoppingFragment = new ShoppingCartFragment();
 *                     transaction.add(R.id.fragment_container, shoppingFragment);
 *                 } else {
 *                     transaction.show(shoppingFragment);
 *                 }
 *                 break;
 *             case TAB_MINE:
 *                 btnHomePager.setSelected(false);
 *                 btnShoppingCart.setSelected(false);
 *                 btnMine.setSelected(true);
 *                 if (mineFragment == null) {
 *                     mineFragment = new MineFragment();
 *                     transaction.add(R.id.fragment_container, mineFragment);
 *                 } else {
 *                     transaction.show(mineFragment);
 *                 }
 *                 break;
 *         }
 *         transaction.commitAllowingStateLoss();
 *     }
 *  
 * 2、第二个问题的出现正是因为使用了Fragment的状态保存，当系统内存不足，Fragment的宿主Activity回收的时候，Fragment的实例并没有随之被回收。Activity被系统回收时，会主动调用onSaveInstance()方法来保存视图层（View Hierarchy），所以当Activity通过导航再次被重建时，之前被实例化过的Fragment依然会出现在Activity中，然而从上述代码中可以明显看出，再次重建了新的Fragment，综上这些因素导致了多个Fragment重叠在一起。
 * 我尝试了很多种方法去解决这个问题，比如：
 * 在onSaveInstance()里面去remove()所有非空的Fragment，然后在onRestoreInstanceState()中去再次按照问题一的方式创建Activity。当我处于打开“不保留活动”的时候，效果非常令人满意，然而当我关闭“不保留活动”的时候，问题却出现了。当转跳到其他Activity、打开多任务窗口、使用Home回到主屏幕再返回时，发现根本没有Fragment了，一篇空白。
 * 于是跟踪下去，我调查了onSaveInstanceState()与onRestoreInstanceState()这两个方法。原本以为只有在系统因为内存回收Activity时才会调用的onSaveInstanceState()，居然在转跳到其他Activity、打开多任务窗口、使用Home回到主屏幕这些操作中也被调用，然而onRestoreInstanceState()并没有在再次回到Activity时被调用。而且我在onResume()发现之前的Fragment只是被移除，并不是空，所以就算你在onResume()中执行问题一中创建的Fragment的方法，同样无济于事。所以通过remove()宣告失败。
 * 接着通过调查资料发现Activity中的onSaveInstanceState()里面有一句super.onRestoreInstanceState(savedInstanceState)，Google对于这句话的解释是“Always call the superclass so it can save the view hierarchy state”，大概意思是“总是执行这句代码来调用父类去保存视图层的状态”。其实到这里大家也就明白了，就是因为这句话导致了重影的出现，于是我删除了这句话，然后onCreate()与onRestoreInstanceState()中同时使用问题一中的创建Fragment方法，然后再通过保存切换的状态，发现结果非常完美。代码如下：
 *     //记录Fragment的位置
 *     private int position = 0;
 * 
 *     @Override
 *     protected void onCreate(Bundle savedInstanceState) {
 *         super.onCreate(savedInstanceState);
 *         setContentView(R.layout.activity_index);
 * 
 *         setTabSelection(position);
 *     }
 * 
 *     @Override
 *     protected void onRestoreInstanceState(Bundle savedInstanceState) {
 *         position = savedInstanceState.getInt("position");
 *         setTabSelection(position);
 *         super.onRestoreInstanceState(savedInstanceState);
 *     }
 * 
 *     @Override
 *     protected void onSaveInstanceState(Bundle outState) {
 *         //记录当前的position
 *         outState.putInt("position", position);
 *     }
 * 
 * 记录于此，希望能帮助到一些正遇到这种问题的朋友！
 * </pre>
 */
public class BaseFragmentActivity extends FragmentActivity {

    public static final int FRAMELAYOUT_ID = Integer.MAX_VALUE;

    /** Return the intent that started this activity. */
    public Intent getIntent() {
        return super.getIntent();
    }

    /**
     * Change the intent returned by {@link #getIntent}. This holds a reference
     * to the given intent; it does not copy it. Often used in conjunction with
     * {@link #onNewIntent}.
     * 
     * @param newIntent The new Intent object to return from getIntent
     * @see #getIntent
     * @see #onNewIntent
     */
    public void setIntent(Intent newIntent) {
        super.setIntent(newIntent);
    }

    /** Retrieve the window manager for showing custom windows. */
    public WindowManager getWindowManager() {
        return super.getWindowManager();
    }

    /**
     * Retrieve the current {@link android.view.Window} for the activity. This
     * can be used to directly access parts of the Window API that are not
     * available through Activity/Screen.
     * 
     * @return Window The current window, or null if the activity is not visual.
     */
    public Window getWindow() {
        return super.getWindow();
    }

    /**
     * Calls {@link android.view.Window#getCurrentFocus} on the Window of this
     * Activity to return the currently focused view.
     * 
     * @return View The current View with focus or null.
     * @see #getWindow
     * @see android.view.Window#getCurrentFocus
     */
    @Nullable
    public View getCurrentFocus() {
        return super.getCurrentFocus();
    }

    /**
     * Called when the activity is starting. This is where most initialization
     * should go: calling {@link #setContentView(int)} to inflate the activity's
     * UI, using {@link #findViewById} to programmatically interact with widgets
     * in the UI, calling
     * {@link #managedQuery(android.net.Uri , String[], String, String[], String)}
     * to retrieve cursors for data being displayed, etc.
     * <p>
     * You can call {@link #finish} from within this function, in which case
     * onDestroy() will be immediately called without any of the rest of the
     * activity lifecycle ({@link #onStart}, {@link #onResume}, {@link #onPause}
     * , etc) executing.
     * <p>
     * <em>Derived classes must call through to the super class's
     * implementation of this method.  If they do not, an exception will be
     * thrown.</em>
     * </p>
     * Perform initialization of all fragments and loaders.
     * <p>
     * 
     * @param savedInstanceState If the activity is being re-initialized after
     *            previously being shut down then this Bundle contains the data
     *            it most recently supplied in {@link #onSaveInstanceState}.
     *            <b><i>Note: Otherwise it is null.</i></b>
     * @see #onStart
     * @see #onSaveInstanceState
     * @see #onRestoreInstanceState
     * @see #onPostCreate
     */
    protected void onCreate(@Nullable
    Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ExitHelper.getInstance().addActivity(this);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
    }

    /**
     * This method is called after {@link #onStart} when the activity is being
     * re-initialized from a previously saved state, given here in
     * <var>savedInstanceState</var>. Most implementations will simply use
     * {@link #onCreate} to restore their state, but it is sometimes convenient
     * to do it here after all of the initialization has been done or to allow
     * subclasses to decide whether to use your default implementation. The
     * default implementation of this method performs a restore of any view
     * state that had previously been frozen by {@link #onSaveInstanceState}.
     * <p>
     * This method is called between {@link #onStart} and {@link #onPostCreate}.
     * 
     * @param savedInstanceState the data most recently supplied in
     *            {@link #onSaveInstanceState}.
     * @see #onCreate
     * @see #onPostCreate
     * @see #onResume
     * @see #onSaveInstanceState
     */
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    /**
     * Called when activity start-up is complete (after {@link #onStart} and
     * {@link #onRestoreInstanceState} have been called). Applications will
     * generally not implement this method; it is intended for system classes to
     * do final initialization after application code has run.
     * <p>
     * <em>Derived classes must call through to the super class's
     * implementation of this method.  If they do not, an exception will be
     * thrown.</em>
     * </p>
     * 
     * @param savedInstanceState If the activity is being re-initialized after
     *            previously being shut down then this Bundle contains the data
     *            it most recently supplied in {@link #onSaveInstanceState}.
     *            <b><i>Note: Otherwise it is null.</i></b>
     * @see #onCreate
     */
    protected void onPostCreate(@Nullable
    Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
    }

    /**
     * Called after {@link #onCreate} &mdash; or after {@link #onRestart} when
     * the activity had been stopped, but is now again being displayed to the
     * user. It will be followed by {@link #onResume}.
     * <p>
     * <em>Derived classes must call through to the super class's
     * implementation of this method.  If they do not, an exception will be
     * thrown.</em>
     * </p>
     * Dispatch onStart() to all fragments. Ensure any created loaders are now
     * started.<br>
     * 
     * @see #onCreate
     * @see #onStop
     * @see #onResume
     */
    protected void onStart() {
        super.onStart();
    }

    /**
     * Called after {@link #onStop} when the current activity is being
     * re-displayed to the user (the user has navigated back to it). It will be
     * followed by {@link #onStart} and then {@link #onResume}.
     * <p>
     * For activities that are using raw {@link Cursor} objects (instead of
     * creating them through
     * {@link #managedQuery(android.net.Uri , String[], String, String[], String)}
     * , this is usually the place where the cursor should be requeried (because
     * you had deactivated it in {@link #onStop}.
     * <p>
     * <em>Derived classes must call through to the super class's
     * implementation of this method.  If they do not, an exception will be
     * thrown.</em>
     * </p>
     * 
     * @see #onStop
     * @see #onStart
     * @see #onResume
     */
    protected void onRestart() {
        super.onRestart();
    }

    /**
     * Called after {@link #onRestoreInstanceState}, {@link #onRestart}, or
     * {@link #onPause}, for your activity to start interacting with the user.
     * This is a good place to begin animations, open exclusive-access devices
     * (such as the camera), etc.
     * <p>
     * Keep in mind that onResume is not the best indicator that your activity
     * is visible to the user; a system window such as the keyguard may be in
     * front. Use {@link #onWindowFocusChanged} to know for certain that your
     * activity is visible to the user (for example, to resume a game).
     * <p>
     * <em>Derived classes must call through to the super class's
     * implementation of this method.  If they do not, an exception will be
     * thrown.</em>
     * </p>
     * Dispatch onResume() to fragments. Note that for better inter-operation
     * with older versions of the platform, at the point of this call the
     * fragments attached to the activity are <em>not</em> resumed. This means
     * that in some cases the previous state may still be saved, not allowing
     * fragment transactions that modify the state. To correctly interact with
     * fragments in their proper state, you should instead override
     * {@link #onResumeFragments()}.
     * <p>
     * 
     * @see #onRestoreInstanceState
     * @see #onRestart
     * @see #onPostResume
     * @see #onPause
     */
    protected void onResume() {
        super.onResume();
    }

    /**
     * Called when activity resume is complete (after {@link #onResume} has been
     * called). Applications will generally not implement this method; it is
     * intended for system classes to do final setup after application resume
     * code has run.
     * <p>
     * <em>Derived classes must call through to the super class's
     * implementation of this method.  If they do not, an exception will be
     * thrown.</em>
     * </p>
     * Dispatch onResume() to fragments.
     * <p>
     * 
     * @see #onResume
     */
    protected void onPostResume() {
        super.onPostResume();
    }

    /**
     * This is called for activities that set launchMode to "singleTop" in their
     * package, or if a client used the {@link Intent#FLAG_ACTIVITY_SINGLE_TOP}
     * flag when calling {@link #startActivity}. In either case, when the
     * activity is re-launched while at the top of the activity stack instead of
     * a new instance of the activity being started, onNewIntent() will be
     * called on the existing instance with the Intent that was used to
     * re-launch it.
     * <p>
     * An activity will always be paused before receiving a new intent, so you
     * can count on {@link #onResume} being called after this method.
     * <p>
     * Note that {@link #getIntent} still returns the original Intent. You can
     * use {@link #setIntent} to update it to this new Intent.
     * <p>
     * Handle onNewIntent() to inform the fragment manager that the state is not
     * saved. If you are handling new intents and may be making changes to the
     * fragment state, you want to be sure to call through to the super-class
     * here first. Otherwise, if your state is saved but the activity is not
     * stopped, you could get an onNewIntent() call which happens before
     * onResume() and trying to perform fragment operations at that point will
     * throw IllegalStateException because the fragment manager thinks the state
     * is still saved.
     * <p>
     * 
     * @param intent The new intent that was started for the activity.
     * @see #getIntent
     * @see #setIntent
     * @see #onResume
     */
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
    }

    /**
     * Called to retrieve per-instance state from an activity before being
     * killed so that the state can be restored in {@link #onCreate} or
     * {@link #onRestoreInstanceState} (the {@link Bundle} populated by this
     * method will be passed to both).
     * <p>
     * This method is called before an activity may be killed so that when it
     * comes back some time in the future it can restore its state. For example,
     * if activity B is launched in front of activity A, and at some point
     * activity A is killed to reclaim resources, activity A will have a chance
     * to save the current state of its user interface via this method so that
     * when the user returns to activity A, the state of the user interface can
     * be restored via {@link #onCreate} or {@link #onRestoreInstanceState}.
     * <p>
     * Do not confuse this method with activity lifecycle callbacks such as
     * {@link #onPause}, which is always called when an activity is being placed
     * in the background or on its way to destruction, or {@link #onStop} which
     * is called before destruction. One example of when {@link #onPause} and
     * {@link #onStop} is called and not this method is when a user navigates
     * back from activity B to activity A: there is no need to call
     * {@link #onSaveInstanceState} on B because that particular instance will
     * never be restored, so the system avoids calling it. An example when
     * {@link #onPause} is called and not {@link #onSaveInstanceState} is when
     * activity B is launched in front of activity A: the system may avoid
     * calling {@link #onSaveInstanceState} on activity A if it isn't killed
     * during the lifetime of B since the state of the user interface of A will
     * stay intact.
     * <p>
     * The default implementation takes care of most of the UI per-instance
     * state for you by calling {@link android.view.View#onSaveInstanceState()}
     * on each view in the hierarchy that has an id, and by saving the id of the
     * currently focused view (all of which is restored by the default
     * implementation of {@link #onRestoreInstanceState}). If you override this
     * method to save additional information not captured by each individual
     * view, you will likely want to call through to the default implementation,
     * otherwise be prepared to save all of the state of each view yourself.
     * <p>
     * If called, this method will occur before {@link #onStop}. There are no
     * guarantees about whether it will occur before or after {@link #onPause}.
     * <p>
     * Save all appropriate fragment state.
     * <p>
     * 
     * @param outState Bundle in which to place your saved state.
     * @see #onCreate
     * @see #onRestoreInstanceState
     * @see #onPause
     */
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    /**
     * Called as part of the activity lifecycle when an activity is going into
     * the background, but has not (yet) been killed. The counterpart to
     * {@link #onResume}.
     * <p>
     * When activity B is launched in front of activity A, this callback will be
     * invoked on A. B will not be created until A's {@link #onPause} returns,
     * so be sure to not do anything lengthy here.
     * <p>
     * This callback is mostly used for saving any persistent state the activity
     * is editing, to present a "edit in place" model to the user and making
     * sure nothing is lost if there are not enough resources to start the new
     * activity without first killing this one. This is also a good place to do
     * things like stop animations and other things that consume a noticeable
     * amount of CPU in order to make the switch to the next activity as fast as
     * possible, or to close resources that are exclusive access such as the
     * camera.
     * <p>
     * In situations where the system needs more memory it may kill paused
     * processes to reclaim resources. Because of this, you should be sure that
     * all of your state is saved by the time you return from this function. In
     * general {@link #onSaveInstanceState} is used to save per-instance state
     * in the activity and this method is used to store global persistent data
     * (in content providers, files, etc.)
     * <p>
     * After receiving this call you will usually receive a following call to
     * {@link #onStop} (after the next activity has been resumed and displayed),
     * however in some cases there will be a direct call back to
     * {@link #onResume} without going through the stopped state.
     * <p>
     * <em>Derived classes must call through to the super class's
     * implementation of this method.  If they do not, an exception will be
     * thrown.</em>
     * </p>
     * Dispatch onPause() to fragments.
     * <p>
     * 
     * @see #onResume
     * @see #onSaveInstanceState
     * @see #onStop
     */
    protected void onPause() {
        super.onPause();
    }

    /**
     * Called as part of the activity lifecycle when an activity is about to go
     * into the background as the result of user choice. For example, when the
     * user presses the Home key, {@link #onUserLeaveHint} will be called, but
     * when an incoming phone call causes the in-call Activity to be
     * automatically brought to the foreground, {@link #onUserLeaveHint} will
     * not be called on the activity being interrupted. In cases when it is
     * invoked, this method is called right before the activity's
     * {@link #onPause} callback.
     * <p>
     * This callback and {@link #onUserInteraction} are intended to help
     * activities manage status bar notifications intelligently; specifically,
     * for helping activities determine the proper time to cancel a notfication.
     * 
     * @see #onUserInteraction()
     */
    protected void onUserLeaveHint() {
        super.onUserLeaveHint();
    }

    /**
     * Generate a new thumbnail for this activity. This method is called before
     * pausing the activity, and should draw into <var>outBitmap</var> the
     * imagery for the desired thumbnail in the dimensions of that bitmap. It
     * can use the given <var>canvas</var>, which is configured to draw into the
     * bitmap, for rendering if desired.
     * <p>
     * The default implementation returns fails and does not draw a thumbnail;
     * this will result in the platform creating its own thumbnail if needed.
     * 
     * @param outBitmap The bitmap to contain the thumbnail.
     * @param canvas Can be used to render into the bitmap.
     * @return Return true if you have drawn into the bitmap; otherwise after
     *         you return it will be filled with a default thumbnail.
     * @see #onCreateDescription
     * @see #onSaveInstanceState
     * @see #onPause
     */
    public boolean onCreateThumbnail(Bitmap outBitmap, Canvas canvas) {
        return super.onCreateThumbnail(outBitmap, canvas);
    }

    /**
     * Generate a new description for this activity. This method is called
     * before pausing the activity and can, if desired, return some textual
     * description of its current state to be displayed to the user.
     * <p>
     * The default implementation returns null, which will cause you to inherit
     * the description from the previous activity. If all activities return
     * null, generally the label of the top activity will be used as the
     * description.
     * 
     * @return A description of what the user is doing. It should be short and
     *         sweet (only a few words).
     * @see #onCreateThumbnail
     * @see #onSaveInstanceState
     * @see #onPause
     */
    @Nullable
    public CharSequence onCreateDescription() {
        return super.onCreateDescription();
    }

    /**
     * Called when you are no longer visible to the user. You will next receive
     * either {@link #onRestart}, {@link #onDestroy}, or nothing, depending on
     * later user activity.
     * <p>
     * Note that this method may never be called, in low memory situations where
     * the system does not have enough memory to keep your activity's process
     * running after its {@link #onPause} method is called.
     * <p>
     * <em>Derived classes must call through to the super class's
     * implementation of this method.  If they do not, an exception will be
     * thrown.</em>
     * </p>
     * Dispatch onStop() to all fragments. Ensure all loaders are stopped. <br>
     * 
     * @see #onRestart
     * @see #onResume
     * @see #onSaveInstanceState
     * @see #onDestroy
     */
    protected void onStop() {
        super.onStop();
    }

    /**
     * Perform any final cleanup before an activity is destroyed. This can
     * happen either because the activity is finishing (someone called
     * {@link #finish} on it, or because the system is temporarily destroying
     * this instance of the activity to save space. You can distinguish between
     * these two scenarios with the {@link #isFinishing} method.
     * <p>
     * <em>Note: do not count on this method being called as a place for
     * saving data! For example, if an activity is editing data in a content
     * provider, those edits should be committed in either {@link #onPause} or
     * {@link #onSaveInstanceState}, not here.</em> This method is usually
     * implemented to free resources like threads that are associated with an
     * activity, so that a destroyed activity does not leave such things around
     * while the rest of its application is still running. There are situations
     * where the system will simply kill the activity's hosting process without
     * calling this method (or any others) in it, so it should not be used to do
     * things that are intended to remain around after the process goes away.
     * <p>
     * <em>Derived classes must call through to the super class's
     * implementation of this method.  If they do not, an exception will be
     * thrown.</em>
     * </p>
     * Destroy all fragments and loaders.
     * <p>
     * 
     * @see #onPause
     * @see #onStop
     * @see #finish
     * @see #isFinishing
     */
    protected void onDestroy() {
        super.onDestroy();
        ExitHelper.getInstance().delActivity(this);
    }

    /**
     * Called by the system when the device configuration changes while your
     * activity is running. Note that this will <em>only</em> be called if you
     * have selected configurations you would like to handle with the
     * {@link android.R.attr#configChanges} attribute in your manifest. If any
     * configuration change occurs that is not selected to be reported by that
     * attribute, then instead of reporting it the system will stop and restart
     * the activity (to have it launched with the new configuration).
     * <p>
     * At the time that this function has been called, your Resources object
     * will have been updated to return resource values matching the new
     * configuration.
     * <p>
     * Dispatch configuration change to all fragments.
     * <p>
     * 
     * @param newConfig The new device configuration.
     */
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    /**
     * If this activity is being destroyed because it can not handle a
     * configuration parameter being changed (and thus its
     * {@link #onConfigurationChanged(Configuration)} method is <em>not</em>
     * being called), then you can use this method to discover the set of
     * changes that have occurred while in the process of being destroyed. Note
     * that there is no guarantee that these will be accurate (other changes
     * could have happened at any time), so you should only use this as an
     * optimization hint.
     * 
     * @return Returns a bit field of the configuration parameters that are
     *         changing, as defined by the
     *         {@link android.content.res.Configuration} class.
     */
    public int getChangingConfigurations() {
        return super.getChangingConfigurations();
    }

    /**
     * Retrieve the non-configuration instance data that was previously returned
     * by {@link #onRetainNonConfigurationInstance()}. This will be available
     * from the initial {@link #onCreate} and {@link #onStart} calls to the new
     * instance, allowing you to extract any useful dynamic state from the
     * previous instance.
     * <p>
     * Note that the data you retrieve here should <em>only</em> be used as an
     * optimization for handling configuration changes. You should always be
     * able to handle getting a null pointer back, and an activity must still be
     * able to restore itself to its previous state (through the normal
     * {@link #onSaveInstanceState(Bundle)} mechanism) even if this function
     * returns null.
     * 
     * @return Returns the object previously returned by
     *         {@link #onRetainNonConfigurationInstance()}.
     * @deprecated Use the new {@link Fragment} API
     *             {@link Fragment#setRetainInstance(boolean)} instead; this is
     *             also available on older platforms through the Android
     *             compatibility package.
     */
    @Nullable
    @Deprecated
    public Object getLastNonConfigurationInstance() {
        return super.getLastNonConfigurationInstance();
    }

    /**
     * Dispatch onLowMemory() to all fragments.
     */
    public void onLowMemory() {
        super.onLowMemory();
    }

    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
    }

    /**
     * This method allows the activity to take care of managing the given
     * {@link Cursor}'s lifecycle for you based on the activity's lifecycle.
     * That is, when the activity is stopped it will automatically call
     * {@link Cursor#deactivate} on the given Cursor, and when it is later
     * restarted it will call {@link Cursor#requery} for you. When the activity
     * is destroyed, all managed Cursors will be closed automatically.
     * <em>If you are targeting {@link android.os.Build.VERSION_CODES#HONEYCOMB}
     * or later, consider instead using {@link LoaderManager} instead, available
     * via {@link #getLoaderManager()}.</em>
     * <p>
     * <strong>Warning:</strong> Do not call {@link Cursor#close()} on cursor
     * obtained from {@link #managedQuery}, because the activity will do that
     * for you at the appropriate time. However, if you call
     * {@link #stopManagingCursor} on a cursor from a managed query, the system
     * <em>will not</em> automatically close the cursor and, in that case, you
     * must call {@link Cursor#close()}.
     * </p>
     * 
     * @param c The Cursor to be managed.
     * @see #managedQuery(android.net.Uri , String[], String, String[], String)
     * @see #stopManagingCursor
     * @deprecated Use the new {@link android.content.CursorLoader} class with
     *             {@link LoaderManager} instead; this is also available on
     *             older platforms through the Android compatibility package.
     */
    @Deprecated
    public void startManagingCursor(Cursor c) {
        super.startManagingCursor(c);
    }

    /**
     * Given a Cursor that was previously given to {@link #startManagingCursor},
     * stop the activity's management of that cursor.
     * <p>
     * <strong>Warning:</strong> After calling this method on a cursor from a
     * managed query, the system <em>will not</em> automatically close the
     * cursor and you must call {@link Cursor#close()}.
     * </p>
     * 
     * @param c The Cursor that was being managed.
     * @see #startManagingCursor
     * @deprecated Use the new {@link android.content.CursorLoader} class with
     *             {@link LoaderManager} instead; this is also available on
     *             older platforms through the Android compatibility package.
     */
    @Deprecated
    public void stopManagingCursor(Cursor c) {
        super.stopManagingCursor(c);
    }

    /**
     * Finds a view that was identified by the id attribute from the XML that
     * was processed in {@link #onCreate}.
     * 
     * @return The view if found or null otherwise.
     */
    public View findViewById(int id) {
        return super.findViewById(id);
    }

    /**
     * Set the activity content from a layout resource. The resource will be
     * inflated, adding all top-level views to the activity.
     * 
     * @param layoutResID Resource ID to be inflated.
     * @see #setContentView(android.view.View)
     * @see #setContentView(android.view.View,
     *      android.view.ViewGroup.LayoutParams)
     */
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
    }

    /**
     * Set the activity content to an explicit view. This view is placed
     * directly into the activity's view hierarchy. It can itself be a complex
     * view hierarchy. When calling this method, the layout parameters of the
     * specified view are ignored. Both the width and the height of the view are
     * set by default to {@link ViewGroup.LayoutParams#MATCH_PARENT}. To use
     * your own layout parameters, invoke
     * {@link #setContentView(android.view.View, android.view.ViewGroup.LayoutParams)}
     * instead.
     * 
     * @param view The desired content to display.
     * @see #setContentView(int)
     * @see #setContentView(android.view.View,
     *      android.view.ViewGroup.LayoutParams)
     */
    public void setContentView(View view) {
        super.setContentView(view);
    }

    /**
     * Set the activity content to an explicit view. This view is placed
     * directly into the activity's view hierarchy. It can itself be a complex
     * view hierarchy.
     * 
     * @param view The desired content to display.
     * @param params Layout parameters for the view.
     * @see #setContentView(android.view.View)
     * @see #setContentView(int)
     */
    public void setContentView(View view, ViewGroup.LayoutParams params) {
        super.setContentView(view, params);
    }

    /**
     * Add an additional content view to the activity. Added after any existing
     * ones in the activity -- existing views are NOT removed.
     * 
     * @param view The desired content to display.
     * @param params Layout parameters for the view.
     */
    public void addContentView(View view, ViewGroup.LayoutParams params) {
        super.addContentView(view, params);
    }

    /**
     * Sets whether this activity is finished when touched outside its window's
     * bounds.
     */
    public void setFinishOnTouchOutside(boolean finish) {
        super.setFinishOnTouchOutside(finish);
    }

    /**
     * Called when a key was pressed down and not handled by any of the views
     * inside of the activity. So, for example, key presses while the cursor is
     * inside a TextView will not trigger the event (unless it is a navigation
     * to another object) because TextView handles its own key presses.
     * <p>
     * If the focused view didn't want this event, this method is called.
     * <p>
     * The default implementation takes care of {@link KeyEvent#KEYCODE_BACK} by
     * calling {@link #onBackPressed()}, though the behavior varies based on the
     * application compatibility mode: for
     * {@link android.os.Build.VERSION_CODES#ECLAIR} or later applications, it
     * will set up the dispatch to call {@link #onKeyUp} where the action will
     * be performed; for earlier applications, it will perform the action
     * immediately in on-down, as those versions of the platform behaved.
     * <p>
     * Other additional default key handling may be performed if configured with
     * {@link #setDefaultKeyMode}.
     * <p>
     * Take care of calling onBackPressed() for pre-Eclair platforms.
     * <p>
     * 
     * @return Return <code>true</code> to prevent this event from being
     *         propagated further, or <code>false</code> to indicate that you
     *         have not handled this event and it should continue to be
     *         propagated.
     * @see #onKeyUp
     * @see android.view.KeyEvent
     */
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return super.onKeyDown(keyCode, event);
    }

    /**
     * Default implementation of
     * {@link KeyEvent.Callback#onKeyLongPress(int, KeyEvent)
     * KeyEvent.Callback.onKeyLongPress()}: always returns false (doesn't handle
     * the event).
     */
    public boolean onKeyLongPress(int keyCode, KeyEvent event) {
        return super.onKeyLongPress(keyCode, event);
    }

    /**
     * Called when a key was released and not handled by any of the views inside
     * of the activity. So, for example, key presses while the cursor is inside
     * a TextView will not trigger the event (unless it is a navigation to
     * another object) because TextView handles its own key presses.
     * <p>
     * The default implementation handles KEYCODE_BACK to stop the activity and
     * go back.
     * 
     * @return Return <code>true</code> to prevent this event from being
     *         propagated further, or <code>false</code> to indicate that you
     *         have not handled this event and it should continue to be
     *         propagated.
     * @see #onKeyDown
     * @see KeyEvent
     */
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        return super.onKeyUp(keyCode, event);
    }

    /**
     * Default implementation of
     * {@link KeyEvent.Callback#onKeyMultiple(int, int, KeyEvent)
     * KeyEvent.Callback.onKeyMultiple()}: always returns false (doesn't handle
     * the event).
     */
    public boolean onKeyMultiple(int keyCode, int repeatCount, KeyEvent event) {
        return super.onKeyMultiple(keyCode, repeatCount, event);
    }

    /**
     * Called when the activity has detected the user's press of the back key.
     * The default implementation simply finishes the current activity, but you
     * can override this to do whatever you want.
     * <p>
     * Take care of popping the fragment back stack or finishing the activity as
     * appropriate.
     * <p>
     */
    public void onBackPressed() {
        super.onBackPressed();
    }

    /**
     * Called when a key shortcut event is not handled by any of the views in
     * the Activity. Override this method to implement global key shortcuts for
     * the Activity. Key shortcuts can also be implemented by setting the
     * {@link MenuItem#setShortcut(char, char) shortcut} property of menu items.
     * 
     * @param keyCode The value in event.getKeyCode().
     * @param event Description of the key event.
     * @return True if the key shortcut was handled.
     */
    public boolean onKeyShortcut(int keyCode, KeyEvent event) {
        return super.onKeyShortcut(keyCode, event);
    }

    /**
     * Called when a touch screen event was not handled by any of the views
     * under it. This is most useful to process touch events that happen outside
     * of your window bounds, where there is no view to receive it.
     * 
     * @param event The touch screen event being processed.
     * @return Return true if you have consumed the event, false if you haven't.
     *         The default implementation always returns false.
     */
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }

    /**
     * Called when the trackball was moved and not handled by any of the views
     * inside of the activity. So, for example, if the trackball moves while
     * focus is on a button, you will receive a call here because buttons do not
     * normally do anything with trackball events. The call here happens
     * <em>before</em> trackball movements are converted to DPAD key events,
     * which then get sent back to the view hierarchy, and will be processed at
     * the point for things like focus navigation.
     * 
     * @param event The trackball event being processed.
     * @return Return true if you have consumed the event, false if you haven't.
     *         The default implementation always returns false.
     */
    public boolean onTrackballEvent(MotionEvent event) {
        return super.onTrackballEvent(event);
    }

    /**
     * Called when a generic motion event was not handled by any of the views
     * inside of the activity.
     * <p>
     * Generic motion events describe joystick movements, mouse hovers, track
     * pad touches, scroll wheel movements and other input events. The
     * {@link MotionEvent#getSource() source} of the motion event specifies the
     * class of input that was received. Implementations of this method must
     * examine the bits in the source before processing the event. The following
     * code example shows how this is done.
     * </p>
     * <p>
     * Generic motion events with source class
     * {@link android.view.InputDevice#SOURCE_CLASS_POINTER} are delivered to
     * the view under the pointer. All other generic motion events are delivered
     * to the focused view.
     * </p>
     * <p>
     * See {@link View#onGenericMotionEvent(MotionEvent)} for an example of how
     * to handle this event.
     * </p>
     * 
     * @param event The generic motion event being processed.
     * @return Return true if you have consumed the event, false if you haven't.
     *         The default implementation always returns false.
     */
    public boolean onGenericMotionEvent(MotionEvent event) {
        return super.onGenericMotionEvent(event);
    }

    /**
     * Called whenever a key, touch, or trackball event is dispatched to the
     * activity. Implement this method if you wish to know that the user has
     * interacted with the device in some way while your activity is running.
     * This callback and {@link #onUserLeaveHint} are intended to help
     * activities manage status bar notifications intelligently; specifically,
     * for helping activities determine the proper time to cancel a notfication.
     * <p>
     * All calls to your activity's {@link #onUserLeaveHint} callback will be
     * accompanied by calls to {@link #onUserInteraction}. This ensures that
     * your activity will be told of relevant user activity such as pulling down
     * the notification pane and touching an item there.
     * <p>
     * Note that this callback will be invoked for the touch down action that
     * begins a touch gesture, but may not be invoked for the touch-moved and
     * touch-up actions that follow.
     * 
     * @see #onUserLeaveHint()
     */
    public void onUserInteraction() {
        super.onUserInteraction();
    }

    public void onWindowAttributesChanged(WindowManager.LayoutParams params) {
        super.onWindowAttributesChanged(params);
    }

    public void onContentChanged() {
        super.onContentChanged();
    }

    /**
     * Called when the current {@link Window} of the activity gains or loses
     * focus. This is the best indicator of whether this activity is visible to
     * the user. The default implementation clears the key tracking state, so
     * should always be called.
     * <p>
     * Note that this provides information about global focus state, which is
     * managed independently of activity lifecycles. As such, while focus
     * changes will generally have some relation to lifecycle changes (an
     * activity that is stopped will not generally get window focus), you should
     * not rely on any particular order between the callbacks here and those in
     * the other lifecycle methods such as {@link #onResume}.
     * <p>
     * As a general rule, however, a resumed activity will have window focus...
     * unless it has displayed other dialogs or popups that take input focus, in
     * which case the activity itself will not have focus when the other windows
     * have it. Likewise, the system may display system-level windows (such as
     * the status bar notification panel or a system alert) which will
     * temporarily take window input focus without pausing the foreground
     * activity.
     * 
     * @param hasFocus Whether the window of this activity has focus.
     * @see #hasWindowFocus()
     * @see #onResume
     * @see View#onWindowFocusChanged(boolean)
     */
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
    }

    /**
     * Called when the main window associated with the activity has been
     * attached to the window manager. See {@link View#onAttachedToWindow()
     * View.onAttachedToWindow()} for more information.
     * 
     * @see View#onAttachedToWindow
     */
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
    }

    /**
     * Called when the main window associated with the activity has been
     * detached from the window manager. See {@link View#onDetachedFromWindow()
     * View.onDetachedFromWindow()} for more information.
     * 
     * @see View#onDetachedFromWindow
     */
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
    }

    /**
     * Returns true if this activity's <em>main</em> window currently has window
     * focus. Note that this is not the same as the view itself having focus.
     * 
     * @return True if this activity's main window currently has window focus.
     * @see #onWindowAttributesChanged(android.view.WindowManager.LayoutParams)
     */
    public boolean hasWindowFocus() {
        return super.hasWindowFocus();
    }

    /**
     * Called to process key events. You can override this to intercept all key
     * events before they are dispatched to the window. Be sure to call this
     * implementation for key events that should be handled normally.
     * 
     * @param event The key event.
     * @return boolean Return true if this event was consumed.
     */
    public boolean dispatchKeyEvent(KeyEvent event) {
        return super.dispatchKeyEvent(event);
    }

    /**
     * Called to process a key shortcut event. You can override this to
     * intercept all key shortcut events before they are dispatched to the
     * window. Be sure to call this implementation for key shortcut events that
     * should be handled normally.
     * 
     * @param event The key shortcut event.
     * @return True if this event was consumed.
     */
    public boolean dispatchKeyShortcutEvent(KeyEvent event) {
        return super.dispatchKeyShortcutEvent(event);
    }

    /**
     * Called to process touch screen events. You can override this to intercept
     * all touch screen events before they are dispatched to the window. Be sure
     * to call this implementation for touch screen events that should be
     * handled normally.
     * 
     * @param ev The touch screen event.
     * @return boolean Return true if this event was consumed.
     */
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return super.dispatchTouchEvent(ev);
    }

    /**
     * Called to process trackball events. You can override this to intercept
     * all trackball events before they are dispatched to the window. Be sure to
     * call this implementation for trackball events that should be handled
     * normally.
     * 
     * @param ev The trackball event.
     * @return boolean Return true if this event was consumed.
     */
    public boolean dispatchTrackballEvent(MotionEvent ev) {
        return super.dispatchTrackballEvent(ev);
    }

    /**
     * Called to process generic motion events. You can override this to
     * intercept all generic motion events before they are dispatched to the
     * window. Be sure to call this implementation for generic motion events
     * that should be handled normally.
     * 
     * @param ev The generic motion event.
     * @return boolean Return true if this event was consumed.
     */
    public boolean dispatchGenericMotionEvent(MotionEvent ev) {
        return super.dispatchGenericMotionEvent(ev);
    }

    public boolean dispatchPopulateAccessibilityEvent(AccessibilityEvent event) {
        return super.dispatchPopulateAccessibilityEvent(event);
    }

    /**
     * Default implementation of
     * {@link android.view.Window.Callback#onCreatePanelView} for activities.
     * This simply returns null so that all panel sub-windows will have the
     * default menu behavior.
     */
    @Nullable
    public View onCreatePanelView(int featureId) {
        return super.onCreatePanelView(featureId);
    }

    /**
     * Default implementation of
     * {@link android.view.Window.Callback#onCreatePanelMenu} for activities.
     * This calls through to the new {@link #onCreateOptionsMenu} method for the
     * {@link android.view.Window#FEATURE_OPTIONS_PANEL} panel, so that
     * subclasses of Activity don't need to deal with feature codes.
     * <p>
     * Dispatch to Fragment.onCreateOptionsMenu().
     * <p>
     */
    public boolean onCreatePanelMenu(int featureId, Menu menu) {
        return super.onCreatePanelMenu(featureId, menu);
    }

    /**
     * Default implementation of
     * {@link android.view.Window.Callback#onPreparePanel} for activities. This
     * calls through to the new {@link #onPrepareOptionsMenu} method for the
     * {@link android.view.Window#FEATURE_OPTIONS_PANEL} panel, so that
     * subclasses of Activity don't need to deal with feature codes.
     * <p>
     * Dispatch onPrepareOptionsMenu() to fragments.<br>
     */
    public boolean onPreparePanel(int featureId, View view, Menu menu) {
        return super.onPreparePanel(featureId, view, menu);
    }

    /**
     * {@inheritDoc}
     * 
     * @return The default implementation returns true.
     */
    public boolean onMenuOpened(int featureId, Menu menu) {
        return super.onMenuOpened(featureId, menu);
    }

    /**
     * Default implementation of
     * {@link android.view.Window.Callback#onMenuItemSelected} for activities.
     * This calls through to the new {@link #onOptionsItemSelected} method for
     * the {@link android.view.Window#FEATURE_OPTIONS_PANEL} panel, so that
     * subclasses of Activity don't need to deal with feature codes.
     * <p>
     * Dispatch context and options menu to fragments.
     * <p>
     */
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        return super.onMenuItemSelected(featureId, item);
    }

    /**
     * Default implementation of
     * {@link android.view.Window.Callback#onPanelClosed(int, Menu)} for
     * activities. This calls through to {@link #onOptionsMenuClosed(Menu)}
     * method for the {@link android.view.Window#FEATURE_OPTIONS_PANEL} panel,
     * so that subclasses of Activity don't need to deal with feature codes. For
     * context menus ({@link Window#FEATURE_CONTEXT_MENU}), the
     * {@link #onContextMenuClosed(Menu)} will be called.
     * <p>
     * Call onOptionsMenuClosed() on fragments.
     * <p>
     */
    public void onPanelClosed(int featureId, Menu menu) {
        super.onPanelClosed(featureId, menu);
    }

    /**
     * Declare that the options menu has changed, so should be recreated. The
     * {@link #onCreateOptionsMenu(Menu)} method will be called the next time it
     * needs to be displayed.
     */
    public void invalidateOptionsMenu() {
        super.invalidateOptionsMenu();
    }

    /**
     * Initialize the contents of the Activity's standard options menu. You
     * should place your menu items in to <var>menu</var>.
     * <p>
     * This is only called once, the first time the options menu is displayed.
     * To update the menu every time it is displayed, see
     * {@link #onPrepareOptionsMenu}.
     * <p>
     * The default implementation populates the menu with standard system menu
     * items. These are placed in the {@link Menu#CATEGORY_SYSTEM} group so that
     * they will be correctly ordered with application-defined menu items.
     * Deriving classes should always call through to the base implementation.
     * <p>
     * You can safely hold on to <var>menu</var> (and any items created from
     * it), making modifications to it as desired, until the next time
     * onCreateOptionsMenu() is called.
     * <p>
     * When you add items to the menu, you can implement the Activity's
     * {@link #onOptionsItemSelected} method to handle them there.
     * 
     * @param menu The options menu in which you place your items.
     * @return You must return true for the menu to be displayed; if you return
     *         false it will not be shown.
     * @see #onPrepareOptionsMenu
     * @see #onOptionsItemSelected
     */
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    /**
     * Prepare the Screen's standard options menu to be displayed. This is
     * called right before the menu is shown, every time it is shown. You can
     * use this method to efficiently enable/disable items or otherwise
     * dynamically modify the contents.
     * <p>
     * The default implementation updates the system menu items based on the
     * activity's state. Deriving classes should always call through to the base
     * class implementation.
     * 
     * @param menu The options menu as last shown or first initialized by
     *            onCreateOptionsMenu().
     * @return You must return true for the menu to be displayed; if you return
     *         false it will not be shown.
     * @see #onCreateOptionsMenu
     */
    public boolean onPrepareOptionsMenu(Menu menu) {
        return super.onPrepareOptionsMenu(menu);
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
     * </p>
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
     * This method is called whenever the user chooses to navigate Up within
     * your application's activity hierarchy from the action bar.
     * <p>
     * If the attribute {@link android.R.attr#parentActivityName
     * parentActivityName} was specified in the manifest for this activity or an
     * activity-alias to it, default Up navigation will be handled
     * automatically. If any activity along the parent chain requires extra
     * Intent arguments, the Activity subclass should override the method
     * {@link #onPrepareNavigateUpTaskStack(TaskStackBuilder)} to supply those
     * arguments.
     * </p>
     * <p>
     * See <a href="{@docRoot}
     * guide/topics/fundamentals/tasks-and-back-stack.html">Tasks and Back
     * Stack</a> from the developer guide and <a href="{@docRoot}
     * design/patterns/navigation.html">Navigation</a> from the design guide for
     * more information about navigating within your app.
     * </p>
     * <p>
     * See the {@link TaskStackBuilder} class and the Activity methods
     * {@link #getParentActivityIntent()}, {@link #shouldUpRecreateTask(Intent)}
     * , and {@link #navigateUpTo(Intent)} for help implementing custom Up
     * navigation. The AppNavigation sample application in the Android SDK is
     * also available for reference.
     * </p>
     * 
     * @return true if Up navigation completed successfully and this Activity
     *         was finished, false otherwise.
     */
    public boolean onNavigateUp() {
        return super.onNavigateUp();
    }

    /**
     * This is called when a child activity of this one attempts to navigate up.
     * The default implementation simply calls onNavigateUp() on this activity
     * (the parent).
     * 
     * @param child The activity making the call.
     */
    public boolean onNavigateUpFromChild(Activity child) {
        return super.onNavigateUpFromChild(child);
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
     * Programmatically opens the options menu. If the options menu is already
     * open, this method does nothing.
     */
    public void openOptionsMenu() {
        super.openOptionsMenu();
    }

    /**
     * Progammatically closes the options menu. If the options menu is already
     * closed, this method does nothing.
     */
    public void closeOptionsMenu() {
        super.closeOptionsMenu();
    }

    /**
     * Called when a context menu for the {@code view} is about to be shown.
     * Unlike {@link #onCreateOptionsMenu(Menu)}, this will be called every time
     * the context menu is about to be shown and should be populated for the
     * view (or item inside the view for {@link AdapterView} subclasses, this
     * can be found in the {@code menuInfo})).
     * <p>
     * Use {@link #onContextItemSelected(android.view.MenuItem)} to know when an
     * item has been selected.
     * <p>
     * It is not safe to hold onto the context menu after this method returns.
     */
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    /**
     * Registers a context menu to be shown for the given view (multiple views
     * can show the context menu). This method will set the
     * {@link OnCreateContextMenuListener} on the view to this activity, so
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
     * Programmatically opens the context menu for a particular {@code view}.
     * The {@code view} should have been added via
     * {@link #registerForContextMenu(View)}.
     * 
     * @param view The view to show the context menu for.
     */
    public void openContextMenu(View view) {
        super.openContextMenu(view);
    }

    /**
     * Programmatically closes the most recently opened context menu, if
     * showing.
     */
    public void closeContextMenu() {
        super.closeContextMenu();
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
     * This hook is called whenever the context menu is being closed (either by
     * the user canceling the menu with the back/menu button, or when an item is
     * selected).
     * 
     * @param menu The context menu that is being closed.
     */
    public void onContextMenuClosed(Menu menu) {
        super.onContextMenuClosed(menu);
    }

    /**
     * @deprecated Old no-arguments version of
     *             {@link #onCreateDialog(int, Bundle)}.
     */
    @Deprecated
    protected Dialog onCreateDialog(int id) {
        return super.onCreateDialog(id);
    }

    /**
     * Callback for creating dialogs that are managed (saved and restored) for
     * you by the activity. The default implementation calls through to
     * {@link #onCreateDialog(int)} for compatibility.
     * <em>If you are targeting {@link android.os.Build.VERSION_CODES#HONEYCOMB}
     * or later, consider instead using a {@link DialogFragment} instead.</em>
     * <p>
     * If you use {@link #showDialog(int)}, the activity will call through to
     * this method the first time, and hang onto it thereafter. Any dialog that
     * is created by this method will automatically be saved and restored for
     * you, including whether it is showing.
     * <p>
     * If you would like the activity to manage saving and restoring dialogs for
     * you, you should override this method and handle any ids that are passed
     * to {@link #showDialog}.
     * <p>
     * If you would like an opportunity to prepare your dialog before it is
     * shown, override {@link #onPrepareDialog(int, Dialog, Bundle)}.
     * 
     * @param id The id of the dialog.
     * @param args The dialog arguments provided to
     *            {@link #showDialog(int, Bundle)}.
     * @return The dialog. If you return null, the dialog will not be created.
     * @see #onPrepareDialog(int, Dialog, Bundle)
     * @see #showDialog(int, Bundle)
     * @see #dismissDialog(int)
     * @see #removeDialog(int)
     * @deprecated Use the new {@link DialogFragment} class with
     *             {@link FragmentManager} instead; this is also available on
     *             older platforms through the Android compatibility package.
     */
    @Nullable
    @Deprecated
    protected Dialog onCreateDialog(int id, Bundle args) {
        return super.onCreateDialog(id, args);
    }

    /**
     * @deprecated Old no-arguments version of
     *             {@link #onPrepareDialog(int, Dialog, Bundle)}.
     */
    @Deprecated
    protected void onPrepareDialog(int id, Dialog dialog) {
        super.onPrepareDialog(id, dialog);
    }

    /**
     * Provides an opportunity to prepare a managed dialog before it is being
     * shown. The default implementation calls through to
     * {@link #onPrepareDialog(int, Dialog)} for compatibility.
     * <p>
     * Override this if you need to update a managed dialog based on the state
     * of the application each time it is shown. For example, a time picker
     * dialog might want to be updated with the current time. You should call
     * through to the superclass's implementation. The default implementation
     * will set this Activity as the owner activity on the Dialog.
     * 
     * @param id The id of the managed dialog.
     * @param dialog The dialog.
     * @param args The dialog arguments provided to
     *            {@link #showDialog(int, Bundle)}.
     * @see #onCreateDialog(int, Bundle)
     * @see #showDialog(int)
     * @see #dismissDialog(int)
     * @see #removeDialog(int)
     * @deprecated Use the new {@link DialogFragment} class with
     *             {@link FragmentManager} instead; this is also available on
     *             older platforms through the Android compatibility package.
     */
    @Deprecated
    protected void onPrepareDialog(int id, Dialog dialog, Bundle args) {
        super.onPrepareDialog(id, dialog, args);
    }

    /**
     * This hook is called when the user signals the desire to start a search.
     * <p>
     * You can use this function as a simple way to launch the search UI, in
     * response to a menu item, search button, or other widgets within your
     * activity. Unless overidden, calling this function is the same as calling
     * {@link #startSearch startSearch(null, false, null, false)}, which
     * launches search for the current activity as specified in its manifest,
     * see {@link SearchManager}.
     * <p>
     * You can override this function to force global search, e.g. in response
     * to a dedicated search key, or to block search entirely (by simply
     * returning false).
     * <p>
     * Note: when running in a {@link Configuration#UI_MODE_TYPE_TELEVISION},
     * the default implementation changes to simply return false and you must
     * supply your own custom implementation if you want to support search.
     * </p>
     * 
     * @return Returns {@code true} if search launched, and {@code false} if the
     *         activity does not respond to search. The default implementation
     *         always returns {@code true}, except when in
     *         {@link Configuration#UI_MODE_TYPE_TELEVISION} mode where it
     *         returns false.
     * @see android.app.SearchManager
     */
    public boolean onSearchRequested() {
        return super.onSearchRequested();
    }

    /**
     * This hook is called to launch the search UI.
     * <p>
     * It is typically called from onSearchRequested(), either directly from
     * Activity.onSearchRequested() or from an overridden version in any given
     * Activity. If your goal is simply to activate search, it is preferred to
     * call onSearchRequested(), which may have been overridden elsewhere in
     * your Activity. If your goal is to inject specific data such as context
     * data, it is preferred to <i>override</i> onSearchRequested(), so that any
     * callers to it will benefit from the override.
     * 
     * @param initialQuery Any non-null non-empty string will be inserted as
     *            pre-entered text in the search query box.
     * @param selectInitialQuery If true, the initial query will be preselected,
     *            which means that any further typing will replace it. This is
     *            useful for cases where an entire pre-formed query is being
     *            inserted. If false, the selection point will be placed at the
     *            end of the inserted query. This is useful when the inserted
     *            query is text that the user entered, and the user would expect
     *            to be able to keep typing. <i>This parameter is only
     *            meaningful if initialQuery is a non-empty string.</i>
     * @param appSearchData An application can insert application-specific
     *            context here, in order to improve quality or specificity of
     *            its own searches. This data will be returned with SEARCH
     *            intent(s). Null if no extra data is required.
     * @param globalSearch If false, this will only launch the search that has
     *            been specifically defined by the application (which is usually
     *            defined as a local search). If no default search is defined in
     *            the current application or activity, global search will be
     *            launched. If true, this will always launch a platform-global
     *            (e.g. web-based) search instead.
     * @see android.app.SearchManager
     * @see #onSearchRequested
     */
    public void startSearch(@Nullable
    String initialQuery, boolean selectInitialQuery,
            @Nullable
            Bundle appSearchData, boolean globalSearch) {
        super.startSearch(initialQuery, selectInitialQuery, appSearchData, globalSearch);
    }

    /**
     * Similar to {@link #startSearch}, but actually fires off the search query
     * after invoking the search dialog. Made available for testing purposes.
     * 
     * @param query The query to trigger. If empty, the request will be ignored.
     * @param appSearchData An application can insert application-specific
     *            context here, in order to improve quality or specificity of
     *            its own searches. This data will be returned with SEARCH
     *            intent(s). Null if no extra data is required.
     */
    public void triggerSearch(String query, @Nullable
    Bundle appSearchData) {
        super.triggerSearch(query, appSearchData);
    }

    /**
     * Request that key events come to this activity. Use this if your activity
     * has no views with focus, but the activity still wants a chance to process
     * key events.
     * 
     * @see android.view.Window#takeKeyEvents
     */
    public void takeKeyEvents(boolean get) {
        super.takeKeyEvents(get);
    }

    /**
     * Convenience for calling {@link android.view.Window#getLayoutInflater}.
     */
    @NonNull
    public LayoutInflater getLayoutInflater() {
        return super.getLayoutInflater();
    }

    /**
     * Returns a {@link MenuInflater} with this context.
     */
    @NonNull
    public MenuInflater getMenuInflater() {
        return super.getMenuInflater();
    }

    @Override
    protected void onApplyThemeResource(Resources.Theme theme, int resid,
            boolean first) {
        super.onApplyThemeResource(theme, resid, first);
    }

    /**
     * Same as calling {@link #startActivityForResult(Intent, int, Bundle)} with
     * no options.
     * <p>
     * Modifies the standard behavior to allow results to be delivered to
     * fragments. This imposes a restriction that requestCode be <= 0xffff.
     * <p>
     * 
     * @param intent The intent to start.
     * @param requestCode If >= 0, this code will be returned in
     *            onActivityResult() when the activity exits.
     * @throws android.content.ActivityNotFoundException
     * @see #startActivity
     */
    public void startActivityForResult(Intent intent, int requestCode) {
        super.startActivityForResult(intent, requestCode);
    }

    /**
     * Launch an activity for which you would like a result when it finished.
     * When this activity exits, your onActivityResult() method will be called
     * with the given requestCode. Using a negative requestCode is the same as
     * calling {@link #startActivity} (the activity is not launched as a
     * sub-activity).
     * <p>
     * Note that this method should only be used with Intent protocols that are
     * defined to return a result. In other protocols (such as
     * {@link Intent#ACTION_MAIN} or {@link Intent#ACTION_VIEW}), you may not
     * get the result when you expect. For example, if the activity you are
     * launching uses the singleTask launch mode, it will not run in your task
     * and thus you will immediately receive a cancel result.
     * <p>
     * As a special case, if you call startActivityForResult() with a
     * requestCode >= 0 during the initial onCreate(Bundle
     * savedInstanceState)/onResume() of your activity, then your window will
     * not be displayed until a result is returned back from the started
     * activity. This is to avoid visible flickering when redirecting to another
     * activity.
     * <p>
     * This method throws {@link android.content.ActivityNotFoundException} if
     * there was no Activity found to run the given Intent.
     * 
     * @param intent The intent to start.
     * @param requestCode If >= 0, this code will be returned in
     *            onActivityResult() when the activity exits.
     * @param options Additional options for how the Activity should be started.
     *            See
     *            {@link android.content.Context#startActivity(Intent, Bundle)
     *            Context.startActivity(Intent, Bundle)} for more details.
     * @throws android.content.ActivityNotFoundException
     * @see #startActivity
     */
    public void startActivityForResult(Intent intent, int requestCode, @Nullable
    Bundle options) {
        super.startActivityForResult(intent, requestCode, options);
    }

    /**
     * Same as calling
     * {@link #startIntentSenderForResult(IntentSender, int, Intent, int, int, int, Bundle)}
     * with no options.
     * 
     * @param intent The IntentSender to launch.
     * @param requestCode If >= 0, this code will be returned in
     *            onActivityResult() when the activity exits.
     * @param fillInIntent If non-null, this will be provided as the intent
     *            parameter to {@link IntentSender#sendIntent}.
     * @param flagsMask Intent flags in the original IntentSender that you would
     *            like to change.
     * @param flagsValues Desired values for any bits set in
     *            <var>flagsMask</var>
     * @param extraFlags Always set to 0.
     */
    public void startIntentSenderForResult(IntentSender intent, int requestCode,
            @Nullable
            Intent fillInIntent, int flagsMask, int flagsValues, int extraFlags)
            throws IntentSender.SendIntentException {
        super.startIntentSenderForResult(intent, requestCode, fillInIntent, flagsMask,
                flagsValues, extraFlags);
    }

    /**
     * Like {@link #startActivityForResult(Intent, int)}, but allowing you to
     * use a IntentSender to describe the activity to be started. If the
     * IntentSender is for an activity, that activity will be started as if you
     * had called the regular {@link #startActivityForResult(Intent, int)} here;
     * otherwise, its associated action will be executed (such as sending a
     * broadcast) as if you had called {@link IntentSender#sendIntent
     * IntentSender.sendIntent} on it.
     * 
     * @param intent The IntentSender to launch.
     * @param requestCode If >= 0, this code will be returned in
     *            onActivityResult() when the activity exits.
     * @param fillInIntent If non-null, this will be provided as the intent
     *            parameter to {@link IntentSender#sendIntent}.
     * @param flagsMask Intent flags in the original IntentSender that you would
     *            like to change.
     * @param flagsValues Desired values for any bits set in
     *            <var>flagsMask</var>
     * @param extraFlags Always set to 0.
     * @param options Additional options for how the Activity should be started.
     *            See
     *            {@link android.content.Context#startActivity(Intent, Bundle)
     *            Context.startActivity(Intent, Bundle)} for more details. If
     *            options have also been supplied by the IntentSender, options
     *            given here will override any that conflict with those given by
     *            the IntentSender.
     */
    public void startIntentSenderForResult(IntentSender intent, int requestCode,
            @Nullable
            Intent fillInIntent, int flagsMask, int flagsValues, int extraFlags,
            Bundle options) throws IntentSender.SendIntentException {
        super.startIntentSenderForResult(intent, requestCode, fillInIntent, flagsMask, flagsValues, extraFlags, options);
    }

    /**
     * Same as {@link #startActivity(Intent, Bundle)} with no options specified.
     * 
     * @param intent The intent to start.
     * @throws android.content.ActivityNotFoundException
     * @see {@link #startActivity(Intent, Bundle)}
     * @see #startActivityForResult
     */
    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
    }

    /**
     * Launch a new activity. You will not receive any information about when
     * the activity exits. This implementation overrides the base version,
     * providing information about the activity performing the launch. Because
     * of this additional information, the {@link Intent#FLAG_ACTIVITY_NEW_TASK}
     * launch flag is not required; if not specified, the new activity will be
     * added to the task of the caller.
     * <p>
     * This method throws {@link android.content.ActivityNotFoundException} if
     * there was no Activity found to run the given Intent.
     * 
     * @param intent The intent to start.
     * @param options Additional options for how the Activity should be started.
     *            See
     *            {@link android.content.Context#startActivity(Intent, Bundle)
     *            Context.startActivity(Intent, Bundle)} for more details.
     * @throws android.content.ActivityNotFoundException
     * @see {@link #startActivity(Intent)}
     * @see #startActivityForResult
     */
    @Override
    public void startActivity(Intent intent, @Nullable
    Bundle options) {
        super.startActivity(intent, options);
    }

    /**
     * Same as {@link #startActivities(Intent[], Bundle)} with no options
     * specified.
     * 
     * @param intents The intents to start.
     * @throws android.content.ActivityNotFoundException
     * @see {@link #startActivities(Intent[], Bundle)}
     * @see #startActivityForResult
     */
    @Override
    public void startActivities(Intent[] intents) {
        super.startActivities(intents);
    }

    /**
     * Launch a new activity. You will not receive any information about when
     * the activity exits. This implementation overrides the base version,
     * providing information about the activity performing the launch. Because
     * of this additional information, the {@link Intent#FLAG_ACTIVITY_NEW_TASK}
     * launch flag is not required; if not specified, the new activity will be
     * added to the task of the caller.
     * <p>
     * This method throws {@link android.content.ActivityNotFoundException} if
     * there was no Activity found to run the given Intent.
     * 
     * @param intents The intents to start.
     * @param options Additional options for how the Activity should be started.
     *            See
     *            {@link android.content.Context#startActivity(Intent, Bundle)
     *            Context.startActivity(Intent, Bundle)} for more details.
     * @throws android.content.ActivityNotFoundException
     * @see {@link #startActivities(Intent[])}
     * @see #startActivityForResult
     */
    @Override
    public void startActivities(Intent[] intents, @Nullable
    Bundle options) {
        super.startActivities(intents, options);
    }

    /**
     * Same as calling
     * {@link #startIntentSender(IntentSender, Intent, int, int, int, Bundle)}
     * with no options.
     * 
     * @param intent The IntentSender to launch.
     * @param fillInIntent If non-null, this will be provided as the intent
     *            parameter to {@link IntentSender#sendIntent}.
     * @param flagsMask Intent flags in the original IntentSender that you would
     *            like to change.
     * @param flagsValues Desired values for any bits set in
     *            <var>flagsMask</var>
     * @param extraFlags Always set to 0.
     */
    public void startIntentSender(IntentSender intent,
            @Nullable
            Intent fillInIntent, int flagsMask, int flagsValues, int extraFlags)
            throws IntentSender.SendIntentException {
        super.startIntentSender(intent, fillInIntent, flagsMask, flagsValues,
                extraFlags);
    }

    /**
     * Like {@link #startActivity(Intent, Bundle)}, but taking a IntentSender to
     * start; see
     * {@link #startIntentSenderForResult(IntentSender, int, Intent, int, int, int, Bundle)}
     * for more information.
     * 
     * @param intent The IntentSender to launch.
     * @param fillInIntent If non-null, this will be provided as the intent
     *            parameter to {@link IntentSender#sendIntent}.
     * @param flagsMask Intent flags in the original IntentSender that you would
     *            like to change.
     * @param flagsValues Desired values for any bits set in
     *            <var>flagsMask</var>
     * @param extraFlags Always set to 0.
     * @param options Additional options for how the Activity should be started.
     *            See
     *            {@link android.content.Context#startActivity(Intent, Bundle)
     *            Context.startActivity(Intent, Bundle)} for more details. If
     *            options have also been supplied by the IntentSender, options
     *            given here will override any that conflict with those given by
     *            the IntentSender.
     */
    public void startIntentSender(IntentSender intent,
            @Nullable
            Intent fillInIntent, int flagsMask, int flagsValues, int extraFlags,
            Bundle options) throws IntentSender.SendIntentException {
        super.startIntentSender(intent, fillInIntent, flagsMask, flagsValues, extraFlags, options);
    }

    /**
     * Same as calling {@link #startActivityIfNeeded(Intent, int, Bundle)} with
     * no options.
     * 
     * @param intent The intent to start.
     * @param requestCode If >= 0, this code will be returned in
     *            onActivityResult() when the activity exits, as described in
     *            {@link #startActivityForResult}.
     * @return If a new activity was launched then true is returned; otherwise
     *         false is returned and you must handle the Intent yourself.
     * @see #startActivity
     * @see #startActivityForResult
     */
    public boolean startActivityIfNeeded(@NonNull
    Intent intent, int requestCode) {
        return super.startActivityIfNeeded(intent, requestCode);
    }

    /**
     * A special variation to launch an activity only if a new activity instance
     * is needed to handle the given Intent. In other words, this is just like
     * {@link #startActivityForResult(Intent, int)} except: if you are using the
     * {@link Intent#FLAG_ACTIVITY_SINGLE_TOP} flag, or singleTask or singleTop
     * {@link android.R.styleable#AndroidManifestActivity_launchMode launchMode}
     * , and the activity that handles <var>intent</var> is the same as your
     * currently running activity, then a new instance is not needed. In this
     * case, instead of the normal behavior of calling {@link #onNewIntent} this
     * function will return and you can handle the Intent yourself.
     * <p>
     * This function can only be called from a top-level activity; if it is
     * called from a child activity, a runtime exception will be thrown.
     * 
     * @param intent The intent to start.
     * @param requestCode If >= 0, this code will be returned in
     *            onActivityResult() when the activity exits, as described in
     *            {@link #startActivityForResult}.
     * @param options Additional options for how the Activity should be started.
     *            See
     *            {@link android.content.Context#startActivity(Intent, Bundle)
     *            Context.startActivity(Intent, Bundle)} for more details.
     * @return If a new activity was launched then true is returned; otherwise
     *         false is returned and you must handle the Intent yourself.
     * @see #startActivity
     * @see #startActivityForResult
     */
    public boolean startActivityIfNeeded(@NonNull
    Intent intent, int requestCode,
            @Nullable
            Bundle options) {
        return super.startActivityIfNeeded(intent, requestCode, options);
    }

    /**
     * Same as calling {@link #startNextMatchingActivity(Intent, Bundle)} with
     * no options.
     * 
     * @param intent The intent to dispatch to the next activity. For correct
     *            behavior, this must be the same as the Intent that started
     *            your own activity; the only changes you can make are to the
     *            extras inside of it.
     * @return Returns a boolean indicating whether there was another Activity
     *         to start: true if there was a next activity to start, false if
     *         there wasn't. In general, if true is returned you will then want
     *         to call finish() on yourself.
     */
    public boolean startNextMatchingActivity(@NonNull
    Intent intent) {
        return super.startNextMatchingActivity(intent);
    }

    /**
     * Special version of starting an activity, for use when you are replacing
     * other activity components. You can use this to hand the Intent off to the
     * next Activity that can handle it. You typically call this in
     * {@link #onCreate} with the Intent returned by {@link #getIntent}.
     * 
     * @param intent The intent to dispatch to the next activity. For correct
     *            behavior, this must be the same as the Intent that started
     *            your own activity; the only changes you can make are to the
     *            extras inside of it.
     * @param options Additional options for how the Activity should be started.
     *            See
     *            {@link android.content.Context#startActivity(Intent, Bundle)
     *            Context.startActivity(Intent, Bundle)} for more details.
     * @return Returns a boolean indicating whether there was another Activity
     *         to start: true if there was a next activity to start, false if
     *         there wasn't. In general, if true is returned you will then want
     *         to call finish() on yourself.
     */
    public boolean startNextMatchingActivity(@NonNull
    Intent intent, @Nullable
    Bundle options) {
        return super.startNextMatchingActivity(intent, options);
    }

    /**
     * Same as calling
     * {@link #startActivityFromChild(Activity, Intent, int, Bundle)} with no
     * options.
     * 
     * @param child The activity making the call.
     * @param intent The intent to start.
     * @param requestCode Reply request code. < 0 if reply is not requested.
     * @throws android.content.ActivityNotFoundException
     * @see #startActivity
     * @see #startActivityForResult
     */
    public void startActivityFromChild(@NonNull
    Activity child, Intent intent,
            int requestCode) {
        super.startActivityFromChild(child, intent, requestCode);
    }

    /**
     * This is called when a child activity of this one calls its
     * {@link #startActivity} or {@link #startActivityForResult} method.
     * <p>
     * This method throws {@link android.content.ActivityNotFoundException} if
     * there was no Activity found to run the given Intent.
     * 
     * @param child The activity making the call.
     * @param intent The intent to start.
     * @param requestCode Reply request code. < 0 if reply is not requested.
     * @param options Additional options for how the Activity should be started.
     *            See
     *            {@link android.content.Context#startActivity(Intent, Bundle)
     *            Context.startActivity(Intent, Bundle)} for more details.
     * @throws android.content.ActivityNotFoundException
     * @see #startActivity
     * @see #startActivityForResult
     */
    public void startActivityFromChild(@NonNull
    Activity child, Intent intent,
            int requestCode, @Nullable
            Bundle options) {
        super.startActivityFromChild(child, intent, requestCode, options);
    }

    /**
     * Same as calling
     * {@link #startIntentSenderFromChild(Activity, IntentSender, int, Intent, int, int, int, Bundle)}
     * with no options.
     */
    public void startIntentSenderFromChild(Activity child, IntentSender intent,
            int requestCode, Intent fillInIntent, int flagsMask, int flagsValues,
            int extraFlags)
            throws IntentSender.SendIntentException {
        super.startIntentSenderFromChild(child, intent, requestCode, fillInIntent, flagsMask, flagsValues, extraFlags);
    }

    /**
     * Like {@link #startActivityFromChild(Activity, Intent, int)}, but taking a
     * IntentSender; see
     * {@link #startIntentSenderForResult(IntentSender, int, Intent, int, int, int)}
     * for more information.
     */
    public void startIntentSenderFromChild(Activity child, IntentSender intent,
            int requestCode, Intent fillInIntent, int flagsMask, int flagsValues,
            int extraFlags, @Nullable
            Bundle options)
            throws IntentSender.SendIntentException {
        super.startIntentSenderFromChild(child, intent, requestCode, fillInIntent, flagsMask, flagsValues, extraFlags, options);
    }

/**
     * Call immediately after one of the flavors of {@link #startActivity(Intent)}
     * or {@link #finish} to specify an explicit transition animation to
     * perform next.
     *
     * <p>As of {@link android.os.Build.VERSION_CODES#JELLY_BEAN} an alternative
     * to using this with starting activities is to supply the desired animation
     * information through a {@link ActivityOptions} bundle to
     * {@link #startActivity(Intent, Bundle) or a related function.  This allows
     * you to specify a custom animation even when starting an activity from
     * outside the context of the current top activity.
     *
     * @param enterAnim A resource ID of the animation resource to use for
     * the incoming activity.  Use 0 for no animation.
     * @param exitAnim A resource ID of the animation resource to use for
     * the outgoing activity.  Use 0 for no animation.
     */
    public void overridePendingTransition(int enterAnim, int exitAnim) {
        super.overridePendingTransition(enterAnim, exitAnim);
    }

    /**
     * Return the name of the package that invoked this activity. This is who
     * the data in {@link #setResult setResult()} will be sent to. You can use
     * this information to validate that the recipient is allowed to receive the
     * data.
     * <p class="note">
     * Note: if the calling activity is not expecting a result (that is it did
     * not use the {@link #startActivityForResult} form that includes a request
     * code), then the calling package will be null.
     * </p>
     * <p class="note">
     * Note: prior to {@link android.os.Build.VERSION_CODES#JELLY_BEAN_MR2}, the
     * result from this method was unstable. If the process hosting the calling
     * package was no longer running, it would return null instead of the proper
     * package name. You can use {@link #getCallingActivity()} and retrieve the
     * package name from that instead.
     * </p>
     * 
     * @return The package of the activity that will receive your reply, or null
     *         if none.
     */
    @Nullable
    public String getCallingPackage() {
        return super.getCallingPackage();
    }

    /**
     * Return the name of the activity that invoked this activity. This is who
     * the data in {@link #setResult setResult()} will be sent to. You can use
     * this information to validate that the recipient is allowed to receive the
     * data.
     * <p class="note">
     * Note: if the calling activity is not expecting a result (that is it did
     * not use the {@link #startActivityForResult} form that includes a request
     * code), then the calling package will be null.
     * 
     * @return The ComponentName of the activity that will receive your reply,
     *         or null if none.
     */
    @Nullable
    public ComponentName getCallingActivity() {
        return super.getCallingActivity();
    }

    /**
     * Control whether this activity's main window is visible. This is intended
     * only for the special case of an activity that is not going to show a UI
     * itself, but can't just finish prior to onResume() because it needs to
     * wait for a service binding or such. Setting this to false allows you to
     * prevent your UI from being shown during that time.
     * <p>
     * The default value for this is taken from the
     * {@link android.R.attr#windowNoDisplay} attribute of the activity's theme.
     */
    public void setVisible(boolean visible) {
        super.setVisible(visible);
    }

    /**
     * Check to see whether this activity is in the process of finishing, either
     * because you called {@link #finish} on it or someone else has requested
     * that it finished. This is often used in {@link #onPause} to determine
     * whether the activity is simply pausing or completely finishing.
     * 
     * @return If the activity is finishing, returns true; else returns false.
     * @see #finish
     */
    public boolean isFinishing() {
        return super.isFinishing();
    }

    /**
     * Returns true if the final {@link #onDestroy()} call has been made on the
     * Activity, so this instance is now dead.
     */
    public boolean isDestroyed() {
        return super.isDestroyed();
    }

    /**
     * Check to see whether this activity is in the process of being destroyed
     * in order to be recreated with a new configuration. This is often used in
     * {@link #onStop} to determine whether the state needs to be cleaned up or
     * will be passed on to the next instance of the activity via
     * {@link #onRetainNonConfigurationInstance()}.
     * 
     * @return If the activity is being torn down in order to be recreated with
     *         a new configuration, returns true; else returns false.
     */
    public boolean isChangingConfigurations() {
        return super.isChangingConfigurations();
    }

    /**
     * Cause this Activity to be recreated with a new instance. This results in
     * essentially the same flow as when the Activity is created due to a
     * configuration change -- the current instance will go through its
     * lifecycle to {@link #onDestroy} and a new instance then created after it.
     */
    public void recreate() {
        super.recreate();
    }

    /**
     * Call this when your activity is done and should be closed. The
     * ActivityResult is propagated back to whoever launched you via
     * onActivityResult().
     */
    public void finish() {
        super.finish();
    }

    /**
     * Finish this activity as well as all activities immediately below it in
     * the current task that have the same affinity. This is typically used when
     * an application can be launched on to another task (such as from an
     * ACTION_VIEW of a content type it understands) and the user has used the
     * up navigation to switch out of the current task and in to its own task.
     * In this case, if the user has navigated down into any other activities of
     * the second application, all of those should be removed from the original
     * task as part of the task switch.
     * <p>
     * Note that this finish does <em>not</em> allow you to deliver results to
     * the previous activity, and an exception will be thrown if you are trying
     * to do so.
     * </p>
     */
    public void finishAffinity() {
        super.finishAffinity();
    }

    /**
     * This is called when a child activity of this one calls its
     * {@link #finish} method. The default implementation simply calls finish()
     * on this activity (the parent), finishing the entire group.
     * 
     * @param child The activity making the call.
     * @see #finish
     */
    public void finishFromChild(Activity child) {
        super.finishFromChild(child);
    }

    /**
     * Force finish another activity that you had previously started with
     * {@link #startActivityForResult}.
     * 
     * @param requestCode The request code of the activity that you had given to
     *            startActivityForResult(). If there are multiple activities
     *            started with this request code, they will all be finished.
     */
    public void finishActivity(int requestCode) {
        super.finishActivity(requestCode);
    }

    /**
     * This is called when a child activity of this one calls its
     * finishActivity().
     * 
     * @param child The activity making the call.
     * @param requestCode Request code that had been used to start the activity.
     */
    public void finishActivityFromChild(@NonNull
    Activity child, int requestCode) {
        super.finishActivityFromChild(child, requestCode);
    }

    /**
     * Called when an activity you launched exits, giving you the requestCode
     * you started it with, the resultCode it returned, and any additional data
     * from it. The <var>resultCode</var> will be {@link #RESULT_CANCELED} if
     * the activity explicitly returned that, didn't return any result, or
     * crashed during its operation.
     * <p>
     * You will receive this call immediately before onResume() when your
     * activity is re-starting.
     * <p>
     * Dispatch incoming result to the correct fragment.
     * <p>
     * 
     * @param requestCode The integer request code originally supplied to
     *            startActivityForResult(), allowing you to identify who this
     *            result came from.
     * @param resultCode The integer result code returned by the child activity
     *            through its setResult().
     * @param data An Intent, which can return result data to the caller
     *            (various data can be attached to Intent "extras").
     * @see #startActivityForResult
     * @see #createPendingResult
     * @see #setResult(int)
     */
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * Return the identifier of the task this activity is in. This identifier
     * will remain the same for the lifetime of the activity.
     * 
     * @return Task identifier, an opaque integer.
     */
    public int getTaskId() {
        return super.getTaskId();
    }

    /**
     * Return whether this activity is the root of a task. The root is the first
     * activity in a task.
     * 
     * @return True if this is the root activity, else false.
     */
    public boolean isTaskRoot() {
        return super.isTaskRoot();
    }

    /**
     * Move the task containing this activity to the back of the activity stack.
     * The activity's order within the task is unchanged.
     * 
     * @param nonRoot If false then this only works if the activity is the root
     *            of a task; if true it will work for any activity in a task.
     * @return If the task was moved (or it was already at the back) true is
     *         returned, else false.
     */
    public boolean moveTaskToBack(boolean nonRoot) {
        return super.moveTaskToBack(nonRoot);
    }

    /**
     * Returns class name for this activity with the package prefix removed.
     * This is the default name used to read and write settings.
     * 
     * @return The local class name.
     */
    @NonNull
    public String getLocalClassName() {
        return super.getLocalClassName();
    }

    /**
     * Returns complete component name of this activity.
     * 
     * @return Returns the complete component name for this activity
     */
    public ComponentName getComponentName() {
        return super.getComponentName();
    }

    /**
     * Retrieve a {@link SharedPreferences} object for accessing preferences
     * that are private to this activity. This simply calls the underlying
     * {@link #getSharedPreferences(String, int)} method by passing in this
     * activity's class name as the preferences name.
     * 
     * @param mode Operating mode. Use {@link #MODE_PRIVATE} for the default
     *            operation, {@link #MODE_WORLD_READABLE} and
     *            {@link #MODE_WORLD_WRITEABLE} to control permissions.
     * @return Returns the single SharedPreferences instance that can be used to
     *         retrieve and modify the preference values.
     */
    public SharedPreferences getPreferences(int mode) {
        return super.getPreferences(mode);
    }

    /**
     * Change the title associated with this activity. If this is a top-level
     * activity, the title for its window will change. If it is an embedded
     * activity, the parent can do whatever it wants with it.
     */
    public void setTitle(CharSequence title) {
        super.setTitle(title);
    }

    /**
     * Change the title associated with this activity. If this is a top-level
     * activity, the title for its window will change. If it is an embedded
     * activity, the parent can do whatever it wants with it.
     */
    public void setTitle(int titleId) {
        super.setTitle(titleId);
    }

    /**
     * Change the color of the title associated with this activity.
     * <p>
     * This method is deprecated starting in API Level 11 and replaced by action
     * bar styles. For information on styling the Action Bar, read the <a
     * href="{@docRoot} guide/topics/ui/actionbar.html">Action Bar</a> developer
     * guide.
     * 
     * @deprecated Use action bar styles instead.
     */
    @Deprecated
    public void setTitleColor(int textColor) {
        super.setTitleColor(textColor);
    }

    protected void onTitleChanged(CharSequence title, int color) {
        super.onTitleChanged(title, color);
    }

    protected void onChildTitleChanged(Activity childActivity, CharSequence title) {
        super.onChildTitleChanged(childActivity, title);
    }

    /**
     * Standard implementation of
     * {@link android.view.LayoutInflater.Factory#onCreateView} used when
     * inflating with the LayoutInflater returned by {@link #getSystemService}.
     * This implementation does nothing and is for pre-
     * {@link android.os.Build.VERSION_CODES#HONEYCOMB} apps. Newer apps should
     * use {@link #onCreateView(View, String, Context, AttributeSet)}.
     * <p>
     * Add support for inflating the &lt;fragment> tag.
     * <p>
     * 
     * @see android.view.LayoutInflater#createView
     * @see android.view.Window#getLayoutInflater
     */
    @Nullable
    public View onCreateView(String name, Context context, AttributeSet attrs) {
        return super.onCreateView(name, context, attrs);
    }

    /**
     * Standard implementation of
     * {@link android.view.LayoutInflater.Factory2#onCreateView(View, String, Context, AttributeSet)}
     * used when inflating with the LayoutInflater returned by
     * {@link #getSystemService}. This implementation handles <fragment> tags to
     * embed fragments inside of the activity.
     * 
     * @see android.view.LayoutInflater#createView
     * @see android.view.Window#getLayoutInflater
     */
    public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {
        return super.onCreateView(parent, name, context, attrs);
    }

    /**
     * Print the Activity's state into the given stream. This gets invoked if
     * you run "adb shell dumpsys activity &lt;activity_component_name&gt;".
     * 
     * @param prefix Desired prefix to prepend at each line of output.
     * @param fd The raw file descriptor that the dump is being sent to.
     * @param writer The PrintWriter to which you should dump your state. This
     *            will be closed for you after you return.
     * @param args additional arguments to the dump request.
     */
    public void dump(String prefix, FileDescriptor fd, PrintWriter writer, String[] args) {
        super.dump(prefix, fd, writer, args);
    }

    /**
     * Returns true if the app should recreate the task when navigating 'up'
     * from this activity by using targetIntent.
     * <p>
     * If this method returns false the app can trivially call
     * {@link #navigateUpTo(Intent)} using the same parameters to correctly
     * perform up navigation. If this method returns false, the app should
     * synthesize a new task stack by using {@link TaskStackBuilder} or another
     * similar mechanism to perform up navigation.
     * </p>
     * 
     * @param targetIntent An intent representing the target destination for up
     *            navigation
     * @return true if navigating up should recreate a new task stack, false if
     *         the same task should be used for the destination
     */
    public boolean shouldUpRecreateTask(Intent targetIntent) {
        return super.shouldUpRecreateTask(targetIntent);
    }

    /**
     * Navigate from this activity to the activity specified by upIntent,
     * finishing this activity in the process. If the activity indicated by
     * upIntent already exists in the task's history, this activity and all
     * others before the indicated activity in the history stack will be
     * finished.
     * <p>
     * If the indicated activity does not appear in the history stack, this will
     * finish each activity in this task until the root activity of the task is
     * reached, resulting in an "in-app home" behavior. This can be useful in
     * apps with a complex navigation hierarchy when an activity may be reached
     * by a path not passing through a canonical parent activity.
     * </p>
     * <p>
     * This method should be used when performing up navigation from within the
     * same task as the destination. If up navigation should cross tasks in some
     * cases, see {@link #shouldUpRecreateTask(Intent)}.
     * </p>
     * 
     * @param upIntent An intent representing the target destination for up
     *            navigation
     * @return true if up navigation successfully reached the activity indicated
     *         by upIntent and upIntent was delivered to it. false if an
     *         instance of the indicated activity could not be found and this
     *         activity was simply finished normally.
     */
    public boolean navigateUpTo(Intent upIntent) {
        return super.navigateUpTo(upIntent);
    }

    /**
     * This is called when a child activity of this one calls its
     * {@link #navigateUpTo} method. The default implementation simply calls
     * navigateUpTo(upIntent) on this activity (the parent).
     * 
     * @param child The activity making the call.
     * @param upIntent An intent representing the target destination for up
     *            navigation
     * @return true if up navigation successfully reached the activity indicated
     *         by upIntent and upIntent was delivered to it. false if an
     *         instance of the indicated activity could not be found and this
     *         activity was simply finished normally.
     */
    public boolean navigateUpToFromChild(Activity child, Intent upIntent) {
        return super.navigateUpToFromChild(child, upIntent);
    }

    /**
     * Obtain an {@link Intent} that will launch an explicit target activity
     * specified by this activity's logical parent. The logical parent is named
     * in the application's manifest by the
     * {@link android.R.attr#parentActivityName parentActivityName} attribute.
     * Activity subclasses may override this method to modify the Intent
     * returned by super.getParentActivityIntent() or to implement a different
     * mechanism of retrieving the parent intent entirely.
     * 
     * @return a new Intent targeting the defined parent of this activity or
     *         null if there is no valid parent.
     */
    @Nullable
    public Intent getParentActivityIntent() {
        return super.getParentActivityIntent();
    }

    // ------------------------------------------------------------------------
    // HOOKS INTO ACTIVITY
    // ------------------------------------------------------------------------

    /**
     * Reverses the Activity Scene entry Transition and triggers the calling
     * Activity to reverse its exit Transition. When the exit Transition
     * completes, {@link #finish()} is called. If no entry Transition was used,
     * finish() is called immediately and the Activity exit Transition is run.
     * <p>
     * On Android 4.4 or lower, this method only finishes the Activity with no
     * special exit transition.
     * </p>
     */
    public void supportFinishAfterTransition() {
        super.supportFinishAfterTransition();
    }

    /**
     * When
     * {@link android.app.ActivityOptions#makeSceneTransitionAnimation(Activity, android.view.View, String)}
     * was used to start an Activity, <var>callback</var> will be called to
     * handle shared elements on the <i>launched</i> Activity. This requires
     * {@link Window#FEATURE_CONTENT_TRANSITIONS}.
     * 
     * @param callback Used to manipulate shared element transitions on the
     *            launched Activity.
     */
    public void setEnterSharedElementCallback(SharedElementCallback callback) {
        super.setEnterSharedElementCallback(callback);
    }

    /**
     * When
     * {@link android.app.ActivityOptions#makeSceneTransitionAnimation(Activity, android.view.View, String)}
     * was used to start an Activity, <var>listener</var> will be called to
     * handle shared elements on the <i>launching</i> Activity. Most calls will
     * only come when returning from the started Activity. This requires
     * {@link Window#FEATURE_CONTENT_TRANSITIONS}.
     * 
     * @param listener Used to manipulate shared element transitions on the
     *            launching Activity.
     */
    public void setExitSharedElementCallback(SharedElementCallback listener) {
        super.setExitSharedElementCallback(listener);
    }

    /**
     * Support library version of
     * {@link android.app.Activity#postponeEnterTransition()} that works only on
     * API 21 and later.
     */
    public void supportPostponeEnterTransition() {
        super.supportPostponeEnterTransition();
    }

    /**
     * Support library version of
     * {@link android.app.Activity#startPostponedEnterTransition()} that only
     * works with API 21 and later.
     */
    public void supportStartPostponedEnterTransition() {
        super.supportStartPostponedEnterTransition();
    }

    /**
     * This is the fragment-orientated version of {@link #onResume()} that you
     * can override to perform operations in the Activity at the same point
     * where its fragments are resumed. Be sure to always call through to the
     * super-class.
     */
    protected void onResumeFragments() {
        super.onResumeFragments();
    }

    // ------------------------------------------------------------------------
    // NEW METHODS
    // ------------------------------------------------------------------------

    /**
     * Use this instead of {@link #onRetainNonConfigurationInstance()}. Retrieve
     * later with {@link #getLastCustomNonConfigurationInstance()}.
     */
    public Object onRetainCustomNonConfigurationInstance() {
        return super.onRetainCustomNonConfigurationInstance();
    }

    /**
     * Return the value previously returned from
     * {@link #onRetainCustomNonConfigurationInstance()}.
     */
    public Object getLastCustomNonConfigurationInstance() {
        return super.getLastCustomNonConfigurationInstance();
    }

    /**
     * Support library version of {@link Activity#invalidateOptionsMenu}.
     * <p>
     * Invalidate the activity's options menu. This will cause relevant
     * presentations of the menu to fully update via calls to
     * onCreateOptionsMenu and onPrepareOptionsMenu the next time the menu is
     * requested.
     */
    public void supportInvalidateOptionsMenu() {
        super.supportInvalidateOptionsMenu();
    }

    // ------------------------------------------------------------------------
    // FRAGMENT SUPPORT
    // ------------------------------------------------------------------------

    /**
     * Called when a fragment is attached to the activity.
     */
    public void onAttachFragment(Fragment fragment) {
        super.onAttachFragment(fragment);
    }

    /**
     * Return the FragmentManager for interacting with fragments associated with
     * this activity.
     */
    public FragmentManager getSupportFragmentManager() {
        return super.getSupportFragmentManager();
    }

    /**
     * Called by Fragment.startActivityForResult() to implement its behavior.
     */
    public void startActivityFromFragment(Fragment fragment, Intent intent,
            int requestCode) {
        super.startActivityFromFragment(fragment, intent, requestCode);
    }

    // ------------------------------------------------------------------------
    // LOADER SUPPORT
    // ------------------------------------------------------------------------

    /**
     * Return the LoaderManager for this fragment, creating it if needed.
     */
    public LoaderManager getSupportLoaderManager() {
        return super.getSupportLoaderManager();
    }
}

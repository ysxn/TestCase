/*
 * Copyright (C) 2009 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.android.musicsss;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.os.Environment;
import android.view.animation.Animation;
import android.view.View;
import android.widget.RemoteViews;
import android.widget.TextView;
import android.util.AttributeSet;

/**
 * Simple widget to show currently playing album art along
 * with play/pause and next track buttons.  
 */
public class HtcMediaAppWidgetProviderSmall extends AppWidgetProvider {
    static final String TAG = "HtcMusicAppWidgetProviderSmall";
    
    public static final String CMDAPPWIDGETUPDATE = "htcappwidgetupdatesmall";

    private static HtcMediaAppWidgetProviderSmall sInstance;
    private Bitmap mAlbum = null;
    private Bitmap mReflect = null;
    private Animation mFadeIn = null;
    private Animation mFadeOut = null;
    private boolean mChanged = false;
    
    static synchronized HtcMediaAppWidgetProviderSmall getInstance() {
        if (sInstance == null) {
            sInstance = new HtcMediaAppWidgetProviderSmall();
        }
        return sInstance;
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        final Resources res = context.getResources();
        final RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.htc_album_appwidget_small);

        linkButtons(context, views, false /* not playing */);
        pushUpdate(context, appWidgetIds, views);
        
        // Send broadcast intent to any running MediaPlaybackService so it can
        // wrap around with an immediate update.
        Intent updateIntent = new Intent(MediaPlaybackService.SERVICECMD);
        updateIntent.putExtra(MediaPlaybackService.CMDNAME,
                HtcMediaAppWidgetProviderSmall.CMDAPPWIDGETUPDATE);
        updateIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, appWidgetIds);
        updateIntent.addFlags(Intent.FLAG_RECEIVER_REGISTERED_ONLY);
        context.sendBroadcast(updateIntent);
    }

    @Override
    public void onDisabled(Context context) {
        super.onDisabled(context);
    }

    @Override
    public void onEnabled(Context context) {
        super.onEnabled(context);
    }

    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        super.onDeleted(context, appWidgetIds);
    }

    private void pushUpdate(Context context, int[] appWidgetIds, RemoteViews views) {
        // Update specific list of appWidgetIds if given, otherwise default to all
        final AppWidgetManager gm = AppWidgetManager.getInstance(context);
        if (appWidgetIds != null) {
            gm.updateAppWidget(appWidgetIds, views);
        } else {
            gm.updateAppWidget(new ComponentName(context, this.getClass()), views);
        }
    }
    
    /**
     * Check against {@link AppWidgetManager} if there are any instances of this widget.
     */
    private boolean hasInstances(Context context) {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(
                new ComponentName(context, this.getClass()));
        return (appWidgetIds.length > 0);
    }

    /**
     * Handle a change notification coming over from {@link MediaPlaybackService}
     */
    void notifyChange(MediaPlaybackService service, String what) {
        if (hasInstances(service)) {
            service.notifyINotHaveWidget(0, true);
            if (MediaPlaybackService.PLAYBACK_COMPLETE.equals(what) ||
                    MediaPlaybackService.META_CHANGED.equals(what) ||
                    MediaPlaybackService.PLAYSTATE_CHANGED.equals(what) ||
                    MediaPlaybackService.PLAYSTATE_REFRESH_HTC.equals(what)) {
                performUpdate(service, null);
            }
        } else {
            service.notifyINotHaveWidget(0, false);
        }
    }
    
    void notifyBitmapChanged(MediaPlaybackService service, Bitmap bitmap, Bitmap bitmapReflect, Animation in, Animation out) {
        if (hasInstances(service)) {
            service.notifyINotHaveWidget(0, true);
            mAlbum = bitmap;
            mReflect= bitmapReflect;
            mChanged = true;
            if (mFadeIn== null) mFadeIn = in;
            if (mFadeOut== null) mFadeOut = out;
            performUpdate(service, null);
        } else {
            service.notifyINotHaveWidget(0, false);
        }
    }
    
    /**
     * Update all active widget instances by pushing changes 
     */
    void performUpdate(MediaPlaybackService service, int[] appWidgetIds) {
        final Resources res = service.getResources();
        final RemoteViews views = new RemoteViews(service.getPackageName(), R.layout.htc_album_appwidget_small);
        
        CharSequence titleName = service.getTrackName();
        CharSequence artistName = service.getArtistName();
        long duration = service.duration();
        long currentPosition = service.position();
        CharSequence errorState = null;
        int shuffleMode = service.getShuffleMode();
        int repeatMode = service.getRepeatMode();
        int resIDShuffle = R.drawable.ic_mp_shuffle_off_btn;
        int resIDRepeat = R.drawable.ic_mp_repeat_off_btn;

        if (shuffleMode == MediaPlaybackService.SHUFFLE_NORMAL ||
                shuffleMode == MediaPlaybackService.SHUFFLE_AUTO) {
            resIDShuffle = R.drawable.ic_mp_shuffle_on_btn;
        }

        if (repeatMode == MediaPlaybackService.REPEAT_CURRENT) {
            resIDRepeat = R.drawable.ic_mp_repeat_once_btn;
        } else if (repeatMode == MediaPlaybackService.REPEAT_ALL) {
            resIDRepeat = R.drawable.ic_mp_repeat_all_btn;
        }
        // Format title string with track number, or show SD card message
        String status = Environment.getExternalStorageState();
        if (status.equals(Environment.MEDIA_SHARED) ||
                status.equals(Environment.MEDIA_UNMOUNTED)) {
            errorState = res.getText(R.string.sdcard_busy_title);
        } else if (status.equals(Environment.MEDIA_REMOVED)) {
            errorState = res.getText(R.string.sdcard_missing_title);
        } else if (titleName == null) {
            errorState = res.getText(R.string.emptyplaylist);
        }

	if (errorState != null) {
            // Show error state to user
            views.setViewVisibility(R.id.txt_player_title, View.GONE);
            views.setTextViewText(R.id.txt_player_artist, errorState);
        } else {
            // No error, so show normal titles
            views.setViewVisibility(R.id.txt_player_title_small, View.VISIBLE);
            views.setTextViewText(R.id.txt_player_title_small, titleName);
            views.setTextViewText(R.id.txt_player_artist_small, artistName);
            views.setTextViewText(R.id.txt_player_lapse_small, MusicUtils.makeTimeStringHtc(currentPosition / 1000));
            views.setTextViewText(R.id.txt_player_duration_small, MusicUtils.makeTimeStringHtc(duration / 1000));
            views.setProgressBar(R.id.prb_player_progress_small, (int) 1000, (int) (1000 * currentPosition / duration), false);
            views.setImageViewResource(R.id.btn_player_shuffle_small, resIDShuffle);
            views.setImageViewResource(R.id.btn_player_repeat_small, resIDRepeat);
        }

        if (mAlbum != null) {
            views.setImageViewBitmap(R.id.img_player_cover_small, mAlbum);
        } else {
            views.setImageViewResource(R.id.img_player_cover_small, R.drawable.albumart_mp_unknown_htc);
        }

        if (mReflect != null) {
            views.setViewVisibility(R.id.img_player_cover_reflect_small, View.VISIBLE);
            views.setImageViewBitmap(R.id.img_player_cover_reflect_small, mReflect);
        } else {
            views.setViewVisibility(R.id.img_player_cover_reflect_small, View.GONE);
        }
        
        // Set correct drawable for pause state
        final boolean playing = service.isPlaying();
        if (playing) {
            views.setImageViewResource(R.id.btn_player_playpause_small, R.drawable.music_icon_pause_htc);
        } else {
            views.setImageViewResource(R.id.btn_player_playpause_small, R.drawable.music_icon_play_htc);
        }

        // Link actions buttons to intents
        linkButtons(service, views, playing);
        
        pushUpdate(service, appWidgetIds, views);
    }

    /**
     * Link up various button actions using {@link PendingIntents}.
     * 
     * @param playerActive True if player is active in background, which means
     *            widget click will launch {@link MediaPlaybackActivity},
     *            otherwise we launch {@link MusicBrowserActivity}.
     */
    private void linkButtons(Context context, RemoteViews views, boolean playerActive) {
        // Connect up various buttons and touch events
        Intent intent;
        PendingIntent pendingIntent;
        
        final ComponentName serviceName = new ComponentName(context, MediaPlaybackService.class);
        
        if (playerActive) {
            intent = new Intent(context, MediaPlaybackActivity.class);
            pendingIntent = PendingIntent.getActivity(context,
                    0 , intent, 0 );
            views.setOnClickPendingIntent(R.id.img_player_cover_small, pendingIntent);
        } else {
            intent = new Intent(context, MusicBrowserActivity.class);
            pendingIntent = PendingIntent.getActivity(context,
                    0 , intent, 0 );
            views.setOnClickPendingIntent(R.id.img_player_cover_small, pendingIntent);
        }
        
        intent = new Intent(MediaPlaybackService.TOGGLEPAUSE_ACTION);
        intent.setComponent(serviceName);
        pendingIntent = PendingIntent.getService(context,
                0 /* no requestCode */, intent, 0 /* no flags */);
        views.setOnClickPendingIntent(R.id.btn_player_playpause_small, pendingIntent);
        
        intent = new Intent(MediaPlaybackService.NEXT_ACTION);
        intent.setComponent(serviceName);
        pendingIntent = PendingIntent.getService(context,
                0 /* no requestCode */, intent, 0 /* no flags */);
        views.setOnClickPendingIntent(R.id.btn_player_next_small, pendingIntent);

        intent = new Intent(MediaPlaybackService.PREVIOUS_ACTION);
        intent.setComponent(serviceName);
        pendingIntent = PendingIntent.getService(context,
                0 /* no requestCode */, intent, 0 /* no flags */);
        views.setOnClickPendingIntent(R.id.btn_player_prev_small, pendingIntent);

        intent = new Intent(MediaPlaybackService.TOGGLESHUFFLE_ACTION);
        intent.setComponent(serviceName);
        pendingIntent = PendingIntent.getService(context,
                0 /* no requestCode */, intent, 0 /* no flags */);
        views.setOnClickPendingIntent(R.id.btn_player_shuffle_small, pendingIntent);

        intent = new Intent(MediaPlaybackService.TOGGLEREPEAT_ACTION);
        intent.setComponent(serviceName);
        pendingIntent = PendingIntent.getService(context,
                0 /* no requestCode */, intent, 0 /* no flags */);
        views.setOnClickPendingIntent(R.id.btn_player_repeat_small, pendingIntent);
    }
}

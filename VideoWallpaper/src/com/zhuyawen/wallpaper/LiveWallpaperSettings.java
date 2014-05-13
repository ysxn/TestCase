
package com.zhuyawen.wallpaper;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceScreen;
import android.util.Log;

public class LiveWallpaperSettings extends PreferenceActivity implements
        SharedPreferences.OnSharedPreferenceChangeListener {

    private final int REQUEST_SELECT_FILE = 2000;

    private Preference mSelectFilePreference;

    private Preference mAboutPreference;

    @Override
    protected void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        getPreferenceManager().setSharedPreferencesName(LiveWallpaperService.PREFERENCES);
        addPreferencesFromResource(R.xml.settings);
        getPreferenceManager().getSharedPreferences()
                .registerOnSharedPreferenceChangeListener(this);
        mSelectFilePreference = findPreference(LiveWallpaperService.PREFERENCE_SELECTFILE);
        mSelectFilePreference.setSummary(getString(R.string.preference_selectfile_summary)
                + LiveWallpaperService.mFileName);
        mAboutPreference = findPreference("preference_about");
    }

    @Override
    protected void onDestroy() {
        getPreferenceManager().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(
                this);
        super.onDestroy();
    }

    @Override
    public boolean onPreferenceTreeClick(PreferenceScreen preferenceScreen, Preference preference) {
        // TODO Auto-generated method stub
        if (preference == mSelectFilePreference) {
            if (LiveWallpaperService.DEBUG)
                Log.i(LiveWallpaperService.TAG, ">>>>>>>>>settings onPreferenceTreeClick");
            Intent intent = new Intent();
            intent.setClass(this, FileExplorer.class);
            startActivityForResult(intent, REQUEST_SELECT_FILE);

        } else if (preference == mAboutPreference) {
            showAlertDialog();
        }
        return super.onPreferenceTreeClick(preferenceScreen, preference);
    }

    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (LiveWallpaperService.DEBUG)
            Log.i(LiveWallpaperService.TAG,
                    ">>>>>>>>>>>>>>>>>>settings onSharedPreferenceChanged,key=" + key + ", value="
                            + Integer.parseInt(sharedPreferences.getString(key, "0")));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        switch (requestCode) {
            case REQUEST_SELECT_FILE:
                if (resultCode == RESULT_OK) {
                    LiveWallpaperService.mFileName = data.getExtras().getString(
                            Defined.INTENT_EXTRA_KEY_FILENAME);
                    mSelectFilePreference
                            .setSummary(getString(R.string.preference_selectfile_summary)
                                    + LiveWallpaperService.mFileName);
                } else if (resultCode == RESULT_CANCELED) {
                    if (LiveWallpaperService.DEBUG)
                        Log.i(LiveWallpaperService.TAG,
                                ">>>>>>>>>>>>>>>>>>settings onActivityResult RESULT_CANCELED");
                }
                break;

            default:
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    protected void showAlertDialog() {
        // TODO Auto-generated method stub
        new AlertDialog.Builder(this).setTitle(R.string.preference_about_title)
                .setMessage(R.string.text_about)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub
                    }
                }).show();
    }
}

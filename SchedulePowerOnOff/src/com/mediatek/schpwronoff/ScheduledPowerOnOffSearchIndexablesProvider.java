package com.mediatek.schpwronoff;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.util.Log;

import com.mediatek.schpwronoff.R;
import com.lenovo.settings.search.provider.SearchIndexableRaw;
import com.lenovo.settings.search.provider.SearchIndexableResource;
import com.lenovo.settings.search.provider.SearchIndexablesContract;
import com.lenovo.settings.search.provider.SearchIndexablesProvider;

public class ScheduledPowerOnOffSearchIndexablesProvider extends SearchIndexablesProvider{
	private static final String TAG = "ScheduledPowerOnOff"; 
	private static String[] NON_INDEXABLE_KEY;
    private static SearchIndexableRaw[] INDEXABLE_RAW;
    private static SearchIndexableResource[] INDEXABLE_RES;
    static {
        NON_INDEXABLE_KEY = new String[] {
                "power_setting"
        };

        INDEXABLE_RES = new SearchIndexableResource[] {
                new SearchIndexableResource(1, R.layout.preference_header_item_rom20,
                        "com.lenovo.pluginitem.PowerSettingPluginItem",
                        R.drawable.ic_settings_timely_power_on_off)
        };
    }

	@Override
	public Cursor queryNonIndexableKeys(String[] projection) {
		MatrixCursor cursor = new MatrixCursor(SearchIndexablesContract.NON_INDEXABLES_KEYS_COLUMNS);
        for (String key : NON_INDEXABLE_KEY) {
            Object[] ref = new Object[1];
            ref[0] = key;
            Log.d(TAG,"key = "+key.toString());
            cursor.addRow(ref);
        }

        return null;
	}

	@Override
	public Cursor queryRawData(String[] projection) {
		final Context context = getContext();
        MatrixCursor cursor = new MatrixCursor(SearchIndexablesContract.INDEXABLES_RAW_COLUMNS);

        {
            INDEXABLE_RAW = new SearchIndexableRaw[1];
            SearchIndexableRaw sir = new SearchIndexableRaw(null);
            sir.rank = 1;
            sir.key = "power_setting";
            sir.title = context.getString(R.string.schedule_power_on_off_settings_title);
            sir.summaryOn = null;
            sir.summaryOff = null;
            sir.screenTitle = context.getString(R.string.schedule_power_on_off_settings_title);
            sir.iconResId = R.drawable.ic_settings_timely_power_on_off;
            INDEXABLE_RAW[0] = sir;
        }

        for (SearchIndexableRaw sir : INDEXABLE_RAW) {
            Object[] ref = new Object[14];
            ref[0] = Integer.valueOf(sir.rank);
            ref[1] = sir.title;
            ref[2] = sir.summaryOn;
            ref[3] = sir.summaryOff;
            ref[4] = null;
            ref[5] = null;
            ref[6] = sir.screenTitle;
            ref[7] = null;
            ref[8] = Integer.valueOf(sir.iconResId);
            ref[9] = Intent.ACTION_MAIN;
            ref[10] = context.getPackageName();
            ref[11] = "com.mediatek.schpwronoff.AlarmClock";
            ref[12] = sir.key;
            ref[13] = -1;
            Log.d(TAG, "queryRawData REF = "+ref.toString());
            cursor.addRow(ref);
        }

        return cursor;
	}

	@Override
	public Cursor queryXmlResources(String[] projection) {
		 MatrixCursor cursor = new MatrixCursor(SearchIndexablesContract.INDEXABLES_XML_RES_COLUMNS);

	        for (SearchIndexableResource sir : INDEXABLE_RES) {
	            Object[] ref = new Object[7];
	            ref[0] = Integer.valueOf(sir.rank);
                    Log.d(TAG, "sir.rank = "+Integer.valueOf(sir.rank).toString());
	            ref[1] = Integer.valueOf(sir.xmlResId);
                    Log.d(TAG, "sir.rank = "+sir.xmlResId);
	            ref[2] = null;
	            ref[3] = Integer.valueOf(sir.iconResId);
	            ref[4] = Intent.ACTION_MAIN;
	            ref[5] = getContext().getPackageName();
                    Log.d(TAG, "packageName = "+getContext().getPackageName());
	            ref[6] = sir.className;
	            Log.d(TAG, "sir.className = "+sir.className);
	            cursor.addRow(ref);
	        }

	        return null;
	}

	@Override
	public boolean onCreate() {
		
		return true;
	}

}

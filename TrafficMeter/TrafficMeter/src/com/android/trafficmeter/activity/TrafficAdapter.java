package com.android.trafficmeter.activity;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.trafficmeter.DailyModel;
import com.android.trafficmeter.R;
import com.android.trafficmeter.TrafficHelper;
import com.android.trafficmeter.DailyModel.StatisticsInfo;

/**
 * An adapter that is used for ListView
 * @author archermind
 *
 */
public class TrafficAdapter extends BaseAdapter {

	private LayoutInflater mInflater;
	private ArrayList<DailyModel.StatisticsInfo> mStatisticsInfos;
	public TrafficAdapter(Context context) {
		mInflater  = LayoutInflater.from(context);
	}

	public void setMStatisticsInfos(
			ArrayList<DailyModel.StatisticsInfo> statisticsInfos) {
		mStatisticsInfos = statisticsInfos;
		notifyDataSetChanged();
	}

	public int getCount() {
		return mStatisticsInfos.size();
	}

	public Object getItem(int position) {
		return mStatisticsInfos.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		convertView = mInflater.inflate(R.layout.item, null);
		StatisticsInfo mStatisticsInfo=mStatisticsInfos.get(position);
		ItemViewWrapper viewWrapper = new ItemViewWrapper(convertView);
		viewWrapper.getMStatstype().setText(mStatisticsInfo.statstype);
		viewWrapper.getMUp().setText(TrafficHelper.convertBytes(mStatisticsInfo.up));
		viewWrapper.getMDown().setText(TrafficHelper.convertBytes(mStatisticsInfo.down));
		viewWrapper.getMAll().setText(TrafficHelper.convertBytes(mStatisticsInfo.all));
		return convertView;
	}

	/**
	 * View wrapper for item row
	 */
	static class ItemViewWrapper {
		private final View mBase;
		private TextView mStatstype;
		private TextView mUp;
		private TextView mDown;
		private TextView mAll;

		public ItemViewWrapper(View view) {
			mBase = view;
		}

		public TextView getMStatstype() {
			if(mStatstype==null) {
				mStatstype =(TextView) mBase.findViewById(R.id.statstype);
			}
			return mStatstype;
		}

		public TextView getMUp() {
			if(mUp == null) {
				mUp =(TextView)mBase.findViewById(R.id.up);
			}
			return mUp;
		}

		public TextView getMDown() {
			if(mDown == null) {
				mDown =(TextView)mBase.findViewById(R.id.down);
			}
			return mDown;
		}

		public TextView getMAll() {
			if(mAll == null) {
				mAll =(TextView)mBase.findViewById(R.id.all);
			}
			return mAll;
		}

	}

}

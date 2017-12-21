/**
 * @author valkesh Patel
 */
package com.dr.app.adapter;

import java.util.ArrayList;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dr.app.R;


/**
 * @author valkesh Patel
 */

public class GooglePlaceAPIAdapter extends BaseAdapter implements Filterable {
	private Context mContext;
	Dialog dialog;
	int pos = -1;
	LayoutInflater mInflater;
	ViewHolder holder = null;
	OnShoutDetailsListener getshout;
	private ArrayList<String> googleApiModel = new ArrayList<String>();
	// ArrayList<Boolean> inProgress = new ArrayList<Boolean>();
	Activity activity;

	public GooglePlaceAPIAdapter(Activity context,
			ArrayList<String> googleApiModel, OnShoutDetailsListener getshout) {
		mContext = context;
		activity = context;
		this.getshout = getshout;
		this.googleApiModel = googleApiModel;
		mInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	public interface OnShoutDetailsListener {
		public void onShoutDetails(String shout_id);
	}

	@Override
	public int getCount() {
		return googleApiModel.size();
	}

	@Override
	public Object getItem(int position) {
		return googleApiModel.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	static class ViewHolder {
		TextView lable;
	}

	public View getView(final int position, View convertView, ViewGroup parent) {
		if (mInflater == null) {
			mInflater = (LayoutInflater) mContext
					.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
		}
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.googleapiadpter, null);
			holder = new ViewHolder();

			holder.lable = (TextView) convertView.findViewById(R.id.lable);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.lable.setText(googleApiModel.get(position));
		return convertView;
	}

	@Override
	public Filter getFilter() {
		return new CityFilter();
	}

	private class CityFilter extends Filter {

		@Override
		public String convertResultToString(Object resultValue) {
			return (resultValue.toString());
		}

		@Override
		protected FilterResults performFiltering(CharSequence constraint) {
			if (constraint != null) {	
				// ArrayList<String> mData = new
				// ArrayList<String>(googleApiModel);
				// ArrayList<String> mSuggestions = new
				// ArrayList<String>(googleApiModel);

				// Check for similarities in data from constraint
				// for (String value : mData) {
				// if
				// (value.toLowerCase().contains(constraint.toString().toLowerCase()))
				// {
				// mSuggestions.add(value);
				// // Toast.makeText(mContext, value + " contains " +
				// constraint.toString(), Toast.LENGTH_LONG).show();
				// } else {
				// // Toast.makeText(mContext, value + " does not " +
				// constraint.toString(), Toast.LENGTH_LONG).show();
				//
				// }
				// }

				FilterResults filterResults = new FilterResults();
				filterResults.values = googleApiModel;
				filterResults.count = googleApiModel.size();
				return filterResults;
			} else {
				return null;
			}
		}

		@Override
		protected void publishResults(CharSequence constraint,
				FilterResults results) {
			if (results != null && results.count > 0) {
				notifyDataSetChanged();
			}
		}
	}

}

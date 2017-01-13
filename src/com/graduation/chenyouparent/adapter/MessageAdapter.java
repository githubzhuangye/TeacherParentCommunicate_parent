package com.graduation.chenyouparent.adapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.buu.bysj.domain.JsonToBeanUtils;
import com.buu.bysj.domain.Message;
import com.buu.bysj.domain.Msg;
import com.graduation.chenyouparent.R;
import com.graduation.chenyouparent.util.UserInfo;
import com.volley.VolleyInterface;
import com.volley.VolleyRequest;

public class MessageAdapter extends BaseAdapter {
	private ArrayList<Message> lists;
	private LayoutInflater inflater;
	private Context context;

	public MessageAdapter(Context context, ArrayList<Message> lists) {
		this.lists = lists;
		inflater = LayoutInflater.from(context);
		this.context = context;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return lists.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return lists.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(final int position, View view, ViewGroup arg2) {
		ViewHolder holder = null;
		if (view == null) {
			holder = new ViewHolder();
			view = inflater.inflate(R.layout.item_message, null);
			holder.tv_item_message_title = (TextView) view
					.findViewById(R.id.tv_item_message_title);
			holder.tv_item_message_systime = (TextView) view
					.findViewById(R.id.tv_item_message_systime);
			holder.tv_item_message_content = (TextView) view
					.findViewById(R.id.tv_item_message_content);
			view.setTag(holder);
		} else {
			holder = (ViewHolder) view.getTag();
		}

		holder.tv_item_message_title.setText(lists.get(position).getTitle());
		holder.tv_item_message_systime.setText(JsonToBeanUtils.formatter1
				.format(lists.get(position).getSystime()));
		holder.tv_item_message_content
				.setText(lists.get(position).getContent());
		return view;
	}

	private class ViewHolder {
		private TextView tv_item_message_title;
		private TextView tv_item_message_systime;
		private TextView tv_item_message_content;
	}


}

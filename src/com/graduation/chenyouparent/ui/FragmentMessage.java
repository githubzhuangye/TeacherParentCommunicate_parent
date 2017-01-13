package com.graduation.chenyouparent.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.buu.bysj.domain.JsonToBeanUtils;
import com.buu.bysj.domain.Message;
import com.graduation.chenyouparent.R;
import com.graduation.chenyouparent.adapter.MessageAdapter;
import com.graduation.chenyouparent.util.UserInfo;
import com.volley.VolleyInterface;
import com.volley.VolleyRequest;

public class FragmentMessage extends Fragment implements OnClickListener {

	private ListView lv_message_list;

	protected Context mContext;

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_message, container, false);

		// 初始化组件
		getView2Init(v);
		mContext = getActivity();
		//填充数据
		setMessageListData(mContext);
		return v;
	}

	/**
	 * 获得布局文件上的控件并初始化
	 */
	private void getView2Init(View view) {
		lv_message_list = (ListView) view.findViewById(R.id.lv_message_list);
	}

	// 点击事件
	@Override
	public void onClick(View v) {
		// 点击新建公告
//		if (v == btn_send_new_message) {
//			addNewMessage(mContext);
//		}
	}

	// 填充公告数据
	private void setMessageListData(final Context context) {
		// 获取收藏的投票
		String getTeacherMessageUrl = JsonToBeanUtils.url + "getStudentMessage";
		Map<String, String> parameter = new HashMap<String, String>();
		parameter.put("sid", UserInfo.uid);
		// 网络请求
		VolleyRequest.RequestPost(getTeacherMessageUrl, "getStudentMessage",
				parameter, new VolleyInterface() {
					// 请求失败
					@Override
					public void onfailure(VolleyError error) {
						// error.toString()
						Toast.makeText(context, "网络异常", Toast.LENGTH_LONG)
								.show();
					}
					// 请求成功
					@Override
					public void onSuccess(String result) {
						try {
							JSONArray arr = new JSONArray(result);
							final ArrayList<Message> messageList = (ArrayList<Message>) JsonToBeanUtils
									.JsonToMessageList(arr);

							// 设置数据设配器
							lv_message_list.setAdapter(new MessageAdapter(
									context, messageList));
							// 设置公告列表item项点击事件
//							lv_message_list
//									.setOnItemClickListener(new OnItemClickListener() {
//										@Override
//										public void onItemClick(AdapterView<?> arg0, View view,int position, long arg3) {
//											// TODO Auto-generated method stub
//											String messageId = messageList.get(position).getId();
//											Intent intent = new Intent(context,ActivityMessageDetail.class);
//											intent.putExtra("messageId", messageId     );
//											startActivity(intent);
//										}
//									});
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					}
				});

	}

}

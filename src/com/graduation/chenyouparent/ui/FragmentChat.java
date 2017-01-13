package com.graduation.chenyouparent.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.buu.bysj.domain.Chat;
import com.buu.bysj.domain.JsonToBeanUtils;
import com.buu.bysj.domain.Msg;
import com.buu.bysj.domain.Student;
import com.graduation.chenyouparent.R;
import com.graduation.chenyouparent.adapter.ChatAdapter;
import com.graduation.chenyouparent.util.UserInfo;
import com.volley.VolleyInterface;
import com.volley.VolleyRequest;

/**
 * 新闻fragment
 * 
 * @author xxf
 *
 */

public class FragmentChat extends Fragment implements OnClickListener {
	// 定义控件
	private ListView lv_all_chat_list;// 私信列表
	//新的聊天
	private EditText et_new_chat_content;
	private Button btn_send_new_chat;
	
	protected Context mContext;
	protected String teacherId;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRetainInstance(true);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_chat, container, false);
		
		mContext = getActivity();
		
		// 获取控件
		getView2Init(v);
		//填充聊天数据
		setAllChatListData(mContext);
		getStudentBySid(mContext);
		return v;
	}

	/**
	 * 获得布局文件上的控件并初始化
	 */
	private void getView2Init(View view) {
		lv_all_chat_list = (ListView) view.findViewById(R.id.lv_all_chat_list);
		et_new_chat_content = (EditText) view.findViewById(R.id.et_new_chat_content);
		btn_send_new_chat = (Button) view.findViewById(R.id.btn_send_new_chat);
		// 设置点击事件
		btn_send_new_chat.setOnClickListener(this);
	}

	// 点击事件
	@Override
	public void onClick(View v) {
		if (v == btn_send_new_chat) {
			// 发送新私信
			sendNewChat(mContext);
		}
	}
	// 根据学生id获取老师id
			private void getStudentBySid(final Context context) {
				new Thread() {
					@Override
					public void run() {
						// 获取工具书URL
						String getStudentUrl = JsonToBeanUtils.url + "getLoginStudent";
						Map<String, String> parameter = new HashMap<String, String>();
						parameter.put("id", UserInfo.uid);

						// 网络请求
						VolleyRequest.RequestPost(getStudentUrl, "getLoginStudent", parameter,
								new VolleyInterface() {
									// 请求失败
									@Override
									public void onfailure(VolleyError error) {
										// TODO Auto-generated method stub
										Toast.makeText(context, error.toString(),
												Toast.LENGTH_LONG).show();
									}

									// 请求成功
									@Override
									public void onSuccess(String result) {
										try {
											JSONObject obj = new JSONObject(result);
											Student student = JsonToBeanUtils.JsonToStudent(obj);
											teacherId = student.getTid().getId();
										} catch (Exception e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										}

									}
								});

					}
				}.start();
			}
	// 填充聊天列表数据
		private void setAllChatListData(final Context context) {
			new Thread() {
				@Override
				public void run() {
					// 获取工具书URL
					String getAllChatUrl = JsonToBeanUtils.url + "getStudentChat";
					Map<String, String> parameter = new HashMap<String, String>();
					parameter.put("sid", UserInfo.uid);

					// 网络请求
					VolleyRequest.RequestPost(getAllChatUrl, "getStudentChat", parameter,
							new VolleyInterface() {
								// 请求失败
								@Override
								public void onfailure(VolleyError error) {
									// TODO Auto-generated method stub
									Toast.makeText(context, error.toString(),
											Toast.LENGTH_LONG).show();
								}

								// 请求成功
								@Override
								public void onSuccess(String result) {
									try {
										JSONArray arr = new JSONArray(result);
										ArrayList<Chat> chatList = (ArrayList<Chat>) JsonToBeanUtils.JsonToChatList(arr);
										// 设置数据设配器
										lv_all_chat_list.setAdapter(new ChatAdapter(context, chatList));
									} catch (Exception e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}

								}
							});

				}
			}.start();
		}

	// 发送聊天
	private void sendNewChat(final Context context) {
		String content = et_new_chat_content.getText().toString().trim();// 聊天内容
		if ("".equals(content)) {
			Toast.makeText(context, "请填写内容", Toast.LENGTH_LONG).show();
		} else {
			// 获取工具书URL
			final String sendNewChatUrl = JsonToBeanUtils.url + "addChat";
			final Map<String, String> parameter = new HashMap<String, String>();
			parameter.put("sid", UserInfo.uid);
			parameter.put("tid", teacherId);
			parameter.put("content", content);
			parameter.put("user", "student");
			new Thread() {

				public void run() {
					// 网络请求获取工具书
					VolleyRequest.RequestPost(sendNewChatUrl,"addChat", parameter,
							new VolleyInterface() {
								// 请求失败
								@Override
								public void onfailure(VolleyError error) {
									// TODO Auto-generated method stub
									Toast.makeText(context, error.toString(),
											Toast.LENGTH_LONG).show();
								}
								// 请求成功
								@Override
								public void onSuccess(String result) {
									try {
										JSONObject obj = new JSONObject(result);
										// 日志列表
										final Msg msg = JsonToBeanUtils
												.JsonToMsg(obj);
										if (msg.getStatus().equals("success")) {
											//清空数据
											et_new_chat_content.setText(null);
											//刷新数据
											setAllChatListData(context);
										}
									} catch (Exception e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}

								}
							});

				}
			}.start();
		}

	}

}

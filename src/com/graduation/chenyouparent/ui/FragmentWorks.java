package com.graduation.chenyouparent.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.buu.bysj.domain.JsonToBeanUtils;
import com.buu.bysj.domain.Message;
import com.buu.bysj.domain.Msg;
import com.buu.bysj.domain.Works;
import com.graduation.chenyouparent.R;
import com.graduation.chenyouparent.adapter.WorksAdapter;
import com.graduation.chenyouparent.util.UserInfo;
import com.volley.VolleyInterface;
import com.volley.VolleyRequest;

public class FragmentWorks extends Fragment{

	// 已有作品
	private ListView lv_works_list;

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
		View v = inflater.inflate(R.layout.fragment_works, container, false);

		// 初始化组件
		getView2Init(v);
		mContext = getActivity();
		//填充数据
		setWorksListData(mContext);
		return v;
	}

	/**
	 * 获得布局文件上的控件并初始化
	 */
	private void getView2Init(View view) {
		lv_works_list = (ListView) view.findViewById(R.id.lv_works_list);
	}


	// 填充作品列表数据
	private void setWorksListData(final Context context) {
		// 获取关注的用户
		String getWorksUrl = JsonToBeanUtils.url + "getStudentWorks";
		Map<String, String> parameter = new HashMap<String, String>();
		parameter.put("sid", UserInfo.uid);
		// 网络请求
		VolleyRequest.RequestPost(getWorksUrl, "getStudentWorks",
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
							final ArrayList<Works> worksList = (ArrayList<Works>) JsonToBeanUtils
									.JsonToWorksList(arr);

							// 设置数据设配器
							lv_works_list.setAdapter(new WorksAdapter(
									context, worksList));
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					}
				});

	}

}

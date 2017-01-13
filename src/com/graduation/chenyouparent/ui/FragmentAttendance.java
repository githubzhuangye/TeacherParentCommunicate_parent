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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.buu.bysj.domain.Attendance;
import com.buu.bysj.domain.JsonToBeanUtils;
import com.buu.bysj.domain.Student;
import com.graduation.chenyouparent.R;
import com.graduation.chenyouparent.adapter.AttendanceAdapter;
import com.graduation.chenyouparent.util.UserInfo;
import com.volley.VolleyInterface;
import com.volley.VolleyRequest;

public class FragmentAttendance extends Fragment implements
		OnClickListener {
	//学生考勤列表
	private ListView lv_attendance_list;

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
		View v = inflater.inflate(R.layout.fragment_attendance, container, false);
		
		// 获取上下文
		mContext = getActivity();
		// 初始化组件
		getView2Init(v);
		// 设置diarylit数据
		setAttendanceListData(mContext);

		return v;
	}

	/**
	 * 获得布局文件上的控件并初始化
	 */
	private void getView2Init(View view) {
		// 列表
		lv_attendance_list = (ListView) view.findViewById(R.id.lv_attendance_list);
	}

	// 填充学生数据
	private void setAttendanceListData(final Context context) {
		// 获取学生URL
		String getStudentAttendanceUrl = JsonToBeanUtils.url + "getStudentAttendance";
		final Map<String, String> parameter = new HashMap<String, String>();
		parameter.put("sid", UserInfo.uid);
		// 网络请求
				VolleyRequest.RequestPost(getStudentAttendanceUrl, "getStudentAttendance",
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
									final ArrayList<Attendance> attendanceList = (ArrayList<Attendance>) JsonToBeanUtils.JsonToAttendanceList(arr);
							// 设置数据
									lv_attendance_list.setAdapter(new AttendanceAdapter(context, attendanceList));
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					}
				});
	}

	// 点击事件
	@Override
	public void onClick(View v) {
//		if (v == btn_send_student_attendance) {
//			// 发送
//			sendStudentAttendance();
//		} else if (v == tv_search_student_btn) {
//			// 按照关键字搜索
//			searchStudent();
//		}
		
	}
	

}

package com.graduation.chenyouparent.ui;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.buu.bysj.domain.JsonToBeanUtils;
import com.buu.bysj.domain.Msg;
import com.graduation.chenyouparent.R;
import com.graduation.chenyouparent.util.UserInfo;
import com.volley.VolleyInterface;
import com.volley.VolleyRequest;

public class ActivityLogin extends FragmentActivity implements OnClickListener {
	private EditText et_login_stunum;
	private EditText et_login_password;
	private Button btn_login;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		// 初始化控件
		getView2Init();

	}

	/**
	 * 获得布局文件上的控件并初始化
	 */
	private void getView2Init() {
		et_login_stunum = (EditText) findViewById(R.id.et_login_stunum);
		et_login_password = (EditText) findViewById(R.id.et_login_password);
		btn_login = (Button) findViewById(R.id.btn_login);
		// 设置点击事件
		btn_login.setOnClickListener(this);
	}

	// 点击事件
	@Override
	public void onClick(View v) {
		if (v == btn_login) {
			login();
		}
	}

	// 登录
	private void login() {
		new Thread() {
			@Override
			public void run() {
				final String stunum = et_login_stunum.getText().toString()
						.trim();
				final String password = et_login_password.getText().toString()
						.trim();
				// [2.1]定义get方式要提交的路径 小细节 如果提交中文要对name 和 pwd 进行一个urlencode 编码
				String loginUrl = JsonToBeanUtils.url + "loginStudent";
				Map<String, String> parameter = new HashMap<String, String>();
				parameter.put("stunum", stunum);
				parameter.put("password", password);
				VolleyRequest.RequestPost(loginUrl, "loginStudent", parameter,
						new VolleyInterface() {

							@Override
							public void onfailure(VolleyError error) {
								// TODO Auto-generated method
								// stuberror.toString()
								Toast.makeText(getApplicationContext(), "网络异常",
										Toast.LENGTH_LONG).show();
							}

							@Override
							public void onSuccess(String result) {
								try {
									JSONObject obj = new JSONObject(result);
									Msg msg = JsonToBeanUtils.JsonToMsg(obj);
									if (msg.getStatus().equals("error")) {
										Toast.makeText(getApplicationContext(),
												msg.getMsg(), Toast.LENGTH_LONG)
												.show();
									} else if (msg.getStatus()
											.equals("success")) {
										// 保存登录信息
										UserInfo.save(getApplicationContext(),
												stunum, password, msg.getMsg());
										// 跳转页面
										Intent intent = new Intent(
												ActivityLogin.this,
												MainActivity.class);
										startActivity(intent);
									} else {
										Toast.makeText(getApplicationContext(),
												"服务器繁忙，请稍后", Toast.LENGTH_LONG)
												.show();
										;
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

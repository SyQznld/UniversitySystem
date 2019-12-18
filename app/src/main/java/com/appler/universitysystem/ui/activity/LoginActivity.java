package com.appler.universitysystem.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.appler.universitysystem.MainActivity;
import com.appler.universitysystem.R;
import com.appler.universitysystem.api.FlagConstant;
import com.appler.universitysystem.base.BaseActivity;
import com.appler.universitysystem.bean.LoginUser;
import com.appler.universitysystem.bean.UserData;
import com.appler.universitysystem.dao.UserDao;
import com.appler.universitysystem.presenter.LoginPresenter;
import com.appler.universitysystem.view.LoginView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zhy.m.permission.MPermissions;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity implements LoginView {

    private String TAG = getClass().getSimpleName();
    @BindView(R.id.btn_login)
    Button btnLogin;
    @BindView(R.id.et_username)
    EditText etUsername;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.pb_login)
    ProgressBar pbLogin;
    private Toast toast;
    private LoginPresenter loginPresenter;
    private static final int REQUECT_CODE_SDCARD = 0;

    @Override
    public void doBusiness(Context context) {

        loginPresenter = new LoginPresenter(this);
        //动态申请权限
        setPermission();

        UserData user = UserDao.getUser();
        if (null != user) {
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }

        etUsername.setText("后勤员工");
        etPassword.setText("111");
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_login;
    }

    @OnClick({R.id.btn_login})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_login: //登录
                String username = etUsername.getText().toString();
                String password = etPassword.getText().toString();
                String msg = TextUtils.isEmpty(username) ? "账号不能为空！" : TextUtils.isEmpty(password) ? "密码不能为空！" : "";
                if (!TextUtils.isEmpty(msg)) {
                    pbLogin.setVisibility(View.GONE);
                    showToast(msg);
                } else {
                    pbLogin.setVisibility(View.VISIBLE);
                    loginPresenter.getLogin(FlagConstant.FLAG_LOGIN, username, password);
                }
                break;
        }
    }

    /**
     * 请求接口返回数据
     */
    @Override
    public void getLogin(String string) {
        Log.i(TAG, "getLogin: " + string);
        ConnectivityManager manager = (ConnectivityManager) LoginActivity.this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isAvailable()) {
            if ("用户名或密码错误".equals(string)) {
                pbLogin.setVisibility(View.GONE);
                showToast(string);
            } else if (!"".equals(string) && string.startsWith("[{")){ //账号密码正确
                pbLogin.setVisibility(View.GONE);
                List<LoginUser> userBeanList = new Gson().fromJson(string, new TypeToken<List<LoginUser>>() {
                }.getType());
                LoginUser userBean = userBeanList.get(0);
                UserData user = UserDao.getUser();
                if (user == null) {
                    user = new UserData();

                    saveUserInfo(userBean, user);
                    UserDao.insertUserData(user);
                } else {
                    saveUserInfo(userBean, user);
                    UserDao.updateUserData(user);
                }
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                intent.putExtra("flag", 1);//百度导航弹框
                startActivity(intent);
            } else {
                pbLogin.setVisibility(View.GONE);
                Toast.makeText(LoginActivity.this, "连接超时请检查网络", Toast.LENGTH_SHORT).show();
            }
        } else {
            pbLogin.setVisibility(View.GONE);
            Toast.makeText(LoginActivity.this, "网络不可用", Toast.LENGTH_SHORT).show();
        }

    }

    public void showToast(String message) {
        if (toast != null) {
            toast.cancel();
            toast = null;
        }
        toast = Toast.makeText(this, message, Toast.LENGTH_SHORT);
        toast.show();
    }


    /**
     * 设置人员信息
     */
    private void saveUserInfo(LoginUser loginUser, UserData user) {
        user.setUserId(loginUser.getId());
        user.setUser_name(loginUser.getUser_name());
        user.setUser_password(loginUser.getUser_password());
        user.setUser_role(loginUser.getUser_role());
        user.setUser_tele(loginUser.getUser_tele());
        user.setUser_email(loginUser.getUser_email());
        user.setUser_depart(loginUser.getUser_depart());
        user.setIsdelete(loginUser.isIsdelete());
    }


    /**
     * 动态申请权限
     */
    private boolean setPermission() {
        MPermissions.requestPermissions(LoginActivity.this, REQUECT_CODE_SDCARD,
                "android.permission.WRITE_EXTERNAL_STORAGE",
                "android.permission.READ_EXTERNAL_STORAGE",
                "android.permission.MOUNT_UNMOUNT_FILESYSTEMS",
                "android.permission.READ_PHONE_STATE",
                "android.permission.VIBRATE",
                "android.permission.ACCESS_NETWORK_STATE",
                "android.permission.ACCESS_WIFI_STATE",
                "android.permission.CHANGE_WIFI_STATE",
                "android.permission.INTERNET",
                "android.permission.ACCESS_FINE_LOCATION",
                "android.permission.BAIDU_LOCATION_SERVICE",
                "android.permission.ACCESS_COARSE_LOCATION",
                "android.permission.ACCESS_GPS",
                "android.permission.READ_LOGS",
                "android.permission.CAMERA",
                "android.permission.WRITE_SETTINGS");
        return true;
    }
}

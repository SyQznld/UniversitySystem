package com.appler.universitysystem.ui.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.graphics.Color;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.appler.universitysystem.R;
import com.appler.universitysystem.api.FlagConstant;
import com.appler.universitysystem.base.BaseActivity;
import com.appler.universitysystem.bean.UserData;
import com.appler.universitysystem.dao.UserDao;
import com.appler.universitysystem.presenter.VersionUpdatePresenter;
import com.appler.universitysystem.utils.ClearCacheUtil;
import com.appler.universitysystem.utils.ToastUtils;
import com.appler.universitysystem.view.VersionUpdateView;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class UserActivity extends BaseActivity implements VersionUpdateView {

    private String TAG = getClass().getSimpleName();

    @BindView(R.id.toolbar_info)
    Toolbar toolbarInfo;
    @BindView(R.id.civ_mine_head)
    CircleImageView civMineHead;
    @BindView(R.id.tv_mine_name)
    TextView tvMineName;
    @BindView(R.id.tv_mine_role)
    TextView tvMineRole;
    @BindView(R.id.rl_mine_admin)
    RelativeLayout rlMineAdmin;
    @BindView(R.id.tv_unread_message)
    TextView tvUnreadMessage;
    @BindView(R.id.ll_mine_message)
    LinearLayout llMineMessage;
    @BindView(R.id.ll_mine_docs)
    LinearLayout ll_docs;
    @BindView(R.id.tv_version)
    TextView tvVersion;
    @BindView(R.id.ll_mine_version)
    LinearLayout llMineVersion;
    @BindView(R.id.tv_mine_totalCache)
    TextView tvMineTotalCache;
    @BindView(R.id.ll_mine_clear)
    LinearLayout llMineClear;
    @BindView(R.id.ll_mine_resetPwd)
    LinearLayout llMineResetPwd;
    @BindView(R.id.ll_mine_exit)
    LinearLayout llMineExit;

    private String versionName;
    private VersionUpdatePresenter versionUpdatePresenter;

    @Override
    public int bindLayout() {
        return R.layout.activity_user;
    }

    @Override
    public void doBusiness(Context context) {
        versionUpdatePresenter = new VersionUpdatePresenter(this);
        toolbarInfo.setTitle("设置中心");
        toolbarInfo.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        UserData user = UserDao.getUser();
        tvMineName.setText(user.getUser_name());
        if ("null".equals(user.getUser_role()) || user.getUser_role() == null || "".equals(user.getUser_role())) {
            tvMineRole.setVisibility(View.GONE);
        } else {
            tvMineRole.setVisibility(View.VISIBLE);
            tvMineRole.setText("职位：" + user.getUser_role());
        }
        tvMineName.setText(user.getUser_name());
        tvMineRole.setText("计算机学院  物联网");


        try {
            String totalCacheSize = ClearCacheUtil.getTotalCacheSize(UserActivity.this);
            tvMineTotalCache.setText(totalCacheSize);

            PackageInfo packageInfo = UserActivity.this.getPackageManager().getPackageInfo(UserActivity.this.getPackageName(), 0);
            versionName = packageInfo.versionName;
            tvVersion.setText(versionName);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    @OnClick({R.id.rl_mine_admin,
            R.id.ll_mine_message, R.id.ll_mine_docs,
            R.id.ll_mine_version, R.id.ll_mine_clear, R.id.ll_mine_resetPwd,
            R.id.ll_mine_exit})
    public void onViewClick(View v) {
        switch (v.getId()) {
            case R.id.rl_mine_admin://个人信息栏
                Intent InfoIntent = new Intent(UserActivity.this, UserInfoActivity.class);
                startActivity(InfoIntent);
                break;
            case R.id.ll_mine_message://消息通知
                Intent MessIntent = new Intent(UserActivity.this, MessageListActivity.class);
                startActivity(MessIntent);
                break;
            case R.id.ll_mine_docs://资料下载
                Intent docsIntent = new Intent(UserActivity.this, DocsListActivity.class);
                docsIntent.putExtra("flag", 3);
                startActivity(docsIntent);
                break;
            case R.id.ll_mine_version://版本更新
//                VersionUpdateUtils.checkUpdate(versionUpdateData, tv_version, getActivity());
                break;
            case R.id.ll_mine_clear://清除缓存
                try {
                    new MaterialDialog.Builder(UserActivity.this)
                            .title("清除缓存")
                            .content("确定清除" + ClearCacheUtil.getTotalCacheSize(UserActivity.this) + "缓存！")
                            .positiveText("确定")
                            .negativeText("取消")
                            .widgetColor(Color.BLUE)//不再提醒的checkbox 颜色

                            .onAny(new MaterialDialog.SingleButtonCallback() {
                                @Override
                                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                    if (which == DialogAction.POSITIVE) {
                                        ClearCacheUtil.clearAllCache(UserActivity.this);
                                        tvMineTotalCache.setText("0.0KB");

                                    } else if (which == DialogAction.NEGATIVE) {
                                        dialog.dismiss();
                                    }

                                }
                            }).show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.ll_mine_resetPwd://重置密码
                AlertDialog.Builder builder = new AlertDialog.Builder(UserActivity.this);
                LayoutInflater inflater = getLayoutInflater();
                View dialog = inflater.inflate(R.layout.reset_password_layout, null);
                final EditText et_pwd_old = dialog.findViewById(R.id.et_pwd_old);
                final EditText et_pwd_new = dialog.findViewById(R.id.et_pwd_new);
                final EditText et_pwd_new_sure = dialog.findViewById(R.id.et_pwd_new_sure);

                builder.setTitle("重置密码！");
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String oldStr = et_pwd_old.getText().toString();
                        String newStr = et_pwd_new.getText().toString();
                        String newSureStr = et_pwd_new_sure.getText().toString();
                        if ("".equals(oldStr)) {
                            ToastUtils.showShortToast(UserActivity.this, "旧密码不能为空~");
                        } else if ("".equals(newStr)) {
                            ToastUtils.showShortToast(UserActivity.this, "新密码不能为空~");
                        } else if ("".equals(newSureStr)) {
                            ToastUtils.showShortToast(UserActivity.this, "请输入新密码确认~");
                        } else if (newStr.equals(newSureStr) == false) {
                            ToastUtils.showShortToast(UserActivity.this, "两次输入密码不同~");
                        } else if (newStr.equals(oldStr) || newSureStr.equals(oldStr)) {
                            ToastUtils.showShortToast(UserActivity.this, "新密码与旧密码一样~");
                        } else {
                            versionUpdatePresenter.resetPassword(FlagConstant.FLAG_UpdataPW, UserDao.getUserID(), oldStr, newStr);
                        }
                    }
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.setView(dialog);
                builder.show();
                break;
            case R.id.ll_mine_exit://退出登录
                new MaterialDialog.Builder(UserActivity.this)
                        .title("退出登录！")
                        .content("确定退出当前用户！")
                        .positiveText("确定")
                        .negativeText("取消")
                        .widgetColor(Color.BLUE)//不再提醒的checkbox 颜色

                        .onAny(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                if (which == DialogAction.POSITIVE) {
                                    new Handler().postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            UserDao.deleteUserData(UserDao.getUser().getId());
                                            Intent intent = new Intent(UserActivity.this, LoginActivity.class);
                                            startActivity(intent);
                                            finish();
                                        }
                                    }, 500);

                                } else if (which == DialogAction.NEGATIVE) {
                                    dialog.dismiss();
                                }
                            }
                        }).show();
                break;

        }
    }

    @Override
    public void resetPassword(String string) {
        if ("success".equals(string)) {
            Toast.makeText(UserActivity.this, "密码修改成功", Toast.LENGTH_LONG).show();
            UserDao.deleteUserData(UserDao.getUser().getId());
            Intent intent = new Intent(UserActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        } else if ("原密码错误,请重新输入".equals(string)) {
            Toast.makeText(UserActivity.this, "原密码错误,请重新输入", Toast.LENGTH_LONG).show();
        }
    }
}

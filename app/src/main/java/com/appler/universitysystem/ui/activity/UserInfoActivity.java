package com.appler.universitysystem.ui.activity;

import android.content.Context;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.appler.universitysystem.R;
import com.appler.universitysystem.base.BaseActivity;
import com.appler.universitysystem.bean.UserData;
import com.appler.universitysystem.dao.UserDao;

import butterknife.BindView;
import de.hdodenhof.circleimageview.CircleImageView;

public class UserInfoActivity extends BaseActivity {
    @BindView(R.id.iv_frame_background)
    ImageView ivFrameBackground;
    @BindView(R.id.tv_info_name)
    TextView tvInfoName;
    @BindView(R.id.tv_info_tele)
    TextView tvInfoTele;
    @BindView(R.id.tv_info_depart)
    TextView tvInfoDepart;
    @BindView(R.id.tv_info_email)
    TextView tvInfoEmail;
    @BindView(R.id.civ_info_image)
    CircleImageView civInfoImage;
    @BindView(R.id.toolbar_info)
    Toolbar toolbarInfo;
    @BindView(R.id.rl_frame_info)
    RelativeLayout rlFrameInfo;
    @BindView(R.id.tv_info_role)
    TextView tvInfoRole;
    private String TAG = getClass().getSimpleName();

    @Override
    public int bindLayout() {
        return R.layout.activity_user_info;
    }

    @Override
    public void doBusiness(Context context) {
        toolbarInfo.setTitle("个人详情");
        toolbarInfo.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        UserData user = UserDao.getUser();
        tvInfoName.setText(user.getUser_name());
        tvInfoTele.setText(user.getUser_tele());
//        tvInfoDepart.setText(user.getUser_depart());
//        tvInfoRole.setText(user.getUser_role());
        tvInfoRole.setText("计算机学院");
        tvInfoDepart.setText("物联网");
        tvInfoEmail.setText(user.getUser_email());

    }

}

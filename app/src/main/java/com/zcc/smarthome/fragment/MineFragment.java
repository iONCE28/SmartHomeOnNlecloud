package com.zcc.smarthome.fragment;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.zcc.smarthome.R;
import com.zcc.smarthome.activity.AboutActivity;
import com.zcc.smarthome.activity.AlterUserInfActivity;
import com.zcc.smarthome.activity.LoginActivity;
import com.zcc.smarthome.activity.MyDevicesListActivity;
import com.zcc.smarthome.bean.User;
import com.zcc.smarthome.utils.TakePictureManager;
import com.zcc.smarthome.utils.ToastUtils;
import com.zcc.smarthome.view.AnimotionPopupWindow;
import com.zcc.smarthome.view.BlurTransformation;
import com.zcc.smarthome.view.CircleTransform;
import com.zcc.smarthome.view.PullScrollView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.UploadFileListener;


public class MineFragment extends BaseFragment implements View.OnClickListener {


    private ImageView ivHeaderBg;
    private ImageView ivmeIcon;
    private PullScrollView pullView;
    private TextView tvName;
    private TextView mTvDevices;
    private TextView mTvShareDevices;
    private TextView mTvDevicesLog;
    private com.lqr.optionitemview.OptionItemView mOVUserInf;
    private com.lqr.optionitemview.OptionItemView mOVCarText;
    private com.lqr.optionitemview.OptionItemView mOVDayHappy;
    private com.lqr.optionitemview.OptionItemView mOVAbout;
    private com.lqr.optionitemview.OptionItemView OVVegetable;
    //上传图片用到
    private TakePictureManager takePictureManager;
    //拍照完图片保存的路径

    @Override
    protected int setLayoutId() {
        return R.layout.fragment_mine;
    }

    @Override
    protected void initView(View view) {

        mTvDevices = (TextView) view.findViewById(R.id.tvDevices);
        mTvDevices.setOnClickListener(this);
        mTvShareDevices = (TextView) view.findViewById(R.id.tvShareDevices);
        mTvShareDevices.setOnClickListener(this);
        mTvDevicesLog = (TextView) view.findViewById(R.id.tvDevicesLog);
        mTvDevicesLog.setOnClickListener(this);

        mOVUserInf = (com.lqr.optionitemview.OptionItemView) view.findViewById(R.id.OVUserInf);
        mOVUserInf.setOnClickListener(this);
        mOVCarText = (com.lqr.optionitemview.OptionItemView) view.findViewById(R.id.OVCarText);
        mOVCarText.setOnClickListener(this);
        mOVDayHappy = (com.lqr.optionitemview.OptionItemView) view.findViewById(R.id.OVDayHappy);
        mOVDayHappy.setOnClickListener(this);
        mOVAbout = (com.lqr.optionitemview.OptionItemView) view.findViewById(R.id.OVAbout);
        OVVegetable = (com.lqr.optionitemview.OptionItemView) view.findViewById(R.id.OVWeather);
        mOVAbout.setOnClickListener(this);
        OVVegetable.setOnClickListener(this);


        ivHeaderBg = (ImageView) view.findViewById(R.id.ivHeaderBg);
        ivmeIcon = (ImageView) view.findViewById(R.id.ivIcon);
        tvName = (TextView) view.findViewById(R.id.tvName);
        ivmeIcon.setOnClickListener(this);
        pullView = (PullScrollView) view.findViewById(R.id.pullView);
        pullView.setZoomView(ivHeaderBg);

//        getUserInf();

    }


    @Override
    public void onResume() {
        super.onResume();
//        getUserInf();
    }

    @Override
    public void onClick(final View v) {
        switch (v.getId()) {


            //我的设备
            case R.id.tvDevices:
                startActivity(new Intent(getActivity(), MyDevicesListActivity.class));
                break;

            //设备分享
            case R.id.tvShareDevices:
                break;

            //设备日志
            case R.id.tvDevicesLog:
                break;

            case R.id.ivIcon:
                //判断是否已经登录
                if (BmobUser.getCurrentUser(User.class) == null) {
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                } else {
                    List<String> list = new ArrayList<>();
                    list.add("更改头像");
                    list.add("切换账号");
                    list.add("退出登录");
                    AnimotionPopupWindow popupWindow = new AnimotionPopupWindow(getActivity(), list);
                    popupWindow.show();
                    popupWindow.setAnimotionPopupWindowOnClickListener(new AnimotionPopupWindow.AnimotionPopupWindowOnClickListener() {
                        @Override
                        public void onPopWindowClickListener(int position) {
                            switch (position) {
                                //更改头像
                                case 0:
                                    changeMyIcon();
                                    break;
                                //切换账号
                                case 1:
                                    startActivity(new Intent(getActivity(), AlterUserInfActivity.class));
                                    break;
                                //退出登录
                                case 2:
                                    User.logOut();
                                    ToastUtils.showToast(getActivity(), "退出成功！");
//                                    getUserInf();
                                    break;

                            }
                        }
                    });
                }
                break;

            case R.id.OVUserInf:
                startActivity(new Intent(getActivity(), AlterUserInfActivity.class));
                break;
            case R.id.OVCarText:
                break;
            case R.id.OVDayHappy:
                break;
            //天气预报
            case R.id.OVWeather:
                break;
            case R.id.OVAbout:
                startActivity(new Intent(getActivity(), AboutActivity.class));
                break;
        }
    }

    /**
     * 获取当前用户信息
     */
    private void getUserInf() {
        User userInfo = BmobUser.getCurrentUser(User.class);
        if (userInfo != null) {
            String nick = userInfo.getNick();
            Picasso.with(getActivity())
                    .load(nick)
                    .transform(new BlurTransformation(getActivity()))
                    .fit()
                    .into(ivHeaderBg);

            Picasso.with(getActivity())
                    .load(nick)
                    .transform(new CircleTransform())
                    .into(ivmeIcon);

            tvName.setText(userInfo.getUsername());

        } else {
            Picasso.with(getActivity())
                    .load(R.mipmap.ic_mine_header_bg)
                    .transform(new BlurTransformation(getActivity()))
                    .fit()
                    .into(ivHeaderBg);

            Picasso.with(getActivity())
                    .load(R.mipmap.ic_unget)
                    .transform(new CircleTransform())
                    .into(ivmeIcon);

            tvName.setText("欢迎使用智能管家");
        }
    }


    private void changeMyIcon() {

        List<String> list = new ArrayList<>();
        list.add("拍照");
        list.add("相册");

        AnimotionPopupWindow animotionPopupWindow = new AnimotionPopupWindow(getActivity(), list);
        animotionPopupWindow.show();
        animotionPopupWindow.setAnimotionPopupWindowOnClickListener(new AnimotionPopupWindow.AnimotionPopupWindowOnClickListener() {
            @Override
            public void onPopWindowClickListener(int position) {
                switch (position) {
                    case 0:
                        openCamera();
                        break;
                    case 1:
                        openAlbun();
                        break;
                }
            }
        });

    }

    private void openAlbun() {
        takePictureManager = new TakePictureManager(this);
        takePictureManager.setTailor(1, 1, 350, 350);
        takePictureManager.startTakeWayByAlbum();
        takePictureManager.setTakePictureCallBackListener(new TakePictureManager.takePictureCallBackListener() {
            @Override
            public void successful(boolean isTailor, File outFile, Uri filePath) {
                updataMyicon(outFile);
            }

            @Override
            public void failed(int errorCode, List<String> deniedPermissions) {

            }

        });
    }

    private void openCamera() {

        takePictureManager = new TakePictureManager(this);
        takePictureManager.setTailor(1, 1, 350, 350);
        takePictureManager.startTakeWayByCarema();
        takePictureManager.setTakePictureCallBackListener(new TakePictureManager.takePictureCallBackListener() {
            @Override
            public void successful(boolean isTailor, File outFile, Uri filePath) {
                updataMyicon(outFile);
            }

            @Override
            public void failed(int errorCode, List<String> deniedPermissions) {

            }
        });

    }


    private void updataMyicon(final File outFile) {
        final User userInfo = BmobUser.getCurrentUser(User.class);
        //删除当前文件
        BmobFile file = new BmobFile();
        file.setUrl(userInfo.getNick());//此url是上传文件成功之后通过bmobFile.getUrl()方法获取的。
        file.delete(new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    final BmobFile bmobFile = new BmobFile(outFile);
                    bmobFile.uploadblock(new UploadFileListener() {
                        @Override
                        public void done(BmobException e) {
                            if (e == null) {
                                userInfo.setNick(bmobFile.getFileUrl());
                                userInfo.update(new UpdateListener() {
                                    @Override
                                    public void done(BmobException e) {
                                        if (e == null) {
                                            ToastUtils.showToast(getActivity(), "更新用户信息成功:");
                                            getUserInf();
                                        }
                                    }
                                });

                            } else {
                                ToastUtils.showToast(getActivity(), "上传文件失败：" + e.getMessage());
                            }

                        }

                        @Override
                        public void onProgress(Integer value) {
                            // 返回的上传进度（百分比）
                        }
                    });
                } else {
                    ToastUtils.showToast(getActivity(), "失败：" + e.getErrorCode() + "," + e.getMessage());
                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        takePictureManager.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        takePictureManager.attachToActivityForResult(requestCode, resultCode, data);
    }

}
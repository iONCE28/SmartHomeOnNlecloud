package com.zcc.smarthome;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.gyf.barlibrary.ImmersionBar;
import com.zcc.smarthome.activity.BaseActivity;
import com.zcc.smarthome.adapter.MainViewPagerAdapter;
import com.zcc.smarthome.fragment.DevicesFragment;
import com.zcc.smarthome.fragment.HomeFragment;
import com.zcc.smarthome.fragment.MineFragment;
import com.zcc.smarthome.fragment.ScenceFragment;
import com.zcc.smarthome.utils.L;
import com.zcc.smarthome.view.AnimotionPopupAddDevicesWindow;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    private RelativeLayout rlAddDevices;
    private TextView mIv_home_press;
    private TextView mIv_home_normal;
    private TextView mTv_home_normal;
    private TextView mTv_home_press;
    private TextView mIv_fishpond_press;
    private TextView mIv_fishpond_normal;
    private TextView mTv_fishpond_normal;
    private TextView mTv_fishpond_press;
    private TextView mIv_message_normal;
    private TextView mIv_message_press;
    private TextView mTv_message_normal;
    private TextView mTv_message_press;
    private TextView mIv_mine_press;
    private TextView mIv_mine_normal;
    private TextView mTv_mine_normal;
    private TextView mTv_mine_press;
    private ViewPager mViewPager;

    public long exitTime = 0;

    private RelativeLayout all_one, all_two, all_three, all_four;
    private List<android.support.v4.app.Fragment> fragmentList;

    private MainViewPagerAdapter mAdapter;
    private AnimotionPopupAddDevicesWindow animotionPopupAddDevicesWindow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //设置为黑色的状态栏
        ImmersionBar.with(this)
                .statusBarDarkFont(true, 0.2f)
                .init();

        if (getIntent().getBooleanExtra("isLastVersion", false)) {
            //更新APP地逻辑
        }

        bindViews();
        //默认选中第一个
        setTransparency();
    }

    private void bindViews() {

        all_one = findViewById(R.id.all_one);
        all_one.setOnClickListener(this);
        all_two = findViewById(R.id.all_two);
        all_two.setOnClickListener(this);
        all_three = findViewById(R.id.all_three);
        all_three.setOnClickListener(this);
        all_four = findViewById(R.id.all_four);
        rlAddDevices = findViewById(R.id.rlAddDevices);
        all_four.setOnClickListener(this);
        rlAddDevices.setOnClickListener(this);

        mIv_home_press = findViewById(R.id.iv_home_press);
        mIv_home_normal = findViewById(R.id.iv_home_normal);
        mTv_home_normal = findViewById(R.id.tv_home_normal);
        mTv_home_press = findViewById(R.id.tv_home_press);


        mIv_fishpond_press = findViewById(R.id.iv_fishpond_press);
        mIv_fishpond_normal = findViewById(R.id.iv_fishpond_normal);
        mTv_fishpond_normal = findViewById(R.id.tv_fishpond_normal);
        mTv_fishpond_press = findViewById(R.id.tv_fishpond_press);


        mIv_message_normal = findViewById(R.id.iv_message_normal);
        mIv_message_press = findViewById(R.id.iv_message_press);
        mTv_message_normal = findViewById(R.id.tv_message_normal);
        mTv_message_press = findViewById(R.id.tv_message_press);


        mIv_mine_press = findViewById(R.id.iv_mine_press);
        mIv_mine_normal = findViewById(R.id.iv_mine_normal);
        mTv_mine_normal = findViewById(R.id.tv_mine_normal);
        mTv_mine_press = findViewById(R.id.tv_mine_press);


        mViewPager = findViewById(R.id.mViewPager);
        fragmentList = new ArrayList<>();

        fragmentList.add(new HomeFragment());
        fragmentList.add(new DevicesFragment());
        fragmentList.add(new ScenceFragment());
        fragmentList.add(new MineFragment());
        mAdapter = new MainViewPagerAdapter(getSupportFragmentManager(), fragmentList);
        mViewPager.setAdapter(mAdapter);
        mViewPager.addOnPageChangeListener(mListener);
        //默认是显示第一个
        iconSeletor(0);
    }

    /**
     * 显示哪个图标
     *
     * @param position 位置
     */
    private void iconSeletor(int position) {
        mViewPager.setCurrentItem(position, false);
    }


    private ViewPager.OnPageChangeListener mListener = new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            //根据ViewPager滑动位置更改透明度
            int diaphaneity_one = (int) (255 * positionOffset);
            int diaphaneity_two = (int) (255 * (1 - positionOffset));

            switch (position) {
                case 0:
                    mIv_home_normal.getBackground().setAlpha(diaphaneity_one);
                    mIv_home_press.getBackground().setAlpha(diaphaneity_two);

                    mIv_fishpond_normal.getBackground().setAlpha(diaphaneity_two);
                    mIv_fishpond_press.getBackground().setAlpha(diaphaneity_one);

                    mTv_home_normal.setTextColor(Color.argb(diaphaneity_one, 153, 153, 153));
                    mTv_home_press.setTextColor(Color.argb(diaphaneity_two, 255, 197, 27));

                    mTv_fishpond_normal.setTextColor(Color.argb(diaphaneity_two, 153, 153, 153));
                    mTv_fishpond_press.setTextColor(Color.argb(diaphaneity_one, 255, 197, 27));
                    break;

                case 1:
                    mIv_fishpond_normal.getBackground().setAlpha(diaphaneity_one);
                    mIv_fishpond_press.getBackground().setAlpha(diaphaneity_two);

                    mIv_message_normal.getBackground().setAlpha(diaphaneity_two);
                    mIv_message_press.getBackground().setAlpha(diaphaneity_one);

                    mTv_fishpond_normal.setTextColor(Color.argb(diaphaneity_one, 153, 153, 153));
                    mTv_fishpond_press.setTextColor(Color.argb(diaphaneity_two, 255, 197, 27));

                    mTv_message_normal.setTextColor(Color.argb(diaphaneity_two, 153, 153, 153));
                    mTv_message_press.setTextColor(Color.argb(diaphaneity_one, 255, 197, 27));

                    break;

                case 2:
                    mIv_message_normal.getBackground().setAlpha(diaphaneity_one);
                    mIv_message_press.getBackground().setAlpha(diaphaneity_two);

                    mIv_mine_normal.getBackground().setAlpha(diaphaneity_two);
                    mIv_mine_press.getBackground().setAlpha(diaphaneity_one);


                    mTv_message_press.setTextColor(Color.argb(diaphaneity_two, 255, 197, 27));
                    mTv_message_normal.setTextColor(Color.argb(diaphaneity_one, 153, 153, 153));


                    mTv_mine_normal.setTextColor(Color.argb(diaphaneity_two, 153, 153, 153));
                    mTv_mine_press.setTextColor(Color.argb(diaphaneity_one, 255, 197, 27));

                    break;

            }


        }

        @Override
        public void onPageSelected(int position) {

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    /**
     * 把press图片、文字全部隐藏(设置透明度)
     */
    private void setTransparency() {

        mIv_home_normal.getBackground().setAlpha(255);
        mIv_fishpond_normal.getBackground().setAlpha(255);
        mIv_message_normal.getBackground().setAlpha(255);
        mIv_mine_normal.getBackground().setAlpha(255);

        mIv_home_press.getBackground().setAlpha(1);
        mIv_fishpond_press.getBackground().setAlpha(1);
        mIv_message_press.getBackground().setAlpha(1);
        mIv_mine_press.getBackground().setAlpha(1);

        mTv_home_normal.setTextColor(Color.argb(255, 153, 153, 153));
        mTv_fishpond_normal.setTextColor(Color.argb(255, 153, 153, 153));
        mTv_message_normal.setTextColor(Color.argb(255, 153, 153, 153));
        mTv_mine_normal.setTextColor(Color.argb(255, 153, 153, 153));

        mTv_home_press.setTextColor(Color.argb(0, 69, 192, 26));
        mTv_fishpond_press.setTextColor(Color.argb(0, 69, 192, 26));
        mTv_message_press.setTextColor(Color.argb(0, 69, 192, 26));
        mTv_mine_press.setTextColor(Color.argb(0, 69, 192, 26));
    }


    @Override
    public void onClick(View v) {
        setTransparency();
        switch (v.getId()) {
            case R.id.all_one:

                mViewPager.setCurrentItem(0, false);
                mIv_home_press.getBackground().setAlpha(255);
                mTv_home_press.setTextColor(Color.argb(255, 153, 153, 153));
                break;

            case R.id.all_two:

                mViewPager.setCurrentItem(1, false);
                mIv_fishpond_press.getBackground().setAlpha(255);
                mTv_fishpond_press.setTextColor(Color.argb(255, 153, 153, 153));
                break;

            case R.id.all_three:

                mViewPager.setCurrentItem(2, false);
                mIv_message_press.getBackground().setAlpha(255);
                mTv_message_press.setTextColor(Color.argb(255, 153, 153, 153));
                break;

            case R.id.all_four:
                mViewPager.setCurrentItem(3, false);
                mIv_mine_press.getBackground().setAlpha(255);
                mTv_mine_press.setTextColor(Color.argb(255, 153, 153, 153));
                break;

            case R.id.rlAddDevices:

                animotionPopupAddDevicesWindow = new AnimotionPopupAddDevicesWindow(MainActivity.this, new AnimotionPopupAddDevicesWindow.OnPopWindowClickListener() {
                    @Override
                    public void onPopWindowClickListener(View v) {
                        switch (v.getId()) {
                            case R.id.iv_push_photo:
                                L.e("==w", "taobao");
                                animotionPopupAddDevicesWindow.dismiss();
                                break;
                            case R.id.iv_push_resale:
                                L.e("==w", "photo");
                                animotionPopupAddDevicesWindow.dismiss();
                                break;
                        }

                    }
                });
                animotionPopupAddDevicesWindow.show();


                break;

        }
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exit();
            return false;

        }
        return super.onKeyDown(keyCode, event);
    }

    public void exit() {
        if ((System.currentTimeMillis() - exitTime) > 2000) {
            Toast.makeText(this, "再按一次退出程序",
                    Toast.LENGTH_SHORT).show();
            exitTime = System.currentTimeMillis();
        } else {
            finish();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }


}

package ca.com.androidcustomviews.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;

/**
 * Created by hp on 2018/7/18.
 */

    public abstract class BaseActivity extends AppCompatActivity {


        //设置布局View
        protected abstract int initContentView();
        //读取缓存数据
       // protected abstract void readInstanceState(Bundle savedInstanceState);
        //初始化控件
        protected abstract void initView();
        //数据初始化
        protected abstract void initData();
        //点击事件


        @Override
        protected void onCreate(Bundle savedInstanceState) {

            super.onCreate(savedInstanceState);

            //设置布局View
            setContentView(initContentView());
            ButterKnife.bind(this);
//            //读取缓存数据
//            readInstanceState(savedInstanceState);

            //初始化控件
            initView();
            //数据初始化
            initData();

        }



}

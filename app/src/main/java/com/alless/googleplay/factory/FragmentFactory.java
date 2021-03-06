package com.alless.googleplay.factory;

import android.support.v4.app.Fragment;

import com.alless.googleplay.ui.fragment.AppFragment;
import com.alless.googleplay.ui.fragment.CategoryFragment;
import com.alless.googleplay.ui.fragment.GameFragment;
import com.alless.googleplay.ui.fragment.HomeFragment;
import com.alless.googleplay.ui.fragment.HotFragment;
import com.alless.googleplay.ui.fragment.RecommendFragment;
import com.alless.googleplay.ui.fragment.SubjectFragment;

/**
 * Created by Leon on 2017/3/26.
 */

/**
 * 生产Fragment的工厂
 */
public class FragmentFactory {

    private static final int FRAGMENT_HOME = 0;
    private static final int FRAGMENT_APP = 1;
    private static final int FRAGMENT_GAME = 2;
    private static final int FRAGMENT_SUBJECT = 3;
    private static final int FRAGMENT_RECOMMEND = 4;
    private static final int FRAGMENT_CATEGORY = 5;
    private static final int FRAGMENT_HOT = 6;

    //单例模式，一个app中只存在一个FragmentFactory实例
    private static FragmentFactory sFragmentFactory;

    private FragmentFactory() {
    }

    /**
     * 两个非空判断一个锁
     * @return
     */
    public static FragmentFactory getInstance() {
        if (sFragmentFactory == null) {
            //只需要在sFragmentFactory为空时候才加锁创建就可以
            synchronized (FragmentFactory.class) {
                if (sFragmentFactory == null) {//还是要去判断是否为空
                    sFragmentFactory = new FragmentFactory();
                }
            }
        }
        //如果对象已经创建好了，就不要加锁，直接返回
        return sFragmentFactory;
    }


    /**
     * 根据不同的位置生产出不同的Fragment
     * @param position fragment位置
     */
    public Fragment getFragment(int position) {
        switch (position) {
            case FRAGMENT_HOME:
                return new HomeFragment();
            case FRAGMENT_APP:
                return new AppFragment();
            case FRAGMENT_GAME:
                return new GameFragment();
            case FRAGMENT_SUBJECT:
                return new SubjectFragment();
            case FRAGMENT_RECOMMEND:
                return new RecommendFragment();
            case FRAGMENT_CATEGORY:
                return new CategoryFragment();
            case FRAGMENT_HOT:
                return new HotFragment();
        }
        return null;
    }
}

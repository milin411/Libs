package com.mysiga.common.component.lazy;

import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.mysiga.common.component.impl.BaseFragment;


/**
 * 懒加载{@code fragment}，通常用于{@link android.support.v4.view.ViewPager}
 *
 * @author wilson.wu
 */
public abstract class BaseLazyFragment extends BaseFragment {
    /**
     * 是否懒加载过
     */
    private boolean isLazyLoaded;
    /**
     * 是否必要优先加载
     */
    private boolean isNecessaryFirstLoad;

    /**
     * 设置是否必要优先加载，用于懒加载处理
     *
     * @param isNecessaryFirstLoad
     */
    public void setIsNecessaryFirstLoad(boolean isNecessaryFirstLoad) {
        this.isNecessaryFirstLoad = isNecessaryFirstLoad;
    }

    @CallSuper
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        lazyLoad(isNecessaryFirstLoad);
    }

    @CallSuper
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        isLazyLoaded = false;
    }

    /**
     * <ol>
     * <li>在{@link #onCreate(Bundle)}和{@link #onCreateView(LayoutInflater, ViewGroup, Bundle)}之间会被调用</li>
     * <li>在{@code viewpager}中，会被{@link android.support.v4.app.FragmentPagerAdapter#setPrimaryItem(ViewGroup, int, Object)}
     * 或者{@link android.support.v4.app.FragmentStatePagerAdapter#setPrimaryItem(ViewGroup, int, Object)}调用</li>
     * </ol>
     *
     * @param isVisibleToUser
     * @see #setUserVisibleHint(boolean)
     */
    @CallSuper
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            lazyLoad(isVisible());
        }
    }

    /**
     * 懒加载
     *
     * @param canLazyLoad 能否懒加载
     * @see #setUserVisibleHint(boolean)
     */
    @UiThread
    private void lazyLoad(boolean canLazyLoad) {
        if (!isLazyLoaded && canLazyLoad) {
            isLazyLoaded = true;
            onLazyLoad();
        }
    }

    /**
     * 懒加载回调
     *
     * @see #lazyLoad(boolean)
     */
    @UiThread
    protected abstract void onLazyLoad();
}

package com.mysiga.common.component.impl;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

/**
 * 继承自{@link Fragment}，作为APK使用的一个基类fragment
 * <ul>
 * <li>如果要执行{@link FragmentTransaction}，务必调用{@code commitXYZ()}系列方法。
 * 否则，会造成在{@link #onSaveInstanceState(Bundle)}生命周期后执行造成程序crash
 * </ul>
 *
 * @author wilson.wu
 */
public class BaseFragment extends Fragment implements IResumed, ICommitInResume {
    private final Handler mHandler = new Handler();
    private boolean mIsResumedFlag = true;
    /**
     * {@link FragmentTransaction}执行代理
     */
    private CommitProxy<? extends BaseFragment> mCommitProxy;

    @Override
    public boolean isResumedFlag() {
        return mIsResumedFlag;
    }

    @CallSuper
    @Override
    public void onAttach(Context context) {
        if (mCommitProxy != null) {
            mCommitProxy.clean();
        }
        mCommitProxy = new CommitProxy<>(this);

        super.onAttach(context);
    }

    @CallSuper
    @Override
    public void onResume() {
        super.onResume();
        mIsResumedFlag = true;
        commitAllInResume();
    }

    @CallSuper
    @Override
    public void onPause() {
        mIsResumedFlag = false;
        super.onPause();
    }

    @CallSuper
    @Override
    public void onSaveInstanceState(Bundle outState) {
        mIsResumedFlag = false;
        super.onSaveInstanceState(outState);
    }

    @CallSuper
    @Override
    public void onDetach() {
        if (mCommitProxy != null) {
            mCommitProxy.clean();
            mCommitProxy = null;
        }

        super.onDetach();
    }

    /**
     * 让action在UI线程中执行。
     * <ul>
     * <li>如果当前线程是UI线程，那么action将会立即执行</li>
     * <li>如果当前线程是非UI线程，那么action将会{@link Handler}被post到UI线程的消息队列当中，等待执行</li>
     * </ul>
     *
     * @param action
     */
    public final void runOnUiThread(Runnable action) {
        // 判断是否为UI线程
        if (Looper.myLooper() != Looper.getMainLooper()) {
            mHandler.post(action);
        } else {
            action.run();
        }
    }

    @UiThread
    @Override
    public void commitAllInResume() {
        if (mCommitProxy == null) {
            return;
        }
        mCommitProxy.commitAllInResume();
    }

    @UiThread
    @Override
    public void commitInResume(FragmentTransaction transaction) {
        if (mCommitProxy == null) {
            return;
        }
        mCommitProxy.commitInResume(transaction);
    }

    @UiThread
    @Override
    public void commitInNextResume(FragmentTransaction transaction) {
        if (mCommitProxy == null) {
            return;
        }
        mCommitProxy.commitInNextResume(transaction);
    }

    @UiThread
    @Override
    public void commitInResumeAfterClearAll(FragmentTransaction transaction) {
        if (mCommitProxy == null) {
            return;
        }
        mCommitProxy.commitInResumeAfterClearAll(transaction);
    }

    @UiThread
    @Override
    public void commitInNextResumeAfterClearAll(FragmentTransaction transaction) {
        if (mCommitProxy == null) {
            return;
        }
        mCommitProxy.commitInNextResumeAfterClearAll(transaction);
    }

    /**
     * 获取fragment页面名字
     *
     * @return
     */
    public String getFragmentName() {
        return getClass().getName();
    }
}

package com.mysiga.common.component.impl;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;

/**
 * 基类{@link FragmentActivity activity}
 * <ul>
 * <li>如果要执行{@link FragmentTransaction}，务必调用{@code commitXYZ()}系列方法。
 * 否则，会造成为在{@link #onSaveInstanceState(Bundle)}生命周期后执行造成程序crash，
 * </ul>
 *
 * @author wilson.wu
 */
public class BaseActivity extends FragmentActivity implements IResumed, ICommitInResume {
    private boolean mIsResumedFlag = true;
    /**
     * {@link FragmentTransaction}执行代理
     */
    private CommitProxy<? extends BaseActivity> mCommitProxy;

    @Override
    public boolean isResumedFlag() {
        return mIsResumedFlag;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (mCommitProxy != null) {
            mCommitProxy.clean();
        }
        mCommitProxy = new CommitProxy<>(this);

        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mIsResumedFlag = true;
        commitAllInResume();
    }

    @Override
    protected void onPause() {
        mIsResumedFlag = false;
        super.onPause();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        mIsResumedFlag = false;
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onDestroy() {
        if (mCommitProxy != null) {
            mCommitProxy.clean();
            mCommitProxy = null;
        }

        super.onDestroy();
    }

    @Override
    public void commitAllInResume() {
        if (mCommitProxy == null) {
            return;
        }
        mCommitProxy.commitAllInResume();
    }

    @Override
    public void commitInResume(FragmentTransaction transaction) {
        if (mCommitProxy == null) {
            return;
        }
        mCommitProxy.commitInResume(transaction);
    }

    @Override
    public void commitInNextResume(FragmentTransaction transaction) {
        if (mCommitProxy == null) {
            return;
        }
        mCommitProxy.commitInNextResume(transaction);
    }

    @Override
    public void commitInResumeAfterClearAll(FragmentTransaction transaction) {
        if (mCommitProxy == null) {
            return;
        }
        mCommitProxy.commitInResumeAfterClearAll(transaction);
    }

    @Override
    public void commitInNextResumeAfterClearAll(FragmentTransaction transaction) {
        if (mCommitProxy == null) {
            return;
        }
        mCommitProxy.commitInNextResumeAfterClearAll(transaction);
    }
}

package com.mysiga.common.component.impl;

import android.support.annotation.UiThread;
import android.support.v4.app.FragmentTransaction;

import java.util.LinkedList;
import java.util.Queue;

/**
 * 执行{@link FragmentTransaction}代理 <li>线程安全<br>
 * <strong>由于{@code transaction}添加到{@link #mTransactionQueue}
 * 可能是异步过程，为了确保fragment事务正常，需要仔细考虑调用时机问题</strong>
 *
 * @param <T> {@link IResumed}子类
 * @author wilson.wu
 */
public final class CommitProxy<T extends IResumed> implements ICommitInResume {

    private final Queue<FragmentTransaction> mTransactionQueue = new LinkedList<>();
    private T mResumedCtx;

    public CommitProxy(T resumedCtx) {
        this.mResumedCtx = resumedCtx;
    }

    @UiThread
    @Override
    public void commitAllInResume() {
        if (!mResumedCtx.isResumedFlag()) {
            return;
        }

        while (mResumedCtx.isResumedFlag() && !mTransactionQueue.isEmpty()) {
            FragmentTransaction fragmentTransaction = mTransactionQueue.poll();
            if (fragmentTransaction != null && !fragmentTransaction.isEmpty()) {
                fragmentTransaction.commit();
            }
        }
    }

    @UiThread
    @Override
    public void commitInResume(final FragmentTransaction transaction) {
        if (mResumedCtx.isResumedFlag()) {
            if (transaction == null || transaction.isEmpty()) {
                return;
            }
            transaction.commit(); // Fragment事务操作只能在UI线程中进行
            return;
        }

        commitInNextResume(transaction);
    }

    @UiThread
    @Override
    public void commitInNextResume(FragmentTransaction transaction) {
        if (transaction == null || transaction.isEmpty()) {
            return;
        }

        mTransactionQueue.add(transaction);
    }

    @UiThread
    @Override
    public void commitInResumeAfterClearAll(final FragmentTransaction transaction) {
        if (!mTransactionQueue.isEmpty()) {
            mTransactionQueue.clear();
        }

        if (!mResumedCtx.isResumedFlag()) {
            if (transaction == null || transaction.isEmpty()) {
                return;
            }

            mTransactionQueue.add(transaction);
            return;
        }

        if (transaction == null || transaction.isEmpty()) {
            return;
        }

        transaction.commit(); // Fragment事务操作只能在UI线程中进行
    }

    @UiThread
    @Override
    public void commitInNextResumeAfterClearAll(FragmentTransaction transaction) {
        if (!mTransactionQueue.isEmpty()) {
            mTransactionQueue.clear();
        }

        if (transaction == null || transaction.isEmpty()) {
            return;
        }

        mTransactionQueue.add(transaction);
    }

    /**
     * 代理事务清理
     */
    @UiThread
    public void clean() {
        if (!mTransactionQueue.isEmpty()) {
            mTransactionQueue.clear();
            mResumedCtx = null;
        }
    }
}

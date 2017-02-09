package com.mysiga.common.component.impl;

import android.support.v4.app.FragmentTransaction;

/**
 * 在{@code resumed}状态中执行{@link FragmentTransaction}
 *
 * @author wilson.wu
 */
interface ICommitInResume {

    /**
     * 在{@code resumed}状态中执行所有的{@link FragmentTransaction}
     */
    void commitAllInResume();

    /**
     * 在{@code resumed}状态中执行{@code transaction}
     *
     * @param transaction
     */
    void commitInResume(FragmentTransaction transaction);

    /**
     * 在下一次{@code resumed}状态中执行{@code transaction}
     *
     * @param transaction
     */
    void commitInNextResume(FragmentTransaction transaction);

    /**
     * 清空所有的待执行{@link FragmentTransaction}后，在{@code resumed}状态中执行
     * {@code transaction} <br/>
     * <li>建议在生命周期{@code onCreate}中使用，避免因为一些不可知的因素造成{@code fragment transaction}
     * 影响，比如系统会因为系统内存不足，而将进入后台的app杀死，这时候点击图标回到那个app，那么那个app将会重启，并从原先保存的
     * {@code savedInstanceState}恢复数据
     *
     * @param transaction
     */
    void commitInResumeAfterClearAll(FragmentTransaction transaction);

    /**
     * 清空所有的待执行{@link FragmentTransaction}后，在一次{@code resumed}状态中执行
     * {@code transaction}
     *
     * @param transaction
     */
    void commitInNextResumeAfterClearAll(FragmentTransaction transaction);

}

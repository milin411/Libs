package com.mysiga.common.component.impl;

/**
 * {@code resumed}接口
 *
 * @author wilson.wu
 */
interface IResumed {
    /**
     * 是否{@code resumed}。<br/>
     * <li>这里方法不叫isResumed的原因是如果交由activity实现，会导致运行时报错：<strong>isResumed
     * overrides final Landroid/app/Activity;.isResumed</strong>
     *
     * @return true：activity已{@code resumed}；反之，未{@code resumed}
     */
    boolean isResumedFlag();
}

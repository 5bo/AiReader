package com.hdmb.ireader.utils;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * 作者    武博
 * 时间    2016/6/7 0007 17:29
 * 文件    Yolanda
 * 描述
 */
public class RxUtils {

    public static void unsubscribeIfNotNull(Subscription subscription) {
        if (subscription != null && !subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
    }

    public static CompositeSubscription getNewCompositeSubIfUnsubscribed(CompositeSubscription subscription) {
        if (subscription == null || subscription.isUnsubscribed()) {
            return new CompositeSubscription();
        }
        return subscription;
    }
}

package com.ljz.plat.android.mwidget.data;

import androidx.annotation.NonNull;

public interface MyCallback<T> {
    /**
     * 返回结果
     * @param result 结果
     */
    void onResult(T result);
    /**
     * 返回异常信息
     * @param exception 异常
     */
    void onException(@NonNull Exception exception);
}

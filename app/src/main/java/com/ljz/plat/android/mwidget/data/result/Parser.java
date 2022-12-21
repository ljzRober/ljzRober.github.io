package com.ljz.plat.android.mwidget.data.result;

public interface Parser<T> {

  T parse(String str) throws Exception;

}

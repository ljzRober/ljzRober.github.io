package com.ljz.plat.android.mwidget.data;

public interface Transformer<M, F, S> {

    M transform(F first, S second, M main);

}

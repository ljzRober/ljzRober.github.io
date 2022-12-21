package com.ljz.plat.android.mwidget.data;

import androidx.annotation.NonNull;
import androidx.lifecycle.Observer;

public class MergedObserver<M, F, S> extends Observers<M> {
    private Transformer<M, F, S> mTransformer;
    @NonNull
    private Observers<F> first;
    @NonNull
    private Observers<S> second;
    @NonNull
    private Observers<M> main;

    public MergedObserver() {}

    public MergedObserver(@NonNull Observers<F> first,
                          @NonNull Observers<S> second,
                          @NonNull Observers<M> main) {
        this.first = first;
        this.second = second;
        this.main = main;
    }

    public void setObservers(@NonNull Observers<F> first,
                             @NonNull Observers<S> second,
                             @NonNull Observers<M> main) {
        this.first = first;
        this.second = second;
        this.main = main;
    }

    public void setTransformer(@NonNull Transformer<M, F, S> transformer) {
        this.mTransformer = transformer;
    }

    @Override
    public void observe(@NonNull Observer<M> observer) {
        super.observe(observer);
        setValue(mTransformer.transform(first.getValue(),
                second.getValue(),
                main.getValue()));
    }
}

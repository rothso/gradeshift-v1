package com.rothanak.grades.ui;

import java.lang.ref.WeakReference;

public abstract class Presenter<V> {

    protected WeakReference<V> view;

    public void attachView(V view) {
        this.view = new WeakReference<>(view);
    }

    public void detachView() {
        if (view != null) {
            view.clear();
            view = null;
        }
    }

    protected V getView() {
        return view.get();
    }

    protected boolean isViewAttached() {
        return view != null && view.get() != null;
    }

}

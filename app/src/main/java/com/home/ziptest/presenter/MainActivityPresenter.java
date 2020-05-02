package com.home.ziptest.presenter;

import com.home.ziptest.contract.MainActivityContract;

public class MainActivityPresenter implements MainActivityContract.Presenter {
    private MainActivityContract.View view;

    public MainActivityPresenter(MainActivityContract.View view) {
        this.view = view;
    }

    @Override
    public void init() {

    }
}

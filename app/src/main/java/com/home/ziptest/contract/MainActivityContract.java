package com.home.ziptest.contract;

public interface MainActivityContract {
    interface View {
        void init();
        void checkPermission();
    }

    interface Presenter {
        void init();
        void copyFileToMobile(String filename);
    }
}

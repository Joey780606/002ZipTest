package com.home.ziptest.contract;

public interface MainActivityContract {
    interface View {
        void init();
        void checkPermission();
        void showVer(String version);
    }

    interface Presenter {
        void init();
        void copyFileToMobile(String filename);
        void getFileVersion();
    }
}

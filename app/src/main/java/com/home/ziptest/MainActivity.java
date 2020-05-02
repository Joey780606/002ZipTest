package com.home.ziptest;

/**
 * Created by JoeyYang 2020/05/02
 *   Reference info:
 *   1. How to create assets and join zip file into assets
 *      https://www.youtube.com/watch?v=Xbifoj_x9W4
 *
 *   2. Create MVP mode
 *   Reference info:  https://www.jianshu.com/p/881d705e86b1
 *      a. Build related folder and file.  contractor > MainActivityContract.java
 *      b. MainActivity.java need implements MainActivityContract.View, and override MainActivityContract.View's  function.
 *      c. Declare MainActivityContract.Presenter variable in MainActivity.java, and constructor presenter in the onCreate function
 *      d. Build related folder and file.  Folder presenter  > MainActivityContract.java.  It need to  implements MainActivityContract.Presenter and override Presenter's function.
 */

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.home.ziptest.contract.MainActivityContract;
import com.home.ziptest.presenter.MainActivityPresenter;

public class MainActivity extends AppCompatActivity implements MainActivityContract.View {
    private MainActivityContract.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        presenter = new MainActivityPresenter(this);
    }

    @Override
    public void init() {

    }
}

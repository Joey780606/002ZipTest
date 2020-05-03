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
 *   Afterward control:
 *      a. MainActivity.java do presenter.init(); at first, then this will call view's function.
 *      b. Then MainActivity.java can do presenter function to control model and UI via view's function
 *
 *    About zip file:
 *    https://www.youtube.com/watch?v=AUeTkPc4iB4
 *    https://stackoom.com/question/3WuVa/Android-ZipFile-java-util-zip-ZipException-%E6%96%87%E4%BB%B6%E5%A4%AA%E7%9F%AD%E8%80%8C%E4%B8%8D%E8%83%BD%E6%88%90%E4%B8%BAzip%E6%96%87%E4%BB%B6
 *    http://www.41post.com/3985/programming/android-loading-files-from-the-assets-and-raw-folders
 */

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.home.ziptest.contract.MainActivityContract;
import com.home.ziptest.presenter.MainActivityPresenter;

public class MainActivity extends AppCompatActivity implements MainActivityContract.View {
    private static final String TAG = MainActivity.class.getSimpleName();
    private MainActivityContract.Presenter presenter;

    private Button mbtnCopyZipFile;
    private boolean iWork = false;
    private static final int PERMISSIONS_WRITE_EXTERNAL_STORAGE = 100;
    private int mCheckBLEPermission = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        presenter = new MainActivityPresenter(this, this);
        presenter.init();
    }

    @Override
    public void init() {
        mbtnCopyZipFile = (Button) findViewById(R.id.btnCopyZipFile);

        mbtnCopyZipFile.setOnClickListener(new BtnCopyZipFileOnClickListener());
    }

    @Override
    public void checkPermission() {
        int permissionWRITE_EXTERNAL_STORAGE = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        Log.v(TAG, "permissionCheck check:" + permissionWRITE_EXTERNAL_STORAGE);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {  // Need < 6.0
            iWork = true;
        } else if (permissionWRITE_EXTERNAL_STORAGE != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSIONS_WRITE_EXTERNAL_STORAGE);
        } else if (permissionWRITE_EXTERNAL_STORAGE == PackageManager.PERMISSION_GRANTED) {
            iWork = true;
        }

    }

    private class BtnCopyZipFileOnClickListener implements android.view.View.OnClickListener {    // Important, implements parts
        @Override
        public void onClick(View view) {
            presenter.copyFileToMobile("Mastrad_probe_ota_fileware_v3.00.zip");

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        int ivalue = 0;
        mCheckBLEPermission = 0;
        switch (requestCode) {
            case PERMISSIONS_WRITE_EXTERNAL_STORAGE: {
                for (int i = 0; i < permissions.length; i++) {
                    if (ivalue == 0) {
                        if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
                            ivalue = -1;
                        }
                    }

                    Log.v(TAG, "PermissionResult:" + i + " , " + permissions[i] + " , " + grantResults[i]);
                }
                // If request is cancelled, the result arrays are empty.
                if (permissions.length > 0 && ivalue == 0) {
                    mCheckBLEPermission = 1;
                }
                break;
            }

            default:
                break;
        }
        if (mCheckBLEPermission == 0) {
            Toast.makeText(MainActivity.this, "Don't accept write external storage permission!", Toast.LENGTH_SHORT).show();
        }
    }
}

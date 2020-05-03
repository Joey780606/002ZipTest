package com.home.ziptest.presenter;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.Environment;
import android.widget.Toast;

import com.home.ziptest.contract.MainActivityContract;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class MainActivityPresenter implements MainActivityContract.Presenter {
    private MainActivityContract.View view;
    private Context mContext;

    public MainActivityPresenter(MainActivityContract.View view, Context context) {
        this.view = view;
        this.mContext = context;
    }

    @Override
    public void init() {
        view.init();
        view.checkPermission();
    }

    @Override
    public void copyFileToMobile(String filename) {
        copyAsset(filename);
    }

    private void copyAsset(String filename) {
        String dirPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/MyFiles";  // Target file place directory
        File dir = new File(dirPath);
        if(!dir.exists()) {
            dir.mkdir();
        }
        AssetManager assetManager = mContext.getAssets();
        InputStream in = null;
        OutputStream out = null;
        try {
            in = assetManager.open(filename);
            File outFile = new File(dirPath, filename);
            out = new FileOutputStream(outFile);
            copyFile(in, out);
            Toast.makeText(mContext, "File saved!", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(mContext, "File failed!", Toast.LENGTH_SHORT).show();
        } finally {
            if(in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void copyFile(InputStream in, OutputStream out) throws IOException {    // throws IOException
        byte[] buffer = new byte[1024];
        int read;
        while((read = in.read(buffer)) != -1) {
            out.write(buffer, 0, read);
        }
    }
}

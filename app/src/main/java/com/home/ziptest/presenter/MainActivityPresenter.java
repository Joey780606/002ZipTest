package com.home.ziptest.presenter;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.Environment;
import android.util.Log;
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

    private static final String TAG = MainActivityPresenter.class.getSimpleName();
    private static final String R_FILE_TITLE = "JoeyTest_R_v";
    private static final String P_FILE_TITLE = "JoeyTest_P_v";

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

    @Override
    public void getFileVersion() {
        AssetManager assetManager = mContext.getAssets();
        String sResult = "";
        try {
            String[] files = assetManager.list("");     // If "" mean root directory, if have text, ex:"abc" is mean get the file from "abc" directory

            for(String file_name: files) {
                Log.v(TAG, "Get file: " + file_name);
                String sVerInfo = "";

                sVerInfo = fCheckDeviceVer(file_name, R_FILE_TITLE);
                sResult += sVerInfo;

                sVerInfo = fCheckDeviceVer(file_name, P_FILE_TITLE);
                sResult += sVerInfo;
            }
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(mContext, "Get file fail", Toast.LENGTH_SHORT).show();
        } finally {
        }
        view.showVer(sResult);
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

    private String fCheckDeviceVer(String fileName, String check_Name) {
        String sResult = "";
        if(fileName.indexOf(check_Name) == 0 && fileName.substring(fileName.length()-4).equals(".zip")) {
            int fileCheckPosition = fileName.indexOf("_v");
            Float file_fwver = 0f;
            boolean isNameCorrect = true;

            String sVer = fileName.substring(fileCheckPosition + 2, fileCheckPosition + 6);
            try {
                file_fwver = Float.parseFloat(sVer) * 100;
            } catch (Exception ex) {
                ex.printStackTrace();
                isNameCorrect = false;
            }
            if(isNameCorrect) {
                if(check_Name.indexOf(R_FILE_TITLE) == 0)
                    sResult = "R ver: " + sVer + ";";
                if(check_Name.indexOf(P_FILE_TITLE) == 0)
                    sResult = "P ver: " + sVer + ";";
            } else
                sResult = "";
        }
        return sResult;
    }
}

package com.example.arun.permissions;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by arun on 18/5/17.
 */
public class BaseActivity extends AppCompatActivity {

    private int mReqCode;
    private PermissionStatusListener mPermissionStatusListener;
      /**
       * this method handle request permission for an activity
       *
       * @param permission array of permissions
       * @param reqCode    request code for an permissions
       */
   /* public void requestPermission(PermissionStatusListener permissionStatusListeners,
                                  String[] permission, int reqCode) {
        mPermissionStatusListener = permissionStatusListeners;
        mReqCode = reqCode;
        ActivityCompat.requestPermissions(this, permission, reqCode);
    }*/

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        PermissionHandler.getInstance().onRequestPermissionsResult(requestCode, permissions, grantResults);
        /*boolean isTempDenied = false;
        boolean isPermDenied = false;
        for (String permission : permissions) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, permission)) {
                isTempDenied = true;
            } else {
                if (ActivityCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED) {
                    Log.e("allowed", permission);
                } else {
                    //set to never ask again
                    isPermDenied = true;
                    Log.e("set to never ask again", permission);
                    //do something here.
                }
            }
        }

        if (isTempDenied) {
            mPermissionStatusListener.showStatusMsg(isTempDenied);
        } else if (isPermDenied) {
            mPermissionStatusListener.showStatusMsg(isTempDenied);
        } else {
            mPermissionStatusListener.onSuccessStatusMsg();
        }*/

    }

    public boolean isPermissionSuccess(String permission) {
        return ActivityCompat.checkSelfPermission(this, permission) == 0;
    }
}
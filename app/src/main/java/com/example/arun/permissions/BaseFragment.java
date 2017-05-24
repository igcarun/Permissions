package com.example.arun.permissions;

import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;

/**
 * Created by arun on 23/5/17.
 */

public class BaseFragment extends Fragment {

    private int mReqCode;
    private PermissionStatusListener mPermissionStatusListener;
    /**
     * this method handle request permission for an activity
     *
     * @param permission array of permissions
     * @param reqCode    request code for an permissions
     */
    public void requestPermission(PermissionStatusListener permissionStatusListeners,
                                  String[] permission, int reqCode) {
        mPermissionStatusListener = permissionStatusListeners;
        mReqCode = reqCode;
        requestPermissions(permission, reqCode);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        boolean isTempDenied = false;
        boolean isPermDenied = false;
        for (String permission : permissions) {
            if (shouldShowRequestPermissionRationale(permission)) {
                isTempDenied = true;
            } else {
                if (ContextCompat.checkSelfPermission(getContext(),permission) == PackageManager.PERMISSION_GRANTED) {
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
        }

    }

    public boolean isPermissionSuccess(String permission) {
        return ContextCompat.checkSelfPermission(getContext(), permission) == 0;
    }
}

package com.example.arun.permissions;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by arun on 18/5/17.
 */

public class MainFragment extends BaseFragment implements PermissionStatusListener {

    private static final int PERMISSION_SETTINGS_REQ_CODE = 101;
    private int PERMISSION_REQ_CODE = 102;
    private CoordinatorLayout mParentLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, null);
        Log.i("MainFragment", "Main fragment called");
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mParentLayout = (CoordinatorLayout) getView().findViewById(R.id.fragment_main_cl);
        Log.i("MainFragment", "On Activity Created");
        permissionList();
    }

    private void permissionList() {
        boolean isPermissionsSuccess = true;
        List<String> stringList = new ArrayList<>();

        if (!isPermissionSuccess(Manifest.permission.CAMERA) ) {
            isPermissionsSuccess = false;
            stringList.add(Manifest.permission.CAMERA);
        }

        if (!isPermissionSuccess(Manifest.permission.ACCESS_FINE_LOCATION)) {
            isPermissionsSuccess = false;
            stringList.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }

        if (!isPermissionSuccess(Manifest.permission.READ_EXTERNAL_STORAGE)) {
            isPermissionsSuccess = false;
            stringList.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        }

        if (isPermissionsSuccess) {
            Toast.makeText(getContext(), "All permissions success", Toast.LENGTH_SHORT).show();
        } else {
            PermissionHandler.getInstance().requestPermission(getActivity(), this)
                    .setRequestPermissions(stringList.toArray(new String[0]))
                    .setRequestCode(PERMISSION_REQ_CODE)
                    .builder();
        }
    }

    @Override
    public void showStatusMsg(final boolean isTempDenied) {
        Snackbar.make(mParentLayout, "Need Permission to continue",
                Snackbar.LENGTH_INDEFINITE).setAction("Continue", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isTempDenied) {
                    Log.i("MainFragment", "Show status message called");
                    permissionList();
                } else {
                    Intent intent = new Intent();
                    intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                    Uri uri = Uri.fromParts("package", getActivity().getPackageName(), null);
                    intent.setData(uri);
                    startActivityForResult(intent, PERMISSION_SETTINGS_REQ_CODE);
                }
            }
        }).show();
    }

    @Override
    public void onSuccessStatusMsg() {
        Toast.makeText(getContext(), "Super all permissions are success", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PERMISSION_SETTINGS_REQ_CODE) {
            Log.i("MainFragment", "on Activity Result called");
            permissionList();
        }
    }

    /*public boolean isPermissionSuccess(String permission) {
        return ContextCompat.checkSelfPermission(getContext(), permission) == 0;
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
            showStatusMsg(isTempDenied);
        } else if (isPermDenied) {
            showStatusMsg(isTempDenied);
        } else {
            onSuccessStatusMsg();
        }
    }*/
}

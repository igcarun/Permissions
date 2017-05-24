package com.example.arun.permissions;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity implements PermissionStatusListener {

    private int PERMISSION_REQ_CODE = 78;
    private CoordinatorLayout mParentLayout;
    private int PERMISSION_SETTINGS_REQ_CODE = 79;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mParentLayout = (CoordinatorLayout) findViewById(R.id.activity_main_cl);
        permissionList();
    }

    private void permissionList() {
        boolean isPermissionsSuccess = true;
        List<String> stringList = new ArrayList<>();

        if (!isPermissionSuccess(Manifest.permission.CAMERA) ) {
            isPermissionsSuccess = false;
            stringList.add(Manifest.permission.CAMERA);
        }

        if (!isPermissionSuccess(Manifest.permission.READ_EXTERNAL_STORAGE)) {
            isPermissionsSuccess = false;
            stringList.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        }

        if (isPermissionsSuccess) {
            Toast.makeText(this, "All permissions success", Toast.LENGTH_SHORT).show();
        } else {
            requestPermission(this, stringList.toArray(new String[0]), PERMISSION_REQ_CODE);
        }
    }

    @Override
    public void showStatusMsg(final boolean isTempDenied) {
       Snackbar.make(mParentLayout, "Need Permission to continue",
                Snackbar.LENGTH_INDEFINITE).setAction("Continue", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isTempDenied) {
                    permissionList();
                } else {
                    Intent intent = new Intent();
                    intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                    Uri uri = Uri.fromParts("package", getPackageName(), null);
                    intent.setData(uri);
                    startActivityForResult(intent, PERMISSION_SETTINGS_REQ_CODE);
                }
            }
        }).show();
    }

    @Override
    public void onSuccessStatusMsg() {
        Toast.makeText(this, "Super all permissions are success", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PERMISSION_SETTINGS_REQ_CODE) {
            permissionList();
        }
    }
}
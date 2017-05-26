package com.example.arun.permissions;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements PermissionStatusListener {

    private int PERMISSION_REQ_CODE = 78;
    private CoordinatorLayout mParentLayout;
    private int PERMISSION_SETTINGS_REQ_CODE = 79;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mParentLayout = (CoordinatorLayout) findViewById(R.id.activity_main_cl);
        permissionList();
        Log.i("MainActivity", "onCreate Called");
       /* if(savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.activity_main_fl, new MainFragment()).commit();
        } else {
            Log.i("MainActivity", savedInstanceState.toString());
        }*/
    }

    private void permissionList() {
        List<String> stringList = new ArrayList<>();
        stringList.add(Manifest.permission.CAMERA);
        stringList.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        stringList.add(Manifest.permission.ACCESS_FINE_LOCATION);
        PermissionHandler.getInstance().requestPermission(this, this)
                .setRequestPermissions(stringList.toArray(new String[0]))
                .setRequestCode(PERMISSION_REQ_CODE)
                .builder();
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

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        PermissionHandler.getInstance().onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
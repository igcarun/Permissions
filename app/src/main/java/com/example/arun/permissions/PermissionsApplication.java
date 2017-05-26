package com.example.arun.permissions;

import android.app.Application;

/**
 * Created by arun on 25/5/17.
 */

public class PermissionsApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        PermissionHandler.permissionInitialize();
    }
}

package com.example.arun.permissions;

/**
 * Created by arun on 22/5/17.
 */

public interface PermissionStatusListener {

    void showStatusMsg(boolean isTempDenied);

    void onSuccessStatusMsg();
}

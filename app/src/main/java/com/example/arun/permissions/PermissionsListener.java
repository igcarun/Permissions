package com.example.arun.permissions;

import android.support.annotation.NonNull;

/**
 * Created by arun on 24/5/17.
 */

public interface PermissionsListener {

    void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults);
}

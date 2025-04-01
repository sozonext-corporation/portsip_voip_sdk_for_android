package com.portsip.sipsample.util;

import android.app.Activity;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import com.portsip.sipsample.MyDeviceAdminReceiver;

public class KioskUtils {

    private final Context context;
    private final ComponentName deviceAdmin;
    private final DevicePolicyManager dpm;

    public KioskUtils(Context context) {
        this.context = context;
        this.deviceAdmin = new ComponentName(context, MyDeviceAdminReceiver.class);
        this.dpm = (DevicePolicyManager) context.getSystemService(Context.DEVICE_POLICY_SERVICE);
    }

    private boolean hasDeviceOwnerPermission() {
        return dpm.isAdminActive(deviceAdmin) && dpm.isDeviceOwnerApp(context.getPackageName());
    }

    public void setLockTaskPackage() {
        dpm.setLockTaskPackages(deviceAdmin, new String[]{context.getPackageName()});
    }

    public void start(Activity activity) {
        if (hasDeviceOwnerPermission()) {
            activity.startLockTask();
        }
    }

    public void stop(Activity activity) {
        if (hasDeviceOwnerPermission()) {
            activity.stopLockTask();
        }
    }

}
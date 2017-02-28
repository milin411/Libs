package com.mysiga.common.utils;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.os.Build;

/**
 * Created by wilson.wu on 2017/2/28.
 */

public class SystemUtils {

    /**
     * you must add permission {@link android.Manifest.permission#BLUETOOTH},{@link android.Manifest.permission#BLUETOOTH_ADMIN},
     * if Build.VERSION.SDK_INT>=Build.VERSION_CODES.JELLY_BEAN_MR2,add {@link android.Manifest.permission#BLUETOOTH_PRIVILEGED}
     *
     * @param context
     * @return
     */
    public static boolean isOpenBluetooth(Context context) {
        BluetoothAdapter bluetoothAdapter = null;
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            final BluetoothManager bluetoothManager = (BluetoothManager) context.getSystemService(Context.BLUETOOTH_SERVICE);
            bluetoothAdapter = bluetoothManager.getAdapter();
        }
        if (bluetoothAdapter == null) {
            throw new NullPointerException("bluetooth device not support");
        }
        return bluetoothAdapter.isEnabled();
    }
    // TODO: 2017/2/28 调整打开蓝牙页面
}

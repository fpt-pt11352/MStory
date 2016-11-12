package vn.edu.poly.mstory.object.handle.backgroundtask;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

/**
 * Created by Tu on 11/12/2016.
 */

public class CheckingInternet extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        final ConnectivityManager connMgr = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        final android.net.NetworkInfo wifi = connMgr
                .getNetworkInfo(ConnectivityManager.TYPE_WIFI);

        final android.net.NetworkInfo mobile = connMgr
                .getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        if (mobile.isAvailable() && mobile.isConnected()) {
            Toast.makeText(context, "Mạng khả dụng.\n Kết nối 3G/4G", Toast.LENGTH_SHORT).show();
        } else if (mobile.isAvailable()) {
            Toast.makeText(context, "Mạng khả dụng.\n Chưa kết nối 3G/4G", Toast.LENGTH_SHORT).show();

        } else if (wifi.isAvailable() && wifi.isConnected()) {
            Toast.makeText(context, "Mạng khả dụng.\n Kết nối wifi", Toast.LENGTH_SHORT).show();
        } else if (wifi.isAvailable()) {
            Toast.makeText(context, "Mạng khả dụng.\n Chưa kết nối wifi", Toast.LENGTH_SHORT).show();
        }
    }

    public boolean checkConnection(Context context) {
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = manager.getActiveNetworkInfo();
        if (info != null && info.isConnected()) {
            return true;
        }
        return false;
    }
}

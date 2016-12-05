package vn.edu.poly.mcomics.object.handle.backgroundtask;

import android.content.Context;
import android.net.ConnectivityManager;

/**
 * Created by Tu on 12/5/2016.
 */

public class checkIntenet {
    public checkIntenet(Context context) {
    }

    public boolean Check(Context context) {
        ConnectivityManager conMgr = (ConnectivityManager) context.getSystemService(Context
                .CONNECTIVITY_SERVICE);
        if (conMgr.getActiveNetworkInfo() != null
                && conMgr.getActiveNetworkInfo().isAvailable()
                && conMgr.getActiveNetworkInfo().isConnected()) {
            return true;
        }
        return false;
    }
}

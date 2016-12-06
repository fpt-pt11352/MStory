package vn.edu.poly.mcomics.object.handle.other;

import android.app.Activity;
import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;

import vn.edu.poly.mcomics.R;

/**
 * Created by lucius on 06/12/2016.
 */

public class SQLiteHandle {
    private SQLiteDatabase db;
    public SQLiteHandle(Activity activity, DatabaseErrorHandler databaseErrorHandler){
        db = activity.openOrCreateDatabase(activity.getResources().getString(R.string.mcomics_database), Context.MODE_PRIVATE, null, databaseErrorHandler);
        createTable();
    }

    public void createTable(){
        db.execSQL("CREATE TABLE IF NOT EXISTS setting(" +
                "brightness int," +
                "view_mode bit" +
                ")");
    }
}

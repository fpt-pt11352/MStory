package vn.edu.poly.mcomics.object.handle.other;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.widget.LinearLayoutManager;

import vn.edu.poly.mcomics.R;

/**
 * Created by lucius on 06/12/2016.
 */

public final class SettingHandle {
    private SQLiteDatabase db;
    public final int VERTICAL = LinearLayoutManager.VERTICAL;
    public final int HORIZONTAL = LinearLayoutManager.HORIZONTAL;

    public SettingHandle(Activity activity) {
        db = activity.openOrCreateDatabase(activity.getResources().getString(R.string.mcomics_database), Context.MODE_PRIVATE, null);
        createTable();
    }

    public void createTable() {
        db.execSQL("CREATE TABLE IF NOT EXISTS setting(" +
                "setting_name text," +
                "value text" +
                ")");
        setDefault();
    }

    public void setDefault() {
        Cursor cursor = db.rawQuery("SELECT * FROM setting WHERE setting_name = 'orientation'", null);
        if (cursor.getCount() <= 0) {
            db.execSQL("INSERT INTO setting VALUES ( 'orientation', " + VERTICAL + ")");
        }
    }

    public int getOrientation() {
        Cursor cursor = db.rawQuery("SELECT * FROM setting WHERE setting_name = 'orientation'", null);
        if (cursor.getCount() == 1) {
            cursor.moveToNext();
            Show.log("get", cursor.getInt(1)+"");
            if (cursor.getInt(1) == LinearLayoutManager.VERTICAL) {
                return LinearLayoutManager.VERTICAL;
            } else {
                return LinearLayoutManager.HORIZONTAL;
            }
        }
        return LinearLayoutManager.VERTICAL;
    }

    public void setOrientation(int orientation) {
        Show.log("setOrientation", orientation + "");
        ContentValues vl = new ContentValues();
        vl.put("value", orientation);
        db.update("setting", vl, "setting_name ==?", new String[]{"orientation"});
        //db.execSQL("UPDATE setting SET value= '" + orientation + "' WHERE setting_name = 'orientation'");

    }
}



package vn.edu.poly.mcomics.object.handle.other;

import android.app.Activity;
import android.app.Application;
import android.app.Dialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.view.LayoutInflaterCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Layout;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import vn.edu.poly.mcomics.R;

/**
 * Created by lucius on 11/16/16.
 */

public class NavigationDrawer{
    private LayoutInflater inflater;
    private ViewGroup parent;
    private View view;
    private Activity activity;
    private Dialog dialog;
    private CallbackManager callbackManager;
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;

    public NavigationDrawer(final Activity activity, int layout, ViewGroup viewGroup) {
        this.activity = activity;
        this.inflater = (LayoutInflater.from(activity));
        this.parent = viewGroup;

        view = (inflater.inflate(layout, parent, false));

        ((AppCompatActivity)activity).getSupportActionBar().hide();

        toolbar = (Toolbar) inflater.inflate(R.layout.toolbar, ((ViewGroup) view), false)
                .findViewById(R.id.toolBar);

        ((LinearLayout)view).addView(toolbar, 0);
        ((FrameLayout) parent.findViewById(R.id.root)).addView(view);

        drawerLayout = ((DrawerLayout)parent);

        callbackManager = CallbackManager.Factory.create();
        LoginButton loginButton = (LoginButton)parent.findViewById(R.id.fb_login);
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Toast.makeText(activity, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancel() {

            }
//
            @Override
            public void onError(FacebookException error) {
                Toast.makeText(activity, "Đăng nhập thất bại", Toast.LENGTH_SHORT).show();
            }
        });

        TextView change = (TextView) parent.findViewById(R.id.change);

        clickBrightness();
        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.getWindow().setLayout(1000, 150);
                dialog.show();
            }
        });

        createNavifationButton();
    }

    public void createNavifationButton(){
        ((ImageView)toolbar.findViewById(R.id.navigation_button)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showHideNavigation();
            }
        });
    }

    public void showHideNavigation(){
        if(drawerLayout.isDrawerOpen(Gravity.START))
            drawerLayout.closeDrawer(Gravity.START);
        else
            drawerLayout.openDrawer(Gravity.START);
    }

    public void clickBrightness() {
        dialog = new Dialog(activity);
        dialog.setTitle("Thay đổi độ sáng");
        dialog.setContentView(R.layout.change_brightness);
        SeekBar seekBar = (SeekBar) dialog.findViewById(R.id.sbBrightness);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                Settings.System.putInt(activity.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS, progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
}

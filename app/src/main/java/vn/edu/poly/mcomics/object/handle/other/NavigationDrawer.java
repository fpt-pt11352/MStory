package vn.edu.poly.mcomics.object.handle.other;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Point;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import vn.edu.poly.mcomics.R;
import vn.edu.poly.mcomics.object.handle.social.FacebookAPI;

/**
 * Created by lucius on 11/16/16.
 */

public class NavigationDrawer implements View.OnClickListener {
    private LayoutInflater inflater;
    private ViewGroup parent;
    private View mainView;
    private Activity activity;
    private Dialog dialog;
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private FacebookAPI facebookAPI;
    private LoginButton loginButton;
    private TextView txv_log;
    protected SeekBar seekBar;

    private final int REQUEST_CODE = 200;
    private TableRow btn_likes, btn_shares, btn_log, btn_brightness, btn_viewMode;
    private final float ONE_PERCENT = 255f / 100f;

    public NavigationDrawer(final Activity activity, int layout, ViewGroup viewGroup) {
        this.activity = activity;
        this.inflater = (LayoutInflater.from(activity));
        this.parent = viewGroup;
        mappings(layout);

        ((LinearLayout) mainView).addView(toolbar, 0);
        ((FrameLayout) parent.findViewById(R.id.root)).addView(mainView);

        createFbLoginButton();
        setButtonOnClick();
        createBrightnessDialog();

        new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {
                checkLogin();
            }
        };

        createNavigationButton();
    }

    public void mappings(int layout) {
        facebookAPI = new FacebookAPI(activity);
        facebookAPI.init();
        toolbar = (Toolbar) inflater.inflate(R.layout.view_toolbar, ((ViewGroup) mainView), false).findViewById(R.id.toolBar);
        drawerLayout = ((DrawerLayout) parent);
        mainView = (inflater.inflate(layout, parent, false));
        loginButton = (LoginButton) parent.findViewById(R.id.fb_login);
        btn_likes = (TableRow) parent.findViewById(R.id.btn_like);
        btn_shares = (TableRow) parent.findViewById(R.id.btn_share);
        btn_log = (TableRow) parent.findViewById(R.id.btn_log);
        btn_brightness = (TableRow) parent.findViewById(R.id.btn_brightness);
        btn_viewMode = (TableRow) parent.findViewById(R.id.btn_viewMode);
        txv_log = (TextView) parent.findViewById(R.id.txv_log);
    }

    public void setButtonOnClick() {
        btn_likes.setOnClickListener(this);
        btn_likes.setOnClickListener(this);
        btn_log.setOnClickListener(this);
        btn_brightness.setOnClickListener(this);
        btn_viewMode.setOnClickListener(this);
    }

    public void createBrightnessDialog() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (activity.checkSelfPermission(Manifest.permission.WRITE_SETTINGS) != PackageManager.PERMISSION_GRANTED) {
                if (Settings.System.canWrite(activity)) {
                    ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.WRITE_SETTINGS}, REQUEST_CODE);
                } else {
                    Intent intent = new Intent(android.provider.Settings.ACTION_MANAGE_WRITE_SETTINGS);
                    intent.setData(Uri.parse("package:" + activity.getPackageName()));
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    activity.startActivity(intent);
                }
            }
        }

        dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.view_brightness);

        ((LinearLayout) dialog.findViewById(R.id.dialog_brightness)).getLayoutParams().width = (int) (getScreenWidth() * 0.90);

        seekBar = (SeekBar) dialog.findViewById(R.id.sbBrightness);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                Settings.System.putInt(activity.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS, progress);
                ((TextView) dialog.findViewById(R.id.txv_currentBrightness)).setText((int) (progress/ONE_PERCENT)+"");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }

    public int getScreenWidth() {
        Point point = new Point();
        activity.getWindowManager().getDefaultDisplay().getSize(point);
        return point.x;
    }


    public void createFbLoginButton() {
        facebookAPI.createLoginButton(loginButton,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        Toast.makeText(activity, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCancel() {

                    }

                    @Override
                    public void onError(FacebookException error) {
                        Toast.makeText(activity, "Đăng nhập thất bại", Toast.LENGTH_SHORT).show();
                    }
                });
        checkLogin();
    }

    public void checkLogin() {
        if (facebookAPI.isLogged()) {
            btn_likes.setAlpha(1);
            btn_shares.setAlpha(1);
            txv_log.setText("Đăng xuất");
        } else {
            btn_likes.setAlpha(0.7f);
            btn_shares.setAlpha(0.7f);
            txv_log.setText("Đăng Nhập");
        }
    }


    public void createNavigationButton() {
        ((ImageView) toolbar.findViewById(R.id.navigation_button)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showHideNavigation();
            }
        });
    }

    public void showHideNavigation() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START))
            drawerLayout.closeDrawer(GravityCompat.START);
        else
            drawerLayout.openDrawer(GravityCompat.START);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == btn_likes.getId()) {
            likesClicked();
        } else if (id == btn_shares.getId()) {
            sharesClicked();
        } else if (id == btn_log.getId()) {
            loginButton.performClick();
        } else if (id == btn_brightness.getId()) {
            brightnessClicked();
        } else if (id == btn_viewMode.getId()) {
            viewModeClicked();
        }
    }

    public void likesClicked() {

    }

    public void sharesClicked() {

    }

    public void brightnessClicked() {
        int currentBrightness = Settings.System.getInt(activity.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS, -1);
        seekBar.setProgress(currentBrightness);
        dialog.show();
    }

    public void viewModeClicked() {

    }
}

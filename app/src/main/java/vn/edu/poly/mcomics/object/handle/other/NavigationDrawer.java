package vn.edu.poly.mcomics.object.handle.other;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.provider.Settings;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
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
import vn.edu.poly.mcomics.object.handle.social.FacebookAPI;
import vn.edu.poly.mcomics.object.variable.Comics;

/**
 * Created by lucius on 11/16/16.
 */

public class NavigationDrawer {
    private LayoutInflater inflater;
    private ViewGroup parent;
    private View view;
    private Activity activity;
    private Dialog dialog;
    private CallbackManager callbackManager;
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private FacebookAPI facebookAPI;
    private Boolean ishow;
    //
    private Button btn_openComics;
    //    private FacebookAPI facebookAPI;
    private boolean isShow;
    private TextView txv_readMoreTop, txv_readMoreBottom, txv_review;
    private NavigationDrawer navigationDrawer;
    private String id;
    private Comics comics;


    public NavigationDrawer(final Activity activity, int layout, ViewGroup viewGroup) {
        this.activity = activity;
        this.inflater = (LayoutInflater.from(activity));
        this.parent = viewGroup;
        facebookAPI = new FacebookAPI(activity);
        facebookAPI.init();
        //

        //
        view = (inflater.inflate(layout, parent, false));

        ((AppCompatActivity) activity).getSupportActionBar().hide();
        toolbar = (Toolbar) inflater.inflate(R.layout.view_toolbar, ((ViewGroup) view), false)
                .findViewById(R.id.toolBar);
        ((LinearLayout) parent.findViewById(R.id.lyInformatipn_app)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialogAA = new Dialog(activity);
                dialogAA.setTitle("Thông tin nhóm 1");
                dialogAA.setContentView(R.layout.view_information_app);
                dialogAA.getWindow().setLayout(1000,1200);
                dialogAA.show();

            }
        });
        ((LinearLayout) view).addView(toolbar, 0);
        ((FrameLayout) parent.findViewById(R.id.root)).addView(view);
        drawerLayout = ((DrawerLayout) parent);
        callbackManager = CallbackManager.Factory.create();
        LoginButton loginButton = (LoginButton) parent.findViewById(R.id.fb_login);
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
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkShowHide();
            }
        });
        //
        //
        //

        TextView change = (TextView) parent.findViewById(R.id.change);
        clickBrightness();

        change.setText("Độ sáng :" + Settings.System.getInt(activity.getContentResolver(), Settings
                .System.SCREEN_BRIGHTNESS, 0) * 100 / 255 + " %");
        change.setOnClickListener(new View.OnClickListener()

                                  {
                                      @Override
                                      public void onClick(View v) {
                                          dialog.setTitle("Độ sáng");
                                          dialog.getWindow().setLayout(1000, 230);
                                          dialog.show();
                                      }
                                  }

        );
        ((EditText) toolbar.findViewById(R.id.ed_search)).

                setVisibility(View.INVISIBLE);

        beforeIconSearchClick();

        createNavifationButton();

        clickSearch();

        beforeIconSearchClick();
    }


    public void createNavifationButton() {
        ((ImageView) toolbar.findViewById(R.id.navigation_button)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showHideNavigation();
            }
        });
    }

    public void checkShowHide() {
        if (!facebookAPI.isLogined()) {
            sShow();
            ;
        } else {
            hHide();
        }
    }

    public void sShow() {
        ((LinearLayout) parent.findViewById(R.id.lyLike))
                .setVisibility(View
                        .VISIBLE);
        ((LinearLayout) parent.findViewById(R.id.lyComment)).setVisibility(View.VISIBLE);
        ((LinearLayout) parent.findViewById(R.id.lyShare)).setVisibility(View.VISIBLE);
    }

    public void hHide() {

        ((LinearLayout) parent.findViewById(R.id.lyLike))
                .setVisibility(View
                        .GONE);
        ((LinearLayout) parent.findViewById(R.id.lyComment)).setVisibility(View.GONE);
        ((LinearLayout) parent.findViewById(R.id.lyShare)).setVisibility(View.GONE);
    }

    public void showHideNavigation() {
        if (drawerLayout.isDrawerOpen(Gravity.START))
            drawerLayout.closeDrawer(Gravity.START);
        else
            drawerLayout.openDrawer(Gravity.START);
    }

    public void clickSearch() {
        ((ImageView) toolbar.findViewById(R.id.img_v_search)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((TextView) toolbar.findViewById(R.id.txv_AppName)).setVisibility(View.INVISIBLE);
                ((EditText) toolbar.findViewById(R.id.ed_search)).setVisibility(View.VISIBLE);
                // khi nhan vao nt tim kiem thi ban phim ao hien len
                InputMethodManager keyBoard = (InputMethodManager) activity.getSystemService(Context
                        .INPUT_METHOD_SERVICE);
                keyBoard.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
                ((ImageView) toolbar.findViewById(R.id.img_v_search)).setVisibility(View.INVISIBLE);
                ((ImageView) toolbar.findViewById(R.id.img_v_searched)).setVisibility(View.VISIBLE);
                ((ImageView) toolbar.findViewById(R.id.img_v_searched)).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        afterIconSearchClicked();
                    }
                });

            }
        });
    }

    //
    //
    //
    //
    //
    //
    //


    public void beforeIconSearchClick() {
        ((ImageView) toolbar.findViewById(R.id.img_v_search)).setVisibility(View.VISIBLE);
        ((ImageView) toolbar.findViewById(R.id.img_v_searched)).setVisibility(View.INVISIBLE);
    }

    public void afterIconSearchClicked() {

        if (((EditText) toolbar.findViewById(R.id.ed_search)).getText().length() > 0) {
            Toast.makeText(activity, ((EditText) toolbar.findViewById(R.id.ed_search)).getText(),
                    Toast.LENGTH_SHORT).show();
        }
        ((ImageView) toolbar.findViewById(R.id.img_v_search)).setVisibility(View.VISIBLE);
        ((ImageView) toolbar.findViewById(R.id.img_v_searched)).setVisibility(View.INVISIBLE);
        ((TextView) toolbar.findViewById(R.id.txv_AppName)).setVisibility(View.VISIBLE);
        ((EditText) toolbar.findViewById(R.id.ed_search)).setVisibility(View.INVISIBLE);
        ((EditText) toolbar.findViewById(R.id.ed_search)).setText(null);
        // khi nhan tim kiem sau khi nhap du lieu thi ban phim ao bien mat
        InputMethodManager keyBoard = (InputMethodManager) activity.getSystemService(Context
                .INPUT_METHOD_SERVICE);
        keyBoard.toggleSoftInput(InputMethodManager.HIDE_NOT_ALWAYS, 0);
        //Code thuc hien tim kiem truyen o duoi day
    }

    public void clickBrightness() {
        dialog = new Dialog(activity);
        dialog.setTitle("Thay đổi độ sáng");
        dialog.setContentView(R.layout.view_change_brightness);
        SeekBar seekBar = (SeekBar) dialog.findViewById(R.id.sbBrightness);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                Settings.System.putInt(activity.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS, progress);
                TextView change = (TextView) parent.findViewById(R.id.change);
                change.setText("Độ sáng :" + progress * 100 / 255 + " %");
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

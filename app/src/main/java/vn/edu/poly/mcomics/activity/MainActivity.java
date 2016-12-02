package vn.edu.poly.mcomics.activity;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.facebook.FacebookSdk;

import org.json.JSONException;

import java.util.ArrayList;

import vn.edu.poly.mcomics.R;
import vn.edu.poly.mcomics.object.handle.backgroundtask.LoadJsonInBackground;
import vn.edu.poly.mcomics.object.handle.custom.adapter.RecyclerviewCustomAdapter;
import vn.edu.poly.mcomics.object.handle.eventlistener.DownloadEvent;
import vn.edu.poly.mcomics.object.handle.eventlistener.OnViewCreateCallback;
import vn.edu.poly.mcomics.object.handle.json.ParserJSON;
import vn.edu.poly.mcomics.object.handle.other.NavigationDrawer;
import vn.edu.poly.mcomics.object.handle.social.FacebookAPI;
import vn.edu.poly.mcomics.object.variable.Comics;
import vn.edu.poly.mcomics.object.variable.ComicsKind;

public class MainActivity extends AppCompatActivity implements DownloadEvent {
    private FacebookAPI facebookAPI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        facebookAPI = new FacebookAPI(this);
        facebookAPI.init();
        setContentView(R.layout.activity_main);

        getSupportActionBar().hide();
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);

        createLoadingFragment();
        startLoadData();
    }

    //button search
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.action, menu);
        return true;
    }

    public void startLoadData() {
        LoadJsonInBackground backgroundTask = new LoadJsonInBackground();
        backgroundTask.setOnFinishEvent(this);
        backgroundTask.execute("http://grayguy.xyz/?kind=top");
    }

    @Override
    public void onLoadFinish(final String string) {
        FragmentCreator fragment = FragmentCreator.getFragment(R.layout.navigation_view, "main");
        fragment.setOnViewCreateCallback(new OnViewCreateCallback() {
            @Override
            public void OnViewCreate(View view, String tag) {
                try {
                    createMainFragment(view, string);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        getFragmentManager().beginTransaction().replace(android.R.id.content, fragment).commit();
    }

    public void createLoadingFragment() {
        FragmentCreator fragment = FragmentCreator.getFragment(R.layout.loading_fragment, "loading");
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(android.R.id.content, fragment).commit();
    }

    public void createMainFragment(View view, String string) throws JSONException {
        getSupportActionBar().show();
        new NavigationDrawer(this, R.layout.main_fragment, (ViewGroup) view);
        ArrayList<Comics> comicsArray = new ParserJSON().getComicArray(string);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerViewList);
        recyclerView.setLayoutManager(layoutManager);
        RecyclerviewCustomAdapter adapter = new RecyclerviewCustomAdapter(comicsArray);
        recyclerView.setAdapter(adapter);

        LoadJsonInBackground backgroundTask = new LoadJsonInBackground();
        backgroundTask.setOnFinishEvent(new DownloadEvent() {
            @Override
            public void onLoadFinish(String string) {
                try {
                    showKindListItem(string);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        backgroundTask.execute("http://grayguy.xyz/?kind=all&table=comic_kinds");
    }

    public void showKindListItem(String string) throws JSONException {
        LinearLayout layout = (LinearLayout) findViewById(R.id.linear_kind);
        final ArrayList<ComicsKind> comicsKindArray = new ParserJSON().getComicKindArray(string);
        for (int x = 0; x < comicsKindArray.size(); x++) {
            View view = (LayoutInflater.from(this)).inflate(R.layout.kind_list_item_view, null, false);
            ((TextView) view.findViewById(R.id.id)).setText(comicsKindArray.get(x).getId() + "");
            ((TextView) view.findViewById(R.id.text)).setText(comicsKindArray.get(x).getKind());
            final int finalX = x;
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this, ComicsCategoryActivity.class);
                    intent.putExtra("id", comicsKindArray.get(finalX).getId());
                    startActivity(intent);
                }
            });
            layout.addView(view, x);
        }
    }

    public static class FragmentCreator extends Fragment {
        private OnViewCreateCallback onViewCreateCallback;
        static int layout = R.layout.activity_main;
        static String tag;

        public static FragmentCreator getFragment(int layoutInt, String tagStr) {
            layout = layoutInt;
            tag = tagStr;
            return new FragmentCreator();
        }

        public void setOnViewCreateCallback(OnViewCreateCallback onViewCreateCallback) {
            this.onViewCreateCallback = onViewCreateCallback;
        }

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View view = inflater.inflate(layout, container, false);
            if (onViewCreateCallback != null) {
                onViewCreateCallback.OnViewCreate(view, tag);
            }
            return view;
        }
    }

}

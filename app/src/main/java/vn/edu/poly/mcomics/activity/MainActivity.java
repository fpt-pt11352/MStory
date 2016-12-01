package vn.edu.poly.mcomics.activity;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONException;

import java.util.ArrayList;

import vn.edu.poly.mcomics.R;
import vn.edu.poly.mcomics.object.handle.backgroundtask.LoadJsonInBackground;
import vn.edu.poly.mcomics.object.handle.custom.adapter.RecyclerviewCustomAdapter;
import vn.edu.poly.mcomics.object.handle.eventlistener.DownloadEvent;
import vn.edu.poly.mcomics.object.handle.eventlistener.OnViewCreateCallback;
import vn.edu.poly.mcomics.object.handle.json.ParserJSON;
import vn.edu.poly.mcomics.object.variable.Comics;
import vn.edu.poly.mcomics.object.variable.ComicsKind;

public class MainActivity extends AppCompatActivity implements DownloadEvent {
    private Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);

        this.activity = this;
        LoadJsonInBackground backgroundTask = new LoadJsonInBackground();
        backgroundTask.setOnFinishEvent(this);
        backgroundTask.execute("http://grayguy.xyz/?kind=top");
        createLoadingFragment();
    }

    //button search
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.action, menu);
        return true;
    }

    public void createLoadingFragment() {
        FragmentCreater fragment = FragmentCreater.getFragment(R.layout.loading_fragment, "loading");
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(android.R.id.content, fragment).commit();
    }

    public void showKindListItem(String string) throws JSONException {
        LinearLayout layout = (LinearLayout) findViewById(R.id.linear_kind);
        ArrayList<ComicsKind> comicsKindArray = new ParserJSON().getComicKindArray(string);
        Log.e("arrsize", comicsKindArray.size() + "");
        for (int x = 0; x < comicsKindArray.size(); x++) {
            View view = (LayoutInflater.from(this)).inflate(R.layout.kind_list_item_view, null, false);
            ((TextView) view.findViewById(R.id.id)).setText(comicsKindArray.get(x).getId() + "");
            ((TextView) view.findViewById(R.id.text)).setText(comicsKindArray.get(x).getKind());
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this, ComicCategory.class);
                    Bundle bundle=new Bundle();
                    String kind_text =((TextView)v.findViewById(R.id.text)).getText().toString();

                    bundle.putString("name", kind_text);
                    intent.putExtra("MyPackage", bundle);
                    startActivity(intent);
                }
            });
            layout.addView(view, x);
        }
    }

    @Override
    public void onLoadFinish(final String string) {
        FragmentCreater fragment = FragmentCreater.getFragment(R.layout.main_fragment, "main");
        fragment.setOnViewCreateCallback(new OnViewCreateCallback() {
            @Override
            public void OnViewCreate(View view, String tag) {
                createMainFragment(view, string);
            }
        });
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(android.R.id.content, fragment).commit();
    }

    public void createMainFragment(View view, String string) {
        getSupportActionBar().show();
        try {
            ArrayList<Comics> comicsArray = new ParserJSON().getComicArray(string);

            LinearLayoutManager layoutManager = new LinearLayoutManager(activity);
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
        } catch (Exception e) {
            Log.e("createMainFragment", "fail");
            e.printStackTrace();
        }
    }

    public static class FragmentCreater extends Fragment {
        private OnViewCreateCallback onViewCreateCallback;
        static int layout = R.layout.activity_main;
        static String tag;

        public static FragmentCreater getFragment(int layoutInt, String tagStr) {
            layout = layoutInt;
            tag = tagStr;
            return new FragmentCreater();
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

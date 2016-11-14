package vn.edu.poly.mstory.object.handle.json;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;


import vn.edu.poly.mstory.model.Comics;

/**
 * Created by vuong on 12/11/2016.
 */

public class ParseJson {

    public ArrayList<Comics> parseComics(JSONArray jArr) {
        ArrayList<Comics> arrComic;
        try {
            JSONArray jsonArray = jArr;
            int length = jsonArray.length();
            arrComic = new ArrayList<>();
            for (int i = 0; i < length; i++) {
                JSONObject jComic = jsonArray.getJSONObject(i);

                String id = jComic.getString("id");
                String name = jComic.getString("comics_name");
                String kind = jComic.getString("kind");
                String thumbnail = jComic.getString("thumbnail");
                String views = jComic.getString("views");
                String author = jComic.getString("author");
                String episodes = jComic.getString("episodes");
                String content = jComic.getString("content");

                Comics comic = new Comics(id, name, kind, thumbnail, views, author, episodes,content);
                arrComic.add(comic);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        Log.d("ok","ok roi");
        return arrComic;
    }

}

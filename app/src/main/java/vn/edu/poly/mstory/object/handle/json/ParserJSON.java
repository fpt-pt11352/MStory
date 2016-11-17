package vn.edu.poly.mstory.object.handle.json;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import vn.edu.poly.mstory.object.variable.Comic;
import vn.edu.poly.mstory.object.variable.ComicKind;

/**
 * Created by lucius on 11/16/16.
 */

public class ParserJSON {
    public ArrayList<Comic> getComicArray(String json) throws JSONException {
        ArrayList<Comic> comicsArray = new ArrayList<>();
        JSONArray jsonArray = new JSONArray(json);
        for (int x = 0; x < jsonArray.length(); x++) {
            comicsArray.add(getComic(jsonArray.getJSONObject(x)));
        }
        return comicsArray;
    }

    public Comic getComic(JSONObject jsonObject) throws JSONException {
        return new Comic(jsonObject.getInt("id"),
                jsonObject.getString("comics_name"),
                jsonObject.getString("kind"),
                jsonObject.getString("thumbnail"),
                jsonObject.getInt("views"),
                jsonObject.getString("author"),
                jsonObject.getString("episodes"),
                jsonObject.getString("content"));
    }

    public ArrayList<ComicKind> getComicKindArray(String json) throws JSONException {
        ArrayList<ComicKind> comicKindArray = new ArrayList<>();
        JSONArray jsonArray = new JSONArray(json);
        for (int x = 0; x < jsonArray.length(); x++) {
            comicKindArray.add(getComicKind(jsonArray.getJSONObject(x)));
        }
        return comicKindArray;
    }

    public ComicKind getComicKind(JSONObject jsonObject) throws JSONException {
        return new ComicKind(jsonObject.getInt("id"),
                jsonObject.getString("kind_name"));
    }
}

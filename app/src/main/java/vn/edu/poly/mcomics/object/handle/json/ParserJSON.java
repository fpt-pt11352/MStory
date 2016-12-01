package vn.edu.poly.mcomics.object.handle.json;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import vn.edu.poly.mcomics.object.variable.Comics;
import vn.edu.poly.mcomics.object.variable.ComicsKind;

/**
 * Created by lucius on 11/16/16.
 */

public class ParserJSON {
    public ArrayList<Comics> getComicArray(String json) throws JSONException {
        ArrayList<Comics> comicsArray = new ArrayList<>();
        JSONArray jsonArray = new JSONArray(json);
        for (int x = 0; x < jsonArray.length(); x++) {
            comicsArray.add(getComic(jsonArray.getJSONObject(x)));
        }
        return comicsArray;
    }

    public Comics getComic(JSONObject jsonObject) throws JSONException {
        return new Comics(jsonObject.getInt("id"),
                jsonObject.getString("comics_name"),
                jsonObject.getString("kind"),
                jsonObject.getString("thumbnail"),
                jsonObject.getInt("views"),
                jsonObject.getString("author"),
                jsonObject.getString("episodes"),
                jsonObject.getString("content"));
    }

    public ArrayList<ComicsKind> getComicKindArray(String json) throws JSONException {
        ArrayList<ComicsKind> comicKindArray = new ArrayList<>();
        JSONArray jsonArray = new JSONArray(json);
        for (int x = 0; x < jsonArray.length(); x++) {
            comicKindArray.add(getComicKind(jsonArray.getJSONObject(x)));
        }
        return comicKindArray;
    }

    public ComicsKind getComicKind(JSONObject jsonObject) throws JSONException {
        return new ComicsKind(jsonObject.getInt("id"),
                jsonObject.getString("kind_name"));
    }

    public ArrayList<Integer> getChapterArray(String json) throws JSONException{
        ArrayList<Integer> chapterArray = new ArrayList<>();
        JSONArray jsonArray = new JSONArray(json);
        for (int x = 0; x < jsonArray.length(); x++) {
            chapterArray.add(jsonArray.getJSONObject(x).getInt("chapter_number"));
        }
        return chapterArray;
    }
}
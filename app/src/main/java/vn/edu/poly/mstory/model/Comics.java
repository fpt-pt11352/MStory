package vn.edu.poly.mstory.model;

/**
 * Created by vuong on 12/11/2016.
 */

public class Comics {

    public String id;
    public String comicsName;
    public String kind;
    public String thumbnail;
    public String views;
    public String author;
    public String episodes;
    public String content;

    public Comics(String id, String name, String kind, String thumbnail, String views, String author, String episodes, String content) {
        this.id = id;
        this.comicsName = name;
        this.kind = kind;
        this.thumbnail = thumbnail;
        this.views = views;
        this.author = author;
        this.episodes = episodes;
        this.content = content;
    }

    public Comics() {

    }

}


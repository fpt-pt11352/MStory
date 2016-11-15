package vn.edu.poly.mstory.object.variable;

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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getComicsName() {
        return comicsName;
    }

    public void setComicsName(String comicsName) {
        this.comicsName = comicsName;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getViews() {
        return views;
    }

    public void setViews(String views) {
        this.views = views;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getEpisodes() {
        return episodes;
    }

    public void setEpisodes(String episodes) {
        this.episodes = episodes;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}

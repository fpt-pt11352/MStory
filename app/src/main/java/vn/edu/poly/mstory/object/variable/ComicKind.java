package vn.edu.poly.mstory.object.variable;

/**
 * Created by lucius on 11/17/16.
 */

public class ComicKind {
    public int id;
    public String kind;

    public ComicKind(int id, String kind) {
        this.id = id;
        this.kind = kind;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }
}

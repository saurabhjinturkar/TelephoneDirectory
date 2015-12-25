package in.saurabhjinturkar.telephonedirectory.bean;

/**
 * Created by Saurabh on 8/19/2015.
 */
public class News {

    private String id;
    private String title;
    private String body;
    private String time;

    public News(String title, String body, String time) {
        this.title = title;
        this.body = body;
        this.time = time;
    }

    public News(String id, String title, String body, String time) {
        this.id = id;
        this.title = title;
        this.body = body;
        this.time = time;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getBody() {
        return body;
    }

    public String getTime() {
        return time;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        News news = (News) o;

        if (!id.equals(news.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public String toString() {
        return "News{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", body='" + body + '\'' +
                ", time='" + time + '\'' +
                '}';
    }
}

package fr.karmakat.wiflic.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(collection = "posts")
public class Post {
    @Id
    private int id;
    private String title;
    private String content;
    private Date date;

    public Post(int id, String title, String content, Date date) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public Date getDate() {
        return date;
    }
}

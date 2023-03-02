package com.example.revision27.models;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.util.MultiValueMap;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Review {
    
    private ObjectId _id;
    private String user;
    private Integer rating;
    private String comment;
    private Integer gid;
    private Date posted;
    private String name;
    private List<NewReview> edited;

    public static Review createFromForm(MultiValueMap<String, String> form) {
        Review r = new Review();
        Date date = new Date();
        Timestamp timestamp = new Timestamp(date.getTime());

        r.setUser(form.getFirst("user"));
        r.setComment(form.getFirst("comment"));
        r.setRating(Integer.parseInt(form.getFirst("rating")));
        r.setGid(Integer.parseInt(form.getFirst("gameId")));
        r.setPosted(timestamp);
        return r;
    }

    public Document toDocumentA() {
        Document doc = new Document();
        doc.put("user", getUser());
        doc.put("comment", getComment());
        doc.put("rating", getRating());
        doc.put("gid", getGid());
        doc.put("name", getName());
        doc.put("posted", getPosted());
        doc.put("edited", getEdited());
        return doc;
    }

    public Review createFromDoc(Document d) {
        Review r = new Review();
        r.set_id(d.getObjectId("_id"));
        r.setUser(d.getString("user"));
        r.setRating(d.getInteger("rating"));
        r.setComment(d.getString("comment"));
        r.setGid(d.getInteger("gid"));
        r.setPosted(d.getDate("posted"));
        r.setName(d.getString("name"));
        return r;
    }

    public Document toDocumentUpdate() {
        Document doc = new Document();
        doc.put("user", getUser());
        doc.put("comment", getComment());
        doc.put("rating", getRating());
        doc.put("gid", getGid());
        doc.put("name", getName());
        doc.put("posted", getPosted());
        doc.put("edited", getEdited());

        return doc;
    }

    public int size() {
        return 0;
    }
}


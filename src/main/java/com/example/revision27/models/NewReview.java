package com.example.revision27.models;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Timestamp;
import java.util.Date;

import org.bson.Document;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewReview {
    
    private String comment;
    private Integer rating;
    private Date posted;

    public static NewReview createFromJson(String newReviewJson) throws IOException {
        NewReview nr = new NewReview();

        try(InputStream is = new ByteArrayInputStream(newReviewJson.getBytes())){
            JsonReader r = Json.createReader(is);
            JsonObject o = r.readObject();

            Date date = new Date();
            Timestamp timestamp = new Timestamp(date.getTime());
            nr.setComment(o.getString("comment"));
            nr.setRating(o.getInt("rating"));
            nr.setPosted(timestamp);
        }

        return nr;
    }

    public Document toDocument() {
        Document editedElement = new Document()
                        .append("comment", getClass())
                        .append("rating", getRating())
                        .append("posted", getPosted());
        return editedElement;
    }

}

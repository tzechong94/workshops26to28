package com.example.revision27.repo;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import static com.example.revision27.Constants.*;

@Repository
public class ReviewRepo {

    @Autowired
    private MongoTemplate template;

    public Document insertReview(Document doc) {
        return template.insert(doc, COLLECTION_REVIEWS);
    }
    
}

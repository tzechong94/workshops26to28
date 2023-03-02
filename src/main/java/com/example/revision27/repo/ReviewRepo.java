package com.example.revision27.repo;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import com.example.revision27.models.NewReview;
import com.mongodb.client.result.UpdateResult;

import static com.example.revision27.Constants.*;


@Repository
public class ReviewRepo {

    @Autowired
    private MongoTemplate template;

    public Document insertReview(Document doc) {
        return template.insert(doc, COLLECTION_REVIEWS);
    }

    public Document findReviewById(String reviewId) {
        ObjectId _id = new ObjectId(reviewId);
        Criteria criteria = Criteria.where("_id").is(_id);
        Query query = Query.query(criteria);
        Document result = template.findOne(query, Document.class, COLLECTION_REVIEWS);
        return result;
    }

    public void updateReviewById(String reviewId, NewReview newReview) {
        ObjectId _id = new ObjectId(reviewId);
        // Document editedDocumentElement = newReview.toDocument();
        Criteria criteria = Criteria.where("_id").is(_id);
        Query query = Query.query(criteria);

        
        Update updateOps = new Update().push("edited").each(newReview);

        UpdateResult updateResult = template.updateFirst(query, updateOps, COLLECTION_REVIEWS);

        if (updateResult == null)
            System.out.println("not updated");
        else 
            System.out.println(updateResult.getModifiedCount() + " document(s) updated..");
    }
}
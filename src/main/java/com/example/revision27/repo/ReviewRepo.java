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
import com.example.revision27.models.Review;
import com.mongodb.client.result.UpdateResult;

import static com.example.revision27.Constants.*;

import java.util.ArrayList;
import java.util.List;


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

    public Review getReviewById(String reviewId){
        ObjectId _id = new ObjectId(reviewId);
        return template.findById(_id, Review.class, COLLECTION_REVIEWS);
        // mongotemplate automatically maps the results to the review class. if the name doesn't match,
        // you can also manually configure mapping i m
    }

    public List<String> getReviewIdByGid(String gameId) {
        Criteria criteria = Criteria.where("gid").is(Integer.parseInt(gameId));
        Query query = Query.query(criteria);
        List<Review> result = template.find(query, Review.class, COLLECTION_GAMES);
        List<String> reviewIdList = new ArrayList<String>();

        for (Review x : result) {
            reviewIdList.add("/review/" + x.get_id().toString());
        }
        System.out.println(reviewIdList);
        return reviewIdList;
    }
    
}
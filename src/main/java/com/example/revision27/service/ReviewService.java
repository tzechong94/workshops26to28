package com.example.revision27.service;


import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.revision27.models.NewReview;
import com.example.revision27.models.Review;
import com.example.revision27.repo.ReviewRepo;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepo reviewRepo;

    public Review insertReview(Document doc) {
        Document d = reviewRepo.insertReview(doc);
        Review r = new Review();
        return r.createFromDoc(d);
    }

    public Review findReviewById(String reviewId) {
        Document d = reviewRepo.findReviewById(reviewId);
        Review r = new Review();
        return r.createFromDoc(d);
    }

    public void updateReviewById(String reviewId, NewReview newReview) {
        reviewRepo.updateReviewById(reviewId, newReview);
    }
    
}

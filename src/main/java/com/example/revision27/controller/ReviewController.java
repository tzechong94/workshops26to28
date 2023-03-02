package com.example.revision27.controller;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.revision27.models.NewReview;
import com.example.revision27.models.Review;
import com.example.revision27.service.GameService;
import com.example.revision27.service.ReviewService;

import jakarta.json.Json;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;

@Controller
public class ReviewController {
    
    @Autowired
    private GameService gameSvc;

    @Autowired
    private ReviewService reviewSvc;


    @GetMapping("/game/{gameId}/review")
    public String getForm(@PathVariable String gameId, Model model) {
        model.addAttribute("gameId", gameId);
        model.addAttribute("review", new Review());

        return "reviewform";
    }


    @PostMapping("/review")
    public ResponseEntity<JsonObject> postReview(@RequestBody MultiValueMap<String, String> form) {

        Date date = new Date();
        Timestamp timestamp = new Timestamp(date.getTime());
        Review review = Review.createFromForm(form);
        NewReview nr = new NewReview();

        nr.setComment(review.getComment());
        nr.setRating(review.getRating());
        nr.setPosted(review.getPosted());

        List<NewReview> newReviewList = new ArrayList<>();
        newReviewList.add(nr);

        Integer gameId = review.getGid();

        String gameName = gameSvc.getGameNameFromId(gameId);
        
        // JsonArrayBuilder arrBuilder = Json.createArrayBuilder();
        // List<NewReview> listOfReview = gameList
        //             .stream()
        //             .map(g -> g.toJsonObject())
        //             .toList();
        
        // for (JsonObject x : listOfGames) {
        //     arrBuilder.add(x);
        // }

        review.setName(gameName);
        review.setPosted(timestamp);
        review.setEdited(newReviewList);
        

        System.out.println(review.getName() + "name");
        System.out.println(review.getUser() + "user");
        System.out.println(review.getGid() + "gameid");
        System.out.println(review.getComment() + "comment");
        System.out.println(review.getPosted() + "posted");
        System.out.println(review.getRating() + "rating");

        // JsonArrayBuilder arrbld = 

        JsonObject result = Json.createObjectBuilder()
                        .add("user", review.getName())
                        .add("rating", review.getRating())
                        .add("comment", review.getComment())
                        .add("ID", review.getGid())
                        .add("posted", review.getPosted().toString())
                        .add("name", gameName)
                        // .add("edited", review.getEdited().toArray());
                        .build();

        reviewSvc.insertReview(review.toDocumentA());

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PutMapping("/review/{reviewId}")
    public ResponseEntity<String> updateReviewIfExists(@PathVariable String reviewId, @RequestBody String newReviewJson) throws IOException {
        // ObjectId _id = new ObjectId(reviewId);

        NewReview nr = NewReview.createFromJson(newReviewJson);

        Review returnedReview = reviewSvc.findReviewById(reviewId);
        
        if (returnedReview != null){
            // List<NewReview> currentEditedHistory = returnedReview.getEdited();
            // currentEditedHistory.add(nr);
            reviewSvc.updateReviewById(reviewId, nr);
            return new ResponseEntity<>(HttpStatus.OK);

        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/review/{reviewId}")
    public ResponseEntity<String> getLatestReviewById(@PathVariable String reviewId) {

        Review returnedReview = reviewSvc.getReviewById(reviewId);
        List<NewReview> listOfEdited = returnedReview.getEdited();

        NewReview latestReview = listOfEdited.get(listOfEdited.size()-1);
        System.out.println(latestReview);

        JsonObject result = Json.createObjectBuilder()
            .add("user", returnedReview.getName())
            .add("rating", latestReview.getRating())
            .add("comment", latestReview.getComment())
            .add("ID", returnedReview.getGid())
            .add("posted", latestReview.getPosted().toString())
            .add("name", returnedReview.getName())
        // .add("edited", review.getEdited().toArray());
            .build();

        if (result.isEmpty()) 
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(result.toString(), HttpStatus.OK);
    }

    @GetMapping("/review/{reviewId}/history")
    public ResponseEntity<String> getHistoryOfEdits(@PathVariable String reviewId){
        Review returnedReview = reviewSvc.getReviewById(reviewId);
        List<NewReview> listOfEdited = returnedReview.getEdited();
        NewReview latestReview = listOfEdited.get(listOfEdited.size()-1);

        System.out.println(listOfEdited + "history");

        JsonArrayBuilder arrbld = Json.createArrayBuilder();
        
        List<JsonObject> listOfNewReviews = listOfEdited
                                        .stream()
                                        .map(r -> r.toJsonObject())
                                        .toList();

        for (JsonObject x : listOfNewReviews) {
            arrbld.add(x);
        }


        JsonObject result = Json.createObjectBuilder()
            .add("user", returnedReview.getName())
            .add("rating", latestReview.getRating())
            .add("comment", latestReview.getComment())
            .add("ID", returnedReview.getGid())
            .add("posted", latestReview.getPosted().toString())
            .add("name", returnedReview.getName())
            .add("edited", arrbld)
        // .add("edited", review.getEdited().toArray());
            .build();

        return new ResponseEntity<>(result.toString(), HttpStatus.OK);

    }
}

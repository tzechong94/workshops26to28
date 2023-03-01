package com.example.revision27.controller;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.revision27.models.Review;
import com.example.revision27.repo.GameRepo;
import com.example.revision27.service.GameService;
import com.example.revision27.service.ReviewService;

import jakarta.json.Json;
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

        Review review = Review.createFromForm(form);

        Integer gameId = review.getGameId();

        String gameName = gameSvc.getGameNameFromId(gameId);
        review.setName(gameName);

        System.out.println(review.getName() + "name");
        System.out.println(review.getUser() + "user");
        System.out.println(review.getGameId() + "gameid");
        System.out.println(review.getComment() + "comment");
        System.out.println(review.getPosted() + "posted");
        System.out.println(review.getRating() + "rating");


        JsonObject result = Json.createObjectBuilder()
                        .add("user", review.getName())
                        .add("rating", review.getRating())
                        .add("comment", review.getComment())
                        .add("ID", review.getGameId())
                        .add("posted", review.getPosted().toString())
                        .add("name", gameName)
                        .build();

        reviewSvc.insertReview(review.toDocumentA());

        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}

package com.example.revision27.repo;

import java.util.List;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;


import static com.example.revision27.Constants.*;


@Repository
public class GameRepo {

    @Autowired
    private MongoTemplate template;

    public List<Document> findAllGames(Integer limit, Integer offset) {
        Query query = new Query().limit(limit).skip(offset);
        return template.find(query, Document.class, COLLECTION_GAMES);
    }

    public Integer count() {
        Query query = new Query();
        List<Document> resultList = template.find(query, Document.class, COLLECTION_GAMES);
        Integer count = resultList.size();
        return count;
    }

    public List<Document> findGamesByRank(Integer limit, Integer offset) {
        Query query = new Query()
                    .with(Sort.by(Direction.ASC, FIELD_RANKING))
                    .limit(limit).skip(offset);
        return template.find(query, Document.class, COLLECTION_GAMES);
    }

    
}

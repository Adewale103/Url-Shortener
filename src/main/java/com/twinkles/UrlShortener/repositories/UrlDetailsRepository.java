package com.twinkles.UrlShortener.repositories;

import com.twinkles.UrlShortener.models.UrlDetails;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UrlDetailsRepository extends MongoRepository<UrlDetails, String> {
    Optional<UrlDetails> findUrlDetailsByShortenedUrl(String shortenedUrl);
    Optional<UrlDetails> findUrlDetailsByOriginalUrl(String originalUrl);
}

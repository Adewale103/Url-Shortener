package com.twinkles.UrlShortener.services;

import com.twinkles.UrlShortener.dtos.requests.UrlRequest;
import com.twinkles.UrlShortener.dtos.responses.UrlDetailsResponse;
import com.twinkles.UrlShortener.exception.UrlNotFoundException;
import com.twinkles.UrlShortener.exception.UrlShortenerException;

public interface UrlDetailsService {
    UrlDetailsResponse generateShortUrl(UrlRequest urlRequest) throws UrlShortenerException;
    String updateShortenedUrl(String url, String customUrl) throws UrlShortenerException;
    String getEncodedUrl(String originalUrl) throws UrlNotFoundException;
    String getDecodedUrl(String shortLink) throws UrlNotFoundException;
    void deleteUrlUsingShortenedUrl(String shortenedUrl) throws UrlNotFoundException;
    void deleteUrlUsingOriginalUrl(String originalUrl) throws UrlNotFoundException;
    int size();
}

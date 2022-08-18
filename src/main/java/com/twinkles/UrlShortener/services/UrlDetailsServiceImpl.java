package com.twinkles.UrlShortener.services;

import com.google.common.hash.Hashing;
import com.twinkles.UrlShortener.dtos.requests.UrlRequest;
import com.twinkles.UrlShortener.dtos.responses.UrlDetailsResponse;
import com.twinkles.UrlShortener.exception.UrlNotFoundException;
import com.twinkles.UrlShortener.exception.UrlShortenerException;
import com.twinkles.UrlShortener.models.UrlDetails;
import com.twinkles.UrlShortener.repositories.UrlDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class UrlDetailsServiceImpl implements UrlDetailsService {
    @Autowired
    private UrlDetailsRepository urlDetailsRepository;
    @Override
    public UrlDetailsResponse generateShortUrl(UrlRequest urlRequest) throws UrlShortenerException {
        if(!isValid(urlRequest.getUrl())){
            throw new UrlShortenerException("Invalid url!");
        }
        String encodedUrl = encodeUrl(urlRequest.getUrl());
        UrlDetails urlDetails = new UrlDetails();
        urlDetails.setOriginalUrl(urlRequest.getUrl());
        urlDetails.setShortenedUrl(encodedUrl);
        urlDetails.setCreationDate(LocalDateTime.now());
        urlDetailsRepository.save(urlDetails);

        UrlDetailsResponse response = new UrlDetailsResponse();
        response.setOriginalUrl(urlDetails.getOriginalUrl());
        response.setShortenedUrl(urlDetails.getShortenedUrl());

        return response;
    }

    @Override
    public String updateShortenedUrl(String url, String customUrl) throws  UrlShortenerException {
        UrlDetails urlDetails = urlDetailsRepository.findUrlDetailsByShortenedUrl(url).orElseThrow(()->  new UrlNotFoundException("Url not found!"));
        if(!isValid(customUrl)){
            throw new UrlShortenerException("Invalid url!");
        }
        urlDetails.setShortenedUrl(customUrl);
        urlDetailsRepository.save(urlDetails);
        return urlDetails.getShortenedUrl();
    }

    @Override
    public String getEncodedUrl(String originalUrl) throws UrlNotFoundException {
        UrlDetails urlDetails = urlDetailsRepository.findUrlDetailsByOriginalUrl(originalUrl).orElseThrow(()->  new UrlNotFoundException("Url not found!"));
        return urlDetails.getShortenedUrl();
    }

    @Override
    public String getDecodedUrl(String shortLink) throws UrlNotFoundException {
        UrlDetails urlDetails = urlDetailsRepository.findUrlDetailsByShortenedUrl(shortLink).orElseThrow(()->  new UrlNotFoundException("Url not found!"));
        return urlDetails.getOriginalUrl();
    }

    @Override
    public void deleteUrlUsingShortenedUrl(String shortenedUrl) throws UrlNotFoundException {
        UrlDetails urlDetails = urlDetailsRepository.findUrlDetailsByShortenedUrl(shortenedUrl).orElseThrow(()->  new UrlNotFoundException("Url not found!"));
        urlDetailsRepository.delete(urlDetails);
    }

    @Override
    public void deleteUrlUsingOriginalUrl(String originalUrl) throws UrlNotFoundException {
        UrlDetails urlDetails = urlDetailsRepository.findUrlDetailsByOriginalUrl(originalUrl).orElseThrow(()->  new UrlNotFoundException("Url not found!"));
        urlDetailsRepository.delete(urlDetails);
    }

    @Override
    public int size() {
        return (int)urlDetailsRepository.count();
    }


    private String encodeUrl(String url){
        LocalDateTime time = LocalDateTime.now();
        return Hashing.murmur3_32().hashString(url.concat(time.toString()),StandardCharsets.UTF_8).toString();
    }

    private boolean isValid(String url){
        String regex =  "((http|https)://)(www.)?"
                + "[a-zA-Z0-9@:%._\\+~#?&//=]"
                + "{2,256}\\.[a-z]"
                + "{2,6}\\b([-a-zA-Z0-9@:%"
                + "._\\+~#?&//=]*)";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(url);
        return matcher.matches();
    }
}

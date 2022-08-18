package com.twinkles.UrlShortener.controllers;


import com.twinkles.UrlShortener.dtos.requests.UrlRequest;
import com.twinkles.UrlShortener.exception.UrlNotFoundException;
import com.twinkles.UrlShortener.exception.UrlShortenerException;
import com.twinkles.UrlShortener.services.UrlDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping("/api/v1/url")
public class UrlController {

    @Autowired
    UrlDetailsService urlDetailsService;

    @PostMapping("/getShortLink")
    public ResponseEntity<?> getShortLinkOfUrl(@RequestBody UrlRequest request){
        try{
            return new ResponseEntity<>(urlDetailsService.generateShortUrl(request), HttpStatus.OK);
        }
        catch(UrlShortenerException ex){
            return new ResponseEntity<>(new ApiResponse(false, ex.getMessage()), HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @GetMapping("/{shortUrl}")
    public ResponseEntity <?> redirectToOriginalUrl(@PathVariable String shortUrl){
        try{return new ResponseEntity<>(urlDetailsService.getDecodedUrl(shortUrl), HttpStatus.FOUND);}
        catch(UrlShortenerException ex){
            return new ResponseEntity<>(new ApiResponse(false, ex.getMessage()), HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @GetMapping("/get/{shortUrl}")
    public void getFullUrl(HttpServletResponse response, @PathVariable String shortUrl) throws IOException, UrlNotFoundException {
        response.sendRedirect(urlDetailsService.getDecodedUrl(shortUrl));
    }

    @GetMapping("/get/get/{shortUrl}")
    public RedirectView getFullUrl1(@PathVariable String shortUrl) throws UrlNotFoundException {
        RedirectView redirectView = new RedirectView();

        redirectView.setUrl (urlDetailsService.getDecodedUrl(shortUrl));
        return redirectView;
    }

    @DeleteMapping("/{originalUrl}/")
    public void deleteUrl(@PathVariable String originalUrl) throws UrlNotFoundException {
        urlDetailsService.deleteUrlUsingOriginalUrl(originalUrl);
    }


    @PatchMapping("/customiseUrl")
    public ResponseEntity<?> customiseShortLink(@RequestParam String shortLink, @RequestParam String customiseUrl) {
        try{return new ResponseEntity<>(urlDetailsService.updateShortenedUrl(shortLink,customiseUrl), HttpStatus.ACCEPTED);}
            catch(UrlShortenerException ex){
                return new ResponseEntity<>(new ApiResponse(false, ex.getMessage()), HttpStatus.NOT_ACCEPTABLE);
        }
    }
}

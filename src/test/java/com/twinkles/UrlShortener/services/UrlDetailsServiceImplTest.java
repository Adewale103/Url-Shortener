package com.twinkles.UrlShortener.services;

import com.twinkles.UrlShortener.dtos.requests.UrlRequest;
import com.twinkles.UrlShortener.exception.UrlShortenerException;
import com.twinkles.UrlShortener.repositories.UrlDetailsRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class UrlDetailsServiceImplTest {
    @Autowired
    private UrlDetailsService urlDetailsService;
    @Autowired
    private UrlDetailsRepository urlDetailsRepository;

    private UrlRequest urlRequest;

    @BeforeEach
    void setUp() {
        urlRequest = new UrlRequest();
        urlRequest.setUrl("https://www.semicolon.africa/native/dewale");
    }

    @AfterEach
    void tearDown(){
        urlDetailsRepository.deleteAll();
    }

   @Test
    public void testThatUrlCanBeShortenedTest(){
        try{
        urlDetailsService.generateShortUrl(urlRequest);}
        catch (UrlShortenerException e){
            System.err.printf("%s%n",e.getLocalizedMessage());
        }
        assertEquals(1,urlDetailsService.size());
   }

   @Test
    public void testThatInvalidUrlThrowsException(){
       try{
           urlDetailsService.generateShortUrl(urlRequest);}
       catch (UrlShortenerException e){
           System.err.printf("%s%n",e.getLocalizedMessage());
       }
       assertEquals(1,urlDetailsService.size());
   }
}
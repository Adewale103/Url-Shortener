package com.twinkles.UrlShortener.dtos.responses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UrlDetailsResponse {
    private String originalUrl;
    private String shortenedUrl;
}

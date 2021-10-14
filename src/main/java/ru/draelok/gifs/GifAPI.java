package ru.draelok.gifs;

import feign.Param;
import feign.RequestLine;

public interface GifAPI {
    @RequestLine("GET /search?q={search_key}&api_key={api_key}")
    GifResponse getGifImage(@Param("search_key") String searchKey, @Param("api_key") String apiKey);
}
package ru.draelok.gifs.api;

import feign.Param;
import feign.RequestLine;
import ru.draelok.gifs.model.GifResponse;

public interface GifAPI {
    @RequestLine("GET /search?q={search_key}&api_key={api_key}")
    GifResponse getGifImage(@Param("search_key") String searchKey, @Param("api_key") String apiKey);
}
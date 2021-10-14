package ru.draelok.gifs.api;

import feign.Headers;
import feign.RequestLine;

public interface ImageDataAPI {
    @RequestLine("GET")
    @Headers("Accept: image/gif")
    byte[] getImageData();
}

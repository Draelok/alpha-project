package ru.draelok.gifs;

import feign.Headers;
import feign.Param;
import feign.RequestLine;

import java.net.URI;

public interface ImageDataAPI {
    @RequestLine("GET")
    @Headers("Accept: image/gif")
    byte[] getImageData();
}

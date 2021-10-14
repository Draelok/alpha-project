package ru.draelok.gifs.model;

import lombok.Data;

@Data
public class GifData {
    private String type;
    private String id;
    private String url;
    private GifImages images;
}

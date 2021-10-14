package ru.draelok.gifs;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class GifImages {
    @JsonProperty("fixed_height")
    private GifProps fixedHeight;
}

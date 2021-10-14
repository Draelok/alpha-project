package ru.draelok.gifs;

import feign.Feign;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import feign.okhttp.OkHttpClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.draelok.gifs.api.GifAPI;

@Configuration
public class GifConfig {
    @Bean
    public GifAPI gifAPI(){
        return Feign.builder()
                .client(new OkHttpClient())
                .encoder(new JacksonEncoder())
                .decoder(new JacksonDecoder())
                .target(GifAPI.class, "https://api.giphy.com/v1/gifs");
    }
}

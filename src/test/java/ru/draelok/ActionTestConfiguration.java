package ru.draelok;

import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import ru.draelok.currency.CurrencyAPI;
import ru.draelok.gifs.api.GifAPI;
import ru.draelok.gifs.api.ImageDataAPI;

@Configuration
public class ActionTestConfiguration {
    @Bean
    @Primary
    public CurrencyAPI currencyAPI() {
        return Mockito.mock(CurrencyAPI.class);
    }

    @Bean
    @Primary
    public GifAPI gifAPI() {
        return Mockito.mock(GifAPI.class);
    }

    @Bean
    @Primary
    public ImageDataAPI imageDataAPI() {
        return Mockito.mock(ImageDataAPI.class);
    }
}

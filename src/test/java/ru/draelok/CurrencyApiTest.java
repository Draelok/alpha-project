package ru.draelok;

import feign.Feign;
import feign.okhttp.OkHttpClient;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import ru.draelok.action.GetGifUrlAction;
import ru.draelok.currency.CurrencyAPI;
import ru.draelok.currency.CurrencyData;
import ru.draelok.gifs.api.GifAPI;
import ru.draelok.gifs.api.ImageDataAPI;
import ru.draelok.gifs.model.GifData;
import ru.draelok.gifs.model.GifImages;
import ru.draelok.gifs.model.GifProps;
import ru.draelok.gifs.model.GifResponse;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@ContextConfiguration(classes = {ActionTestConfiguration.class, GetGifUrlAction.class})
public class CurrencyApiTest {

    @Autowired
    private CurrencyAPI currencyAPI;

    @Autowired
    private GifAPI gifAPI;

    @Autowired
    private ImageDataAPI imageDataAPI;

    @Autowired
    private GetGifUrlAction getGifUrlAction;

    @Test
    void getAllTest(){
        CurrencyData data = new CurrencyData();
        Map<String, Double> rates = new HashMap<>();
        rates.put("EUR", 4.2345);
        rates.put("RUB", 60.1841);
        data.setRates(rates);

        when(currencyAPI.getLatest(any())).thenReturn(data);
        var latest = currencyAPI.getLatest(any());

        assertNotNull(latest);
    }

    private String getYesterdayDate() {
        final Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.format(cal.getTime());
    }

    @Test
    void getHistoryTest(){
        CurrencyData data = new CurrencyData();
        HashMap<String, Double> rates = new HashMap<>();
        rates.put("EUR", 5.6659);
        rates.put("RUB", 71.3445);
        data.setRates(rates);

        when(currencyAPI.getHistory(any(), any())).thenReturn(data);
        var res = currencyAPI.getHistory(any(), any());

        assertNotNull(res);
        assertEquals(data, res);
    }

    @Test
    void getImageData(){
        ImageDataAPI imageDataAPI = Feign.builder()
                .client(new OkHttpClient())
                .target(ImageDataAPI.class, "https://media1.giphy.com/media/cZ7rmKfFYOvYI/200.gif");

        var data = imageDataAPI.getImageData();

        assertNotNull(data);
    }

    @Test
    void execTest() throws GetGifUrlAction.CurrencyDataError {
            CurrencyData data = new CurrencyData();
            Map<String, Double> rates = new HashMap<>();
            rates.put("EUR", 7.3445);
            rates.put("RUB", 100.33);
            data.setRates(rates);

            CurrencyData hData = new CurrencyData();
            Map<String, Double> hRates = new HashMap<>();
            hRates.put("EUR", 7.2445);
            hRates.put("RUB", 98.33);
            hData.setRates(hRates);

            GifProps gifProps = new GifProps();
            gifProps.setUrl("http://123123");

            GifImages gifImages = new GifImages();
            gifImages.setFixedHeight(gifProps);

            GifData[] gifData = new GifData[1];
            gifData[0] = new GifData();
            gifData[0].setImages(gifImages);

            GifResponse gifResponse = new GifResponse();
            gifResponse.setData(gifData);

            byte[] bytes = new byte[10];

            when(gifAPI.getGifImage(any(), any())).thenReturn(gifResponse);
            when(currencyAPI.getLatest(any())).thenReturn(data);
            when(currencyAPI.getHistory(any(), any())).thenReturn(hData);

            var imageDataAPIMock = Mockito.mock(ImageDataAPI.class);
            when(imageDataAPIMock.getImageData()).thenReturn(bytes);

            getGifUrlAction.setImageDataAPI(imageDataAPIMock);

            var res = getGifUrlAction.exec();

            assertNotNull(res);
    }
}

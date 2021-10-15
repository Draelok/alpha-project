package ru.draelok.action;

import feign.Feign;
import feign.okhttp.OkHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import ru.draelok.currency.CurrencyAPI;
import ru.draelok.currency.CurrencyData;
import ru.draelok.gifs.api.GifAPI;
import ru.draelok.gifs.api.ImageDataAPI;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Random;

@Component
public class GetGifUrlAction {

    @Autowired
    private Environment environment;

    @Autowired
    private GifAPI gifAPI;

    @Autowired
    private CurrencyAPI currencyAPI;

    private ImageDataAPI imageDataAPI;

    public void setImageDataAPI(ImageDataAPI imageDataAPI){
        this.imageDataAPI = imageDataAPI;
    }

    private final static Logger log = LoggerFactory.getLogger(GetGifUrlAction.class);

    @Value("${draelok.currencyAPI.appID}")
    private String currencyAppID;
    @Value("${draelok.gifAPI.appID}")
    private String gifAppID;
    @Value("${draelok.currencyName:RUB}")
    private String currencyName;

    private static String getYesterdayDate() {
        final Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.format(cal.getTime());
    }

    private String getQuery() throws CurrencyDataError {
        CurrencyData data = currencyAPI.getLatest(currencyAppID);
        CurrencyData hData = currencyAPI.getHistory(getYesterdayDate(), currencyAppID);

        return getRate(data) > getRate(hData) ? "rich" : "broke";
    }

    private double getRate(CurrencyData data) throws CurrencyDataError {
        Double rub = data.getRates().get("RUB");
        if(rub == null || rub == 0.)
            throw new CurrencyDataError();

        Double rates = data.getRates().get(currencyName);
        if(rates == null)
            throw new CurrencyDataError();

        return rates / rub;
    }

    private int getRandNum(int max) {
        Random random = new Random();
        return random.nextInt(max);
    }

    private ImageDataAPI getImageDataAPI(String url){
        return Feign.builder()
                .client(new OkHttpClient())
                .target(ImageDataAPI.class, url);
    }

    public byte[] exec() throws CurrencyDataError{
        var data = gifAPI.getGifImage(getQuery(), gifAppID).getData();

        String url = data[getRandNum(data.length)].getImages().getFixedHeight().getUrl();

        if(imageDataAPI == null)
            return getImageDataAPI(url).getImageData();

        return imageDataAPI.getImageData();
    }

    public static class CurrencyDataError extends Exception{
        public CurrencyDataError(){
            super("Get data error");
        }
    }
}

package ru.draelok.action;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import ru.draelok.currency.CurrencyAPI;
import ru.draelok.currency.CurrencyData;
import ru.draelok.gifs.GifAPI;

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

    private String getQuery(){
        CurrencyData data = currencyAPI.getLatest(currencyAppID);
        CurrencyData hData = currencyAPI.getHistory(getYesterdayDate(), currencyAppID);

        Double num = data.getRates().get(currencyName);
        Double hNum = hData.getRates().get(currencyName);

        return num > hNum ? "rich" : "broke";
    }

    private int getRandNum(int max) {
        Random random = new Random();
        return random.nextInt(max);
    }

    public String exec(){
        var data = gifAPI.getGifImage(getQuery(), gifAppID).getData();

        return data[getRandNum(data.length)].getUrl();
    }
}

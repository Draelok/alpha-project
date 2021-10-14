package ru.draelok;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.draelok.currency.CurrencyAPI;
import ru.draelok.gifs.GifAPI;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class CurrencyApiTest {

    @Autowired
    private CurrencyAPI currencyAPI;

    @Autowired
    private GifAPI gifAPI;

    @Test
    void getAllTest(){
        var data = currencyAPI.getLatest("996f44924d244ceb83bb7dc7dbfe6af5");

        assertNotNull(data);
    }

    private String getYesterdayDate() {
        final Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.format(cal.getTime());
    }

    @Test
    void getHistoryTest(){
        var data = currencyAPI.getHistory(getYesterdayDate(), "996f44924d244ceb83bb7dc7dbfe6af5");

        assertNotNull(data);
    }

    @Test
    void getGif(){
        var data = gifAPI.getGifImage("rich", "aZI6qcUowwoYdvqdHHCCa8PkiyFDoRSM");

        assertNotNull(data);
    }



}

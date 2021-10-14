package ru.draelok.currency;

import feign.Param;
import feign.RequestLine;

public interface CurrencyAPI {
    @RequestLine("GET /latest.json?app_id={app_id}")
    CurrencyData getLatest(@Param("app_id") String appID);

    @RequestLine("GET /historical/{date}.json?app_id={app_id}") //The requested date in YYYY-MM-DD format (required)
    CurrencyData getHistory(@Param("date") String date, @Param("app_id") String appID);
}
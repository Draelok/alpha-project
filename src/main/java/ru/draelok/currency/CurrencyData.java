package ru.draelok.currency;

import lombok.Data;

import java.util.Map;

@Data
public class CurrencyData {
    private String disclaimer;
    private String license;
    private Long timestamp;
    private String base;
    private Map<String, Double> rates;
}

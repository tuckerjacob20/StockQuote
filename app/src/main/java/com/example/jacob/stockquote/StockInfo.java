package com.example.jacob.stockquote;

import android.content.SharedPreferences;

/**
 * Created by Jacob on 7/15/2016.
 */
public class StockInfo {
    private String name;
    private String yearLow;
    private String yearHigh;
    private String dayLow;
    private String dayHigh;
    private String change;
    private String dayRange;
    private String lastTradePriceOnly;

    public StockInfo(String name, String yearLow, String yearHigh, String dayLow, String dayHigh, String change, String dayRange, String lastTradePriceOnly) {
        this.name = name;
        this.yearLow = yearLow;
        this.yearHigh = yearHigh;
        this.dayLow = dayLow;
        this.dayHigh = dayHigh;
        this.change = change;
        this.dayRange = dayRange;
        this.lastTradePriceOnly = lastTradePriceOnly;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getYearLow() {
        return yearLow;
    }

    public void setYearLow(String yearLow) {
        this.yearLow = yearLow;
    }

    public String getYearHigh() {
        return yearHigh;
    }

    public void setYearHigh(String yearHigh) {
        this.yearHigh = yearHigh;
    }

    public String getDayLow() {
        return dayLow;
    }

    public void setDayLow(String dayLow) {
        this.dayLow = dayLow;
    }

    public String getDayHigh() {
        return dayHigh;
    }

    public void setDayHigh(String dayHigh) {
        this.dayHigh = dayHigh;
    }

    public String getChange() {
        return change;
    }

    public void setChange(String change) {
        this.change = change;
    }

    public String getDayRange() {
        return dayRange;
    }

    public void setDayRange(String dayRange) {
        this.dayRange = dayRange;
    }

    public String getLastTradePriceOnly() {
        return lastTradePriceOnly;
    }

    public void setLastTradePriceOnly(String lastTradePriceOnly) {
        this.lastTradePriceOnly = lastTradePriceOnly;
    }
}

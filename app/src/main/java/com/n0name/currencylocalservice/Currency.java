package com.n0name.currencylocalservice;

/**
 * Created by N0Name on 19-Mar-16.
 */
public class Currency {


    private long id;
    private String currency_name;
    private int value;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCurrencyname() {
        return currency_name;
    }

    public int getCurrencyvalue() {
        return value;
    }

    public void setCurrencyname(String currency_name) {
        this.currency_name = currency_name;
    }

    public void setCurrencyvalue(int value) {
        this.value = value;
    }

    // Will be used by the ArrayAdapter in the ListView
    @Override
    public String toString() {
        return currency_name;
    }

}

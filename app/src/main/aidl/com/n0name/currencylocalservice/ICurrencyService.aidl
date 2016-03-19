// ICurrencyService.aidl
package com.n0name.currencylocalservice;

// Declare any non-default types here with import statements

interface ICurrencyService {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
  double getCurrency(String country);
}

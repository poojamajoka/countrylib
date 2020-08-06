package com.boloro.countrylib.helper;

/**
 * filter class
 */
public interface Filterable {
   /**
    *
    * @param asLocale true for locale
    * @return filter data
    */
   String getFilter(boolean asLocale);
}

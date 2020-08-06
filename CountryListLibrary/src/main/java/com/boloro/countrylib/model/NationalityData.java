package com.boloro.countrylib.model;

import androidx.annotation.Keep;

import com.boloro.countrylib.helper.Filterable;
import com.google.gson.annotations.SerializedName;



@Keep
public class NationalityData implements Filterable, Comparable<NationalityData> {
    String id;

    @SerializedName("full_name_locale")
    String fullLocaleName;

    @SerializedName("full_name_english")
    String fullEnglishName;

    public String getFullLocaleName() {
        return fullLocaleName;
    }

    public String getFullEnglishName() {
        return fullEnglishName;
    }

    public String getId() {
        return id;
    }

    @Override
    public String getFilter(boolean asLocale) {
        return asLocale ? fullLocaleName : fullEnglishName;
    }

    @Override
    public int compareTo(NationalityData nationalityData) {
        return this.fullLocaleName.compareTo(nationalityData.fullLocaleName);
    }
}

package com.boloro.countrylib;

import androidx.annotation.Keep;


import com.google.gson.annotations.SerializedName;

import java.util.List;


@Keep
public class Country implements Comparable<Country>, Filterable {
    @SerializedName("id")
    private String mId;

    @SerializedName("two_letter_abbreviation")
    private String mTwoLetterAbr;

    @SerializedName("three_letter_abbreviation")
    private String mThreeLetterAbr;

    @SerializedName("full_name_locale")
    private String mFullLocaleName;

    @SerializedName("full_name_english")
    private String mEnglishLocaleName;

    @SerializedName("phone_code")
    private String isdCode;

    @SerializedName("available_regions")
    private List<Region> mRegions;

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        this.mId = id;
    }

    public String getIsdCode() {
        return isdCode;
    }

    public String getTwoLetterAbr() {
        return mTwoLetterAbr;
    }

    public void setTwoLetterAbr(String twoLetterAbr) {
        this.mTwoLetterAbr = twoLetterAbr;
    }

    public String getThreeLetterAbr() {
        return mThreeLetterAbr;
    }

    public void setThreeLetterAbr(String threeLetterAbr) {
        this.mThreeLetterAbr = threeLetterAbr;
    }

    public String getFullLocaleName() {
        return mFullLocaleName;
    }

    public void setFullLocaleName(String fullLocaleName) {
        this.mFullLocaleName = fullLocaleName;
    }

    public String getEnglishLocaleName() {
        return mEnglishLocaleName;
    }

    public void setEnglishLocaleName(String englishLocaleName) {
        this.mEnglishLocaleName = englishLocaleName;
    }

    public List<Region> getRegions() {
        return mRegions;
    }

    public void setRegions(List<Region> regions) {
        this.mRegions = regions;
    }

    @Override
    public int compareTo(Country country) {
        return this.mFullLocaleName.compareTo(country.mFullLocaleName);
    }

    public void setIsdCode(String isdCode) {
        this.isdCode = isdCode;
    }

    public String getmEnglishLocaleName() {
        return mEnglishLocaleName;
    }

    public String getmFullLocaleName() {
        return mFullLocaleName;
    }

    @Override
    public String getFilter(boolean asLocale) {
        return asLocale ? mFullLocaleName : mEnglishLocaleName;
    }

}

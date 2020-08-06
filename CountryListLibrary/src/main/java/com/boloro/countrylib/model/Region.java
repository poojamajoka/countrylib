package com.boloro.countrylib.model;

import androidx.annotation.Keep;

import com.boloro.countrylib.helper.Filterable;
import com.google.gson.annotations.SerializedName;


/**
 * region model
 */
@Keep
public class Region implements Filterable {
    @SerializedName("id")
    private String mId;

    @SerializedName("code")
    private String mCode;

    @SerializedName("name")
    private String mName;

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        this.mId = id;
    }

    public String getCode() {
        return mCode;
    }

    public void setCode(String code) {
        this.mCode = code;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        this.mName = name;
    }

    @Override
    public String getFilter(boolean asLocale) {
        return mName;
    }
}

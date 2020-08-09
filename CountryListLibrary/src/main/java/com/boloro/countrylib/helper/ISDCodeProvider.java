package com.boloro.countrylib.helper;

import android.content.Context;
import android.text.TextUtils;

import com.boloro.countrylib.R;
import com.boloro.countrylib.model.Country;
import com.boloro.countrylib.model.NationalityData;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * ISC Code and flag handler
 */
public class ISDCodeProvider {
    private static ISDCodeProvider isdCodeProvider;
    private List<Country> countriesCodeList;
    private List<NationalityData> nationalityDataList;
    private List<Country> countriesDataList;
    private String DEFAULT_ISD_CODE = "AE";

    /**
     * @param context class context
     */
    public synchronized static void initialize(Context context, String localeCode) {
        if (isdCodeProvider == null) {
            isdCodeProvider = new ISDCodeProvider();
        }
        isdCodeProvider.loadCountriesWithISDCode(context, R.raw.countries);
        if (!localeCode.isEmpty() && localeCode.equals("en_US")) {
            isdCodeProvider.loadCountriesData(context, R.raw.country_en);
            isdCodeProvider.loadNationalityData(context, R.raw.nationality_en);
        } else {
            isdCodeProvider.loadCountriesData(context, R.raw.country_ar);
            isdCodeProvider.loadNationalityData(context, R.raw.nationality_ar);
        }
    }

    /**
     * @return isd code
     */
    public static ISDCodeProvider getIsdCodeProvider() {
        return isdCodeProvider;
    }

    public void setDefaultISD(String isdCode) {
        DEFAULT_ISD_CODE = isdCode;
    }

    public List<Country> getCountriesCodeList() {
        return countriesCodeList;
    }

    private String loadJSONFromResource(Context context, int rawFile) {
        String json = null;
        try {
            InputStream is = context.getResources().openRawResource(rawFile);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return json;
    }

    /**
     * Returns image res based on country name code
     *
     * @param id id
     * @return flag
     */
    public String getFlagEmoji(String id) {
        switch (id.toLowerCase()) {
            //this should be sorted based on country name code.
            case "ad":
                return "🇦🇩";
            case "ae":
                return "🇦🇪";
            case "af":
                return "🇦🇫";
            case "ag":
                return "🇦🇬";
            case "ai":
                return "🇦🇮";
            case "al":
                return "🇦🇱";
            case "am":
                return "🇦🇲";
            case "ao":
                return "🇦🇴";
            case "aq":
                return "🇦🇶";
            case "ar":
                return "🇦🇷";
            case "as":
                return "🇦🇸";
            case "at":
                return "🇦🇹";
            case "au":
                return "🇦🇺";
            case "aw":
                return "🇦🇼";
            case "ax":
                return "🇦🇽";
            case "az":
                return "🇦🇿";
            case "ba":
                return "🇧🇦";
            case "bb":
                return "🇧🇧";
            case "bd":
                return "🇧🇩";
            case "be":
                return "🇧🇪";
            case "bf":
                return "🇧🇫";
            case "bg":
                return "🇧🇬";
            case "bh":
                return "🇧🇭";
            case "bi":
                return "🇧🇮";
            case "bj":
                return "🇧🇯";
            case "bl":
                return "🇧🇱";
            case "bm":
                return "🇧🇲";
            case "bn":
                return "🇧🇳";
            case "bo":
                return "🇧🇴";
            case "bq":
                return "🇧🇶";
            case "br":
                return "🇧🇷";
            case "bs":
                return "🇧🇸";
            case "bt":
                return "🇧🇹";
            case "bv":
                return "🇧🇻";
            case "bw":
                return "🇧🇼";
            case "by":
                return "🇧🇾";
            case "bz":
                return "🇧🇿";
            case "ca":
                return "🇨🇦";
            case "cc":
                return "🇨🇨";
            case "cd":
                return "🇨🇩";
            case "cf":
                return "🇨🇫";
            case "cg":
                return "🇨🇬";
            case "ch":
                return "🇨🇭";
            case "ci":
                return "🇨🇮";
            case "ck":
                return "🇨🇰";
            case "cl":
                return "🇨🇱";
            case "cm":
                return "🇨🇲";
            case "cn":
                return "🇨🇳";
            case "co":
                return "🇨🇴";
            case "cr":
                return "🇨🇷";
            case "cu":
                return "🇨🇺";
            case "cv":
                return "🇨🇻";
            case "cw":
                return "🇨🇼";
            case "cx":
                return "🇨🇽";
            case "cy":
                return "🇨🇾";
            case "cz":
                return "🇨🇿";
            case "de":
                return "🇩🇪";
            case "dj":
                return "🇩🇯";
            case "dk":
                return "🇩🇰";
            case "dm":
                return "🇩🇲";
            case "do":
                return "🇩🇴";
            case "dz":
                return "🇩🇿";
            case "ec":
                return "🇪🇨";
            case "ee":
                return "🇪🇪";
            case "eg":
                return "🇪🇬";
            case "eh":
                return "🇪🇭";
            case "er":
                return "🇪🇷";
            case "es":
                return "🇪🇸";
            case "et":
                return "🇪🇹";
            case "fi":
                return "🇫🇮";
            case "fj":
                return "🇫🇯";
            case "fk":
                return "🇫🇰";
            case "fm":
                return "🇫🇲";
            case "fo":
                return "🇫🇴";
            case "fr":
                return "🇫🇷";
            case "ga":
                return "🇬🇦";
            case "gb":
                return "🇬🇧";
            case "gd":
                return "🇬🇩";
            case "ge":
                return "🇬🇪";
            case "gf":
                return "🇬🇫";
            case "gg":
                return "🇬🇬";
            case "gh":
                return "🇬🇭";
            case "gi":
                return "🇬🇮";
            case "gl":
                return "🇬🇱";
            case "gm":
                return "🇬🇲";
            case "gn":
                return "🇬🇳";
            case "gp":
                return "🇬🇵";
            case "gq":
                return "🇬🇶";
            case "gr":
                return "🇬🇷";
            case "gs":
                return "🇬🇸";
            case "gt":
                return "🇬🇹";
            case "gu":
                return "🇬🇺";
            case "gw":
                return "🇬🇼";
            case "gy":
                return "🇬🇾";
            case "hk":
                return "🇭🇰";
            case "hm":
                return "🇭🇲";
            case "hn":
                return "🇭🇳";
            case "hr":
                return "🇭🇷";
            case "ht":
                return "🇭🇹";
            case "hu":
                return "🇭🇺";
            case "id":
                return "🇮🇩";
            case "ie":
                return "🇮🇪";
            case "il":
                return "🇮🇱";
            case "im":
                return "🇮🇲";
            case "in":
                return "🇮🇳";
            case "io":
                return "🇮🇴";
            case "iq":
                return "🇮🇶";
            case "ir":
                return "🇮🇷";
            case "is":
                return "🇮🇸";
            case "it":
                return "🇮🇹";
            case "je":
                return "🇯🇪";
            case "jm":
                return "🇯🇲";
            case "jo":
                return "🇯🇴";
            case "jp":
                return "🇯🇵";
            case "ke":
                return "🇰🇪";
            case "kg":
                return "🇰🇬";
            case "kh":
                return "🇰🇭";
            case "ki":
                return "🇰🇮";
            case "km":
                return "🇰🇲";
            case "kn":
                return "🇰🇳";
            case "kp":
                return "🇰🇵";
            case "kr":
                return "🇰🇷";
            case "kw":
                return "🇰🇼";
            case "ky":
                return "🇰🇾";
            case "kz":
                return "🇰🇿";
            case "la":
                return "🇱🇦";
            case "lb":
                return "🇱🇧";
            case "lc":
                return "🇱🇨";
            case "li":
                return "🇱🇮";
            case "lk":
                return "🇱🇰";
            case "lr":
                return "🇱🇷";
            case "ls":
                return "🇱🇸";
            case "lt":
                return "🇱🇹";
            case "lu":
                return "🇱🇺";
            case "lv":
                return "🇱🇻";
            case "ly":
                return "🇱🇾";
            case "ma":
                return "🇲🇦";
            case "mc":
                return "🇲🇨";
            case "md":
                return "🇲🇩";
            case "me":
                return "🇲🇪";
            case "mf":
                return "🇲🇫";
            case "mg":
                return "🇲🇬";
            case "mh":
                return "🇲🇭";
            case "mk":
                return "🇲🇰";
            case "ml":
                return "🇲🇱";
            case "mm":
                return "🇲🇲";
            case "mn":
                return "🇲🇳";
            case "mo":
                return "🇲🇴";
            case "mp":
                return "🇲🇵";
            case "mq":
                return "🇲🇶";
            case "mr":
                return "🇲🇷";
            case "ms":
                return "🇲🇸";
            case "mt":
                return "🇲🇹";
            case "mu":
                return "🇲🇺";
            case "mv":
                return "🇲🇻";
            case "mw":
                return "🇲🇼";
            case "mx":
                return "🇲🇽";
            case "my":
                return "🇲🇾";
            case "mz":
                return "🇲🇿";
            case "na":
                return "🇳🇦";
            case "nc":
                return "🇳🇨";
            case "ne":
                return "🇳🇪";
            case "nf":
                return "🇳🇫";
            case "ng":
                return "🇳🇬";
            case "ni":
                return "🇳🇮";
            case "nl":
                return "🇳🇱";
            case "no":
                return "🇳🇴";
            case "np":
                return "🇳🇵";
            case "nr":
                return "🇳🇷";
            case "nu":
                return "🇳🇺";
            case "nz":
                return "🇳🇿";
            case "om":
                return "🇴🇲";
            case "pa":
                return "🇵🇦";
            case "pe":
                return "🇵🇪";
            case "pf":
                return "🇵🇫";
            case "pg":
                return "🇵🇬";
            case "ph":
                return "🇵🇭";
            case "pk":
                return "🇵🇰";
            case "pl":
                return "🇵🇱";
            case "pm":
                return "🇵🇲";
            case "pn":
                return "🇵🇳";
            case "pr":
                return "🇵🇷";
            case "ps":
                return "🇵🇸";
            case "pt":
                return "🇵🇹";
            case "pw":
                return "🇵🇼";
            case "py":
                return "🇵🇾";
            case "qa":
                return "🇶🇦";
            case "re":
                return "🇷🇪";
            case "ro":
                return "🇷🇴";
            case "rs":
                return "🇷🇸";
            case "ru":
                return "🇷🇺";
            case "rw":
                return "🇷🇼";
            case "sa":
                return "🇸🇦";
            case "sb":
                return "🇸🇧";
            case "sc":
                return "🇸🇨";
            case "sd":
                return "🇸🇩";
            case "se":
                return "🇸🇪";
            case "sg":
                return "🇸🇬";
            case "sh":
                return "🇸🇭";
            case "si":
                return "🇸🇮";
            case "sj":
                return "🇸🇯";
            case "sk":
                return "🇸🇰";
            case "sl":
                return "🇸🇱";
            case "sm":
                return "🇸🇲";
            case "sn":
                return "🇸🇳";
            case "so":
                return "🇸🇴";
            case "sr":
                return "🇸🇷";
            case "ss":
                return "🇸🇸";
            case "st":
                return "🇸🇹";
            case "sv":
                return "🇸🇻";
            case "sx":
                return "🇸🇽";
            case "sy":
                return "🇸🇾";
            case "sz":
                return "🇸🇿";
            case "tc":
                return "🇹🇨";
            case "td":
                return "🇹🇩";
            case "tf":
                return "🇹🇫";
            case "tg":
                return "🇹🇬";
            case "th":
                return "🇹🇭";
            case "tj":
                return "🇹🇯";
            case "tk":
                return "🇹🇰";
            case "tl":
                return "🇹🇱";
            case "tm":
                return "🇹🇲";
            case "tn":
                return "🇹🇳";
            case "to":
                return "🇹🇴";
            case "tr":
                return "🇹🇷";
            case "tt":
                return "🇹🇹";
            case "tv":
                return "🇹🇻";
            case "tw":
                return "🇹🇼";
            case "tz":
                return "🇹🇿";
            case "ua":
                return "🇺🇦";
            case "ug":
                return "🇺🇬";
            case "um":
                return "🇺🇲";
            case "us":
                return "🇺🇸";
            case "uy":
                return "🇺🇾";
            case "uz":
                return "🇺🇿";
            case "va":
                return "🇻🇦";
            case "vc":
                return "🇻🇨";
            case "ve":
                return "🇻🇪";
            case "vg":
                return "🇻🇬";
            case "vi":
                return "🇻🇮";
            case "vn":
                return "🇻🇳";
            case "vu":
                return "🇻🇺";
            case "wf":
                return "🇼🇫";
            case "ws":
                return "🇼🇸";
            case "xk":
                return "🇽🇰";
            case "ye":
                return "🇾🇪";
            case "yt":
                return "🇾🇹";
            case "za":
                return "🇿🇦";
            case "zm":
                return "🇿🇲";
            case "zw":
                return "🇿🇼";
            default:
                return "\uD83C\uDF0D";
        }
    }

    public Country getDefaultCountry() {
        return getCountryFromCountryCode(DEFAULT_ISD_CODE);
    }

    public Country getCountryFromCountryCode(String code) {
        if (TextUtils.isEmpty(code))
            return getDefaultCountry();

        if (!countriesCodeList.isEmpty()) {
            for (Country country : countriesCodeList) {
                if (country.getId().equalsIgnoreCase(code))
                    return country;
            }
        }
        return null;
    }

    public Country getCountryWithoutFlag(String isdCode) {
        Country country = new Country();
        country.setId("\uD83C\uDF0D");
        country.setIsdCode(isdCode);
        return country;
    }


    public Country getCountryFromIsdCode(String isdCode) {
        int count = 0;
        Country c = getCountryWithoutFlag(isdCode);

        for (Country country : countriesCodeList) {
            if (country.getIsdCode().equalsIgnoreCase(isdCode)) {
                c = country;
                if (++count > 1) {
                    c = getCountryWithoutFlag(isdCode);
                    break;
                }
            }
        }
        return c;
    }


    private void loadCountriesWithISDCode(Context context, int rawFile) {
        countriesCodeList = new Gson().fromJson(loadJSONFromResource(context, rawFile),
                new TypeToken<List<Country>>() {
                }.getType());
    }

    private void loadCountriesData(Context context, int rawFile) {
        countriesDataList = new Gson().fromJson(loadJSONFromResource(context, rawFile),
                new TypeToken<List<Country>>() {
                }.getType());
    }

    private void loadNationalityData(Context context, int rawFile) {
        nationalityDataList = new Gson().fromJson(loadJSONFromResource(context, rawFile),
                new TypeToken<List<NationalityData>>() {
                }.getType());
    }

    public List<NationalityData> getNationalityDataList() {
        return nationalityDataList;
    }

    public List<Country> getCountriesDataList() {
        return countriesDataList;
    }
}

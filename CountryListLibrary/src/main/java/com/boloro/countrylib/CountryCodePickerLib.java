package com.boloro.countrylib;

import android.app.Dialog;
import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.widget.ImageView;

import androidx.appcompat.widget.AppCompatEditText;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.boloro.countrylib.adapter.CountrySearchAdapter;
import com.boloro.countrylib.helper.ISDCodeProvider;
import com.boloro.countrylib.helper.Utility;
import com.boloro.countrylib.model.Country;

import java.util.List;

public class CountryCodePickerLib {

    public static CountryCodePickerLib countryCodePickerLib;


    /**
     * intialize isdcode provider
     *
     * @param context class context
     */
    public synchronized static void initialize(Context context, String locale) {
        ISDCodeProvider.initialize(context, locale);
    }

    /**
     * @return instance of class
     */
    public static CountryCodePickerLib getInstance() {
        if (countryCodePickerLib == null) {
            countryCodePickerLib = new CountryCodePickerLib();
        }
        return countryCodePickerLib;
    }


    /**
     * show country dialog
     */
    public void showSearchDialog(Context context, ICountryCodePickerListner listner) {
        Dialog searchDialog = Utility.setDialogProperty(context, R.layout.search_dialog);
        List<Country> countries = ISDCodeProvider.getIsdCodeProvider().getCountriesCodeList();
        RecyclerView recycleView = searchDialog.findViewById(R.id.recycler_view);
        recycleView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        recycleView.setHasFixedSize(true);
        CountrySearchAdapter adapter = new CountrySearchAdapter(countries, true, true);
        recycleView.setAdapter(adapter);
        AppCompatEditText inputView = searchDialog.findViewById(R.id.input_search);
        ImageView ivDelete = searchDialog.findViewById(R.id.iv_delete);
        inputView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                adapter.getFilter().filter(editable.toString());
            }
        });

        adapter.setItemSelectedListener((item, position) -> {
            searchDialog.dismiss();
            if (item != null) {
                if (listner != null) {
                    listner.getCountryCode(item);
                    listner.getCountryFlag(ISDCodeProvider.getIsdCodeProvider()
                            .getFlagEmoji(item.getId()));
                }
            }
        });
        searchDialog.findViewById(R.id.btn_cancel).setOnClickListener(listener_view -> {
            searchDialog.dismiss();
        });
        ivDelete.setOnClickListener(listener_view -> {
            inputView.setText("");
        });
        searchDialog.setCancelable(true);
        searchDialog.show();

    }

    /**
     * @param countryCode user selected countryCode
     */
    public Country getCountryCode(String countryCode) {
        ISDCodeProvider isdCodeProvider = ISDCodeProvider.getIsdCodeProvider();
        Country country = isdCodeProvider.getCountryFromCountryCode(countryCode);
        if (country == null)
            country = isdCodeProvider.getDefaultCountry();
        return (country);
    }

    /**
     * @param isdCode user selected isdcode
     */
    public Country getIsdCode(String isdCode) {
        ISDCodeProvider isdCodeProvider = ISDCodeProvider.getIsdCodeProvider();
        Country country;
        if (TextUtils.isEmpty(isdCode))
            country = isdCodeProvider.getDefaultCountry();
        else
            country = isdCodeProvider.getCountryFromIsdCode(isdCode);
        return country;
    }

    /**
     * @return get country flag
     */
    public String getCountryFlag(Country country) {
        return ISDCodeProvider.getIsdCodeProvider().getFlagEmoji(country.getId());
    }

    /**
     * @return default country code
     */
    public Country getDefaultCountryCode() {
        return ISDCodeProvider.getIsdCodeProvider().getDefaultCountry();
    }

    /**
     * @return get default country flag
     */
    public String getDefaultCountryFlag() {
        return ISDCodeProvider.getIsdCodeProvider().getFlagEmoji(getDefaultCountryCode().getId());
    }

    /**
     * @param isdCode set your default isdCode
     */
    public void setDefaultISD(String isdCode) {
        ISDCodeProvider.getIsdCodeProvider().setDefaultISD(isdCode);
    }


    /**
     * country code listner to return country code and flag
     */
    public interface ICountryCodePickerListner {
        /**
         * @param country return country code
         */
        void getCountryCode(Country country);

        /**
         * @param flag return flag
         */
        void getCountryFlag(String flag);
    }


}



package com.boloro.countrylib;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CountryCodeLib extends AppCompatDialogFragment {
    private Country selectedCountry;
    CountryCodeListner countryCodeListner;

    public CountryCodeLib(CountryCodeListner callback) {
        countryCodeListner = callback;
    }

    public CountryCodeLib() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.search_dialog, container, false);
        showSearchDialog(view);
        return view;
    }

    /**
     * @param callback countrycode callback listner
     * @return instance
     */
    public static CountryCodeLib getInstance(CountryCodeListner callback) {
        return new CountryCodeLib(callback);
    }

    /**
     *
     * @return instance of class
     */
    public static CountryCodeLib getInstance() {
        return new CountryCodeLib();
    }

    /**
     * show country dialog
     *
     * @param view view
     */
    private void showSearchDialog(View view) {
        ISDCodeProvider.initialize(getContext());
        List<Country> countries = ISDCodeProvider.getIsdCodeProvider().getCountries();
        RecyclerView recycleView = view.findViewById(R.id.recycler_view);
        recycleView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        recycleView.setHasFixedSize(true);
        CountrySearchAdapter adapter = new CountrySearchAdapter(countries, true, true);
        recycleView.setAdapter(adapter);
        AppCompatEditText inputView = view.findViewById(R.id.input_search);
        ImageView ivDelete = view.findViewById(R.id.iv_delete);
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
            dismiss();
            if (item != null)
                processIsdCode(item);
        });
        view.findViewById(R.id.btn_cancel).setOnClickListener(listener_view -> {
            dismiss();
        });
        ivDelete.setOnClickListener(listener_view -> {
            inputView.setText("");
        });
        setCancelable(true);


    }

    /**
     *
     * @param countryCode user selected countryCode
     */
    public void setCountryCode(String countryCode) {
        ISDCodeProvider isdCodeProvider = ISDCodeProvider.getIsdCodeProvider();
        Country country = isdCodeProvider.getCountryFromCountryCode(countryCode);
        processIsdCode(country);
    }

    /**
     *
     * @param isdCode user selected isdcode
     */
    public void processIsdCode(String isdCode) {
        ISDCodeProvider isdCodeProvider = ISDCodeProvider.getIsdCodeProvider();
        if (TextUtils.isEmpty(isdCode))
            selectedCountry = isdCodeProvider.getDefaultCountry();
        else
            selectedCountry = isdCodeProvider.getCountryFromIsdCode(isdCode);
        processIsdCode(selectedCountry);
    }

    private void processIsdCode(Country country) {
        ISDCodeProvider isdCodeProvider = ISDCodeProvider.getIsdCodeProvider();
        if (country == null)
            country = isdCodeProvider.getDefaultCountry();
        selectedCountry = country;
        countryCodeListner.getCountryCode(country);
        countryCodeListner.getCountryflag(isdCodeProvider.getFlagEmoji(selectedCountry.getId()));

    }


    /**
     * countrycode listner to return counrty code and flag
     */
    public interface CountryCodeListner {
        /**
         * @param code return country code
         */
        void getCountryCode(Country code);

        /**
         * @param flag return flag
         */
        void getCountryflag(String flag);
    }


}

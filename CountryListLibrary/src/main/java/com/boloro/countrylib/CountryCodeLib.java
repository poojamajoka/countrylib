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

import java.util.Date;
import java.util.List;
import java.util.Locale;

public class CountryCodeLib extends AppCompatDialogFragment {
    private Country selectedCountry;
    CountryCodeListner countryCodeListner;
    private CountryCodeChangeListener countryCodeListener;
    private IsdCodeChangeListener isdCodeListener;

    public CountryCodeLib(CountryCodeListner callback) {
        countryCodeListner=callback;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.search_dialog, container, false);
        showSearchDialog(view);
        return view;
    }

    public static CountryCodeLib getInstance(CountryCodeListner callback) {
        CountryCodeLib picker = new CountryCodeLib(callback);

        return picker;
    }
    private void showSearchDialog(View view) {
        List<Country> countries = ISDCodeProvider.getIsdCodeProvider().getCountries();

       // Dialog searchDialog = AppUtility.setDialogProperty(getContext(), R.layout.search_dialog);
       // searchDialog.setCancelable(true);
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
                setCountryCode(item);
        });
        view.findViewById(R.id.btn_cancel).setOnClickListener(listener_view -> {
            dismiss();
        });
        ivDelete.setOnClickListener(listener_view -> {
            inputView.setText("");
        });
        setCancelable(true);

    }

    public interface CountryCodeChangeListener {
        void onCodeChangeListener(Country country);
    }

    public interface IsdCodeChangeListener {
        void onCodeChangeListener(Country country);
    }

    public void setCountryCode(String countryCode) {
        if (TextUtils.isEmpty(countryCode))
            return;
        ISDCodeProvider isdCodeProvider = ISDCodeProvider.getIsdCodeProvider();
        Country country = isdCodeProvider.getCountryFromCountryCode(countryCode);
        setCountryCode(country);
    }

    public void setCountryCode(Country country) {
        setIsdCode(country);
    }

    public void setIsdCode(String isdCode) {
        ISDCodeProvider isdCodeProvider = ISDCodeProvider.getIsdCodeProvider();
        if (TextUtils.isEmpty(isdCode))
            selectedCountry = isdCodeProvider.getDefaultCountry();
        else
            selectedCountry = isdCodeProvider.getCountryFromIsdCode(isdCode);
        setIsdCode(selectedCountry);
    }

    public void setIsdCode(Country country) {
        ISDCodeProvider isdCodeProvider = ISDCodeProvider.getIsdCodeProvider();
        if (country == null)
            country = isdCodeProvider.getDefaultCountry();

        selectedCountry = country;
        countryCodeListner.getCountryCode(String.format(Locale.ENGLISH, "+%s", selectedCountry.getIsdCode()));
        countryCodeListner.getCountryflag(isdCodeProvider.getFlagEmoji(selectedCountry.getId()));
      //  mBinding.inputCountyFlag.setText(isdCodeProvider.getFlagEmoji(selectedCountry.getId()));
       // mBinding.tvIsdCode.setText(String.format(Locale.ENGLISH, "+%s", selectedCountry.getIsdCode()));

        if (countryCodeListener != null)
            countryCodeListener.onCodeChangeListener(selectedCountry);
        if (isdCodeListener != null)
            isdCodeListener.onCodeChangeListener(selectedCountry);
    }
    public void setCountryCodeChangeListener(CountryCodeChangeListener listener) {
        this.countryCodeListener = listener;
    }

    public void setIsdCodeChangeListener(IsdCodeChangeListener listener) {
        this.isdCodeListener = listener;
    }


    public interface CountryCodeListner{
        void getCountryCode(String code);
        void getCountryflag(String flag);
    }


}

package com.boloro.countrylib;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;



import java.util.ArrayList;
import java.util.List;
import java.util.Locale;



public class CountrySearchAdapter extends SearchAdapter<Country> {
    private boolean showFlag;

    public CountrySearchAdapter() {
        this(new ArrayList<>(),false, false);
    }

    public CountrySearchAdapter(List<Country> list, boolean showAsLocale, boolean showFlag) {
        super(list,showAsLocale);
        this.showFlag = showFlag;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ItemViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(R.layout.item_country_search, parent, false));

    }

    @Override
    public void onBindViewHolder(@NonNull SearchAdapter.ItemViewHolder holder, int position) {
        Country country = getItemList().get(position);
        AppCompatTextView tvFlag = holder.itemView.findViewById(R.id.tv_flag);
        AppCompatTextView tvIsdCode = holder.itemView.findViewById(R.id.tv_isd_code);
        AppCompatTextView tvName = holder.itemView.findViewById(R.id.tv_name);

        String text = country.getFilter(showAsLocale);
        tvName.setText(text);

        if (showFlag) {
            String emoji = ISDCodeProvider.getIsdCodeProvider().getFlagEmoji(country.getId());
            tvFlag.setVisibility(View.VISIBLE);
            tvFlag.setText(emoji);
        } else
            tvFlag.setVisibility(View.GONE);

        if (!TextUtils.isEmpty(country.getIsdCode())) {
            text = String.format(Locale.ENGLISH, "+%s", country.getIsdCode());
            tvIsdCode.setVisibility(View.VISIBLE);
            tvIsdCode.setText(text);
        } else
            tvIsdCode.setVisibility(View.GONE);


    }

}

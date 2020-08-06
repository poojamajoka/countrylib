package com.boloro.countrylib;

import android.app.Dialog;
import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import androidx.appcompat.widget.AppCompatEditText;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.boloro.countrylib.adapter.CountrySearchAdapter;
import com.boloro.countrylib.adapter.SearchAdapter;
import com.boloro.countrylib.helper.Filterable;
import com.boloro.countrylib.helper.ISDCodeProvider;
import com.boloro.countrylib.model.Country;
import com.boloro.countrylib.model.NationalityData;
import com.boloro.countrylib.model.Region;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CountryNationalityLib {
    private WeakReference<Context> mContext;

    private static CountryNationalityLib countryNationalityLib;
    private List<Region> regionList;

    public synchronized static void initialize(Context context) {
        if (countryNationalityLib == null) {
            new CountryNationalityLib(context);
        }
    }

    public static CountryNationalityLib getCountryNationalityLib() {
       return countryNationalityLib;
    }

    private CountryNationalityLib(Context context) {
        mContext = new WeakReference<>(context);
    }

    private Dialog showSearchDialog(SearchAdapter<? extends Filterable> searchAdapter, SearchAdapter.CancelListener cancelListener) {
        Dialog searchDialog = setDialogProperty(mContext.get(), R.layout.search_dialog);
        searchDialog.setCancelable(true);
        RecyclerView recycleView = searchDialog.findViewById(R.id.recycler_view);
        recycleView.setLayoutManager(new LinearLayoutManager(mContext.get(), LinearLayoutManager.VERTICAL, false));
        recycleView.setHasFixedSize(true);
        recycleView.setAdapter(searchAdapter);
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
                ivDelete.setVisibility(TextUtils.isEmpty(editable.toString()) ? View.GONE : View.VISIBLE);
                searchAdapter.getFilter().filter(editable.toString());
            }
        });

        searchDialog.findViewById(R.id.btn_cancel).setOnClickListener(view -> {
            searchDialog.cancel();
            cancelListener.onCancel();
        });
        /*can(dialogInterface -> {
            ((BaseActivity) getActivity()).hideKeyboard();
        });*/
        ivDelete.setOnClickListener(view -> inputView.setText(""));

        //searchDialog.show();
        return searchDialog;
    }

    public void showCountryDialog(ICountryCallBack countryCallBack) {
        CountrySearchAdapter adapter = new CountrySearchAdapter(ISDCodeProvider.getIsdCodeProvider().getCountriesDataList(), true, false);
        Dialog dialog = showSearchDialog(adapter, () -> {
            if (countryCallBack != null)
                countryCallBack.onCancel();
        });
        adapter.setItemSelectedListener((item, position) -> {
            dialog.cancel();
            regionList = item.getRegions();
            if (regionList == null) {
                regionList = new ArrayList<>();
            }
            if (countryCallBack != null)
                countryCallBack.itemSelect(item);
        });
        dialog.show();
    }

    public void showStateDialog(IStateCallBack iStateCallBack) {
        SearchAdapter<Region> adapter = new SearchAdapter(regionList, true);
        Dialog dialog = showSearchDialog(adapter, () -> {
            if (iStateCallBack != null)
                iStateCallBack.onCancel();
        });
        adapter.setItemSelectedListener((item, position) -> {
            dialog.cancel();
            if (iStateCallBack != null)
                iStateCallBack.itemSelect(item);

        });
    }

    public void showNationalityDialog(INationalityCallBack iNationalityCallBack) {
        SearchAdapter<NationalityData> adapter = new SearchAdapter(ISDCodeProvider.getIsdCodeProvider().getNationalityDataList(), true);
        Dialog dialog = showSearchDialog(adapter, () -> {
            if (iNationalityCallBack != null)
                iNationalityCallBack.onCancel();
        });
        adapter.setItemSelectedListener((item, position) -> {
            dialog.cancel();
            if (iNationalityCallBack != null)
                iNationalityCallBack.itemSelect(item);

        });
    }


    /**
     * set dialog property
     *
     * @param context class context
     * @param layout  layout
     * @return dialog object
     */
    private Dialog setDialogProperty(final Context context, final int layout) {
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        Objects.requireNonNull(dialog.getWindow()).
                setBackgroundDrawableResource(android.R.color.transparent);
        dialog.setContentView(layout);
        final WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.gravity = Gravity.CENTER;
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.getWindow().setAttributes(lp);
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        return dialog;
    }

    public interface ICountryCallBack {
        void itemSelect(Country item);

        void onCancel();
    }

    public interface IStateCallBack {
        void itemSelect(Region item);

        void onCancel();
    }

    public interface INationalityCallBack {
        void itemSelect(NationalityData item);

        void onCancel();
    }
}

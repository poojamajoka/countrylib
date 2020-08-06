package com.boloro.countrylib.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.boloro.countrylib.helper.Filterable;
import com.boloro.countrylib.R;
import com.boloro.countrylib.helper.SearchText;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Search Adapter
 * @param <T> pass class
 */
public class SearchAdapter<T extends Filterable> extends RecyclerView.Adapter<SearchAdapter.ItemViewHolder> implements android.widget.Filterable {

    protected boolean showAsLocale;
    protected List<T> itemList;
    private ItemSelectedListener<T> listener;
    private SearchText<T> searchCountryName;

    private SearchAdapter() {
        showAsLocale = false;
        this.itemList = new ArrayList<>();
    }

    public SearchAdapter(boolean showAsLocale) {
        this();
        this.showAsLocale = showAsLocale;
    }

    /**
     *
     * @param list list
     * @param showAsLocale true for locale
     */
    public SearchAdapter(List<T> list, boolean showAsLocale) {
        this(showAsLocale);
        if (list != null)
            this.itemList.addAll(list);
    }

    /**
     *
     * @param listener listner
     */
    public void setItemSelectedListener(ItemSelectedListener<T> listener) {
        this.listener = listener;
    }

    public void setItemList(List<? extends Filterable> list) {
        if (itemList == null || itemList.isEmpty())
            this.itemList = new ArrayList<>();

        itemList.clear();

        if (list != null) {
            itemList.addAll((Collection<T>) list);
        }
        notifyDataSetChanged();

    }

    public List<T> getItemList() {
        return itemList;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ItemViewHolder(
                (TextView) LayoutInflater.from(parent.getContext()).inflate(R.layout.textview, parent, false));

    }

    @Override
    public void onBindViewHolder(@NonNull SearchAdapter.ItemViewHolder holder, int position) {
        Filterable item = getItemList().get(position);

        ((TextView) holder.itemView).setText(item.getFilter(showAsLocale));
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    @Override
    public Filter getFilter() {

        if (searchCountryName == null) {
            searchCountryName = new SearchText<>(this, itemList, showAsLocale);
        }
        return searchCountryName;

    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {

        ItemViewHolder(@NonNull View itemView) {
            super(itemView);

            itemView.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onItemSelected(itemList.get(getAdapterPosition()), getAdapterPosition());
                }
            });
        }
    }

    public interface ItemSelectedListener<T extends Filterable> {
        void onItemSelected(T item, int position);
    }

    public interface CancelListener {
        void onCancel();
    }

}

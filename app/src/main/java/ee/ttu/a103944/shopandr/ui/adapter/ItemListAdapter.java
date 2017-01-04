package ee.ttu.a103944.shopandr.ui.adapter;

import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import ee.ttu.a103944.shopandr.model.Catalog;
import ee.ttu.a103944.shopandr.model.PagesInfo;


public abstract class ItemListAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    protected List<Catalog> childs;
    protected PagesInfo pagesInfo;
    private String TAG = "ItemListAdapter";
    private List<T> items;

    public ItemListAdapter() {
        this.items = new ArrayList<>();
        this.childs = new ArrayList<>();
        pagesInfo = new PagesInfo();
    }


    public void addItems(List<T> items, List<Catalog> childs, PagesInfo pagesInfo) {
        this.items.addAll(items);

        if (childs != null)
            this.childs = childs;
        this.pagesInfo = pagesInfo;
        notifyItemRangeInserted(getItemCount() - (items.size() + (childs != null ? childs.size() : 0)),
                (items.size() != 0 ? (items.size()) : (childs != null ? childs.size() : 0)));//+childs.size()));
    }

    public void removeItem(int location) {
        items.remove(location);
        notifyDataSetChanged();

    }

    public void delAll() {
        this.items.clear();
        this.childs.clear();
        this.pagesInfo = new PagesInfo();
        notifyDataSetChanged();
    }

    public List<T> getItems() {
        return items;
    }

    @Override
    public int getItemCount() {
        return items.size() + childs.size();
    }
}

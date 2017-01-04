package ee.ttu.a103944.shopandr.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ee.ttu.a103944.shopandr.R;
import ee.ttu.a103944.shopandr.model.Catalog;


public class CategoryAdapter extends BaseAdapter {

    private List<Catalog> catalogs = new ArrayList<>();
    private LayoutInflater inflater;

    public CategoryAdapter(Context context) {
        inflater = LayoutInflater.from(context);
    }

    public void swapCtlgs(List<Catalog> catalogs) {
        if (catalogs != null) {
            this.catalogs.clear();
            this.catalogs.addAll(catalogs);
            notifyDataSetChanged();
        }
    }

    @Override
    public int getCount() {
        return catalogs.size();
    }

    @Override
    public Object getItem(int i) {
        return catalogs.get(i);
    }

    @Override
    public long getItemId(int i) {
        return catalogs.get(i).getId();
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.v_category_spinner_row, viewGroup, false);
            CatalogViewHolder catalogViewHolder = new CatalogViewHolder();
            catalogViewHolder.tv = (TextView) convertView;
            convertView.setTag(catalogViewHolder);
        }
        CatalogViewHolder catalogViewHolder = ((CatalogViewHolder) convertView.getTag());
        catalogViewHolder.tv.setText(catalogs.get(i).getTitle());

        return convertView;
    }

    private class CatalogViewHolder {
        public TextView tv;
    }
}

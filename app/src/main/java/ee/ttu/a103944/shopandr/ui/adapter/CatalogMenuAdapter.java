package ee.ttu.a103944.shopandr.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import ee.ttu.a103944.shopandr.model.Catalog;


public class CatalogMenuAdapter extends BaseAdapter {

    private Context context;
    private List<Catalog> catalogs;
    private LayoutInflater layoutInflater;

    public CatalogMenuAdapter(Context context, List<Catalog> catalogs) {
        this.context = context;
        this.catalogs = catalogs;
        layoutInflater = ((Activity) context).getLayoutInflater();
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

    private Catalog getCatalog(int pos) {
        return (Catalog) getItem(pos);
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        if (convertView == null) {
            convertView = layoutInflater.inflate(android.R.layout.simple_list_item_1, viewGroup, false);
            ViewHolder viewHolder = new ViewHolder();
            viewHolder.tv = ((TextView) convertView.findViewById(android.R.id.text1));
            convertView.setTag(viewHolder);
        }

        Catalog catalog = getCatalog(i);
        ((ViewHolder) convertView.getTag()).tv.setText(catalog.getTitle());
        return convertView;
    }

    private class ViewHolder {
        public TextView tv;
    }
}

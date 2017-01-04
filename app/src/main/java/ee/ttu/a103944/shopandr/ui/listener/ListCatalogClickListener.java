package ee.ttu.a103944.shopandr.ui.listener;

import android.view.View;
import android.widget.AdapterView;

import ee.ttu.a103944.shopandr.model.Catalog;


public class ListCatalogClickListener implements AdapterView.OnItemClickListener {

    private onItemClickListener listener;

    public ListCatalogClickListener(onItemClickListener listener) {
        this.listener = listener;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        listener.onItemClick((Catalog) adapterView.getItemAtPosition(i), i);
    }

    public interface onItemClickListener {
        void onItemClick(Catalog catalog, int pos);
    }
}

package ee.ttu.a103944.shopandr.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import ee.ttu.a103944.shopandr.R;
import ee.ttu.a103944.shopandr.model.PreparedProduct;
import ee.ttu.a103944.shopandr.model.Stock;


public class StocksFragment extends AbstractFragment {

    private static String ARG_PP = "pp";
    private String TAG = "StocksFragment";
    private PreparedProduct pp;
    private ListView stocksL;
    private StocksListAdapter stockAdapter;


    public static StocksFragment newInstance(PreparedProduct pp) {
        StocksFragment fragment = new StocksFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_PP, pp);
        fragment.setArguments(args);
        return fragment;
    }

    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null)
            return;

        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(),
                View.MeasureSpec.UNSPECIFIED);
        int totalHeight = 0;
        View view = null;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            view = listAdapter.getView(i, view, listView);
            if (i == 0)
                view.setLayoutParams(new ViewGroup.LayoutParams(desiredWidth,
                        LinearLayout.LayoutParams.WRAP_CONTENT));

            view.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            totalHeight += view.getMeasuredHeight() + 50;
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight
                + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        params.height = Math.max(params.height, 150);
        listView.setLayoutParams(params);
        listView.requestLayout();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null) {
            pp = savedInstanceState.getParcelable("pp1");
        } else
            pp = getArguments().getParcelable(ARG_PP);
        stockAdapter = new StocksListAdapter(getActivity().getLayoutInflater());

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelable("pp1", pp);
        super.onSaveInstanceState(outState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_stocks, container, false);

        stocksL = (ListView) view.findViewById(R.id.stocksl);
        stocksL.setAdapter(stockAdapter);
        stockAdapter.setPp(pp);
        setListViewHeightBasedOnChildren(stocksL);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private class StocksListAdapter extends BaseAdapter {

        PreparedProduct pp = new PreparedProduct();
        private LayoutInflater layoutInflater;

        public StocksListAdapter(LayoutInflater layoutInflater) {
            this.layoutInflater = layoutInflater;
        }

        public void setPp(PreparedProduct pp) {
            this.pp = pp;
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return pp.getProduct().getStocks().size();
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return (long) i;
        }

        @Override
        public View getView(int position, View view, ViewGroup viewGroup) {
            if (view == null) {
                view = layoutInflater.inflate(R.layout.prod_stock_item, viewGroup, false);
                myVH myVH = new myVH();
                myVH.quantity = (TextView) view.findViewById(R.id.quantity);
                myVH.ship_date = (TextView) view.findViewById(R.id.ship_date);
                myVH.shop_address = (TextView) view.findViewById(R.id.shop_address);
                myVH.shop_name = (TextView) view.findViewById(R.id.shop_name);
                view.setTag(myVH);
            }

            int quantity = ((Stock) pp.getProduct().getStocks().toArray()[position]).getQuantity();
            ((myVH) view.getTag()).quantity.setText("" + quantity);
            ((myVH) view.getTag()).ship_date.setText(((Stock) pp.getProduct().getStocks().toArray()[position]).getShipment_type().getShip_date());
            ((myVH) view.getTag()).shop_address.setText(((Stock) pp.getProduct().getStocks().toArray()[position]).getShop().getShop_address());
            ((myVH) view.getTag()).shop_name.setText(((Stock) pp.getProduct().getStocks().toArray()[position]).getShop().getShop_name());
            return view;
        }

        private class myVH {
            TextView quantity;
            TextView ship_date;
            TextView shop_address;
            TextView shop_name;

        }
    }

}

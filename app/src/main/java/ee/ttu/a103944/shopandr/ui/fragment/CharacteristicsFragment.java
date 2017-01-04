package ee.ttu.a103944.shopandr.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import ee.ttu.a103944.shopandr.R;
import ee.ttu.a103944.shopandr.model.Detail;
import ee.ttu.a103944.shopandr.model.PreparedProduct;


public class CharacteristicsFragment extends AbstractFragment {

    private static String ARG_PP = "pp";
    private String TAG = "CharacteristicsFragment";
    private PreparedProduct pp;
    private ListView detList;
    private DetListAdapter detAdapter;


    public static CharacteristicsFragment newInstance(PreparedProduct pp) {
        CharacteristicsFragment fragment = new CharacteristicsFragment();
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
            totalHeight += view.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight
                + (listView.getDividerHeight() * (listAdapter.getCount() - 1)) + 200;
        listView.setLayoutParams(params);
        listView.requestLayout();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate");

        if (savedInstanceState != null)
            pp = savedInstanceState.getParcelable("pp");
        else
            pp = getArguments().getParcelable(ARG_PP);
        detAdapter = new DetListAdapter(getActivity().getLayoutInflater());


    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable("pp", pp);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView");
        return inflater.inflate(R.layout.fragment_prod_det, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d(TAG, "onViewCreated");
        detList = findView(R.id.detlist);
        detList.setAdapter(detAdapter);
        detAdapter.setPp(pp);
        setListViewHeightBasedOnChildren(detList);
    }

    private class DetListAdapter extends BaseAdapter {

        PreparedProduct pp = new PreparedProduct();
        private LayoutInflater layoutInflater;

        public DetListAdapter(LayoutInflater layoutInflater) {
            this.layoutInflater = layoutInflater;
        }

        public void setPp(PreparedProduct pp) {
            this.pp = pp;
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return pp.getProduct().getDetails().size();
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
                view = layoutInflater.inflate(R.layout.prod_det_item, viewGroup, false);
                myVH myVH = new myVH();
                myVH.name = (TextView) view.findViewById(R.id.name);
                myVH.value = (TextView) view.findViewById(R.id.value);
                view.setTag(myVH);
            }

            ((myVH) view.getTag()).name.setText(((Detail) pp.getProduct().getDetails().toArray()[position]).getName());
            ((myVH) view.getTag()).value.setText(((Detail) pp.getProduct().getDetails().toArray()[position]).getValue());
            return view;
        }

        private class myVH {
            TextView name;
            TextView value;
        }
    }
}

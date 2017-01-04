package ee.ttu.a103944.shopandr.ui.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import ee.ttu.a103944.shopandr.R;
import ee.ttu.a103944.shopandr.ui.activity.BasketListActivity;


public class BasketSummaryFragment extends AbstractFragment {

    public static String TAG = "BasketSummaryFragment";

    private String totalAmount;
    private TextView totalAmountTV;
    private String totalPrice;
    private TextView totalPriceTV;
    private View orderButton;
    private Listener listener;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            this.listener = (Listener) getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException(getActivity().toString() + " must implement BasketSummaryFragment.Listener");
        }
        ;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_basket_summary, container, false);
        if (savedInstanceState != null) {
            totalPrice = savedInstanceState.getString("price");
            totalAmount = savedInstanceState.getString("amount");
        }
        totalAmountTV = (TextView) view.findViewById(R.id.total_amount);
        totalPriceTV = (TextView) view.findViewById(R.id.total_sum);
        orderButton = view.findViewById(R.id.pay);
        orderButton.setOnClickListener(new Listener2());
        view.findViewById(R.id.basket_layout).setOnClickListener(new Listener3());
        updateLabels();
        return view;
    }

    public void updateSummary(String totalAmount, String totalPrice, BasketSummaryFragment basketSummaryFragment) {
        this.totalAmount = totalAmount;
        this.totalPrice = totalPrice;
        if (getView() != null) {
            updateLabels();
        }
        if (basketSummaryFragment.isHidden() && Integer.valueOf(totalAmount) > 0)
            setBasketSummaryVisibility(true, basketSummaryFragment);
        else if (Integer.valueOf(totalAmount) == 0)
            setBasketSummaryVisibility(false, basketSummaryFragment);
    }

    private void setBasketSummaryVisibility(boolean visible, BasketSummaryFragment basketSummaryFragment) {
        Activity activity = getActivity();
        FragmentManager fm = getActivity().getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();

        if (visible) {
            ft.show(basketSummaryFragment);
        } else {
            ft.hide(basketSummaryFragment);
        }
        ft.commit();
    }

    private void updateLabels() {
        Resources res = getActivity().getResources();
        String st1 = String.format(res.getString(R.string.total_item), totalAmount);
        String st2 = String.format(res.getString(R.string.total_price), totalPrice);
        this.totalPriceTV.setText(st1);
        this.totalAmountTV.setText(st2);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("amount", totalAmount);
        outState.putString("price", totalPrice);
    }

    public interface Listener {
        void onStartCheckoutProcess();
    }

    private class Listener2 implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            listener.onStartCheckoutProcess();
        }
    }

    private class Listener3 implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            BasketListActivity.start(getActivity());
        }
    }
}

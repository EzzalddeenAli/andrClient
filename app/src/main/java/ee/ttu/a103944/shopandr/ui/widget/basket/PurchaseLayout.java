package ee.ttu.a103944.shopandr.ui.widget.basket;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.text.NumberFormat;
import java.util.Locale;

import ee.ttu.a103944.shopandr.BuildConfig;
import ee.ttu.a103944.shopandr.R;


public class PurchaseLayout extends RelativeLayout {
    private TextView priceTextView;

    public PurchaseLayout(Context context) {
        super(context);
    }

    public PurchaseLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        ((LayoutInflater) context.getSystemService("layout_inflater"))
                .inflate(R.layout.v_purchase_layout,this,true);
        priceTextView = (TextView) findViewById(R.id.price_text);
        setBackgroundColor(getResources().getColor(R.color.dark_red));
    }



    public PurchaseLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void updateData(String price) {
        priceTextView.setText(price);
    }
}

package ee.ttu.a103944.shopandr.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import ee.ttu.a103944.shopandr.utils.LocaleHelper;
import ee.ttu.a103944.shopandr.utils.ViewUtils;


public abstract class AbstractActivity extends AppCompatActivity {

    private String TAG = "AbstractActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LocaleHelper.onCreate(this);
    }

    public <V extends View> V findView(int id) {
        return ViewUtils.findView(this, id);
    }

}

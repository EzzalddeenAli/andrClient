package ee.ttu.a103944.shopandr;

import android.app.Application;

import com.squareup.leakcanary.LeakCanary;

import ee.ttu.a103944.shopandr.utils.Constant;
import ee.ttu.a103944.shopandr.utils.LocaleHelper;


public class ShopApplication  extends Application{

    @Override
    public void onCreate() {
        String currentLanguage = LocaleHelper.getLanguage(this);
        if(!currentLanguage.equals(Constant.LANG_EN) || !currentLanguage.equals(Constant.LANG_ET) ||
                !currentLanguage.equals(Constant.LANG_RU))
            LocaleHelper.onCreate(this, Constant.LANG_RU);
        super.onCreate();
    }
}

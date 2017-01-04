package ee.ttu.a103944.shopandr.utils;

import android.app.Activity;
import android.view.View;

public class ViewUtils {

    public static <V extends View> V findView(Activity activity,int id){
        V v = null;
        v= (V) activity.findViewById(id);
        return v;
    }

    public static <V extends View> V findView(final View root, final int id) {
        V v = null;
        try {
            v = (V) root.findViewById(id);
        } catch (final ClassCastException cce) {
        }
        return v;
    }
}

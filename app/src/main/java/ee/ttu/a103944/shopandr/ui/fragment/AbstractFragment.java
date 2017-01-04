package ee.ttu.a103944.shopandr.ui.fragment;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import ee.ttu.a103944.shopandr.R;
import ee.ttu.a103944.shopandr.utils.ViewUtils;


public class AbstractFragment extends Fragment {

    public static String TAG = "AbstractFragment";

    private boolean activityDestroyed;
    private boolean fragmentsStateSaved;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
        } catch (ClassCastException e) {

        }
    }

    public <V extends View> V findView(final int id) {
        return ViewUtils.findView(getView(), id);
    }

    @Override
    public void onStart() {
        super.onStart();
        fragmentsStateSaved = false;
    }

    @Override
    public void onStop() {
        super.onStop();
        fragmentsStateSaved = true;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        activityDestroyed = true;
    }
}

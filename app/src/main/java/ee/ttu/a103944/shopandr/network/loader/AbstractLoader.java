package ee.ttu.a103944.shopandr.network.loader;


import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import ee.ttu.a103944.shopandr.network.response.AbstractResponse;


public abstract class AbstractLoader extends AsyncTaskLoader<AbstractResponse> {

    String TAG = "AbstractLoader";

    public AbstractLoader(Context context) {
        super(context);
        Log.d(TAG, "Constructor");
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        Log.d(TAG, "onStartLoading ");
        forceLoad();
    }

    @Override
    protected void onStopLoading() {
        //super.onStopLoading();
        Log.d(TAG, "onStopLoading ");
        boolean canceled = cancelLoad();

    }

    @Override
    protected void onReset() {
        Log.d(TAG, "onReset ");
        onStopLoading();
    }
}

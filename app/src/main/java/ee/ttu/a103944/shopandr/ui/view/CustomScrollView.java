package ee.ttu.a103944.shopandr.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;


public class CustomScrollView extends ScrollView {

    private ScrollViewListener scrollViewListener;

    public interface ScrollViewListener {
        void onScrollChanged(CustomScrollView customScrollView, int i, int i2, int i3, int i4);
    }

    public CustomScrollView(Context context) {
        super(context);
        scrollViewListener=null;
    }

    public CustomScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        scrollViewListener=null;
    }

    public CustomScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        scrollViewListener=null;
    }

    public void setScrollViewListener(ScrollViewListener scrollViewListener) {
        this.scrollViewListener = scrollViewListener;
    }

    @Override
    protected void onScrollChanged(int x, int y, int oldx, int oldy) {
        super.onScrollChanged(x, y, oldx, oldy);
        if(scrollViewListener!=null){
            scrollViewListener.onScrollChanged(this,x,y,oldx,oldy);
        }
    }
}

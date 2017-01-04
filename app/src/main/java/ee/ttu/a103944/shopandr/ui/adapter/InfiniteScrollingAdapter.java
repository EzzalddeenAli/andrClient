package ee.ttu.a103944.shopandr.ui.adapter;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.List;

import ee.ttu.a103944.shopandr.model.Catalog;
import ee.ttu.a103944.shopandr.model.PagesInfo;


public abstract class InfiniteScrollingAdapter<T> extends ItemListAdapter<T> {

    private T load_more_view = null;
    private boolean isLoading = false;
    private int visibleThreshold = 0;
    private LinearLayoutManager linearLayoutManager;

    private OnLoadMoreProductsListener onLoadMoreProductsListener;

    public InfiniteScrollingAdapter(RecyclerView recyclerView) {
        super();
        linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        recyclerView.addOnScrollListener(getOnScrollListener());
    }

    public void setOnLoadMoreProductsListener(OnLoadMoreProductsListener onLoadMoreProductsListener) {
        this.onLoadMoreProductsListener = onLoadMoreProductsListener;
    }

    public RecyclerView.OnScrollListener getOnScrollListener() {
        return new RecyclerView.OnScrollListener() {

            private boolean toLast = false;

            @Override
            public void onScrollStateChanged(final RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    int totalItemCount = linearLayoutManager.getItemCount();
                    int lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
                    if (toLast && !isLoading && (totalItemCount - 1) <= (lastVisibleItem + visibleThreshold) && pagesInfo.isHasNext()) {
                        if (onLoadMoreProductsListener != null) {

                            addLoadMoreViewItem();
                            isLoading = true;
                            recyclerView.smoothScrollToPosition(getItemCount() - 1);
                            onLoadMoreProductsListener.onLoadMoreProducts();
                        }
                    }
                }
            }

            @Override
            public void onScrolled(final RecyclerView recyclerView, int dx, final int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if (dy > 0) {
                    toLast = true;
                } else
                    toLast = false;
            }
        };
    }

    public void setLoaded() {
        isLoading = false;
    }

    private void addLoadMoreViewItem() {
        getItems().add(load_more_view);
        notifyItemInserted(getItemCount() - 1);
    }

    private void removeLoadMoreViewItem() {
        if (getItems().size() > 0) {
            getItems().remove(getItems().size() - 1);
            notifyItemRemoved(getItemCount());
        }
    }

    @Override
    public void addItems(List<T> items, List<Catalog> childs, PagesInfo pagesInfo) {
        removeLoadMoreViewItem();
        super.addItems(items, childs, pagesInfo);
        setLoaded();

    }

    public interface OnLoadMoreProductsListener {
        void onLoadMoreProducts();
    }


}

package ee.ttu.a103944.shopandr.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import ee.ttu.a103944.shopandr.R;
import ee.ttu.a103944.shopandr.model.Catalog;
import ee.ttu.a103944.shopandr.model.PreparedProduct;
import ee.ttu.a103944.shopandr.network.service.ServiceCreator;


public class ProductListAdapter extends InfiniteScrollingAdapter<PreparedProduct> {

    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;
    private final int VIEW_TYPE_CATALOG = 2;
    private Context context;
    private Activity activity;
    private LayoutInflater layoutInflater;
    private ProductlistListener productlistListener;
    private int mCurrentPosition = -1;
    public ProductListAdapter(RecyclerView recyclerView, Context context, Activity activity,
                              ProductlistListener productlistListener) {
        super(recyclerView);
        this.context = context;
        this.activity = activity;
        this.layoutInflater = layoutInflater.from(context);
        this.productlistListener = productlistListener;
    }

    public int getCurrentPosition() {
        return mCurrentPosition;
    }

    @Override
    public int getItemViewType(int position) {
        if (childs.size() > 0 && position < childs.size()) {
            return VIEW_TYPE_CATALOG;
        }
        return getItems().get(position - childs.size()) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_ITEM) {
            View view = layoutInflater
                    .inflate(R.layout.product_item, parent, false);
            return new ProductItemViewHolder(view);
        } else if (viewType == VIEW_TYPE_LOADING) {
            View view = layoutInflater
                    .inflate(R.layout.load_more_product, parent, false);
            return new LoadingMoreItemsViewHolder(view);
        } else if (viewType == VIEW_TYPE_CATALOG) {
            View view = layoutInflater
                    .inflate(R.layout.detail_category_item, parent, false);
            return new CatalogItemViewHolder(view);
        }

        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (holder.getItemViewType() == VIEW_TYPE_ITEM) {
            ProductItemViewHolder productItemViewHolder = (ProductItemViewHolder) holder;
            mCurrentPosition = holder.getAdapterPosition();
            PreparedProduct pProd = getItems().get(position - childs.size());

            initializeThumbnailImage(productItemViewHolder.thumbnail, pProd.getProduct().getImage());
            productItemViewHolder.nameField.setText(pProd.getProduct().getName());
            productItemViewHolder.priceField.setText(pProd.getProduct().getPrice().toString());
        } else if (holder.getItemViewType() == VIEW_TYPE_CATALOG) {
            CatalogItemViewHolder catalogItemViewHolder = (CatalogItemViewHolder) holder;
            Catalog catalog = childs.get(position);
            catalogItemViewHolder.title.setText(catalog.getTitle());
        }
    }

    private void initializeThumbnailImage(ImageView thumbnailImage, String url) {

        Glide.with(activity)
                .load(String.format("%s%s", ServiceCreator.API_ENDPOINT, url))
                .into(thumbnailImage);
    }

    public interface ProductlistListener {
        void onCatalogClick(String catalog);

        void onProdClick(int pos);

    }

    public class CatalogItemViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        TextView title;


        public CatalogItemViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.category_title);
            title.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            productlistListener.onCatalogClick(((TextView) view).getText().toString());
        }
    }

    public class ProductItemViewHolder extends RecyclerView.ViewHolder {

        TextView nameField;
        TextView priceField;
        Button buyButton;
        private ImageView thumbnail;


        public ProductItemViewHolder(View itemView) {
            super(itemView);
            thumbnail = (ImageView) itemView.findViewById(R.id.thumbnailImage);
            nameField = (TextView) itemView.findViewById(R.id.nameField);
            priceField = (TextView) itemView.findViewById(R.id.PriceField);
            buyButton = (Button) itemView.findViewById(R.id.buyButton);

            buyButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = getLayoutPosition();
                    productlistListener.onProdClick(pos);
                }
            });
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = getLayoutPosition();
                    productlistListener.onProdClick(pos);
                }
            });
        }
    }

    public class LoadingMoreItemsViewHolder extends RecyclerView.ViewHolder {

        public LoadingMoreItemsViewHolder(View itemView) {
            super(itemView);
        }
    }
}

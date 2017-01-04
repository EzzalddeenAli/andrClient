package ee.ttu.a103944.shopandr.ui.fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import ee.ttu.a103944.shopandr.R;
import ee.ttu.a103944.shopandr.model.CartInfo;
import ee.ttu.a103944.shopandr.model.Product;
import ee.ttu.a103944.shopandr.model.ProductInfoDTO;
import ee.ttu.a103944.shopandr.network.loader.ProductInfoLoader;
import ee.ttu.a103944.shopandr.network.response.AbstractResponse;
import ee.ttu.a103944.shopandr.network.service.OrderService;
import ee.ttu.a103944.shopandr.network.service.ServiceCreator;
import ee.ttu.a103944.shopandr.ui.adapter.ProdViewPagerAdapter;
import ee.ttu.a103944.shopandr.ui.view.CustomScrollView;
import ee.ttu.a103944.shopandr.ui.widget.basket.PurchaseLayout;
import ee.ttu.a103944.shopandr.utils.BasketUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ItemDetailFragment extends AbstractFragment {

    public static String TAG = "ItemDetailFragment";
    public static String ARG_URL = "url";
    private final int[] coordinates;
    private String url;
    private PurchaseLayout clingingPurchaseLayout;
    private PurchaseLayout scrollingPurchaseLayout;
    private int topOffset;
    private ProductInfoDTO piDTO;
    private boolean isClingingLayoutShown;
    private ImageView image;
    private TextView title;
    private TextView description;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private ProdViewPagerAdapter prodViewPagerAdapter;
    private BasketSummaryFragment basketSummaryFragment;
    private LoaderManager.LoaderCallbacks<AbstractResponse> productsInfoCallbacks = new LoaderManager.LoaderCallbacks<AbstractResponse>() {
        @Override
        public Loader<AbstractResponse> onCreateLoader(int id, Bundle args) {
            Log.d(TAG, "onCreateLoader ");
            switch (id) {
                case R.id.productInfo_loader: {
                    return new ProductInfoLoader(getActivity(), url);
                }
                default: {
                    return null;
                }
            }
        }

        @Override
        public void onLoadFinished(Loader<AbstractResponse> loader, AbstractResponse response) {
            Log.d(TAG, "onLoadFinished ");
            int id = loader.getId();
            if (id == R.id.productInfo_loader) {
                switch (response.getRequestStatus()) {
                    case SUCCUSS:
                        ProductInfoDTO piDTO = response.getResponse();
                        ItemDetailFragment.this.piDTO = piDTO;
                        Log.d(TAG, "loaded " + piDTO.getPp().getProduct().getName());
                        String imgUrl = piDTO.getPp().getProduct().getImage();
                        loadPhoto(imgUrl);
                        Log.d(TAG, "img " + imgUrl);
                        title.setText(piDTO.getPp().getProduct().getName());

                        Product product = piDTO.getPp().getProduct();
                        String attrs = "";
                        if (product.getType().equalsIgnoreCase("monitors")) {
                            attrs = "brand:" + product.getBrand() + " \n";
                            attrs += "resolution:" + product.getResolution() + " \n";
                            attrs += "screen:" + product.getScreen() + " \n";
                            attrs += "Resp time:" + product.getResp_time() + " \n";
                        } else if (product.getType().equalsIgnoreCase("smartphones")) {
                            attrs = "brand:" + product.getBrand() + " \n";
                            attrs += "resolution:" + product.getResolution() + " \n";
                            attrs += "color:" + product.getColor() + " \n";
                            attrs += "Battery life:" + product.getBattery_life() + " \n";
                        }
                        description.setText(piDTO.getPp().getProduct().getDescription() + " \n" + attrs);

                        prodViewPagerAdapter.setPp(piDTO.getPp());
                        clingingPurchaseLayout.updateData(piDTO.getPp().getProduct().getPrice().toString());
                        scrollingPurchaseLayout.updateData(piDTO.getPp().getProduct().getPrice().toString());

                        break;
                    case ERROR:
                        Toast.makeText(getActivity(), "prodInfo.error!", Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        break;
                }


            }
            Log.d(TAG, "loader destroy ");
            getActivity().getSupportLoaderManager().destroyLoader(id);
        }

        @Override
        public void onLoaderReset(Loader<AbstractResponse> loader) {

        }
    };
    private View.OnClickListener buyOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            OrderService orderService = ServiceCreator.createService(OrderService.class, getActivity());
            Call<CartInfo> call = orderService.addToBasket("" + piDTO.getPp().getProduct().getId(), "" + 1);
            call.enqueue(new Callback<CartInfo>() {
                @Override
                public void onResponse(Call<CartInfo> call, Response<CartInfo> response) {
                    CartInfo cartInfo = response.body();
                    basketSummaryFragment
                            .updateSummary("" + cartInfo.getTotalItems()
                                    , cartInfo.getTotalPrice().toString()
                                    , basketSummaryFragment);
                }

                @Override
                public void onFailure(Call<CartInfo> call, Throwable t) {

                }
            });
        }
    };

    public ItemDetailFragment() {
        coordinates = new int[2];
    }

    public static ItemDetailFragment getInstance(String url) {
        Bundle args = new Bundle();
        args.putString(ARG_URL, url);
        ItemDetailFragment itemDetailFragment = new ItemDetailFragment();
        itemDetailFragment.setArguments(args);
        return itemDetailFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        prodViewPagerAdapter = new ProdViewPagerAdapter(getActivity().getSupportFragmentManager(), getActivity());


        if (savedInstanceState != null) {
            url = savedInstanceState.getString("ARG_URL");
            piDTO = savedInstanceState.getParcelable("piDTO");
            prodViewPagerAdapter.setPp(piDTO.getPp());
        } else {
            url = getArguments().getString(ARG_URL);
            getActivity().getSupportLoaderManager().initLoader(R.id.productInfo_loader, Bundle.EMPTY, productsInfoCallbacks);
        }

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product_details, container, false);
        initPurchaseLayout(view);
        initPhoto(view);

        title = (TextView) view.findViewById(R.id.item_title_long);
        description = (TextView) view.findViewById(R.id.item_description_text);
        viewPager = (ViewPager) view.findViewById(R.id.prod_viewpager);
        tabLayout = (TabLayout) view.findViewById(R.id.prod_tabs);

        viewPager.setAdapter(prodViewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);

        if (savedInstanceState != null) {
            String imgUrl = piDTO.getPp().getProduct().getImage();
            loadPhoto(imgUrl);
            title.setText(piDTO.getPp().getProduct().getName());
            Product product = piDTO.getPp().getProduct();
            String attrs = "";
            if (product.getType().equalsIgnoreCase("monitors")) {
                attrs = "brand:" + product.getBrand() + " \n";
                attrs += "resolution:" + product.getResolution() + " \n";
                attrs += "screen:" + product.getScreen() + " \n";
                attrs += "Resp time:" + product.getResp_time() + " \n";
            } else if (product.getType().equalsIgnoreCase("smartphones")) {
                attrs = "brand:" + product.getBrand() + " \n";
                attrs += "resolution:" + product.getResolution() + " \n";
                attrs += "color:" + product.getColor() + " \n";
                attrs += "Battery life:" + product.getBattery_life() + " \n";
            }
            description.setText(piDTO.getPp().getProduct().getDescription() + " \n" + attrs);

            clingingPurchaseLayout.updateData(piDTO.getPp().getProduct().getPrice().toString());
            scrollingPurchaseLayout.updateData(piDTO.getPp().getProduct().getPrice().toString());
        }
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        basketSummaryFragment = new BasketSummaryFragment();
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.basket_summary_frame, basketSummaryFragment, BasketSummaryFragment.TAG)
                .hide(basketSummaryFragment).commit();

    }

    @Override
    public void onResume() {
        super.onResume();
        BasketUtils.updateBasket(basketSummaryFragment, getActivity());
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    private void loadPhoto(String imgUrl) {
        new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... params) {
                Glide.get(getActivity()).clearDiskCache();
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Glide.get(getActivity()).clearMemory();
                    }
                });

                return null;
            }

        }.execute();

        Glide.with(getActivity())
                .load(String.format("%s%s", ServiceCreator.API_ENDPOINT, imgUrl))
                .into(image)
        ;
    }

    private void initPhoto(View view) {
        image = (ImageView) view.findViewById(R.id.image);
    }

    private void initPurchaseLayout(View view) {
        final View drawerLayout = getActivity().findViewById(R.id.drawerLayout);
        drawerLayout.post(new OffsetCalc(drawerLayout));
        clingingPurchaseLayout = (PurchaseLayout) view.findViewById(R.id.clinging_order);
        clingingPurchaseLayout.updateData("0");
        clingingPurchaseLayout.setOnClickListener(buyOnClickListener);
        scrollingPurchaseLayout = (PurchaseLayout) view.findViewById(R.id.order_container);
        scrollingPurchaseLayout.updateData("0");
        scrollingPurchaseLayout.setOnClickListener(buyOnClickListener);
        ((CustomScrollView) view.findViewById(R.id.observable_scroll_view)).setScrollViewListener(new ScrollViewListener());
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(ARG_URL, url);
        outState.putParcelable("piDTO", piDTO);
    }

    class OffsetCalc implements Runnable {
        private final View drawerLayout;

        OffsetCalc(View drawerLayout) {
            this.drawerLayout = drawerLayout;
        }

        @Override
        public void run() {
            DisplayMetrics dm = new DisplayMetrics();
            getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
            Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
            topOffset = dm.heightPixels - drawerLayout.getMeasuredHeight() + toolbar.getMeasuredHeight();
        }
    }

    class ScrollViewListener implements CustomScrollView.ScrollViewListener {

        @Override
        public void onScrollChanged(CustomScrollView customScrollView, int i, int i2, int i3, int i4) {
            scrollingPurchaseLayout.getLocationInWindow(coordinates);
            if (coordinates[1] - topOffset <= 0 && !isClingingLayoutShown) {
                clingingPurchaseLayout.setVisibility(View.VISIBLE);
                scrollingPurchaseLayout.setVisibility(View.INVISIBLE);
                isClingingLayoutShown = true;
            } else if (coordinates[1] - topOffset > 0) {
                clingingPurchaseLayout.setVisibility(View.INVISIBLE);
                scrollingPurchaseLayout.setVisibility(View.VISIBLE);
                isClingingLayoutShown = false;
            }
        }
    }

}

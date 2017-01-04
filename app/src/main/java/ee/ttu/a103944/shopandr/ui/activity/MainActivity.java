package ee.ttu.a103944.shopandr.ui.activity;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import ee.ttu.a103944.shopandr.R;
import ee.ttu.a103944.shopandr.event.UserEvents;
import ee.ttu.a103944.shopandr.model.AccInfoDTO;
import ee.ttu.a103944.shopandr.network.service.ServiceCreator;
import ee.ttu.a103944.shopandr.network.service.UserService;
import ee.ttu.a103944.shopandr.ui.adapter.NavMenuAdapter;
import ee.ttu.a103944.shopandr.ui.fragment.BasketSummaryFragment;
import ee.ttu.a103944.shopandr.ui.fragment.MainCatalogFragment;
import ee.ttu.a103944.shopandr.utils.BasketUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AbstractActivity implements
        BasketSummaryFragment.Listener {

    DrawerLayout drawerLayout;
    NavMenuAdapter navMenuAdapter;
    private String TAG = "MainActivity";
    private BasketSummaryFragment basketSummaryFragment;
    private List<Integer> menuContents;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate ");
        setContentView(R.layout.activity_main);

        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        float dpH = displayMetrics.heightPixels / displayMetrics.density;
        Log.d(TAG, "width is " + dpWidth + " " + dpH + " " + displayMetrics.density);

        Toolbar toolbar = (Toolbar) findViewById((R.id.toolbar));
        setSupportActionBar(toolbar);
        initDrawer(toolbar);
        menuContents = new ArrayList<>();

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.right_drawer, new MainCatalogFragment(), MainCatalogFragment.TAG)
                    .commit();
        }

        final RecyclerView menuRV = findView(R.id.navrv);
        menuRV.setLayoutManager(new LinearLayoutManager(this));
        navMenuAdapter = new NavMenuAdapter(getLayoutInflater(), this);
        menuRV.setAdapter(navMenuAdapter);

        basketSummaryFragment = new BasketSummaryFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.basket_summary_frame, basketSummaryFragment, BasketSummaryFragment.TAG)
                .hide(basketSummaryFragment).commit();
    }

    public void initDrawer(Toolbar toolbar) {
        drawerLayout = findView(R.id.drawerLayout);
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this,
                drawerLayout, toolbar, R.string.app_name, R.string.app_name);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onHandleLogin(UserEvents.LoginEvent loginEvent) {
        Log.d(TAG, "onHandleLogin " + this + " " + Thread.currentThread().getId());
        updateMenu();
        BasketUtils.updateBasket(basketSummaryFragment, MainActivity.this);
    }

    private void updateMenu() {

        UserService orderService = ServiceCreator.createService(UserService.class
                , this);
        Call<AccInfoDTO> call = orderService.getUserInfo();
        call.enqueue(new Callback<AccInfoDTO>() {
            @Override
            public void onResponse(Call<AccInfoDTO> call, Response<AccInfoDTO> response) {

                AccInfoDTO accInfoDTO = response.body();
                menuContents = new ArrayList<Integer>();
                menuContents.add(NavMenuAdapter.VIEW_TYPE_MAIN);
                menuContents.add(NavMenuAdapter.VIEW_TYPE_PROFILE_LABEL);
                if (accInfoDTO.getUser() != null && !accInfoDTO.getUser().getNick().isEmpty()) {
                    menuContents.add(NavMenuAdapter.VIEW_TYPE_NICK);
                    menuContents.add(NavMenuAdapter.VIEW_TYPE_ORDERS);
                    menuContents.add(NavMenuAdapter.VIEW_TYPE_LOGOUT);
                    navMenuAdapter.setNick(accInfoDTO.getUser().getNick());
                } else {
                    menuContents.add(NavMenuAdapter.VIEW_TYPE_AUTHORIZATION);
                    menuContents.add(NavMenuAdapter.VIEW_TYPE_REGISTER);
                }
                menuContents.add(NavMenuAdapter.VIEW_TYPE_BASKET_LABEL);
                if (accInfoDTO.getCart().getTotalItems() > 0) {
                    menuContents.add(NavMenuAdapter.VIEW_TYPE_BASKET_ENTRY);
                    navMenuAdapter.setTotalAmount("" + accInfoDTO.getCart().getTotalItems(),
                            accInfoDTO.getCart().getTotalPrice().toString());
                } else {
                    menuContents.add(NavMenuAdapter.VIEW_TYPE_BASKET_VALUE);
                    navMenuAdapter.setTotalAmount("" + accInfoDTO.getCart().getTotalItems(),
                            accInfoDTO.getCart().getTotalPrice().toString());
                }
                menuContents.add(NavMenuAdapter.VIEW_TYPE_ABOUT_SHOP_LABEL);
                menuContents.add(NavMenuAdapter.VIEW_TYPE_ABOUT_SHOP_CONTACTS);
                menuContents.add(NavMenuAdapter.VIEW_TYPE_ABOUT_APP);
                menuContents.add(NavMenuAdapter.VIEW_TYPE_LANGUAGE);
                navMenuAdapter.setMenuContents(menuContents);
                navMenuAdapter.notifyDataSetChanged();

            }

            @Override
            public void onFailure(Call<AccInfoDTO> call, Throwable t) {
                int i = 1;
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        BasketUtils.updateBasket(basketSummaryFragment, MainActivity.this);
        EventBus.getDefault().register(this);
        updateMenu();
    }

    @Override
    protected void onPause() {
        super.onPause();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy ");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {

        return super.onCreateView(parent, name, context, attrs);
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START) || drawerLayout.isDrawerOpen(GravityCompat.END)) {
            drawerLayout.closeDrawer(GravityCompat.START);
            drawerLayout.closeDrawer(GravityCompat.END);
        } else
            super.onBackPressed();
    }

    public void openDrawer(int id) {
        if (!drawerLayout.isDrawerOpen(id)) {
            drawerLayout.openDrawer(id);
        }
    }


    public void closeDrawer() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START) || drawerLayout.isDrawerOpen(GravityCompat.END)) {
            drawerLayout.closeDrawer(GravityCompat.START);
            drawerLayout.closeDrawer(GravityCompat.END);
        }
    }


    @Override
    public void onStartCheckoutProcess() {

    }
}

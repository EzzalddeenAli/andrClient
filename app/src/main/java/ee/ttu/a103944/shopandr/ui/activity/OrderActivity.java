package ee.ttu.a103944.shopandr.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.math.BigDecimal;

import ee.ttu.a103944.shopandr.R;
import ee.ttu.a103944.shopandr.model.OrderDTO;
import ee.ttu.a103944.shopandr.model.OrderItem;
import ee.ttu.a103944.shopandr.model.PreparedOrderItem;
import ee.ttu.a103944.shopandr.network.service.OrderService;
import ee.ttu.a103944.shopandr.network.service.ServiceCreator;
import ee.ttu.a103944.shopandr.ui.fragment.BasketSummaryFragment;
import ee.ttu.a103944.shopandr.ui.fragment.ItemDetailFragment;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class OrderActivity extends AbstractActivity implements
        BasketSummaryFragment.Listener {

    private String TAG = "OrderActivity";

    private OrderDTO orderDTO;
    private RecyclerView order_items;
    private OrderAdapter orderAdapter;
    private String title;
    private boolean twoPane;


    public static void start(Activity activity, String title) {
        Intent intent = new Intent(activity, OrderActivity.class);
        intent.putExtra("title", title);
        activity.startActivityForResult(intent, 0);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_order);
        Toolbar toolbar = findView(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        title = getIntent().getStringExtra("title");

        setTitle("Order " + title);

        if (findViewById(R.id.product_detail_container) != null) {
            twoPane = true;
        }

        order_items = findView(R.id.order_items);
        order_items.setLayoutManager(new LinearLayoutManager(this));
        orderAdapter = new OrderAdapter(getLayoutInflater());
        order_items.setAdapter(orderAdapter);

        if (savedInstanceState != null) {
            orderDTO = savedInstanceState.getParcelable("ordersDTO");
            orderAdapter.setOrderDTO(orderDTO);
            orderAdapter.notifyDataSetChanged();
        } else {

            OrderService orderService = ServiceCreator.createService(OrderService.class, this);
            Call<OrderDTO> call = orderService.getOrderById(title);
            call.enqueue(new Callback<OrderDTO>() {
                @Override
                public void onResponse(Call<OrderDTO> call, Response<OrderDTO> response) {
                    OrderActivity.this.orderDTO = response.body();
                    orderAdapter.setOrderDTO(OrderActivity.this.orderDTO);
                    orderAdapter.notifyDataSetChanged();
                }

                @Override
                public void onFailure(Call<OrderDTO> call, Throwable t) {

                }
            });
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.d(TAG, "onOptionsItemSelected");
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable("ordersDTO", orderDTO);
    }

    @Override
    public void onStartCheckoutProcess() {

    }

    private void showNoProdFrameIfNeed() {
        if (!twoPane) {
            return;
        }
        RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.select_product_frame);
        if (orderAdapter.getCurrentPosition() == -1) {
            if (relativeLayout != null) {
                relativeLayout.setVisibility(View.VISIBLE);
            }
        } else {
            if (relativeLayout != null) {
                relativeLayout.setVisibility(View.GONE);
            }
        }
    }

    private class OrderAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        OrderDTO orderDTO = new OrderDTO();
        private int view_type_header = 0;
        private int view_type_item = 1;
        private LayoutInflater layoutInflater;

        private int mCurrentPosition = -1;
        private View.OnClickListener onNameClick = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = (String) view.getTag();
                if (twoPane) {
                    ItemDetailFragment itemDetailFragment = ItemDetailFragment.getInstance(url);
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.product_detail_container, itemDetailFragment, ItemDetailFragment.TAG)
                            .commit();
                    showNoProdFrameIfNeed();
                } else {
                    ItemActivity.start(OrderActivity.this, url);
                }
            }
        };

        public OrderAdapter(LayoutInflater layoutInflater) {
            this.layoutInflater = layoutInflater;
        }

        public int getCurrentPosition() {
            return mCurrentPosition;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            if (viewType == view_type_header) {
                View view = layoutInflater.inflate(R.layout.v_order_header, parent, false);
                return new HeaderVH(view);
            } else if (viewType == view_type_item) {
                View view = layoutInflater.inflate(R.layout.v_order_item, parent, false);
                return new ItemVH(view);
            }
            return null;
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            int i = holder.getItemViewType();
            if (i == view_type_item) {
                mCurrentPosition = holder.getAdapterPosition();
                ItemVH itemVH = (ItemVH) holder;
                PreparedOrderItem preparedOrderItem = (PreparedOrderItem) orderDTO.getPorder().getPrepOrderItem().toArray()[position - 1];
                OrderItem orderItem = preparedOrderItem.getOrderItem();
                itemVH.name.setText("" + orderItem.getProduct().getName());
                itemVH.name.setEnabled(true);
                itemVH.name.setTag(preparedOrderItem.getUrl() + "/" + orderItem.getProduct().getName() + "-" +
                        orderItem.getProduct().getId());
                itemVH.qty.setText("" + orderItem.getQty());
                itemVH.price.setText(orderItem.getPrice().toString());
                itemVH.cost.setText(orderItem.getPrice().multiply(new BigDecimal(orderItem.getQty())).toString());
            }
        }

        @Override
        public int getItemViewType(int position) {
            return position == 0 ? view_type_header : view_type_item;
        }

        @Override
        public int getItemCount() {
            return orderDTO.getPorder().getPrepOrderItem().size() + 1;
        }

        public void setOrderDTO(OrderDTO orderDTO) {
            this.orderDTO = orderDTO;
        }

        private class HeaderVH extends RecyclerView.ViewHolder {

            public HeaderVH(View itemView) {
                super(itemView);
            }
        }

        private class ItemVH extends RecyclerView.ViewHolder {

            TextView name;
            TextView qty;
            TextView price;
            TextView cost;

            public ItemVH(View itemView) {
                super(itemView);
                name = (TextView) itemView.findViewById(R.id.name);
                name.setOnClickListener(onNameClick);
                qty = (TextView) itemView.findViewById(R.id.qty);
                price = (TextView) itemView.findViewById(R.id.price);
                cost = (TextView) itemView.findViewById(R.id.cost);
            }
        }
    }
}

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
import android.widget.TextView;

import ee.ttu.a103944.shopandr.R;
import ee.ttu.a103944.shopandr.model.Order;
import ee.ttu.a103944.shopandr.model.OrdersDTO;
import ee.ttu.a103944.shopandr.model.PreparedOrder;
import ee.ttu.a103944.shopandr.network.service.OrderService;
import ee.ttu.a103944.shopandr.network.service.ServiceCreator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class OrdersActivity extends AbstractActivity {

    private String TAG = "OrdersActivity";

    private OrdersDTO ordersDTO;
    private RecyclerView orders_list;
    private OrdersAdapter ordersAdapter;

    public static void start(Activity activity) {
        Intent intent = new Intent(activity, OrdersActivity.class);
        activity.startActivityForResult(intent, 0);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_orders);

        Toolbar toolbar = findView(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setTitle("Orders");

        orders_list = findView(R.id.orders_list);
        orders_list.setLayoutManager(new LinearLayoutManager(this));
        ordersAdapter = new OrdersAdapter(getLayoutInflater());
        orders_list.setAdapter(ordersAdapter);

        if (savedInstanceState != null) {
            ordersDTO = savedInstanceState.getParcelable("ordersDTO");
            ordersAdapter.setOrdersDTO(ordersDTO);
            ordersAdapter.notifyDataSetChanged();
        } else {
            OrderService orderService = ServiceCreator.createService(OrderService.class, this);
            Call<OrdersDTO> call = orderService.getOrders();
            call.enqueue(new Callback<OrdersDTO>() {
                @Override
                public void onResponse(Call<OrdersDTO> call, Response<OrdersDTO> response) {
                    OrdersActivity.this.ordersDTO = response.body();
                    ordersAdapter.setOrdersDTO(OrdersActivity.this.ordersDTO);
                    ordersAdapter.notifyDataSetChanged();
                }

                @Override
                public void onFailure(Call<OrdersDTO> call, Throwable t) {

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
        outState.putParcelable("ordersDTO", ordersDTO);
    }

    private class OrdersAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        OrdersDTO ordersDTO = new OrdersDTO();
        private int view_type_header = 0;
        private int view_type_item = 1;
        private LayoutInflater layoutInflater;
        private View.OnClickListener onNoClick = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OrderActivity.start(OrdersActivity.this, ((Integer) view.getTag()).toString());
            }
        };

        public OrdersAdapter(LayoutInflater layoutInflater) {
            this.layoutInflater = layoutInflater;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            if (viewType == view_type_header) {
                View view = layoutInflater.inflate(R.layout.v_orders_header, parent, false);
                return new HeaderVH(view);
            } else if (viewType == view_type_item) {
                View view = layoutInflater.inflate(R.layout.v_orders_item, parent, false);
                return new ItemVH(view);
            }
            return null;
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            int i = holder.getItemViewType();
            if (i == view_type_item) {
                ItemVH itemVH = (ItemVH) holder;
                PreparedOrder porder = (PreparedOrder) ordersDTO.getOrders().toArray()[position - 1];
                Order order = porder.getOrder();
                itemVH.invno.setText("" + order.getId());
                itemVH.invno.setEnabled(true);
                itemVH.invno.setTag(order.getId());
                itemVH.orddate.setText(order.getCreationDate().toString());
                itemVH.ordsum.setText(porder.getSubtotal().toString());
                itemVH.ordsts.setText(order.getOrder_status().getName());
            }
        }

        @Override
        public int getItemViewType(int position) {
            return position == 0 ? view_type_header : view_type_item;
        }

        @Override
        public int getItemCount() {
            return ordersDTO.getOrders().size() + 1;
        }

        public void setOrdersDTO(OrdersDTO ordersDTO) {
            this.ordersDTO = ordersDTO;
        }

        private class HeaderVH extends RecyclerView.ViewHolder {

            public HeaderVH(View itemView) {
                super(itemView);
            }
        }

        private class ItemVH extends RecyclerView.ViewHolder {

            TextView invno;
            TextView orddate;
            TextView ordsum;
            TextView ordsts;

            public ItemVH(View itemView) {
                super(itemView);
                invno = (TextView) itemView.findViewById(R.id.invno);
                invno.setOnClickListener(onNoClick);
                orddate = (TextView) itemView.findViewById(R.id.orddate);
                ordsum = (TextView) itemView.findViewById(R.id.ordsum);
                ordsts = (TextView) itemView.findViewById(R.id.ordsts);
            }
        }
    }
}

package ee.ttu.a103944.shopandr.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import ee.ttu.a103944.shopandr.R;
import ee.ttu.a103944.shopandr.model.BasketListDTO;
import ee.ttu.a103944.shopandr.model.ItemProps;
import ee.ttu.a103944.shopandr.model.PreparedProduct;
import ee.ttu.a103944.shopandr.model.Product;
import ee.ttu.a103944.shopandr.network.loader.BasketListLoader;
import ee.ttu.a103944.shopandr.network.response.AbstractResponse;
import ee.ttu.a103944.shopandr.network.service.OrderService;
import ee.ttu.a103944.shopandr.network.service.ServiceCreator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class BasketListFragment extends AbstractFragment {

    private static String TAG = "BasketListFragment";

    private BasketListDTO blDTO;
    private RecyclerView basketList;
    private BasketItemsAdapter basketItemsAdapter;
    private EditText fn;
    private EditText ln;
    private EditText phone;
    private EditText adr;
    private EditText email;
    private View.OnClickListener orderListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            OrderService orderService = ServiceCreator.createService(OrderService.class
                    , getActivity());
            Call<String> call = orderService.createOrder(
                    fn.getText().toString(),
                    ln.getText().toString(),
                    phone.getText().toString(),
                    email.getText().toString(),
                    adr.getText().toString()
            );
            call.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    int code = response.code();
                    if (code != 404) {
                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.content,
                                new OrderCreateSuccFragment(),
                                OrderCreateSuccFragment.class.toString()).commit();
                    }
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {

                }
            });

        }
    };
    private LoaderManager.LoaderCallbacks<AbstractResponse> cartsInfoCallbacks = new LoaderManager.LoaderCallbacks<AbstractResponse>() {
        @Override
        public Loader<AbstractResponse> onCreateLoader(int id, Bundle args) {
            Log.d(TAG, "onCreateLoader ");
            switch (id) {
                case R.id.cartInfo_loader: {
                    return new BasketListLoader(getActivity());
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
            if (id == R.id.cartInfo_loader) {
                switch (response.getRequestStatus()) {
                    case SUCCUSS:
                        BasketListDTO blDTO = response.getResponse();
                        BasketListFragment.this.blDTO = blDTO;
                        basketItemsAdapter.setBlDTO(blDTO);

                        break;
                    case ERROR:
                        Toast.makeText(getActivity(), "basketlist.error!", Toast.LENGTH_SHORT).show();
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

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        basketItemsAdapter = new BasketItemsAdapter(getActivity().getLayoutInflater(),
                getActivity().getSupportFragmentManager(),
                getActivity());

        if (savedInstanceState != null) {
            blDTO = savedInstanceState.getParcelable("blDTO");
            basketItemsAdapter.setBlDTO(blDTO);
        } else {
            getActivity().getSupportLoaderManager().initLoader(R.id.cartInfo_loader, Bundle.EMPTY, cartsInfoCallbacks);

        }


    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_basket, container, false);

        fn = (EditText) view.findViewById(R.id.fname);
        ln = (EditText) view.findViewById(R.id.lname);
        phone = (EditText) view.findViewById(R.id.phone);
        adr = (EditText) view.findViewById(R.id.address);
        email = (EditText) view.findViewById(R.id.email);

        Button order = (Button) view.findViewById(R.id.order);
        order.setOnClickListener(orderListener);

        basketList = (RecyclerView) view.findViewById(R.id.basket_list);
        basketList.setLayoutManager(new LinearLayoutManager(getActivity()));

        basketList.setAdapter(basketItemsAdapter);

        setupRecyclerView(basketList);

        return view;
    }

    private void setupRecyclerView(@NonNull RecyclerView basketList) {
        ItemTouchHelper.SimpleCallback simpleItemTouchCallback =
                new ItemTouchHelper.SimpleCallback(0,//idle
                        ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
                    @Override
                    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                        return false;
                    }

                    @Override
                    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                        BasketItemsAdapter.MyVH myVH = (BasketItemsAdapter.MyVH) viewHolder;
                        int id = (Integer) myVH.del.getTag();
                        OrderService orderService = ServiceCreator.createService(OrderService.class
                                , getActivity());
                        Call<BasketListDTO> call = orderService.delFromCart("" + id);
                        call.enqueue(new Callback<BasketListDTO>() {
                            @Override
                            public void onResponse(Call<BasketListDTO> call, Response<BasketListDTO> response) {
                                BasketListDTO blDTO = response.body();
                                //class has reference to fields everywhere
                                if (blDTO.getCartDetails() != null && blDTO.getCartDetails().getTotalItems() > 0) {
                                    basketItemsAdapter.blDTO = blDTO;
                                    BasketListFragment.this.blDTO = blDTO;
                                } else {
                                    basketItemsAdapter.blDTO = new BasketListDTO();
                                    BasketListFragment.this.blDTO = new BasketListDTO();
                                }
                                basketItemsAdapter.notifyDataSetChanged();
                            }

                            @Override
                            public void onFailure(Call<BasketListDTO> call, Throwable t) {

                            }
                        });
                        basketItemsAdapter.removeItem(viewHolder.getAdapterPosition());
                    }
                };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(basketList);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable("blDTO", blDTO);
    }

    public class BasketItemsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        private BasketListDTO blDTO = new BasketListDTO();
        private LayoutInflater layoutInflater;
        private FragmentManager fragmentManager;
        private Context context;

        public BasketItemsAdapter(LayoutInflater layoutInflater, FragmentManager fragmentManager,
                                  Context context) {
            this.layoutInflater = layoutInflater;
            this.fragmentManager = fragmentManager;
            this.context = context;
        }

        public void removeItem(int location) {
            PreparedProduct pp = (PreparedProduct) blDTO.getCartDetails().getItems().keySet().toArray()[location];
            blDTO.getCartDetails().getItems().remove(pp);
            notifyDataSetChanged();

        }

        public void setBlDTO(BasketListDTO blDTO) {
            this.blDTO = blDTO;
            notifyDataSetChanged();
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = layoutInflater.inflate(R.layout.v_basket_item, parent, false);
            return new MyVH(view);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            PreparedProduct pp = (PreparedProduct) blDTO.getCartDetails().getItems().keySet().toArray()[position];
            Product product = pp.getProduct();
            ItemProps itemProps = blDTO.getCartDetails().getItems().get(pp);
            MyVH myVH = (MyVH) holder;
            myVH.price.setText(itemProps.getTotal().toString());
            myVH.title.setText(product.getName());
            myVH.item_count.setText(String.valueOf(itemProps.getQuantity()));
            tmpTag tmpTag = new tmpTag();
            tmpTag.prodid = product.getId();
            tmpTag.qty = itemProps.getQuantity();
            myVH.item_count.setTag(tmpTag);
            myVH.del.setTag(product.getId());
            String url = product.getImage();
            Glide.with(BasketListFragment.this)
                    .load(String.format("%s%s", ServiceCreator.API_ENDPOINT, url))
                    .into(myVH.image);
        }

        @Override
        public int getItemCount() {
            return blDTO.getCartDetails().getItems().size();
        }

        private class tmpTag {
            int qty;
            int prodid;
        }

        public class MyVH extends RecyclerView.ViewHolder {

            ImageView image;
            Button item_count;
            TextView title;
            TextView price;
            ImageButton del;
            private View.OnClickListener onDelClick = new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int id = ((Integer) view.getTag());
                    OrderService orderService = ServiceCreator.createService(OrderService.class
                            , context);
                    Call<BasketListDTO> call = orderService.delFromCart("" + id);
                    call.enqueue(new Callback<BasketListDTO>() {
                        @Override
                        public void onResponse(Call<BasketListDTO> call, Response<BasketListDTO> response) {
                            BasketListDTO blDTO = response.body();
                            if (blDTO.getCartDetails() != null && blDTO.getCartDetails().getTotalItems() > 0) {
                                BasketItemsAdapter.this.blDTO = blDTO;
                                BasketListFragment.this.blDTO = blDTO;
                            } else {
                                BasketItemsAdapter.this.blDTO = new BasketListDTO();
                                BasketListFragment.this.blDTO = new BasketListDTO();
                            }
                            BasketItemsAdapter.this.notifyDataSetChanged();
                        }

                        @Override
                        public void onFailure(Call<BasketListDTO> call, Throwable t) {

                        }
                    });


                }
            };
            private View.OnClickListener onAmountClick = new View.OnClickListener() {
                @Override
                public void onClick(final View view) {
                    tmpTag tmpTag = ((tmpTag) view.getTag());
                    AmountDialogFragment.show(fragmentManager, tmpTag.prodid, tmpTag.qty, new AmountDialogFragment.IAmountSelectedLstener() {
                        @Override
                        public void onSelect(int prodid, int paramInt, Context context) {
                            ((Button) view).setText(String.valueOf(paramInt));
                            OrderService orderService = ServiceCreator.createService(OrderService.class
                                    , context);
                            Call<BasketListDTO> call = orderService.updateCart("" + prodid, "" + paramInt);
                            call.enqueue(new Callback<BasketListDTO>() {
                                @Override
                                public void onResponse(Call<BasketListDTO> call, Response<BasketListDTO> response) {
                                    BasketListDTO blDTO = response.body();
                                    //reference to outer class
                                    Log.d(TAG, " blDto resp " + blDTO.getCartDetails().getTotalItems());
                                    BasketListFragment.this.blDTO = blDTO;
                                    BasketItemsAdapter.this.blDTO = blDTO;
                                    BasketItemsAdapter.this.notifyDataSetChanged();
                                }

                                @Override
                                public void onFailure(Call<BasketListDTO> call, Throwable t) {

                                }
                            });
                        }
                    });
                }
            };

            public MyVH(View itemView) {
                super(itemView);
                image = (ImageView) itemView.findViewById(R.id.item_image);
                item_count = (Button) itemView.findViewById(R.id.input_item_count);
                item_count.setOnClickListener(onAmountClick);
                del = (ImageButton) itemView.findViewById(R.id.input_item_delete);
                del.setOnClickListener(onDelClick);
                title = (TextView) itemView.findViewById(R.id.basket_item_title);
                price = (TextView) itemView.findViewById(R.id.item_price);
            }
        }
    }


}

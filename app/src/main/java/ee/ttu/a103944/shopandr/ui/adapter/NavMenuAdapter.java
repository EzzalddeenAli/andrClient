package ee.ttu.a103944.shopandr.ui.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import ee.ttu.a103944.shopandr.R;
import ee.ttu.a103944.shopandr.event.UserEvents;
import ee.ttu.a103944.shopandr.network.service.ServiceCreator;
import ee.ttu.a103944.shopandr.network.service.UserService;
import ee.ttu.a103944.shopandr.ui.activity.BasketListActivity;
import ee.ttu.a103944.shopandr.ui.activity.LoginActivity;
import ee.ttu.a103944.shopandr.ui.activity.MainActivity;
import ee.ttu.a103944.shopandr.ui.activity.OrdersActivity;
import ee.ttu.a103944.shopandr.ui.activity.RegisterActivity;
import ee.ttu.a103944.shopandr.utils.Constant;
import ee.ttu.a103944.shopandr.utils.LocaleHelper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class NavMenuAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final int VIEW_TYPE_MAIN = 0;
    public static final int VIEW_TYPE_PROFILE_LABEL = 1;
    public static final int VIEW_TYPE_AUTHORIZATION = 2;
    public static final int VIEW_TYPE_BASKET_LABEL = 5;
    public static final int VIEW_TYPE_BASKET_VALUE = 6;
    public static final int VIEW_TYPE_ABOUT_SHOP_LABEL = 8;
    public static final int VIEW_TYPE_ABOUT_SHOP_CONTACTS = 9;
    public static final int VIEW_TYPE_ABOUT_APP = 10;
    public static final int VIEW_TYPE_NICK = 3;
    public static final int VIEW_TYPE_ORDERS = 4;
    public static final int VIEW_TYPE_BASKET_ENTRY = 7;
    public static final int VIEW_TYPE_LOGOUT = 11;
    public static final int VIEW_TYPE_REGISTER = 12;
    public static final int VIEW_TYPE_LANGUAGE = 13;
    private Context context;
    private View.OnClickListener onHomeClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            ((MainActivity) context).closeDrawer();
        }
    };
    private View.OnClickListener onLoginClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            ((MainActivity) context).closeDrawer();
            LoginActivity.start(((MainActivity) context));
        }
    };
    private View.OnClickListener onBasketClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            ((MainActivity) context).closeDrawer();
            BasketListActivity.start(((MainActivity) context));
        }
    };
    private View.OnClickListener onRegisterClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            ((MainActivity) context).closeDrawer();
            RegisterActivity.start(((MainActivity) context));
        }
    };
    private View.OnClickListener onLogoutClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            ((MainActivity) context).closeDrawer();
            UserService orderService = ServiceCreator.createService(UserService.class
                    , (MainActivity) context);
            Call<Void> call = orderService.logout();
            call.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    EventBus.getDefault().post(new UserEvents.LoginEvent());//actually logout
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {

                }
            });
        }
    };
    private View.OnClickListener onOrdersClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            ((MainActivity) context).closeDrawer();
            OrdersActivity.start(((MainActivity) context));
        }
    };
    private LayoutInflater layoutInflater;
    private List<Integer> menuContents = new ArrayList<>();
    private String nick;
    private String totalAmount;
    private String totalPrice;
    public NavMenuAdapter(LayoutInflater layoutInflater, Context context) {
        this.layoutInflater = layoutInflater;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View convertView;
        switch (viewType) {
            default:
                return null;
            case VIEW_TYPE_MAIN:
                convertView = layoutInflater.inflate(R.layout.v_left_menu_generic_value, parent, false);
                convertView.setOnClickListener(onHomeClick);
                return new GenericHolder(convertView);
            case VIEW_TYPE_PROFILE_LABEL:
                convertView = layoutInflater.inflate(R.layout.v_left_menu_generic_label, parent, false);
                return new LabelHolder(convertView);
            case VIEW_TYPE_AUTHORIZATION:
                convertView = layoutInflater.inflate(R.layout.v_left_menu_generic_value, parent, false);
                convertView.setOnClickListener(onLoginClick);
                return new GenericHolder(convertView);
            case VIEW_TYPE_REGISTER:
                convertView = layoutInflater.inflate(R.layout.v_left_menu_generic_value, parent, false);
                convertView.setOnClickListener(onRegisterClick);
                return new GenericHolder(convertView);
            case VIEW_TYPE_NICK:
                convertView = layoutInflater.inflate(R.layout.v_left_menu_generic_value, parent, false);
                return new GenericHolder(convertView);
            case VIEW_TYPE_ORDERS:
                convertView = layoutInflater.inflate(R.layout.v_left_menu_generic_value, parent, false);
                convertView.setOnClickListener(onOrdersClick);
                return new GenericHolder(convertView);
            case VIEW_TYPE_LOGOUT:
                convertView = layoutInflater.inflate(R.layout.v_left_menu_generic_value, parent, false);
                convertView.setOnClickListener(onLogoutClick);
                return new GenericHolder(convertView);
            case VIEW_TYPE_BASKET_LABEL:
                convertView = layoutInflater.inflate(R.layout.v_left_menu_generic_label, parent, false);
                return new LabelHolder(convertView);
            case VIEW_TYPE_BASKET_VALUE:
                convertView = layoutInflater.inflate(R.layout.v_left_menu_generic_value, parent, false);
                return new GenericHolder(convertView);
            case VIEW_TYPE_BASKET_ENTRY:
                convertView = layoutInflater.inflate(R.layout.nav_item_basket, parent, false);
                convertView.setMinimumHeight(100);
                convertView.setOnClickListener(onBasketClick);
                return new NavBaskteVH(convertView);
            case VIEW_TYPE_ABOUT_SHOP_LABEL:
                convertView = layoutInflater.inflate(R.layout.v_left_menu_generic_label, parent, false);
                return new LabelHolder(convertView);
            case VIEW_TYPE_ABOUT_SHOP_CONTACTS:
                convertView = layoutInflater.inflate(R.layout.v_left_menu_generic_value, parent, false);
                return new GenericHolder(convertView);
            case VIEW_TYPE_ABOUT_APP:
                convertView = layoutInflater.inflate(R.layout.v_left_menu_generic_value, parent, false);
                return new GenericHolder(convertView);
            case VIEW_TYPE_LANGUAGE:
                convertView = layoutInflater.inflate(R.layout.nav_item_language, parent, false);
                return new NavLangVH(convertView);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int type = holder.getItemViewType();
        GenericHolder genericHolder;
        LabelHolder labelHolder;
        NavBaskteVH navBaskteVH;
        NavLangVH navLangVH;
        switch (type) {
            default:
                break;
            case VIEW_TYPE_MAIN:
                genericHolder = (GenericHolder) holder;
                genericHolder.labelTextView.setText(R.string.nav_item_main);
                break;
            case VIEW_TYPE_PROFILE_LABEL:
                labelHolder = (LabelHolder) holder;
                labelHolder.labelTextView.setText(R.string.nav_item_profile_label);
                break;
            case VIEW_TYPE_AUTHORIZATION:
                genericHolder = (GenericHolder) holder;
                genericHolder.labelTextView.setText(R.string.nav_item_authorization);
                break;
            case VIEW_TYPE_REGISTER:
                genericHolder = (GenericHolder) holder;
                genericHolder.labelTextView.setText(R.string.nav_item_register);
                break;
            case VIEW_TYPE_NICK:
                genericHolder = (GenericHolder) holder;
                genericHolder.labelTextView.setText(nick);
                break;
            case VIEW_TYPE_ORDERS:
                genericHolder = (GenericHolder) holder;
                genericHolder.labelTextView.setText(R.string.nav_item_orders);
                break;
            case VIEW_TYPE_LOGOUT:
                genericHolder = (GenericHolder) holder;
                genericHolder.labelTextView.setText(R.string.nav_item_logout);
                break;
            case VIEW_TYPE_BASKET_LABEL:
                labelHolder = (LabelHolder) holder;
                labelHolder.labelTextView.setText(R.string.nav_item_basket_label);
                break;
            case VIEW_TYPE_BASKET_VALUE:
                genericHolder = (GenericHolder) holder;
                genericHolder.labelTextView.setText(R.string.nav_item_basket_value);
                break;
            case VIEW_TYPE_BASKET_ENTRY:
                navBaskteVH = (NavBaskteVH) holder;
                Resources res = context.getResources();
                String st1 = String.format(res.getString(R.string.total_item), totalAmount);
                String st2 = String.format(res.getString(R.string.total_price), totalPrice);

                navBaskteVH.totAm.setText(st1);
                navBaskteVH.totPrice.setText(st2);
                break;
            case VIEW_TYPE_ABOUT_SHOP_LABEL:
                labelHolder = (LabelHolder) holder;
                labelHolder.labelTextView.setText(R.string.nav_item_about_shop_label);
                break;
            case VIEW_TYPE_ABOUT_SHOP_CONTACTS:
                genericHolder = (GenericHolder) holder;
                genericHolder.labelTextView.setText(R.string.nav_item_about_shop_contacts);
                break;
            case VIEW_TYPE_ABOUT_APP:
                genericHolder = (GenericHolder) holder;
                genericHolder.labelTextView.setText(R.string.nav_item_about_app);
                break;
            case VIEW_TYPE_LANGUAGE:
                navLangVH = (NavLangVH) holder;
                navLangVH.rus.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        LocaleHelper.setLocale(context, Constant.LANG_RU);
                        MainActivity mainActivity = ((MainActivity) context);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                            mainActivity.recreate();
                        } else {
                            mainActivity.finish();
                            mainActivity.startActivity(mainActivity.getIntent());
                        }

                    }
                });
                navLangVH.est.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        LocaleHelper.setLocale(context, Constant.LANG_ET);
                        MainActivity mainActivity = ((MainActivity) context);
                        mainActivity.finish();
                        mainActivity.startActivity(mainActivity.getIntent());

                    }
                });
                navLangVH.eng.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        LocaleHelper.setLocale(context, Constant.LANG_EN);
                        MainActivity mainActivity = ((MainActivity) context);
                        mainActivity.finish();
                        mainActivity.startActivity(mainActivity.getIntent());

                    }
                });
        }
    }

    @Override
    public int getItemViewType(int position) {
        int i = menuContents.get(position).intValue();
        ;
        return i;
    }

    @Override
    public int getItemCount() {
        int i = menuContents.size();
        return i;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public void setTotalAmount(String totalAmount, String totalPrice) {
        this.totalAmount = totalAmount;
        this.totalPrice = totalPrice;
    }

    public void setMenuContents(List<Integer> menuContents) {
        this.menuContents = menuContents;
    }

    private static class GenericHolder extends RecyclerView.ViewHolder {
        public TextView labelTextView;

        public GenericHolder(View itemView) {
            super(itemView);
            labelTextView = (TextView) itemView.findViewById(R.id.generic);
        }
    }

    private static class LabelHolder extends RecyclerView.ViewHolder {
        public TextView labelTextView;

        public LabelHolder(View itemView) {
            super(itemView);
            labelTextView = ((TextView) itemView.findViewById(R.id.label));
        }
    }

    private class NavLangVH extends RecyclerView.ViewHolder {
        public ImageView rus;
        public ImageView est;
        public ImageView eng;

        public NavLangVH(View itemView) {
            super(itemView);
            rus = (ImageView) itemView.findViewById(R.id.langrus);
            est = (ImageView) itemView.findViewById(R.id.langest);
            eng = (ImageView) itemView.findViewById(R.id.langeng);
        }
    }

    private class NavBaskteVH extends RecyclerView.ViewHolder {
        public TextView totAm;
        public TextView totPrice;

        public NavBaskteVH(View itemView) {
            super(itemView);
            totAm = (TextView) itemView.findViewById(R.id.nav_total_amount);
            totPrice = (TextView) itemView.findViewById(R.id.nav_total_sum);
        }
    }

}
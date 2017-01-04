package ee.ttu.a103944.shopandr.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.math.BigDecimal;
import java.util.LinkedHashSet;
import java.util.Set;


public class PreparedOrder implements Parcelable {
    public static final Creator<PreparedOrder> CREATOR = new Creator<PreparedOrder>() {
        @Override
        public PreparedOrder createFromParcel(Parcel in) {
            return new PreparedOrder(in);
        }

        @Override
        public PreparedOrder[] newArray(int size) {
            return new PreparedOrder[size];
        }
    };
    private Order order = new Order();
    private Set<PreparedOrderItem> prepOrderItem = new LinkedHashSet<>();
    private BigDecimal subtotal = new BigDecimal(0);

    public PreparedOrder() {
    }

    protected PreparedOrder(Parcel in) {
        order = in.readParcelable(Order.class.getClassLoader());
        String string = in.readString();
        Gson gson = new GsonBuilder().enableComplexMapKeySerialization().create();
        prepOrderItem = gson.fromJson(string, new TypeToken<Set<PreparedOrderItem>>() {
        }.getType());
        subtotal = new BigDecimal(in.readString());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeParcelable(order, i);
        Gson gson = new GsonBuilder().enableComplexMapKeySerialization().create();
        parcel.writeString(gson.toJson(prepOrderItem));
        parcel.writeString(subtotal.toString());
    }

    public Order getOrder() {
        return order;
    }

    public Set<PreparedOrderItem> getPrepOrderItem() {
        return prepOrderItem;
    }

    public BigDecimal getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
    }
}

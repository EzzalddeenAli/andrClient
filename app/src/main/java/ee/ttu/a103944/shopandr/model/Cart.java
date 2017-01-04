package ee.ttu.a103944.shopandr.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;


public class Cart implements Parcelable {

    public static final Creator<Cart> CREATOR = new Creator<Cart>() {
        @Override
        public Cart createFromParcel(Parcel in) {
            return new Cart(in);
        }

        @Override
        public Cart[] newArray(int size) {
            return new Cart[size];
        }
    };
    private BigDecimal totalPrice = new BigDecimal(0);
    private int totalItems;
    private Map<PreparedProduct, ItemProps> items = new HashMap<>();

    public Cart() {
    }

    protected Cart(Parcel in) {
        totalItems = in.readInt();
        totalPrice = new BigDecimal(in.readString());
        String string = in.readString();
        Gson gson = new GsonBuilder().enableComplexMapKeySerialization().create();
        items = gson.fromJson(string, new TypeToken<Map<PreparedProduct, ItemProps>>() {
        }.getType());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(totalItems);
        parcel.writeString(totalPrice.toString());
        Gson gson = new GsonBuilder().enableComplexMapKeySerialization().create();
        parcel.writeString(gson.toJson(items));
    }


    public int getTotalItems() {
        return totalItems;
    }

    public Map<PreparedProduct, ItemProps> getItems() {
        return items;
    }
}

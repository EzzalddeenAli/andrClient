package ee.ttu.a103944.shopandr.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;


public class Order implements Parcelable {

    public static final Creator<Order> CREATOR = new Creator<Order>() {
        @Override
        public Order createFromParcel(Parcel in) {
            return new Order(in);
        }

        @Override
        public Order[] newArray(int size) {
            return new Order[size];
        }
    };
    private int id;
    private Timestamp creationDate;
    private String first_name;
    private String last_name;
    private String phone;
    private String email;
    private String address;
    private User user;
    private Order_status order_status;
    private Set<OrderItem> orderItems = new HashSet<>();

    public Order() {
    }

    protected Order(Parcel in) {
        id = in.readInt();
        first_name = in.readString();
        last_name = in.readString();
        phone = in.readString();
        email = in.readString();
        address = in.readString();
        user = in.readParcelable(User.class.getClassLoader());
        order_status = in.readParcelable(Order_status.class.getClassLoader());
        creationDate = Timestamp.valueOf(in.readString());
        String string = in.readString();
        Gson gson = new GsonBuilder().enableComplexMapKeySerialization().create();
        orderItems = gson.fromJson(string, new TypeToken<Set<OrderItem>>() {
        }.getType());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(first_name);
        parcel.writeString(last_name);
        parcel.writeString(phone);
        parcel.writeString(email);
        parcel.writeString(address);
        parcel.writeParcelable(user, i);
        parcel.writeParcelable(order_status, i);
        parcel.writeString(creationDate.toString());
        Gson gson = new GsonBuilder().enableComplexMapKeySerialization().create();
        parcel.writeString(gson.toJson(orderItems));

    }

    public int getId() {
        return id;
    }

    public Timestamp getCreationDate() {
        return creationDate;
    }

    public String getEmail() {
        return email;
    }

    public Order_status getOrder_status() {
        return order_status;
    }


}

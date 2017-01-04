package ee.ttu.a103944.shopandr.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;


public class OrdersDTO implements Parcelable {
    public static final Creator<OrdersDTO> CREATOR = new Creator<OrdersDTO>() {
        @Override
        public OrdersDTO createFromParcel(Parcel in) {
            return new OrdersDTO(in);
        }

        @Override
        public OrdersDTO[] newArray(int size) {
            return new OrdersDTO[size];
        }
    };
    List<PreparedOrder> porders = new ArrayList<>();
    private CartInfo cart = new CartInfo();

    public OrdersDTO() {
    }

    protected OrdersDTO(Parcel in) {
        cart = in.readParcelable(CartInfo.class.getClassLoader());
        porders = in.createTypedArrayList(PreparedOrder.CREATOR);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeParcelable(cart, i);
        parcel.writeTypedList(porders);

    }

    public List<PreparedOrder> getOrders() {
        return porders;
    }

    public CartInfo getCart() {
        return cart;
    }
}

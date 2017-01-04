package ee.ttu.a103944.shopandr.model;

import android.os.Parcel;
import android.os.Parcelable;

public class OrderDTO implements Parcelable {

    public static final Creator<OrderDTO> CREATOR = new Creator<OrderDTO>() {
        @Override
        public OrderDTO createFromParcel(Parcel in) {
            return new OrderDTO(in);
        }

        @Override
        public OrderDTO[] newArray(int size) {
            return new OrderDTO[size];
        }
    };
    PreparedOrder porder = new PreparedOrder();
    private CartInfo cart = new CartInfo();

    public OrderDTO() {
    }

    protected OrderDTO(Parcel in) {
        porder = in.readParcelable(PreparedOrder.class.getClassLoader());
        cart = in.readParcelable(CartInfo.class.getClassLoader());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeParcelable(porder, i);
        parcel.writeParcelable(cart, i);
    }

    public PreparedOrder getPorder() {
        return porder;
    }

    public CartInfo getCart() {
        return cart;
    }
}

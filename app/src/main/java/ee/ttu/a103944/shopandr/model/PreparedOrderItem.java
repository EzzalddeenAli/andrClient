package ee.ttu.a103944.shopandr.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.math.BigDecimal;


public class PreparedOrderItem implements Parcelable {
    public static final Creator<PreparedOrderItem> CREATOR = new Creator<PreparedOrderItem>() {
        @Override
        public PreparedOrderItem createFromParcel(Parcel in) {
            return new PreparedOrderItem(in);
        }

        @Override
        public PreparedOrderItem[] newArray(int size) {
            return new PreparedOrderItem[size];
        }
    };
    private OrderItem orderItem;
    private String url;
    private BigDecimal totalPrice = new BigDecimal(0);

    protected PreparedOrderItem(Parcel in) {
        orderItem = in.readParcelable(OrderItem.class.getClassLoader());
        url = in.readString();
        totalPrice = new BigDecimal(in.readString());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeParcelable(orderItem, i);
        parcel.writeString(url);
        parcel.writeString(totalPrice.toString());
    }

    public OrderItem getOrderItem() {
        return orderItem;
    }

    public String getUrl() {
        return url;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }
}

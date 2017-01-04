package ee.ttu.a103944.shopandr.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.math.BigDecimal;


public class OrderItem implements Parcelable {

    public static final Creator<OrderItem> CREATOR = new Creator<OrderItem>() {
        @Override
        public OrderItem createFromParcel(Parcel in) {
            return new OrderItem(in);
        }

        @Override
        public OrderItem[] newArray(int size) {
            return new OrderItem[size];
        }
    };
    private BigDecimal price = new BigDecimal(0);
    private int qty;
    private Product product;
    private Order order;

    protected OrderItem(Parcel in) {
        qty = in.readInt();
        product = in.readParcelable(Product.class.getClassLoader());
        price = new BigDecimal(in.readString());
        order = in.readParcelable(Order.class.getClassLoader());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(qty);
        parcel.writeParcelable(product, i);
        parcel.writeString(price.toString());
        parcel.writeParcelable(order, i);
    }

    public BigDecimal getPrice() {
        return price;
    }

    public int getQty() {
        return qty;
    }

    public Product getProduct() {
        return product;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}

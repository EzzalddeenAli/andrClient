package ee.ttu.a103944.shopandr.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.math.BigDecimal;


public class CartInfo implements Parcelable {


    public static final Creator<CartInfo> CREATOR = new Creator<CartInfo>() {
        @Override
        public CartInfo createFromParcel(Parcel in) {
            return new CartInfo(in);
        }

        @Override
        public CartInfo[] newArray(int size) {
            return new CartInfo[size];
        }
    };
    @SerializedName("totalPrice")
    private BigDecimal totalPrice = new BigDecimal(0);
    @SerializedName("totalItems")
    private int totalItems;
    @SerializedName("rowPrice")
    private BigDecimal rowPrice = new BigDecimal(0);

    public CartInfo() {
    }

    protected CartInfo(Parcel in) {
        totalPrice = new BigDecimal(in.readString());
        rowPrice = new BigDecimal(in.readString());
        totalItems = in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int i) {
        dest.writeString(totalPrice.toString());
        dest.writeString(rowPrice.toString());
        dest.writeInt(totalItems);
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public int getTotalItems() {
        return totalItems;
    }

}

package ee.ttu.a103944.shopandr.model;

import android.os.Parcel;
import android.os.Parcelable;


public class Shop implements Parcelable {

    public static final Creator<Shop> CREATOR = new Creator<Shop>() {
        @Override
        public Shop createFromParcel(Parcel in) {
            return new Shop(in);
        }

        @Override
        public Shop[] newArray(int size) {
            return new Shop[size];
        }
    };
    private int id;
    private String shop_address;
    private String shop_name;

    protected Shop(Parcel in) {
        id = in.readInt();
        shop_address = in.readString();
        shop_name = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(shop_address);
        parcel.writeString(shop_name);
    }

    public String getShop_address() {
        return shop_address;
    }

    public String getShop_name() {
        return shop_name;
    }
}

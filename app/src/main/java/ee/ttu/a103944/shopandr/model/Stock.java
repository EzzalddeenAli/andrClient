package ee.ttu.a103944.shopandr.model;

import android.os.Parcel;
import android.os.Parcelable;


public class Stock implements Parcelable {

    public static final Creator<Stock> CREATOR = new Creator<Stock>() {
        @Override
        public Stock createFromParcel(Parcel in) {
            return new Stock(in);
        }

        @Override
        public Stock[] newArray(int size) {
            return new Stock[size];
        }
    };
    private int id;
    private int quantity;
    private Shipment_type shipment_type;
    private Shop shop;

    protected Stock(Parcel in) {
        id = in.readInt();
        quantity = in.readInt();
        shipment_type = in.readParcelable(Shipment_type.class.getClassLoader());
        shop = in.readParcelable(Shop.class.getClassLoader());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeInt(quantity);
        parcel.writeParcelable(shipment_type, i);
        parcel.writeParcelable(shop, i);
    }

    public int getQuantity() {
        return quantity;
    }

    public Shipment_type getShipment_type() {
        return shipment_type;
    }

    public Shop getShop() {
        return shop;
    }
}


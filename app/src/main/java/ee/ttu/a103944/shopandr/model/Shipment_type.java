package ee.ttu.a103944.shopandr.model;

import android.os.Parcel;
import android.os.Parcelable;


public class Shipment_type implements Parcelable {

    public static final Creator<Shipment_type> CREATOR = new Creator<Shipment_type>() {
        @Override
        public Shipment_type createFromParcel(Parcel in) {
            return new Shipment_type(in);
        }

        @Override
        public Shipment_type[] newArray(int size) {
            return new Shipment_type[size];
        }
    };
    private int id;
    private String ship_date;

    protected Shipment_type(Parcel in) {
        id = in.readInt();
        ship_date = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(ship_date);
    }

    public String getShip_date() {
        return ship_date;
    }
}

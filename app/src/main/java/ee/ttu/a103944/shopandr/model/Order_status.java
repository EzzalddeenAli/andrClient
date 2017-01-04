package ee.ttu.a103944.shopandr.model;

import android.os.Parcel;
import android.os.Parcelable;


public class Order_status implements Parcelable {
    public static final Creator<Order_status> CREATOR = new Creator<Order_status>() {
        @Override
        public Order_status createFromParcel(Parcel in) {
            return new Order_status(in);
        }

        @Override
        public Order_status[] newArray(int size) {
            return new Order_status[size];
        }
    };
    private int id;
    private String name;

    protected Order_status(Parcel in) {
        id = in.readInt();
        name = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(name);
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}

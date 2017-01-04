package ee.ttu.a103944.shopandr.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Filter_variant implements Parcelable {

    public static final Creator<Filter_variant> CREATOR = new Creator<Filter_variant>() {
        @Override
        public Filter_variant createFromParcel(Parcel in) {
            return new Filter_variant(in);
        }

        @Override
        public Filter_variant[] newArray(int size) {
            return new Filter_variant[size];
        }
    };
    private int id;
    private String value;
    private String param = "";
    private int orderby;

    protected Filter_variant(Parcel in) {
        id = in.readInt();
        value = in.readString();
        param = in.readString();
        orderby = in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(value);
        parcel.writeString(param);
        parcel.writeInt(orderby);
    }

    public int getId() {
        return id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}

package ee.ttu.a103944.shopandr.model;

import android.os.Parcel;
import android.os.Parcelable;


public class Variant implements Parcelable {

    public static final Creator<Variant> CREATOR = new Creator<Variant>() {
        @Override
        public Variant createFromParcel(Parcel in) {
            return new Variant(in);
        }

        @Override
        public Variant[] newArray(int size) {
            return new Variant[size];
        }
    };
    private Filter_variant filter_variant;
    private String url;
    private boolean current;

    protected Variant(Parcel in) {
        filter_variant = in.readParcelable(Filter_variant.class.getClassLoader());
        url = in.readString();
        current = in.readByte() != 0;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeParcelable(filter_variant, i);
        parcel.writeString(url);
        parcel.writeByte((byte) (current ? 1 : 0));
    }

    public Filter_variant getFilter_variant() {
        return filter_variant;
    }

    public String getUrl() {
        return url;
    }

    public boolean isCurrent() {
        return current;
    }

    public void setCurrent(boolean current) {
        this.current = current;
    }
}

package ee.ttu.a103944.shopandr.model;

import android.os.Parcel;
import android.os.Parcelable;


public class Page implements Parcelable {

    public static final Creator<Page> CREATOR = new Creator<Page>() {
        @Override
        public Page createFromParcel(Parcel in) {
            return new Page(in);
        }

        @Override
        public Page[] newArray(int size) {
            return new Page[size];
        }
    };
    private final String url;
    private final int index;
    private final boolean current;

    protected Page(Parcel in) {
        url = in.readString();
        index = in.readInt();
        current = in.readByte() != 0;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(url);
        parcel.writeInt(index);
        parcel.writeByte((byte) (current ? 1 : 0));
    }
}

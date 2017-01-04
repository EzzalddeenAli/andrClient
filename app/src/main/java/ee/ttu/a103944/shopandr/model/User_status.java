package ee.ttu.a103944.shopandr.model;

import android.os.Parcel;
import android.os.Parcelable;


public class User_status implements Parcelable {

    public static final Creator<User_status> CREATOR = new Creator<User_status>() {
        @Override
        public User_status createFromParcel(Parcel in) {
            return new User_status(in);
        }

        @Override
        public User_status[] newArray(int size) {
            return new User_status[size];
        }
    };
    private int id;
    private String name;

    protected User_status(Parcel in) {
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
}

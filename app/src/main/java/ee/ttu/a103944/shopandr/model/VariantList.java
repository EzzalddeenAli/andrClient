package ee.ttu.a103944.shopandr.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class VariantList implements Parcelable {
    public static final Creator<VariantList> CREATOR = new Creator<VariantList>() {
        @Override
        public VariantList createFromParcel(Parcel in) {
            return new VariantList(in);
        }

        @Override
        public VariantList[] newArray(int size) {
            return new VariantList[size];
        }
    };
    private final ArrayList<Variant> variants;
    private final boolean choosed;

    protected VariantList(Parcel in) {
        variants = in.createTypedArrayList(Variant.CREATOR);
        choosed = in.readByte() != 0;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeTypedList(variants);
        parcel.writeByte((byte) (choosed ? 1 : 0));
    }

    public ArrayList<Variant> getVariants() {
        return variants;
    }

    public boolean isChoosed() {
        return choosed;
    }
}

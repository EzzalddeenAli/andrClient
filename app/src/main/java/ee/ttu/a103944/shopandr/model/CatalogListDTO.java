package ee.ttu.a103944.shopandr.model;

import android.os.Parcel;
import android.os.Parcelable;


public class CatalogListDTO implements Parcelable {

    public static final Creator<CatalogListDTO> CREATOR = new Creator<CatalogListDTO>() {
        @Override
        public CatalogListDTO createFromParcel(Parcel in) {
            return new CatalogListDTO(in);
        }

        @Override
        public CatalogListDTO[] newArray(int size) {
            return new CatalogListDTO[size];
        }
    };
    private CartInfo cartInfo;
    private PrepCtlg prepCtlgs;

    protected CatalogListDTO(Parcel in) {
        cartInfo = in.readParcelable(CartInfo.class.getClassLoader());
        prepCtlgs = in.readParcelable(PrepCtlg.class.getClassLoader());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeParcelable(cartInfo, i);
        parcel.writeParcelable(prepCtlgs, i);
    }

    public PrepCtlg getPrepCtlgs() {
        return prepCtlgs;
    }
}



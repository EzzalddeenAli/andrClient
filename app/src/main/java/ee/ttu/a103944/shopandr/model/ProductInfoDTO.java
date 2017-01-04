package ee.ttu.a103944.shopandr.model;

import android.os.Parcel;
import android.os.Parcelable;


public class ProductInfoDTO implements Parcelable {
    public static final Creator<ProductInfoDTO> CREATOR = new Creator<ProductInfoDTO>() {
        @Override
        public ProductInfoDTO createFromParcel(Parcel in) {
            return new ProductInfoDTO(in);
        }

        @Override
        public ProductInfoDTO[] newArray(int size) {
            return new ProductInfoDTO[size];
        }
    };
    PrepCtlg prepCtlgs = new PrepCtlg();
    PreparedProduct pp = new PreparedProduct();
    CartInfo cart = new CartInfo();

    public ProductInfoDTO() {
    }

    protected ProductInfoDTO(Parcel in) {
        prepCtlgs = in.readParcelable(PrepCtlg.class.getClassLoader());
        pp = in.readParcelable(PreparedProduct.class.getClassLoader());
        cart = in.readParcelable(CartInfo.class.getClassLoader());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeParcelable(prepCtlgs, i);
        parcel.writeParcelable(pp, i);
        parcel.writeParcelable(cart, i);
    }

    public PreparedProduct getPp() {
        return pp;
    }
}

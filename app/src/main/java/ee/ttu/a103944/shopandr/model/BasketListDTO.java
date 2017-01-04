package ee.ttu.a103944.shopandr.model;

import android.os.Parcel;
import android.os.Parcelable;


public class BasketListDTO implements Parcelable {

    public static final Creator<BasketListDTO> CREATOR = new Creator<BasketListDTO>() {
        @Override
        public BasketListDTO createFromParcel(Parcel in) {
            return new BasketListDTO(in);
        }

        @Override
        public BasketListDTO[] newArray(int size) {
            return new BasketListDTO[size];
        }
    };
    private Cart cartDetails = new Cart();
    private CartInfo cart = new CartInfo();

    public BasketListDTO() {
    }

    protected BasketListDTO(Parcel in) {
        cartDetails = in.readParcelable(Cart.class.getClassLoader());
        cart = in.readParcelable(CartInfo.class.getClassLoader());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeParcelable(cartDetails, i);
        parcel.writeParcelable(cart, i);
    }

    public Cart getCartDetails() {
        return cartDetails;
    }

    public CartInfo getCart() {
        return cart;
    }
}

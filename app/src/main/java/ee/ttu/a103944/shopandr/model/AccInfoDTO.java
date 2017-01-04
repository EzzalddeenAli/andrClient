package ee.ttu.a103944.shopandr.model;

import android.os.Parcel;
import android.os.Parcelable;


public class AccInfoDTO implements Parcelable {

    public static final Creator<AccInfoDTO> CREATOR = new Creator<AccInfoDTO>() {
        @Override
        public AccInfoDTO createFromParcel(Parcel in) {
            return new AccInfoDTO(in);
        }

        @Override
        public AccInfoDTO[] newArray(int size) {
            return new AccInfoDTO[size];
        }
    };
    private Cart cartDetails = new Cart();
    private CartInfo cart = new CartInfo();
    private User user = new User();

    protected AccInfoDTO(Parcel in) {
        cartDetails = in.readParcelable(Cart.class.getClassLoader());
        cart = in.readParcelable(CartInfo.class.getClassLoader());
        user = in.readParcelable(User.class.getClassLoader());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeParcelable(cartDetails, i);
        parcel.writeParcelable(cart, i);
        parcel.writeParcelable(user, i);
    }

    public Cart getCartDetails() {
        return cartDetails;
    }

    public CartInfo getCart() {
        return cart;
    }

    public User getUser() {
        return user;
    }
}

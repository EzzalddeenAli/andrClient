package ee.ttu.a103944.shopandr.model;

import android.os.Parcel;
import android.os.Parcelable;

public class PreparedProduct implements Parcelable {
    public static final Creator<PreparedProduct> CREATOR = new Creator<PreparedProduct>() {
        @Override
        public PreparedProduct createFromParcel(Parcel in) {
            return new PreparedProduct(in);
        }

        @Override
        public PreparedProduct[] newArray(int size) {
            return new PreparedProduct[size];
        }
    };
    private Product product = new Product();
    private String url;

    public PreparedProduct() {
    }

    protected PreparedProduct(Parcel in) {
        String reconstitutedString = new String(in.marshall());
        product = in.readParcelable(Product.class.getClassLoader());
        url = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeParcelable(product, i);
        parcel.writeString(url);
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}

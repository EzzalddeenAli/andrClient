package ee.ttu.a103944.shopandr.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;


public class PreparedProductList implements Parcelable {

    public static final Creator<PreparedProductList> CREATOR = new Creator<PreparedProductList>() {
        @Override
        public PreparedProductList createFromParcel(Parcel in) {
            return new PreparedProductList(in);
        }

        @Override
        public PreparedProductList[] newArray(int size) {
            return new PreparedProductList[size];
        }
    };
    private ArrayList<PreparedProduct> pProdsForPage = new ArrayList<>();
    private Long totalProds;


    public PreparedProductList() {
    }

    protected PreparedProductList(Parcel in) {
        pProdsForPage = in.createTypedArrayList(PreparedProduct.CREATOR);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeTypedList(pProdsForPage);
    }

    public ArrayList<PreparedProduct> getpProdsForPage() {
        return pProdsForPage;
    }

    public void setpProdsForPage(ArrayList<PreparedProduct> pProdsForPage) {
        this.pProdsForPage = pProdsForPage;
    }

    public Long getTotalProds() {
        return totalProds;
    }

    public void setTotalProds(Long totalProds) {
        this.totalProds = totalProds;
    }
}

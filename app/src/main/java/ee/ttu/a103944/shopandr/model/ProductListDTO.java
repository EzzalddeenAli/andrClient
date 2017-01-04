package ee.ttu.a103944.shopandr.model;

import android.os.Parcel;
import android.os.Parcelable;


public class ProductListDTO implements Parcelable {

    public static final Creator<ProductListDTO> CREATOR = new Creator<ProductListDTO>() {
        @Override
        public ProductListDTO createFromParcel(Parcel in) {
            return new ProductListDTO(in);
        }

        @Override
        public ProductListDTO[] newArray(int size) {
            return new ProductListDTO[size];
        }
    };
    PrepCtlg prepCtlgs = new PrepCtlg();
    PreparedProductList ppList = new PreparedProductList();
    PagesInfo pages = new PagesInfo();
    CartInfo cartInfo;
    FilterBox filterBox = new FilterBox();


    public ProductListDTO() {
    }

    protected ProductListDTO(Parcel in) {
        prepCtlgs = in.readParcelable(PrepCtlg.class.getClassLoader());
        ppList = in.readParcelable(PreparedProductList.class.getClassLoader());
        pages = in.readParcelable(PagesInfo.class.getClassLoader());
        cartInfo = in.readParcelable(CartInfo.class.getClassLoader());
        filterBox = in.readParcelable(FilterBox.class.getClassLoader());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeParcelable(prepCtlgs, i);
        parcel.writeParcelable(ppList, i);
        parcel.writeParcelable(pages, i);
        parcel.writeParcelable(cartInfo, i);
        parcel.writeParcelable(filterBox, i);
    }

    public PrepCtlg getPrepCtlgs() {
        return prepCtlgs;
    }

    public void setPrepCtlgs(PrepCtlg prepCtlgs) {
        this.prepCtlgs = prepCtlgs;
    }

    public PreparedProductList getPpList() {
        return ppList;
    }

    public void setPpList(PreparedProductList ppList) {
        this.ppList = ppList;
    }

    public PagesInfo getPages() {
        return pages;
    }

    public void setPages(PagesInfo pages) {
        this.pages = pages;
    }

    public CartInfo getCartInfo() {
        return cartInfo;
    }

    public void setCartInfo(CartInfo cartInfo) {
        this.cartInfo = cartInfo;
    }

    public FilterBox getFilterBox() {
        return filterBox;
    }

    public void setFilterBox(FilterBox filterBox) {
        this.filterBox = filterBox;
    }
}

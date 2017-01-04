package ee.ttu.a103944.shopandr.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;


public class PrepCtlg implements Parcelable {

    public static final Creator<PrepCtlg> CREATOR = new Creator<PrepCtlg>() {
        @Override
        public PrepCtlg createFromParcel(Parcel in) {
            return new PrepCtlg(in);
        }

        @Override
        public PrepCtlg[] newArray(int size) {
            return new PrepCtlg[size];
        }
    };
    private List<Catalog> catalogs = new ArrayList<Catalog>();
    private String selected = "";

    public PrepCtlg() {
    }

    protected PrepCtlg(Parcel in) {
        catalogs = in.createTypedArrayList(Catalog.CREATOR);
        selected = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeTypedList(catalogs);
        parcel.writeString(selected);
    }

    public List<Catalog> getCatalogs() {
        return catalogs;
    }

    public void setCatalogs(List<Catalog> catalogs) {
        this.catalogs = catalogs;
    }

    public String getSelected() {
        return selected;
    }

    public void setSelected(String selected) {
        this.selected = selected;
    }
}

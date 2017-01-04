package ee.ttu.a103944.shopandr.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;


public class Catalog implements Parcelable {

    public static final Creator<Catalog> CREATOR = new Creator<Catalog>() {
        @Override
        public Catalog createFromParcel(Parcel in) {
            return new Catalog(in);
        }

        @Override
        public Catalog[] newArray(int size) {
            return new Catalog[size];
        }
    };
    private int id;
    private String title;
    private String urlname;
    private String image;
    private int catalog_level;
    private ArrayList<Catalog> childs;
    private Catalog parent;


    public Catalog() {
    }

    protected Catalog(Parcel in) {
        id = in.readInt();
        title = in.readString();
        urlname = in.readString();
        image = in.readString();
        catalog_level = in.readInt();
        childs = in.createTypedArrayList(Catalog.CREATOR);
        parent = in.readParcelable(Catalog.class.getClassLoader());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(title);
        parcel.writeString(urlname);
        parcel.writeString(image);
        parcel.writeInt(catalog_level);
        parcel.writeTypedList(childs);
        parcel.writeParcelable(parent, i);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrlname() {
        return urlname;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public ArrayList<Catalog> getChilds() {
        return childs;
    }

    public Catalog getParent() {
        return parent;
    }

    public void setParent(Catalog parent) {
        this.parent = parent;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}

package ee.ttu.a103944.shopandr.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;


public class PagesInfo implements Parcelable {

    public static final Creator<PagesInfo> CREATOR = new Creator<PagesInfo>() {
        @Override
        public PagesInfo createFromParcel(Parcel in) {
            return new PagesInfo(in);
        }

        @Override
        public PagesInfo[] newArray(int size) {
            return new PagesInfo[size];
        }
    };
    private ArrayList<Page> pages = new ArrayList<>();
    private int pageLinksSize;
    private boolean hasPrevious;
    private boolean hasFirst;
    private boolean hasCurrent;
    private boolean hasLast;
    private boolean hasNext;
    private String previous;
    private String first;
    private String current;
    private String last;
    private String next;


    public PagesInfo() {
    }

    protected PagesInfo(Parcel in) {
        pages = in.createTypedArrayList(Page.CREATOR);
        pageLinksSize = in.readInt();
        hasPrevious = in.readByte() != 0;
        hasFirst = in.readByte() != 0;
        hasCurrent = in.readByte() != 0;
        hasLast = in.readByte() != 0;
        hasNext = in.readByte() != 0;
        previous = in.readString();
        first = in.readString();
        current = in.readString();
        last = in.readString();
        next = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeTypedList(pages);
        parcel.writeInt(pageLinksSize);
        parcel.writeByte((byte) (hasPrevious ? 1 : 0));
        parcel.writeByte((byte) (hasFirst ? 1 : 0));
        parcel.writeByte((byte) (hasCurrent ? 1 : 0));
        parcel.writeByte((byte) (hasLast ? 1 : 0));
        parcel.writeByte((byte) (hasNext ? 1 : 0));
        parcel.writeString(previous);
        parcel.writeString(first);
        parcel.writeString(current);
        parcel.writeString(last);
        parcel.writeString(next);
    }

    public ArrayList<Page> getPages() {
        return pages;
    }

    public void setPages(ArrayList<Page> pages) {
        this.pages = pages;
    }

    public int getPageLinksSize() {
        return pageLinksSize;
    }

    public void setPageLinksSize(int pageLinksSize) {
        this.pageLinksSize = pageLinksSize;
    }

    public boolean isHasPrevious() {
        return hasPrevious;
    }

    public void setHasPrevious(boolean hasPrevious) {
        this.hasPrevious = hasPrevious;
    }

    public boolean isHasFirst() {
        return hasFirst;
    }

    public void setHasFirst(boolean hasFirst) {
        this.hasFirst = hasFirst;
    }

    public boolean isHasCurrent() {
        return hasCurrent;
    }

    public void setHasCurrent(boolean hasCurrent) {
        this.hasCurrent = hasCurrent;
    }

    public boolean isHasLast() {
        return hasLast;
    }

    public void setHasLast(boolean hasLast) {
        this.hasLast = hasLast;
    }

    public boolean isHasNext() {
        return hasNext;
    }

    public void setHasNext(boolean hasNext) {
        this.hasNext = hasNext;
    }

    public String getPrevious() {
        return previous;
    }

    public void setPrevious(String previous) {
        this.previous = previous;
    }

    public String getFirst() {
        return first;
    }

    public void setFirst(String first) {
        this.first = first;
    }

    public String getCurrent() {
        return current;
    }

    public void setCurrent(String current) {
        this.current = current;
    }

    public String getLast() {
        return last;
    }

    public void setLast(String last) {
        this.last = last;
    }

    public String getNext() {
        return next;
    }

    public void setNext(String next) {
        this.next = next;
    }
}

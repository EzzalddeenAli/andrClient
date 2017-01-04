package ee.ttu.a103944.shopandr.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.util.LinkedHashMap;
import java.util.Map;


public class FilterBox implements Parcelable {
    public static final Creator<FilterBox> CREATOR = new Creator<FilterBox>() {
        @Override
        public FilterBox createFromParcel(Parcel in) {
            return new FilterBox(in);
        }

        @Override
        public FilterBox[] newArray(int size) {
            return new FilterBox[size];
        }
    };
    private Map<Filter, VariantList> filters = new LinkedHashMap<>();

    public FilterBox() {
    }

    protected FilterBox(Parcel in) {
        String string = in.readString();
        Gson gson = new GsonBuilder().enableComplexMapKeySerialization().create();
        filters = gson.fromJson(string, new TypeToken<Map<Filter, VariantList>>() {
        }.getType());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        Gson gson = new GsonBuilder().enableComplexMapKeySerialization().create();
        parcel.writeString(gson.toJson(filters));
    }

    public Map<Filter, VariantList> getFilters() {
        return filters;
    }

}


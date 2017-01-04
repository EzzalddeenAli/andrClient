package ee.ttu.a103944.shopandr.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.math.BigDecimal;
import java.util.LinkedHashSet;
import java.util.Set;


public class Product implements Parcelable {

    public static final Creator<Product> CREATOR = new Creator<Product>() {
        @Override
        public Product createFromParcel(Parcel in) {
            return new Product(in);
        }

        @Override
        public Product[] newArray(int size) {
            return new Product[size];
        }
    };
    private int id;
    private String name;
    private BigDecimal price = new BigDecimal(0);
    private String description;
    private String image = "";
    private Catalog catalog;
    private String brand;
    private String resolution;
    private String screen;
    private String Resp_time;
    private String color;
    private String Battery_life;
    private String type;
    private Set<Stock> stocks = new LinkedHashSet<>();
    private Set<Detail> details = new LinkedHashSet<>();

    public Product() {
    }

    protected Product(Parcel in) {
        id = in.readInt();
        name = in.readString();
        price = new BigDecimal(in.readString());
        description = in.readString();
        image = in.readString();
        catalog = in.readParcelable(Catalog.class.getClassLoader());
        String string = in.readString();
        Gson gson = new GsonBuilder().enableComplexMapKeySerialization().create();
        stocks = gson.fromJson(string, new TypeToken<Set<Stock>>() {
        }.getType());
        string = in.readString();
        details = gson.fromJson(string, new TypeToken<Set<Detail>>() {
        }.getType());
        brand = in.readString();
        resolution = in.readString();
        screen = in.readString();
        Resp_time = in.readString();
        color = in.readString();
        Battery_life = in.readString();
        type = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(name);
        parcel.writeString(price.toString());
        parcel.writeString(description);
        parcel.writeString(image);
        parcel.writeParcelable(catalog, i);
        Gson gson = new GsonBuilder().enableComplexMapKeySerialization().create();
        parcel.writeString(gson.toJson(stocks));
        parcel.writeString(gson.toJson(details));
        parcel.writeString(brand);
        parcel.writeString(resolution);
        parcel.writeString(screen);
        parcel.writeString(Resp_time);
        parcel.writeString(color);
        parcel.writeString(Battery_life);
        parcel.writeString(type);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Catalog getCatalog() {
        return catalog;
    }

    public void setCatalog(Catalog catalog) {
        this.catalog = catalog;
    }

    public Set<Stock> getStocks() {
        return stocks;
    }

    public void setStocks(Set<Stock> stocks) {
        this.stocks = stocks;
    }

    public Set<Detail> getDetails() {
        return details;
    }

    public void setDetails(Set<Detail> details) {
        this.details = details;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getResolution() {
        return resolution;
    }

    public void setResolution(String resolution) {
        this.resolution = resolution;
    }

    public String getScreen() {
        return screen;
    }

    public void setScreen(String screen) {
        this.screen = screen;
    }

    public String getResp_time() {
        return Resp_time;
    }

    public void setResp_time(String resp_time) {
        Resp_time = resp_time;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getBattery_life() {
        return Battery_life;
    }

    public void setBattery_life(String battery_life) {
        Battery_life = battery_life;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}

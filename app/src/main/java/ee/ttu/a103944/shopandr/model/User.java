package ee.ttu.a103944.shopandr.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.sql.Timestamp;


public class User implements Parcelable {

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };
    private int id;
    private User_status user_status;
    private String nick;
    private String passwd;
    private String salt;
    private String email;
    private Timestamp reg_date;
    private Timestamp last_login;
    private String style;
    private boolean is_client = true;
    private boolean is_employee = false;

    public User() {
    }

    protected User(Parcel in) {
        id = in.readInt();
        user_status = in.readParcelable(User_status.class.getClassLoader());
        nick = in.readString();
        passwd = in.readString();
        salt = in.readString();
        email = in.readString();
        style = in.readString();
        is_client = in.readByte() != 0;
        is_employee = in.readByte() != 0;
        last_login = Timestamp.valueOf(in.readString());
        reg_date = Timestamp.valueOf(in.readString());

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeParcelable(user_status, i);
        parcel.writeString(nick);
        parcel.writeString(passwd);
        parcel.writeString(salt);
        parcel.writeString(email);
        parcel.writeString(style);
        parcel.writeByte((byte) (is_client ? 1 : 0));
        parcel.writeByte((byte) (is_employee ? 1 : 0));
        parcel.writeString(last_login == null ? "" : last_login.toString());
        parcel.writeString(reg_date == null ? "" : reg_date.toString());

    }

    public int getId() {
        return id;
    }

    public User_status getUser_status() {
        return user_status;
    }

    public String getNick() {
        return nick;
    }

    public String getPasswd() {
        return passwd;
    }

    public String getSalt() {
        return salt;
    }

    public String getEmail() {
        return email;
    }

    public Timestamp getReg_date() {
        return reg_date;
    }

    public Timestamp getLast_login() {
        return last_login;
    }

    public String getStyle() {
        return style;
    }

    public boolean is_client() {
        return is_client;
    }

    public boolean is_employee() {
        return is_employee;
    }
}

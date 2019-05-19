package kr.ac.sangmyung.compeng.sampleparcelable;

import android.os.Parcel;
import android.os.Parcelable;

public class SimpleData implements Parcelable {
    private int number;
    private String message;

    public SimpleData(int number, String message) {
        this.number = number;
        this.message = message;
    }

    protected SimpleData(Parcel in) {
        number = in.readInt();
        message = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(number);
        dest.writeString(message);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<SimpleData> CREATOR = new Creator<SimpleData>() {
        @Override
        public SimpleData createFromParcel(Parcel in) {
            return new SimpleData(in);
        }

        @Override
        public SimpleData[] newArray(int size) {
            return new SimpleData[size];
        }
    };

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}


package kr.ac.sangmyung.compeng.doitmission_06;

import android.os.Parcel;
import android.os.Parcelable;

public class SimpleData implements Parcelable {
    private String message;
    String from;

    public SimpleData(String from, String message) {
        this.from = from;
        this.message = message;
    }

    protected SimpleData(Parcel in) {
        message = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
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

    public String getMessage() {
        return message;
    }

    public String getFrom() { return from; }
}


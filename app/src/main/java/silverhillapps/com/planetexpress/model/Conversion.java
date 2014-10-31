package silverhillapps.com.planetexpress.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Entity class that represent a single unit conversion
 * @author salva
 *
 */
public class Conversion implements Parcelable{

    @SerializedName("from")private String fromUnitCode;
    @SerializedName("to")private String toUnitCode;
    private String conversion;


    public Conversion() {

    }

    /**
     * Constructor used by the CREATOR
     *
     * @param source
     */
    public Conversion(Parcel source) {
        this.fromUnitCode = source.readString();
        this.toUnitCode = source.readString();
        this.conversion = source.readString();
    }


    // Getters and Setters

    public String getFromUnitCode() {
        return fromUnitCode;
    }

    public void setFromUnitCode(String fromUnitCode) {
        this.fromUnitCode = fromUnitCode;
    }

    public String getToUnitCode() {
        return toUnitCode;
    }

    public void setToUnitCode(String toUnitCode) {
        this.toUnitCode = toUnitCode;
    }

    public String getConversion() {
        return conversion;
    }

    public void setConversion(String conversion) {
        this.conversion = conversion;
    }


    //Parcelable methods

    @Override
    public int describeContents() {
        // TODO Auto-generated method stub
        return 0;
    }


    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(fromUnitCode);
        dest.writeString(toUnitCode);
        dest.writeString(conversion);
    }

    /**
     * This is the creator of the parcelable
     */
    public static final Parcelable.Creator<Conversion> CREATOR = new Creator<Conversion>() {

        public Conversion createFromParcel(Parcel source) {

            return new Conversion(source);
        }

        @Override
        public Conversion[] newArray(int size) {
            return new Conversion[size];
        }


    };
}

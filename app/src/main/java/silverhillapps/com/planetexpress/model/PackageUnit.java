package silverhillapps.com.planetexpress.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Entity class that represent a single package
 * @author salva
 *
 */

public class PackageUnit implements Parcelable {

    @SerializedName("id")private String idPackage;
    @SerializedName("destination")private String destinationCode;
    private String weight;
    @SerializedName("measure")private String measureUnit;

    public PackageUnit() {

    }

    /**
     * Constructor used by the CREATOR
     *
     * @param source
     */
    public PackageUnit(Parcel source) {
        this.idPackage = source.readString();
        this.destinationCode = source.readString();
        this.weight = source.readString();
        this.measureUnit = source.readString();
    }


    // Getters and Setters

    public String getIdPackage() {
        return idPackage;
    }

    public void setIdPackage(String idPackage) {
        this.idPackage = idPackage;
    }

    public String getDestinationCode() {
        return destinationCode;
    }

    public void setDestinationCode(String destinationCode) {
        this.destinationCode = destinationCode;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getMeasureUnit() {
        return measureUnit;
    }

    public void setMeasureUnit(String measureUnit) {
        this.measureUnit = measureUnit;
    }


    //Parcelable methods

    @Override
    public int describeContents() {
        // TODO Auto-generated method stub
        return 0;
    }


    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(idPackage);
        dest.writeString(destinationCode);
        dest.writeString(weight);
        dest.writeString(measureUnit);
    }

    /**
     * This is the creator of the parcelable
     */
    public static final Parcelable.Creator<PackageUnit> CREATOR = new Creator<PackageUnit>() {

        public PackageUnit createFromParcel(Parcel source) {

            return new PackageUnit(source);
        }

        @Override
        public PackageUnit[] newArray(int size) {
            return new PackageUnit[size];
        }


    };
}

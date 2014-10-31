package silverhillapps.com.planetexpress.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

import silverhillapps.com.planetexpress.conf.Conf;
import silverhillapps.com.planetexpress.utils.DigitUtils;

/**
 * Entity class that represent a country aggregated result
 * @author salva
 *
 */
public class CountryResult implements Parcelable{

    private String countryCode;
    private int numberAirplanes;
    private double totalWeight; //Expressed in default unit
    private List<PackageUnit> packageUnitList;

    public CountryResult() {
        this.totalWeight = 0;
        this.numberAirplanes = 0;
        this.packageUnitList = new ArrayList<PackageUnit>();
    }

    /**
     * Constructor used by the CREATOR
     *
     * @param source
     */
    public CountryResult(Parcel source) {
        this.countryCode = source.readString();
        this.numberAirplanes = source.readInt();
        this.totalWeight = source.readDouble();
        this.packageUnitList = new ArrayList<PackageUnit>();
        source.readTypedList(this.packageUnitList, PackageUnit.CREATOR);

    }


    // Getters and Setters

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public int getNumberAirplanes() {
        return numberAirplanes;
    }

    public void setNumberAirplanes(int numberAirplanes) {
        this.numberAirplanes = numberAirplanes;
    }

    public double getTotalWeight() {
        return totalWeight;
    }

    public void setTotalWeight(double totalWeight) {
        this.totalWeight = totalWeight;
    }

    public List<PackageUnit> getPackageUnitList() {
        return packageUnitList;
    }

    public void setPackageUnitList(List<PackageUnit> packageUnitList) {
        this.packageUnitList = packageUnitList;
    }


    //Parcelable methods

    @Override
    public int describeContents() {
        // TODO Auto-generated method stub
        return 0;
    }


    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(countryCode);
        dest.writeInt(numberAirplanes);
        dest.writeDouble(totalWeight);
        dest.writeTypedList(packageUnitList);
    }

    /**
     * This is the creator of the parcelable
     */
    public static final Parcelable.Creator<CountryResult> CREATOR = new Creator<CountryResult>() {

        public CountryResult createFromParcel(Parcel source) {

            return new CountryResult(source);
        }

        @Override
        public CountryResult[] newArray(int size) {
            return new CountryResult[size];
        }


    };

    public void includePackageUnit(PackageUnit p) {

        if(DigitUtils.isDoubleNumber(p.getWeight())){
            this.totalWeight+=DigitUtils.getDoubleFromString(p.getWeight());
        }

        this.numberAirplanes = (int) Math.ceil(this.totalWeight / Conf.AirplaneInfoConf.MAXIMUM_CARGO_AIRPLANE);

        this.packageUnitList.add(p);
        this.countryCode = p.getDestinationCode();

    }

    @Override
    public String toString() {
        return countryCode.toUpperCase();
    }
}

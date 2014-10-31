package silverhillapps.com.planetexpress.manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import silverhillapps.com.planetexpress.conf.Conf;
import silverhillapps.com.planetexpress.model.Conversion;
import silverhillapps.com.planetexpress.model.PackageUnit;
import silverhillapps.com.planetexpress.utils.DigitUtils;

/**
 * This class is in charge of the conversion loading, and conversion adaptation as well.
 */

public class ConversionManager {

    private Map<String, Double> conversionsMap; // final map with the conversions calculated according to the default unit.
    private List<Conversion> conversionList; // input data retrieved from the model.
    private List<Conversion> incompleteConversionList; // list used for calculation purposes. It stores the conversions not used in the first match.
    private List<String> codesCalculated; // Units calculated in the process
    private List<String> codesNotCalculated; // Units that left to be calculated

    @Inject
    public ConversionManager(){
        this.conversionList = new ArrayList<Conversion>();
        this.conversionsMap = new HashMap<String, Double>();
        this.incompleteConversionList = new ArrayList<Conversion>();
        this.codesCalculated = new ArrayList<String>();
        this.codesNotCalculated = new ArrayList<String>();
    }

    /**
     * This method calculate the essential matching between the conversions available from the model and the default
     * measure unit. Also obtains the units that are not possible to calculate by the essential matching process, for
     * posterior evaluation.
     */
    private void calculateConversionsMapToDefault(){
        double number = 0;
        if(conversionList!=null){
            if(conversionList.size() > 0){
                for(Conversion c:conversionList){


                    if(c.getToUnitCode().compareToIgnoreCase(Conf.ConversionConf.DEFAULT_CONVERSION_CODE) == 0){//Simplest case
                        insertInMap(c.getFromUnitCode(), c.getConversion(), false);
                        this.codesCalculated.add(c.getFromUnitCode().toLowerCase());
                        if(codesNotCalculated.contains(c.getFromUnitCode().toLowerCase())){codesNotCalculated.remove(c.getFromUnitCode().toLowerCase());}
                    }

                    else if(c.getFromUnitCode().compareToIgnoreCase(Conf.ConversionConf.DEFAULT_CONVERSION_CODE) == 0) {//inverse case
                        insertInMap(c.getToUnitCode(), c.getConversion(), true);
                        this.codesCalculated.add(c.getToUnitCode().toLowerCase());
                        if(codesNotCalculated.contains(c.getToUnitCode().toLowerCase())){codesNotCalculated.remove(c.getToUnitCode().toLowerCase());}
                    }
                    else{ // It is not possible to calculate the conversion to default unit directly, so we annotate it for posterior calculation.

                        if(!codesCalculated.contains(c.getToUnitCode().toLowerCase())){
                            this.codesNotCalculated.add(c.getToUnitCode().toLowerCase());
                        }
                        if(!codesCalculated.contains(c.getFromUnitCode().toLowerCase())) {
                            this.codesNotCalculated.add(c.getFromUnitCode().toLowerCase());
                        }
                        incompleteConversionList.add(c);
                    }
                }
                if(incompleteConversionList.size()>0 && this.codesNotCalculated.size()>0){
                    conversionCalculation(); // After all the essential knowledge was extracted, we start the refinement algorithm for the units still pending.
                }

            }
        }
    }

    /**
     * This method calculates the units conversion that still left to be obtained from the default method.
     */
    private void conversionCalculation() {

        boolean lastStepUpdated = true;
        double value = 0;
        while(codesNotCalculated.size()>0 && lastStepUpdated){ // if in the last step we didn't get any change, we cannot continue because we won't get anything new in a new iteration.
            lastStepUpdated = false;
            for(Conversion c: incompleteConversionList){
                if(this.conversionsMap.containsKey(c.getFromUnitCode().toLowerCase())){ // Inverse case
                    if(this.codesNotCalculated.contains(c.getToUnitCode().toLowerCase())){
                        value = this.conversionsMap.get(c.getFromUnitCode().toLowerCase()) / DigitUtils.getDoubleFromString(c.getConversion());
                        insertInMap(c.getToUnitCode().toLowerCase(), value, false);
                        lastStepUpdated = true;
                        this.codesNotCalculated.remove(c.getToUnitCode().toLowerCase());
                        this.codesCalculated.add(c.getToUnitCode().toLowerCase());
                    }
                }

                if(this.conversionsMap.containsKey(c.getToUnitCode().toLowerCase())){ // Direct case
                    if(this.codesNotCalculated.contains(c.getFromUnitCode().toLowerCase())){
                        value = this.conversionsMap.get(c.getToUnitCode().toLowerCase()) * DigitUtils.getDoubleFromString(c.getConversion());
                        insertInMap(c.getFromUnitCode().toLowerCase(), value, false);
                        lastStepUpdated = true;
                        this.codesNotCalculated.remove(c.getFromUnitCode().toLowerCase());
                        this.codesCalculated.add(c.getFromUnitCode().toLowerCase());
                    }
                }
            }
        }

    }

    /**
     * Method for adding a new conversion into the map.
     * @param unit
     * @param value
     * @param inverse
     */
    private void insertInMap(String unit, String value, boolean inverse) {
        double number;
        if(!conversionsMap.containsKey(unit.toLowerCase())){
            if(DigitUtils.isDoubleNumber(value)){
                number = DigitUtils.getDoubleFromString(value);
                if(inverse){
                    number = 1/number;
                }
                conversionsMap.put(unit.toLowerCase(), DigitUtils.truncateDecima(number,3));
            }
        }
    }

    /**
     * Method for adding a new conversion into the map.
     * @param unit
     * @param value
     * @param inverse
     */
    private void insertInMap(String unit, double value, boolean inverse) {
        double number;
        if(!conversionsMap.containsKey(unit.toLowerCase())){
            number = value;
                if(inverse){
                    number = 1/number;
                }
                conversionsMap.put(unit.toLowerCase(), DigitUtils.truncateDecima(number,3));
            }

    }

    /**
     * When the new conversion list is available, we start the calculation of all the conversions to the reference default unit.
     * @param conversionList
     */
    public void setConversionList(List<Conversion> conversionList){
        this.conversionList = conversionList;
        calculateConversionsMapToDefault();
    }

    public boolean isEmpty(){
        return conversionsMap.size()<=0;
    }

    public int getSize() {
        return conversionsMap.size();
    }

    /**
     * This method returns a conversion between the unit and the default unit.
     * @param unit
     * @return
     */
    public double getConversionToDefault(String unit){
        double conversion = -1;
        if(Conf.ConversionConf.DEFAULT_CONVERSION_CODE.compareToIgnoreCase(unit) == 0){
            conversion = 1;
        }else
        if(conversionsMap.containsKey(unit.toLowerCase())){
            conversion = conversionsMap.get(unit.toLowerCase());
        }
        return conversion;
    }

    /**
     * This method updates the value of a @PackageUnit to adequate it to the default measurement unit.
     * @param p
     * @return
     */
    public PackageUnit correctUnitsPackage(PackageUnit p ){

        double conversion = getConversionToDefault(p.getMeasureUnit());
        double newWeight = -1;
        if(DigitUtils.isDoubleNumber(p.getWeight())){
            newWeight = DigitUtils.getDoubleFromString(p.getWeight());
            newWeight *= conversion;
            p.setWeight(String.valueOf(newWeight));
            p.setMeasureUnit(Conf.ConversionConf.DEFAULT_CONVERSION_CODE);
            return p;
        }
        return null;

    }

    /**
     * This method cleans all the data calculated in the manager.
     */
    public void clean(){
        this.conversionsMap = new HashMap<String, Double>();
        this.conversionList = new ArrayList<Conversion>();
        this.incompleteConversionList = new ArrayList<Conversion>();
    }
}

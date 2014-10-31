package silverhillapps.com.planetexpress.conf;

/**
 * Constants class
 */
public class Constants {

    /**
     * Class for storing the rest api constants
     */
    public static final class RestApiConstants{
        public static final String PACKAGING_API_BASE_URL = "http://iphone.apps.taptapnetworks.com/install/codetest/";
        public static final String PACKAGING_LIST_METHOD = "packages.json";
        public static final String CONVERSION_LIST_METHOD = "conversions.json";

    }


    /**
     * Class for storing the passing parameters ids contacts
     */
    public static final class PassingParamConstants{

        public static final int REST_RETURN_LIST_PACKAGES = 1;
        public static final int REST_RETURN_LIST_CONVERSION = 2;

        public static final java.lang.String ACTIVITY_PASS_PACKAGE = "activity_pass_contact";

    }

    /**
     * Class for storing the file repository constants
     */
    public static final class FileRepositoryConstants{

        public static final String PACKAGING_PACKAGES_URL = "packages.json";
        public static final String PACKAGING_CONVERSION_URL = "conversions_test.json";

    }

}

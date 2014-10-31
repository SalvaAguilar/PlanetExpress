package silverhillapps.com.planetexpress;

import android.app.Instrumentation;
import android.test.ActivityInstrumentationTestCase2;
import android.test.suitebuilder.annotation.MediumTest;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 * Very simple test case for checking that is loading data in start, meaning that the manager injection was made correctly.
 */
public class CountriesActivityTest extends ActivityInstrumentationTestCase2<CountriesActivity> {


    private Instrumentation mInstrumentation;
    private CountriesActivity mActivity;


    public CountriesActivityTest(Class<CountriesActivity> activityClass) {
        super(activityClass);
    }

    public CountriesActivityTest() {
        super(CountriesActivity.class);
    }

    protected void setUp() throws Exception {
        super.setUp();
        mInstrumentation = getInstrumentation();

        mActivity = getActivity(); // get a references to the app under test

    }


    @MediumTest
    public void testLoadingOnFirstRun() {

        assertTrue(mActivity.isLoading() == true);
    }
}
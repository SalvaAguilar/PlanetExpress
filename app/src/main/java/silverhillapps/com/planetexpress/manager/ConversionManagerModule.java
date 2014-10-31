package silverhillapps.com.planetexpress.manager;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;


/**
 * This class defines the module for ConversionManager class injection
 */

@Module(complete = false,
        injects = LoaderManagerFacade.class

)
public class ConversionManagerModule {

    @Provides @Singleton ConversionManager provideConversionManager() {
        return new ConversionManager();
    }
}
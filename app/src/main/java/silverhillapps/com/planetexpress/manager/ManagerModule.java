package silverhillapps.com.planetexpress.manager;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import silverhillapps.com.planetexpress.CountriesActivity;
import silverhillapps.com.planetexpress.loader.PackagingRepositoryModule;

/**
 * This class defines the module for Manager class injection
 */

@Module(
        injects = CountriesActivity.class,
        includes = {
                PackagingRepositoryModule.class
        }
)
public class ManagerModule {

    @Provides @Singleton ManagerInterface provideManager() {
        return new LoaderManagerFacade();
    }
}
package silverhillapps.com.planetexpress.loader;

import com.google.gson.Gson;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import silverhillapps.com.planetexpress.file.FilePackagingRepository;
import silverhillapps.com.planetexpress.manager.LoaderManagerFacade;
import silverhillapps.com.planetexpress.rest.RestPackagingRepository;

/**
 * Dagger Module for Manager injection
 */

@Module(injects = {LoaderManagerFacade.class,RestPackagingRepository.class, FilePackagingRepository.class}, complete = false, library = true)
public class PackagingRepositoryModule {

    @Provides @Singleton PackagingRepository providePackaging() {
        return new RestPackagingRepository();
    }

    @Provides
    @Singleton
    Gson provideGson() {
        return new Gson();
    }


}
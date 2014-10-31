package silverhillapps.com.planetexpress.loader;

import silverhillapps.com.planetexpress.manager.ManagerInterface;

/**
 * This abstract class determines the structure of a generic loader
 */
public abstract class PackagingRepository {

    public abstract void getPackageList(RepositoryReceiver receiver, ManagerInterface manager);
    public abstract void getConversionList(RepositoryReceiver receiver, ManagerInterface manager);
}

package silverhillapps.com.planetexpress.manager;

import java.util.List;

import silverhillapps.com.planetexpress.loader.RepositoryReceiver;

/**
 * This interface defines the skeleton for a generic manager
 */
public interface ManagerInterface {

    public List<Package> getCountryResultList(RepositoryReceiver receiver);
    public void getResults(Object result, int code, RepositoryReceiver receiver);
    public void clean();
}
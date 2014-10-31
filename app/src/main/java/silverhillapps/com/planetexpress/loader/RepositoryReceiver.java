package silverhillapps.com.planetexpress.loader;

/**
 * Interface for receiving the asynchronous results from the repository
 */
public interface RepositoryReceiver {

    public void getResults(Object result, int code);

    public void getError(int code);
}
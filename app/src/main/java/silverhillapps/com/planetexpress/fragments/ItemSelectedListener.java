package silverhillapps.com.planetexpress.fragments;


/**
 * This interface models the communication between the main activity and the  fragment
 * @author salva
 *
 */
public interface ItemSelectedListener {

    /**
     * This method is used to notify the parent which element was clicked.
     * @param contentId
     */
    public void onItemSelected(String contentId);

}
package com.liferay.portal.kernel.messaging.mgmt;


/**
 * <a href="DestinationMBean.java.html"><b><i>View Source</i></b></a>
 *
 * @author Michael C. Han
 */
public interface DestinationManagerMBean {

    /**
     * Obtain the total number of listeners at a given destination
     * @return number of listeners
     */
    public int getNumListeners();


	
}

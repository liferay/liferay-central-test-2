package com.liferay.portlet;

/**
 * <a href="InstrumentedInvokerPortletManagerMBean.java.html"><b><i>View
 * Source</i></b></a>
 *
 * @author Michael C. Han
 */
public interface InstrumentedInvokerPortletManagerMBean {
	boolean isMonitorActionActive();

	boolean isMonitorEventActive();

	boolean isMonitorRenderActive();

	boolean isMonitorResourceActive();

	void setMonitorActionActive(boolean monitorActionActive);

	void setMonitorEventActive(boolean monitorEventActive);

	void setMonitorRenderActive(boolean monitorRenderActive);

	void setMonitorResourceActive(boolean monitorResourceActive);
}

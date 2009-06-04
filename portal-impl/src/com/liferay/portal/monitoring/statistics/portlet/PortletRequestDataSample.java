package com.liferay.portal.monitoring.statistics.portlet;

import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.monitoring.statistics.BaseDataSample;
import com.liferay.portal.monitoring.MonitorNames;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.PortletResponseImpl;

/**
 * <a href="PortletRequestSample.java.html"><b><i>View Source</i></b></a>
 *
 * The instrumentation data gathered for one portlet request
 *
 * @author Karthik Sudarshan
 * @author Michael C. Han
 */
public class PortletRequestDataSample extends BaseDataSample {

	public PortletRequestDataSample(
		PortletRequestType requestType,
		LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse) {

		super();

		com.liferay.portal.model.Portlet portlet =
			((PortletResponseImpl)liferayPortletResponse).getPortlet();

		_user = liferayPortletRequest.getRemoteUser();
		_companyId = PortalUtil.getCompanyId(liferayPortletRequest);
		_namespace = MonitorNames.PORTLET_CONTAINER; 
		_name = portlet.getPortletName();
		_displayName = portlet.getDisplayName();
		_portletId = portlet.getPortletId();
		_requestType = requestType;
	}

	public String getDisplayName() {
		return _displayName;
	}

	public String getPortletId() {
		return _portletId;
	}

	public PortletRequestType getRequestType() {
		return _requestType;
	}

	public String toString() {
		return "PortletRequestDataSample{" +
			   super.toString() +
			   ", _portletId='" + _portletId + '\'' +
			   ", _requestType=" + _requestType +
			   '}';
	}

	private String _portletId;
	private PortletRequestType _requestType;
	private String _displayName;
}

package com.liferay.portal.apache.bridges.struts;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.portals.bridges.struts.StrutsPortletURL;

import com.liferay.portlet.PortletURLImplWrapper;
import com.liferay.portlet.RenderResponseImpl;
import com.liferay.util.Http;

public class LiferayStrutsPortletURLImpl extends PortletURLImplWrapper {

	public LiferayStrutsPortletURLImpl(RenderResponseImpl res, boolean action) {
		super(res, action);
	}

	public void setParameter(String name, String value) {
		super.setParameter(name, value);

		// Add parameters from the query string
		// because bridges passes these through
		// instead of setting them on the portlet
		// url
		String decodedValue = Http.decodeURL(value);

		try {
			if (name.equals(StrutsPortletURL.PAGE)) {
				String[] urlComponents = decodedValue.split("\\?", 2);

				if (urlComponents.length != 2) {
					return;
				}

				String[] nameValue = urlComponents[1].split("\\&");

				for (int i = 0; i < nameValue.length; i++) {
					String[] nameValuePair = nameValue[i].split("\\=", 2);

					if (nameValuePair.length == 2) {
						super.setParameter(nameValuePair[0], nameValuePair[1]);
					} else if (nameValuePair.length == 1) {
						super.setParameter(nameValuePair[0], "true");
					}
				}
			}
		} catch (Throwable t) {
			_log.error("Could not parse struts page query string: " + value, t);
		}
	}

	private static Log _log = LogFactory
			.getLog(LiferayStrutsPortletURLImpl.class);
}

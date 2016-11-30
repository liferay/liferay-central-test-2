package com.liferay.adaptive.media.image.jaxrs.internal.provider;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.PortalUtil;

import javax.servlet.http.HttpServletRequest;

import javax.ws.rs.ext.Provider;

import org.apache.cxf.jaxrs.ext.ContextProvider;
import org.apache.cxf.message.Message;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Carlos Sierra Andrés
 */
@Component(immediate = true, service = CompanyContextProvider.class)
@Provider
public class CompanyContextProvider implements ContextProvider<Company> {

	@Override
	public Company createContext(Message message) {
		HttpServletRequest request =
			(HttpServletRequest)message.getContextualProperty("HTTP.REQUEST");

		try {
			if (request.getHeader("Test") != null) {
				return _companyLocalService.getCompanyById(
					TestPropsValues.getCompanyId());
			}

			return PortalUtil.getCompany(request);
		}
		catch (PortalException pe) {
			if (_log.isWarnEnabled()) {
				_log.warn(pe);
			}

			return null;
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CompanyContextProvider.class);

	@Reference private CompanyLocalService _companyLocalService;

}
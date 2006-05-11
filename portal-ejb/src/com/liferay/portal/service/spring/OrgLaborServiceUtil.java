/**
 * Copyright (c) 2000-2006 Liferay, LLC. All rights reserved.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.liferay.portal.service.spring;

/**
 * <a href="OrgLaborServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class OrgLaborServiceUtil {
	public static com.liferay.portal.model.OrgLabor addOrgLabor(
		java.lang.String organizationId, java.lang.String typeId, int sunOpen,
		int sunClose, int monOpen, int monClose, int tueOpen, int tueClose,
		int wedOpen, int wedClose, int thuOpen, int thuClose, int friOpen,
		int friClose, int satOpen, int satClose)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		try {
			OrgLaborService orgLaborService = OrgLaborServiceFactory.getService();

			return orgLaborService.addOrgLabor(organizationId, typeId, sunOpen,
				sunClose, monOpen, monClose, tueOpen, tueClose, wedOpen,
				wedClose, thuOpen, thuClose, friOpen, friClose, satOpen,
				satClose);
		}
		catch (com.liferay.portal.PortalException pe) {
			throw pe;
		}
		catch (com.liferay.portal.SystemException se) {
			throw se;
		}
		catch (Exception e) {
			throw new com.liferay.portal.SystemException(e);
		}
	}

	public static void deleteOrgLabor(java.lang.String orgLaborId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		try {
			OrgLaborService orgLaborService = OrgLaborServiceFactory.getService();
			orgLaborService.deleteOrgLabor(orgLaborId);
		}
		catch (com.liferay.portal.PortalException pe) {
			throw pe;
		}
		catch (com.liferay.portal.SystemException se) {
			throw se;
		}
		catch (Exception e) {
			throw new com.liferay.portal.SystemException(e);
		}
	}

	public static com.liferay.portal.model.OrgLabor getOrgLabor(
		java.lang.String orgLaborId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		try {
			OrgLaborService orgLaborService = OrgLaborServiceFactory.getService();

			return orgLaborService.getOrgLabor(orgLaborId);
		}
		catch (com.liferay.portal.PortalException pe) {
			throw pe;
		}
		catch (com.liferay.portal.SystemException se) {
			throw se;
		}
		catch (Exception e) {
			throw new com.liferay.portal.SystemException(e);
		}
	}

	public static java.util.List getOrgLabors(java.lang.String organizationId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		try {
			OrgLaborService orgLaborService = OrgLaborServiceFactory.getService();

			return orgLaborService.getOrgLabors(organizationId);
		}
		catch (com.liferay.portal.PortalException pe) {
			throw pe;
		}
		catch (com.liferay.portal.SystemException se) {
			throw se;
		}
		catch (Exception e) {
			throw new com.liferay.portal.SystemException(e);
		}
	}

	public static com.liferay.portal.model.OrgLabor updateOrgLabor(
		java.lang.String orgLaborId, int sunOpen, int sunClose, int monOpen,
		int monClose, int tueOpen, int tueClose, int wedOpen, int wedClose,
		int thuOpen, int thuClose, int friOpen, int friClose, int satOpen,
		int satClose)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		try {
			OrgLaborService orgLaborService = OrgLaborServiceFactory.getService();

			return orgLaborService.updateOrgLabor(orgLaborId, sunOpen,
				sunClose, monOpen, monClose, tueOpen, tueClose, wedOpen,
				wedClose, thuOpen, thuClose, friOpen, friClose, satOpen,
				satClose);
		}
		catch (com.liferay.portal.PortalException pe) {
			throw pe;
		}
		catch (com.liferay.portal.SystemException se) {
			throw se;
		}
		catch (Exception e) {
			throw new com.liferay.portal.SystemException(e);
		}
	}
}
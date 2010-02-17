/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.service;


/**
 * <a href="PortletLocalServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class is a wrapper for {@link PortletLocalService}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       PortletLocalService
 * @generated
 */
public class PortletLocalServiceWrapper implements PortletLocalService {
	public PortletLocalServiceWrapper(PortletLocalService portletLocalService) {
		_portletLocalService = portletLocalService;
	}

	public com.liferay.portal.model.Portlet addPortlet(
		com.liferay.portal.model.Portlet portlet)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _portletLocalService.addPortlet(portlet);
	}

	public com.liferay.portal.model.Portlet createPortlet(long id) {
		return _portletLocalService.createPortlet(id);
	}

	public void deletePortlet(long id)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_portletLocalService.deletePortlet(id);
	}

	public void deletePortlet(com.liferay.portal.model.Portlet portlet)
		throws com.liferay.portal.kernel.exception.SystemException {
		_portletLocalService.deletePortlet(portlet);
	}

	public java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _portletLocalService.dynamicQuery(dynamicQuery);
	}

	public java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return _portletLocalService.dynamicQuery(dynamicQuery, start, end);
	}

	public com.liferay.portal.model.Portlet getPortlet(long id)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _portletLocalService.getPortlet(id);
	}

	public java.util.List<com.liferay.portal.model.Portlet> getPortlets(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _portletLocalService.getPortlets(start, end);
	}

	public int getPortletsCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _portletLocalService.getPortletsCount();
	}

	public com.liferay.portal.model.Portlet updatePortlet(
		com.liferay.portal.model.Portlet portlet)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _portletLocalService.updatePortlet(portlet);
	}

	public com.liferay.portal.model.Portlet updatePortlet(
		com.liferay.portal.model.Portlet portlet, boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _portletLocalService.updatePortlet(portlet, merge);
	}

	public void checkPortlet(com.liferay.portal.model.Portlet portlet)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_portletLocalService.checkPortlet(portlet);
	}

	public void checkPortlets(long companyId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_portletLocalService.checkPortlets(companyId);
	}

	public com.liferay.portal.model.Portlet deployRemotePortlet(
		com.liferay.portal.model.Portlet portlet, java.lang.String category)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _portletLocalService.deployRemotePortlet(portlet, category);
	}

	public void destroyRemotePortlet(com.liferay.portal.model.Portlet portlet) {
		_portletLocalService.destroyRemotePortlet(portlet);
	}

	public void destroyPortlet(com.liferay.portal.model.Portlet portlet) {
		_portletLocalService.destroyPortlet(portlet);
	}

	public java.util.List<com.liferay.portlet.expando.model.CustomAttributesDisplay> getCustomAttributesDisplays() {
		return _portletLocalService.getCustomAttributesDisplays();
	}

	public com.liferay.portal.model.PortletCategory getEARDisplay(
		java.lang.String xml)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _portletLocalService.getEARDisplay(xml);
	}

	public com.liferay.portal.model.PortletApp getPortletApp(
		java.lang.String servletContextName) {
		return _portletLocalService.getPortletApp(servletContextName);
	}

	public com.liferay.portal.model.PortletCategory getWARDisplay(
		java.lang.String servletContextName, java.lang.String xml)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _portletLocalService.getWARDisplay(servletContextName, xml);
	}

	public java.util.List<com.liferay.portal.model.Portlet> getFriendlyURLMapperPortlets() {
		return _portletLocalService.getFriendlyURLMapperPortlets();
	}

	public java.util.List<com.liferay.portal.kernel.portlet.FriendlyURLMapper> getFriendlyURLMappers() {
		return _portletLocalService.getFriendlyURLMappers();
	}

	public com.liferay.portal.model.Portlet getPortletById(
		java.lang.String portletId) {
		return _portletLocalService.getPortletById(portletId);
	}

	public com.liferay.portal.model.Portlet getPortletById(long companyId,
		java.lang.String portletId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _portletLocalService.getPortletById(companyId, portletId);
	}

	public com.liferay.portal.model.Portlet getPortletByStrutsPath(
		long companyId, java.lang.String strutsPath)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _portletLocalService.getPortletByStrutsPath(companyId, strutsPath);
	}

	public java.util.List<com.liferay.portal.model.Portlet> getPortlets() {
		return _portletLocalService.getPortlets();
	}

	public java.util.List<com.liferay.portal.model.Portlet> getPortlets(
		long companyId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _portletLocalService.getPortlets(companyId);
	}

	public java.util.List<com.liferay.portal.model.Portlet> getPortlets(
		long companyId, boolean showSystem, boolean showPortal)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _portletLocalService.getPortlets(companyId, showSystem,
			showPortal);
	}

	public boolean hasPortlet(long companyId, java.lang.String portletId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _portletLocalService.hasPortlet(companyId, portletId);
	}

	public void initEAR(javax.servlet.ServletContext servletContext,
		java.lang.String[] xmls,
		com.liferay.portal.kernel.plugin.PluginPackage pluginPackage) {
		_portletLocalService.initEAR(servletContext, xmls, pluginPackage);
	}

	public java.util.List<com.liferay.portal.model.Portlet> initWAR(
		java.lang.String servletContextName,
		javax.servlet.ServletContext servletContext, java.lang.String[] xmls,
		com.liferay.portal.kernel.plugin.PluginPackage pluginPackage) {
		return _portletLocalService.initWAR(servletContextName, servletContext,
			xmls, pluginPackage);
	}

	public com.liferay.portal.model.Portlet newPortlet(long companyId,
		java.lang.String portletId) {
		return _portletLocalService.newPortlet(companyId, portletId);
	}

	public com.liferay.portal.model.Portlet updatePortlet(long companyId,
		java.lang.String portletId, java.lang.String roles, boolean active)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _portletLocalService.updatePortlet(companyId, portletId, roles,
			active);
	}

	public PortletLocalService getWrappedPortletLocalService() {
		return _portletLocalService;
	}

	private PortletLocalService _portletLocalService;
}
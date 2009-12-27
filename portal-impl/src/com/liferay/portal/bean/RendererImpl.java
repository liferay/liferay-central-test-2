/**
 * Copyright (c) 2000-2009 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.bean;

import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.bean.BeanLocator;
import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.bean.PortletBeanLocatorUtil;
import com.liferay.portal.kernel.bean.Renderer;
import com.liferay.portal.kernel.bean.RendererException;
import com.liferay.portal.kernel.io.unsync.UnsyncStringWriter;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.velocity.VelocityContext;
import com.liferay.portal.kernel.velocity.VelocityEngineUtil;
import com.liferay.portal.util.ContentUtil;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PrefsPropsUtil;
import com.liferay.portal.velocity.VelocityVariables;
import com.liferay.portlet.PortletPreferencesFactoryUtil;

import java.lang.reflect.Method;

import javax.portlet.PortletPreferences;
import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.NoSuchBeanDefinitionException;

/**
 * <a href="RendererImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Raymond Augé
 */
public class RendererImpl implements Renderer {

	public String renderBean(
			HttpServletRequest request, HttpServletResponse response,
			Object bean)
		throws RendererException {

		return renderBean(request, response, null, bean, null);
	}

	public String renderBean(
			HttpServletRequest request, HttpServletResponse response,
			Object bean, String varientSuffix)
		throws RendererException {

		return renderBean(request, response, null, bean, varientSuffix);
	}

	public String renderBean(
			HttpServletRequest request, HttpServletResponse response,
			String servletContextName, Object bean)
		throws RendererException {

		return renderBean(request, response, servletContextName, bean, null);
	}

	public String renderBean(
			HttpServletRequest request, HttpServletResponse response,
			String servletContextName, Object bean, String varientSuffix)
		throws RendererException {

		if (bean == null) {
			return null;
		}

		long companyId = PortalUtil.getCompanyId(request);

		String className = _normalizeClassName(bean.getClass().getName());

		if (Validator.isNotNull(varientSuffix)) {
			className = varientSuffix;
		}

		String velocityTemplateContent = null;

		PortletPreferences preferences = _getPortletPreferences(request);

		if (preferences != null) {
			velocityTemplateContent = preferences.getValue(
				RENDERER_TEMPLATE_PREFIX + className, StringPool.BLANK);
		}

		if (Validator.isNull(velocityTemplateContent) &&
			Validator.isNotNull(servletContextName)) {

			if (servletContextName.startsWith(StringPool.SLASH)) {
				servletContextName = servletContextName.substring(1);
			}

			try {
				BeanLocator beanLocator = PortletBeanLocatorUtil.getBeanLocator(
					servletContextName);

				velocityTemplateContent = ContentUtil.get(
					beanLocator.getClassLoader(),
					PropsUtil.get(RENDERER_TEMPLATE_PREFIX + className));
			}
			catch (Exception e) {
			}
		}

		if (Validator.isNull(velocityTemplateContent)) {
			try {
				velocityTemplateContent = PrefsPropsUtil.getContent(
					companyId, RENDERER_TEMPLATE_PREFIX + className);
			}
			catch (Exception e) {
			}
		}

		if (Validator.isNull(velocityTemplateContent)) {
			_log.warn(
				"No entity renderer template found for " + className);

			return null;
		}

		VelocityContext velocityContext =
			VelocityEngineUtil.getWrappedStandardToolsContext();

		// Velocity variables

		VelocityVariables.insertVariables(velocityContext, request);

		velocityContext.put(_BEAN, bean);

		try {
			UnsyncStringWriter unsyncStringWriter = new UnsyncStringWriter(
				true);

			VelocityEngineUtil.mergeTemplate(
				className, velocityTemplateContent, velocityContext,
				unsyncStringWriter);

			return unsyncStringWriter.toString();
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RendererException(e);
		}
	}

	public String renderBean(
			PortletRequest portletRequest, PortletResponse portletResponse,
			Object bean)
		throws RendererException {

		return renderBean(portletRequest, portletResponse, null, bean, null);
	}

	public String renderBean(
			PortletRequest portletRequest, PortletResponse portletResponse,
			Object bean, String varientSuffix)
		throws RendererException {

		return renderBean(
			portletRequest, portletResponse, null, bean, varientSuffix);
	}

	public String renderBean(
			PortletRequest portletRequest, PortletResponse portletResponse,
			String servletContextName, Object bean)
		throws RendererException {

		return renderBean(
			portletRequest, portletResponse, servletContextName, bean, null);
	}

	public String renderBean(
			PortletRequest portletRequest, PortletResponse portletResponse,
			String servletContextName, Object bean, String varientSuffix)
		throws RendererException {

		HttpServletRequest request = PortalUtil.getHttpServletRequest(
			portletRequest);
		HttpServletResponse response = PortalUtil.getHttpServletResponse(
			portletResponse);

		return renderBean(
			request, response, servletContextName, bean, varientSuffix);
	}

	public String renderEntity(
			HttpServletRequest request, HttpServletResponse response,
			String className, Object classPK)
		throws RendererException {

		return renderEntity(request, response, null, className, classPK, null);
	}

	public String renderEntity(
			HttpServletRequest request, HttpServletResponse response,
			String className, Object classPK, String varientSuffix)
		throws RendererException {

		return renderEntity(
			request, response, null, className, classPK, varientSuffix);
	}

	public String renderEntity(
			HttpServletRequest request, HttpServletResponse response,
			String servletContextName, String className, Object classPK)
		throws RendererException {

		return renderEntity(
			request, response, servletContextName, className, classPK, null);
	}

	public String renderEntity(
			HttpServletRequest request, HttpServletResponse response,
			String servletContextName, String className, Object classPK,
			String varientSuffix)
		throws RendererException {

		if (Validator.isNull(className)) {
			return null;
		}

		className = _normalizeClassName(className);

		String[] beanNameParts = StringUtil.split(className, _MODEL);

		Object serviceBean = null;

		if (Validator.isNotNull(servletContextName)) {
			if (servletContextName.startsWith(StringPool.SLASH)) {
				servletContextName = servletContextName.substring(1);
			}

			try {
				serviceBean = PortletBeanLocatorUtil.locate(
					servletContextName,
					beanNameParts[0] + _SERVICE + beanNameParts[1] +
						_LOCAL_SERVICE_UTIL);
			}
			catch (NoSuchBeanDefinitionException nsbde) {
			}
		}
		else {
			try {
				serviceBean = PortalBeanLocatorUtil.locate(
					beanNameParts[0] + _SERVICE + beanNameParts[1] +
						_LOCAL_SERVICE_UTIL);
			}
			catch (NoSuchBeanDefinitionException nsbde) {
			}
		}

		Object bean = null;

		if (serviceBean != null) {
			Method getMethod = null;

			try {
				getMethod = serviceBean.getClass().getDeclaredMethod(
					"get" + beanNameParts[1], classPK.getClass());
			}
			catch (Exception e) {
			}

			if (getMethod == null) {
				try {
					getMethod = serviceBean.getClass().getDeclaredMethod(
						"get" + beanNameParts[1],
						_mapToPrimitive(classPK.getClass()));
				}
				catch (Exception e) {
				}
			}

			if (getMethod != null) {
				try {
					bean = getMethod.invoke(null, classPK);
				}
				catch (Exception e) {
					_log.warn(e.getMessage());
				}
			}
		}

		return renderBean(
			request, response, servletContextName, bean, varientSuffix);
	}

	public String renderEntity(
			PortletRequest portletRequest, PortletResponse portletResponse,
			String className, Object classPK)
		throws RendererException {

		return renderEntity(
			portletRequest, portletResponse, null, className, classPK, null);
	}

	public String renderEntity(
			PortletRequest portletRequest, PortletResponse portletResponse,
			String className, Object classPK, String varientSuffix)
		throws RendererException {

		return renderEntity(
			portletRequest, portletResponse, null, className, classPK,
			varientSuffix);
	}

	public String renderEntity(
			PortletRequest portletRequest, PortletResponse portletResponse,
			String servletContextName, String className, Object classPK)
		throws RendererException {

		return renderEntity(
			portletRequest, portletResponse, servletContextName, className,
			classPK, null);
	}

	public String renderEntity(
			PortletRequest portletRequest, PortletResponse portletResponse,
			String servletContextName, String className, Object classPK,
			String varientSuffix)
		throws RendererException {

		HttpServletRequest request = PortalUtil.getHttpServletRequest(
			portletRequest);
		HttpServletResponse response = PortalUtil.getHttpServletResponse(
			portletResponse);

		return renderEntity(
			request, response, servletContextName, className, classPK,
			varientSuffix);
	}

	protected PortletPreferences _getPortletPreferences(
		HttpServletRequest request) {

		PortletPreferences preferences = PortalUtil.getPreferences(request);

		String portletResource = ParamUtil.getString(
			request, "portletResource");

		if (Validator.isNotNull(portletResource)) {
			try {
				preferences = PortletPreferencesFactoryUtil.getPortletSetup(
					request, portletResource);
			}
			catch (SystemException se) {
			}
		}

		return preferences;
	}

	protected Class<?> _mapToPrimitive(Class<?> clazz) {
		Class<?> mapping = clazz;

		if (clazz == Integer.class) {
			mapping = int.class;
		}
		else if (clazz == Long.class) {
			mapping = long.class;
		}

		return mapping;
	}

	protected String _normalizeClassName(String className) {
		className = StringUtil.replace(
			className,
			new String[] {
				".impl.",
				"Impl"
			},
			new String[] {
				StringPool.PERIOD,
				StringPool.BLANK
			}
		);

		return className;
	}

	private static final String _BEAN = "bean";

	private static final String _LOCAL_SERVICE_UTIL = "LocalServiceUtil";

	private static final String _MODEL = ".model.";

	private static final String _SERVICE = ".service.";

	private static Log _log = LogFactoryUtil.getLog(RendererImpl.class);

}
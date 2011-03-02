/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portal.model;

import com.liferay.portal.NoSuchLayoutRevisionException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.staging.Staging;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalClassLoaderUtil;
import com.liferay.portal.kernel.util.ReflectionUtil;
import com.liferay.portal.service.LayoutRevisionLocalServiceUtil;
import com.liferay.portal.service.LayoutSetBranchLocalServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceContextThreadLocal;
import com.liferay.portal.util.LayoutTypePortletFactoryUtil;
import com.liferay.portlet.PortalPreferences;
import com.liferay.portlet.PortletPreferencesFactoryUtil;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Raymond Augé
 * @author Brian Wing Shun Chan
 */
public class LayoutStagingHandler implements InvocationHandler {

	public LayoutStagingHandler(Layout layout) {
		this(layout, null);
	}

	public Layout getLayout() {
		return _layout;
	}

	public LayoutRevision getLayoutRevision() {
		return _layoutRevision;
	}

	public Object invoke(Object proxy, Method method, Object[] args)
		throws Throwable {

		try {
			if (_layoutRevision == null) {
				return method.invoke(_layout, args);
			}

			String methodName = method.getName();

			if (methodName.equals("getLayoutType")) {
				return _getLayoutType();
			}

			if (methodName.equals("toEscapedModel")) {
				if (_layout.isEscapedModel()) {
					return this;
				}

				return _toEscapedModel();
			}

			if (methodName.equals("clone")) {
				return _clone();
			}

			Object bean = _layout;

			if (_layoutRevisionMethodNames.contains(methodName)) {
				try {
					Class<?> layoutRevisionClass = _layoutRevision.getClass();

					method = layoutRevisionClass.getMethod(
						methodName, ReflectionUtil.getParameterTypes(args));

					bean = _layoutRevision;
				}
				catch (NoSuchMethodException nsme) {
					_log.error(nsme);
				}
			}

			return method.invoke(bean, args);
		}
		catch (InvocationTargetException ite) {
			throw ite.getTargetException();
		}
	}

	private LayoutStagingHandler(
		Layout layout, LayoutRevision layoutRevision) {

		_layout = layout;

		try {
			_layoutRevision = _getLayoutRevision(layout, layoutRevision);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new IllegalStateException(e);
		}
	}

	private Object _clone() {
		return Proxy.newProxyInstance(
			PortalClassLoaderUtil.getClassLoader(), new Class[] {Layout.class},
			new LayoutStagingHandler(_layout, _layoutRevision));
	}

	private LayoutRevision _getLayoutRevision(
			Layout layout, LayoutRevision layoutRevision)
		throws PortalException, SystemException {

		if (layoutRevision != null) {
			return layoutRevision;
		}

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		if (!serviceContext.isSignedIn()) {
			return layoutRevision;
		}

		long layoutRevisionId = ParamUtil.getLong(
			serviceContext, "layoutRevisionId");
		long layoutSetBranchId = ParamUtil.getLong(
			serviceContext, "layoutSetBranchId");

		PortalPreferences portalPreferences =
			PortletPreferencesFactoryUtil.getPortalPreferences(
				layout.getCompanyId(), serviceContext.getUserId(),
				serviceContext.isSignedIn());

		if (layoutSetBranchId <= 0) {
			layoutSetBranchId = GetterUtil.getLong(
				portalPreferences.getValue(
					Staging.class.getName(), "LAYOUT_SET_BRANCH_ID"));
		}

		LayoutSetBranch layoutSetBranch = null;

		if (layoutSetBranchId > 0) {
			layoutSetBranch =
				LayoutSetBranchLocalServiceUtil.getLayoutSetBranch(
					layoutSetBranchId);
		}
		else {
			layoutSetBranch =
				LayoutSetBranchLocalServiceUtil.getMasterLayoutSetBranch(
					layout.getGroupId(), layout.isPrivateLayout());

			layoutSetBranchId = layoutSetBranch.getLayoutSetBranchId();
		}

		if (layoutRevisionId <= 0) {
			layoutRevisionId = GetterUtil.getLong(
				portalPreferences.getValue(
					Staging.class.getName(),
					LayoutRevisionConstants.encodeKey(
						layoutSetBranchId, layout.getPlid())));
		}

		try {
			return LayoutRevisionLocalServiceUtil.getLayoutRevision(
				layoutRevisionId);
		}
		catch (NoSuchLayoutRevisionException nslre) {
		}

		try {
			return LayoutRevisionLocalServiceUtil.getLayoutRevision(
				layoutSetBranch.getLayoutSetBranchId(),
				layout.getPlid(), true);
		}
		catch (NoSuchLayoutRevisionException nslre) {
			List<LayoutRevision> revisions =
				LayoutRevisionLocalServiceUtil.getLayoutRevisions(
					layoutSetBranch.getLayoutSetBranchId(),
					layout.getPlid(),
					LayoutRevisionConstants.DEFAULT_PARENT_LAYOUT_REVISION_ID);

			if (!revisions.isEmpty()) {
				return revisions.get(0);
			}
		}

		return LayoutRevisionLocalServiceUtil.addLayoutRevision(
			serviceContext.getUserId(), layoutSetBranchId,
			LayoutRevisionConstants.DEFAULT_PARENT_LAYOUT_REVISION_ID,
			false, layout.getPlid(), layout.getPrivateLayout(), layout.getName(),
			layout.getTitle(), layout.getDescription(), layout.getKeywords(),
			layout.getRobots(), layout.getTypeSettings(), layout.getIconImage(),
			layout.getIconImageId(), layout.getThemeId(),
			layout.getColorSchemeId(), layout.getWapThemeId(),
			layout.getWapColorSchemeId(), layout.getCss(), serviceContext);
	}

	private LayoutType _getLayoutType() {
		return LayoutTypePortletFactoryUtil.create(
			(Layout)Proxy.newProxyInstance(
				PortalClassLoaderUtil.getClassLoader(),
				new Class[] {Layout.class},
				new LayoutStagingHandler(_layout, _layoutRevision)));
	}

	private Object _toEscapedModel() {
		return Proxy.newProxyInstance(
			PortalClassLoaderUtil.getClassLoader(),
			new Class[] {Layout.class},
			new LayoutStagingHandler(
				_layout.toEscapedModel(), _layoutRevision.toEscapedModel()));
	}

	private static Log _log = LogFactoryUtil.getLog(LayoutStagingHandler.class);

	private static Set<String> _layoutRevisionMethodNames =
		new HashSet<String>();

	static {
		_layoutRevisionMethodNames.add("getColorSchemeId");
		_layoutRevisionMethodNames.add("getCss");
		_layoutRevisionMethodNames.add("getDescription");
		_layoutRevisionMethodNames.add("getIconImage");
		_layoutRevisionMethodNames.add("getIconImageId");
		_layoutRevisionMethodNames.add("getKeywords");
		_layoutRevisionMethodNames.add("getName");
		_layoutRevisionMethodNames.add("getRobots");
		_layoutRevisionMethodNames.add("getThemeId");
		_layoutRevisionMethodNames.add("getTitle");
		_layoutRevisionMethodNames.add("getTypeSettings");
		_layoutRevisionMethodNames.add("getTypeSettingsProperties");
		_layoutRevisionMethodNames.add("getWapColorSchemeId");
		_layoutRevisionMethodNames.add("getWapThemeId");
		_layoutRevisionMethodNames.add("isEscapedModel");
		_layoutRevisionMethodNames.add("isIconImage");
		_layoutRevisionMethodNames.add("setColorSchemeId");
		_layoutRevisionMethodNames.add("setCss");
		_layoutRevisionMethodNames.add("setDescription");
		_layoutRevisionMethodNames.add("setDescriptionMap");
		_layoutRevisionMethodNames.add("setEscapedModel");
		_layoutRevisionMethodNames.add("setIconImage");
		_layoutRevisionMethodNames.add("setIconImageId");
		_layoutRevisionMethodNames.add("setKeywords");
		_layoutRevisionMethodNames.add("setKeywordsMap");
		_layoutRevisionMethodNames.add("setName");
		_layoutRevisionMethodNames.add("setNameMap");
		_layoutRevisionMethodNames.add("setRobots");
		_layoutRevisionMethodNames.add("setRobotsMap");
		_layoutRevisionMethodNames.add("setThemeId");
		_layoutRevisionMethodNames.add("setTitle");
		_layoutRevisionMethodNames.add("setTitleMap");
		_layoutRevisionMethodNames.add("setTypeSettings");
		_layoutRevisionMethodNames.add("setTypeSettingsProperties");
		_layoutRevisionMethodNames.add("setWapColorSchemeId");
		_layoutRevisionMethodNames.add("setWapThemeId");
	}

	private Layout _layout;
	private LayoutRevision _layoutRevision;

}
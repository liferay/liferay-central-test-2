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

package com.liferay.portal.service.impl;

import com.liferay.portal.kernel.staging.LayoutStagingUtil;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutRevision;
import com.liferay.portal.model.PortletPreferencesIds;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.service.LayoutRevisionLocalServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceContextThreadLocal;
import com.liferay.portal.service.persistence.LayoutRevisionUtil;
import com.liferay.portal.util.PortletKeys;

import java.lang.reflect.Method;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

/**
 * @author Raymond Augé
 */
public class PortletPreferencesLocalServiceStagingAdvice
	extends LayoutLocalServiceImpl implements MethodInterceptor {

	public Object invoke(MethodInvocation methodInvocation) throws Throwable {
		try {
			String methodName = methodInvocation.getMethod().getName();

			if (methodName.equals("getPreferences")) {
				return _getPreferences(methodInvocation);
			}
			else if (methodName.equals("updatePreferences")) {
				return _updatePreferences(methodInvocation);
			}
			else {
				return methodInvocation.proceed();
			}
		}
		catch (Throwable throwable) {
			throw throwable;
		}
	}

	protected Object _getPreferences(MethodInvocation methodInvocation)
		throws Throwable {

		Method method = methodInvocation.getMethod();

		Object args[] = methodInvocation.getArguments();

		long plid = 0;
		String portletId = null;

		if (args.length == 1) {
			PortletPreferencesIds portletPreferencesIds =
				(PortletPreferencesIds)args[0];

			plid = portletPreferencesIds.getPlid();
			portletId = portletPreferencesIds.getPortletId();
		}
		else {
			plid = (Long)args[3];
			portletId = (String)args[4];
		}

		if (portletId.equals(PortletKeys.LIFERAY_PORTAL) ||
			(plid <= 0)) {

			return methodInvocation.proceed();
		}

		Layout layout = LayoutLocalServiceUtil.getLayout(plid);

		if (!LayoutStagingUtil.isBranchingLayout(layout)) {
			return methodInvocation.proceed();
		}

		LayoutRevision layoutRevision =
			LayoutStagingUtil.getLayoutRevision(layout);

		plid = layoutRevision.getLayoutRevisionId();

		if (args.length == 1) {
			PortletPreferencesIds portletPreferencesIds =
				(PortletPreferencesIds)args[0];

			portletPreferencesIds.setPlid(plid);
		}
		else {
			args[3] = plid;
		}

		return method.invoke(methodInvocation.getThis(), args);
	}

	protected Object _updatePreferences(MethodInvocation methodInvocation)
		throws Throwable {

		Method method = methodInvocation.getMethod();

		Object args[] = methodInvocation.getArguments();

		long plid = (Long)args[2];
		String portletId = (String)args[3];

		if (portletId.equals(PortletKeys.LIFERAY_PORTAL) ||
			(plid <= 0)) {

			return methodInvocation.proceed();
		}

		LayoutRevision layoutRevision = LayoutRevisionUtil.fetchByPrimaryKey(
			plid);

		if (layoutRevision == null) {
			return methodInvocation.proceed();
		}

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		layoutRevision = LayoutRevisionLocalServiceUtil.updateLayoutRevision(
			serviceContext.getUserId(), layoutRevision.getLayoutRevisionId(),
			layoutRevision.getName(), layoutRevision.getTitle(),
			layoutRevision.getDescription(), layoutRevision.getKeywords(),
			layoutRevision.getRobots(), layoutRevision.getTypeSettings(),
			layoutRevision.getIconImage(), layoutRevision.getIconImageId(),
			layoutRevision.getThemeId(), layoutRevision.getColorSchemeId(),
			layoutRevision.getWapThemeId(),
			layoutRevision.getWapColorSchemeId(), layoutRevision.getCss(),
			serviceContext);

		args[2] = layoutRevision.getLayoutRevisionId();

		return method.invoke(methodInvocation.getThis(), args);
	}

}
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

import com.liferay.portal.kernel.util.PortalClassLoaderUtil;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutRevision;
import com.liferay.portal.model.impl.LayoutStagingHandler;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

import java.util.ArrayList;
import java.util.List;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

/**
 * @author Raymond Augé
 * @author Brian Wing Shun Chan
 */
public class LayoutLocalServiceStagingAdvice implements MethodInterceptor {

	public Object invoke(MethodInvocation methodInvocation) throws Throwable {
		try {
			Object returnValue = methodInvocation.proceed();

			returnValue = wrapReturnValue(returnValue);

			return returnValue;
		}
		catch (Throwable throwable) {
			throw throwable;
		}
	}

	protected LayoutStagingHandler getLayoutStagingHandler(Layout layout) {
		if (!Proxy.isProxyClass(layout.getClass())) {
			return null;
		}

		InvocationHandler invocationHandler = Proxy.getInvocationHandler(
			layout);

		if (!(invocationHandler instanceof LayoutStagingHandler)) {
			return null;
		}

		return (LayoutStagingHandler)invocationHandler;
	}

	protected boolean isStagingLayout(Layout layout) {
		try {
			Group group = layout.getGroup();

			if (group.isStagingGroup()) {
				group = group.getLiveGroup();
			}

			if (group.isStaged()) {
				return true;
			}

			return false;
		}
		catch (Exception e) {
			throw new IllegalStateException(e);
		}
	}

	protected Layout unwrapLayout(Layout layout) {
		LayoutStagingHandler layoutStagingHandler = getLayoutStagingHandler(
			layout);

		if (layoutStagingHandler == null) {
			return layout;
		}

		return layoutStagingHandler.getLayout();
	}

	protected LayoutRevision unwrapLayoutRevision(Layout layout) {
		LayoutStagingHandler layoutStagingHandler = getLayoutStagingHandler(
			layout);

		if (layoutStagingHandler == null) {
			return null;
		}

		return layoutStagingHandler.getLayoutRevision();
	}

	protected Layout wrapLayout(Layout layout) {
		if (Proxy.isProxyClass(layout.getClass())) {
			InvocationHandler invocationHandler = Proxy.getInvocationHandler(
				layout);

			if (invocationHandler instanceof LayoutStagingHandler) {
				return layout;
			}
		}

		if (!isStagingLayout(layout)) {
			return layout;
		}

		return (Layout)Proxy.newProxyInstance(
			PortalClassLoaderUtil.getClassLoader(), new Class[] {Layout.class},
			new LayoutStagingHandler(layout));
	}

	protected List<Layout> wrapLayouts(List<Layout> layouts) {
		if (layouts.isEmpty()) {
			return layouts;
		}

		Layout firstLayout = layouts.get(0);

		Layout wrappedFirstLayout = wrapLayout(firstLayout);

		if (wrappedFirstLayout == firstLayout) {
			return layouts;
		}

		List<Layout> wrappedLayouts = new ArrayList<Layout>(layouts.size());

		wrappedLayouts.add(wrappedFirstLayout);

		for (int i = 1; i < layouts.size(); i++) {
			Layout layout = layouts.get(i);

			wrappedLayouts.add(wrapLayout(layout));
		}

		return layouts;
	}

	protected Object wrapReturnValue(Object returnValue) {
		if (returnValue instanceof Layout) {
			returnValue = wrapLayout((Layout)returnValue);
		}
		else if (returnValue instanceof List<?>) {
			returnValue = wrapLayouts((List<Layout>)returnValue);
		}

		return returnValue;
	}

}
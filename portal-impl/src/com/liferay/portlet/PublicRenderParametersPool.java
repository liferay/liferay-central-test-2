/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
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

package com.liferay.portlet;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.AutoResetThreadLocal;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutSet;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.util.PropsValues;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author Michael Young
 */
public class PublicRenderParametersPool {

	public static PublicRenderParameters get(
		HttpServletRequest request, long plid, boolean isWarFile) {

		Map<String, String[]> threadLocalMap = null;

		Map<String, String[]> map = get(request, plid);

		if (isWarFile) {
			if (_publicRenderParametersThreadLocal.get() == null) {
				_publicRenderParametersThreadLocal.set(
					new HashMap<String, String[]>());
			}
			else {
				map.putAll(_publicRenderParametersThreadLocal.get());
			}

			threadLocalMap = _publicRenderParametersThreadLocal.get();
		}

		return new PublicRenderParameters(map, threadLocalMap);
	}

	protected static Map<String, String[]> get(
		HttpServletRequest request, long plid) {

		if (PropsValues.PORTLET_PUBLIC_RENDER_PARAMETER_DISTRIBUTION_LAYOUT) {
			return RenderParametersPool.get(
				request, plid, _PUBLIC_RENDER_PARAMETERS);
		}

		HttpSession session = request.getSession();

		Map<Long, Map<String, String[]>> publicRenderParametersPool =
			(Map<Long, Map<String, String[]>>)session.getAttribute(
				WebKeys.PUBLIC_RENDER_PARAMETERS_POOL);

		if (publicRenderParametersPool == null) {
			publicRenderParametersPool = new ConcurrentHashMap<>();

			session.setAttribute(
				WebKeys.PUBLIC_RENDER_PARAMETERS_POOL,
				publicRenderParametersPool);
		}

		try {
			Layout layout = LayoutLocalServiceUtil.getLayout(plid);

			LayoutSet layoutSet = layout.getLayoutSet();

			Map<String, String[]> publicRenderParameters =
				publicRenderParametersPool.get(layoutSet.getLayoutSetId());

			if (publicRenderParameters == null) {
				publicRenderParameters = new HashMap<>();

				publicRenderParametersPool.put(
					layoutSet.getLayoutSetId(), publicRenderParameters);
			}

			return publicRenderParameters;
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn(e, e);
			}

			return new HashMap<>();
		}
	}

	private static final String _PUBLIC_RENDER_PARAMETERS =
		"PUBLIC_RENDER_PARAMETERS";

	private static final Log _log = LogFactoryUtil.getLog(
		PublicRenderParametersPool.class);

	private static final ThreadLocal<Map<String, String[]>>
		_publicRenderParametersThreadLocal = new AutoResetThreadLocal<>(
			PublicRenderParametersPool.class +
				"._publicRenderParametersThreadLocal");

}
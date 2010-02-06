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

package com.liferay.portlet;

import com.liferay.portal.util.WebKeys;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * <a href="RenderParametersPool.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class RenderParametersPool {

	public static void clear(HttpServletRequest request, long plid) {
		Map<String, Map<String, String[]>> plidPool = get(request, plid);

		plidPool.clear();
	}

	public static void clear(
		HttpServletRequest request, long plid, String portletId) {

		Map<String, String[]> params = get(request, plid, portletId);

		params.clear();
	}

	public static Map<String, Map<String, String[]>> get(
		HttpServletRequest request, long plid) {

		HttpSession session = request.getSession();

		if (plid <= 0) {
			return new ConcurrentHashMap<String, Map<String, String[]>>();
		}

		Map<Long, Map<String, Map<String, String[]>>> pool =
			_getRenderParametersPool(session);

		Map<String, Map<String, String[]>> plidPool = pool.get(plid);

		if (plidPool == null) {
			plidPool = new ConcurrentHashMap<String, Map<String, String[]>>();

			pool.put(plid, plidPool);
		}

		return plidPool;
	}

	public static Map<String, String[]> get(
		HttpServletRequest request, long plid, String portletId) {

		Map<String, Map<String, String[]>> plidPool = get(request, plid);

		Map<String, String[]> params = plidPool.get(portletId);

		if (params == null) {
			params = new HashMap<String, String[]>();

			plidPool.put(portletId, params);
		}

		return params;
	}

	public static void put(
		HttpServletRequest request, long plid, String portletId,
		Map<String, String[]> params) {

		Map<String, Map<String, String[]>> plidPool = get(request, plid);

		plidPool.put(portletId, params);
	}

	private static Map<Long, Map<String, Map<String, String[]>>>
		_getRenderParametersPool(HttpSession session) {

		Map<Long, Map<String, Map<String, String[]>>> renderParametersPool =
			(Map<Long, Map<String, Map<String, String[]>>>)session.getAttribute(
				WebKeys.PORTLET_RENDER_PARAMETERS);

		if (renderParametersPool == null) {
			renderParametersPool = new ConcurrentHashMap
				<Long, Map<String, Map<String, String[]>>>();

			session.setAttribute(
				WebKeys.PORTLET_RENDER_PARAMETERS, renderParametersPool);
		}

		return renderParametersPool;
	}

}
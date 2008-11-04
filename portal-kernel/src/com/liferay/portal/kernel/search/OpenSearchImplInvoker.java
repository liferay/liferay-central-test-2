/**
 * Copyright (c) 2000-2008 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.kernel.search;

import com.liferay.portal.kernel.portlet.PortletBag;
import com.liferay.portal.kernel.portlet.PortletBagPool;
import com.liferay.portal.kernel.servlet.PortletServlet;
import com.liferay.portal.kernel.util.InstancePool;
import com.liferay.portal.kernel.util.PortletClassInvoker;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

/**
 * <a href="OpenSearchImplInvoker.java.html"><b><i>View Source</i></b></a>
 *
 * @author Allen Chiang
 *
 */
public class OpenSearchImplInvoker implements OpenSearch {
        private String className = null;
        private String portletId = null;
        static Map<String, OpenSearch> instances = new HashMap();

        public static OpenSearch getOpenSearchInstance(String className, String portletId) {
            OpenSearch instance = instances.get(className);
            if (instance != null) {
                return instance;
            }
            PortletBag portletBag = PortletBagPool.get(portletId);
            // the value will be null if the portlet is in the same webapp of portal
            if (portletBag != null) {
                ClassLoader portletClassLoader =
			(ClassLoader)portletBag.getServletContext().getAttribute(
				PortletServlet.PORTLET_CLASS_LOADER);
                    instance = new OpenSearchImplInvoker(className, portletId);
                    instances.put(className, instance);
                    return instance;
            }
            return (OpenSearch)InstancePool.get(className);
        }

        private OpenSearchImplInvoker(String className, String portletId) {
            this.className = className;
            this.portletId = portletId;
        }

	public Boolean isEnabled() {
            try {
                Object o = PortletClassInvoker.invoke(portletId, className, "isEnabled");
                return (Boolean)o;
            } catch (Exception e) {

            }
            return Boolean.FALSE;
        }

	public String search(HttpServletRequest request, String url)
		throws SearchException {
            try {
            Object o = PortletClassInvoker.invoke(portletId, className, "search", request, url);
            return (String)o;
            } catch (Exception e) {
                throw new SearchException(e);
            }

        }

	public String search(
			HttpServletRequest request, String keywords, Integer startPage,
			Integer itemsPerPage)
		throws SearchException {
            try {
            Object o = PortletClassInvoker.invoke(portletId, className, "search", request, keywords, startPage, itemsPerPage);
            return (String)o;
            } catch (Exception e) {
                throw new SearchException(e);
            }

        }

}
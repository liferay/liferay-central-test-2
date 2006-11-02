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

package com.liferay.taglib.security;

import com.liferay.portal.kernel.util.MethodInvoker;
import com.liferay.portal.kernel.util.MethodWrapper;
import com.liferay.portal.kernel.util.PortalClassLoaderUtil;
import com.liferay.util.StringPool;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * <a href="PermissionsURLTag.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class PermissionsURLTag extends TagSupport {

	public int doEndTag() throws JspException {
		ClassLoader contextClassLoader =
			Thread.currentThread().getContextClassLoader();

		try {
			Thread.currentThread().setContextClassLoader(
				PortalClassLoaderUtil.getClassLoader());

			MethodWrapper methodWrapper = new MethodWrapper(
				_TAG_CLASS, _TAG_DO_END_METHOD,
				new Object[] {
					pageContext, _redirect, _modelResource,
					_modelResourceDescription, _resourcePrimKey, _var
				});

			MethodInvoker.invoke(methodWrapper);
		}
		catch (Exception e) {
			throw new JspException(e);
		}
		finally {
			Thread.currentThread().setContextClassLoader(contextClassLoader);
		}

		return EVAL_PAGE;
	}

	public void setRedirect(String redirect) {
		_redirect = redirect;
	}

	public void setModelResource(String modelResource) {
		_modelResource = modelResource;
	}

	public void setModelResourceDescription(String modelResourceDescription) {
		_modelResourceDescription = modelResourceDescription;
	}

	public void setResourcePrimKey(String resourcePrimKey) {
		_resourcePrimKey = resourcePrimKey;
	}

	public void setVar(String var) {
		_var = var;
	}

	private static final String _TAG_CLASS =
		"com.liferay.portal.servlet.taglib.security.PermissionsURLTagUtil";

	private static final String _TAG_DO_END_METHOD = "doEndTag";

	private String _redirect = StringPool.BLANK;
	private String _modelResource;
	private String _modelResourceDescription;
	private String _resourcePrimKey;
	private String _var = StringPool.BLANK;

}
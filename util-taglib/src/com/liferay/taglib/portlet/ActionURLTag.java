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

package com.liferay.taglib.portlet;

import com.liferay.portal.shared.util.BooleanWrapper;
import com.liferay.portal.shared.util.MethodInvoker;
import com.liferay.portal.shared.util.MethodWrapper;
import com.liferay.portal.shared.util.NullWrapper;
import com.liferay.portal.shared.util.PortalClassLoaderUtil;
import com.liferay.taglib.util.ParamAncestorTagImpl;

import java.util.Map;

import javax.servlet.jsp.JspException;

/**
 * <a href="ActionURLTag.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class ActionURLTag extends ParamAncestorTagImpl {

	public int doEndTag() throws JspException {
		ClassLoader contextClassLoader =
			Thread.currentThread().getContextClassLoader();

		try {
			Thread.currentThread().setContextClassLoader(
				PortalClassLoaderUtil.getClassLoader());

			Object windowStateWrapper = _windowState;

			if (windowStateWrapper == null) {
				windowStateWrapper = new NullWrapper(String.class.getName());
			}

			Object portletModeWrapper = _portletMode;

			if (portletModeWrapper == null) {
				portletModeWrapper = new NullWrapper(String.class.getName());
			}

			Object varWrapper = _var;

			if (varWrapper == null) {
				varWrapper = new NullWrapper(String.class.getName());
			}

			Object varImplWrapper = _varImpl;

			if (varImplWrapper == null) {
				varImplWrapper = new NullWrapper(String.class.getName());
			}

			Object secureWrapper = _secure;

			if (secureWrapper == null) {
				secureWrapper = new NullWrapper(Boolean.class.getName());
			}

			Object portletNameWrapper = _portletName;

			if (portletNameWrapper == null) {
				portletNameWrapper = new NullWrapper(String.class.getName());
			}

			Object anchorWrapper = _anchor;

			if (anchorWrapper == null) {
				anchorWrapper = new NullWrapper(Boolean.class.getName());
			}

			Object encryptWrapper = _encrypt;

			if (encryptWrapper == null) {
				encryptWrapper = new NullWrapper(Boolean.class.getName());
			}

			Object paramsWrapper = getParams();

			if (paramsWrapper == null) {
				paramsWrapper = new NullWrapper(Map.class.getName());
			}

			MethodWrapper methodWrapper = new MethodWrapper(
				_TAG_CLASS, _TAG_DO_END_METHOD,
				new Object[] {
					pageContext, new BooleanWrapper(isAction()),
					windowStateWrapper, portletModeWrapper, varWrapper,
					varImplWrapper, secureWrapper, portletNameWrapper,
					anchorWrapper, encryptWrapper,
					new BooleanWrapper(_portletConfiguration), paramsWrapper
				});

			MethodInvoker.invoke(methodWrapper);
		}
		catch (Exception e) {
			if (e instanceof JspException) {
				throw (JspException)e;
			}
			else {
				throw new JspException(e);
			}
		}
		finally {
			clearParams();

			Thread.currentThread().setContextClassLoader(contextClassLoader);
		}

		return EVAL_PAGE;
	}

	public boolean isAction() {
		return _ACTION;
	}

	public void setWindowState(String windowState) {
		_windowState = windowState;
	}

	public void setPortletMode(String portletMode) {
		_portletMode = portletMode;
	}

	public void setVar(String var) {
		_var = var;
	}

	public void setVarImpl(String varImpl) {
		_varImpl = varImpl;
	}

	public void setSecure(boolean secure) {
		_secure = new Boolean(secure);
	}

	public void setPortletName(String portletName) {
		_portletName = portletName;
	}

	public void setAnchor(boolean anchor) {
		_anchor = new Boolean(anchor);
	}

	public void setEncrypt(boolean encrypt) {
		_encrypt = new Boolean(encrypt);
	}

	public void setPortletConfiguration(boolean portletConfiguration) {
		_portletConfiguration = portletConfiguration;
	}

	private static final String _TAG_CLASS =
		"com.liferay.portlet.ActionURLTagUtil";

	private static final String _TAG_DO_END_METHOD = "doEndTag";

	private static final boolean _ACTION = true;

	private String _windowState;
	private String _portletMode;
	private String _var;
	private String _varImpl;
	private Boolean _secure;
	private String _portletName;
	private Boolean _anchor;
	private Boolean _encrypt;
	private boolean _portletConfiguration;

}
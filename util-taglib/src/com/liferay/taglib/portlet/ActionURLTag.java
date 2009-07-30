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

package com.liferay.taglib.portlet;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.BooleanWrapper;
import com.liferay.portal.kernel.util.LongWrapper;
import com.liferay.portal.kernel.util.MethodInvoker;
import com.liferay.portal.kernel.util.MethodWrapper;
import com.liferay.portal.kernel.util.NullWrapper;
import com.liferay.portal.kernel.util.PortalClassLoaderUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.LayoutConstants;
import com.liferay.taglib.util.ParamAndPropertyAncestorTagImpl;

import java.util.Map;

import javax.portlet.PortletRequest;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

/**
 * <a href="ActionURLTag.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class ActionURLTag extends ParamAndPropertyAncestorTagImpl {

	public static String doTag(
			String lifecycle, String windowState, String portletMode,
			String var, String varImpl, Boolean secure,
			Boolean copyCurrentRenderParameters, Boolean escapeXml, String name,
			String resourceID, String cacheability, long plid,
			String portletName, Boolean anchor, Boolean encrypt,
			long doAsUserId, Boolean portletConfiguration,
			Map<String, String[]> params, boolean writeOutput,
			PageContext pageContext)
		throws Exception {

		Object returnObj = null;

		Thread currentThread = Thread.currentThread();

		ClassLoader contextClassLoader = currentThread.getContextClassLoader();

		try {
			currentThread.setContextClassLoader(
				PortalClassLoaderUtil.getClassLoader());

			Object windowStateWrapper = windowState;

			if (windowStateWrapper == null) {
				windowStateWrapper = new NullWrapper(String.class.getName());
			}

			Object portletModeWrapper = portletMode;

			if (portletModeWrapper == null) {
				portletModeWrapper = new NullWrapper(String.class.getName());
			}

			Object varWrapper = var;

			if (varWrapper == null) {
				varWrapper = new NullWrapper(String.class.getName());
			}

			Object varImplWrapper = varImpl;

			if (varImplWrapper == null) {
				varImplWrapper = new NullWrapper(String.class.getName());
			}

			Object secureWrapper = secure;

			if (secureWrapper == null) {
				secureWrapper = new NullWrapper(Boolean.class.getName());
			}

			Object copyCurrentRenderParametersWrapper =
				copyCurrentRenderParameters;

			if (copyCurrentRenderParametersWrapper == null) {
				copyCurrentRenderParametersWrapper = new NullWrapper(
					Boolean.class.getName());
			}

			Object escapeXmlWrapper = escapeXml;

			if (escapeXmlWrapper == null) {
				escapeXmlWrapper = new NullWrapper(Boolean.class.getName());
			}

			Object nameWrapper = name;

			if (nameWrapper == null) {
				nameWrapper = new NullWrapper(String.class.getName());
			}

			Object resourceIDWrapper = resourceID;

			if (resourceIDWrapper == null) {
				resourceIDWrapper = new NullWrapper(String.class.getName());
			}

			Object cacheabilityWrapper = cacheability;

			if (cacheabilityWrapper == null) {
				cacheabilityWrapper = new NullWrapper(String.class.getName());
			}

			Object portletNameWrapper = portletName;

			if (portletNameWrapper == null) {
				portletNameWrapper = new NullWrapper(String.class.getName());
			}

			Object anchorWrapper = anchor;

			if (anchorWrapper == null) {
				anchorWrapper = new NullWrapper(Boolean.class.getName());
			}

			Object encryptWrapper = encrypt;

			if (encryptWrapper == null) {
				encryptWrapper = new NullWrapper(Boolean.class.getName());
			}

			Object portletConfigurationWrapper = portletConfiguration;

			if (portletConfigurationWrapper == null) {
				portletConfigurationWrapper = new NullWrapper(
					Boolean.class.getName());
			}

			Object paramsWrapper = params;

			if (paramsWrapper == null) {
				paramsWrapper = new NullWrapper(Map.class.getName());
			}

			MethodWrapper methodWrapper = new MethodWrapper(
				_TAG_CLASS, _TAG_DO_END_METHOD,
				new Object[] {
					lifecycle, windowStateWrapper, portletModeWrapper,
					varWrapper, varImplWrapper, secureWrapper,
					copyCurrentRenderParametersWrapper, escapeXmlWrapper,
					nameWrapper, resourceIDWrapper, cacheabilityWrapper,
					new LongWrapper(plid), portletNameWrapper, anchorWrapper,
					encryptWrapper, new LongWrapper(doAsUserId),
					portletConfigurationWrapper, paramsWrapper,
					new BooleanWrapper(writeOutput), pageContext
				});

			returnObj = MethodInvoker.invoke(methodWrapper);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw e;
		}
		finally {
			currentThread.setContextClassLoader(contextClassLoader);
		}

		if (returnObj != null) {
			return returnObj.toString();
		}
		else {
			return StringPool.BLANK;
		}
	}

	public int doEndTag() throws JspException {
		try {
			doTag(
				getLifecycle(), _windowState, _portletMode, _var, _varImpl,
				_secure, _copyCurrentRenderParameters, _escapeXml, _name,
				_resourceID, _cacheability, _plid, _portletName, _anchor,
				_encrypt, _doAsUserId, _portletConfiguration, getParams(), true,
				pageContext);
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
			clearProperties();

			_plid = LayoutConstants.DEFAULT_PLID;
		}

		return EVAL_PAGE;
	}

	public String getLifecycle() {
		return PortletRequest.ACTION_PHASE;
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
		_secure = Boolean.valueOf(secure);
	}

	public void setCopyCurrentRenderParameters(
		boolean copyCurrentRenderParameters) {

		_copyCurrentRenderParameters = Boolean.valueOf(
			copyCurrentRenderParameters);
	}

	public void setEscapeXml(boolean escapeXml) {
		_escapeXml = Boolean.valueOf(escapeXml);
	}

	public void setName(String name) {
		_name = name;
	}

	public void setId(String resourceID) {
		_resourceID = resourceID;
	}

	public void setCacheability(String cacheability) {
		_cacheability = cacheability;
	}

	public void setPlid(long plid) {
		_plid = plid;
	}

	public void setPortletName(String portletName) {
		_portletName = portletName;
	}

	public void setAnchor(boolean anchor) {
		_anchor = Boolean.valueOf(anchor);
	}

	public void setEncrypt(boolean encrypt) {
		_encrypt = Boolean.valueOf(encrypt);
	}

	public void setDoAsUserId(long doAsUserId) {
		_doAsUserId = doAsUserId;
	}

	public void setPortletConfiguration(boolean portletConfiguration) {
		_portletConfiguration = Boolean.valueOf(portletConfiguration);
	}

	private static final String _TAG_CLASS =
		"com.liferay.portal.servlet.taglib.portlet.ActionURLTagUtil";

	private static final String _TAG_DO_END_METHOD = "doEndTag";

	private static Log _log = LogFactoryUtil.getLog(ActionURLTag.class);

	private String _windowState;
	private String _portletMode;
	private String _var;
	private String _varImpl;
	private Boolean _secure;
	private Boolean _copyCurrentRenderParameters;
	private Boolean _escapeXml;
	private String  _name;
	private String _resourceID;
	private String _cacheability;
	private long _plid = LayoutConstants.DEFAULT_PLID;
	private String _portletName;
	private Boolean _anchor;
	private Boolean _encrypt;
	private long _doAsUserId;
	private Boolean _portletConfiguration;

}
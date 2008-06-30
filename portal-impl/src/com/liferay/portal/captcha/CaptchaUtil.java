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

package com.liferay.portal.captcha;

import com.liferay.portal.kernel.configuration.Configuration;
import com.liferay.portal.kernel.configuration.ConfigurationFactoryUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.util.PropsFiles;
import com.liferay.portal.util.PropsValues;
import com.liferay.portal.util.WebKeys;

import javax.portlet.PortletRequest;
import javax.portlet.PortletSession;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import nl.captcha.servlet.CaptchaProducer;
import nl.captcha.util.Helper;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <a href="CaptchaUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class CaptchaUtil {

	public static void check(HttpServletRequest request)
		throws CaptchaTextException {

		_instance._check(request);
	}

	public static void check(PortletRequest portletRequest)
		throws CaptchaTextException {

		_instance._check(portletRequest);
	}

	public static CaptchaProducer getCaptchaProducer() {
		return _instance._getCaptchaProducer();
	}

	public static boolean isEnabled(HttpServletRequest request) {
		return _instance._isEnabled(request);
	}

	public static boolean isEnabled(PortletRequest portletRequest) {
		return _instance._isEnabled(portletRequest);
	}

	private CaptchaUtil() {
		_configuration = ConfigurationFactoryUtil.getConfiguration(
			CaptchaUtil.class.getClassLoader(), PropsFiles.CAPTCHA);
		_captchaProducer = (CaptchaProducer)Helper.ThingFactory.loadImpl(
			Helper.ThingFactory.CPROD, _configuration.getProperties());
	}

	private void _check(HttpServletRequest request)
		throws CaptchaTextException {

		if (!_isEnabled(request)) {
			return;
		}

		HttpSession ses = request.getSession();

		String captchaText = (String)ses.getAttribute(WebKeys.CAPTCHA_TEXT);

		if (captchaText == null) {
			if (_log.isErrorEnabled()) {
				_log.error(
					"Captcha text is null. User " + request.getRemoteUser() +
						" may be trying to circumvent the captcha.");
			}

			throw new CaptchaTextException();
		}

		if (!captchaText.equals(ParamUtil.getString(request, "captchaText"))) {
			throw new CaptchaTextException();
		}

		if (_log.isDebugEnabled()) {
			_log.debug("Captcha text is valid");
		}

		ses.removeAttribute(WebKeys.CAPTCHA_TEXT);

		if ((PropsValues.CAPTCHA_MAX_CHALLENGES > 0) &&
			(Validator.isNotNull(request.getRemoteUser()))) {

			Integer count = (Integer)ses.getAttribute(WebKeys.CAPTCHA_COUNT);

			if (count == null) {
				count = new Integer(1);
			}
			else {
				count = new Integer(count.intValue() + 1);
			}

			ses.setAttribute(WebKeys.CAPTCHA_COUNT, count);
		}
	}

	private void _check(PortletRequest portletRequest)
		throws CaptchaTextException {

		if (!_isEnabled(portletRequest)) {
			return;
		}

		PortletSession portletSession = portletRequest.getPortletSession();

		String captchaText = (String)portletSession.getAttribute(
			WebKeys.CAPTCHA_TEXT);

		if (captchaText == null) {
			if (_log.isErrorEnabled()) {
				_log.error(
					"Captcha text is null. User " +
						portletRequest.getRemoteUser() +
							" may be trying to circumvent the captcha.");
			}

			throw new CaptchaTextException();
		}

		if (!captchaText.equals(
				ParamUtil.getString(portletRequest, "captchaText"))) {

			throw new CaptchaTextException();
		}

		if (_log.isDebugEnabled()) {
			_log.debug("Captcha text is valid");
		}

		portletSession.removeAttribute(WebKeys.CAPTCHA_TEXT);

		if ((PropsValues.CAPTCHA_MAX_CHALLENGES > 0) &&
			(Validator.isNotNull(portletRequest.getRemoteUser()))) {

			Integer count = (Integer)portletSession.getAttribute(
				WebKeys.CAPTCHA_COUNT);

			if (count == null) {
				count = new Integer(1);
			}
			else {
				count = new Integer(count.intValue() + 1);
			}

			portletSession.setAttribute(WebKeys.CAPTCHA_COUNT, count);
		}
	}

	private CaptchaProducer _getCaptchaProducer() {
		return _captchaProducer;
	}

	private boolean _isEnabled(HttpServletRequest request) {
		if (PropsValues.CAPTCHA_MAX_CHALLENGES > 0) {
			HttpSession session = request.getSession();

			Integer count = (Integer)session.getAttribute(
				WebKeys.CAPTCHA_COUNT);

			if ((count != null) &&
				(PropsValues.CAPTCHA_MAX_CHALLENGES <= count.intValue())) {

				return false;
			}
			else {
				return true;
			}
		}
		else if (PropsValues.CAPTCHA_MAX_CHALLENGES < 0) {
			return false;
		}
		else {
			return true;
		}
	}

	private boolean _isEnabled(PortletRequest portletRequest) {
		if (PropsValues.CAPTCHA_MAX_CHALLENGES > 0) {
			PortletSession portletSession = portletRequest.getPortletSession();

			Integer count = (Integer)portletSession.getAttribute(
				WebKeys.CAPTCHA_COUNT);

			if ((count != null) &&
				(PropsValues.CAPTCHA_MAX_CHALLENGES <= count.intValue())) {

				return false;
			}
			else {
				return true;
			}
		}
		else if (PropsValues.CAPTCHA_MAX_CHALLENGES < 0) {
			return false;
		}
		else {
			return true;
		}
	}

	private static Log _log = LogFactory.getLog(CaptchaUtil.class);

	private static CaptchaUtil _instance = new CaptchaUtil();

	private Configuration _configuration;
	private CaptchaProducer _captchaProducer;

}
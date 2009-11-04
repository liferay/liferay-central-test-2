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

package com.liferay.portal.captcha;

import com.liferay.portal.kernel.captcha.Captcha;
import com.liferay.portal.kernel.captcha.CaptchaTextException;
import com.liferay.portal.kernel.configuration.Configuration;
import com.liferay.portal.kernel.configuration.ConfigurationFactoryUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PropsFiles;
import com.liferay.portal.util.PropsValues;
import com.liferay.portal.util.WebKeys;

import java.io.IOException;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.portlet.PortletSession;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import nl.captcha.servlet.CaptchaProducer;
import nl.captcha.util.Helper;

/**
 * <a href="CaptchaImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 * @author Jorge Ferrer
 * @author Tagnaouti Boubker
 */
public class CaptchaImpl implements Captcha {

	private CaptchaImpl() {
		_configuration = ConfigurationFactoryUtil.getConfiguration(
			CaptchaImpl.class.getClassLoader(), PropsFiles.CAPTCHA);
		_captchaProducer = (CaptchaProducer)Helper.ThingFactory.loadImpl(
			Helper.ThingFactory.CPROD, _configuration.getProperties());
	}

	public void check(HttpServletRequest request)
		throws CaptchaTextException {

		if (!isEnabled(request)) {
			return;
		}

		if (PropsValues.CAPTCHA_ENGINE.equals("re_captcha")) {
			checkReCaptcha(request);
		}
		else {
			checkSimpleCaptcha(request);
		}
	}

	public void check(PortletRequest portletRequest)
		throws CaptchaTextException {

		if (!isEnabled(portletRequest)) {
			return;
		}

		if (PropsValues.CAPTCHA_ENGINE.equals("re_captcha")) {
			checkReCaptcha(portletRequest);
		}
		else {
			checkSimpleCaptcha(portletRequest);
		}
	}

	public boolean isEnabled(HttpServletRequest request) {
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

	public boolean isEnabled(PortletRequest portletRequest) {
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

	public void serveImage(
			HttpServletRequest request, HttpServletResponse response)
		throws IOException {

		HttpSession session = request.getSession();

		String captchaText = _captchaProducer.createText();

		session.setAttribute(WebKeys.CAPTCHA_TEXT, captchaText);

		response.setContentType(ContentTypes.IMAGE_JPEG);

		_captchaProducer.createImage(response.getOutputStream(), captchaText);
	}

	public void serveImage(
			PortletRequest portletRequest, PortletResponse portletResponse)
		throws IOException {

		PortletSession portletSession = portletRequest.getPortletSession();

		String captchaText = _captchaProducer.createText();

		portletSession.setAttribute(WebKeys.CAPTCHA_TEXT, captchaText);

		HttpServletResponse response = PortalUtil.getHttpServletResponse(
			portletResponse);

		response.setContentType(ContentTypes.IMAGE_JPEG);

		_captchaProducer.createImage(response.getOutputStream(), captchaText);
	}

	protected void checkReCaptcha(PortletRequest portletRequest)
		throws CaptchaTextException {

		HttpServletRequest servletRequest =
			PortalUtil.getHttpServletRequest(portletRequest);

		checkReCaptcha(servletRequest);
	}

	protected void checkReCaptcha(HttpServletRequest servletRequest)
		throws CaptchaTextException {

		String remoteIp = servletRequest.getRemoteAddr();

		String recaptchaChallenge =
			ParamUtil.getString(servletRequest, "recaptcha_challenge_field");
		String recaptchaResponse =
			ParamUtil.getString(servletRequest, "recaptcha_response_field");

		if (recaptchaResponse.equals(StringPool.BLANK)) {
			throw new CaptchaTextException("Captcha response cannot be empty");
		}

		Http.Options options = new Http.Options();

		options.setLocation(PropsValues.CAPTCHA_ENGINE_RECAPTCHA_URL_VERIFY);
		options.addPart(
			"privatekey", PropsValues.CAPTCHA_ENGINE_RECAPTCHA_KEY_PRIVATE);
		options.addPart("remoteip", remoteIp);
		options.addPart("challenge", recaptchaChallenge);
		options.addPart("response", recaptchaResponse);
		options.setPost(true);

		String message = null;

		try {
			message = HttpUtil.URLtoString(options);
		}
		catch (IOException e) {
			_log.error("Error requestion recaptcha message", e);

			throw new CaptchaTextException();
		}

		if (message == null) {
			_log.error("Answer from reCaptcha is null");

			throw new CaptchaTextException();
		}

		String[] messages = message.split("\r?\n");

		if (messages.length < 1) {
			_log.error("Invalid answer from reCaptcha : " + message);

			throw new CaptchaTextException();
		}

		if (!GetterUtil.getBoolean(messages[0])) {
			throw new CaptchaTextException();
		}
	}

	protected void checkSimpleCaptcha(PortletRequest portletRequest)
		throws CaptchaTextException {

		if (!isEnabled(portletRequest)) {
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

	protected void checkSimpleCaptcha(HttpServletRequest request)
		throws CaptchaTextException {

		if (!isEnabled(request)) {
			return;
		}

		HttpSession session = request.getSession();

		String captchaText = (String)session.getAttribute(WebKeys.CAPTCHA_TEXT);

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

		session.removeAttribute(WebKeys.CAPTCHA_TEXT);

		if ((PropsValues.CAPTCHA_MAX_CHALLENGES > 0) &&
			(Validator.isNotNull(request.getRemoteUser()))) {

			Integer count = (Integer)session.getAttribute(
				WebKeys.CAPTCHA_COUNT);

			if (count == null) {
				count = new Integer(1);
			}
			else {
				count = new Integer(count.intValue() + 1);
			}

			session.setAttribute(WebKeys.CAPTCHA_COUNT, count);
		}
	}

	private static Log _log = LogFactoryUtil.getLog(CaptchaImpl.class);

	private Configuration _configuration;
	private CaptchaProducer _captchaProducer;

}
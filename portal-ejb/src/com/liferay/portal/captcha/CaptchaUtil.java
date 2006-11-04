/**
 * Copyright (c) 2000-2006 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.util.PropsUtil;
import com.liferay.portal.util.WebKeys;
import com.liferay.util.GetterUtil;
import com.liferay.util.ParamUtil;

import javax.portlet.PortletRequest;
import javax.portlet.PortletSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <a href="CaptchaUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class CaptchaUtil {

	public static void check(PortletRequest req) throws CaptchaTextException {
		if (isEnabled(req)) {
			PortletSession ses = req.getPortletSession();

			String captchaText = (String)ses.getAttribute(WebKeys.CAPTCHA_TEXT);

			// Captcha should never be null, but on the rare occasion it is,
			// just let people register.

			if (captchaText != null) {
				if (!captchaText.equals(
						ParamUtil.getString(req, "captchaText"))) {

					throw new CaptchaTextException();
				}
				else {
					if (_log.isDebugEnabled()) {
						_log.debug("Captcha text is valid");
					}

					int captchaMaxChallenges = GetterUtil.getInteger(
						PropsUtil.get(PropsUtil.CAPTCHA_MAX_CHALLENGES));

					if (captchaMaxChallenges > 0) {
						Integer count = (Integer)ses.getAttribute(
							WebKeys.CAPTCHA_COUNT);

						if (count == null) {
							count = new Integer(1);
						}
						else {
							count = new Integer(count.intValue() + 1);
						}

						ses.setAttribute(WebKeys.CAPTCHA_COUNT, count);
					}
				}
			}
			else {
				if (_log.isErrorEnabled()) {
					_log.error("Captcha text is null");
				}
			}
		}
	}

	public static boolean isEnabled(PortletRequest req) {
		int captchaMaxChallenges = GetterUtil.getInteger(
			PropsUtil.get(PropsUtil.CAPTCHA_MAX_CHALLENGES));

		if (captchaMaxChallenges > 0) {
			PortletSession ses = req.getPortletSession();

			Integer count = (Integer)ses.getAttribute(WebKeys.CAPTCHA_COUNT);

			if ((count != null) && (captchaMaxChallenges <= count.intValue())) {
				return false;
			}
			else {
				return true;
			}
		}
		else if (captchaMaxChallenges < 0) {
			return false;
		}
		else {
			return true;
		}
	}

	private static Log _log = LogFactory.getLog(CaptchaUtil.class);

}
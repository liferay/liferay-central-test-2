/**
 * Copyright (c) 2000-2007 Liferay, Inc. All rights reserved.
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

package com.liferay.util.bridges.jsf.common;

import com.liferay.portal.kernel.language.LanguageUtil;

import java.util.Locale;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.FacesContext;

/**
 * <a href="FacesMessageUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * This class provides static convenience methods for creating FacesMessage
 * objects from values in resource bundles, and adding them to the FacesContext
 * either globally, or to individual components only.
 * </p>
 *
 * @author Neil Griffin
 *
 */
public class FacesMessageUtil {

	public static void addComponentMessage(
		String clientId, FacesContext facesContext, Severity severity,
		String message) {

		FacesMessage facesMessage = new FacesMessage(severity, message, null);
		facesContext.addMessage(clientId, facesMessage);
	}

	public static void addComponentMessage(
		String clientId, FacesContext facesContext, Severity severity,
		String message, Object argument) {
		Locale locale = JSFPortletUtil.getLocale(facesContext);
		addComponentMessage(
			clientId, facesContext, locale, severity, message, argument);
	}

	public static void addComponentMessage(
		String clientId, FacesContext facesContext, Locale locale,
		Severity severity, String message, Object argument) {
		String formattedMessage = LanguageUtil.format(
				locale, message, argument);
		addComponentMessage(clientId, facesContext, severity, formattedMessage);
	}

	public static void addComponentMessage(
		String clientId, FacesContext facesContext, Locale locale,
		Severity severity, String message, Object[] arguments) {
		String formattedMessage = LanguageUtil.format(
				locale, message, arguments);
		addComponentMessage(clientId, facesContext, severity, formattedMessage);
	}

	public static void addComponentMessageFromLiferay(
		String clientId, FacesContext facesContext, Severity severity,
		String key) {

		Locale locale = JSFPortletUtil.getLocale(facesContext);
		String message = LanguageUtil.get(locale, key);
		addComponentMessage(clientId, facesContext, severity, message);
	}

	public static void addComponentMessageFromLiferay(
		String clientId, FacesContext facesContext, Severity severity,
		String key, Object argument) {

		Locale locale = JSFPortletUtil.getLocale(facesContext);
		String message = LanguageUtil.get(locale, key);
		addComponentMessage(
			clientId, facesContext, locale, severity, message, argument);
	}

	public static void addComponentMessageFromLiferay(
		String clientId, FacesContext facesContext, Severity severity,
		String key, Object[] arguments) {

		Locale locale = JSFPortletUtil.getLocale(facesContext);
		String message = LanguageUtil.get(locale, key);
		addComponentMessage(
			clientId, facesContext, locale, severity, message, arguments);
	}

	public static void addGlobalMessage(
		FacesContext facesContext, Severity severity, String message) {

		FacesMessage facesMessage = new FacesMessage(severity, message, null);
		facesContext.addMessage(null, facesMessage);
	}

	public static void addGlobalMessage(
		FacesContext facesContext, Severity severity, String message,
		Object argument) {
		Locale locale = JSFPortletUtil.getLocale(facesContext);
		addGlobalMessage(facesContext, locale, severity, message, argument);
	}

	public static void addGlobalMessage(
		FacesContext facesContext, Locale locale, Severity severity,
		String message, Object argument) {
		String formattedMessage = LanguageUtil.format(
				locale, message, argument);
		addGlobalMessage(facesContext, severity, formattedMessage);
	}

	public static void addGlobalMessage(
		FacesContext facesContext, Locale locale, Severity severity,
		String message, Object[] arguments) {
		String formattedMessage = LanguageUtil.format(
				locale, message, arguments);
		addGlobalMessage(facesContext, severity, formattedMessage);
	}

	public static void addGlobalMessageFromLiferay(
		FacesContext facesContext, Severity severity, String key) {

		Locale locale = JSFPortletUtil.getLocale(facesContext);
		String message = LanguageUtil.get(locale, key);
		addGlobalMessage(facesContext, severity, message);
	}

	public static void addGlobalMessageFromLiferay(
		FacesContext facesContext, Severity severity, String key,
		Object argument) {

		Locale locale = JSFPortletUtil.getLocale(facesContext);
		String message = LanguageUtil.get(locale, key);
		addGlobalMessage(facesContext, locale, severity, message, argument);
	}

	public static void addGlobalMessageFromLiferay(
		FacesContext facesContext, Severity severity, String key,
		Object[] arguments) {

		Locale locale = JSFPortletUtil.getLocale(facesContext);
		String message = LanguageUtil.get(locale, key);
		addGlobalMessage(facesContext, locale, severity, message, arguments);
	}

}

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

package com.liferay.portlet.social.service;


/**
 * <a href="SocialRequestInterpreterLocalServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class is a wrapper for {@link SocialRequestInterpreterLocalService}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       SocialRequestInterpreterLocalService
 * @generated
 */
public class SocialRequestInterpreterLocalServiceWrapper
	implements SocialRequestInterpreterLocalService {
	public SocialRequestInterpreterLocalServiceWrapper(
		SocialRequestInterpreterLocalService socialRequestInterpreterLocalService) {
		_socialRequestInterpreterLocalService = socialRequestInterpreterLocalService;
	}

	public void addRequestInterpreter(
		com.liferay.portlet.social.model.SocialRequestInterpreter requestInterpreter) {
		_socialRequestInterpreterLocalService.addRequestInterpreter(requestInterpreter);
	}

	public void deleteRequestInterpreter(
		com.liferay.portlet.social.model.SocialRequestInterpreter requestInterpreter) {
		_socialRequestInterpreterLocalService.deleteRequestInterpreter(requestInterpreter);
	}

	public com.liferay.portlet.social.model.SocialRequestFeedEntry interpret(
		com.liferay.portlet.social.model.SocialRequest request,
		com.liferay.portal.theme.ThemeDisplay themeDisplay) {
		return _socialRequestInterpreterLocalService.interpret(request,
			themeDisplay);
	}

	public void processConfirmation(
		com.liferay.portlet.social.model.SocialRequest request,
		com.liferay.portal.theme.ThemeDisplay themeDisplay) {
		_socialRequestInterpreterLocalService.processConfirmation(request,
			themeDisplay);
	}

	public void processRejection(
		com.liferay.portlet.social.model.SocialRequest request,
		com.liferay.portal.theme.ThemeDisplay themeDisplay) {
		_socialRequestInterpreterLocalService.processRejection(request,
			themeDisplay);
	}

	public SocialRequestInterpreterLocalService getWrappedSocialRequestInterpreterLocalService() {
		return _socialRequestInterpreterLocalService;
	}

	private SocialRequestInterpreterLocalService _socialRequestInterpreterLocalService;
}
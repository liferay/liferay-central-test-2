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

package com.liferay.portal.ccpp;

import com.sun.ccpp.ProfileFactoryImpl;

import javax.ccpp.Profile;
import javax.ccpp.ProfileFactory;
import javax.ccpp.ValidationMode;

import javax.servlet.http.HttpServletRequest;

/**
 * <a href="PortalProfileFactory.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class PortalProfileFactory {

	public static Profile getCCPPProfile(HttpServletRequest request) {
		ProfileFactory profileFactory = ProfileFactory.getInstance();

		if (profileFactory == null) {
			profileFactory = ProfileFactoryImpl.getInstance();

			ProfileFactory.setInstance(profileFactory);
		}

		Profile profile = profileFactory.newProfile(
			request, ValidationMode.VALIDATIONMODE_NONE);

		if (profile == null) {
			profile = _EMPTY_PROFILE;
		}

		return profile;
	}

	private static final Profile _EMPTY_PROFILE = new EmptyProfile();

}
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

/**
 * The contents of this file are subject to the terms of the Common Development
 * and Distribution License (the License). You may not use this file except in
 * compliance with the License.
 *
 * You can obtain a copy of the License at http://www.sun.com/cddl/cddl.html and
 * legal/CDDLv1.0.txt. See the License for the specific language governing
 * permission and limitations under the License.
 *
 * When distributing Covered Code, include this CDDL Header Notice in each file
 * and include the License file at legal/CDDLv1.0.txt.
 *
 * If applicable, add the following below the CDDL Header, with the fields
 * enclosed by brackets [] replaced by your own identifying information:
 * "Portions Copyrighted [year] [name of copyright owner]"
 *
 * Copyright 2009 Sun Microsystems Inc. All rights reserved.
 */

package com.liferay.portal.security.permission.basicrulesengine;

import java.util.regex.Matcher;

/**
 * <a href="MatchesRule.java.html"><b><i>View Source</i></b></a>
 *
 * @author Prashant Dighe
 * @author Brian Wing Shun Chan
 *
 */
public class MatchesRule implements RenderRule {

	public MatchesRule(RenderRule rule1, RenderRule rule2) {
		_rule1 = rule1;
		_rule2 = rule2;
	}

	public String getRuleString() {
		throw new UnsupportedOperationException("Invalid operation");
	}

	public boolean isRenderable(long userId) {

		boolean render = false;

		String name = _rule1.getRuleString();
		String value2 = _rule2.getRuleString();

		if (name.startsWith(UserAttributesUtil.USER_ATTR_PREFIX)) {

			String wildcard = Matcher.quoteReplacement(".*");

			boolean regex = false;

			if (value2.contains("*")) {
				regex = true;
				value2 = value2.replaceAll("\\*", wildcard);
			}
			
			String value1 = UserAttributesUtil.getAttribute(userId, name);

			if (regex && value1.matches(value2)) {
				render = true;
			}
			else if (value1.equalsIgnoreCase(value2)) {
				render = true;
			}
		}
		else if (name.equals(DateTimeUtil.DATE_ATTR)) {

			String timeZoneId = UserAttributesUtil.getTimeZoneId(userId);

			int result = DateTimeUtil.compareDateWithToday(timeZoneId, value2);

			if (result == 0) {
				render = true;
			}
		}
		else if (name.equals(DateTimeUtil.TIME_ATTR)) {

			String timeZoneId = UserAttributesUtil.getTimeZoneId(userId);

			int result = DateTimeUtil.compareTimeWithNow(timeZoneId, value2);

			if (result == 0) {
				render = true;
			}
		}
		else if (name.equals(DateTimeUtil.DAY_ATTR)) {

			String timeZoneId = UserAttributesUtil.getTimeZoneId(userId);

			int result = DateTimeUtil.compareDayWithToday(timeZoneId, value2);

			if (result == 0) {
				render = true;
			}

		}

		return render;
	}

	private RenderRule _rule1, _rule2;
}

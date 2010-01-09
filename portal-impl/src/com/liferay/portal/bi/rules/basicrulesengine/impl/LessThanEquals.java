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

package com.liferay.portal.bi.rules.basicrulesengine.impl;

import com.liferay.portal.bi.rules.basicrulesengine.BasicFact;

/**
 * <a href="LessThanEquals.java.html"><b><i>View Source</i></b></a>
 *
 * @author Prashant Dighe
 * @author Brian Wing Shun Chan
 *
 */
public class LessThanEquals implements Rule {

	public LessThanEquals(Rule lhs, Rule rhs) {
		_lhs = lhs;
		_rhs = rhs;
	}

	public boolean evaluate(BasicFact basicFact) {
		boolean truthValue = false;

		String name = _lhs.toString();

		String rhsValue = _rhs.toString();

		if (name.startsWith(UserAttributesUtil.USER_ATTRIBUTE_PREFIX)) {
			String lhsValue = UserAttributesUtil.getAttribute(
				basicFact.getUserId(), name);

			try {
				truthValue = (Double.parseDouble(lhsValue) <=
					Double.parseDouble(rhsValue));
			}
			catch (NumberFormatException nfe) {
				truthValue = lhsValue.compareTo(rhsValue) <= 0;
			}
		}
		else if (name.equals(DateTimeUtil.DATE_ATTRIBUTE)) {
			String timeZoneId = UserAttributesUtil.getTimeZoneId(
				basicFact.getUserId());

			int result = DateTimeUtil.compareDateWithToday(
				timeZoneId, rhsValue);

			if (result == 0 || result == -1) {
				truthValue = true;
			}
		}
		else if (name.equals(DateTimeUtil.TIME_ATTRIBUTE)) {
			String timeZoneId = UserAttributesUtil.getTimeZoneId(
				basicFact.getUserId());

			int result = DateTimeUtil.compareTimeWithNow(timeZoneId, rhsValue);

			if (result == 0 || result == -1) {
				truthValue = true;
			}
		}
		else if (name.equals(DateTimeUtil.DAY_ATTRIBUTE)) {
			String timeZoneId = UserAttributesUtil.getTimeZoneId(
				basicFact.getUserId());

			int result = DateTimeUtil.compareDayWithToday(timeZoneId, rhsValue);

			if (result <= 0) {
				truthValue = true;
			}
		}

		return truthValue;
	}

	private Rule _lhs;
	private Rule _rhs;

}
/*
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.liferay.portal.security.auth;

import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

/**
 * <a href="DefaultFullNameGenerator.java.html"><b><i>View Source</i></b></a>
 *
 * @author Michael C. Han
 */
public class DefaultFullNameGenerator implements FullNameGenerator {
	
	public String getFullName(
		String firstName, String middleName, String lastName) {

		StringBuilder sb = new StringBuilder();

		sb.append(firstName);
		sb.append(StringPool.SPACE);

		if (Validator.isNull(middleName)) {
			sb.append(lastName);
		}
		else {
			sb.append(middleName);
			sb.append(StringPool.SPACE);
			sb.append(lastName);
		}

		return sb.toString();
	}

	public String[] splitFullName(String fullName) {
		String firstName = StringPool.BLANK;
		String lastName = StringPool.BLANK;
		String middleName = StringPool.BLANK;

		if (Validator.isNotNull(fullName)) {
			String[] name = StringUtil.split(fullName, " ");

			firstName = name[0];
			lastName = name[name.length - 1];
			middleName = StringPool.BLANK;

			if (name.length > 2) {
				for (int i = 1; i < name.length - 1; i++) {
					if (Validator.isNull(name[i].trim())) {
						continue;
					}

					if (i != 1) {
						middleName += " ";
					}

					middleName += name[i].trim();
				}
			}
		}
		else {
			firstName = GetterUtil.getString(firstName, lastName);
			lastName = firstName;
		}

		return new String[] {firstName, middleName, lastName};
	}
}
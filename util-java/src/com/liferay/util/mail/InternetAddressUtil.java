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

package com.liferay.util.mail;

import com.liferay.portal.kernel.util.StringPool;
import com.liferay.util.Validator;

import java.util.ArrayList;
import java.util.List;

import javax.mail.Address;
import javax.mail.internet.InternetAddress;

/**
 * <a href="InternetAddressUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Alexander Chow
 *
 */
public class InternetAddressUtil {

	public static boolean contains(
		InternetAddress[] internetAddresses, String emailAddress) {

		if ((internetAddresses != null) && Validator.isNotNull(emailAddress)) {
			for (int i = 0; i < internetAddresses.length; i++) {
				if (emailAddress.equals(internetAddresses[i].getAddress())) {
					return true;
				}
			}
		}

		return false;
	}

	public static InternetAddress[] removeEntry(
		Address[] addresses, String emailAddress) {

		InternetAddress[] internetAddresses = (InternetAddress[])addresses;

		List list = new ArrayList();

		if ((internetAddresses == null) || Validator.isNull(emailAddress)) {
			return internetAddresses;
		}

		for (int i = 0; i < internetAddresses.length; i++) {
			if (!emailAddress.equals(internetAddresses[i].getAddress())) {
				list.add(internetAddresses[i]);
			}
		}

		return (InternetAddress[])list.toArray(new InternetAddress[0]);
	}

	public static String toString(Address[] addresses) {
		InternetAddress[] internetAddresses = (InternetAddress[])addresses;

		StringBuffer sb = new StringBuffer();

		if (internetAddresses != null) {
			for (int i = 0; i < internetAddresses.length; i++) {
				sb.append(internetAddresses[i].toUnicodeString());

				if (i < internetAddresses.length - 1) {
					sb.append(StringPool.COMMA + StringPool.NBSP);
				}
			}
		}

		return sb.toString();
	}

}
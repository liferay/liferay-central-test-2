/**
 * Copyright (c) 2000-2006 Liferay, LLC. All rights reserved.
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

import com.liferay.util.StringPool;
import com.liferay.util.Validator;

import java.io.UnsupportedEncodingException;

import java.util.ArrayList;
import java.util.List;

import javax.mail.Address;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

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

	public static InternetAddress getAddress(String entry)
		throws AddressException, UnsupportedEncodingException {

		InternetAddress internetAddress = new InternetAddress();

		String[] parts = entry.split(StringPool.LESS_THAN);

		if (parts.length == 2) {
			String name = parts[0].trim();

			if (name.endsWith(StringPool.QUOTE) ||
				name.endsWith(StringPool.APOSTROPHE)) {

				name = name.substring(0, name.length() - 1);
			}

			if (name.startsWith(StringPool.QUOTE) ||
				name.startsWith(StringPool.APOSTROPHE)) {

				name = name.substring(1);
			}

			String address = parts[1].trim();

			if (address.endsWith(StringPool.GREATER_THAN)) {
				address = address.substring(0, address.length() - 1);
			}

			if (!Validator.isAddress(address)) {
				if (_log.isErrorEnabled()) {
					_log.error("Invalid email address " + address);
				}

				throw new AddressException();
			}

			internetAddress.setAddress(address.trim());
			internetAddress.setPersonal(name.trim());
		}
		else if (parts.length == 1) {
			if (!Validator.isAddress(parts[0])) {
				if (_log.isErrorEnabled()) {
					_log.error("Invalid email address " + parts[0]);
				}

				throw new AddressException();
			}

			internetAddress.setAddress(parts[0].trim());
		}
		else {
			if (_log.isErrorEnabled()) {
				_log.error("Invalid email address " + entry);
			}

			throw new AddressException();
		}

		return internetAddress;
	}

	public static InternetAddress[] getAddresses(String s)
		throws AddressException, UnsupportedEncodingException {

		List list = new ArrayList();

		String[] entries = s.split(",|;");

		for (int i = 0; i < entries.length; i++) {
			String entry = entries[i].trim();

			if (Validator.isNotNull(entry)) {
				list.add(getAddress(entry));
			}
		}

		return (InternetAddress[])list.toArray(new InternetAddress[0]);
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
					sb.append(StringPool.COMMA + StringPool.BLANK);
				}
			}
		}

		return sb.toString();
	}

	private static Log _log = LogFactory.getLog(InternetAddressUtil.class);

}
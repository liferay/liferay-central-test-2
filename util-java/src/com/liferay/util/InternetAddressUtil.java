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
package com.liferay.util;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.mail.Address;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.liferay.util.StringPool;
import com.liferay.util.Validator;

/**
 * <a href="InternetAddressUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Alexander Chow
 *
 */
public class InternetAddressUtil {

	public static boolean contains(
			InternetAddress [] ias, String emailAddress) {

		if (Validator.isNotNull(emailAddress) && ias != null) {
			for (int i = 0; i < ias.length; i++) {
				if (emailAddress.equals(ias[i].getAddress())) {
					return true;
				}
			}
		}

		return false;
	}
	
	public static InternetAddress [] getAddresses(String mailingList)
		throws Exception {
		
		String [] entries = mailingList.split(",|;");

		List addresses = new ArrayList();
		for (int i = 0; i < entries.length; i++) {
			String entry = entries[i].trim();
			if (Validator.isNotNull(entry)) {
				addresses.add(getAddress(entry));
			}
		}

		return (InternetAddress [])addresses.toArray(new InternetAddress [] {});
	}

	public static InternetAddress getAddress(String entry)
		throws AddressException, UnsupportedEncodingException {

		InternetAddress ia = new InternetAddress();

		String [] parts = entry.split(StringPool.LESS_THAN);

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
				_log.error("Invalid email address " + address);

				throw new AddressException();
			}

			ia.setAddress(address.trim());
			ia.setPersonal(name.trim());
		}
		else if (parts.length == 1) {
			if (!Validator.isAddress(parts[0])) {
				_log.error("Invalid email address " + parts[0]);

				throw new AddressException();
			}
			ia.setAddress(parts[0].trim());
		}
		else {
			_log.error("Invalid email address " + entry);

			throw new AddressException();
		}

		return ia;
	}

	public static InternetAddress [] removeEntry(
		Address [] as, String emailAddress) {

		InternetAddress [] ias = (InternetAddress [])as;

		List list = new ArrayList();

		if (Validator.isNull(emailAddress) || ias == null) {
			return ias;
		}
		
		for (int i = 0; i < ias.length; i++) {
			if (!emailAddress.equals(ias[i].getAddress())) {
				list.add(ias[i]);
			}
		}
		
		return (InternetAddress [])list.toArray(new InternetAddress [] {});
	}

	public static String toString(Address [] as) {
		InternetAddress [] ias = (InternetAddress [])as;

		StringBuffer sb = new StringBuffer();
		
		if (ias != null) {
			for (int i = 0; i < ias.length; i++) {
				sb.append(ias[i].toUnicodeString());
				
				if (i < ias.length - 1) {
					sb.append(StringPool.COMMA + StringPool.BLANK);
				}
			}
		}

		return sb.toString();
	}
	
	private static Log _log = LogFactory.getLog(InternetAddressUtil.class);

}
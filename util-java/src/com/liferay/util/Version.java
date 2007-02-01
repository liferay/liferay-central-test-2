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

package com.liferay.util;

import java.util.StringTokenizer;

/**
 * <a href="Version.java.html"><b><i>View Source</i></b></a>
 *
 * @author Jorge Ferrer
 */
public class Version implements Comparable {

	public Version(
			String major, String minor, String bugFix, String buildNumber) {
		_major = major;
		_minor = minor;
		_bugFix = bugFix;
		_buildNumber = buildNumber;
	}

	public Version(String version) {
		StringTokenizer st = new StringTokenizer(version, SEPARATOR);

		_major = st.nextToken();

		if (st.hasMoreTokens()) {
			_minor = st.nextToken();
		}

		if (st.hasMoreTokens()) {
			_bugFix = st.nextToken();
		}

		StringBuffer buildNumber = new StringBuffer();

		while (st.hasMoreTokens()) {
			buildNumber.append(st.nextToken());
			if (st.hasMoreTokens()) {
				buildNumber.append(SEPARATOR);
			}
		}

		_buildNumber = buildNumber.toString();
	}

	public String getMajor() {
		return _major;
	}

	public String getMinor() {
		return _minor;
	}

	public String getBugFix() {
		return _bugFix;
	}

	public String getBuildNumber() {
		return _buildNumber;
	}

	public boolean isLaterVersionThan(String version) {
		if (compareTo(new Version(version)) > 0) {
			return true;
		} else {
			return false;
		}
	}

	public boolean includes(Version version) {
		if (equals(version)) {
			return true;
		}

		if (getMajor().equals(version.getMajor())) {
			if (getMinor().equals("*")) {
				return true;
			}

			if (getMinor().equals(version.getMinor())) {
				if (getBugFix().equals("*")) {
					return true;
				}

				if (getBugFix().equals(version.getBugFix())) {
					if ((getBuildNumber().equals("*")) ||
							getBuildNumber().equals(version.getBuildNumber())) {
						return true;
					}
				}
			}
		}

		return false;
	}

	public String toString() {
		StringBuffer result = new StringBuffer();

		result.append(_major);

		if (Validator.isNotNull(_minor)) {
			result.append(SEPARATOR);
			result.append(_minor);

			if (Validator.isNotNull(_bugFix)) {
				result.append(SEPARATOR);
				result.append(_bugFix);

				if (Validator.isNotNull(_buildNumber)) {
					result.append(SEPARATOR);
					result.append(_buildNumber);
				}
			}
		}

		return result.toString();
	}

	public int compareTo(Object o) {
		if ((o == null) || (!(o instanceof Version))) {
			return -1;
		}

		Version pv = (Version) o;

		int result = _major.compareTo(pv.getMajor());
		if (result != 0) {
			return result;
		}

		result = _minor.compareTo(pv.getMinor());
		if (result != 0) {
			return result;
		}

		result = _bugFix.compareTo(pv.getBugFix());
		if (result != 0) {
			return result;
		}

		return _buildNumber.compareTo(pv.getBuildNumber());
	}

	public boolean equals(Object o) {
		if ((o == null) || (!(o instanceof Version))) {
			return false;
		}

		Version pv = (Version) o;
		return toString().equals(pv.toString());
	}

	public int hashCode() {
		return toString().hashCode();
	}

	private String _major;
	private String _minor;
	private String _bugFix;
	private String _buildNumber;

	private static final String SEPARATOR = ".";

}


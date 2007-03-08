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

package com.liferay.util;

import com.liferay.portal.kernel.util.StringMaker;
import com.liferay.portal.kernel.util.StringPool;

import java.util.Map;
import java.util.StringTokenizer;

/**
 * <a href="Version.java.html"><b><i>View Source</i></b></a>
 *
 * @author Jorge Ferrer
 *
 */
public class Version implements Comparable {

	public static final String UNKNOWN = "unknown";

	public static Version getInstance(String version) {
		Version versionObj = (Version)_versions.get(version);

		if (versionObj == null) {
			versionObj =  new Version(version);

			_versions.put(version, versionObj);
		}

		return versionObj;
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
		if (compareTo(getInstance(version)) > 0) {
			return true;
		}
		else {
			return false;
		}
	}

	public boolean isPreviousVersionThan(String version) {
		if (compareTo(getInstance(version)) < 0) {
			return true;
		}
		else {
			return false;
		}
	}

	public boolean isSameVersionAs(String version) {
		if (compareTo(getInstance(version)) == 0) {
			return true;
		}
		else {
			return false;
		}
	}

	public boolean includes(Version version) {
		if (equals(version)) {
			return true;
		}

		if (getMajor().equals(version.getMajor())) {
			if (getMinor().equals(StringPool.STAR)) {
				return true;
			}

			if (getMinor().equals(version.getMinor())) {
				if (getBugFix().equals(StringPool.STAR)) {
					return true;
				}

				if (getBugFix().equals(version.getBugFix())) {
					if (getBuildNumber().equals(StringPool.STAR) ||
						getBuildNumber().equals(version.getBuildNumber())) {

						return true;
					}
				}
			}
		}

		return false;
	}

	public String toString() {
		StringMaker sm = new StringMaker();

		sm.append(_major);

		if (Validator.isNotNull(_minor)) {
			sm.append(_SEPARATOR);
			sm.append(_minor);

			if (Validator.isNotNull(_bugFix)) {
				sm.append(_SEPARATOR);
				sm.append(_bugFix);

				if (Validator.isNotNull(_buildNumber)) {
					sm.append(_SEPARATOR);
					sm.append(_buildNumber);
				}
			}
		}

		return sm.toString();
	}

	public int compareTo(Object obj) {
		if ((obj == null) || (!(obj instanceof Version))) {
			return 1;
		}

		Version version = (Version)obj;

		// Unknown is always considered a lower version

		if (version.toString().equals(UNKNOWN)) {
			return 1;
		}

		if (toString().equals(UNKNOWN)) {
			return -1;
		}

		int result = _compareVersionFragments(_major, version.getMajor());

		if (result != 0) {
			return result;
		}

		result = _compareVersionFragments(_minor, version.getMinor());

		if (result != 0) {
			return result;
		}

		result = _compareVersionFragments(_bugFix, version.getBugFix());

		if (result != 0) {
			return result;
		}

		return _compareVersionFragments(_buildNumber, version.getBuildNumber());
	}

	public boolean equals(Object obj) {
		if ((obj == null) || (!(obj instanceof Version))) {
			return false;
		}

		Version version = (Version)obj;

		if (version.toString().equals(UNKNOWN) ||
			(toString().equals(UNKNOWN))) {
			return false;
		}

		return toString().equals(version.toString());
	}

	public int hashCode() {
		return toString().hashCode();
	}

	protected Version(String version) {
		StringTokenizer st = new StringTokenizer(version, _SEPARATOR);

		_major = st.nextToken();

		if (st.hasMoreTokens()) {
			_minor = st.nextToken();
		}

		if (st.hasMoreTokens()) {
			_bugFix = st.nextToken();
		}

		StringMaker buildNumber = new StringMaker();

		while (st.hasMoreTokens()) {
			buildNumber.append(st.nextToken());

			if (st.hasMoreTokens()) {
				buildNumber.append(_SEPARATOR);
			}
		}

		_buildNumber = buildNumber.toString();
	}

	private int _compareVersionFragments(
		String versionFragmentA, String versionFragmentB) {

		if (Validator.isNull(versionFragmentA)) {
			versionFragmentA = "0";
		}

		if (Validator.isNull(versionFragmentB)) {
			versionFragmentB = "0";
		}

		return versionFragmentA.compareTo(versionFragmentB);
	}

	private static final String _SEPARATOR = StringPool.PERIOD;

	private static Map _versions = CollectionFactory.getSyncHashMap();

	private String _major;
	private String _minor;
	private String _bugFix;
	private String _buildNumber;

}
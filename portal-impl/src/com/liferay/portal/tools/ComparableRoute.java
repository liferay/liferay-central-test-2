/**
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
 */

package com.liferay.portal.tools;

import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 * A dummy container for friendly URL route data used by source formatter.
 * Implements the comparable interface to sort routes by increasing generality.
 *
 * @author Connor McKay
 */
public class ComparableRoute implements Comparable<ComparableRoute> {
	public static boolean hasRegex(String fragment) {
		return (fragment.indexOf(":") != -1);
	}

	public static boolean isCaptureFragment(String fragment) {
		return (fragment.indexOf("{") != -1);
	}

	public static boolean isMatchAny(String fragment) {
		return fragment.matches(".*\\{.+?:\\.\\*\\}.*");
	}

	public ComparableRoute(String pattern) {
		_pattern = pattern;
	}

	public void addGeneratedParameter(String name, String pattern) {
		_generatedParameters.put(name, pattern);
	}

	public void addIgnoredParameter(String name) {
		_ignoredParameters.add(name);
	}

	public void addImplicitParameter(String name, String value) {
		_implicitParameters.put(name, value);
	}

	public void addOverriddenParameter(String name, String value) {
		_overriddenParameters.put(name, value);
	}

	public int compareTo(ComparableRoute r) {
		// Don't split on .*

		String[] myFragments = _pattern.split("[/\\.](?!\\*)");
		String[] theirFragments = r.getPattern().split("[/\\.](?!\\*)");

		int i;

		for (i = 0; i < myFragments.length && i < theirFragments.length; i++) {
			String myFragment = myFragments[i];
			String theirFragment = theirFragments[i];

			// Capture fragments are more general than static ones

			boolean iCapture = isCaptureFragment(myFragment);
			boolean theyCapture = isCaptureFragment(theirFragment);

			if (theyCapture && !iCapture) {
				return -1;
			}

			if (iCapture && !theyCapture) {
				return 1;
			}

			// A fragment matching .* is more general than anything

			boolean iMatchAny = isMatchAny(myFragment);
			boolean theyMatchAny = isMatchAny(theirFragment);

			if (theyMatchAny && !iMatchAny) {
				return -1;
			}

			if (iMatchAny && !theyMatchAny) {
				return 1;
			}

			// Not having a regex is more general than having a custom one

			boolean iRegex = hasRegex(myFragment);
			boolean theyRegex = hasRegex(theirFragment);

			if (iRegex && !theyRegex) {
				return -1;
			}

			if (theyRegex && !iRegex) {
				return 1;
			}
		}

		// Having more fragments is more general

		if (i < theirFragments.length && i >= myFragments.length) {
			return -1;
		}

		if (i < myFragments.length && i >= theirFragments.length) {
			return 1;
		}

		// Having fewer implicit parameters is more general

		int myImplicitCount = _implicitParameters.size();
		int theirImplicitCount = r.getImplicitParameters().size();

		if (myImplicitCount > theirImplicitCount) {
			return -1;
		}

		if (theirImplicitCount > myImplicitCount) {
			return 1;
		}

		return _pattern.compareTo(r.getPattern());
	}

	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof ComparableRoute)) {
			return false;
		}

		ComparableRoute r = (ComparableRoute)obj;

		return (compareTo(r) == 0);
	}

	public Map<String, String> getGeneratedParameters() {
		return _generatedParameters;
	}

	public Set<String> getIgnoredParameters() {
		return _ignoredParameters;
	}

	public Map<String, String> getImplicitParameters() {
		return _implicitParameters;
	}

	public Map<String, String> getOverriddenParameters() {
		return _overriddenParameters;
	}

	public String getPattern() {
		return _pattern;
	}

	private Map<String, String> _generatedParameters =
		new TreeMap<String, String>();
	private Set<String> _ignoredParameters = new TreeSet<String>();
	private Map<String, String> _implicitParameters =
		new TreeMap<String, String>();
	private String _pattern;
	private Map<String, String> _overriddenParameters =
		new TreeMap<String, String>();

}
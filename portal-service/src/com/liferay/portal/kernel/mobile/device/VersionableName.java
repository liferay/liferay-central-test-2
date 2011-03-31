/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.kernel.mobile.device;

import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.Validator;

import java.io.Serializable;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

/**
 * Represents versionable name (a name and a set of versions)
 *
 * @author Milen Dyankov
 * @author Michael C. Han
 */
public class VersionableName
	implements Comparable<VersionableName>, Serializable {

	public VersionableName(String name, Set<String> versions) {

		if (Validator.isNull(name)) {
			throw new IllegalArgumentException(
				"Can not create VersionableName without name!");
		}

		_name = name;
		_versions = versions;
	}

	public VersionableName(String name, String version) {
		this(name, new HashSet<String>());

		_versions.add(version);
	}

	public VersionableName(String name) {
		this(name, (Set<String>) null);
	}

	public void addVersion(String version) {
		if (version == null) {
			return;
		}

		if (_versions == null) {
			_versions = new TreeSet<String>();
		}

		_versions.add(version);
	}

	public int compareTo(VersionableName versionableName) {
		return _name.toUpperCase().compareTo(
			versionableName.getName().toUpperCase());
	}

	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if ((object == null) || (getClass() != object.getClass())) {
			return false;
		}

		VersionableName versionableName = (VersionableName) object;

		return _name.equals(versionableName.getName());
	}

	public String getName() {
		return _name;
	}

	public Set<String> getVersions() {
		if (_versions == null) {
			return Collections.emptySet();
		}

		return Collections.unmodifiableSet(_versions);
	}

	public int hashCode() {
		return _name != null ? _name.hashCode() : 0;
	}

	public String toString() {
		StringBundler sb = new StringBundler(5);

		sb.append("{name=");
		sb.append(_name);
		sb.append(", versions=");
		sb.append(_versions);
		sb.append("}");

		return sb.toString();
	}

	private String _name;
	private Set<String> _versions;

}
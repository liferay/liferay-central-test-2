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

package com.liferay.portal.security.ntlm.msrpc;

import jcifs.dcerpc.ndr.NdrBuffer;
import jcifs.dcerpc.ndr.NdrException;
import jcifs.dcerpc.ndr.NdrObject;

/**
 * <a href="GroupMembership.java.html"><b><i>View Source</i></b></a>
 *
 * @author Marcellus Tavares
 */
public class GroupMembership extends NdrObject {

	public GroupMembership() {

	}

	public GroupMembership(int relativeId, int attributes) {
		_relativeId = relativeId;
		_attributes = attributes;
	}

	public void decode(NdrBuffer buffer) throws NdrException {
		buffer.align(4);

		_relativeId = buffer.dec_ndr_long();
		_attributes = buffer.dec_ndr_long();
	}

	public void encode(NdrBuffer buffer) throws NdrException {
		buffer.align(4);

		buffer.enc_ndr_long(_relativeId);
		buffer.enc_ndr_long(_attributes);
	}

	public int getRelativeId() {
		return _relativeId;
	}

	public int getAttributes() {
		return _attributes;
	}

	private int _relativeId;
	private int _attributes;

}
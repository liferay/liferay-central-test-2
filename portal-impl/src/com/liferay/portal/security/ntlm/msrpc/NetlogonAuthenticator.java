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
 * <a href="NetlogonAuthenticator.java.html"><b><i>View Source</i></b></a>
 *
 * @author Marcellus Tavares
 */
public class NetlogonAuthenticator extends NdrObject {

	public NetlogonAuthenticator() {
		_credential = new byte[8];
	}

	public NetlogonAuthenticator(
		byte[] credential, int timestamp) {

		_credential = credential;
		_timestamp = timestamp;
	}

	public void decode(NdrBuffer buffer) throws NdrException {
		buffer.align(4);

		int index = buffer.index;
		buffer.advance(8);

		_timestamp = buffer.dec_ndr_long();

		buffer = buffer.derive(index);

		for (int i = 0; i < 8; i++) {
			_credential[i] = (byte) buffer.dec_ndr_small();
		}
	}

	public void encode(NdrBuffer buffer) throws NdrException {
		buffer.align(4);

		int index = buffer.index;

		buffer.advance(8);
		buffer.enc_ndr_long(_timestamp);
		buffer = buffer.derive(index);

		for (int i = 0; i < 8; i++) {
			buffer.enc_ndr_small(_credential[i]);
		}
	}

	private byte[] _credential;
	private int _timestamp;

}
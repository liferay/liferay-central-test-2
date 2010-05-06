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

import jcifs.dcerpc.DcerpcMessage;
import jcifs.dcerpc.ndr.NdrBuffer;
import jcifs.dcerpc.ndr.NdrException;

/**
 * <a href="NetrServerReqChallenge.java.html"><b><i>View Source</i></b></a>
 *
 * @author Marcellus Tavares
 */
public class NetrServerReqChallenge extends DcerpcMessage {

	public NetrServerReqChallenge(
		String primaryName, String computerName, byte[] clientChallenge,
		byte[] serverChallenge) {

		_primaryName = primaryName;
		_computerName = computerName;
		_clientChallenge = clientChallenge;
		_serverChallenge = serverChallenge;

		 ptype = 0;
		 flags = DCERPC_FIRST_FRAG | DCERPC_LAST_FRAG;
	}

	public void decode_out(NdrBuffer buffer) throws NdrException {
		int index = buffer.index;
		buffer.advance(8);
		buffer = buffer.derive(index);

		for (int i = 0; i < 8; i++) {
			_serverChallenge[i] = (byte) buffer.dec_ndr_small();
		}

		_status = buffer.dec_ndr_long();
	}

	public void encode_in(NdrBuffer buffer) throws NdrException {
		buffer.enc_ndr_referent(_primaryName, 1);
		buffer.enc_ndr_string(_primaryName);
		buffer.enc_ndr_string(_computerName);

		int index = buffer.index;

		buffer.advance(8);
		buffer = buffer.derive(index);

		for (int i = 0; i < 8; i++) {
			buffer.enc_ndr_small(_clientChallenge[i]);
		}
	}

	public int getOpnum() {
		return 4;
	}

	public byte[] getServerChallenge() {
		return _serverChallenge;
	}

	public int getStatus() {
		return _status;
	}

	private int _status;
	private String _primaryName;
	private String _computerName;
	private byte[] _clientChallenge;
	private byte[] _serverChallenge;

}
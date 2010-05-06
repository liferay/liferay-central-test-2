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
 * <a href="NetrServerAuthenticate3.java.html"><b><i>View Source</i></b></a>
 *
 * @author Marcellus Tavares
 */
public class NetrServerAuthenticate3 extends DcerpcMessage {

	public NetrServerAuthenticate3(
		String primaryName, String accountName,	int secureChannelType,
		String computerName, byte[] clientCredential, byte[] serverCredential,
		int negotiateFlags) {

		_primaryName = primaryName;
		_accountName = accountName;
		_secureChannelType = (short) secureChannelType;
		_computerName = computerName;
		_clientCredential = clientCredential;
		_serverCredential = serverCredential;
		_negotiateFlags = negotiateFlags;

		ptype = 0;
		flags = DCERPC_FIRST_FRAG | DCERPC_LAST_FRAG;
	}

	public void decode_out(NdrBuffer buffer) throws NdrException {
		int index = buffer.index;

		buffer.advance(8);
		buffer = buffer.derive(index);

		for (int i = 0; i < 8; i++) {
			_serverCredential[i] = (byte) buffer.dec_ndr_small();
		}

		_negotiateFlags = buffer.dec_ndr_long();
		_accountRid = buffer.dec_ndr_long();
		_status = buffer.dec_ndr_long();
	}

	public void encode_in(NdrBuffer buffer) throws NdrException {
		NdrBuffer ref = buffer;

		buffer.enc_ndr_referent(_primaryName, 1);
		buffer.enc_ndr_string(_primaryName);
		buffer.enc_ndr_string(_accountName);
		buffer.enc_ndr_short(_secureChannelType);
		buffer.enc_ndr_string(_computerName);

		int index = buffer.index;

		buffer.advance(8);
		buffer = buffer.derive(index);

		for (int i = 0; i < 8; i++) {
			buffer.enc_ndr_small(_clientCredential[i]);
		}

		buffer.enc_ndr_long(_negotiateFlags);

		ref.setIndex(buffer.index);
	}

	public int getOpnum() {
		return 26;
	}

	public int getAccountRid() {
		return _accountRid;
	}

	public int getNegotiatedFlags() {
		return _negotiateFlags;
	}

	public byte[] getServerCredential() {
		return _serverCredential;
	}

	public int getStatus() {
		return _status;
	}

	private int _status;
	private String _primaryName;
	private String _accountName;
	private short _secureChannelType;
	private String _computerName;
	private byte[] _clientCredential;
	private byte[] _serverCredential;
	private int _negotiateFlags;
	private int _accountRid;

}
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
 * <a href="NetrLogonSamLogon.java.html"><b><i>View Source</i></b></a>
 *
 * @author Marcellus Tavares
 */
public class NetrLogonSamLogon extends DcerpcMessage {

	public NetrLogonSamLogon(
		String logonServer, String computerName,
		NetlogonAuthenticator authenticator,
		NetlogonAuthenticator returnAuthenticator,
		int logonLevel, NetlogonNetworkInfo logonInformation,
		int validationLevel, NetlogonValidationSamInfo validationInformation,
		int authoritative) {

		_logonServer = logonServer;
		_computerName = computerName;
		_authenticator = authenticator;
		_returnAuthenticator = returnAuthenticator;
		_logonLevel = (short) logonLevel;
		_logonInformation = logonInformation;
		_validationLevel = (short) validationLevel;
		_validationInformation = validationInformation;
		_authoritative = (byte) authoritative;

		ptype = 0;
		flags = DCERPC_FIRST_FRAG | DCERPC_LAST_FRAG;
	}

	public void decode_out(NdrBuffer buffer) throws NdrException {
		int returnAuthenticator = buffer.dec_ndr_long();

		if (returnAuthenticator > 0) {
			_returnAuthenticator.decode(buffer);
		}

		buffer.dec_ndr_short();

		int validationInformation = buffer.dec_ndr_long();

		if (validationInformation > 0) {
			buffer = buffer.deferred;
			_validationInformation.decode(buffer);
		}

		_authoritative = (byte) buffer.dec_ndr_small();
		_status = buffer.dec_ndr_long();
	}

	public void encode_in(NdrBuffer buffer) throws NdrException {
		buffer.enc_ndr_referent(_logonServer, 1);
		buffer.enc_ndr_string(_logonServer);

		buffer.enc_ndr_referent(_computerName, 1);
		buffer.enc_ndr_string(_computerName);

		buffer.enc_ndr_referent(_authenticator, 1);
		_authenticator.encode(buffer);

		buffer.enc_ndr_referent(_returnAuthenticator, 1);
		_returnAuthenticator.encode(buffer);

		buffer.enc_ndr_short(_logonLevel);
		buffer.enc_ndr_short(_logonLevel);

		buffer.enc_ndr_referent(_logonInformation, 1);
		_logonInformation.encode(buffer);

		buffer.enc_ndr_short(_validationLevel);
	}

	public NetlogonValidationSamInfo getNetlogonValidationSamInfo() {
		return _validationInformation;
	}

	public int getOpnum() {
		return 2;
	}

	public int getStatus() {
		return _status;
	}

	private int _status;
	private String _logonServer;
	private String _computerName;
	private NetlogonAuthenticator _authenticator;
	private NetlogonAuthenticator _returnAuthenticator;
	private short _logonLevel;
	private NetlogonNetworkInfo _logonInformation;
	private short _validationLevel;
	private NetlogonValidationSamInfo _validationInformation;
	private byte _authoritative;

}
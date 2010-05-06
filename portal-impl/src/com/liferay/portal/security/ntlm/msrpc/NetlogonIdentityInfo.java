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

import jcifs.dcerpc.UnicodeString;
import jcifs.dcerpc.ndr.NdrBuffer;
import jcifs.dcerpc.ndr.NdrException;
import jcifs.dcerpc.ndr.NdrObject;
import jcifs.dcerpc.rpc;

/**
 * <a href="NetlogonIdentityInfo.java.html"><b><i>View Source</i></b></a>
 *
 * @author Marcellus Tavares
 */
public class NetlogonIdentityInfo extends NdrObject {

	public NetlogonIdentityInfo(
		String logonDomainName, int parameterControl, int reservedLow,
		int reservedHigh, String userName, String workstation) {

		_logonDomainName = new UnicodeString(logonDomainName, false);
		_parameterControl= parameterControl;
		_reservedLow = reservedLow;
		_reservedHigh = reservedHigh;
		_userName = new UnicodeString(userName, false);;
		_workstation = new UnicodeString(workstation, false);;
	}

	public void decode(NdrBuffer buffer) throws NdrException {
	}

	public void encode(NdrBuffer buffer) throws NdrException {
		buffer.enc_ndr_short(_logonDomainName.length);
		buffer.enc_ndr_short(_logonDomainName.maximum_length);
		buffer.enc_ndr_referent(_logonDomainName.buffer, 1);
		buffer.enc_ndr_long(_parameterControl);
		buffer.enc_ndr_long(_reservedLow);
		buffer.enc_ndr_long(_reservedHigh);
		buffer.enc_ndr_short(_userName.length);
		buffer.enc_ndr_short(_userName.maximum_length);
		buffer.enc_ndr_referent(_userName.buffer, 1);
		buffer.enc_ndr_short(_workstation.length);
		buffer.enc_ndr_short(_workstation.maximum_length);
		buffer.enc_ndr_referent(_workstation.buffer, 1);
	}

	public void encodeLogonDomainName(NdrBuffer buffer) {
		encodeUnicodeString(buffer, _logonDomainName);
	}

	public void encodeUserName(NdrBuffer buffer) {
		encodeUnicodeString(buffer, _userName);
	}

	public void encodeWorkStationName(NdrBuffer buffer) {
		encodeUnicodeString(buffer, _workstation);
	}

	protected void encodeUnicodeString(
		NdrBuffer buffer, rpc.unicode_string string ) {

		buffer = buffer.deferred;

		int stringBufferl = string.length / 2;
		int stringBuffers = string.maximum_length / 2;

		buffer.enc_ndr_long(stringBuffers);
		buffer.enc_ndr_long(0);
		buffer.enc_ndr_long(stringBufferl);

		int stringBufferIndex = buffer.index;

		buffer.advance(2 * stringBufferl);

		buffer = buffer.derive(stringBufferIndex);

		for (int _i = 0; _i < stringBufferl; _i++) {
			buffer.enc_ndr_short(string.buffer[_i]);
		}
	}

	private rpc.unicode_string _logonDomainName;
	private int _parameterControl;
	private int _reservedLow;
	private int _reservedHigh;
	private rpc.unicode_string _userName;
	private rpc.unicode_string _workstation;

}
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
import jcifs.dcerpc.rpc;

/**
 * <a href="NetlogonValidationSamInfo.java.html"><b><i>View Source</i></b></a>
 *
 * @author Marcellus Tavares
 */
public class NetlogonValidationSamInfo extends NdrObject {

	public NetlogonValidationSamInfo() {
		_effectiveName = new rpc.unicode_string();
		_fullName = new rpc.unicode_string();
		_logonScript = new rpc.unicode_string();
		_profilePath = new rpc.unicode_string();
		_homeDirectory = new rpc.unicode_string();
		_homeDirectoryDrive = new rpc.unicode_string();
		_logonServer = new rpc.unicode_string();
		_logonDomainName = new rpc.unicode_string();
		_userSessionKey = new byte[16];
		_logonDomain = new rpc.sid_t();
	}

	public void decode(NdrBuffer buffer) throws NdrException {
		_logonTime = buffer.dec_ndr_hyper();
		_logoffTime = buffer.dec_ndr_hyper();
		_kickoffTime = buffer.dec_ndr_hyper();
		_passwordLastSet = buffer.dec_ndr_hyper();
		_passwordCanChange = buffer.dec_ndr_hyper();
		_passwordMustChange = buffer.dec_ndr_hyper();

		_effectiveName.length = (short) buffer.dec_ndr_short();
		_effectiveName.maximum_length = (short) buffer.dec_ndr_short();
		int effectiveNamePtr = buffer.dec_ndr_long();

		_fullName.length = (short) buffer.dec_ndr_short();
		_fullName.maximum_length = (short) buffer.dec_ndr_short();
		int fullNamePtr = buffer.dec_ndr_long();

		_logonScript.length = (short) buffer.dec_ndr_short();
		_logonScript.maximum_length = (short) buffer.dec_ndr_short();
		int logonScriptPtr = buffer.dec_ndr_long();

		_profilePath.length = (short) buffer.dec_ndr_short();
		_profilePath.maximum_length = (short) buffer.dec_ndr_short();
		int profilePathPtr = buffer.dec_ndr_long();

		_homeDirectory.length = (short) buffer.dec_ndr_short();
		_homeDirectory.maximum_length = (short) buffer.dec_ndr_short();
		int homeDirectoryPtr = buffer.dec_ndr_long();

		_homeDirectoryDrive.length = (short) buffer.dec_ndr_short();
		_homeDirectoryDrive.maximum_length = (short) buffer.dec_ndr_short();
		int homeDirectoryDrivePtr = buffer.dec_ndr_long();

		_logonCount = (short) buffer.dec_ndr_short();
		_badPasswordCount = (short) buffer.dec_ndr_short();

		_userId = buffer.dec_ndr_long();
		_primaryGroupId = buffer.dec_ndr_long();

		_groupCount = buffer.dec_ndr_long();

		int groupIdsPtr = buffer.dec_ndr_long();

		_userFlags = buffer.dec_ndr_long();

		int userSessionKeyI = buffer.index;
		buffer.advance(16);

		_logonServer.length = (short) buffer.dec_ndr_short();
		_logonServer.maximum_length = (short) buffer.dec_ndr_short();
		int logonServerPtr = buffer.dec_ndr_long();

		_logonDomainName.length = (short) buffer.dec_ndr_short();
		_logonDomainName.maximum_length = (short) buffer.dec_ndr_short();
		int logonDomainNamePtr = buffer.dec_ndr_long();

		int logonDomainPtr = buffer.dec_ndr_long();

		buffer.advance(40); // expansion room

		if (effectiveNamePtr > 0) {
			decodeUnicodeString(buffer, _effectiveName);
		}

		if (fullNamePtr > 0) {
			decodeUnicodeString(buffer, _fullName);
		}

		if (logonScriptPtr > 0) {
			decodeUnicodeString(buffer, _logonScript);
		}

		if (profilePathPtr > 0) {
			decodeUnicodeString(buffer, _profilePath);
		}

		if (homeDirectoryPtr > 0) {
			decodeUnicodeString(buffer, _homeDirectory);
		}

		if (homeDirectoryDrivePtr > 0) {
			decodeUnicodeString(buffer, _homeDirectoryDrive);
		}

		if (groupIdsPtr > 0) {
			_groupIds = new GroupMembership[_groupCount];

			buffer = buffer.deferred;

			int groupIdsS = buffer.dec_ndr_long();
			int groupIdsI = buffer.index;

			buffer.advance(8 * groupIdsS);

			buffer = buffer.derive(groupIdsI);

			for (int i = 0; i < groupIdsS; i++) {
				if (_groupIds[i] == null) {
					_groupIds[i] = new GroupMembership();
				}

				_groupIds[i].decode(buffer);
			}
		}

		buffer = buffer.derive(userSessionKeyI);

		for (int i = 0; i < 16; i++) {
			_userSessionKey[i] = (byte) buffer.dec_ndr_small();
		}

		if (logonServerPtr > 0) {
			decodeUnicodeString(buffer, _logonServer);
		}

		if (logonDomainNamePtr > 0) {
			decodeUnicodeString(buffer, _logonDomainName);
		}

		if (logonDomainPtr > 0) {
			buffer = buffer.deferred;

			_logonDomain.decode(buffer);
		}
	}

	public void encode(NdrBuffer buffer) throws NdrException {
	}

	public rpc.unicode_string getEffectiveName() {
		return _effectiveName;
	}

	protected void decodeUnicodeString(
		NdrBuffer buffer, rpc.unicode_string string) {

		buffer = buffer.deferred;

		int bufferS = buffer.dec_ndr_long();
		buffer.dec_ndr_long();
		int bufferL = buffer.dec_ndr_long();
		int bufferI = buffer.index;

		buffer.advance(2 * bufferL);

		if (string.buffer == null) {
			string.buffer = new short[bufferS];
		}

		buffer = buffer.derive(bufferI);

		for (int i = 0; i < bufferL; i++) {
			string.buffer[i] = (short) buffer.dec_ndr_short();
		}
	}

	private long _logonTime;
	private long _logoffTime;
	private long _kickoffTime;
	private long _passwordLastSet;
	private long _passwordCanChange;
	private long _passwordMustChange;
	private rpc.unicode_string _effectiveName;
	private rpc.unicode_string _fullName;
	private rpc.unicode_string _logonScript;
	private rpc.unicode_string _profilePath;
	private rpc.unicode_string _homeDirectory;
	private rpc.unicode_string _homeDirectoryDrive;
	private short _logonCount;
	private short _badPasswordCount;
	private int _userId;
	private int _primaryGroupId;
	private int _groupCount;
	private GroupMembership[] _groupIds;
	private int _userFlags;
	private byte[] _userSessionKey;
	private rpc.unicode_string _logonServer;
	private rpc.unicode_string _logonDomainName;
	private rpc.sid_t _logonDomain;

}
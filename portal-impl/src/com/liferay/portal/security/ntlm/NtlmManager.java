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

package com.liferay.portal.security.ntlm;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.util.PropsValues;

import java.io.UnsupportedEncodingException;

import java.security.MessageDigest;
import java.security.SecureRandom;

import jcifs.ntlmssp.NtlmFlags;
import jcifs.ntlmssp.Type1Message;
import jcifs.ntlmssp.Type2Message;
import jcifs.ntlmssp.Type3Message;

import jcifs.util.Encdec;

/**
 * <a href="NtlmManager.java.html"><b><i>View Source</i></b></a>
 *
 * @author Marcellus Tavares
 */
public class NtlmManager {

	public NtlmManager() {
		_domain = PropsValues.NTLM_DOMAIN;
		_serviceAccount = new NtlmServiceAccount(
			PropsValues.NTLM_SERVICE_ACCOUNT, null);
	}

	public NtlmUserAccount authenticate(byte[] material) {
		try {
			Type3Message msg3 = new Type3Message(material);

			if (msg3.getFlag(NTLMSSP_NEGOTIATE_EXTENDED_SESSIONSECURITY) &&
				msg3.getNTResponse().length == 24) {

				MessageDigest md5 = MessageDigest.getInstance("MD5");

				byte[] tmp = new byte[16];
				System.arraycopy(_serverChallenge, 0, tmp, 0, 8);
				System.arraycopy(msg3.getLMResponse(), 0, tmp, 8, 8);

				md5.update(tmp);

				_serverChallenge = md5.digest();
			}

			 return NetlogonUtil.logon(
				 msg3.getDomain(), msg3.getUser(), msg3.getWorkstation(),
				 _serverChallenge, msg3.getNTResponse(), msg3.getLMResponse());
		}
		catch (Exception e) {
			_log.error(e);
		}

		return null;
	}

	public byte[] negotiate(byte[] material) {
		try {
			Type1Message msg1 = new Type1Message(material);

			_secureRandom.nextBytes(_serverChallenge);

			Type2Message msg2 = new Type2Message(
				msg1.getFlags(), _serverChallenge, _domain);

			if (msg2.getFlag(NTLMSSP_NEGOTIATE_EXTENDED_SESSIONSECURITY)) {
				msg2.setFlag(NtlmFlags.NTLMSSP_NEGOTIATE_LM_KEY, false);
				msg2.setFlag(NtlmFlags.NTLMSSP_NEGOTIATE_TARGET_INFO, true);

				msg2.setTargetInformation(getTargetInformation());
			}

			return msg2.toByteArray();
		}
		catch (Exception e) {
			_log.error(e);
		}

		return null;
	}

	public byte[] getServerChallenge() {
		return _serverChallenge;
	}

	public void setServerChallenge(byte[] serverChallenge) {
		_serverChallenge = serverChallenge;
	}

	protected byte[] getAVPairBytes(int avId, String value)
		throws UnsupportedEncodingException{

		byte[] valueBytes = value.getBytes("UTF-16LE");
		byte[] result = new byte[4 + valueBytes.length];

		Encdec.enc_uint16le((short)avId, result, 0);
		Encdec.enc_uint16le((short)valueBytes.length, result, 2);

		System.arraycopy(valueBytes, 0, result, 4, valueBytes.length);

		return result;
	}

	protected byte[] getTargetInformation() throws UnsupportedEncodingException{
		byte[] computerName = getAVPairBytes(
			1, _serviceAccount.getComputerName());
		byte[] domainName =  getAVPairBytes(2, _domain);
		byte[] eol = getAVPairBytes(0, "");

		byte[] targetInformation = ArrayUtil.append(computerName, domainName);

		targetInformation = ArrayUtil.append(targetInformation, eol);

		return targetInformation;
	}

	private static Log _log = LogFactoryUtil.getLog(NtlmManager.class);

	public static final int NTLMSSP_NEGOTIATE_EXTENDED_SESSIONSECURITY =
		0x00080000;

	private String _domain;
	private SecureRandom _secureRandom = new SecureRandom();
	private byte[] _serverChallenge = new byte[8];
	private NtlmServiceAccount _serviceAccount;

}
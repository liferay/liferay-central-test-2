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
import com.liferay.portal.security.ntlm.msrpc.NetlogonAuthenticator;
import com.liferay.portal.security.ntlm.msrpc.NetlogonIdentityInfo;
import com.liferay.portal.security.ntlm.msrpc.NetlogonNetworkInfo;
import com.liferay.portal.security.ntlm.msrpc.NetlogonValidationSamInfo;
import com.liferay.portal.security.ntlm.msrpc.NetrLogonSamLogon;
import com.liferay.portal.security.ntlm.msrpc.NetrServerAuthenticate3;
import com.liferay.portal.security.ntlm.msrpc.NetrServerReqChallenge;

import java.io.IOException;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import java.util.Arrays;

import jcifs.dcerpc.DcerpcBinding;
import jcifs.dcerpc.DcerpcHandle;
import jcifs.dcerpc.UnicodeString;

import jcifs.smb.NtlmPasswordAuthentication;
import jcifs.smb.SmbException;

import jcifs.util.DES;
import jcifs.util.Encdec;
import jcifs.util.HMACT64;
import jcifs.util.MD4;

/**
 * <a href="Netlogon.java.html"><b><i>View Source</i></b></a>
 *
 * @author Marcellus Tavares
 */
public class Netlogon {

	public Netlogon(
		String domainController, String domainControllerName,
		NtlmServiceAccount serviceAccount) {

		DcerpcBinding.addInterface(
			"netlogon", "12345678-1234-abcd-ef00-01234567cffb:1.0");

		_domainController = domainController;
		_domainControllerName = domainControllerName;
		_serviceAccount = serviceAccount;
	}

	public NtlmUserAccount logon(
		String domain, String userName, String workstation,
		byte[] serverChallenge,	byte[] ntResponse, byte[] lmResponse) {

		try {
			connect();

			NetlogonAuthenticator authenticator =
				computeNetlogonAuthenticator();

			NetlogonIdentityInfo identity = new NetlogonIdentityInfo(
				domain, 0x00000820, 0, 0, userName,	workstation);

			NetlogonNetworkInfo logonInformation = new NetlogonNetworkInfo(
				identity, serverChallenge,	ntResponse, lmResponse);

			NetrLogonSamLogon msg = new NetrLogonSamLogon(
				_domainControllerName, _serviceAccount.getComputerName(),
				authenticator, new NetlogonAuthenticator(), 2,
				logonInformation, 2, new NetlogonValidationSamInfo(), 0);

			_handle.sendrecv(msg);

			if (msg.getStatus() == 0) {
				NetlogonValidationSamInfo samInfo =
					msg.getNetlogonValidationSamInfo();

				UnicodeString name = new UnicodeString(
					samInfo.getEffectiveName(), false);

				return new NtlmUserAccount(name.toString());
			}
			else {
				SmbException se = new SmbException(msg.getStatus(), false);

				_log.warn(se);
			}
		}
		catch (Exception e) {
			_log.error(e);
		}
		finally {
			try {
				disconnect();
			}
			catch (Exception e) {
				_log.error(e);
			}
		}

		return null;
	}

	protected NetlogonAuthenticator computeNetlogonAuthenticator() {
		int timestamp = (int) System.currentTimeMillis();
		int input = Encdec.dec_uint32le(_clientCredential, 0) + timestamp;

		Encdec.enc_uint32le(input, _clientCredential, 0);

		byte[] credential = computeNetlogonCredential(
			_clientCredential, _sessionKey);

		return new NetlogonAuthenticator(credential, timestamp);
	}

	protected byte[] computeNetlogonCredential(
		byte[] input, byte[] sessionKey) {

		byte[] k1 = new byte[7];
		byte[] k2 = new byte[7];

		System.arraycopy(sessionKey, 0, k1, 0, 7);
		System.arraycopy(sessionKey, 7, k2, 0, 7);

		DES k3 = new DES(k1);
		DES k4 = new DES(k2);

		byte[] output1 = new byte[8];
		byte[] output2 = new byte[8];

		k3.encrypt(input, output1);
		k4.encrypt(output1, output2);

		return output2;
	}

	protected byte[] computeSessionKey(
		byte[] sharedSecret, byte[] clientChallenge, byte[] serverChallenge) {

		try {
			MessageDigest md5 = MessageDigest.getInstance("MD5");

			byte[] zeroes = {0, 0, 0, 0};

			md5.update(zeroes, 0, 4);
			md5.update(clientChallenge, 0, 8);
			md5.update(serverChallenge, 0, 8);

			HMACT64 hmac = new HMACT64(sharedSecret);

			hmac.update(md5.digest());

			return hmac.digest();
		}
		catch (NoSuchAlgorithmException nsae) {
			_log.error(nsae);
		}

		return null;
	}

	protected void connect() throws IOException {
		NtlmPasswordAuthentication auth = new NtlmPasswordAuthentication(
			null, _serviceAccount.getAccount(),	_serviceAccount.getPassword());

		 String endpoint =
			 "ncacn_np:" + _domainController + "[\\PIPE\\NETLOGON]";

		_handle = DcerpcHandle.getHandle(endpoint, auth);
		_handle.bind();

		byte[] clientChallenge = new byte[8];

		_secureRandom.nextBytes(clientChallenge);

		NetrServerReqChallenge msg = new NetrServerReqChallenge(
			_domainControllerName, _serviceAccount.getComputerName(),
			clientChallenge, new byte[8]);

		_handle.sendrecv(msg);

		MD4 md4 = new MD4();

		md4.update(_serviceAccount.getPassword().getBytes("UTF-16LE"));

		byte[] sessionKey = computeSessionKey(
			md4.digest(), clientChallenge, msg.getServerChallenge());

		byte[] clientCredential = computeNetlogonCredential(
			clientChallenge, sessionKey);

		NetrServerAuthenticate3 msg2 = new NetrServerAuthenticate3(
			_domainControllerName, _serviceAccount.getAccountName(), 2,
			_serviceAccount.getComputerName(), clientCredential, new byte[8],
			0xFFFFFFFF);

		_handle.sendrecv(msg2);

		byte[] serverCredential = computeNetlogonCredential(
			msg.getServerChallenge(), sessionKey);

		if (!Arrays.equals(serverCredential, msg2.getServerCredential())) {
			_log.error("Session Key negotiation failed.");

			return;
		}

		_clientCredential = clientCredential;
		_sessionKey = sessionKey;
	}

	protected void disconnect() throws IOException {
		if (_handle != null) {
			_handle.close();

			_handle = null;
		}
	}

	private NtlmServiceAccount _serviceAccount;

	private String _domainController;
	private String _domainControllerName;

	private byte[] _sessionKey;
	private byte[] _clientCredential;

	private SecureRandom _secureRandom = new SecureRandom();

	private DcerpcHandle _handle;

	private static Log _log = LogFactoryUtil.getLog(Netlogon.class);

}
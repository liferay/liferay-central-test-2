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
		NtlmServiceAccount ntlmServiceAccount) {

		DcerpcBinding.addInterface(
			"netlogon", "12345678-1234-abcd-ef00-01234567cffb:1.0");

		_domainController = domainController;
		_domainControllerName = domainControllerName;
		_ntlmServiceAccount = ntlmServiceAccount;
	}

	public NtlmUserAccount logon(
		String domain, String userName, String workstation,
		byte[] serverChallenge,	byte[] ntResponse, byte[] lmResponse) {

		try {
			connect();

			NetlogonAuthenticator netlogonAuthenticator =
				computeNetlogonAuthenticator();

			NetlogonIdentityInfo netlogonIdentityInfo =
				new NetlogonIdentityInfo(
					domain, 0x00000820, 0, 0, userName, workstation);

			NetlogonNetworkInfo netlogonNetworkInfo = new NetlogonNetworkInfo(
				netlogonIdentityInfo, serverChallenge,	ntResponse, lmResponse);

			NetrLogonSamLogon netrLogonSamLogon = new NetrLogonSamLogon(
				_domainControllerName, _ntlmServiceAccount.getComputerName(),
				netlogonAuthenticator, new NetlogonAuthenticator(), 2,
				netlogonNetworkInfo, 2, new NetlogonValidationSamInfo(), 0);

			_handle.sendrecv(netrLogonSamLogon);

			if (netrLogonSamLogon.getStatus() == 0) {
				NetlogonValidationSamInfo netlogonValidationSamInfo =
					netrLogonSamLogon.getNetlogonValidationSamInfo();

				UnicodeString name = new UnicodeString(
					netlogonValidationSamInfo.getEffectiveName(), false);

				return new NtlmUserAccount(name.toString());
			}
			else {
				SmbException smbe = new SmbException(
					netrLogonSamLogon.getStatus(), false);

				_log.warn(smbe);
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
		int timestamp = (int)System.currentTimeMillis();
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
			MessageDigest messageDigest = MessageDigest.getInstance("MD5");

			byte[] zeroes = {0, 0, 0, 0};

			messageDigest.update(zeroes, 0, 4);
			messageDigest.update(clientChallenge, 0, 8);
			messageDigest.update(serverChallenge, 0, 8);

			HMACT64 hmact64 = new HMACT64(sharedSecret);

			hmact64.update(messageDigest.digest());

			return hmact64.digest();
		}
		catch (NoSuchAlgorithmException nsae) {
			_log.error(nsae);
		}

		return null;
	}

	protected void connect() throws IOException {
		NtlmPasswordAuthentication ntlmPasswordAuthentication =
			new NtlmPasswordAuthentication(
				null, _ntlmServiceAccount.getAccount(),
				_ntlmServiceAccount.getPassword());

		 String endpoint =
			 "ncacn_np:" + _domainController + "[\\PIPE\\NETLOGON]";

		_handle = DcerpcHandle.getHandle(endpoint, ntlmPasswordAuthentication);

		_handle.bind();

		byte[] clientChallenge = new byte[8];

		_secureRandom.nextBytes(clientChallenge);

		NetrServerReqChallenge netrServerReqChallenge =
			new NetrServerReqChallenge(
				_domainControllerName, _ntlmServiceAccount.getComputerName(),
				clientChallenge, new byte[8]);

		_handle.sendrecv(netrServerReqChallenge);

		MD4 md4 = new MD4();

		md4.update(_ntlmServiceAccount.getPassword().getBytes("UTF-16LE"));

		byte[] sessionKey = computeSessionKey(
			md4.digest(), clientChallenge,
			netrServerReqChallenge.getServerChallenge());

		byte[] clientCredential = computeNetlogonCredential(
			clientChallenge, sessionKey);

		NetrServerAuthenticate3 netrServerAuthenticate3 =
			new NetrServerAuthenticate3(
				_domainControllerName, _ntlmServiceAccount.getAccountName(), 2,
				_ntlmServiceAccount.getComputerName(), clientCredential,
				new byte[8], 0xFFFFFFFF);

		_handle.sendrecv(netrServerAuthenticate3);

		byte[] serverCredential = computeNetlogonCredential(
			netrServerReqChallenge.getServerChallenge(), sessionKey);

		if (!Arrays.equals(
				serverCredential,
				netrServerAuthenticate3.getServerCredential())) {

			_log.error("Session key negotiation failed");

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

	private static Log _log = LogFactoryUtil.getLog(Netlogon.class);

	private byte[] _clientCredential;
	private String _domainController;
	private String _domainControllerName;
	private DcerpcHandle _handle;
	private NtlmServiceAccount _ntlmServiceAccount;
	private SecureRandom _secureRandom = new SecureRandom();
	private byte[] _sessionKey;

}
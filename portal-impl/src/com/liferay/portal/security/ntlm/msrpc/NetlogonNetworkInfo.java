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
 * <a href="NetlogonNetworkInfo.java.html"><b><i>View Source</i></b></a>
 *
 * @author Marcellus Tavares
 */
public class NetlogonNetworkInfo extends NdrObject {

	public NetlogonNetworkInfo(
		NetlogonIdentityInfo identity, byte[] lmChallenge,
		byte[] ntChallengeResponse, byte[] lmChallengeResponse) {

		_identity = identity;
		_lmChallenge = lmChallenge;
		_ntChallengeResponse = ntChallengeResponse;
		_lmChallengeResponse = lmChallengeResponse;
	}

	public void decode(NdrBuffer buffer) throws NdrException {
	}

	public void encode(NdrBuffer buffer) throws NdrException {
		buffer.align(4);

		_identity.encode(buffer);

		int lmChallengeIndex = buffer.index;
		buffer.advance(8);

		buffer.enc_ndr_short((short)_ntChallengeResponse.length);
		buffer.enc_ndr_short((short)_ntChallengeResponse.length);
		buffer.enc_ndr_referent(_ntChallengeResponse, 1);

		buffer.enc_ndr_short((short)_lmChallengeResponse.length);
		buffer.enc_ndr_short((short)_lmChallengeResponse.length);
		buffer.enc_ndr_referent(_lmChallengeResponse, 1);

		_identity.encodeLogonDomainName(buffer);
		_identity.encodeUserName(buffer);
		_identity.encodeWorkStationName(buffer);

		buffer = buffer.derive(lmChallengeIndex);

		for (int i = 0; i < 8; i++) {
			buffer.enc_ndr_small(_lmChallenge[i]);
		}

		encodeChallengeResponse(buffer, _ntChallengeResponse);
		encodeChallengeResponse(buffer, _lmChallengeResponse);
	}

	protected void encodeChallengeResponse(NdrBuffer buffer, byte[] challenge) {
		buffer = buffer.deferred;

		buffer.enc_ndr_long(challenge.length);
		buffer.enc_ndr_long(0);
		buffer.enc_ndr_long(challenge.length);

		int challengeIndex = buffer.index;

		buffer.advance(challenge.length);

		buffer = buffer.derive(challengeIndex);

		for (int i = 0; i < challenge.length; i++) {
			buffer.enc_ndr_small(challenge[i]);
		}
	}

	private NetlogonIdentityInfo _identity;
	private byte[] _lmChallenge;
	private byte[] _ntChallengeResponse;
	private byte[] _lmChallengeResponse;

}
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

package com.liferay.portal.kernel.io;

import java.io.IOException;
import java.io.OutputStream;

/**
 * <a href="Base64OutputStream.java.html"><b><i>View Source</i></b></a>
 *
 * @author Tina Tian
 */
public class Base64OutputStream extends OutputStream {

	public Base64OutputStream (OutputStream out) {
		_out = out;
		_unitBuffer = new byte[3];
		_unitBufferIndex = 0;
		_outPutBuffer = new byte[4];
	}

	public void close() throws IOException {
		flush();
		_out.close();
	}

	public void flush() throws IOException {
		if (_unitBufferIndex == 1) {
			encodeUnit(_unitBuffer[0]);
		}
		else if (_unitBufferIndex == 2) {
			encodeUnit(_unitBuffer[0], _unitBuffer[1]);
		}
		_unitBufferIndex = 0;

		_out.flush();
	}

	public void write(byte[] b) throws IOException {
		write(b, 0, b.length);
	}

	public void write(int b) throws IOException {
		_unitBuffer[_unitBufferIndex++] = (byte)b;

		if (_unitBufferIndex == 3) {
			encodeUnit(_unitBuffer[0], _unitBuffer[1], _unitBuffer[2]);
			_unitBufferIndex = 0;
		}
	}

	public void write(byte[] b, int off, int len) throws IOException {
		if (len <= 0) {
			return;
		}

		while ((_unitBufferIndex != 0) && (len > 0)) {
			write(b[off++]);
			len --;
		}

		if (len <= 0) {
			return;
		}

		int blen = len - len%3;
		len -= blen;

		while (blen > 0) {
			encodeUnit(b[off], b[off+1], b[off+2]);
			blen -=3 ;
			off += 3;
		}

		while (len > 0) {
			write(b[off++]);
			len --;
		}
	}

	protected void encodeUnit(byte b) throws IOException {
		int val = b & 0xff;
		val <<= 4;
		_outPutBuffer[3] = (byte)'=';
		_outPutBuffer[2] = (byte)'=';
		_outPutBuffer[1] = (byte)getChar(val & 0x3f);
		val >>= 6;
		_outPutBuffer[0] = (byte)getChar(val & 0x3f);

		_out.write(_outPutBuffer);
	}

	protected void encodeUnit(byte b1, byte b2) throws IOException {
		int val = b1& 0xff;
		val <<= 8;
		val |= b2& 0xff;
		val <<= 2;
		_outPutBuffer[3] = (byte)'=';
		_outPutBuffer[2] = (byte)getChar(val & 0x3f);
		val >>= 6;
		_outPutBuffer[1] = (byte)getChar(val & 0x3f);
		val >>= 6;
		_outPutBuffer[0] = (byte)getChar(val & 0x3f);

		_out.write(_outPutBuffer);
	}

	protected void encodeUnit(byte b1, byte b2, byte b3) throws IOException {
		int val = b1 & 0xff;
		val <<= 8;
		val |= b2 & 0xff;
		val <<= 8;
		val |= b3 & 0xff;

		_outPutBuffer[3] = (byte)getChar(val & 0x3f);
		val >>= 6;
		_outPutBuffer[2] = (byte)getChar(val & 0x3f);
		val >>= 6;
		_outPutBuffer[1] = (byte)getChar(val & 0x3f);
		val >>= 6;
		_outPutBuffer[0] = (byte)getChar(val & 0x3f);

		_out.write(_outPutBuffer);
	}

	protected char getChar(int sixbit) {
		if (sixbit >= 0 && sixbit <= 25) {
			return (char)(65 + sixbit);
		}

		if (sixbit >= 26 && sixbit <= 51) {
			return (char)(97 + (sixbit - 26));
		}

		if (sixbit >= 52 && sixbit <= 61) {
			return (char)(48 + (sixbit - 52));
		}

		if (sixbit == 62) {
			return '+';
		}

		return sixbit != 63 ? '?' : '/';
	}

	private OutputStream _out;
	private byte[] _outPutBuffer;
	private byte[] _unitBuffer;
	private int _unitBufferIndex;

}
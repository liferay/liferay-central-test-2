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
import java.io.InputStream;

/**
 * <a href="Base64InputStream.java.html"><b><i>View Source</i></b></a>
 *
 * @author Tina Tian
 */
public class Base64InputStream extends InputStream {

	public Base64InputStream (InputStream in) {
		_in = in;
		_unitBufferIndex = 0;
		_avaiableBytes = 0;
		_unitBuffer = new byte[3];
	}

	public int available() throws IOException {
		return ((_in.available() * 3)/4 + _avaiableBytes);
	}

	public int read() throws IOException {
		if (_avaiableBytes == 0) {
			_avaiableBytes = decodeUnit(_unitBuffer, 0);
			if (_avaiableBytes <= 0) {
				return -1;
			}
			_unitBufferIndex = 0;
		}
		_avaiableBytes --;

		return _unitBuffer[_unitBufferIndex ++] & 0xff;
	}

	public int read(byte[] buf, int off, int len) throws IOException {
		if ((len <= 0) || (off < 0)) {
			return -1;
		}

		int initLen = len;

		while ((_avaiableBytes > 0) && (len > 0)) {
			buf[off++] = _unitBuffer[_unitBufferIndex ++];
			_avaiableBytes --;
			len --;
		}

		int blen = len - len%3;

		while (blen > 0) {
			int result = decodeUnit(buf, off);
			if (result > 0) {
				off += result;
				len -= result;
			}
			if (result < 3) {
				if (initLen == len) {
					return -1;
				}
				return initLen - len;
			}

			blen -= 3;
		}

		while (len > 0) {
			int c = read();
			if (c == -1) {
				break;
			}
			buf[off++] = (byte)c;
			len --;
		}

		if (initLen == len) {
			return -1;
		}
		return initLen - len;
	}

	public long skip(long n) throws IOException {
		long initN = n;
		while (n > 0) {
			if (read() <= 0) {
				break;
			}
			n--;
		}

		return initN - n;
	}

	protected int decode(byte [] bytes, byte[] outbuf, int pos, int padNumber) {
		int val = 0;

		for(int i = 0; (i < 4); i++) {
			val <<= 6;
			val |= bytes[i];
		}

		if (padNumber == 2) {
			val >>= 16;
			outbuf[pos] = (byte)(val & 0xff);
			return 1;
		}
		else if (padNumber == 1) {
			val >>= 8;
			outbuf[pos+1] = (byte)(val & 0xff);
			val >>= 8;
			outbuf[pos] = (byte)(val & 0xff);
			return 2;
		}
		else if (padNumber == 0) {
			outbuf[pos+2] = (byte)(val & 0xff);
			val >>= 8;
			outbuf[pos+1] = (byte)(val & 0xff);
			val >>= 8;
			outbuf[pos] = (byte)(val & 0xff);
			return 3;
		}
		else {
			return -1;
		}
	}

	protected int decodeUnit(byte[] outbuf, int pos) throws IOException {
		int b = -1;
		int padNumber = 0;
		int count = 0;
		byte [] decodeUnitBuffer = new byte[4];

		while (count < 4) {
			b = getEncodedByte();

			if (b == -1) {
				return -1;
			}

			if (b == -2) {
				if (count < 2) {
					return -1;
				}

				padNumber ++;
				count ++;

				while (count < 4) {
					b = getEncodedByte();

					if (b != -2) {
						return -1;
					}

					padNumber ++;
					count ++;
				}

				return decode(decodeUnitBuffer, outbuf, pos, padNumber);
			}

			decodeUnitBuffer[count ++] = (byte)b;
		}

		return decode(decodeUnitBuffer, outbuf, pos, padNumber);
	}

	protected int getByte(char c) {
		if ((c >= 'A') && (c <= 'Z')) {
			return c - 65;
		}

		if ((c >= 'a') && (c <= 'z')) {
			return (c - 97) + 26;
		}

		if (c >= '0' && c <= '9') {
			return (c - 48) + 52;
		}

		if (c == '+') {
			return 62;
		}

		if (c == '/') {
			return 63;
		}

		return c != '=' ? -1 : 0;
	}

	protected int getEncodedByte() throws IOException {
		while (true) {
			int result = _in.read();
			if (result == -1) {
				return -1;
			}

			char c = (char)(result & 0xff);
			if (c == '=') {
				return -2;
			}

			int b = getByte(c);

			if (b == -1) {
				continue;
			}

			return b;
		}
	}

	private int _avaiableBytes;
	private InputStream _in;
	private byte [] _unitBuffer;
	private int _unitBufferIndex;

}
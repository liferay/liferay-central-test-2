/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
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

package com.liferay.portal.tools.upgrade.client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import java.net.Socket;

import java.util.ArrayList;
import java.util.List;

/**
 * java client for telnet handshake
 *
 * reference: http://www.laynetworks.com/telnet.htm
 *
 * @author Gregory Amerson
 */
public class GogoTelnetClient implements AutoCloseable {

	public static int[] toIntArray(List<Integer> list) {
		int[] ret = new int[list.size()];
		int i = 0;

		for (Integer e : list) {
			ret[i++] = e.intValue();
		}

		return ret;
	}

	public GogoTelnetClient() throws IOException {
		this("localhost", 11311);
	}

	public GogoTelnetClient(String host, int port) throws IOException {
		_socket = new Socket(host, port);
		_inputStream = new DataInputStream(_socket.getInputStream());
		_outputStream = new DataOutputStream(_socket.getOutputStream());

		_doHandshake();
	}

	public void close() {
		try {
			_socket.close();
			_inputStream.close();
			_outputStream.close();
		}
		catch (IOException ioe) {
		}
	}

	public String send(String command) throws IOException {
		byte[] bytes = command.getBytes();

		int[] codes = new int[bytes.length + 2];

		for (int i = 0; i < bytes.length; i++) {
			codes[i] = bytes[i];
		}

		codes[bytes.length] = '\r';
		codes[bytes.length + 1] = '\n';

		_sendCommand(codes);

		return _readUntilNextGogoPrompt();
	}

	private static void _assertCond(boolean condition) {
		if (!condition) {
			throw new AssertionError();
		}
	}

	private void _doHandshake() throws IOException {

		// gogo server first sends 4 commands

		_readOneCommand();
		_readOneCommand();
		_readOneCommand();
		_readOneCommand();

		// first we negotiate terminal type
		// 255(IAC),251(WILL),24(terminal type)

		_sendCommand(255, 251, 24);

		// server should respond
		// 255(IAC),250(SB),24,1,255(IAC),240(SE)

		_readOneCommand();

		// send the terminal type

		//255(IAC),250(SB),24,0,'V','T','2','2','0',255(IAC),240(SE)
		_sendCommand(255, 250, 24, 0, 'V', 'T', '2', '2', '0', 255, 240);

		// read gogo shell prompt

		_readUntilNextGogoPrompt();
	}

	private int[] _readOneCommand() throws IOException {
		List<Integer> bytes = new ArrayList<>();

		int iac = _inputStream.read();

		_assertCond(iac == 255);

		bytes.add(iac);

		int second = _inputStream.read();

		bytes.add(second);

		if (second == 250) {
			int option = _inputStream.read();

			bytes.add(option);

			int code = _inputStream.read(); // 1 or 0

			_assertCond(code == 0 || code == 1);

			bytes.add(code);

			if (code == 0) {
				throw new IllegalStateException();
			}
			else if (code == 1) {
				iac = _inputStream.read();

				_assertCond(iac == 255);

				bytes.add(iac);

				int se = _inputStream.read(); // SE

				_assertCond(se == 240);

				bytes.add(se);
			}
		}
		else {
			bytes.add(_inputStream.read());
		}

		return toIntArray(bytes);
	}

	private String _readUntilNextGogoPrompt() throws IOException {
		StringBuilder sb = new StringBuilder();

		int c = _inputStream.read();

		while (c != -1) {
			sb.append((char)c);

			String s = sb.toString();

			if (s.endsWith("g! ")) {
				break;
			}

			c = _inputStream.read();
		}

		String output = sb.substring(0, sb.length() - 3);

		return output.trim();
	}

	private void _sendCommand(int... codes) throws IOException {
		for (int code : codes) {
			_outputStream.write(code);
		}
	}

	private final DataInputStream _inputStream;
	private final DataOutputStream _outputStream;
	private final Socket _socket;

}
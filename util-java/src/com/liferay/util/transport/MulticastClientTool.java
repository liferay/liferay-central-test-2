/**
 * Copyright (c) 2000-2008 Liferay, Inc. All rights reserved.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.liferay.util.transport;

import java.net.DatagramPacket;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import java.util.HashMap;
import java.util.Map;
import java.util.zip.GZIPInputStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <a href="MulticastClientTool.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * A client that listens for multicast messages at a designated port. You may
 * use this to for potential multicast issues when tuning distributed caches.
 * </p>
 *
 * @author Michael C. Han
 * @author Raymond Augé
 *
 */
public class MulticastClientTool {

	public static void main(String[] args) {
        try {
        	Map<String, Object> argsMap = processArgs(args);

        	Integer port = (Integer)argsMap.get("port");
			String host = (String)argsMap.get("host");
			final Boolean gzip = (Boolean)argsMap.get("gzip");
			final Boolean shortData = (Boolean)argsMap.get("short");

			DatagramHandler handler = new DatagramHandler() {
				public void process(DatagramPacket packet) {
					byte payload[] = packet.getData();

					if (gzip.booleanValue()) {
						byte ungzipped[] = new byte[0];

						try {
	                        GZIPInputStream inputStream = new GZIPInputStream(
	                        	new ByteArrayInputStream(payload));
	                        ByteArrayOutputStream byteArrayOutputStream =
	                        	new ByteArrayOutputStream(payload.length);

	                        byte buffer[] = new byte[1500];

	                        int bytesRead = 0;

	                        do {
	                            if (bytesRead == -1) {
	                                break;
								}

	                            bytesRead = inputStream.read(buffer, 0, 1500);

	                            if (bytesRead != -1) {
	                                byteArrayOutputStream.write(
	                                	buffer, 0, bytesRead);
								}
	                    	} while (true);

							ungzipped = byteArrayOutputStream.toByteArray();
							inputStream.close();
							byteArrayOutputStream.close();

							payload = ungzipped;
						}
						catch (IOException e) {
							_log.error(e);
						}
					}

					if (shortData.booleanValue()) {
						byte[] temp = new byte[96];
						System.arraycopy(payload, 0, temp, 0, 96);
						payload = temp;
					}

					StringBuilder sb = new StringBuilder();
					sb.append("[");
					sb.append(packet.getSocketAddress());
					sb.append("] ");
					sb.append(new String(payload));

					_log.info(sb);
				}

				public void errorReceived(Throwable t) {
					_log.error(t);
				}

			};

			MulticastTransport transport = new MulticastTransport(
				handler, host, port);

			if (shortData.booleanValue()) {
				_log.info("Truncating to 96 bytes.");
			}

			_log.info("Started up and waiting...");

			transport.connect();

			synchronized (transport) {
				transport.wait();
			}
		}
		catch (Exception e) {
			_log.error(e);

			System.err.println(
				"Usage: java -classpath " +
				"util-java.jar:commons-logging.jar " +
				"com.liferay.util.transport.MulticastClientTool [-g] [-s] " +
				"-h [multicastAddress] -p [port]");

			System.exit(1);
		}
	}

	private static Map<String, Object> processArgs(String[] args)
		throws Exception {

		Map<String, Object> argsMap = new HashMap<String, Object>();

		for (int i = 0; i < args.length; i++) {
			if (args[i].equals("-g")) {
				argsMap.put("gzip", Boolean.TRUE);
			}
			else if (args[i].equals("-s")) {
				argsMap.put("short", Boolean.TRUE);
				i++;
			}
			else if (args[i].equals("-h")) {
				argsMap.put("host", args[i + 1]);
				i++;
			}
			else if (args[i].equals("-p")) {
				argsMap.put("port", new Integer(args[i + 1]));
				i++;
			}
		}

		if (!argsMap.containsKey("gzip")) {
			argsMap.put("gzip", Boolean.FALSE);
		}
		if (!argsMap.containsKey("short")) {
			argsMap.put("short", Boolean.FALSE);
		}

		return argsMap;
	}

    private static final Log _log = LogFactory.getLog(
    	MulticastClientTool.class);

}
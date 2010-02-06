/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.util.StringBundler;

import java.util.HashMap;
import java.util.Map;

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
 */
public class MulticastClientTool {

	public static void main(String[] args) {
		try {
			new MulticastClientTool(args);
		}
		catch (Exception e) {
			e.printStackTrace();

			StringBundler sb = new StringBundler(4);

			sb.append("Usage: java -classpath ");
			sb.append("commons-logging.jar:util-java.jar ");
			sb.append("com.liferay.util.transport.MulticastClientTool [-g] ");
			sb.append("[-s] -h [multicastAddress] -p [port]");

			System.err.println(sb.toString());

			System.exit(1);
		}
	}

	private MulticastClientTool(String[] args) throws Exception {
		Map<String, Object> argsMap = _getArgsMap(args);

		Integer port = (Integer)argsMap.get("port");
		String host = (String)argsMap.get("host");
		Boolean gzipData = (Boolean)argsMap.get("gzip");
		Boolean shortData = (Boolean)argsMap.get("short");

		DatagramHandler handler = new MulticastDatagramHandler(
			gzipData.booleanValue(), shortData.booleanValue());

		MulticastTransport transport = new MulticastTransport(
			handler, host, port);

		if (shortData.booleanValue()) {
			System.out.println("Truncating to 96 bytes.");
		}

		System.out.println("Started up and waiting...");

		transport.connect();

		synchronized (transport) {
			transport.wait();
		}
	}

	private Map<String, Object> _getArgsMap(String[] args)
		throws Exception {

		Map<String, Object> argsMap = new HashMap<String, Object>();

		for (int i = 0; i < args.length; i++) {
			if (args[i].equals("-g")) {
				argsMap.put("gzip", Boolean.TRUE);
			}
			else if (args[i].equals("-s")) {
				argsMap.put("short", Boolean.TRUE);
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

}
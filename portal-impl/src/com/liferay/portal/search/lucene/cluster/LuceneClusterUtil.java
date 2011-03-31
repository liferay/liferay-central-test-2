/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.search.lucene.cluster;

import com.liferay.portal.kernel.cluster.ClusterExecutorUtil;
import com.liferay.portal.kernel.cluster.ClusterNode;
import com.liferay.portal.kernel.cluster.ClusterNodeResponse;
import com.liferay.portal.kernel.cluster.ClusterRequest;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.io.unsync.UnsyncPrintWriter;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;
import com.liferay.portal.kernel.util.ObjectValuePair;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.search.lucene.LuceneHelperUtil;
import com.liferay.portal.security.auth.TransientTokenUtil;
import com.liferay.portal.servlet.LuceneServlet;

import java.io.IOException;
import java.io.InputStream;

import java.net.URL;
import java.net.URLConnection;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * @author Shuyang Zhou
 */
public class LuceneClusterUtil {

	public static void loadIndexesFromCluster(long companyId)
		throws SystemException {
		ObjectValuePair<String, URL> bootupNodeInfo = _getBootupNodeInfo();

		String token = bootupNodeInfo.getKey();
		URL bootupURL = bootupNodeInfo.getValue();

		InputStream is = null;

		try {
			URLConnection connection = bootupURL.openConnection();

			connection.setDoOutput(true);

			UnsyncPrintWriter unsyncPrintWriter = new UnsyncPrintWriter(
				connection.getOutputStream());

			unsyncPrintWriter.write(LuceneServlet.LUCENE_DUMP_INDEX_TOKEN);
			unsyncPrintWriter.write(StringPool.EQUAL);
			unsyncPrintWriter.write(token);

			unsyncPrintWriter.write(StringPool.AMPERSAND);

			unsyncPrintWriter.write(LuceneServlet.LUCENE_DUMP_INDEX_COMPANY_ID);
			unsyncPrintWriter.write(StringPool.EQUAL);
			unsyncPrintWriter.write(String.valueOf(companyId));

			unsyncPrintWriter.close();

			is = connection.getInputStream();

			LuceneHelperUtil.loadIndex(companyId, is);
		}
		catch (IOException ioe) {
			throw new SystemException(ioe);
		}
		finally {
			if (is != null) {
				try {
					is.close();
				}
				catch (IOException ioe) {
					throw new SystemException(ioe);
				}
			}
		}
	}

	private static ObjectValuePair<String, URL> _getBootupNodeInfo()
		throws SystemException {
		ClusterRequest clusterRequest = ClusterRequest.createMulticastRequest(
			new MethodHandler(_createTokenMethodKey, _TOKEN_KEEP_ALIVE_TIME),
			true);

		BlockingQueue<ClusterNodeResponse> clusterNodeResponses =
			ClusterExecutorUtil.execute(clusterRequest).getPartialResults();

		try {
			ClusterNodeResponse clusterNodeResponse = clusterNodeResponses.poll(
				_BOOTUP_NODE_RESPONSE_TIMEOUT, TimeUnit.MILLISECONDS);

			ClusterNode bootupNode = clusterNodeResponse.getClusterNode();

			String token = (String)clusterNodeResponse.getResult();

			URL dumpIndexURL = new URL("http",
				bootupNode.getInetAddress().getHostAddress(),
				bootupNode.getPort(), LuceneServlet.LUCENE_DUMP_INDEX_URL_PATH);

			return new ObjectValuePair<String, URL>(token, dumpIndexURL);
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
	}

	private static final long _BOOTUP_NODE_RESPONSE_TIMEOUT = 10000;

	private static final long _TOKEN_KEEP_ALIVE_TIME = 10000;

	private static MethodKey _createTokenMethodKey =
		new MethodKey(TransientTokenUtil.class.getName(), "createToken",
		long.class);

}
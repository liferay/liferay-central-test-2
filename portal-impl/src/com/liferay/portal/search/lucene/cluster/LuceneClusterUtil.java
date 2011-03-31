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
import com.liferay.portal.kernel.cluster.FutureClusterResponses;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.io.unsync.UnsyncPrintWriter;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;
import com.liferay.portal.kernel.util.ObjectValuePair;
import com.liferay.portal.search.lucene.LuceneHelperUtil;
import com.liferay.portal.security.auth.TransientTokenUtil;

import java.io.IOException;
import java.io.InputStream;

import java.net.InetAddress;
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

		InputStream inputStream = null;

		try {
			ObjectValuePair<String, URL> bootupClusterNodeObjectValuePair =
				_getBootupClusterNodeObjectValuePair();

			URL url = bootupClusterNodeObjectValuePair.getValue();

			URLConnection urlConnection = url.openConnection();

			urlConnection.setDoOutput(true);

			UnsyncPrintWriter unsyncPrintWriter = new UnsyncPrintWriter(
				urlConnection.getOutputStream());

			unsyncPrintWriter.write("transientToken=");
			unsyncPrintWriter.write(bootupClusterNodeObjectValuePair.getKey());
			unsyncPrintWriter.write("&companyId=");
			unsyncPrintWriter.write(String.valueOf(companyId));

			unsyncPrintWriter.close();

			inputStream = urlConnection.getInputStream();

			LuceneHelperUtil.loadIndex(companyId, inputStream);
		}
		catch (IOException ioe) {
			throw new SystemException(ioe);
		}
		finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				}
				catch (IOException ioe) {
					throw new SystemException(ioe);
				}
			}
		}
	}

	private static ObjectValuePair<String, URL>
			_getBootupClusterNodeObjectValuePair()
		throws SystemException {

		ClusterRequest clusterRequest = ClusterRequest.createMulticastRequest(
			new MethodHandler(
				_createTokenMethodKey, _TRANSIENT_TOKEN_KEEP_ALIVE_TIME),
			true);

		FutureClusterResponses futureClusterResponses =
			ClusterExecutorUtil.execute(clusterRequest);

		BlockingQueue<ClusterNodeResponse> clusterNodeResponses =
			futureClusterResponses.getPartialResults();

		try {
			ClusterNodeResponse clusterNodeResponse = clusterNodeResponses.poll(
				_BOOTUP_CLUSTER_NODE_RESPONSE_TIMEOUT, TimeUnit.MILLISECONDS);

			ClusterNode bootupClusterNode =
				clusterNodeResponse.getClusterNode();

			String transientToken = (String)clusterNodeResponse.getResult();

			InetAddress inetAddress = bootupClusterNode.getInetAddress();

			URL url = new URL(
				"http", inetAddress.getHostAddress(),
				bootupClusterNode.getPort(), "/dump");

			return new ObjectValuePair<String, URL>(transientToken, url);
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
	}

	private static final long _BOOTUP_CLUSTER_NODE_RESPONSE_TIMEOUT = 10000;

	private static final long _TRANSIENT_TOKEN_KEEP_ALIVE_TIME = 10000;

	private static MethodKey _createTokenMethodKey =
		new MethodKey(TransientTokenUtil.class.getName(), "createToken",
		long.class);

}
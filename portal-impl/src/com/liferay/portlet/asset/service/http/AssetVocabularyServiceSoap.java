/**
 * Copyright (c) 2000-2009 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.asset.service.http;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import com.liferay.portlet.asset.service.AssetVocabularyServiceUtil;

import java.rmi.RemoteException;

public class AssetVocabularyServiceSoap {
	public static com.liferay.portlet.asset.model.AssetVocabularySoap addVocabulary(
		java.lang.String name,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws RemoteException {
		try {
			com.liferay.portlet.asset.model.AssetVocabulary returnValue = AssetVocabularyServiceUtil.addVocabulary(name,
					serviceContext);

			return com.liferay.portlet.asset.model.AssetVocabularySoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static void deleteVocabulary(long vocabularyId)
		throws RemoteException {
		try {
			AssetVocabularyServiceUtil.deleteVocabulary(vocabularyId);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portlet.asset.model.AssetVocabularySoap[] getCompanyVocabularies(
		long companyId) throws RemoteException {
		try {
			java.util.List<com.liferay.portlet.asset.model.AssetVocabulary> returnValue =
				AssetVocabularyServiceUtil.getCompanyVocabularies(companyId);

			return com.liferay.portlet.asset.model.AssetVocabularySoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portlet.asset.model.AssetVocabularySoap[] getGroupsVocabularies(
		long[] groupIds) throws RemoteException {
		try {
			java.util.List<com.liferay.portlet.asset.model.AssetVocabulary> returnValue =
				AssetVocabularyServiceUtil.getGroupsVocabularies(groupIds);

			return com.liferay.portlet.asset.model.AssetVocabularySoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portlet.asset.model.AssetVocabularySoap[] getGroupVocabularies(
		long groupId) throws RemoteException {
		try {
			java.util.List<com.liferay.portlet.asset.model.AssetVocabulary> returnValue =
				AssetVocabularyServiceUtil.getGroupVocabularies(groupId);

			return com.liferay.portlet.asset.model.AssetVocabularySoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portlet.asset.model.AssetVocabularySoap getVocabulary(
		long vocabularyId) throws RemoteException {
		try {
			com.liferay.portlet.asset.model.AssetVocabulary returnValue = AssetVocabularyServiceUtil.getVocabulary(vocabularyId);

			return com.liferay.portlet.asset.model.AssetVocabularySoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portlet.asset.model.AssetVocabularySoap updateVocabulary(
		long vocabularyId, java.lang.String name,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws RemoteException {
		try {
			com.liferay.portlet.asset.model.AssetVocabulary returnValue = AssetVocabularyServiceUtil.updateVocabulary(vocabularyId,
					name, serviceContext);

			return com.liferay.portlet.asset.model.AssetVocabularySoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	private static Log _log = LogFactoryUtil.getLog(AssetVocabularyServiceSoap.class);
}
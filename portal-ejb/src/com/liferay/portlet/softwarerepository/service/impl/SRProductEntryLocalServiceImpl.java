/**
 * Copyright (c) 2000-2006 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.softwarerepository.service.impl;

import com.liferay.counter.service.CounterLocalServiceUtil;
import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.lucene.LuceneFields;
import com.liferay.portal.lucene.LuceneUtil;
import com.liferay.portal.model.User;
import com.liferay.portal.service.persistence.UserUtil;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.softwarerepository.NoSuchProductEntryException;
import com.liferay.portlet.softwarerepository.model.SRProductEntry;
import com.liferay.portlet.softwarerepository.service.SRProductEntryLocalService;
import com.liferay.portlet.softwarerepository.service.persistence.SRProductEntryUtil;
import com.liferay.portlet.softwarerepository.util.Indexer;
import com.liferay.util.Validator;
import com.liferay.util.lucene.HitsImpl;

import java.io.IOException;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.Searcher;

/**
 * <a href="SRProductEntryLocalServiceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Jorge Ferrer
 *
 */
public class SRProductEntryLocalServiceImpl
	implements SRProductEntryLocalService {

	public SRProductEntry addProductEntry(
		String userId, String plid, String repoArtifactId, String repoGroupId,
		String name, String type, long[] licenseIds, String shortDescription,
		String longDescription, String pageURL)
		throws PortalException, SystemException {

		User user = UserUtil.findByPrimaryKey(userId);
		String groupId = PortalUtil.getPortletGroupId(plid);
		Date now = new Date();

		long productEntryId = CounterLocalServiceUtil.increment(
			SRProductEntry.class.getName());

		SRProductEntry productEntry = SRProductEntryUtil.create(productEntryId);

		productEntry.setGroupId(groupId);
		productEntry.setCompanyId(user.getCompanyId());
		productEntry.setUserId(user.getUserId());
		productEntry.setUserName(user.getFullName());
		productEntry.setCreateDate(now);
		productEntry.setModifiedDate(now);
		productEntry.setRepoArtifactId(repoArtifactId);
		productEntry.setRepoGroupId(repoGroupId);
		productEntry.setName(name);
		productEntry.setType(type);
		productEntry.setShortDescription(shortDescription);
		productEntry.setLongDescription(longDescription);
		productEntry.setPageURL(pageURL);

		SRProductEntryUtil.update(productEntry);
		SRProductEntryUtil.setSRLicenses(productEntryId, licenseIds);

		try {
			Indexer.addProductEntry(
				productEntry.getCompanyId(), productEntryId, user.getFullName(),
				groupId, repoArtifactId, repoGroupId, name, type,
				shortDescription, longDescription, pageURL);
		}
		catch (IOException ioe) {
			_log.warn("Error indexing product entry " + productEntryId, ioe);
		}

		return productEntry;
	}

	public void deleteProductEntry(long productEntryId)
		throws PortalException, SystemException {

		SRProductEntry productEntry =
			SRProductEntryUtil.findByPrimaryKey(productEntryId);

		SRProductEntryUtil.remove(productEntry.getProductEntryId());
	}

	public SRProductEntry getProductEntry(long productEntryId)
		throws PortalException, SystemException {

		return SRProductEntryUtil.findByPrimaryKey(productEntryId);
	}

	public List getProductEntries(
		String groupId, int begin, int end)
		throws SystemException {

		return SRProductEntryUtil.findByGroupId(groupId, begin, end);
	}

	public List getProductEntriesByUserId(
		String groupId, String userId, int begin, int end)
		throws SystemException {

		return SRProductEntryUtil.findByG_U(groupId, userId, begin, end);
	}

	public int getProductEntriesCount(String groupId)
		throws SystemException {

		return SRProductEntryUtil.countByGroupId(groupId);
	}

	public int getProductEntriesCountByUserId(String groupId, String userId)
		throws SystemException {

		return SRProductEntryUtil.countByG_U(groupId, userId);
	}

	public List getSRLicenses(long productEntryId)
		throws SystemException, NoSuchProductEntryException {
		return SRProductEntryUtil.getSRLicenses(productEntryId);
	}

	public void reIndex(String[] ids) throws SystemException {
		try {
			String companyId = ids[0];

			Iterator itr1 = SRProductEntryUtil.findByCompanyId(
				companyId).iterator();

			while (itr1.hasNext()) {
				SRProductEntry productEntry = (SRProductEntry)itr1.next();

				long productEntryId = productEntry.getProductEntryId();

				try {
					Indexer.addProductEntry(
						companyId, productEntryId, productEntry.getUserName(),
						productEntry.getGroupId(),
						productEntry.getRepoArtifactId(),
						productEntry.getRepoGroupId(),
						productEntry.getName(), productEntry.getType(),
						productEntry.getShortDescription(),
						productEntry.getLongDescription(),
						productEntry.getPageURL());
				}
				catch (Exception e1) {

					// Continue indexing even if one message fails

					_log.warn("Could not index product entry " + productEntryId,
						e1);
				}
			}
		}
		catch (SystemException se) {
			throw se;
		}
		catch (Exception e2) {
			throw new SystemException(e2);
		}
	}

	public Hits search(
		String companyId, String groupId, String type, String keywords)
		throws SystemException {

		try {
			HitsImpl hits = new HitsImpl();

			if (Validator.isNull(keywords) && Validator.isNull(type)) {
				return hits;
			}

			BooleanQuery contextQuery = new BooleanQuery();

			LuceneUtil.addRequiredTerm(
				contextQuery, LuceneFields.PORTLET_ID, Indexer.PORTLET_ID);
			LuceneUtil.addRequiredTerm(
				contextQuery, LuceneFields.GROUP_ID, groupId);

			if (Validator.isNotNull(type)) {
				LuceneUtil.addRequiredTerm(contextQuery, "type", type);
			}

			BooleanQuery fullQuery = new BooleanQuery();

			fullQuery.add(contextQuery, BooleanClause.Occur.MUST);

			if (Validator.isNotNull(keywords)) {
				BooleanQuery searchQuery = new BooleanQuery();

				LuceneUtil.addTerm(searchQuery, LuceneFields.TITLE, keywords);
				LuceneUtil.addTerm(searchQuery, LuceneFields.CONTENT, keywords);

				fullQuery.add(searchQuery, BooleanClause.Occur.MUST);
			}

			Searcher searcher = LuceneUtil.getSearcher(companyId);

			hits.recordHits(searcher.search(fullQuery));

			return hits;
		}
		catch (IOException ioe) {
			throw new SystemException(ioe);
		}
		catch (ParseException pe) {
			//throw new SystemException(pe);

			_log.error("Error parsing keywords " + keywords);

			return new HitsImpl();
		}
	}

	public SRProductEntry updateProductEntry(
		long productEntryId, String repoArtifactId, String repoGroupId,
		String name, long[] licenseIds, String shortDescription,
		String longDescription, String pageURL)
		throws PortalException, SystemException {

		SRProductEntry productEntry = SRProductEntryUtil.
			findByPrimaryKey(productEntryId);

		productEntry.setModifiedDate(new Date());
		productEntry.setRepoArtifactId(repoArtifactId);
		productEntry.setRepoGroupId(repoGroupId);
		productEntry.setName(name);
		productEntry.setShortDescription(shortDescription);
		productEntry.setLongDescription(longDescription);
		productEntry.setPageURL(pageURL);

		SRProductEntryUtil.update(productEntry);
		SRProductEntryUtil.setSRLicenses(productEntryId, licenseIds);

		try {
			Indexer.updateProductEntry(
				productEntry.getCompanyId(), productEntryId,
				productEntry.getUserName(), productEntry.getGroupId(),
				repoArtifactId, repoGroupId, name, productEntry.getType(),
				shortDescription, longDescription, pageURL);
		}
		catch (IOException ioe) {
			_log.warn("Error indexing product entry " + productEntryId, ioe);
		}

		return productEntry;
	}

	private static Log _log =
		LogFactory.getLog(SRProductEntryLocalServiceImpl.class);

}
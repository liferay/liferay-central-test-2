/**
 * Copyright (c) 2000-2006 Liferay, LLC. All rights reserved.
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

package com.liferay.portlet.alfresco.service.impl;

import com.liferay.portal.PortalException;
import com.liferay.portlet.alfresco.service.spring.AlfrescoContentLocalService;
import com.liferay.portlet.alfrescocontent.util.AlfrescoContentUtil;
import com.liferay.portlet.alfrescocontent.util.AuthenticationUtils;
import com.liferay.portlet.alfrescocontent.util.WebServiceFactory;
import com.liferay.util.Validator;

import org.alfresco.webservice.content.Content;
import org.alfresco.webservice.content.ContentServiceSoapBindingStub;
import org.alfresco.webservice.repository.QueryResult;
import org.alfresco.webservice.repository.RepositoryServiceSoapBindingStub;
import org.alfresco.webservice.types.Predicate;
import org.alfresco.webservice.types.Reference;
import org.alfresco.webservice.types.ResultSetRow;
import org.alfresco.webservice.types.Store;
import org.alfresco.webservice.types.StoreEnum;
import org.alfresco.webservice.util.Constants;

/**
 * <a href="AlfrescoContentLocalServiceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Michael Young
 *
 */
public class AlfrescoContentLocalServiceImpl
	implements AlfrescoContentLocalService {

	public ResultSetRow[] getNodes(String uuid) throws PortalException {
		ResultSetRow[] rows = null;

		try {
			AuthenticationUtils.startSession("admin", "admin");

			RepositoryServiceSoapBindingStub repositoryService = WebServiceFactory
				.getRepositoryService();

			Reference reference = null;

			if (Validator.isNull(uuid)) {
				reference = new Reference(_SPACES_STORE, null,
					_COMPANY_HOME_PATH);
			}
			else {
				reference = new Reference(_SPACES_STORE, uuid, null);
			}

			QueryResult result = repositoryService.queryChildren(reference);

			rows = result.getResultSet().getRows();
		}
		catch (Throwable e) {
			throw new PortalException(e);
		}
		finally {
			try {
				AuthenticationUtils.endSession();
			}
			catch (Throwable e) {
				throw new PortalException(e);
			}
		}
		
		return rows;
	}

	public String getContent(String uuid) throws PortalException {
		String content = null;
		
		try {
			AuthenticationUtils.startSession("admin", "admin");

            ContentServiceSoapBindingStub contentService = WebServiceFactory.getContentService();        

			Reference reference = new Reference(_SPACES_STORE, uuid, null);

			Predicate predicate = new Predicate(new Reference[] {reference}, _SPACES_STORE, null);

            Content[] readResult = contentService.read(
                predicate, 
                Constants.PROP_CONTENT);
            
            content = AlfrescoContentUtil.getContentAsString(readResult[0]);
		}
		catch (Throwable e) {
			throw new PortalException(e);
		}
		finally {
			try {
				AuthenticationUtils.endSession();
			}
			catch (Throwable e) {
				throw new PortalException(e);
			}
		}
		
		return content;
		
	}
	private static final Store _SPACES_STORE = new Store(StoreEnum.workspace,
		"SpacesStore");

	private static final String _COMPANY_HOME_PATH = "/app:company_home";
}
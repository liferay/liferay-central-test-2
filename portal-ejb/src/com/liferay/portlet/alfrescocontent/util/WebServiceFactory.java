/*
 * Copyright (C) 2005 Alfresco, Inc.
 *
 * Licensed under the Mozilla Public License version 1.1
 * with a permitted attribution clause. You may obtain a
 * copy of the License at
 *
 *   http://www.alfresco.org/legal/license.txt
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific
 * language governing permissions and limitations under the
 * License.
 */
package com.liferay.portlet.alfrescocontent.util;

import javax.xml.rpc.ServiceException;

import org.alfresco.webservice.authentication.AuthenticationServiceLocator;
import org.alfresco.webservice.authentication.AuthenticationServiceSoapBindingStub;
import org.alfresco.webservice.content.ContentServiceLocator;
import org.alfresco.webservice.content.ContentServiceSoapBindingStub;
import org.alfresco.webservice.repository.RepositoryServiceLocator;
import org.alfresco.webservice.repository.RepositoryServiceSoapBindingStub;
import org.alfresco.webservice.util.WebServiceException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 *
 * @author Roy Wetherall
 */
public final class WebServiceFactory {

	public static AuthenticationServiceSoapBindingStub getAuthenticationService(
		String alfrescoWebClientURL) {
		if (_AUTHENTICATION_SERVICE == null) {
			try {
				// Get the authentication service
				AuthenticationServiceLocator locator = new AuthenticationServiceLocator();
				locator
					.setAuthenticationServiceEndpointAddress(alfrescoWebClientURL
						+ _AUTHENTICATION_SERVICE_ADDRESS);
				_AUTHENTICATION_SERVICE = (AuthenticationServiceSoapBindingStub) locator
					.getAuthenticationService();
			}
			catch (ServiceException jre) {
				if (logger.isDebugEnabled() == true) {
					if (jre.getLinkedCause() != null) {
						jre.getLinkedCause().printStackTrace();
					}
				}

				throw new WebServiceException(
					"Error creating authentication service: "
						+ jre.getMessage(), jre);
			}

			// Time out after a minute
			_AUTHENTICATION_SERVICE.setTimeout(60000);
		}

		return _AUTHENTICATION_SERVICE;
	}

	public static RepositoryServiceSoapBindingStub getRepositoryService(String alfrescoWebClientURL) {
		if (_REPOSITORY_SERVICE == null) {
			try {
				// Get the repository service
				RepositoryServiceLocator locator = new RepositoryServiceLocator(
					AuthenticationUtils.getEngineConfiguration());
				locator
					.setRepositoryServiceEndpointAddress(alfrescoWebClientURL
						+ _REPOSITORY_SERVICE_ADDRESS);
				_REPOSITORY_SERVICE = (RepositoryServiceSoapBindingStub) locator
					.getRepositoryService();
			}
			catch (ServiceException jre) {
				if (logger.isDebugEnabled() == true) {
					if (jre.getLinkedCause() != null) {
						jre.getLinkedCause().printStackTrace();
					}
				}

				throw new WebServiceException(
					"Error creating repositoryService service: "
						+ jre.getMessage(), jre);
			}

			// Time out after a minute
			_REPOSITORY_SERVICE.setTimeout(60000);
		}

		return _REPOSITORY_SERVICE;
	}

	public static ContentServiceSoapBindingStub getContentService(String alfrescoWebClientURL) {
		if (_CONTENT_SERVICE == null) {
			try {
				// Get the content service
				ContentServiceLocator locator = new ContentServiceLocator(
					AuthenticationUtils.getEngineConfiguration());
				locator.setContentServiceEndpointAddress(alfrescoWebClientURL
					+ _CONTENT_SERVICE_ADDRESS);
				_CONTENT_SERVICE = (ContentServiceSoapBindingStub) locator
					.getContentService();
			}
			catch (ServiceException jre) {
				if (logger.isDebugEnabled() == true) {
					if (jre.getLinkedCause() != null) {
						jre.getLinkedCause().printStackTrace();
					}
				}

				throw new WebServiceException(
					"Error creating content service: " + jre.getMessage(), jre);
			}

			// Time out after a minute
			_CONTENT_SERVICE.setTimeout(60000);
		}

		return _CONTENT_SERVICE;
	}

	private static Log logger = LogFactory.getLog(WebServiceFactory.class);

	private static final String _AUTHENTICATION_SERVICE_ADDRESS = "/api/AuthenticationService";

	private static final String _REPOSITORY_SERVICE_ADDRESS = "/api/RepositoryService";

	private static final String _CONTENT_SERVICE_ADDRESS = "/api/ContentService";

	private static AuthenticationServiceSoapBindingStub _AUTHENTICATION_SERVICE = null;

	private static RepositoryServiceSoapBindingStub _REPOSITORY_SERVICE = null;

	private static ContentServiceSoapBindingStub _CONTENT_SERVICE = null;
}
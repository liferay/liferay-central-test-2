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

package com.liferay.portal.model;

import com.liferay.portal.service.persistence.ConfiguredProducerPK;

/**
 * <a href="ConfiguredProducerModel.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This interface is a model that represents the <code>ConfiguredProducer</code>
 * table in the database.
 * </p>
 *
 * @author Brian Wing Shun Chan
 *
 * @see com.liferay.portal.model.ConfiguredProducer
 * @see com.liferay.portal.model.impl.ConfiguredProducerImpl
 * @see com.liferay.portal.model.impl.ConfiguredProducerModelImpl
 *
 */
public interface ConfiguredProducerModel extends BaseModel {
	public ConfiguredProducerPK getPrimaryKey();

	public void setPrimaryKey(ConfiguredProducerPK pk);

	public String getPortalId();

	public void setPortalId(String portalId);

	public String getConfiguredProducerId();

	public void setConfiguredProducerId(String configuredProducerId);

	public String getConfiguredProducerName();

	public void setConfiguredProducerName(String configuredProducerName);

	public String getNamespace();

	public void setNamespace(String namespace);

	public String getProducerURL();

	public void setProducerURL(String producerURL);

	public String getProducerVersion();

	public void setProducerVersion(String producerVersion);

	public String getProducerMarkupEndpoint();

	public void setProducerMarkupEndpoint(String producerMarkupEndpoint);

	public int getProducerStatus();

	public void setProducerStatus(int producerStatus);

	public String getRegistrationData();

	public void setRegistrationData(String registrationData);

	public String getRegistrationContext();

	public void setRegistrationContext(String registrationContext);

	public String getServiceDescription();

	public void setServiceDescription(String serviceDescription);

	public String getUserCategoryMapping();

	public void setUserCategoryMapping(String userCategoryMapping);

	public String getCustomUserProfile();

	public void setCustomUserProfile(String customUserProfile);

	public long getSdLastModified();

	public void setSdLastModified(long sdLastModified);

	public String getIdentityPropagationType();

	public void setIdentityPropagationType(String identityPropagationType);

	public int getEntityVersion();

	public void setEntityVersion(int entityVersion);

	public ConfiguredProducer toEscapedModel();
}
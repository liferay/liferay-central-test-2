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

/**
 * The contents of this file are subject to the terms of the Common Development
 * and Distribution License (the License). You may not use this file except in
 * compliance with the License.
 *
 * You can obtain a copy of the License at http://www.sun.com/cddl/cddl.html and
 * legal/CDDLv1.0.txt. See the License for the specific language governing
 * permission and limitations under the License.
 *
 * When distributing Covered Code, include this CDDL Header Notice in each file
 * and include the License file at legal/CDDLv1.0.txt.
 *
 * If applicable, add the following below the CDDL Header, with the fields
 * enclosed by brackets [] replaced by your own identifying information:
 * "Portions Copyrighted [year] [name of copyright owner]"
 *
 * Copyright 2008 Sun Microsystems Inc. All rights reserved.
 */

package com.liferay.wsrp.producer.impl;

import com.liferay.portal.SystemException;
import com.liferay.wsrp.NoSuchProducerException;
import com.liferay.wsrp.model.WSRPProducer;
import com.liferay.wsrp.service.WSRPProducerLocalServiceUtil;

import com.sun.portal.wsrp.common.stubs.v2.ModelDescription;
import com.sun.portal.wsrp.producer.Producer;
import com.sun.portal.wsrp.producer.ProducerException;
import com.sun.portal.wsrp.producer.ProducerManager;

import java.util.ArrayList;
import java.util.List;

/**
 * <a href="ProducerManagerImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Manish Gupta
 *
 */
public class ProducerManagerImpl implements ProducerManager {

	public ProducerManagerImpl(String portalId){
		_portalId = portalId;
	}

	public void addProducer(
			String namespace, String instanceName, boolean status,
			boolean requiresRegistration, boolean supportsInBandRegistration,
			String version, ModelDescription registrationPropertyDescription)
		throws ProducerException {

		try {
			WSRPProducerLocalServiceUtil.addProducer(
				_portalId, true, namespace, instanceName,
				requiresRegistration, supportsInBandRegistration, version,
				null, null, null, _DEFAULT_REGISTRATION_VALIDATOR);
		}
		catch (Exception e) {
			throw new ProducerException(e);
		}
	}

	public boolean areAllProducersDisabled() throws ProducerException {
		//TODO: Global data
		return false;
	}

	public List<Producer> getAllProducers(String namespace)
		throws ProducerException {

		List<Producer> producerImpls = new ArrayList<Producer>();

		if (namespace == null) {
			namespace = _DEFAULT_NAMESPACE;
		}

		try {
			List<WSRPProducer> producers =
				WSRPProducerLocalServiceUtil.getProducersByNamespace(
				_portalId, namespace);

			for (WSRPProducer producer : producers) {
				producerImpls.add(new ProducerImpl(producer));
			}
		}
		catch (Exception e) {
			throw new ProducerException(e);
		}

		return producerImpls;
	}

	public Producer getProducer(String producerKey) throws ProducerException {
		try {
			WSRPProducer producer = _fetchWSRPProducer(producerKey);
			return new ProducerImpl(producer);
		}
		catch (Exception e) {
			throw new ProducerException(e);
		}
	}

	public Producer getProducer(String namespace, String instanceName)
		throws ProducerException {

		return getProducer(instanceName);
	}

	public void removeProducers(String namespace, String[] instanceNames)
		throws ProducerException {

		try {
			for (int i = 0; i < instanceNames.length; i++) {
				WSRPProducer producer = _fetchWSRPProducer(instanceNames[i]);
				WSRPProducerLocalServiceUtil.deleteWSRPProducer(producer);
			}
		}
		catch(Exception e){
			throw new ProducerException(e);
		}
	}

	public void setAllProducersDisabled(boolean value)
			throws ProducerException {

		//TODO: Global data
	}

	public void updateProducer(Producer producer) throws ProducerException {

		if (producer instanceof ProducerImpl) {
			WSRPProducer wsrpProducer = ((ProducerImpl)producer).getModel();

			try {
				WSRPProducerLocalServiceUtil.updateWSRPProducer(wsrpProducer);
			}
			catch (Exception e) {
				throw new ProducerException(e.getMessage());
			}
		}
	}

	private WSRPProducer _fetchWSRPProducer(String key)
		throws SystemException, NoSuchProducerException {

		WSRPProducer producer =
			WSRPProducerLocalServiceUtil.getProducerByKey(key);

		return producer;
	}

	private static final String _DEFAULT_NAMESPACE = "dummyNamespace";
	private static final String _DEFAULT_REGISTRATION_VALIDATOR =
		"com.sun.portal.wsrp.producer." +
			"registration.validator.impl.DefaultRegistrationValidator";

	private String _portalId = null;

}
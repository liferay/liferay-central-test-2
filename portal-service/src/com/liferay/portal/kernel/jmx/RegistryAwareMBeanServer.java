/*
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.kernel.jmx;

import java.io.ObjectInputStream;
import java.util.Set;

import javax.management.Attribute;
import javax.management.AttributeList;
import javax.management.AttributeNotFoundException;
import javax.management.InstanceAlreadyExistsException;
import javax.management.InstanceNotFoundException;
import javax.management.IntrospectionException;
import javax.management.InvalidAttributeValueException;
import javax.management.ListenerNotFoundException;
import javax.management.MBeanException;
import javax.management.MBeanInfo;
import javax.management.MBeanRegistrationException;
import javax.management.MBeanServer;
import javax.management.NotCompliantMBeanException;
import javax.management.NotificationFilter;
import javax.management.NotificationListener;
import javax.management.ObjectInstance;
import javax.management.ObjectName;
import javax.management.OperationsException;
import javax.management.QueryExp;
import javax.management.ReflectionException;
import javax.management.loading.ClassLoaderRepository;

/**
 * <a href="RegistryAwareMBeanServer.java.html"><b><i>View Source</i></b></a>
 *
 * @author Michael C. Han
 */
public class RegistryAwareMBeanServer implements MBeanServer {

	public void setMbeanServer(MBeanServer delegate) {
		_delegate = delegate;
	}

	public void setMbeanRegistry(MBeanRegistry mbeanRegistry) {
		_registry = mbeanRegistry;
	}

	public ObjectInstance createMBean(String className, ObjectName name)
		throws ReflectionException, InstanceAlreadyExistsException,
			   MBeanRegistrationException, MBeanException,
			   NotCompliantMBeanException {

		return _delegate.createMBean(className, name);
	}

	public ObjectInstance createMBean(
			String className, ObjectName name, ObjectName loaderName)
		throws ReflectionException, InstanceAlreadyExistsException,
			   MBeanRegistrationException, MBeanException,
			   NotCompliantMBeanException, InstanceNotFoundException {

		return _delegate.createMBean(className, name, loaderName);
	}

	public ObjectInstance createMBean(
			String className, ObjectName name, Object[]
			params, String[] signature)
		throws ReflectionException, InstanceAlreadyExistsException,
			   MBeanRegistrationException, MBeanException,
			   NotCompliantMBeanException {

		return _delegate.createMBean(className, name, params, signature);
	}

	public ObjectInstance createMBean(
			String className, ObjectName name, ObjectName loaderName,
			Object[] params, String[] signature)
		throws ReflectionException, InstanceAlreadyExistsException,
			   MBeanRegistrationException, MBeanException,
			   NotCompliantMBeanException, InstanceNotFoundException {

		return _delegate.createMBean(
			className, name, loaderName, params, signature);

	}

	public ObjectInstance registerMBean(Object object, ObjectName name)
		throws InstanceAlreadyExistsException, MBeanRegistrationException,
			   NotCompliantMBeanException {

		return _registry.register(name.getCanonicalName(), object, name);
	}

	public void unregisterMBean(ObjectName name)
		throws InstanceNotFoundException, MBeanRegistrationException {

		_registry.unregister(name.getCanonicalName(), name);
	}

	public ObjectInstance getObjectInstance(ObjectName name)
		throws InstanceNotFoundException {

		ObjectName objectName = getPlatformObjectName(name);

		return _delegate.getObjectInstance(objectName);
	}

	public Set<ObjectInstance> queryMBeans(ObjectName name, QueryExp query) {
		return _delegate.queryMBeans(name, query);

	}

	public Set<ObjectName> queryNames(ObjectName name, QueryExp query) {
		return _delegate.queryNames(name, query);

	}

	public boolean isRegistered(ObjectName name) {

		ObjectName objectName = getPlatformObjectName(name);

		return _delegate.isRegistered(objectName);
	}

	public Integer getMBeanCount() {
		return _delegate.getMBeanCount();
	}

	public Object getAttribute(ObjectName name, String attribute)
		throws MBeanException, AttributeNotFoundException,
			   InstanceNotFoundException, ReflectionException {

		ObjectName objectName = getPlatformObjectName(name);

		return _delegate.getAttribute(objectName, attribute);
	}

	public AttributeList getAttributes(ObjectName name, String[] attributes)
		throws InstanceNotFoundException, ReflectionException {

		ObjectName objectName = getPlatformObjectName(name);

		return _delegate.getAttributes(objectName, attributes);
	}

	public void setAttribute(ObjectName name, Attribute attribute)
		throws InstanceNotFoundException, AttributeNotFoundException,
			   InvalidAttributeValueException, MBeanException,
			   ReflectionException {

		ObjectName objectName = getPlatformObjectName(name);

		_delegate.setAttribute(objectName, attribute);
	}

	public AttributeList setAttributes(
		ObjectName name, AttributeList attributes)
		throws InstanceNotFoundException, ReflectionException {

		ObjectName objectName = getPlatformObjectName(name);

		return _delegate.setAttributes(objectName, attributes);
	}

	public Object invoke(
			ObjectName name, String operationName, Object[] params,
			String[] signature)
		throws InstanceNotFoundException, MBeanException, ReflectionException {

		ObjectName objectName = getPlatformObjectName(name);

		return _delegate.invoke(objectName, operationName, params, signature);
	}

	public String getDefaultDomain() {
		return _delegate.getDefaultDomain();		
	}

	public String[] getDomains() {
		return _delegate.getDomains();
	}

	public void addNotificationListener(
			ObjectName name, NotificationListener listener,
			NotificationFilter filter, Object handback)
		throws InstanceNotFoundException {

		ObjectName objectName = getPlatformObjectName(name);

		_delegate.addNotificationListener(
			objectName, listener, filter, handback);
	}

	public void addNotificationListener(
			ObjectName name, ObjectName listener, NotificationFilter filter,
			Object handback)
		throws InstanceNotFoundException {

		ObjectName objectName = getPlatformObjectName(name);
		ObjectName listenerName = getPlatformObjectName(listener);

		_delegate.addNotificationListener(
			objectName, listenerName, filter, handback);

	}

	public void removeNotificationListener(ObjectName name, ObjectName listener)
		throws InstanceNotFoundException, ListenerNotFoundException {

		ObjectName objectName = getPlatformObjectName(name);
		ObjectName listenerName = getPlatformObjectName(listener);

		_delegate.removeNotificationListener(objectName, listenerName);
	}

	public void removeNotificationListener(
			ObjectName name, ObjectName listener, NotificationFilter filter,
			Object handback)
		throws InstanceNotFoundException, ListenerNotFoundException {

		ObjectName objectName = getPlatformObjectName(name);
		ObjectName listenerName = getPlatformObjectName(listener);

		_delegate.removeNotificationListener(
			objectName, listenerName, filter, handback);

	}

	public void removeNotificationListener(
			ObjectName name, NotificationListener listener)
		throws InstanceNotFoundException, ListenerNotFoundException {

		ObjectName objectName = getPlatformObjectName(name);

		_delegate.removeNotificationListener(
			objectName, listener);
	}

	public void removeNotificationListener(
			ObjectName name, NotificationListener listener,
			NotificationFilter filter, Object handback)
		throws InstanceNotFoundException, ListenerNotFoundException {

		ObjectName objectName = getPlatformObjectName(name);

		_delegate.removeNotificationListener(
			objectName, listener, filter, handback);
	}

	public MBeanInfo getMBeanInfo(ObjectName name)
		throws InstanceNotFoundException, IntrospectionException,
			   ReflectionException {

		ObjectName objectName = getPlatformObjectName(name);

		return _delegate.getMBeanInfo(objectName);
	}

	public boolean isInstanceOf(ObjectName name, String className)
		throws InstanceNotFoundException {

		ObjectName objectName = getPlatformObjectName(name);

		return _delegate.isInstanceOf(objectName, className);
	}

	public Object instantiate(String className)
		throws ReflectionException, MBeanException {

		return _delegate.instantiate(className);
	}

	public Object instantiate(String className, ObjectName loaderName)
		throws ReflectionException, MBeanException, InstanceNotFoundException {

		return _delegate.instantiate(className, loaderName);
	}

	public Object instantiate(
		String className, Object[] params, String[] signature)
		throws ReflectionException, MBeanException {

		return _delegate.instantiate(className, params, signature);
	}

	public Object instantiate(
			String className, ObjectName loaderName, Object[] params,
			String[] signature)
		throws ReflectionException, MBeanException, InstanceNotFoundException {

		return _delegate.instantiate(className, loaderName, params, signature);
	}

	@Deprecated
	public ObjectInputStream deserialize(ObjectName name, byte[] data)
		throws InstanceNotFoundException, OperationsException {

		ObjectName objectName = getPlatformObjectName(name);

		return _delegate.deserialize(objectName, data);
	}

	@Deprecated
	public ObjectInputStream deserialize(String className, byte[] data)
		throws OperationsException, ReflectionException {

		return _delegate.deserialize(className, data);
	}

	@Deprecated
	public ObjectInputStream deserialize(
			String className, ObjectName loaderName, byte[] data)
		throws InstanceNotFoundException, OperationsException,
			   ReflectionException {

		return _delegate.deserialize(className, loaderName, data);
	}

	public ClassLoader getClassLoaderFor(ObjectName mbeanName)
		throws InstanceNotFoundException {

		ObjectName objectName = getPlatformObjectName(mbeanName);
		
		return _delegate.getClassLoaderFor(objectName);
	}

	public ClassLoader getClassLoader(ObjectName loaderName)
		throws InstanceNotFoundException {

		return _delegate.getClassLoader(loaderName);
	}

	public ClassLoaderRepository getClassLoaderRepository() {
		return _delegate.getClassLoaderRepository();

	}

	protected ObjectName getPlatformObjectName(ObjectName name) {
		ObjectName objectName = _registry.getObjectName(
			name.getCanonicalName());

		if (objectName == null) {
			objectName = name;
		}
		return objectName;
	}

	private MBeanRegistry _registry;
	private MBeanServer _delegate;
}

/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
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

package com.liferay.osgi.servicetrackermap.internal;

import java.io.File;
import java.io.InputStream;

import java.util.Collection;
import java.util.Dictionary;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleException;
import org.osgi.framework.BundleListener;
import org.osgi.framework.Filter;
import org.osgi.framework.FrameworkListener;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceListener;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;

/**
 * @author Carlos Sierra Andrés
 */
public class BundleContextWrapper implements BundleContext {

    private BundleContext wrapped;

    public BundleContextWrapper(BundleContext bundleContext) {
        if (bundleContext == null)
            throw new IllegalArgumentException();

        this.wrapped = bundleContext;
    }

    @Override
    public String getProperty(String s) {
        return wrapped.getProperty(s);
    }

    @Override
    public Bundle getBundle() {
        return wrapped.getBundle();
    }

    @Override
    public Bundle installBundle(String s, InputStream inputStream) throws BundleException {
        return wrapped.installBundle(s, inputStream);
    }

    @Override
    public Bundle installBundle(String s) throws BundleException {
        return wrapped.installBundle(s);
    }

    @Override
    public Bundle getBundle(long l) {
        return wrapped.getBundle(l);
    }

    @Override
    public Bundle[] getBundles() {
        return wrapped.getBundles();
    }

    @Override
    public void addServiceListener(ServiceListener serviceListener, String s) throws InvalidSyntaxException {
        wrapped.addServiceListener(serviceListener, s);
    }

    @Override
    public void addServiceListener(ServiceListener serviceListener) {
        wrapped.addServiceListener(serviceListener);
    }

    @Override
    public void removeServiceListener(ServiceListener serviceListener) {
        wrapped.removeServiceListener(serviceListener);
    }

    @Override
    public void addBundleListener(BundleListener bundleListener) {
        wrapped.addBundleListener(bundleListener);
    }

    @Override
    public void removeBundleListener(BundleListener bundleListener) {
        wrapped.removeBundleListener(bundleListener);
    }

    @Override
    public void addFrameworkListener(FrameworkListener frameworkListener) {
        wrapped.addFrameworkListener(frameworkListener);
    }

    @Override
    public void removeFrameworkListener(FrameworkListener frameworkListener) {
        wrapped.removeFrameworkListener(frameworkListener);
    }

    @Override
    public ServiceRegistration<?> registerService(String[] strings, Object o, Dictionary<String, ?> stringDictionary) {
        return wrapped.registerService(strings, o, stringDictionary);
    }

    @Override
    public ServiceRegistration<?> registerService(String s, Object o, Dictionary<String, ?> stringDictionary) {
        return wrapped.registerService(s, o, stringDictionary);
    }

    @Override
    public <S> ServiceRegistration<S> registerService(Class<S> sClass, S s, Dictionary<String, ?> stringDictionary) {
        return wrapped.registerService(sClass, s, stringDictionary);
    }

    @Override
    public ServiceReference<?>[] getServiceReferences(String s, String s2) throws InvalidSyntaxException {
        return wrapped.getServiceReferences(s, s2);
    }

    @Override
    public ServiceReference<?>[] getAllServiceReferences(String s, String s2) throws InvalidSyntaxException {
        return wrapped.getAllServiceReferences(s, s2);
    }

    @Override
    public ServiceReference<?> getServiceReference(String s) {
        return wrapped.getServiceReference(s);
    }

    @Override
    public <S> ServiceReference<S> getServiceReference(Class<S> sClass) {
        return wrapped.getServiceReference(sClass);
    }

    @Override
    public <S> Collection<ServiceReference<S>> getServiceReferences(Class<S> sClass, String s)
        throws InvalidSyntaxException {

        return wrapped.getServiceReferences(sClass, s);
    }

    @Override
    public <S> S getService(ServiceReference<S> serviceReference) {
        AtomicInteger serviceReferenceCount = _serviceReferenceCountsMap.get(serviceReference);

        if (serviceReferenceCount == null) {
            serviceReferenceCount = new AtomicInteger(0);

            AtomicInteger previousServiceReferenceCount = _serviceReferenceCountsMap.putIfAbsent(
                serviceReference, serviceReferenceCount);

            if (previousServiceReferenceCount != null) {
                serviceReferenceCount = previousServiceReferenceCount;
            }
        }

        serviceReferenceCount.incrementAndGet();

        return wrapped.getService(serviceReference);
    }

    @Override
    public boolean ungetService(ServiceReference<?> serviceReference) {
        AtomicInteger serviceReferenceCount = _serviceReferenceCountsMap.get(serviceReference);

        if (serviceReferenceCount != null) {
            serviceReferenceCount.decrementAndGet();
        }

        return wrapped.ungetService(serviceReference);
    }

    @Override
    public File getDataFile(String s) {
        return wrapped.getDataFile(s);
    }

    @Override
    public Filter createFilter(String s) throws InvalidSyntaxException {
        return wrapped.createFilter(s);
    }

    @Override
    public Bundle getBundle(String s) {
        return wrapped.getBundle(s);
    }

    private ConcurrentHashMap<ServiceReference<?>, AtomicInteger> _serviceReferenceCountsMap =
        new ConcurrentHashMap<ServiceReference<?>, AtomicInteger>();

    public Map<ServiceReference<?>, AtomicInteger> getServiceReferenceCountsMap() {

        return _serviceReferenceCountsMap;
    }
}

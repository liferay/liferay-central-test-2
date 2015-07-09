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

package com.liferay.ant.bnd.spring;

import aQute.bnd.header.Parameters;
import aQute.bnd.osgi.Analyzer;
import aQute.bnd.osgi.Annotation;
import aQute.bnd.osgi.ClassDataCollector;
import aQute.bnd.osgi.Clazz;
import aQute.bnd.osgi.Descriptors;
import aQute.bnd.osgi.Jar;
import aQute.bnd.osgi.Resource;
import aQute.bnd.osgi.WriteResource;
import aQute.bnd.service.AnalyzerPlugin;

import aQute.lib.io.IO;

import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Miguel Pastor
 */
public class SpringDependencyAnalyzerPlugin implements AnalyzerPlugin {

	@Override
	public boolean analyzeJar(Analyzer analyzer) throws Exception {
		String property = analyzer.getProperty("-service-dependencies");

		Parameters parameters = analyzer.parseHeader(property);

		if (parameters.isEmpty()) {
			return false;
		}

		Jar jar = analyzer.getJar();

		Collection<Clazz> classes = analyzer.getClasses();

		for (String key : parameters.keySet()) {
			ServiceReferenceCollector serviceReferenceCollector =
				new ServiceReferenceCollector(key);

			for (Clazz clazz : classes) {
				clazz.parseClassFileWithCollector(serviceReferenceCollector);
			}

			jar.putResource(
				"OSGI-INF/context/context.dependencies",
				new ContextDependencyWriter(
					analyzer,
					serviceReferenceCollector.getServiceReferences()));
		}

		return false;
	}

	private static class ContextDependencyWriter extends WriteResource {

		public ContextDependencyWriter(
			Analyzer analyzer, Set<String> serviceReferences) {

			Jar jar = analyzer.getJar();

			_resource = jar.getResource("META-INF/spring/context.dependencies");

			_serviceReferences = serviceReferences;
		}

		@Override
		public long lastModified() {
			return 0;
		}

		@Override
		public void write(OutputStream out) throws Exception {
			String contextDependencies = "";

			if (_resource != null) {
				contextDependencies = IO.collect(
					_resource.openInputStream(), "UTF-8");
			}

			PrintWriter pw = new PrintWriter
				(new OutputStreamWriter(out, "UTF-8"));

			if (!contextDependencies.equals("")) {
				pw.println(contextDependencies);
			}

			for (String serviceReference : _serviceReferences) {
				pw.println(serviceReference);
			}

			pw.flush();
		}

		private final Resource _resource;
		private final Set<String> _serviceReferences;

	}

	private static class ServiceReferenceCollector extends ClassDataCollector {

		public ServiceReferenceCollector(String annotationFQN) {
			_annotationFQN = annotationFQN;
		}

		@Override
		public void annotation(Annotation annotation) throws Exception {
			Descriptors.TypeRef typeRef = annotation.getName();

			String fqn = typeRef.getFQN();

			if (!_annotationFQN.equals(fqn)) {
				return;
			}

			Descriptors.Descriptor descriptor = _fieldDef.getDescriptor();

			typeRef = descriptor.getType();

			_serviceReferences.add(typeRef.getFQN());
		}

		@Override
		public void field(Clazz.FieldDef fieldDef) {
			_fieldDef = fieldDef;
		}

		public Set<String> getServiceReferences() {
			return _serviceReferences;
		}

		private final String _annotationFQN;
		private Clazz.FieldDef _fieldDef;
		private final Set<String> _serviceReferences = new HashSet<String>();

	}

}
/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.kernel.lar;

import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.ClassedModel;
import com.liferay.portal.model.Portlet;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Mate Thurzo
 * @author Zsolt Berentey
 */
public class ManifestSummary implements Serializable {

	public static String getManifestSummaryKey(
		String modelName, String referrerModelName) {

		if (Validator.isNull(referrerModelName) ||
			modelName.equals(referrerModelName)) {

			return modelName;
		}

		return referrerModelName.concat(StringPool.POUND).concat(modelName);
	}

	public void addDataPortlet(Portlet portlet) {
		_dataPortlets.add(portlet);
	}

	public void addModelAdditionCount(
		Class<? extends ClassedModel> clazz,
		Class<? extends ClassedModel> referrerClass, long count) {

		addModelAdditionCount(clazz.getName(), referrerClass.getName(), count);
	}

	public void addModelAdditionCount(
		Class<? extends ClassedModel> clazz, long modelCount) {

		addModelAdditionCount(clazz, clazz, modelCount);
	}

	public void addModelAdditionCount(String manifestSummaryKey, long count) {
		_modelAdditionCounters.put(manifestSummaryKey, count);
	}

	public void addModelAdditionCount(
		String className, String referrerClassName, long count) {

		String manifestSummaryKey = getManifestSummaryKey(
			className, referrerClassName);

		addModelAdditionCount(manifestSummaryKey, count);
	}

	public void addModelDeletionCount(
		Class<? extends ClassedModel> clazz, long count) {

		addModelDeletionCount(clazz.getName(), count);
	}

	public void addModelDeletionCount(String modelName, long count) {
		_modelDeletionCounters.put(modelName, count);

		_modelNames.add(modelName);
	}

	public void addSetupPortlet(Portlet portlet) {
		_setupPortlets.add(portlet);
	}

	public List<Portlet> getDataPortlets() {
		return _dataPortlets;
	}

	public Date getExportDate() {
		return _exportDate;
	}

	public long getModelAdditionCount(Class<? extends ClassedModel> clazz) {
		return getModelAdditionCount(clazz, clazz);
	}

	public long getModelAdditionCount(
		Class<? extends ClassedModel> clazz,
		Class<? extends ClassedModel> referrerClass) {

		return getModelAdditionCount(clazz.getName(), referrerClass.getName());
	}

	public long getModelAdditionCount(String manifestSummaryKey) {
		if (!_modelAdditionCounters.containsKey(manifestSummaryKey)) {
			return -1;
		}

		return _modelAdditionCounters.get(manifestSummaryKey);
	}

	public long getModelAdditionCount(
		String className, String referrerClassName) {

		String manifestSummaryKey = getManifestSummaryKey(
			className, referrerClassName);

		return getModelAdditionCount(manifestSummaryKey);
	}

	public Map<String, Long> getModelAdditionCounters() {
		return _modelAdditionCounters;
	}

	public long getModelDeletionCount(Class<? extends ClassedModel> clazz) {
		return getModelDeletionCount(clazz.getName());
	}

	public long getModelDeletionCount(String modelName) {
		if (!_modelDeletionCounters.containsKey(modelName)) {
			return -1;
		}

		return _modelDeletionCounters.get(modelName);
	}

	public Map<String, Long> getModelDeletionCounters() {
		return _modelDeletionCounters;
	}

	public Collection<String> getModelNames() {
		return _modelNames;
	}

	public List<Portlet> getSetupPortlets() {
		return _setupPortlets;
	}

	public void incrementModelAdditionCount(
		Class<? extends ClassedModel> clazz) {

		incrementModelAdditionCount(clazz, clazz);
	}

	public void incrementModelAdditionCount(
		Class<? extends ClassedModel> clazz,
		Class<? extends ClassedModel> referrerClass) {

		String manifestSummaryKey = getManifestSummaryKey(
			clazz.getName(), referrerClass.getName());

		incrementModelAdditionCount(manifestSummaryKey);
	}

	public void incrementModelAdditionCount(String manifestSummaryKey) {
		if (!_modelAdditionCounters.containsKey(manifestSummaryKey)) {
			_modelAdditionCounters.put(manifestSummaryKey, 1L);

			_modelNames.add(manifestSummaryKey);

			return;
		}

		long modelAdditionCounter = _modelAdditionCounters.get(
			manifestSummaryKey);

		_modelAdditionCounters.put(
			manifestSummaryKey, modelAdditionCounter + 1);
	}

	public void incrementModelDeletionCount(
		Class<? extends ClassedModel> clazz) {

		incrementModelDeletionCount(clazz.getName());
	}

	public void incrementModelDeletionCount(String modelName) {
		if (!_modelDeletionCounters.containsKey(modelName)) {
			_modelDeletionCounters.put(modelName, 1L);

			_modelNames.add(modelName);

			return;
		}

		long modelDeletionCounter = _modelDeletionCounters.get(modelName);

		_modelDeletionCounters.put(modelName, modelDeletionCounter + 1);
	}

	public void setExportDate(Date exportDate) {
		_exportDate = exportDate;
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(5);

		sb.append("{modelAdditionCounters=");
		sb.append(MapUtil.toString(_modelAdditionCounters));
		sb.append(", modelDeletionCounters=");
		sb.append(MapUtil.toString(_modelDeletionCounters));
		sb.append("}");

		return sb.toString();
	}

	private List<Portlet> _dataPortlets = new ArrayList<Portlet>();
	private Date _exportDate;
	private Map<String, Long> _modelAdditionCounters =
		new HashMap<String, Long>();
	private Map<String, Long> _modelDeletionCounters =
		new HashMap<String, Long>();
	private Set<String> _modelNames = new HashSet<String>();
	private List<Portlet> _setupPortlets = new ArrayList<Portlet>();

}
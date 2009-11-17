package com.liferay.documentlibrary.util;

import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.service.ServiceContext;

import java.io.IOException;
import java.io.InputStream;

import java.util.Date;

public class HookWriterThread implements Runnable{

	private static final String _ADD_FILE = "addFile";
	private static final String _UPDATE_FILE = "updateFile";

	public HookWriterThread(Hook hook) {
		_hook = hook;
	}

	public void addFile(
			long companyId, String portletId, long groupId,
			long repositoryId, String fileName, long fileEntryId,
			String properties, Date modifiedDate,
			ServiceContext serviceContext, InputStream is)
		throws PortalException, SystemException {

		_method = _ADD_FILE;

		_companyId = companyId;
		_portletId = portletId;
		_groupId = groupId;
		_repositoryId = repositoryId;
		_fileName = fileName;
		_fileEntryId = fileEntryId;
		_properties = properties;
		_modifiedDate = modifiedDate;
		_serviceContext = serviceContext;
		_is = is;
	}

	public void updateFile(
			long companyId, String portletId, long groupId,
			long repositoryId, String fileName, double versionNumber,
			String sourceFileName, long fileEntryId, String properties,
			Date modifiedDate, ServiceContext serviceContext,
			InputStream is)
		throws PortalException, SystemException {

		_method = _UPDATE_FILE;

		_companyId = companyId;
		_portletId = portletId;
		_groupId = groupId;
		_repositoryId = repositoryId;
		_fileName = fileName;
		_versionNumber = versionNumber;
		_sourceFileName = sourceFileName;
		_fileEntryId = fileEntryId;
		_properties = properties;
		_modifiedDate = modifiedDate;
		_serviceContext = serviceContext;
		_is = is;
	}

	public void run() {
		try {
			BaseHook baseHook = (BaseHook)_hook;

			if (_method.equals(_ADD_FILE)) {
				baseHook.addFileImpl(
					_companyId, _portletId, _groupId, _repositoryId,
					_fileName, _fileEntryId, _properties, _modifiedDate,
					_serviceContext, _is);
			}
			else if (_method.equals(_UPDATE_FILE)) {
				baseHook.updateFileImpl(
					_companyId, _portletId, _groupId, _repositoryId,
					_fileName, _versionNumber, _sourceFileName,
					_fileEntryId, _properties, _modifiedDate,
					_serviceContext, _is);
			}
		}
		catch (Exception e) {
			_log.error(e);
		}
		finally {
			try {
				_is.close();
			}
			catch (IOException ioe) {
				_log.error(ioe);
			}
		}
	}

	private static Log _log = LogFactoryUtil.getLog(HookWriterThread.class);

	private String _method;
	private Hook _hook;
	private long _companyId;
	private String _portletId;
	private long _groupId;
	private long _repositoryId;
	private String _fileName;
	private double _versionNumber;
	private String _sourceFileName;
	private long _fileEntryId;
	private String _properties;
	private Date _modifiedDate;
	private ServiceContext _serviceContext;
	private InputStream _is;

}
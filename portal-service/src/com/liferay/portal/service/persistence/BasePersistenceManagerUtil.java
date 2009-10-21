
package com.liferay.portal.service.persistence;

import com.liferay.portal.NoSuchModelException;
import com.liferay.portal.SystemException;
import com.liferay.portal.model.BaseModel;


/**
 * <a href="BasePersistenceManagerUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author Micha Kiener
 */
public class BasePersistenceManagerUtil {

	public static <T extends BaseModel<T>> T findByIdentity(
		ModelIdentity modelIdentity)
		throws NoSuchModelException, SystemException {

		return _basePersistenceManager.findByIdentity(modelIdentity);
	}

	public static <T extends BaseModel<T>> ModelIdentity getIdentity(T entity) {

		return _basePersistenceManager.getIdentity(entity);
	}

	public static <T extends BaseModel<T>> T update(T entity, boolean merge)
		throws SystemException {

		return _basePersistenceManager.update(entity, merge);
	}
	
	public void setBasePersisteceManager(
		BasePersistenceManager basePersistenceManager) {

		_basePersistenceManager = basePersistenceManager;
	}

	private static BasePersistenceManager _basePersistenceManager;
}

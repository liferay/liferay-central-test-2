
package com.liferay.portal.service.persistence;

import com.liferay.portal.NoSuchModelException;
import com.liferay.portal.SystemException;
import com.liferay.portal.model.BaseModel;

/**
 * <a href="BasePersistenceManager.java.html"><b><i>View Source</i></b></a>
 * 
 * <p>
 * This utility class is able to persist and load any type of entity instance
 * managed by service builder, even entities from the portal or any plugin.
 * </p>
 * 
 * @author Micha Kiener
 * 
 */
public interface BasePersistenceManager {
	
	public <T extends BaseModel<T>> T findByIdentity(ModelIdentity modelIdentity)
		throws NoSuchModelException, SystemException;
	
	public <T extends BaseModel<T>> ModelIdentity getIdentity(T entity);

	public <T extends BaseModel<T>> T update(T entity, boolean merge)
		throws SystemException;
}

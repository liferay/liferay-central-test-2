<#include "copyright.txt" parse="false">

package ${baseModelImplPackage};

import com.liferay.portal.model.BaseModel;
import ${propsUtilPackage}.PropsUtil;
import com.liferay.portal.kernel.util.GetterUtil;

/**
 * <a href="BaseModelImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public abstract class BaseModelImpl implements BaseModel {

	public BaseModelImpl() {
	}

	public boolean isNew() {
		return _new;
	}

	public boolean setNew(boolean n) {
		return _new = n;
	}

	public abstract Object clone();

	private boolean _new;

}
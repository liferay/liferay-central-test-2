package com.liferay.portal.util.comparator;

import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.model.Resource;

public class ResourceComparator extends OrderByComparator {

	public static String ORDER_BY_DESC = "resourceId DESC";

	public int compare(Object obj1, Object obj2) {
		Resource rsrc1 = (Resource)obj1;
		Resource rsrc2 = (Resource)obj2;

		long resourceId1 = rsrc1.getResourceId();
		long resourceId2 = rsrc2.getResourceId();

		if (resourceId1 > resourceId2) {
			return -1;
		}
		else if (resourceId1 < resourceId2) {
			return 1;
		}
		else {
			return 0;
		}
	}

	public String getOrderBy() {
		return ORDER_BY_DESC;
	}

}
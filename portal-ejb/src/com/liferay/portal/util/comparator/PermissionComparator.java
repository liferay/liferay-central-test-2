package com.liferay.portal.util.comparator;

import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.model.Permission;

public class PermissionComparator extends OrderByComparator {

	public static String ORDER_BY_DESC = "permissionId DESC";

	public int compare(Object obj1, Object obj2) {
		Permission perm1 = (Permission)obj1;
		Permission perm2 = (Permission)obj2;

		long permissionId1 = perm1.getPermissionId();
		long permissionId2 = perm2.getPermissionId();

		if (permissionId1 > permissionId2) {
			return -1;
		}
		else if (permissionId1 < permissionId2) {
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
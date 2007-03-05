package com.liferay.portal.upgrade.v4_3_0.util;

import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.upgrade.util.ValueMapper;
import com.liferay.util.StringUtil;

public class PrimKeyGroupOrOwnerIdMapper implements ValueMapper {

	public PrimKeyGroupOrOwnerIdMapper(ValueMapper mapper, boolean isGroupId) {
		_mapper = mapper;

		if (isGroupId) {
			_key = "groupId=";
		}
		else {
			_key = "ownerId=";
		}
	}

	public void appendException(Object exception) {
	}

	public Object getNewValue(Object oldValue) throws Exception {
		String primKey = (String)oldValue;

		// {layoutId=1234, ownerId=PRI.5678}

		String[] keyParts = StringUtil.split(
			primKey.substring(1, primKey.length() - 1),
			StringPool.COMMA + StringPool.SPACE);

		for (int i = 0; i < keyParts.length; i++) {
			String[] kvp = StringUtil.split(keyParts[i], StringPool.EQUAL);

			if (kvp[0].equals(_key)) {
				kvp[1] =
					String.valueOf(_mapper.getNewValue(new Long(kvp[1])));

				keyParts[i] = StringUtil.merge(kvp, StringPool.EQUAL);
			}
		}

		primKey =
			StringPool.OPEN_CURLY_BRACE +
			StringUtil.merge(keyParts, StringPool.COMMA + StringPool.SPACE) +
			StringPool.CLOSE_CURLY_BRACE;

		return primKey;
	}

	public void mapValue(Object oldValue, Object newValue) throws Exception {
	}

	private ValueMapper _mapper;
	private String _key;

}
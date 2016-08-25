#! /bin/sh

set -e

TEMP_LIBS='adaptive-media-image-test/libs/com.liferay.item.selector.api'

for m in ${TEMP_LIBS}; do
	(cp ${m}.jar ../bundles/deploy)
done
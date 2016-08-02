#! /bin/sh

set -e

PREFIX='adaptive-media-'
MODULES='api image-impl document-library web'

for m in ${MODULES}; do
	(cd ${PREFIX}${m} && ../gradlew deploy)
done
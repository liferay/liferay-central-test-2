#! /bin/sh

set -e

PREFIX='adaptive-media-'
MODULES='api document-library image-impl image-jax-rs image-item-selector-api image-js web'

for m in ${MODULES}; do
	(cd ${PREFIX}${m} && ../scripts/run.sh ../gradlew deploy)
done

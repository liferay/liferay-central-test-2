#! /bin/sh

set -e

PREFIX='adaptive-media-'
MODULES='api document-library image-impl image-item-selector-api image-jax-rs image-js web'

for m in ${MODULES}; do
	(cd ${PREFIX}${m} && ../scripts/run.sh ../gradlew deploy)
done
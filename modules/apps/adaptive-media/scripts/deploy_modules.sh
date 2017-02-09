#! /bin/sh

set -e

for module in `ls -d */ | grep adaptive-media | grep -v test | grep -v demo`; do
	(cd ${module} && ../scripts/run.sh ../gradlew deploy)
done

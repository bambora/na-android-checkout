#!/bin/bash
#setup script
set -e
scriptDir="$(dirname "$0")"
scriptName="$(basename "$0")"

pushd $scriptDir

#log
now=$(date +"%y%m%d-%H%M%S")
logDir="$scriptDir/logs"
logName="$scriptName.log"
mkdir -p "$logDir"
exec > >(tee "$logDir/$logName")

echo "-----------------------------"
echo "Script: $scriptName"
echo "Time: $now"
echo "-----------------------------"
echo

echo "-----------------------------"
echo "Cleaning..."
echo "-----------------------------"
bash ./gradlew clean 
echo

echo "-----------------------------"
echo "Publishing..."
echo "-----------------------------"
bash ./gradlew assembleRelease artifactoryPublish
echo

echo "-----------------------------"
echo

popd


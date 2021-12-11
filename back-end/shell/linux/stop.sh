#!/bin/bash
processId=$(cat ./process.pid)
kill -9 "$processId"
rm -rf ./process.pid
echo "GuGu_Simple_Upload Stop Successful!"
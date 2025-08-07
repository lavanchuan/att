#!/bin/bash

while true; do
    wrk -t10 -c1000 -d5s -R20000 -s header.lua https://tanmong.online;
    sleep 0;
done
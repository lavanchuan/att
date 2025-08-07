#!/bin/bash

# Gửi 10.000 request đồng thời tới địa chỉ cụ thể
while true; do
    ab -n 10000 -c 10000 https://tanmong.online/
    sleep 5
done

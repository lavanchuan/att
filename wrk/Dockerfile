FROM alpine:latest

# Cài wrk và bash, lua
RUN apk add --no-cache wrk bash lua5.3

# Copy lua script vào container
COPY header.lua header.lua

ENTRYPOINT ["bash"]

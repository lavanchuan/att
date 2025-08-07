FROM alpine:latest

# Cài wrk và bash, lua
RUN apk add --no-cache wrk bash lua5.3

# Copy lua script vào container
COPY header.lua header.lua
COPY att.sh att.sh

RUN chmod +x att.sh
RUN chmod +x header.lua

CMD ["./att.sh"]

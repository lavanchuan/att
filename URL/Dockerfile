FROM ubuntu:22.04

RUN apt-get update && apt-get install -y curl && rm -rf /var/lib/apt/lists/*

COPY att.sh /att.sh
RUN chmod +x /att.sh

CMD ["/att.sh"]

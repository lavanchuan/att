FROM debian:bullseye-slim

# Cài Apache Benchmark (ab) và các tiện ích cơ bản
RUN apt-get update && \
    apt-get install -y apache2-utils curl && \
    apt-get clean && rm -rf /var/lib/apt/lists/*

# Copy script nếu cần (hoặc chạy ab trực tiếp từ CMD)
COPY att.sh /att.sh
RUN chmod +x /att.sh

CMD ["/att.sh"]

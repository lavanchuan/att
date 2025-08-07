# Danh sách User-Agent phổ biến
user_agents=(
  "Mozilla/5.0 (Windows NT 10.0; Win64; x64)"
  "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7)"
  "Mozilla/5.0 (X11; Linux x86_64)"
  "Mozilla/5.0 (iPhone; CPU iPhone OS 14_0 like Mac OS X)"
  "Mozilla/5.0 (Linux; Android 11; SM-G991B)"
  "Mozilla/5.0 (Windows NT 6.1; WOW64)"
  "Mozilla/5.0 (iPad; CPU OS 13_2 like Mac OS X)"
)

# URL đích
target_url="https://tanmong.online"

# File cookie (tùy chọn, nếu muốn giữ session)
cookie_file="cookies.txt"

# Vòng lặp vô hạn
while true; do
  # Random chọn một User-Agent
  ua=${user_agents[$RANDOM % ${#user_agents[@]}]}

  # Gửi request
  curl -s \
    -A "$ua" \
    -b "$cookie_file" -c "$cookie_file" \
    -H "Accept: text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8" \
    -H "Accept-Language: en-US,en;q=0.5" \
    -H "Connection: keep-alive" \
    -H "Referer: https://google.com/" \
    --compressed \
    -o /dev/null \
    -w "[$(date '+%T')] HTTP: %{http_code} | UA: $ua\n" \
    "$target_url"

  # Random thời gian chờ từ 0.1 → 0.3 giây
  sleep_time=$(awk -v min=0.1 -v max=0.3 'BEGIN { srand(); print min + rand() * (max - min) }')
  sleep "$sleep_time"
done

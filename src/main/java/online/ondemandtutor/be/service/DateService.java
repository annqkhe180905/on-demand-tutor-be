package online.ondemandtutor.be.service;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Service
public class DateService {
    public static LocalDateTime getCurrentLocalDateTime() {
        return LocalDateTime.now();
    }

    // Lấy thời gian hiện tại dưới dạng Date (java.util.Date) nếu cần tương thích
    public static Date getCurrentDate() {
        return Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant());
    }
}

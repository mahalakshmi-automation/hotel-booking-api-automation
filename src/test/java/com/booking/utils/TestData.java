package com.booking.utils;
import com.booking.models.BookingDates;
import com.booking.models.BookingRequest;
import com.booking.models.LoginRequest;
public final class TestData {
    private TestData() {}
    public static BookingRequest booking() {
        return new BookingRequest(Config.getInt("booking.roomid"), "John", "Doe", true,
            new BookingDates("2027-03-10", "2027-03-12"), "john.doe@example.com", "98765432101");
    }
    public static LoginRequest credentials() {
        return new LoginRequest(Config.get("auth.username"), Config.get("auth.password"));
    }
}

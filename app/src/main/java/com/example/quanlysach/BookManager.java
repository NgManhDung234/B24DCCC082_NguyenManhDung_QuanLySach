package com.example.quanlysach;

import java.util.ArrayList;

public class BookManager {

    public static ArrayList<Book> books = new ArrayList<>();

    private static boolean initialized = false;

    public static void initialize() {
        if (initialized) {
            return;
        }

        books.add(new Book(
                "Lập trình Android cơ bản",
                "Nguyễn Văn A",
                "Công nghệ",
                "Đang đọc",
                4,
                ""
        ));

        books.add(new Book(
                "Đắc nhân tâm",
                "Dale Carnegie",
                "Kỹ năng sống",
                "Đã đọc",
                5,
                ""
        ));

        books.add(new Book(
                "Dế Mèn phiêu lưu ký",
                "Tô Hoài",
                "Văn học",
                "Chưa đọc",
                0,
                ""
        ));

        initialized = true;
    }

    public static int getTotalBooks() {
        return books.size();
    }

    public static int getReadBooks() {
        int count = 0;

        for (Book book : books) {
            if (book.getStatus().equals("Đã đọc")) {
                count++;
            }
        }

        return count;
    }

    public static int getUnfinishedBooks() {
        int count = 0;

        for (Book book : books) {
            if (book.getStatus().equals("Chưa đọc")
                    || book.getStatus().equals("Đang đọc")) {
                count++;
            }
        }

        return count;
    }
}


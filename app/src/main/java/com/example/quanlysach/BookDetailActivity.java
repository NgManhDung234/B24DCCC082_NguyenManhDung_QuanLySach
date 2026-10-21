package com.example.quanlysach;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class BookDetailActivity extends AppCompatActivity {

    private TextView tvTitle, tvAuthorGenre, tvStatus, tvNote;
    private RatingBar rbDetailRating;
    private Button btnMarkRead, btnBack, btnDelete;

    private Book book;
    private int bookIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_detail);

        tvTitle = findViewById(R.id.tvTitle);
        tvAuthorGenre = findViewById(R.id.tvAuthorGenre);
        tvStatus = findViewById(R.id.tvStatus);
        tvNote = findViewById(R.id.tvNote);
        rbDetailRating = findViewById(R.id.rbDetailRating);
        btnMarkRead = findViewById(R.id.btnMarkRead);
        btnBack = findViewById(R.id.btnBack);
        btnDelete = findViewById(R.id.btnDelete);

        bookIndex = getIntent().getIntExtra("bookIndex", -1);

        if (bookIndex < 0 || bookIndex >= BookManager.books.size()) {
            Toast.makeText(this, "Không tìm thấy sách", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        book = BookManager.books.get(bookIndex);

        displayBook();

        rbDetailRating.setOnRatingBarChangeListener(
                (ratingBar, rating, fromUser) -> {
                    if (fromUser) {
                        book.setRating(rating);
                    }
                }
        );

        btnMarkRead.setOnClickListener(v -> {
            book.setStatus("Đã đọc");
            displayBook();

            Toast.makeText(
                    this,
                    "Đã cập nhật tình trạng đọc",
                    Toast.LENGTH_SHORT
            ).show();
        });

        btnBack.setOnClickListener(v -> finish());

        btnDelete.setOnClickListener(v -> {
            BookManager.books.remove(bookIndex);

            Toast.makeText(
                    this,
                    "Đã xóa sách",
                    Toast.LENGTH_SHORT
            ).show();

            finish();
        });
    }

    private void displayBook() {
        tvTitle.setText(book.getTitle());

        tvAuthorGenre.setText(
                "Tác giả: " + book.getAuthor()
                        + "\nThể loại: " + book.getGenre()
        );

        tvStatus.setText("Tình trạng: " + book.getStatus());

        String note = book.getNote();

        if (note == null || note.trim().isEmpty()) {
            tvNote.setText("Ghi chú: Không có");
        } else {
            tvNote.setText("Ghi chú: " + note);
        }

        rbDetailRating.setRating(book.getRating());

        if ("Đã đọc".equals(book.getStatus())) {
            btnMarkRead.setEnabled(false);
            btnMarkRead.setText("ĐÃ ĐỌC");
        } else {
            btnMarkRead.setEnabled(true);
            btnMarkRead.setText("ĐÁNH DẤU ĐÃ ĐỌC");
        }
    }
}

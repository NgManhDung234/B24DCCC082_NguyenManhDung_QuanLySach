package com.example.quanlysach;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private TextView tvStatistics;
    private ListView lvBooks;
    private Button btnAddBook;
    private BookAdapter adapter;

    private final int GREEN = Color.rgb(0, 127, 115);
    private final int DARK = Color.rgb(37, 37, 37);
    private final int GRAY = Color.rgb(117, 117, 117);
    private final int ORANGE = Color.rgb(255, 165, 0);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvStatistics = findViewById(R.id.tvStatistics);
        lvBooks = findViewById(R.id.lvBooks);
        btnAddBook = findViewById(R.id.btnAddBook);

        BookManager.initialize();

        adapter = new BookAdapter();
        lvBooks.setAdapter(adapter);

        btnAddBook.setOnClickListener(v -> {
            Intent intent = new Intent(
                    MainActivity.this,
                    AddBookActivity.class
            );
            startActivity(intent);
        });

        lvBooks.setOnItemClickListener((parent, view, position, id) -> {
            Intent intent = new Intent(
                    MainActivity.this,
                    BookDetailActivity.class
            );
            intent.putExtra("bookIndex", position);
            startActivity(intent);
        });

        updateBookList();
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (lvBooks != null) {
            updateBookList();
        }
    }

    private void updateBookList() {
        adapter.notifyDataSetChanged();

        String statistics =
                "Tổng số sách: " + BookManager.getTotalBooks()
                        + "\nĐã đọc: " + BookManager.getReadBooks()
                        + "\nChưa đọc xong: " + BookManager.getUnfinishedBooks();

        tvStatistics.setText(statistics);
        tvStatistics.setTextColor(DARK);
    }

    private class BookAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return BookManager.books.size();
        }

        @Override
        public Object getItem(int position) {
            return BookManager.books.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            View view = convertView;

            if (view == null) {
                view = LayoutInflater.from(MainActivity.this)
                        .inflate(R.layout.item_book, parent, false);
            }

            Book book = BookManager.books.get(position);

            TextView tvTitle = view.findViewById(R.id.tvBookTitle);
            TextView tvAuthor = view.findViewById(R.id.tvBookAuthor);
            TextView tvGenre = view.findViewById(R.id.tvBookGenre);
            TextView tvStatus = view.findViewById(R.id.tvBookStatus);
            TextView tvRating = view.findViewById(R.id.tvBookRating);

            tvTitle.setText(book.getTitle());
            tvTitle.setTextColor(DARK);
            tvTitle.setTypeface(null, Typeface.BOLD);

            tvAuthor.setText("Tác giả: " + book.getAuthor());
            tvAuthor.setTextColor(GRAY);

            tvGenre.setText(book.getGenre());
            tvGenre.setTextColor(GREEN);
            tvGenre.setBackground(makeBackground(
                    Color.rgb(221, 243, 239),
                    18
            ));

            String status = book.getStatus();
            tvStatus.setText(status);

            int statusTextColor;
            int statusBackground;

            if (status != null && status.contains("Đã đọc")) {
                statusTextColor = Color.rgb(46, 125, 50);
                statusBackground = Color.rgb(232, 245, 233);
            } else if (status != null && status.contains("Đang đọc")) {
                statusTextColor = Color.rgb(230, 126, 34);
                statusBackground = Color.rgb(255, 240, 221);
            } else {
                statusTextColor = GRAY;
                statusBackground = Color.rgb(238, 238, 238);
            }

            tvStatus.setTextColor(statusTextColor);
            tvStatus.setBackground(makeBackground(statusBackground, 18));

            int rating = Math.max(0, Math.min(5,
                    Math.round(book.getRating())));

            if (rating == 0) {
                tvRating.setText("☆☆☆☆☆");
                tvRating.setTextColor(GRAY);
            } else {
                StringBuilder stars = new StringBuilder();

                for (int i = 0; i < 5; i++) {
                    stars.append(i < rating ? "★" : "☆");
                }

                tvRating.setText(stars.toString());
                tvRating.setTextColor(ORANGE);
            }

            tvRating.setTextSize(17);

            return view;
        }

        private GradientDrawable makeBackground(int color, int radiusDp) {
            GradientDrawable drawable = new GradientDrawable();
            drawable.setColor(color);
            drawable.setCornerRadius(
                    radiusDp * getResources().getDisplayMetrics().density
            );
            return drawable;
        }
    }
}


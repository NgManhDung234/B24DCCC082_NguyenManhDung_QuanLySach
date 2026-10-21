package com.example.quanlysach;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AddBookActivity extends AppCompatActivity {

    private EditText edtTitle, edtAuthor, edtNote;
    private Spinner spGenre;
    private RadioGroup rgStatus;
    private RatingBar rbRating;
    private Button btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);

        edtTitle = findViewById(R.id.edtTitle);
        edtAuthor = findViewById(R.id.edtAuthor);
        edtNote = findViewById(R.id.edtNote);
        spGenre = findViewById(R.id.spGenre);
        rgStatus = findViewById(R.id.rgStatus);
        rbRating = findViewById(R.id.rbRating);
        btnSave = findViewById(R.id.btnSave);

        String[] genres = {
                "Văn học",
                "Khoa học",
                "Công nghệ",
                "Kỹ năng sống",
                "Khác"
        };

        ArrayAdapter<String> genreAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                genres
        );

        genreAdapter.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item
        );

        spGenre.setAdapter(genreAdapter);

        btnSave.setOnClickListener(v -> saveBook());
    }

    private void saveBook() {
        String title = edtTitle.getText().toString().trim();
        String author = edtAuthor.getText().toString().trim();
        String genre = spGenre.getSelectedItem().toString();
        String note = edtNote.getText().toString().trim();

        if (title.isEmpty()) {
            edtTitle.setError("Vui lòng nhập tên sách");
            edtTitle.requestFocus();
            return;
        }

        if (author.isEmpty()) {
            edtAuthor.setError("Vui lòng nhập tên tác giả");
            edtAuthor.requestFocus();
            return;
        }

        int selectedId = rgStatus.getCheckedRadioButtonId();
        String status;

        if (selectedId == R.id.rbReading) {
            status = "Đang đọc";
        } else if (selectedId == R.id.rbRead) {
            status = "Đã đọc";
        } else {
            status = "Chưa đọc";
        }

        float rating = rbRating.getRating();

        Book book = new Book(
                title,
                author,
                genre,
                status,
                rating,
                note
        );

        BookManager.books.add(book);

        Toast.makeText(
                this,
                "Đã thêm sách thành công",
                Toast.LENGTH_SHORT
        ).show();

        finish();
    }
}

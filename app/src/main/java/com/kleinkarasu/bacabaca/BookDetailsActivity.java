package com.kleinkarasu.bacabaca;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.kleinkarasu.bacabaca.Model.Book;
import com.squareup.picasso.Picasso;

public class BookDetailsActivity extends AppCompatActivity {
    Book book;
    DatabaseReference dbBook;
    TextView tvTitle, tvAuthor, tvOverview, tvCategory;
    ImageView imgCover;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_details);

        tvTitle = (TextView) findViewById(R.id.tv_title);
        tvAuthor = (TextView) findViewById(R.id.tv_author);
        tvOverview = (TextView) findViewById(R.id.tv_overview);
        tvCategory = (TextView) findViewById(R.id.tv_category);
        imgCover = (ImageView) findViewById(R.id.img_cover);

        Intent intent = getIntent();
        String bookTitle = intent.getStringExtra("book");

        dbBook  = FirebaseDatabase.getInstance().getReference("Books").child(bookTitle);
        dbBook.addListenerForSingleValueEvent(valueEventListener);
    }

    ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            book = dataSnapshot.getValue(Book.class);
            tvTitle.setText(book.getTitle());
            tvAuthor.setText(book.getAuthor());
            tvOverview.setText(book.getOverview());
            tvCategory.setText(book.getCategory());
            Picasso.with(BookDetailsActivity.this)
                    .load(book.getCover())
                    .into(imgCover);
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    };
}

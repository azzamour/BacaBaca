package com.kleinkarasu.bacabaca;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.kleinkarasu.bacabaca.Adapter.NewBookAdapter;
import com.kleinkarasu.bacabaca.Model.Book;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment implements NewBookAdapter.BookAdapterOnClickHandler {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private List<Book> books = new ArrayList<Book>();

    private DatabaseReference dbBooks;

    private CarouselView carouselView;
    private int[] sampleImages = {R.drawable.cover_openintro, R.drawable.cover_pmbok, R.drawable.cover_iso27001};
    private ImageListener imageListener;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        dbBooks = FirebaseDatabase.getInstance().getReference("Books");
        Query queryNewBooks = dbBooks.orderByChild("num").limitToLast(3);
        dbBooks.addValueEventListener(valueEventListener);

        imageListener = new ImageListener() {
            @Override
            public void setImageForPosition(int position, ImageView imageView) {
                imageView.setImageResource(sampleImages[position]);
            }
        };
        carouselView = (CarouselView) view.findViewById(R.id.carouselView);
        carouselView.setPageCount(sampleImages.length);
        carouselView.setImageListener(imageListener);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.rv_newbook);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new NewBookAdapter(getContext(), books, this);
        mRecyclerView.setAdapter(mAdapter);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            Iterable<DataSnapshot> children = dataSnapshot.getChildren();
            books.clear();
            for (DataSnapshot child : children) {
                Book book = child.getValue(Book.class);
                books.add(book);
            }
            mAdapter.notifyDataSetChanged();
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    };

    @Override
    public void onClick(Book book) {
        Intent intent = new Intent(getContext(), BookDetailsActivity.class);
        intent.putExtra("book", book.getTitle());
        startActivity(intent);
    }
}
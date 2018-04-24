package com.kleinkarasu.bacabaca.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.kleinkarasu.bacabaca.Model.Book;
import com.kleinkarasu.bacabaca.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class NewBookAdapter extends RecyclerView.Adapter<NewBookAdapter.ViewHolder> {
    private List<Book> books;
    private LayoutInflater inflater;
    private final BookAdapterOnClickHandler onClickListener;
    private Context context;


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView imgCover;
        private TextView title;

        public ViewHolder(View v) {
            super(v);
            imgCover = (ImageView) v.findViewById(R.id.img_cover);
            title = (TextView) v.findViewById(R.id.tv_title);

            v.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int postiton = getAdapterPosition();
            Book book = books.get(postiton);

            onClickListener.onClick(book);
        }
    }

    public NewBookAdapter(Context context, List<Book> books, BookAdapterOnClickHandler onClickListener) {
        this.context = context;
        this.books = books;
        this.inflater = LayoutInflater.from(context);
        this.onClickListener = onClickListener;
    }

    @Override
    public NewBookAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                            int viewType) {
        View view = inflater.inflate(R.layout.card_book, parent, false);
        return new NewBookAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Book book = books.get(position);
        holder.title.setText(book.getTitle());
        Picasso.with(context)
                .load(book.getCover())
                .into(holder.imgCover);
    }

    @Override
    public int getItemCount() {
        if (books != null) {
            return books.size();
        }

        return 0;
    }

    public interface BookAdapterOnClickHandler {
        void onClick(Book book);
    }
}

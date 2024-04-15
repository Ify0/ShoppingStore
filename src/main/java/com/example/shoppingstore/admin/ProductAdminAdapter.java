package com.example.shoppingstore.admin;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.shoppingstore.Product;
import com.example.shoppingstore.R;


import java.util.ArrayList;

public class ProductAdminAdapter extends RecyclerView.Adapter<ProductAdminAdapter.MyViewHolder> {
    private final Context context;
    private ArrayList<Product> myDataSet;


    //constructor
    public ProductAdminAdapter(Context context, ArrayList<Product> myDataSet) {
        this.context = context;
        this.myDataSet = myDataSet;
    }


    // Create new views (invoked by the layout manager)
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.retrieved_layout, parent , false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Product product = myDataSet.get(position);

        holder.title.setText(product.getTitle());
        holder.price.setText(product.getPrice());

        Glide.with(context)
                .load(product.getImageUrl())
                .override(1200, 400)
                .into(holder.imageView);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, DetailedAdminActivity.class);
                i.putExtra("detail", product);

                context.startActivity(i);
            }
        });


    }


    @Override
    public int getItemCount() {

        return myDataSet.size();
    }

    public void searchDataList(ArrayList<Product> searchList){
        myDataSet = searchList;
        notifyDataSetChanged();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder{
        private final TextView title, price;
        private final ImageView imageView;
        CardView card;

        public MyViewHolder(@NonNull View view){
            super(view);

            imageView = view.findViewById(R.id.imageView);
            title = view.findViewById(R.id.titleTextView);
            price = view.findViewById(R.id.priceTextView);
            card = view.findViewById(R.id.card);
        }
    }



}

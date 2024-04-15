package com.example.shoppingstore.admin;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;

import com.example.shoppingstore.Product;
import com.example.shoppingstore.R;
import com.example.shoppingstore.customer.ReviewModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class DetailedAdminActivity extends AppCompatActivity {

    ImageView detailImg;
    String detailImage;
    TextView price, reviewText, title, quantity, review, delete;

    int totalQuantity = 0;
    int totalPrice = 1;
    int quant = 0;
    String productId, category, manufacturer, fPrice, fTitle;

    private DetailedAdminViewModel viewModel;

    Button addRemove ,addItem, removeItem;
    Product product = null;
    Toolbar toolbar;

    FirebaseStorage firebaseStorage;
    FirebaseAuth auth;
    FirebaseDatabase firebaseDatabase;

    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Products");
    private final ArrayList<ReviewModel> ratingList = new ArrayList<>();



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_productdetailedadmin);

        viewModel = new ViewModelProvider(this).get(DetailedAdminViewModel.class);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Glide.with(this);

        final Object object = getIntent().getSerializableExtra("detail");
        if(object instanceof Product){
            product = (Product) object;

        }

        detailImg = findViewById(R.id.detailed_img);
        addItem = findViewById(R.id.add_item);
        removeItem = findViewById(R.id.remove_item);
        addRemove = findViewById(R.id.add_remove);
        price = findViewById(R.id.detailed_price);
        title = findViewById(R.id.detail_name);
        quantity = findViewById(R.id.quantity);
        delete = findViewById(R.id.deleteImageView);

        if(product != null){
            price.setText(product.getPrice());
            title.setText(product.getTitle());
            productId = product.getProductId();
            quantity.setText(String.valueOf(product.getQuantity()));
            category = product.getCategory();
            manufacturer = product.getManufacturer();
            detailImage = product.getImageUrl();
        }
        fPrice = String.valueOf(product.getPrice());
        fTitle = String.valueOf(product.getTitle());
        quant = Integer.parseInt(String.valueOf(product.getQuantity()));

        Glide.with(DetailedAdminActivity.this)
                .load(detailImage)
                .override(2400, 800)
                .into(detailImg);

        addItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(quant < 10){
                    quant++;
                    quantity.setText(String.valueOf(quant));
                }
            }
        });
        removeItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(quant > 1){
                    quant--;
                    quantity.setText(String.valueOf(quant));
                }
            }
        });

        addRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setNewQuantity();
            }

        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference fireDB = FirebaseDatabase.getInstance().getReference("Products");
                fireDB.child(productId).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(DetailedAdminActivity.this, "Item deleted", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }
                });
            }
        });



    }

    private void setNewQuantity() {

        Product product = new Product(fTitle, category, manufacturer, fPrice, quant);

        databaseReference.child(productId).setValue(product).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(DetailedAdminActivity.this, "Stock Updated", Toast.LENGTH_SHORT).show();
                    finish();
                }

            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }


}

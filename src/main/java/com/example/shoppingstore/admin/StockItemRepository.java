package com.example.shoppingstore.admin;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;


import com.example.shoppingstore.Product;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;
import java.util.List;

public class StockItemRepository {
    private FirebaseFirestore firestore;
    private CollectionReference stockCollection;

    public StockItemRepository() {
        firestore = FirebaseFirestore.getInstance();
        stockCollection = firestore.collection("Products");
    }

    public LiveData<List<Product>> getAllProducts() {
        MutableLiveData<List<Product>> data = new MutableLiveData<>();
        stockCollection.orderBy("title", Query.Direction.ASCENDING)
                .addSnapshotListener((value, error) -> {
                    if (error != null) {
                        // Handle error
                        return;
                    }
                    List<Product> products = new ArrayList<>();
                    for (DocumentSnapshot document : value.getDocuments()) {
                        Product product = document.toObject(Product.class);
                        products.add(product);
                    }
                    data.setValue(products);
                });
        return data;
    }

    public LiveData<Product> getProduct(String productId) {
        MutableLiveData<Product> data = new MutableLiveData<>();
        stockCollection.document(productId)
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    Product product = documentSnapshot.toObject(Product.class);
                    data.setValue(product);
                });
        return data;
    }

    public void updateProduct(Product product) {
        stockCollection.document(product.getProductId()).set(product);
    }

    public void deleteProduct(String productId) {
        stockCollection.document(productId).delete();
    }

    public LiveData<List<Product>> searchProducts(String query) {
        MutableLiveData<List<Product>> data = new MutableLiveData<>();
        // Perform search query
        return data;
    }
}


package com.example.shoppingstore.admin;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.shoppingstore.Product;


public class DetailedAdminViewModel extends ViewModel {
    private StockItemRepository stockItemRepository;

    public DetailedAdminViewModel() {
        stockItemRepository = new StockItemRepository();
    }

    public LiveData<Product> getProduct(String productId) {
        return stockItemRepository.getProduct(productId);
    }

    public void updateProduct(Product product) {
        stockItemRepository.updateProduct(product);
    }

    public void deleteProduct(String productId) {
        stockItemRepository.deleteProduct(productId);
    }
}

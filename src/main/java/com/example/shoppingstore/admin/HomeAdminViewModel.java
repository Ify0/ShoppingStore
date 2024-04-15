package com.example.shoppingstore.admin;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;


import com.example.shoppingstore.Product;

import java.util.List;

public class HomeAdminViewModel extends ViewModel {
    private StockItemRepository stockItemRepository;
    private LiveData<List<Product>> productList;

    public HomeAdminViewModel() {
        stockItemRepository = new StockItemRepository();
        productList = stockItemRepository.getAllProducts();
    }

    public LiveData<List<Product>> getProductList() {
        return productList;
    }

    public void searchProducts(String query) {
        productList = stockItemRepository.searchProducts(query);
    }
}

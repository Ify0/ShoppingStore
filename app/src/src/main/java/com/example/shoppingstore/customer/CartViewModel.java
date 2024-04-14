package com.example.shoppingstore.customer;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

public class CartViewModel extends ViewModel {

    private MutableLiveData<List<CartModel>> cartItemsLiveData;
    private CartRepository cartRepository;

    public CartViewModel() {
        cartRepository = new CartRepository();
    }

    public LiveData<List<CartModel>> getCartItems(CartRepository.CartDataListener listener) {
        if (cartItemsLiveData == null) {
            cartItemsLiveData = new MutableLiveData<>();
            loadCartItems(listener);
        }
        return cartItemsLiveData;
    }

    private void loadCartItems(CartRepository.CartDataListener listener) {
        cartRepository.getCartItems(new CartRepository.CartDataListener() {
            @Override
            public void onCartItemsLoaded(List<CartModel> cartItems) {
                cartItemsLiveData.setValue(cartItems);
                listener.onCartItemsLoaded(cartItems); // Notify listener
            }

            @Override
            public void onCartItemRemoved() {
                // Handle cart item removed if needed
            }
        });
    }

    public void removeCartItem(String productId) {

    }
}

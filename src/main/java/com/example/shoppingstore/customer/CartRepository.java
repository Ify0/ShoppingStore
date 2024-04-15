package com.example.shoppingstore.customer;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class CartRepository {

    private FirebaseAuth auth;
    private FirebaseUser user;
    private DatabaseReference cartReference;

    public CartRepository() {
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        if (user != null) {
            cartReference = FirebaseDatabase.getInstance().getReference("Users").child(user.getUid()).child("AddToCart");
        }
    }

    public interface CartDataListener {
        void onCartItemsLoaded(List<CartModel> cartItems);

        void onCartItemRemoved();
    }

    public void getCartItems(CartDataListener listener) {
        cartReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<CartModel> cartItems = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    CartModel cartModel = snapshot.getValue(CartModel.class);
                    if (cartModel != null) {
                        cartModel.setProductId(snapshot.getKey());
                        cartItems.add(cartModel);
                    }
                }
                listener.onCartItemsLoaded(cartItems);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle error
            }
        });
    }

    public void removeCartItem(String productId, CartDataListener listener) {
        cartReference.child(productId).removeValue()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        listener.onCartItemRemoved();
                    } else {
                        // Handle error
                    }
                });
    }

}

package com.example.shoppingstore.customer;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.shoppingstore.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CartFragment extends Fragment {

    RecyclerView recyclerView;
    CartAdapter cartAdapter;
    List<CartModel> cartModelList;
    Button payNow;
    ImageView delete;
    Button enter;
    double total, finalTotal, code = 0;

    TextView overTotalAmount;
    EditText discount;
    String discountCode = "";
    String userID;
    FirebaseUser user;

    private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    FirebaseAuth auth;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cart, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        cartModelList = new ArrayList<>();
        cartAdapter = new CartAdapter(getActivity(), cartModelList);
        recyclerView.setAdapter(cartAdapter);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        userID = user.getUid();

        discount = view.findViewById(R.id.discountEditText);
        overTotalAmount = view.findViewById(R.id.textView);
        payNow = view.findViewById(R.id.pay_now);
        enter = view.findViewById(R.id.enterButton);

        LocalBroadcastManager.getInstance(requireActivity()).registerReceiver(mMessageReceiver, new IntentFilter("TotalAmount"));

        DatabaseReference fireDB = FirebaseDatabase.getInstance().getReference("Users").child(userID).child("AddToCart");
        fireDB.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()) {
                    for (DataSnapshot snapshot : task.getResult().getChildren()) {
                        String productId = snapshot.getKey();
                        CartModel cartModel = snapshot.getValue(CartModel.class);
                        if (cartModel != null) {
                            cartModel.setProductId(productId);
                            cartModelList.add(cartModel);
                        }
                    }
                    cartAdapter.notifyDataSetChanged();
                }
            }
        });

        // Initialize ViewModel
        CartViewModel cartViewModel = new ViewModelProvider(this).get(CartViewModel.class);

        // Pass CartDataListener to getCartItems method
        cartViewModel.getCartItems(new CartRepository.CartDataListener() {
            @Override
            public void onCartItemsLoaded(List<CartModel> cartItems) {
                // Update UI with loaded cart items
                cartModelList.clear();
                cartModelList.addAll(cartItems);
                cartAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCartItemRemoved() {
                // Handle cart item removed if needed
            }
        });

        payNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(requireContext(), PaymentActivity.class);
                intent.putExtra("itemList", (Serializable) cartModelList);
                intent.putExtra("total", total);
                startActivity(intent);
            }
        });

        enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                discountCode = discount.getText().toString().trim();
                // Perform any necessary actions with the discount code here
            }
        });
    }

    private final BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            total = intent.getDoubleExtra("totalAmount", 0);
            overTotalAmount.setText(getString(R.string.total_price, total));
        }
    };

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        LocalBroadcastManager.getInstance(requireActivity()).unregisterReceiver(mMessageReceiver);
    }
}

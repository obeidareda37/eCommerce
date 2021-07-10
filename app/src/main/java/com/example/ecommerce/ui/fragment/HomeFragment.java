package com.example.ecommerce.ui.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ecommerce.R;
import com.example.ecommerce.adapter.CategoryAdapter;
import com.example.ecommerce.adapter.NewProductsAdapter;
import com.example.ecommerce.adapter.PopularAdapter;
import com.example.ecommerce.models.Category;
import com.example.ecommerce.models.NewProducts;
import com.example.ecommerce.models.PopularProducts;
import com.example.ecommerce.ui.activity.ShowAllActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment {

    TextView catShowAll,popularShowAll, newProductShowAll;

    LinearLayout linearLayout;
    ProgressDialog progressDialog;

    RecyclerView categoryRecyclerView , newProductRecyclerview , popularRecyclerview;

    //Category recyclerView
    CategoryAdapter categoryAdapter;
    List<Category> categoryList;

    //NewProduct
    NewProductsAdapter newProductsAdapter;
    List<NewProducts> newProductsList;

    //PopularProducts
    PopularAdapter popularAdapter;
    List<PopularProducts> popularProductsList;


    //FireStore
    FirebaseFirestore firebaseFirestore;


    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_home, container, false);
        firebaseFirestore = FirebaseFirestore.getInstance();
        linearLayout = view.findViewById(R.id.home_layout);
        linearLayout.setVisibility(View.GONE);

        progressDialog = new ProgressDialog(getActivity());

        categoryRecyclerView = view.findViewById(R.id.rec_category);
        newProductRecyclerview=view.findViewById(R.id.new_product_rec);
        popularRecyclerview =view.findViewById(R.id.popular_rec);

        catShowAll =view.findViewById(R.id.category_see_all);
        popularShowAll = view.findViewById(R.id.popular_see_all);
        newProductShowAll=view.findViewById(R.id.newProducts_see_all);



        catShowAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ShowAllActivity.class);
                startActivity(intent);
            }
        });
        popularShowAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ShowAllActivity.class);
                startActivity(intent);
            }
        });
        newProductShowAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ShowAllActivity.class);
                startActivity(intent);
            }
        });

        progressDialog.setTitle("Welcome To My ECommerce App");
        progressDialog.setMessage("Please waite....");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        //Category
        categoryRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.HORIZONTAL,false));
        categoryList = new ArrayList<>();
        categoryAdapter = new CategoryAdapter(getContext(),categoryList);
        categoryRecyclerView.setAdapter(categoryAdapter);


        firebaseFirestore.collection("Category")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Category category = document.toObject(Category.class);
                                categoryList.add(category);
                                categoryAdapter.notifyDataSetChanged();
                                linearLayout.setVisibility(View.VISIBLE);
                                progressDialog.dismiss();
                            }
                        } else {
                            Toast.makeText(getActivity(), ""+task.getException(), Toast.LENGTH_SHORT).show();

                        }
                    }
                });


        //NewProducts

        newProductRecyclerview.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.HORIZONTAL,false));
        newProductsList = new ArrayList<>();
        newProductsAdapter = new NewProductsAdapter(getContext(),newProductsList);
        newProductRecyclerview.setAdapter(newProductsAdapter);

        firebaseFirestore.collection("NewProducts")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                NewProducts newProducts = document.toObject(NewProducts.class);
                                newProductsList.add(newProducts);
                                newProductsAdapter.notifyDataSetChanged();
                            }
                        } else {
                            Toast.makeText(getActivity(), ""+task.getException(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });


        popularRecyclerview.setLayoutManager(new GridLayoutManager(getActivity(),2));
        popularProductsList = new ArrayList<>();
        popularAdapter = new PopularAdapter(getContext(),popularProductsList);
        popularRecyclerview.setAdapter(popularAdapter);


        firebaseFirestore.collection("AllProducts")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {

                                PopularProducts popularProducts = document.toObject(PopularProducts.class);
                                popularProductsList.add(popularProducts);
                                popularAdapter.notifyDataSetChanged();
                            }
                        } else {
                            Toast.makeText(getActivity(), ""+task.getException(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        return view;
    }
}
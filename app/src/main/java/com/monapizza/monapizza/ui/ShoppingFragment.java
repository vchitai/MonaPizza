package com.monapizza.monapizza.ui;

import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.monapizza.monapizza.R;
import com.monapizza.monapizza.core.Item;
import com.monapizza.monapizza.core.Ultility;
import com.monapizza.monapizza.ui_adapter.ShoppingListAdapter;

import java.util.ArrayList;

public class ShoppingFragment extends Fragment {
    public ShoppingFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View      rootView =  inflater.inflate(R.layout.fragment_shopping, container, false);

        ArrayList<Item> itemList = Ultility.getDbHelper().getItemList();
        ShoppingListAdapter shoppingListAdapter = new ShoppingListAdapter(getContext(),itemList);
        getActivity().supportInvalidateOptionsMenu();
        RecyclerView shoppingItemListView = (RecyclerView) rootView.findViewById(R.id.shopping_list);

        shoppingItemListView.setLayoutManager(new LinearLayoutManager(getContext()));
        shoppingItemListView.setAdapter(shoppingListAdapter);

        ConstraintLayout emptyView = (ConstraintLayout) rootView.findViewById(R.id.shopping_empty_view);
        if (itemList.isEmpty()) {
            emptyView.setVisibility(View.VISIBLE);
            shoppingItemListView.setVisibility(View.GONE);
        } else {
            emptyView.setVisibility(View.GONE);
            shoppingItemListView.setVisibility(View.VISIBLE);
        }
        return rootView;
    }
}

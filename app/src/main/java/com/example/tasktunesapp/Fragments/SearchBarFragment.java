package com.example.tasktunesapp.Fragments;

import android.app.Activity;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;

import com.example.tasktunesapp.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;


public class SearchBarFragment extends Fragment {

    private TextInputLayout searchInputLayout;
    private TextInputEditText searchEditText;
    private View rootView;

    public SearchBarFragment() {
        // Required empty public constructor
    }

    private void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
        if (imm != null && getView() != null) {
            imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_search_bar, container, false);

        searchInputLayout = rootView.findViewById(R.id.search_input_layout);
        searchEditText = rootView.findViewById(R.id.search_edit_text);

        rootView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    View currentFocus = getActivity().getCurrentFocus();
                    if (currentFocus != null && currentFocus != searchEditText) {
                        currentFocus.clearFocus();
                        hideKeyboard();
                    }
                }
                return false;
            }
        });

        searchEditText.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                performSearch(searchEditText.getText().toString());
                return true;
            }
            return false;
        });

        searchInputLayout.setEndIconOnClickListener(view -> {
            searchEditText.setText("");
            searchEditText.clearFocus();
            hideKeyboard();
        });

        // Inflate the layout for this fragment
        return rootView;
    }

    private void performSearch(String query) {
        // Implement your search functionality here
    }
}

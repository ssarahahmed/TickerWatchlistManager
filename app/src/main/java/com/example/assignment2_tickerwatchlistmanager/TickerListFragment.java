package com.example.assignment2_tickerwatchlistmanager;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;

public class TickerListFragment extends Fragment {

    private OnTickerSelectedListener listener;
    private ArrayList<String> tickers;

    private static final String KEY_TICKERS = "tickers_key";

    public TickerListFragment() {
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof OnTickerSelectedListener) {
            listener = (OnTickerSelectedListener) context;
        } else {
            throw new RuntimeException(context + " must implement OnTickerSelectedListener");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null && savedInstanceState.containsKey(KEY_TICKERS)) {
            tickers = savedInstanceState.getStringArrayList(KEY_TICKERS);
        } else {
            tickers = new ArrayList<>();
            tickers.add("NEE");
            tickers.add("AAPL");
            tickers.add("DIS");
        }
    }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_ticker_list, container, false);

            ListView listView = view.findViewById(R.id.tickerListView);
            ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_list_item_1, tickers);
            listView.setAdapter(adapter);


            listView.setOnItemClickListener((parent, itemView, position, id) -> {
                if (listener != null) {
                    listener.onTickerSelected(tickers.get(position));
                }
            });

            return view;
        }
    }

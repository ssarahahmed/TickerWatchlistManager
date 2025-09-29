package com.example.assignment2_tickerwatchlistmanager;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;

public class TickerListFragment extends Fragment {

    private OnTickerSelectedListener listener;
    private ArrayList<String> tickers;
    private ArrayAdapter<String> adapter;
    private ListView listView;

    // key for saving state
    private static final String KEY_TICKERS = "tickers_key";

    public TickerListFragment() {
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof OnTickerSelectedListener) {
            listener = (OnTickerSelectedListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement OnTickerSelectedListener");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null && savedInstanceState.containsKey(KEY_TICKERS)) {
            tickers = savedInstanceState.getStringArrayList(KEY_TICKERS);
        } else {
            tickers = new ArrayList<>();
            // default entries per assignment
            tickers.add("NEE");
            tickers.add("AAPL");
            tickers.add("DIS");
        }
    }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_ticker_list, container, false);

            listView = view.findViewById(R.id.tickerListView);
            adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_list_item_1, tickers);
            listView.setAdapter(adapter);

            // item click -> notify activity
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View itemView, int position, long id) {
                    if (listener != null) {
                        listener.onTickerSelected(tickers.get(position));
                    }
                }
            });

            return view;
        }

        // Add or replace logic: keep maximum 6 entries, if full replace the 6th (index 5)
        public void addTicker(String ticker) {
            // sanitize simple whitespace & upper-case
            ticker = ticker.trim().toUpperCase();
            if (ticker.isEmpty()) return;

            // if ticker already exists, move it to end (optional) — here we will not duplicate
            if (tickers.contains(ticker)) {
                // move existing to end to show "recent"
                tickers.remove(ticker);
                tickers.add(ticker);
            } else {
                if (tickers.size() >= 6) {
                    tickers.set(5, ticker); // replace sixth
                } else {
                    tickers.add(ticker);
                }
            }
            adapter.notifyDataSetChanged();
        }

        @Override
        public void onSaveInstanceState(@NonNull Bundle outState) {
            super.onSaveInstanceState(outState);
            outState.putStringArrayList(KEY_TICKERS, tickers);
        }
    }

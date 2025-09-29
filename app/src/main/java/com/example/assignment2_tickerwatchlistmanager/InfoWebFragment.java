package com.example.assignment2_tickerwatchlistmanager;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.fragment.app.Fragment;

public class InfoWebFragment extends Fragment {

        private WebView webView;

        public InfoWebFragment() { }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState){
            View view = inflater.inflate(R.layout.fragment_info, container, false);
            webView = view.findViewById(R.id.webView);

            webView.setWebViewClient(new WebViewClient());

            // load default page
            webView.loadUrl("https://seekingalpha.com");

            return view;
        }


        public void loadTicker(String ticker) {
            String url = "https://seekingalpha.com/symbol/" + ticker;
            webView.loadUrl(url);
        }
    }


package com.example.assignment2_tickerwatchlistmanager;

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
        private static final String BASE_URL = "https://seekingalpha.com";
        private static final String SYMBOL_PATH = "/symbol/";

        public InfoWebFragment() { }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState){
            View view = inflater.inflate(R.layout.fragment_info, container, false);
            webView = view.findViewById(R.id.webView);

            WebSettings settings = webView.getSettings();
            settings.setJavaScriptEnabled(true); // needed by many modern sites
            webView.setWebViewClient(new WebViewClient()); // keep navigation inside WebView

            // load default page
            webView.loadUrl(BASE_URL);

            return view;
        }

        // Called from MainActivity when a ticker is selected
        public void loadTicker(String ticker) {
            if (ticker == null || ticker.trim().isEmpty()) return;
            String url = BASE_URL + SYMBOL_PATH + ticker.trim().toUpperCase();
            webView.loadUrl(url);
        }
    }


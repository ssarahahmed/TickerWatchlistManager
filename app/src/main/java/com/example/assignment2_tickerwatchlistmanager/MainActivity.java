package com.example.assignment2_tickerwatchlistmanager;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class MainActivity extends AppCompatActivity implements OnTickerSelectedListener{

    FragmentManager fg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            fg = getSupportFragmentManager();
            FragmentTransaction trans = fg.beginTransaction();

            TickerListFragment listFrag = new TickerListFragment();
            trans.add(R.id.listFragment, listFrag, "listFrag");

            InfoWebFragment infoFrag = new InfoWebFragment();
            trans.add(R.id.infoFragment, infoFrag, "infoFrag");

            trans.commit();


        }
        Intent intent = getIntent();
        String newTicker = intent.getStringExtra("NEW_TICKER");
        if (newTicker != null) handleNewTicker(newTicker);
        String toastMessage = intent.getStringExtra("TOAST_MESSAGE");
        if (toastMessage != null) Toast.makeText(this, toastMessage, Toast.LENGTH_SHORT).show();

    }
    @Override
    public void onTickerSelected(String ticker) {

        InfoWebFragment infoFrag = (InfoWebFragment) getSupportFragmentManager().findFragmentByTag("infoFrag");
        if (infoFrag != null) {
            infoFrag.loadTicker(ticker);
        }
    }

    private void handleNewTicker(String ticker) {
        TickerListFragment listFrag = (TickerListFragment) getSupportFragmentManager().findFragmentByTag("listFrag");
        if (listFrag != null) {
            listFrag.addTicker(ticker);
        }
        onTickerSelected(ticker);
    }
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        String newTicker = intent.getStringExtra("NEW_TICKER");
        if (newTicker != null) {
            handleNewTicker(newTicker);
        }
        String toastMessage = intent.getStringExtra("TOAST_MESSAGE");
        if (toastMessage != null) {
            Toast.makeText(this, toastMessage, Toast.LENGTH_SHORT).show();
        }
    }



}
package com.example.assignment2_tickerwatchlistmanager;

import android.os.Bundle;

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

    }

    @Override
    public void onTickerSelected(String ticker) {

        InfoWebFragment infoFrag = (InfoWebFragment) getSupportFragmentManager().findFragmentByTag("infoFrag");
        if (infoFrag != null) {
            infoFrag.loadTicker(ticker);
        }
    }
}
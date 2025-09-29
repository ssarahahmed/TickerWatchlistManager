package com.example.assignment2_tickerwatchlistmanager;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
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
        // Find the info fragment by tag and call its load method
        InfoWebFragment infoFrag = (InfoWebFragment) getSupportFragmentManager().findFragmentByTag("infoFrag");
        if (infoFrag != null) {
            infoFrag.loadTicker(ticker);
        }
    }
}
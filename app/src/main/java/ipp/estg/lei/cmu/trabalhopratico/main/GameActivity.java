package ipp.estg.lei.cmu.trabalhopratico.main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import ipp.estg.lei.cmu.trabalhopratico.R;

public class GameActivity extends AppCompatActivity implements GameEntranceFragment.OnFragmentInteractionListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        if (findViewById(R.id.fragment_container) != null) {
            if (savedInstanceState != null) return;
            GameEntranceFragment inpFragment = new GameEntranceFragment();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, inpFragment)
                    .commit();
        }
    }

    @Override
    public void startGame(){
       GamePlayFragment gamePlayFragment = new GamePlayFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, gamePlayFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

}

package ipp.estg.lei.cmu.trabalhopratico.main.menu_tabs.nutrition;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import ipp.estg.lei.cmu.trabalhopratico.R;
import ipp.estg.lei.cmu.trabalhopratico.nutricao.classes_nutricao.NutricaoMainActivity;

public class NutritionFragment extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_nutrition, container, false);

        // TO UPDATE, CHANGE ACTIVITIES TO FRAGMENTS
        startActivity(new Intent(getActivity(), NutricaoMainActivity.class));

        return view;
    }
}

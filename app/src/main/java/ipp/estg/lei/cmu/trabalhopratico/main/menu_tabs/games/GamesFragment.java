package ipp.estg.lei.cmu.trabalhopratico.main.menu_tabs.games;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import org.w3c.dom.Text;

import ipp.estg.lei.cmu.trabalhopratico.R;
import ipp.estg.lei.cmu.trabalhopratico.game.GameActivity;

public class GamesFragment extends Fragment {

    private AlertDialog startGameDialog;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_game_list, container, false);

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage("Pretende dar início ao jogo?")
                .setCancelable(false)
                .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    public void onClick(@SuppressWarnings("unused") final DialogInterface dialog,
                                        @SuppressWarnings("unused") final int id) {
                        startActivity(new Intent(getActivity(), GameActivity.class));
                    }
                })
                .setNegativeButton("Não", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        dialog.dismiss();
                    }
                });

        startGameDialog = builder.create();

        view.findViewById(R.id.game_card_view_calculus).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startGameDialog.show();
            }
        });

        return view;
    }
}
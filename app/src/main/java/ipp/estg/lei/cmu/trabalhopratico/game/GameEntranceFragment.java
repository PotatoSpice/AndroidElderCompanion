package ipp.estg.lei.cmu.trabalhopratico.game;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.concurrent.ExecutionException;

import ipp.estg.lei.cmu.trabalhopratico.game.classificacoes.database.ClassiDatabase;
import ipp.estg.lei.cmu.trabalhopratico.game.classificacoes.models.Classificacao;
import ipp.estg.lei.cmu.trabalhopratico.game.classificacoes.models.ClassificacaoDAO;
import ipp.estg.lei.cmu.trabalhopratico.R;

public class GameEntranceFragment extends Fragment {

    private OnFragmentInteractionListener mListener;

    private Button entryButton;
    private TextView scoreTXT;
    ClassiDatabase classiDb;
    Classificacao[] topClassificacao = new Classificacao[1];


    public GameEntranceFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        classiDb = ClassiDatabase.getDatabase(getActivity().getApplicationContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View mViewContent = inflater.inflate(R.layout.fragment_game_entrance, container, false);


        try {
            topClassificacao[0] = new DataAsyncTask(classiDb.getClassiDao()).execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        scoreTXT = mViewContent.findViewById(R.id.bestScoreTXT);
        entryButton = mViewContent.findViewById(R.id.startGameButton);
        if(topClassificacao[0]!=null){
            scoreTXT.setText("Melhor pontuação: "+topClassificacao[0].points);
        }else{
            scoreTXT.setText("Ainda não jogou hoje!");
        }
        entryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onButtonPressed();
            }
        });

        // Inflate the layout for this fragment
        return mViewContent;
    }

    public void onButtonPressed() {
        if (mListener != null) {
            mListener.startGame();
        }
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        void startGame();
    }

    private class DataAsyncTask extends AsyncTask<Void, Void, Classificacao> {

        ClassificacaoDAO dao;

        public DataAsyncTask(ClassificacaoDAO dao){
            this.dao=dao;
        }

        @Override
        protected Classificacao doInBackground(Void ... urls) {
            Calendar c = Calendar.getInstance();
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            String formattedDate = df.format(c.getTime());
            final Classificacao[] bestScore = new Classificacao[1];
            bestScore[0] = dao.loadTopClassToday(formattedDate);
            return bestScore[0];
        }
    }

}

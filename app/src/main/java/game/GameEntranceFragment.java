package game;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import game.classificacoes.database.ClassiDatabase;
import game.classificacoes.models.Classificacao;
import ipp.estg.lei.cmu.trabalhopratico.R;

public class GameEntranceFragment extends Fragment {

    private OnFragmentInteractionListener mListener;

    private Button entryButton;
    private TextView scoreTXT;
    private Classificacao bestScore;
    ClassiDatabase classiDb;
    int entryNumber=0;

    public GameEntranceFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View mViewContent = inflater.inflate(R.layout.fragment_game_entrance, container, false);
        scoreTXT = mViewContent.findViewById(R.id.bestScoreTXT);
        classiDb = ClassiDatabase.getDatabase(getActivity().getApplicationContext());
        DataAsyncTask dataAsyncTask = new DataAsyncTask();
        dataAsyncTask.execute();
        entryButton = mViewContent.findViewById(R.id.startGameButton);
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

    public void setBestScore(){
        ClassiDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                entryNumber = classiDb.getClassiDao().getCount();
                bestScore = classiDb.getClassiDao().loadTopClassificacao();
            }
        });

        if(entryNumber!=0) {
            scoreTXT.setText("Melhor pontuação: " + bestScore.points);
        }else{
            scoreTXT.setText("Sem Melhor pontuação; DEB:" + entryNumber );
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

    private class DataAsyncTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void ... urls) {
            ClassiDatabase.databaseWriteExecutor.execute(new Runnable() {
                @Override
                public void run() {
                   setBestScore();
                }
            });

            return null;
        }
    }

}

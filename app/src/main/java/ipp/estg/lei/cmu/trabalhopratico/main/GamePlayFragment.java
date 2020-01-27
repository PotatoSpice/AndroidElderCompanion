package ipp.estg.lei.cmu.trabalhopratico.main;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import ipp.estg.lei.cmu.trabalhopratico.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link GamePlayFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class GamePlayFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    private OnFragmentInteractionListener mListener;

    TextView textTXT;
    TextView questioncTXT;
    EditText userInput;
    Button checkButton;
    private int counter = 0;

    public GamePlayFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View mViewContent = inflater.inflate(R.layout.fragment_game_play, container, false);
        textTXT= mViewContent.findViewById(R.id.questionTXT);
        textTXT.setText(questionGeneration());
        userInput = mViewContent.findViewById(R.id.replyText);
        questioncTXT = mViewContent.findViewById(R.id.counterTXT);
        checkButton = mViewContent.findViewById(R.id.checkButton);
        checkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String question = textTXT.getText().toString();
                String answer = userInput.getText().toString();
                String[] q_parts = question.substring(0, question.indexOf("=")).split(" ");
                // Parses ...
                int first = Integer.parseInt(q_parts[0]), second = Integer.parseInt(q_parts[2]),
                        ans = Integer.parseInt(answer);
                String op = q_parts[1];
                if (checkResults(first, second, op, ans)) {
                    AlertDialog alertDialog = new AlertDialog.Builder(getContext()).create(); //Read Update
                    alertDialog.setTitle("Correto!");
                    alertDialog.setMessage("O Resultado está correto!");
                    alertDialog.setButton("Continue..", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            textTXT.setText(questionGeneration());
                            questioncTXT.setText(" Questões respondidas:"+ counter);
                        }
                    });
                    alertDialog.show();
                } else {
                    AlertDialog alertDialog = new AlertDialog.Builder(getContext()).create(); //Read Update
                    alertDialog.setTitle("Incorreto!");
                    alertDialog.setMessage("O Resultado está incorreto!");
                    alertDialog.show();  //<-- See This!
                }

                if(counter==12){
                    AlertDialog alertDialog = new AlertDialog.Builder(getContext()).create(); //Read Update
                    alertDialog.setTitle("Jogo terminado");
                    alertDialog.setMessage("Terminou o jogo, respondendo às 12 questões corretamente!");
                    alertDialog.setButton("Voltar ao ínicio", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            FragmentManager fm = getFragmentManager();
                            if (fm.getBackStackEntryCount() > 0) {
                                fm.popBackStack();
                            }
                        }
                    });
                    alertDialog.show();
                }

            }
        });

        return mViewContent;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    public String questionGeneration(){
        if(counter<3){
            String operator = "+";
            int random = (int) Math.floor((Math.random() * 1));
            if (random == 0) {
                operator = "+";
            } else if (random == 1) {
                operator = "-";
            }
            return (int) (Math.floor(Math.random() * 100)) + " " + operator + " " +
                    (int) (Math.floor(Math.random() * 100)) + " = x";
        }else if(counter<6){
            String operator = "*";
            int random = (int) Math.floor((Math.random() * 1));
            if (random == 0) {
                operator = "*";
            } else if (random == 1) {
                operator = "/";
            }
            return (int) (Math.floor(Math.random() * 100)) + " " + operator + " " +
                    (int) (Math.floor(Math.random() * 100)) + " = x";
        }else if(counter<12){
            String operator = "+";
            int random = (int) Math.floor((Math.random() * 3));
            if (random == 0) {
                operator = "+";
            } else if (random == 1) {
                operator = "-";
            } else if (random == 2) {
                operator = "*";
            } else if (random == 3) {
                operator = "/";
            }
            return (int) (Math.floor(Math.random() * 100)) + " " + operator + " " +
                    (int) (Math.floor(Math.random() * 100)) + " = x";
        }
        return  "";
    }

    public boolean checkResults(int n1, int n2, String op, int ans){
        if (op.equals("+")) {
            if (n1 + n2 == ans) {
                counter++;
                return true;
            }
        } else if (op.equals("-")) {
            if (n1 - n2 == ans) {
                counter++;
                return true;
            }
        } else if (op.equals("*")) {
            if (n1 * n2 == ans) {
                counter++;
                return true;
            }
        } else if (op.equals("/")) {
            if (n1 / n2 == ans) {
                counter++;
                return true;
            }
        }
        return false;
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
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}

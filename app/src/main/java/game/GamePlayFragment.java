package game;

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

import java.util.Timer;
import java.util.TimerTask;

import ipp.estg.lei.cmu.trabalhopratico.R;


public class GamePlayFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    TextView textTXT;
    TextView questioncTXT;
    EditText userInput;
    EditText tipTXT;
    Button checkButton;
    private int counter = 0;
    private int[] attempts = new int[12];


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
        tipTXT = mViewContent.findViewById(R.id.gameTipsTxt);
        questioncTXT = mViewContent.findViewById(R.id.counterTXT);
        checkButton = mViewContent.findViewById(R.id.checkButton);

        for(int ix=0; ix<12; ix++)
            attempts[ix]=0; //Inicializa o array de tentativas
        final String[] q_partstip = textTXT.getText().toString().substring(0, textTXT.getText().toString().indexOf("=")).split(" ");
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                int first = Integer.parseInt(q_partstip[0]), second = Integer.parseInt(q_partstip[2]);
                     tipTXT.setText(generateTip(first, second, q_partstip[3]));
            }
        }, 15000);

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
                            new Timer().schedule(new TimerTask() {
                                @Override
                                public void run() {
                                    //Timer para gerar uma tip ao utilizador

                                }
                            }, 15000);
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
                    alertDialog.setMessage("Terminou o jogo, respondendo às 12 questões corretamente! Pontuação alcançada: "+calculateScore());
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

    public String questionGeneration(){
        if(counter<3){
            String operator = "+";
            int random = (int) Math.floor((Math.random() * 1));
            if (random == 0) {
                operator = "+";
            } else if (random == 1) {
                operator = "-";
            }
            return (int) (Math.floor(Math.random() * 15)) + " " + operator + " " +
                    (int) (Math.floor(Math.random() * 15)) + " = x";
        }else if(counter<6){
            String operator = "*";
            int random = (int) Math.floor((Math.random() * 1));
            if (random == 0) {
                operator = "*";
            } else if (random == 1) {
                operator = "/";
            }
            return (int) (Math.floor(Math.random() * 10)) + " " + operator + " " +
                    (int) (Math.floor(Math.random() * 10)) + " = x";
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
            return (int) (Math.floor(Math.random() * 15)) + " " + operator + " " +
                    (int) (Math.floor(Math.random() * 15)) + " = x";
        }
        return  "";
    }

    public boolean checkResults(int n1, int n2, String op, int ans){
        attempts[counter]++;
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

    private int calculateScore(){
       int total = 120;
        for(int ix=0; ix<12; ix++){
            int lostPoints;
            if(attempts[ix]>9)
                lostPoints=9;
            else
                lostPoints=attempts[ix]-1;
            total = total - lostPoints;
        }
        return total;
    }

    private String generateTip(int n1, int n2, String op){

        int ans=0;
        if (op.equals("+")) {
            ans = n1 + n2 ;
        } else if (op.equals("-")) {
            ans = n1-n2;
        } else if (op.equals("*")) {
            ans = n1*n2;
        } else if (op.equals("/")) {
            ans= n1/n2;
        }
        int firstDigit = Integer.parseInt(Integer.toString(ans).substring(0, 1));
        return "Uma ajuda! \n " +
                "O primeiro dígito do número é: "+firstDigit;
    }

}

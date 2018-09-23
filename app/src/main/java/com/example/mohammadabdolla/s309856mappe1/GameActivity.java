package com.example.mohammadabdolla.s309856mappe1;

import android.app.Activity;
import android.app.ActivityOptions;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

import static com.example.mohammadabdolla.s309856mappe1.Constants.APP_PREF_NAME;
import static com.example.mohammadabdolla.s309856mappe1.Constants.MAX_QUESTIONS_IN_APPLICATION;
import static com.example.mohammadabdolla.s309856mappe1.Constants.PREF_MAX_QUESTIONS;
import static com.example.mohammadabdolla.s309856mappe1.Constants.PREF_STATISTICS_SET;

public class GameActivity extends Activity {

    private TextView questionTextView;                                                                                    // TextView som viser regnestykke
    private EditText anserByUserEditText;

    private TextView rightAnswersTextView, wrongAnswersTextView;

    Button button0;                                                                                 // Knapper
    Button button1;
    Button button2;
    Button button3;
    Button button4;
    Button button5;
    Button button6;
    Button button7;
    Button button8;
    Button button9;
    Button buttonC;
    Button buttonEqual;

    String[] question;                                                                              // tabell med regnestykker som hentes fra arrays senere
    String[] answer;                                                                                // tabell med svar på regnestykker som hentes fra arrays senere
    String[] index = new String[25];                                                                // tabell med 25 0-ere som brukes som indekstabell, for å markere besvarte og ubesvarte oppgaver

    int lengthOfAnswer;                                                                             // int som teller antall tall skrevet i edtiText, brukes for slette siste siffer når man visker med C-knappen
    int wrongAnswers = 0;                                                                               // int som skal telle antall svar besvart feil
    int rightAnswers = 0;                                                                               // int som skal telle antall svar besvart riktig
    private SharedPreferences preferences;
    private Set<Integer> questionNumbers = new HashSet<>();
    private int maxLimit = 5;

    private int currentQuestionIndex = 0;

    int counter = 0;

    // int som skal telle antall svar besvart, brukes for å vite når spillet skal avsluttes

    @Override
    public void onCreate(Bundle savedInstanceState) {                                               // onCreate metode
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game);
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        maxLimit = Integer.parseInt(preferences.getString(PREF_MAX_QUESTIONS, "5"));
        populateMyQuestions();
        // henter layout fra game xml

        Resources res = getResources();                                                             // henter resources for å få tak i tabeller
        question = res.getStringArray(R.array.questions);                                           // henter question tabell fra arrays
        answer = res.getStringArray(R.array.answers);                                               // henter answers tabell fra arrays

        anserByUserEditText = findViewById(R.id.textFromInput1);                                               // henter EdtiText fra game xml
        questionTextView = findViewById(R.id.questionstext);                                               // henter Textview fra game xml

        button0 = findViewById(R.id.buttonZero);                                                   // henter knapper fra game xml
        button1 = findViewById(R.id.buttonOne);
        button2 = findViewById(R.id.buttonTwo);
        button3 = findViewById(R.id.buttonThree);
        button4 = findViewById(R.id.buttonFour);
        button5 = findViewById(R.id.buttonFive);
        button6 = findViewById(R.id.buttonSix);
        button7 = findViewById(R.id.buttonSeven);
        button8 = findViewById(R.id.buttonEight);
        button9 = findViewById(R.id.buttonNine);
        buttonC = findViewById(R.id.buttonC);
        buttonEqual = findViewById(R.id.buttonEquals);

        rightAnswersTextView = findViewById(R.id.rightanswers);
        wrongAnswersTextView = findViewById(R.id.wrongAnswers);

        showQuestionToUser();                                                                              // kjører metoden showQuestionToUser som er forklart senere

        // viser svaret til regnestykket i loggen

        buttonEqual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String answerByUser = anserByUserEditText.getText().toString();
                if (answerByUser.equals(answer[currentQuestionIndex])) {
                    rightAnswers++;
                    rightAnswersTextView.setText(String.valueOf(rightAnswers));
                } else {
                    wrongAnswers++;
                    wrongAnswersTextView.setText(String.valueOf(wrongAnswers));
                }
                showQuestionToUser();
            }
        });

        buttonC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {                                                          // Knappen C som skal viske ut siste siffer skrevet i feltet
                lengthOfAnswer = anserByUserEditText.getText().length();                                       // lengthOfAnswer deklaret som antall verdier skrevet i feltet
                if (lengthOfAnswer > 0) {                                                           // Hvis de finnes
                    anserByUserEditText.getText().delete(lengthOfAnswer - 1, lengthOfAnswer);               // Skal de reduserer med en verdi, som er den siste.
                }
            }
        });

    }

    private int getRandValueBetween(int max) {
        return ThreadLocalRandom.current().nextInt(0, max);
    }

    private void populateMyQuestions() {
        while (questionNumbers.size() != maxLimit) {
            questionNumbers.add(getRandValueBetween(MAX_QUESTIONS_IN_APPLICATION));
        }
    }

    public void showQuestionToUser() {
        if (!questionNumbers.isEmpty()) {
            currentQuestionIndex = questionNumbers.iterator().next();
            questionNumbers.remove(currentQuestionIndex);
            questionTextView.setText(question[currentQuestionIndex]);
            anserByUserEditText.setText("");
        } else {
            Set<String> history = preferences.getStringSet(PREF_STATISTICS_SET, new HashSet<String>());
            StatisticsOfGame statisticsOfGame = new StatisticsOfGame(new Date(), maxLimit, rightAnswers, wrongAnswers);
            history.add(statisticsOfGame.toString());
            preferences.edit().putStringSet(PREF_STATISTICS_SET, history).apply();
            Intent intent = new Intent(GameActivity.this, Statistics.class);
            startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(GameActivity.this).toBundle());
            finish();
        }
    }

    public void addNumber(View view) {                                                              // legger til verdi i edittext fea knappene, ved å bruke onClick og tag i xml.
        String number = view.getTag().toString();                                                   // number får verdien til tag-en som knappen har fått tildelt
        anserByUserEditText.append(number);                                                                    // number blir satt inn i edittext feltet.
    }

    @Override
    public void onBackPressed() {
       new AlertDialog.Builder(GameActivity.this)
       .setTitle(R.string.skal_du_slutte)
       .setMessage(R.string.skal_du_slutte_message)
       .setNegativeButton(R.string.nei, new DialogInterface.OnClickListener() {
           @Override
           public void onClick(DialogInterface dialog, int which) {
               dialog.dismiss();
           }
       }).setPositiveButton(R.string.ja, new DialogInterface.OnClickListener() {
           @Override
           public void onClick(DialogInterface dialog, int which) {
               finish();
               dialog.dismiss();
           }
       }).setIcon(android.R.drawable.ic_dialog_alert)
       .create().show();
    }
}

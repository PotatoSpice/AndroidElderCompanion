package ipp.estg.lei.cmu.trabalhopratico;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import ipp.estg.lei.cmu.trabalhopratico.main.MainMenuActivity;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "LOGIN";

    // Initialize Firebase Auth
    private FirebaseAuth mAuth;

    private TextView statusView;
    private TextView detailsView;
    private EditText userEmail;
    private EditText userPassword;
    private ProgressBar mProgressBar;

    private boolean isCreatingAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Toolbar toolbar = findViewById(R.id.login_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("YourAssistant");

        statusView = findViewById(R.id.status);
        detailsView = findViewById(R.id.details);
        mProgressBar = findViewById(R.id.authenticationProgress);

        userEmail = findViewById(R.id.userEmail);
        userPassword = findViewById(R.id.userPassword);

        isCreatingAccount = false;

        mAuth = FirebaseAuth.getInstance();

        String action = getIntent().getAction();
        if (action != null && action.equals("ipp.estg.lei.cmu.trabalhopratico.SIGN_OUT_USER")) {
            signOut();
        }

        findViewById(R.id.signInButton).setOnClickListener(this);
        findViewById(R.id.createAccountButton).setOnClickListener(this);
        // findViewById(R.id.signOutButton).setOnClickListener(this); // para testes
    }

    @Override
    public void onStart() {
        super.onStart();

        // Check if user is signed in (non-null) and update UI accordingly
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }

    private void signOut() {
        mAuth.signOut();
        updateUI(null);
    }

    private void signIn(String email, String password) {
        Log.d(TAG, "signIn:" + email);
        if (!validateForm())
            return;

        mProgressBar.setVisibility(View.VISIBLE);

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user
                            Exception exc = task.getException();
                            Log.w(TAG, "signInWithEmail:failure", exc);
                            detailsView.setText((exc != null ? exc.getLocalizedMessage() : null));
                            updateUI(null);
                        }

                        // ...

                    }
                });
    }

    private void createUser(String email, String password) {
        Log.d(TAG, "createAccount:" + email);
        if (!validateForm()) {
            return;
        }

        mProgressBar.setVisibility(View.VISIBLE);

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user
                            Exception exc = task.getException();
                            Log.w(TAG, "createUserWithEmail:failure", exc);
                            detailsView.setText((exc != null ? exc.getLocalizedMessage() : null));
                            updateUI(null);
                        }

                        // ...

                    }
                });
    }

    private boolean validateForm() {
        boolean valid = true;

        if (userEmail.getText().toString().isEmpty()) {
            userEmail.setError("Required Field");
            valid = false;
        } else {
            userEmail.setError(null);
        }

        if (userPassword.getText().toString().isEmpty()) {
            userPassword.setError("Required Field");
            valid = false;
        } else {
            userPassword.setError(null);
        }

        return valid;
    }

    private void updateUI(FirebaseUser user) {
        if (user != null) {
            if (isCreatingAccount) {
                userEmail.setHint(R.string.email_hint);
                userPassword.setHint(R.string.password_hint);

                statusView.setText(getString(R.string.create_successful, user.getEmail()));

                isCreatingAccount = false;

            } else {
                // Signed In
                statusView.setText(getString(R.string.signed_in, user.getEmail()));

                findViewById(R.id.emailPasswordButtons).setVisibility(View.GONE);
                findViewById(R.id.emailPasswordFields).setVisibility(View.GONE);
                // findViewById(R.id.signOutButton).setVisibility(View.VISIBLE); // para testes

                // IR PARA A ACTIVITY COM O MENU PRINCIPAL DA APP
                startActivity(new Intent(this, MainMenuActivity.class));
                finish(); // remover esta activity da stack para prevenir que o user volte para trás
            }

        } else {
            if (isCreatingAccount) {
                userEmail.setHint(R.string.email_hint);
                userPassword.setHint(R.string.password_hint);

                statusView.setText(R.string.create_failed);

                isCreatingAccount = false;
            } else {
                // Signed out
                statusView.setText(R.string.signed_out);

                findViewById(R.id.emailPasswordButtons).setVisibility(View.VISIBLE);
                findViewById(R.id.emailPasswordFields).setVisibility(View.VISIBLE);
                // findViewById(R.id.signOutButton).setVisibility(View.GONE); // para testes
            }
        }
        userEmail.setText(null);
        userPassword.setText(null);
        mProgressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        detailsView.setText(null);
        if (id == R.id.createAccountButton) {
            if (!isCreatingAccount) {
                userEmail.setHint(R.string.create_email_hint);
                userPassword.setHint(R.string.create_password_hint);
                statusView.setText(R.string.creating_account);
                isCreatingAccount = true;

            } else {
                createUser(userEmail.getText().toString(), userPassword.getText().toString());

            }
        } else if (id == R.id.signInButton) {
            if (!isCreatingAccount) {
                signIn(userEmail.getText().toString(), userPassword.getText().toString());
            } else {
                userEmail.setHint(R.string.email_hint);
                userPassword.setHint(R.string.password_hint);
                statusView.setText(R.string.signed_out);
                isCreatingAccount = false;
            }
        }
        /*else if (id == R.id.signOutButton) {
            signOut();
        }*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.login_bar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                Toast.makeText(this, "You clicked on settings!", Toast.LENGTH_LONG);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}

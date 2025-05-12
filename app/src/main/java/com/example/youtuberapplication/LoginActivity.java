package com.example.youtuberapplication;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import androidx.credentials.*;
import android.os.CancellationSignal;

import androidx.credentials.exceptions.GetCredentialCancellationException;
import androidx.credentials.exceptions.GetCredentialException;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatButton;

import com.google.android.libraries.identity.googleid.GetGoogleIdOption;
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential;
import com.google.android.libraries.identity.googleid.GoogleIdTokenParsingException;
// Note: These imports are placeholders; you'll need to use the proper verification libraries
// For example, if using Firebase Auth, you'd import Firebase authentication components
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;


import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executor;
public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "GoogleSignIn";
    AppCompatButton googleSignInBtn;

    GetGoogleIdOption googleIdOption;
    GetCredentialRequest request;
    CredentialManager credentialManager;
    CancellationSignal cancelSignal;
    Executor mainExecutor;

    private FirebaseAuth mAuth;

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if(currentUser!=null)
        {
            Toast.makeText(this,"User is currently signed in " + currentUser.getDisplayName(),Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        mAuth = FirebaseAuth.getInstance();

        cancelSignal = new CancellationSignal();
        mainExecutor = ContextCompat.getMainExecutor(this);

        // Initialize the credential manager
        credentialManager = CredentialManager.create(this);

        // Set up the Google Id Option
        googleIdOption = new GetGoogleIdOption.Builder()
                .setFilterByAuthorizedAccounts(false) // Check for previously authorized accounts
                .setServerClientId("330474295363-bt5vkvps7vkl535g4nus84a3b9cqrr60.apps.googleusercontent.com") // Web Client ID
                .setAutoSelectEnabled(false) // Automatically selects the best account if available
                .build();

        // Build the request
        request = new GetCredentialRequest.Builder()
                .addCredentialOption(googleIdOption)
                .build();

        googleSignInBtn = findViewById(R.id.googleSignInBtn);

        googleSignInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "Sign-in button tapped!");
                signInWithGoogle();
            }
        });

    }

    public void signInWithGoogle() {
        credentialManager.getCredentialAsync(
                /* context */           this,
                /* your request */      request,
                /* cancellationSignal */cancelSignal,
                /* executor */          mainExecutor,
                /* callback */          new CredentialManagerCallback<GetCredentialResponse, GetCredentialException>() {
                    @Override
                    public void onResult(GetCredentialResponse response) {
                        try {
                            handleSignIn(response);
                        } catch (GoogleIdTokenParsingException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    @Override
                    public void onError(GetCredentialException e) {
                        handleFailure(e);
                    }
                }
        );

    }

    private void handleSignIn(GetCredentialResponse result) throws GoogleIdTokenParsingException {
        Object credential = result.getCredential();
        Log.e(TAG,"In handleSignIn");
        // We expect only a CustomCredential here (Google ID Token)
        if (credential instanceof CustomCredential) {
            CustomCredential cc = (CustomCredential) credential;

            // Check it's really a Google ID token
            if (GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL
                    .equals(cc.getType())) {
                GoogleIdTokenCredential tokenCred =
                        GoogleIdTokenCredential.createFrom(cc.getData());

                String idToken = tokenCred.getIdToken();
                String displayName = tokenCred.getDisplayName();
                String email       = tokenCred.getId();

                Log.d(TAG, "Signed in as " + displayName + " (" + email + ")");
                Toast.makeText(this,
                                "Welcome, " + displayName,
                                Toast.LENGTH_SHORT)
                        .show();

                //  Send the ID token to your backend to verify & create a session
               validateTokenWithBackend(idToken);

                createUserSchema();
               navigateToPackages();

            } else {
                Log.e(TAG, "Unknown custom credential type: " + cc.getType());
                Toast.makeText(this,
                                "Sign-in failed: unexpected credential",
                                Toast.LENGTH_LONG)
                        .show();
            }

        } else {

            Log.e(TAG, "Unexpected credential type: " + credential.getClass());
            Toast.makeText(this,
                            "Sign-in failed: bad credential",
                            Toast.LENGTH_LONG)
                    .show();
        }
    }

    private void validateTokenWithBackend(String idToken) {
        // Using Firebase Authentication
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
            .addOnCompleteListener(this, task -> {
                if (task.isSuccessful()) {
                    // Sign in success
                    Log.d(TAG, "signInWithCredential:success");
                } else {
                    // Sign in fails
                    Log.w(TAG, "signInWithCredential:failure", task.getException());
                }
            });

    }

    private void createUserSchema() {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser == null) {
            Log.e("Firebase", "No authenticated user found.");
            return;
        }

        // Use the correct database URL here
        FirebaseDatabase database = FirebaseDatabase.getInstance("https://youtubers-application-default-rtdb.firebaseio.com/");
        DatabaseReference usersRef = database.getReference("users");

        // Creating a new user
        Map<String, Object> user = new HashMap<>();
        user.put("email", currentUser.getEmail());
        user.put("name", currentUser.getDisplayName());
        user.put("youtubeChannelLink", ""); // Empty initially, to be filled later



        // Add the video to the user's videos map
        Map<String, Object> videos = new HashMap<>();


        // Add videos to the user
        user.put("videos", videos);

        // Use the user's UID as the key to ensure uniqueness and consistency
        String userId = currentUser.getUid();
        usersRef.child(userId).setValue(user).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Log.d("Firebase", "User created successfully!");
            } else {
                Log.e("Firebase", "Failed to create user: " + task.getException().getMessage());
            }
        });
    }
    private void handleFailure(GetCredentialException e) {
        Log.e(TAG, "Credential fetch failed: " + e.getMessage(), e);
        if (e instanceof GetCredentialCancellationException) {
            Log.w(TAG, "User canceled the sign-in flow");
            Toast.makeText(this, "Sign-in canceled", Toast.LENGTH_SHORT).show();
        } else if (e.getMessage() != null) {
            if (e.getMessage().contains("network")) {
                Log.w(TAG, "Network error during sign-in");
                Toast.makeText(this, "Network error. Please try again.", Toast.LENGTH_SHORT).show();
            } else {
                Log.w(TAG, "Unknown error occurred: " + e.getMessage());
                Toast.makeText(this, "An error occurred: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "An unexpected error occurred.", Toast.LENGTH_SHORT).show();
        }
    }

    private void navigateToPackages()
    {

        Intent intent = new Intent(LoginActivity.this,PackagesActivity.class);
        startActivity(intent);
        finish();
    }

}
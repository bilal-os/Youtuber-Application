package com.example.youtuberapplication;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import androidx.credentials.*;
import android.os.CancellationSignal;

import androidx.credentials.exceptions.GetCredentialException;
import android.util.Log;
import android.view.View;

import androidx.appcompat.widget.AppCompatButton;

import com.google.android.libraries.identity.googleid.GetGoogleIdOption;
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential;
import com.google.android.libraries.identity.googleid.GoogleIdTokenParsingException;
// Note: These imports are placeholders; you'll need to use the proper verification libraries
// For example, if using Firebase Auth, you'd import Firebase authentication components
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.GoogleAuthProvider;


import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.concurrent.Executor;
public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "GoogleSignIn";
    AppCompatButton googleSignInBtn;

    GetGoogleIdOption googleIdOption;
    GetCredentialRequest request;
    CredentialManager credentialManager;
    CancellationSignal cancelSignal;
    Executor mainExecutor;

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

        cancelSignal = new CancellationSignal();
        mainExecutor = ContextCompat.getMainExecutor(this);

        // Initialize the credential manager
        credentialManager = CredentialManager.create(this);

        // Set up the Google Id Option
        googleIdOption = new GetGoogleIdOption.Builder()
                .setFilterByAuthorizedAccounts(false) // Check for previously authorized accounts
                .setServerClientId("437955746032-j0237e9vi3acvvpsnjkctd4o9odm9eu6.apps.googleusercontent.com") // Your Web Client ID
                .setAutoSelectEnabled(false) // Automatically selects the best account if available
                .setNonce(generateNonce(16)) // Add nonce for extra security
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
                        handleSignIn(response);
                    }
                    @Override
                    public void onError(GetCredentialException e) {
                        handleFailure(e);
                    }
                }
        );

    }

    private void handleSignIn(GetCredentialResponse result) {
        @SuppressLint({"NewApi", "LocalSuppress"}) Object credential = result.getCredential();

        // Handle different credential types
        if (credential instanceof PublicKeyCredential) {
            // Passkey credential
            PublicKeyCredential publicKeyCredential = (PublicKeyCredential) credential;
            // Share responseJson with your server to validate and authenticate
            String authResponseJson = publicKeyCredential.getAuthenticationResponseJson();
            Log.d(TAG, "Public key credential received");
            // TODO: Send this responseJson to your server

        } else if (credential instanceof PasswordCredential) {
            // Password credential
            PasswordCredential passwordCredential = (PasswordCredential) credential;
            // Send ID and password to your server to validate and authenticate
            String username = passwordCredential.getId();
            String password = passwordCredential.getPassword();
            Log.d(TAG, "Password credential received for user: " + username);
            // TODO: Send these credentials to your server

        } else if (credential instanceof CustomCredential) {
            // Google ID Token credential
            CustomCredential customCredential = (CustomCredential) credential;

            if (customCredential.getType().equals(GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL)) {
                // Extract the Google ID Token
                GoogleIdTokenCredential googleIdTokenCredential =
                        GoogleIdTokenCredential.createFrom(customCredential.getData());

                // Get the ID token to send to your backend
                String idTokenString = googleIdTokenCredential.getIdToken();
                Log.d(TAG, "Google ID Token retrieved successfully");

                // For UX purposes only, you can extract user information directly
                String displayName = googleIdTokenCredential.getDisplayName();
                String email = googleIdTokenCredential.getId(); // The email

                Log.d(TAG, "Display Name: " + displayName);
                Log.d(TAG, "Email: " + email);

                // Send token to your backend for validation
                validateTokenWithBackend(idTokenString);

                // Note: Don't store or control access to user data based on these
                // values without validating the token first

            } else {
                // Catch any unrecognized custom credential type
                Log.e(TAG, "Unexpected type of custom credential: " + customCredential.getType());
            }
        } else {
            // Catch any unrecognized credential type
            Log.e(TAG, "Unexpected type of credential");
        }
    }

    private void validateTokenWithBackend(String idToken) {
        // Option 1: Send the token to your backend server for validation
        // This is the recommended approach for production applications
        //
        // Example with Retrofit:
        /*
        apiService.verifyGoogleToken(idToken).enqueue(new Callback<AuthResponse>() {
            @Override
            public void onResponse(Call<AuthResponse> call, Response<AuthResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // Save authentication state and user info
                    saveUserSession(response.body().getUser());
                    // Navigate to main screen
                    navigateToMainActivity();
                } else {
                    Log.e(TAG, "Server validation failed");
                }
            }

            @Override
            public void onFailure(Call<AuthResponse> call, Throwable t) {
                Log.e(TAG, "Network error during token validation", t);
            }
        });
        */

        // Option 2: If using Firebase Authentication (client-side validation example)
        // Not recommended for production without additional server validation
        /*
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        FirebaseAuth.getInstance().signInWithCredential(credential)
            .addOnCompleteListener(this, task -> {
                if (task.isSuccessful()) {
                    // Sign in success
                    Log.d(TAG, "signInWithCredential:success");
                    // Navigate to main screen
                    navigateToMainActivity();
                } else {
                    // Sign in fails
                    Log.w(TAG, "signInWithCredential:failure", task.getException());
                }
            });
        */

        // For testing purposes only:
        Log.d(TAG, "Token validation would happen here in production");
        // In a real app, don't proceed until token is validated
    }

    private void handleFailure(GetCredentialException e) {
        // Log the failure for debugging
        Log.e(TAG, "Credential fetch failed", e);

        // You can provide more specific error handling based on the exception type
        // For example:
        if (e.getMessage() != null) {
            if (e.getMessage().contains("canceled")) {
                Log.w(TAG, "User canceled the sign-in flow");
                // Maybe show a toast: "Sign-in canceled"
            } else if (e.getMessage().contains("network")) {
                Log.w(TAG, "Network error during sign-in");
                // Maybe show a dialog: "Check your internet connection"
            }
        }
    }

    public String generateNonce(int length) {
        SecureRandom random = new SecureRandom();
        return new BigInteger(length * 5, random).toString(32).substring(0, length);
    }

    // You may want to add methods for navigating to other activities
    /*
    private void navigateToMainActivity() {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        finish(); // Close the login activity
    }
    */

}
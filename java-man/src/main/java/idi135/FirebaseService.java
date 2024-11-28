package idi135;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.auth.oauth2.GoogleCredentials; 
import java.io.FileInputStream;
import java.io.IOException;  // Import IOException

public class FirebaseService {

    private static FirebaseDatabase database;

    // Initialize Firebase SDK
    public static void initializeFirebase() throws IOException {
        if (FirebaseApp.getApps().isEmpty()) {
            try {
                // Point this to your Firebase Admin SDK service account key
                FileInputStream serviceAccount =
                        new FileInputStream("java-man/src/main/java/idi135/multi-man-e8fc8-firebase-adminsdk-r7wf7-2e669e0c1c.json");

                FirebaseOptions options = new FirebaseOptions.Builder()
                        .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                        .setDatabaseUrl("https://multi-man-e8fc8-default-rtdb.europe-west1.firebasedatabase.app/") // Replace with your Firebase Realtime DB URL
                        .build();

                // Initialize Firebase with the options
                FirebaseApp.initializeApp(options);
                database = FirebaseDatabase.getInstance(); // Set the database reference
                System.out.println("Firebase Initialized Successfully!");
            } catch (IOException e) {
                System.err.println("Error initializing Firebase: " + e.getMessage());
                throw e;  // Throw the exception so it can be caught or logged by the caller
            }
        }
    }

    // Write player score to Firebase
    public static void writeLeaderboard(String playerName, int score) {
        if (database == null) {
            System.err.println("Firebase is not initialized.");
            return;
        }

        DatabaseReference ref = database.getReference("leaderboard");
        PlayerScore playerScore = new PlayerScore(playerName, score);
        ref.push().setValueAsync(playerScore);
    }

    public static void getLeaderboard(ValueEventListener listener) {
    DatabaseReference ref = FirebaseDatabase.getInstance().getReference("leaderboard");
    ref.addListenerForSingleValueEvent(listener);  // Add listener to fetch data
}
}

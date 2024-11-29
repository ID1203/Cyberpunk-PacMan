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

    //Static variable for database
    private static FirebaseDatabase database;

    // Initialize Firebase SDK
    //Catcvh asy input output merrorss through exception
    public static void initializeFirebase() throws IOException {
        //checks to see if database has been intialized
        if (FirebaseApp.getApps().isEmpty()) {
            try {
                // Point this to your Firebase Admin SDK service account key
                FileInputStream serviceAccount =
                        new FileInputStream("java-man/src/main/java/idi135/pac-man-4fccb-firebase-adminsdk-1zlrp-8836582a4b.json");
                        
                //This creates a FirebaseOptions object, which holds the configuration for Firebase
                FirebaseOptions options =  FirebaseOptions.builder()
                        .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                        .setDatabaseUrl("https://pac-man-4fccb-default-rtdb.europe-west1.firebasedatabase.app/") // Replace with your Firebase Realtime DB URL
                        .build();

                // Initialize Firebase with the options
                FirebaseApp.initializeApp(options);
                database = FirebaseDatabase.getInstance(); // Set the database reference to be used to read and write 
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
        //checks for leaderboard node
        DatabaseReference ref = database.getReference("leaderboard");
        //created new playerscore
        PlayerScore playerScore = new PlayerScore(playerName, score);
        //pushes new data to database and created uniqueid
        ref.push().setValueAsync(playerScore);
    }
    //collects leaderbaord data
    public static void getLeaderboard(ValueEventListener listener) {
    //finds leaderbaord node
    DatabaseReference ref = FirebaseDatabase.getInstance().getReference("leaderboard");
    ref.addListenerForSingleValueEvent(listener);  // Add listener to fetch data
}
}


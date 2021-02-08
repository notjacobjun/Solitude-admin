package com.Solitude.Configuration;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.io.FileInputStream;
import java.io.IOException;

@Configuration
public class FirebaseConfig {
	 @PostConstruct
    public void init() {

        FileInputStream serviceAccount;
        try {
            serviceAccount = new FileInputStream("solitude-credentials.json");


            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .setDatabaseUrl("https://solitude-478ad.firebaseio.com")
                    .build();

            FirebaseApp firebaseApp = FirebaseApp.initializeApp(options);


            System.out.println(firebaseApp);


        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


package config;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import javax.servlet.annotation.WebListener;

@WebListener
public class FirebaseInitializer implements ServletContextListener{

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        try {
            
            String path = sce.getServletContext().getRealPath("/WEB-INF/firebase-adminsdk.json");
            
            File file = new File(path);
            if(!file.exists()){
                System.out.println("Firebase credentials file not found: " + path);
                return;
            }
            
            FileInputStream serviceAccount = new FileInputStream(file);
            
            GoogleCredentials googleCredentials = GoogleCredentials.fromStream(serviceAccount);
            
            FirebaseOptions firebaseOptions = new FirebaseOptions.Builder().
                    setCredentials(googleCredentials).build();
            
            if(FirebaseApp.getApps().isEmpty()){
                FirebaseApp.initializeApp(firebaseOptions);
                System.out.println("✅ Firebase initialized successfully!");
                
            } else {
                System.out.println("⚠️ Firebase already initialized.");
            }
            
        } catch (IOException e) {
            System.out.println("🔥 Firebase initialization failed: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
         System.out.println("🔴 Firebase shut down with the server.");
    }
    
}

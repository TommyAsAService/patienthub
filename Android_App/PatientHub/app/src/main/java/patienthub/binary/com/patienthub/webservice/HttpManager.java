package patienthub.binary.com.patienthub.webservice;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Mark Aziz on 7/10/2015.
 */
public class HttpManager {

    public static String getData(String uri, String token){

        BufferedReader reader = null;

        try {
            URL url = new URL(uri);

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestProperty("Authorization","Token token="+token);
            connection.setRequestProperty("Content-Type","application/json");
            connection.setRequestMethod("GET");

            StringBuilder builder = new StringBuilder();
            reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            String line;
            while((line = reader.readLine()) != null){
                builder.append(line+"\n");
            }

            return builder.toString();

        }catch (java.io.IOException e) {
            e.printStackTrace();
            return null;
        }finally {
            if(reader != null){
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    return null;
                }
            }

        }


    }

}

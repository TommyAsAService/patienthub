package patienthub.binary.com.patienthub.webservice;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
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

    public static String postMedicationData(String comment, String token, int dosageID, boolean taken) throws IOException {

        //curl -H "Authorization: Token token=dKxUDRoJLsxSHFtec9Nm" -H "Content-Type: application/json" -X POST -d '{"taken":"true","comment":"xyz", "dosage_id":6}' http://patienthubstage.herokuapp.com/api/v1/patient/medication_feedback
        //curl -H "Authorization: Token token=estgV1ygkSR3tWpBBViH" -H "Content-Type: application/json" -X POST -d '{"question":"what","answer":"xyz"}' http://localhost:3000/api/v1/patient/quiz_feedback

        String toReturn = "";

        HttpURLConnection httpcon = null;

        httpcon = (HttpURLConnection) ((new URL("http://patienthubstage.herokuapp.com/api/v1/patient/medication_feedback").openConnection()));

        httpcon.setDoOutput(true);
        httpcon.setRequestProperty("Content-Type", "application/json");
        //ADD THE TOKEN
        token = "Token token="+token;
        httpcon.setRequestProperty("Authorization", token);

        httpcon.setRequestMethod("POST");
        httpcon.connect();

        byte[] outputBytes = new byte[0];

        //ADD THE COMMENT AND DOSAGE ID
        String strTaken = "";
        if(taken) strTaken = "true";
        else strTaken = "false";
        outputBytes = ("{\"taken\":\""+strTaken+"\",\"comment\":\""+comment+"\", \"dosage_id\":\""+dosageID+"\"}").getBytes("UTF-8");

        OutputStream os = null;
        os = httpcon.getOutputStream();
        os.write(outputBytes);

        os.close();

        StringBuilder sb = new StringBuilder();
        int HttpResult = 0;
        HttpResult = httpcon.getResponseCode();
        if(HttpResult == HttpURLConnection.HTTP_OK){
            BufferedReader br = null;
            br = new BufferedReader(new InputStreamReader(httpcon.getInputStream(),"utf-8"));
            String line = null;
            while ((line = br.readLine()) != null) {
                sb.append(line + "\n");
            }

            br.close();

            toReturn = ""+sb.toString();

        }else{
            toReturn = httpcon.getResponseMessage();

        }

        return toReturn;
    }

    public static String postQuizData(String question, String token, String answer) throws IOException {

        //curl -H "Authorization: Token token=estgV1ygkSR3tWpBBViH" -H "Content-Type: application/json" -X POST -d '{"question":"what","answer":"xyz"}' http://localhost:3000/api/v1/patient/quiz_feedback

        String toReturn = "";

        HttpURLConnection httpcon = null;

        httpcon = (HttpURLConnection) ((new URL("http://patienthubstage.herokuapp.com/api/v1/patient/quiz_feedback").openConnection()));

        httpcon.setDoOutput(true);
        httpcon.setRequestProperty("Content-Type", "application/json");
        //ADD THE TOKEN
        token = "Token token="+token;
        httpcon.setRequestProperty("Authorization", token);

        httpcon.setRequestMethod("POST");
        httpcon.connect();

        byte[] outputBytes = new byte[0];

        question = question.replace("\n", "").replace("\r", "");

        //ADD THE COMMENT AND DOSAGE ID
        outputBytes = ("{\"question\":\""+question+"\",\"answer\":\""+answer+"\"}").getBytes("UTF-8");
        System.out.println("{\"question\":\""+question+"\",\"answer\":\""+answer+"\"}");

        OutputStream os = null;
        os = httpcon.getOutputStream();
        os.write(outputBytes);

        os.close();

        StringBuilder sb = new StringBuilder();
        int HttpResult = 0;
        HttpResult = httpcon.getResponseCode();
        if(HttpResult == HttpURLConnection.HTTP_OK){
            BufferedReader br = null;
            br = new BufferedReader(new InputStreamReader(httpcon.getInputStream(),"utf-8"));
            String line = null;
            while ((line = br.readLine()) != null) {
                sb.append(line + "\n");
            }

            br.close();

            toReturn = ""+sb.toString();

        }else{
            toReturn = httpcon.getResponseMessage();

        }

        return toReturn;
    }

}

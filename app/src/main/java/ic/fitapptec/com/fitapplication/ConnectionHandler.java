package ic.fitapptec.com.fitapplication;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class ConnectionHandler {
    public static String APIHOST = "https://binarycoffee.net/fitApp/";

    public static String login(String username, String pass) throws JSONException {
        HttpURLConnection connection = null;
        JSONObject obj = new JSONObject();
        obj.put("username", username);
        obj.put("pass", pass);

        String urlpath = APIHOST + "api/loginCliente.php" ;

        try {
            URL url=new URL(urlpath);
            connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Accept", "application/json");
            OutputStreamWriter streamWriter = new OutputStreamWriter(connection.getOutputStream());
            streamWriter.write(obj.toString());
            streamWriter.flush();
            StringBuilder stringBuilder = new StringBuilder();
            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK){
                InputStreamReader streamReader = new InputStreamReader(connection.getInputStream());
                BufferedReader bufferedReader = new BufferedReader(streamReader);
                String response = "No respuesta";
                while ((response = bufferedReader.readLine()) != null) {
                    stringBuilder.append(response + "\n");
                }
                bufferedReader.close();

                Log.d("LoginJSON", stringBuilder.toString());
                return stringBuilder.toString();
            } else {
                Log.e("ResponseCode=!HTTP_OK", connection.getResponseMessage());
                return "";
            }
        } catch (Exception exception){
            Log.e("LoginJSON", exception.toString());
            return "";
        } finally {
            if (connection != null){
                connection.disconnect();
            }
        }
    }
}

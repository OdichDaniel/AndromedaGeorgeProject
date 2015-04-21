package quiz.Intelli.app.httpHelpers;

import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ConnectTimeoutException;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.SocketException;
import java.net.URI;
import java.net.URISyntaxException;

import quiz.Intelli.app.database.IntelliContracts;
import quiz.Intelli.app.helpers.CommonUtils;
import quiz.Intelli.app.helpers.IntelliConstants;

/**
 * Created by Dan on 4/18/2015.
 */
public class NetworkHelper<T>
{
    private ConnectionCallbacks connectionCallbacks;
    private StringBuilder builder;
    private HttpResponse response;
    private Context context;

    public NetworkHelper(T callback)
    {
       connectionCallbacks = (ConnectionCallbacks)callback;
    }

    public void fetchCategory(String categoryName, Context context)
    {
        builder = new StringBuilder();
        this.context = context;
        //model the api to look something like this__localhost/intelli-websites?category=categoryName;
        builder.append(CommonUtils.encodeString(IntelliConstants.BaseUri)).append("category=").
                append(CommonUtils.encodeString(categoryName));
    }

    private class MakeHttpRequest extends AsyncTask<Object, Void, String>
    {
       int statusCode;
        @Override
        protected String doInBackground(Object... params)
        {
            try
            {
                URI uri = new URI((String)params[0]);
                HttpGet get = new HttpGet(uri);
                response = ClientResolver.getHttpClient(false).execute(get);
                /**
                 *           **  WARNING, no data on the users device might also return status code of 200;
                 *           especially when using data connection, the telecos tend to return their home page which is also 200;
                 *          *****
                 */
                statusCode = response.getStatusLine().getStatusCode();
                if(response.getStatusLine().getStatusCode() == 200)
                {
                    InputStream stream = response.getEntity().getContent();
                    builder = new StringBuilder();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(stream, "UTF-8"),8);
                    String line = null;
                    while((line = reader.readLine()) != null)
                    {
                        builder.append(line + "\n");
                    }
                    return builder.toString();
                }
                else if(response.getStatusLine().getStatusCode() == 0)
                {
                    return "No internet connection, make sure you can connect to internet and try again";
                }
            }

            catch (URISyntaxException e) {
                e.printStackTrace();
            } catch (ClientProtocolException e) {
                e.printStackTrace();
            }
            catch(ConnectTimeoutException es)
            {
                return "Connection timeout";
            }
            catch (SocketException ex)
            {
                return "Server timeout";
            }
            catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result)
        {
            ErrorMessage errorMessage = new ErrorMessage();
            if(statusCode == 200)
            {
                try
                {
                    JSONObject questions = new JSONObject(result);
                    for (int i = 0; i<questions.length(); i++)
                    {
                        String question = questions.getString("questions"); // please make sure that this key names matches the remote
                                                                            // database columns names;
                        String correctAnswer = questions.getString("correctAnswer");
                        String firstIncorrect = questions.getString("firstIncorrect");
                        String secondIncorrect = questions.getString("secondIncorrect");
                        String thirdIncorrect = questions.getString("thirdIncorrect");

                        ContentValues contentValues = new ContentValues();
                        contentValues.put(IntelliContracts.Math.question, question);
                        contentValues.put(IntelliContracts.Math.correctAnswer, correctAnswer);
                        contentValues.put(IntelliContracts.Math.firstIncorrect, firstIncorrect);
                        contentValues.put(IntelliContracts.Math.secondIncorrect, secondIncorrect);
                        contentValues.put(IntelliContracts.Math.thirdIncorrect, thirdIncorrect);
                        //Insert into the db
                        Uri uri = context.getContentResolver().insert(IntelliContracts.Math.MathUploadUri, contentValues);
                    }
                    SuccessMessage successMessage = new SuccessMessage();
                    successMessage.setSuccessMessage("Complete");
                    connectionCallbacks.successQuery(successMessage);

                }catch(JSONException ex)
                {
                    //this should be a home page from a redirect from the telecos
                    errorMessage.setErrorMessage("Intelli server was unreachable, make sure you can connect to internet and try again");
                    connectionCallbacks.failedQuery(errorMessage);
                }
            }
            else
            {
                errorMessage.setErrorMessage(result);
                connectionCallbacks.failedQuery(errorMessage);
            }
        }
    }
}

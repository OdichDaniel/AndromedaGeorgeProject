package quiz.Intelli.app.intelli;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import quiz.Intelli.app.httpHelpers.ConnectionCallbacks;
import quiz.Intelli.app.httpHelpers.ErrorMessage;
import quiz.Intelli.app.httpHelpers.NetworkHelper;
import quiz.Intelli.app.httpHelpers.SuccessMessage;
import quiz.Intelli.app.portal.IntelliPortal;


public class MyActivity extends ActionBarActivity implements ConnectionCallbacks
{
    private Context context;
    private  NetworkHelper<MyActivity> networkHelper;
    private String[] categoryNames = {"", "", "", ""}; //these are the categoryNames, you best know it coz u have it in your db
    private int incrementAndNextCategory = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().hide();

        networkHelper = new NetworkHelper<MyActivity>(MyActivity.this);
        incrementAndNextCategory++;
        networkHelper.fetchCategory(categoryNames[0], this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.my, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void successQuery(SuccessMessage successMessage) {
        // the idea here is to fetch each category after the other, is it does not work
        // just fire async tasks from the oncreate method in a for loop, that is the simplest
        if(incrementAndNextCategory <= categoryNames.length)
        {
            networkHelper.fetchCategory(categoryNames[incrementAndNextCategory], this);
            incrementAndNextCategory++;
        }
        else
        {
            //start the portal
        }
    }

    @Override
    public void failedQuery(ErrorMessage errorMessage) {
        //not sure if the comparison will work well coze the string is too long; // will do a better way
          if(errorMessage.getErrorMessage().equals("No internet connection, make sure you can connect to internet and try again"))
          {
              showDialog(errorMessage.getErrorMessage(), "Connection failed");
          }
    }

    private void showDialog(String message, String title)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                return;
            }
        });
        builder.show();
    }
}

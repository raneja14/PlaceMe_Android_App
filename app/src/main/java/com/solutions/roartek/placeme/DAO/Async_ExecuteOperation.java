package com.solutions.roartek.placeme.DAO;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.solutions.roartek.placeme.Common.Constants;
import com.solutions.roartek.placeme.Common.Utility;
import com.solutions.roartek.placeme.Delegate.ResponseDelegate;
import com.solutions.roartek.placeme.Service.JsonManager;
import com.solutions.roartek.placeme.Service.JsonManagerImpl;

import java.net.URLConnection;

/**
 * Created by Raghav.Aneja on 10-12-2016.
 */
public class Async_ExecuteOperation extends AsyncTask<String,String,String> {

    private Context context;
    private ResponseDelegate responseDelegate;
    private ProgressDialog progressDialog;
    private Persistence persistence;

    public Async_ExecuteOperation(Context context,ResponseDelegate responseDelegate) {
        this.context = context;
        this.responseDelegate = responseDelegate;
    }
    
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog = new ProgressDialog(context);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Processing Request ...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setProgress(0);
        progressDialog.show();
    }

    @Override
    protected String doInBackground(String... params) {

         if(Utility.isInternetOff())
            return Constants.ERROR_MSG_NO_CONNECTION;
        else
        {
            String jsonParameter=params[0];
            persistence=new PersistenceImpl();
            URLConnection urlConn=persistence.establishConnection();
            if(urlConn!=null)
               return persistence.getRespomseFromDB(urlConn,jsonParameter);

           return Constants.ERROR_MSG_CONNECTION_FAILED;
        }
    }

    @Override
    protected void onPostExecute(String response) {
        super.onPostExecute(response);

        progressDialog.dismiss();
        if(response.equals(null) || response.equals("EMPTY") || response.equals(""))
            responseDelegate.onPostExecute(Constants.ERROR_MSG_EMPTY_RESPONSE);
        else  if(response.equals(Constants.ERROR_MSG_NO_CONNECTION) || response.equals(Constants.ERROR_MSG_CONNECTION_FAILED) )
            responseDelegate.onPostExecute(response);
        else
        {
            JsonManager jsonManager=new JsonManagerImpl(context);
            Object responseObj= jsonManager.decodeFromJSON(response);
            responseDelegate.onPostExecute(responseObj);
        }
    }
}

package com.solutions.roartek.placeme.DAO;

import com.solutions.roartek.placeme.Common.MyConfig;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by Raghav.Aneja on 10-12-2016.
 */
public class PersistenceImpl implements Persistence {

    @Override
    public URLConnection establishConnection() {

        URLConnection urlConn=null;
        try
        {
            URL url = new URL(MyConfig.phpURL);
            urlConn = url.openConnection();
            urlConn.setDoInput(true);
            urlConn.setDoOutput(true);
            urlConn.setUseCaches(false);
            urlConn.setConnectTimeout(MyConfig.CONNECTION_OUT_TIME);
            urlConn.setReadTimeout(MyConfig.READ_OUT_TIME);
            urlConn.setRequestProperty("Content-Type","application/json");
            urlConn.connect();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (SocketTimeoutException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return urlConn;
    }


    @Override
    public String getRespomseFromDB(URLConnection conn, String parameter) {
        String response="";
        try
        {
            DataOutputStream printout=new DataOutputStream(conn.getOutputStream());
            printout.writeBytes(parameter.toString());
            printout.flush();
            printout.close();

            BufferedReader reader=new BufferedReader(new InputStreamReader(conn.getInputStream()));
            response = reader.readLine();

         } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }
}

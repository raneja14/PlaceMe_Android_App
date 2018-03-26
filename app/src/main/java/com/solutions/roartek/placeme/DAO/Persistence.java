package com.solutions.roartek.placeme.DAO;

import java.net.URLConnection;

/**
 * Created by Raghav.Aneja on 10-12-2016.
 */
public interface Persistence {

        URLConnection establishConnection();

        String getRespomseFromDB(URLConnection conn,String parameter);
}

package com.solutions.roartek.placeme.Common;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
/**
 * Created by Raghav.Aneja on 10-12-2016.
 */
public class Utility {

    public static boolean isInternetOff()
    {
        try {
            InetAddress ipAddr = InetAddress.getByName("www.google.com");
            return ipAddr.equals("");
        } catch (UnknownHostException e) {
            e.printStackTrace();
            return true;
        }
    }

    public static String getMD5Value(String salt)
    {
       String md5=null;
        try
        {
            MessageDigest md=MessageDigest.getInstance("MD5");
            md.update(salt.getBytes(), 0, salt.length());
            md5=new BigInteger(1,md.digest()).toString(16);
        }
        catch (NoSuchAlgorithmException e)
        {
            e.printStackTrace();
        }
        return md5;
    }

    public static byte[] serialize(Object obj) {

        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream os = new ObjectOutputStream(bos);
            os.writeObject(obj);
            return  bos.toByteArray();

        } catch (IOException ex) {
            return  null;
        }
    }

    public static Object deSerialize(byte[] bytes)
    {
        try {
        ByteArrayInputStream bis=new ByteArrayInputStream(bytes);
        ObjectInputStream is=new ObjectInputStream(bis);
        return is.readObject();

    } catch (IOException ex) {
        return  null;
    } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Date convertStringToDate(String inputDate) {
        try {
            DateFormat parseFormat = new SimpleDateFormat(MyConfig.DATE_DB_FORMAT);
            return  parseFormat.parse(inputDate);
        } catch (ParseException e) {
            return null;
        }
    }

    public static String convertDateToString(Date tempDate) {
        if (tempDate == null)
            return "";
        else {
            DateFormat uiFormat = new SimpleDateFormat(MyConfig.DATE_UI_FORMAT);
            return uiFormat.format(tempDate);
        }
    }

    public static String getUIDate(String selectedDate) {
        try {

            SimpleDateFormat dt = new SimpleDateFormat(MyConfig.DATE_DB_FORMAT);
            Date date = dt.parse(selectedDate);

            SimpleDateFormat dt1 = new SimpleDateFormat(MyConfig.DATE_UI_FORMAT);
            return dt1.format(date);

        } catch (ParseException e) {

            e.printStackTrace();
            return "";
        }
    }

    public static String getDbDate(String uiDate) {
        try {

            SimpleDateFormat dt = new SimpleDateFormat(MyConfig.DATE_UI_FORMAT);
            Date date = dt.parse(uiDate);

            SimpleDateFormat dt1 = new SimpleDateFormat(MyConfig.DATE_DB_FORMAT);
            return dt1.format(date);

        } catch (ParseException e) {

            e.printStackTrace();
            return "";
        }
    }

    public static String getCurrentDate()
    {
        final Calendar c = Calendar.getInstance();
        int year=c.get(Calendar.YEAR);
        int month=c.get(Calendar.MONTH)+1;
        int day=c.get(Calendar.DAY_OF_MONTH);

        return year+"-"+month+"-"+day;
    }

    public static int compareDates(Date date1,Date date2)
    {
        return date1.compareTo(date2);
    }

    public static boolean getIntEquivalentBoolean(int num)
    {
        return num != 0;
    }

    public static String convertObjectToJSON(Object object)
    {
        String jsonString="";
        try
        {

            Gson gson=new Gson();
            jsonString= gson.toJson(object);
         }
        finally {
            return jsonString;
        }
    }

    public static Object convertJSONToObject(Class clazz, String jsonString){

       Object object=null;
        try {
            Gson gson = new Gson();
            object= gson.fromJson(jsonString, clazz);
        }
        finally {
            return object;
        }
    }

    public static void showToast(Context context,String message)
    {
        Toast.makeText(context,message, Toast.LENGTH_LONG).show();
    }

    public static void startAnimation(Context context,View view,int animationXmlId)
    {
        Animation animation= AnimationUtils.loadAnimation(context,animationXmlId);
        view.startAnimation(animation);
    }

    public static void setBackgorund(View layout,BitmapDrawable bitmapDrawable) {
        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.JELLY_BEAN)
            layout.setBackgroundDrawable(bitmapDrawable);
        else
            layout.setBackground(bitmapDrawable);
    }

    public static Bitmap blurimage(Bitmap image,Context context)
    {
        if (null == image) return null;

        Bitmap outputBitmap = Bitmap.createBitmap(image);
        final RenderScript renderScript = RenderScript.create(context);
        Allocation tmpIn = Allocation.createFromBitmap(renderScript, image);
        Allocation tmpOut = Allocation.createFromBitmap(renderScript, outputBitmap);

        //Intrinsic Gausian blur filter
        ScriptIntrinsicBlur theIntrinsic = ScriptIntrinsicBlur.create(renderScript, Element.U8_4(renderScript));
        theIntrinsic.setRadius(22.5f);
        theIntrinsic.setInput(tmpIn);
        theIntrinsic.forEach(tmpOut);
        tmpOut.copyTo(outputBitmap);
        return outputBitmap;
    }
}

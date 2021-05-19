package com.fibo.smartfarmer;

import com.fibo.smartfarmer.utils.Commons;

import org.json.JSONArray;
import org.json.JSONException;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import static org.junit.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void print_date_time(){
        System.out.println(new Commons().getCurrentTime());
    }

    @Test
    public void print_message_id(){
        System.out.println(new Commons().generateMessageId());
    }

    @Test
    public void for_each(){
        String[] arr =new String[]{"x","y","z","y","p"};
        for(String item: arr){
            System.out.println(item);
        }
    }

    @Test
    public void gettingJSONArray(){
        File file=new File("fertilizer.json");

        try {
            FileInputStream inputStream=new FileInputStream(file);
            BufferedReader reader=new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder builder=new StringBuilder();
            String data;
            while ((data=reader.readLine())!=null){
                builder.append(data);
            }

            JSONArray array=new JSONArray(builder.toString());
            for (int i=0;i<array.length();i++){
                String name= (String) array.get(i);
                System.out.println(name);

            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
    }


}
package com.carpcapture.carpcapture;

import android.util.Log;

import org.json.simple.JSONValue;
import org.json.simple.parser.ContainerFactory;
import org.json.simple.parser.JSONParser;

import java.text.ParseException;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by chris on 27/11/15.
 */
public class LakesFactory {
    /** getLakes should return an array of Lake objects
     *
     * Lake should implement getRectangles, id and name
     *
     * Rectangle should implement lat, lng, id
     *
     * */

    public LakesFactory(String jsonText) {
       // Pull out the lakes, iterate through passing everything to a LakeFactory
        JSONParser parser = new JSONParser();

        ContainerFactory containerFactory = new ContainerFactory(){
            public List creatArrayContainer() {
                return new LinkedList();
            }

            public Map createObjectContainer() {
                return new LinkedHashMap();
            }

        };





        try {
            Map json = (Map)parser.parse(jsonText, containerFactory);
            Iterator iter = json.entrySet().iterator();
            Log.e("LakesFactory", "==iterate result==");
            while(iter.hasNext()){
                Map.Entry entry = (Map.Entry)iter.next();
                Log.e("LakesFactory", entry.getKey() + "=>" + entry.getValue());

                Log.e("LakesFactory", entry.getClass().toString()); // LinkedHashMap$LinkedEntry
                Log.e("LakesFactory", entry.getValue().getClass().toString()); // LinkedList
            }

            Log.e("LakesFactory", "==toJSONString()==");
            Log.e("LakesFactory", JSONValue.toJSONString(json));

        }
        catch (org.json.simple.parser.ParseException e) {
            Log.e("LakesFactory", e.toString());
        }
    }
}

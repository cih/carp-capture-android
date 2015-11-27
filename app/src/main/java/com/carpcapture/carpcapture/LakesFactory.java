package com.carpcapture.carpcapture;

import android.util.Log;

import org.json.simple.JSONValue;
import org.json.simple.parser.ContainerFactory;
import org.json.simple.parser.JSONParser;

import java.lang.reflect.Array;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
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

    String jsonText;
    ArrayList lakesArray;

    public LakesFactory(String text) {
        jsonText = text;

        // Pull out the lakes, iterate through passing everything to a LakeFactory
    }

    public ArrayList convertToIterator() {
        Log.e("JSON TEXT", jsonText);

        JSONParser parser = new JSONParser();

        ContainerFactory containerFactory = new ContainerFactory(){
            public List creatArrayContainer() {
                return new ArrayList();
            }

            public Map createObjectContainer() {
                return new LinkedHashMap();
            }

        };


        try {
            Map json = (Map)parser.parse(jsonText, containerFactory);
            lakesArray = (ArrayList) json.get("lakes");

//            Iterator iter = json.entrySet().iterator();
//            Log.e("LakesFactory", "==iterate result==");
//            Log.e("ENTRY SET", json.entrySet().toString());
//
//
//            while(iter.hasNext()){
//                Map.Entry entry = (Map.Entry)iter.next();
//                Log.e("LakesFactory", entry.getKey() + "=>" + entry.getValue());
//
//                Log.e("LakesFactory", entry.getClass().toString()); // LinkedHashMap$LinkedEntry
//                Log.e("LakesFactory Value", entry.getValue().toString()); // LinkedList
//
//                LinkedList lakes = (LinkedList) entry.getValue();
//
//                lakesArray = lakes.listIterator();
//
//                while(lakesArray.hasNext()) {
//                    Log.e("LakeFactory", lakesArray.next().toString());
//                }
//            }

//            Log.e("LakesFactory", "==toJSONString()==");
//            Log.e("LakesFactory", JSONValue.toJSONString(json));
        }
        catch (org.json.simple.parser.ParseException e) {
            Log.e("LakesFactory", e.toString());
        }

        return lakesArray;
    }

}

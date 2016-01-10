package com.carpcapture.carpcapture;

import android.util.Log;

import org.json.simple.parser.ContainerFactory;
import org.json.simple.parser.JSONParser;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by chris on 10/01/16.
 */
public class PredictionFactory {
    String jsonText;
    ArrayList rectanglesArray;

    public PredictionFactory(String text) {
        jsonText = text;
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
            rectanglesArray = (ArrayList) json.get("rectangles");

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
            Log.e("RectanglesFactory", e.toString());
        }

        return rectanglesArray;
    }
}


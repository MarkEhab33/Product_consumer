package Converter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
public class ParseData {
	
	public static List<String> toList(JSONArray array) throws JSONException {
	    List<String> list = new ArrayList<String>();
	    for(int i = 0; i < array.length(); i++) {
	        String value = array.getString(i);
	        
	        list.add(value);
	    }
	    return list;
	}
	
	public static Map<String, Object> toMap(JSONObject object) throws JSONException {
	    Map<String, Object> map = new HashMap<String, Object>();

	    Iterator<String> keysItr = object.keys();
	    while(keysItr.hasNext()) {
	        String key = keysItr.next();
	        Object value = object.get(key);

	        if(value instanceof JSONArray) {
	            value = toList((JSONArray) value);
	        }

	        else if(value instanceof JSONObject) {
	            value = toMap((JSONObject) value);
	        }
	        map.put(key, value);
	    }
	    return map;
	}
	
	public List<String> GetList (String data , String attribute){
		List<String> myList = new ArrayList<String>();
		JSONObject jsonObject;
		try {
			jsonObject = new JSONObject( data );
			JSONArray jsonarr = jsonObject.getJSONArray( attribute );
			myList = this.toList(jsonarr);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		return myList;
	}
	
	public HashMap<String, List<String>> GetMap (String data , String attribute){
		JSONObject jsonObject,object;
		HashMap<String, List<String>> ReturnedMap = new HashMap<String, List<String>>();
		
		try {
			jsonObject = new JSONObject( data );
			object = jsonObject.getJSONObject(attribute);
		
			Map<String, Object> map = this.toMap(object);
			for(String k: map.keySet()) {
				
				ReturnedMap.put(k,((List<String>) map.get(k)));
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ReturnedMap; 
	}
	
}

package com.parser;

import java.io.*;
import org.junit.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

//import javax.json.JsonObject;
import javax.json.JsonObject;
import javax.xml.transform.Result;

public class Parser {
	private static final String filePath = "src/main/resources/response.json";
	public static Object result;

	public int add(int a, int b) {
		return a + b;
	}

//	public JSONObject resultsParser(String filePath)
//	@SuppressWarnings("unchecked")
	public static void main(String[] args)
	{
		//JSON parser object to parse file
		JSONParser jsonParser = new JSONParser();
		JSONObject parsed = null;
		try (FileReader reader = new FileReader(filePath))
		{
			//Read JSON file
			Object obj = jsonParser.parse(reader);
//			JSONObject stagesObject = (JSONObject) obj;
			JSONArray stages = (JSONArray) ((JSONObject) obj).get("Stages");//(JSONArray) stagesObject.get("Stages");

			for(Object o: stages){
				if ( o instanceof JSONObject ) {
					result = parseIDs((JSONObject)o);
					if (result != null) {
//						arrayOfObjects((JsonObject) o);
						result = (JSONObject) o;
					}
				}
			}
//			stages.forEach( event -> parseIDs( (JSONObject) event ));

		} catch (IOException | ParseException e) {
			e.printStackTrace();
		}
		result = parsed;
	}

//	void arrayOfObjects(JsonObject obj){
		//						JSONArray evArr = (JSONArray) ((JSONObject) o).get("Events");
//
//						JSONObject evObj = (JSONObject) evArr.get(0);
//						JSONArray team1Arr = (JSONArray) ((JSONObject) evObj).get("T1");
//
//						System.out.println(team1Arr);
//						JSONArray team2Arr = (JSONArray) ((JSONObject) evObj).get("T1");
//						System.out.println(team2Arr);
//	}


	private static String parseIDs(JSONObject eventContent)
	{
		String res = "";
		JSONArray eventArray = (JSONArray) eventContent.get("Events");
		JSONObject eventObj = (JSONObject) eventArray.get(0);

		JSONArray idsArray = (JSONArray) eventObj.get("IDs");

		for (Object o : idsArray) {
			JSONObject innerObj = (JSONObject) o;
			if (innerObj.get("d") != null) {
				res = innerObj.get("P") + "-" + innerObj.get("ID");
				System.out.println(innerObj.get("P") + "-" + innerObj.get("ID"));
			}
		}
		return res;
	}
}
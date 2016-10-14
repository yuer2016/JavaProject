package brakemr;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Oil {

	private static JSONParser parser = new JSONParser();

	private static String sql = "insert into tb_VehicleOilConsumeSta values  ";

	public static void main(String[] args) throws IOException, ParseException {
		String result = null;

		try(BufferedReader  reader = new BufferedReader(new FileReader(new File("d://oil.txt")));
				BufferedWriter writer = new BufferedWriter(new FileWriter("d://oil.sql",true));){
			while (null != (result=reader.readLine())) {
				//System.out.println(result);
				JSONObject json = (JSONObject)parser.parse(result.split("#")[1]);
				//System.out.println(json.get("maxSpeed").toString());
				StringBuilder sb = new StringBuilder();
				sb.append(sql);
				sb.append("('ycjp0001',");
				sb.append("'"+json.get("vehicleNo").toString()+"'"+",");
				sb.append("null ,");
				sb.append(json.get("avgOil").toString()+",");
				sb.append("null ,");
				sb.append("null ,");
				sb.append("null ,");
				sb.append("null ,");
				sb.append("null ,");
				sb.append("null )");

				writer.write(sb.toString()+";\n");
				//System.out.println(sb.toString());
			}

		}

	}}

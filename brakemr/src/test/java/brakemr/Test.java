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





public class Test {

	private static JSONParser parser = new JSONParser();
	
	private static String sql = "insert into tb_VehicleTypeLocusSta values  ";
	
	public static void main(String[] args) throws IOException, ParseException {
		
		String result = null;
		
		try(BufferedReader  reader = new BufferedReader(new FileReader(new File("d://locus.txt")));
			BufferedWriter writer = new BufferedWriter(new FileWriter("d://locus.sql",true));){
			while (null != (result=reader.readLine())) {
				JSONObject json = (JSONObject)parser.parse(result.split("	")[1]);
				//System.out.println(json.get("maxSpeed").toString());
				StringBuilder sb = new StringBuilder();
				sb.append(sql);
				sb.append("('ycjp0001',");
				sb.append(""+json.get("vehicleType").toString()+""+",");
				sb.append(json.get("maxSpeed").toString()+",");
				sb.append("null ,");
				sb.append("null ,");
				sb.append("null )");
				
				writer.write(sb.toString()+";\n");
				System.out.println(sb.toString());
		}
		
		
		
		
		
		}
		
		
			
			
			
			
		}
		
		
		//Map<String, List<Integer>> maps = new HashMap<String, List<Integer>>();
		/*Object[] values = {"hello","world"};
		String str =StringUtils.join(values,"\t");
		System.out.println(str);*/
		/*List<String> list = new ArrayList<String>();
		list.add("1234");list.add("567");list.add("890");
		JSONArray jsonArray  = new JSONArray();
		jsonArray.addAll(list);
		System.out.println(jsonArray.toJSONString());
		JSONObject obj=new JSONObject();
		obj.put("name","foo");
		obj.put("num",new Integer(100));
		obj.put("balance",new Double(1000.21));
		obj.put("is_vip",new Boolean(true));
		obj.put("nickname",null);  \xE9\x99\x95\xE6\xB5\x8BK1231
		System.out.print(obj.toJSONString());*/
		/*byte[] bytes =  Bytes.toBytes("张磊1234");
		String result = Bytes.toString(bytes);
		System.out.println(result);*/
		
		
		
	}


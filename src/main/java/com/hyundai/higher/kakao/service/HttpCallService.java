package com.hyundai.higher.kakao.service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Service;

@Service
public class HttpCallService {

	public String Call(String method, String reqURL, String header, String param) {
		String result = "";
		try {
			String response = "";
			URL url = new URL(reqURL);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod(method);
			conn.setRequestProperty("Authorization", header);
			if (param != null) {
				System.out.println("param : " + param);
				conn.setDoOutput(true);
				BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
				bw.write(param);
				bw.flush();

			}
			int responseCode = conn.getResponseCode();
			System.out.println("responseCode : " + responseCode);

			System.out.println("reqURL : " + reqURL);
			System.out.println("method : " + method);
			System.out.println("Authorization : " + header);
			InputStream stream = conn.getErrorStream();
			if (stream != null) {
				try (Scanner scanner = new Scanner(stream)) {
					scanner.useDelimiter("\\Z");
					response = scanner.next();
				}
				System.out.println("error response : " + response);
			}
			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line = "";
			while ((line = br.readLine()) != null) {
				result += line;
			}
			System.out.println("response body : " + result);

			JSONParser parser = new JSONParser();
			JSONObject jsonObject = (JSONObject) parser.parse(result);
			
			
			System.out.println("안녕");
			JSONArray jsonarray=(JSONArray) jsonObject.get("elements");
			System.out.println("안녕");
			
			
			if(jsonarray!=null) {
				
				System.out.println("안녕");
			
			
			Map<String, String> has = new HashMap<>();
			List<String> uuid = new ArrayList<>();
			
			for(int i=0; i<jsonarray.size();i++) {
				has = (Map<String, String>) jsonarray.get(i);
				uuid.add(has.get("uuid"));
			}
			
			System.out.print("리스트");
			System.out.print(uuid.toString());
			
			return uuid.toString();
			
			}
			

			br.close();
		} catch (IOException | ParseException e) {
			return e.getMessage();
		}
		return result;
	}

	public String CallwithToken(String method, String reqURL, String access_Token) {
		return CallwithToken(method, reqURL, access_Token, null);
	}

	public String CallwithToken(String method, String reqURL, String access_Token, String param) {
		String header = "Bearer " + access_Token;
		return Call(method, reqURL, header, param);
	}
}

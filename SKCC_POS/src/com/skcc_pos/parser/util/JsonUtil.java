package com.skcc_pos.parser.util;

import java.io.StringWriter;

import org.codehaus.jackson.map.ObjectMapper;

public class JsonUtil {
	private static ObjectMapper mapper = new ObjectMapper();
	
	/**
	 * @param o
	 * @return
	 */
	public static String toString(Object o) {
		try {
			StringWriter writer = new StringWriter();
			mapper.writeValue(writer, o);
			return writer.toString(); 
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}
	
	/**
	 * @param <T>
	 * @param jsonString
	 * @param cl
	 * @return
	 */
	public static <T> T toObject(String jsonString, Class<T> cl) {
		try {
			return mapper.readValue(jsonString, cl);
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}
}

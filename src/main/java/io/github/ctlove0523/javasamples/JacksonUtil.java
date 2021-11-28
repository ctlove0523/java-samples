package io.github.ctlove0523.javasamples;

import java.io.IOException;
import java.util.Objects;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JacksonUtil {
	private static final ObjectMapper MAPPER = new ObjectMapper();


	public static String object2Json(Object o) {
		if (Objects.isNull(o)) {
			return "";
		}

		try {
			return MAPPER.writeValueAsString(o);
		}
		catch (JsonProcessingException e) {
			e.printStackTrace();
		}

		return "";
	}

	public static <T> T json2Object(String json, Class<T> clazz) {
		if (json == null || json.isEmpty()) {
			return null;
		}

		try {
			return MAPPER.readValue(json, clazz);
		}
		catch (IOException exception) {
			exception.printStackTrace();
		}

		return null;
	}
}

package com.parser;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.junit.jupiter.params.provider.CsvSource;
import net.joshka.junit.json.params.JsonSource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.api.DisplayName;
import javax.json.JsonNumber;
import javax.json.JsonObject;
import javax.json.JsonString;

class ParserTests {

	@Test
	@DisplayName("1 + 1 = 2")
	void addsTwoNumbers() {
		Parser calculator = new Parser();
		assertEquals(2, calculator.add(1, 1), "1 + 1 should equal 2");
	}

	@ParameterizedTest(name = "{0} + {1} = {2}")
	@CsvSource({
			"0,    1,   1",
			"1,    2,   3",
			"49,  51, 100",
			"1,  100, 101"
	})
	void add(int first, int second, int expectedResult) {
		Parser calculator = new Parser();
		assertEquals(expectedResult, calculator.add(first, second),
				() -> first + " + " + second + " should equal " + expectedResult);
	}

	/**
	 * When passed <code>{"key":"value"}</code>, is executed a single time
	 * @param object the parsed JsonObject
	 */
	@Disabled
	@ParameterizedTest
	@JsonSource("{\"key\":\"value\"}")
	@DisplayName("provides a single object")
	void singleObject(JsonObject object) {
		assertNotNull(object.getString("key"));
	}

	/**
	 * When passed <code>[{"key":"value1"},{"key","value2"}]</code>, is
	 * executed once per element of the array
	 * @param object the parsed JsonObject array element
	 */
	@ParameterizedTest
	@JsonSource("[{\"key\":\"value1\"},{\"key\":\"value2\"}]")
	@DisplayName("provides an array of objects")
	void arrayOfObjects(JsonObject object) { assert(object.getString("key")).startsWith("value"); }

	/**
	 * When passed <code>[1, 2]</code>, is executed once per array element
	 * @param number the parsed JsonNumber for each array element
	 */
	@Disabled
	@ParameterizedTest
	@JsonSource("[1,2]")
	@DisplayName("provides an array of numbers")
	void arrayOfNumbers(JsonNumber number) {
		assert(number.intValue() > 0);
	}

	/**
	 * When passed <code>["value1","value2"]</code>, is executed once per array
	 * element
	 * @param string the parsed JsonString for each array element
	 */
	@Disabled
	@ParameterizedTest
	@JsonSource("[\"value1\",\"value2\"]")
	@DisplayName("provides an array of strings")
	void arrayOfStrings(JsonString string) {
		assert(string.getString()).startsWith("value");
	}

	/**
	 * When passed <code>{'key':'value'}</code>, is executed a single time.
	 * This simplifies writing inline JSON strings
	 * @param object the parsed JsonObject
	 */
	@Disabled
	@ParameterizedTest
	@JsonSource("{'key':'value'}")
	@DisplayName("handles simplified json")
	void simplifiedJson(JsonObject object) {
		assert(object.getString("key").equals("value"));
	}
}

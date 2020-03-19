package com.mobiquity.packer;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.PropertyConfigurator;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.mobiquity.exception.APIException;

public class Packer_Test {

	@BeforeAll
	public static void initialize() throws FileNotFoundException, IOException {
		try {
			Properties props = new Properties();
			props.load(new FileInputStream("src/main/java/resources/log4j.properties"));
			PropertyConfigurator.configure(props);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testExampleFile() {
		String filePath = "src/main/test/resources/example_input";
		try {
			String actual = Packer.pack(filePath);
			assertEquals("4\n-\n2,7\n8,9\n", actual);
		} catch (APIException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testInput_error_catching() {
		String filePath = "src/main/test/resources/example_input_01";
		try {
			String actual = Packer.pack(filePath);
			assertEquals("2\n-\n2,7\n2,4,8,9\n", actual);
		} catch (APIException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testInput_many_items() {
		String filePath = "src/main/test/resources/example_input_02";
		try {
			String actual = Packer.pack(filePath);
			assertEquals("4\n-\n2,7\n5,8,9,14,15\n-\n", actual);
		} catch (APIException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testInput_constrain_3() {
		String filePath = "src/main/test/resources/example_input_03";
		try {
			String actual = Packer.pack(filePath);
			assertEquals("4\n-\n2,7\n5,8,9,14,15\n-\n4,37,38,39,42\n-\n2,7\n5,8,9,14,15\n-\n2\n-\n2,7\n5,8,9,14,15\n1,2,3,4,5,6,7,12,13,14,18,20,30\n4\n-\n2,6\n5,8,9,14,15\n-\n", actual);
		} catch (APIException e) {
			e.printStackTrace();
		}
	}

}

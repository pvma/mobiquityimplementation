package com.mobiquity.packer;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.PropertyConfigurator;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import com.mobiquity.model.PackageLine;

public class LineParser_Test {

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
	public void test() {
		PackageLine line = LineParser
				.parsePackageLine("75 : (1,85.31,�29) (2,14.55,�74) (3,3.98,�16) (4,26.24,�55) (5,63.69,�52)");
		assertEquals(75.0, line.getMaxWeightAllowed(), 0.0f);
		assertEquals(5, line.getPackages().size());

	}

}

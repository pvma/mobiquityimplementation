package com.mobiquity.packer;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.PropertyConfigurator;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.mobiquity.exception.APIIndexException;
import com.mobiquity.exception.APIWeightException;
import com.mobiquity.model.PackageLine;

public class PackageSelector_Test {

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
	public void testLine1() {
		try {
			PackageLine pl = LineParser.parsePackageLine(
					"81 : (1,53.38,€45) (2,88.62,€98) (3,78.48,€3) (4,72.30,€76) (5,30.18,€9) (6,46.34,€48)");
			String result = PackageSelector.getPackage(pl);
			assertEquals("4", result);
		} catch (APIIndexException | APIWeightException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testLine2() {
		try {
			PackageLine pl = LineParser.parsePackageLine("8 : (1,15.3,€34)");
			String result = PackageSelector.getPackage(pl);
			assertEquals("-", result);
		} catch (APIIndexException | APIWeightException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testLine3() {

		try {
			PackageLine pl = LineParser.parsePackageLine(
					"75 : (1,85.31,€29) (2,14.55,€74) (3,3.98,€16) (4,26.24,€55) (5,63.69,€52) (6,76.25,€75) (7,60.02,€74) (8,93.18,€35) (9,89.95,€78)");
			String result = PackageSelector.getPackage(pl);
			assertEquals("2,7", result);
		} catch (APIIndexException | APIWeightException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testLine4() {

		try {
			PackageLine pl = LineParser.parsePackageLine(
					"56 : (1,90.72,€13) (2,33.80,€40) (3,43.15,€10) (4,37.97,€16) (5,46.81,€36) (6,48.77,€79) (7,81.80,€45) (8,19.36,€79) (9,6.76,€64)");
			String result = PackageSelector.getPackage(pl);
			assertEquals("8,9", result);
		} catch (APIIndexException | APIWeightException e) {
			e.printStackTrace();
		}
	}

}

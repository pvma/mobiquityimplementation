package com.mobiquity.packer;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.mobiquity.constants.PropertyConstants;
import com.mobiquity.exception.APIException;
import com.mobiquity.exception.APIIndexException;
import com.mobiquity.exception.APIWeightException;
import com.mobiquity.model.PackageLine;

public class Packer {

	static final Logger logger = Logger.getLogger(Packer.class);

	private Packer() {

	}

	public static String pack(String filePath) throws APIException {
		initJunit();
		logger.info(filePath);
		List<String> lines = Collections.emptyList();
		try {
			lines = Files.readAllLines(Paths.get(filePath), StandardCharsets.UTF_8);
		} catch (IOException e) {
			throw new APIException("Error reading file. Check if it exist or file is not UTF-8 encoded");
		}
		StringBuilder sb = new StringBuilder();
		if (lines.size() > 0) {
			for (String line : lines) {
				PackageLine packageLine = null;
				
				try {
					packageLine = LineParser.parsePackageLine(line);
					sb.append(PackageSelector.getPackage(packageLine));
					sb.append(PropertyConstants.NEW_LINE);
				} catch (APIWeightException | APIIndexException e) {
					// APIWeightException : if max weight is wrong, continue on next line
					// APIIndexException : if index of an item is wrong continue on next line
					logger.warn(e);
				} catch (Throwable e) {
					// Any other exception, stop and throw as APIException
					throw new APIException(e.getLocalizedMessage());
				}
			}
		}
		logger.info(sb);
		return sb.toString();
	}

	public static void main(String args[]) {
		try {
			Packer.pack(args[1]);
		} catch (APIException e) {
			e.printStackTrace();
		}
	}

	private static void initJunit() {
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
}

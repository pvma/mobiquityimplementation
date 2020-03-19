package com.mobiquity.packer;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.log4j.Logger;

import com.mobiquity.model.PackageLine;
import com.mobiquity.model.Item;

public class LineParser {

	static final Logger logger = Logger.getLogger(LineParser.class);

		
	public static PackageLine parsePackageLine(String line) {
		float maxPackageWeithAllowed = 0;
		List<Item> packages = new ArrayList<Item>();
		if (StringUtils.isNotBlank(line)) {
			String[] parts = line.split(":");
			if (parts.length > 1) {
				maxPackageWeithAllowed = NumberUtils.createFloat(parts[0]);
				packages = LineParser.splitLinePackages(parts[1]);
			}
		}
		logger.debug("maxPackageWeithAllowed: " + maxPackageWeithAllowed);
		logger.debug("packages.size(): " + packages.size());
		PackageLine packageLine = new PackageLine();
		packageLine.setMaxWeightAllowed(maxPackageWeithAllowed);
		packageLine.setPackages(packages);
		return packageLine;
	}

	private static List<Item> splitLinePackages(String str) {
		return Stream.of(str.trim().split(" ")).map(elem -> {
			elem = elem.replace("(", "").replace(")", "");
			String[] values = elem.split(",");
			Item obj = new Item();
			obj.setIndex(Integer.parseInt(values[0]));
			obj.setWeight(Float.parseFloat(values[1]));
			obj.setPrice(Float.parseFloat(values[2].substring(1)));
			return obj;
		}).collect(Collectors.toList());
	}

}

package com.mobiquity.packer;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.log4j.Logger;

import com.mobiquity.exception.APIWeightException;
import com.mobiquity.model.Item;
import com.mobiquity.model.PackageLine;

public class LineParser {

	static final Logger logger = Logger.getLogger(LineParser.class);

	// Return a wrapper object of the max weight allowed plus the list of items to be processed
	public static PackageLine parsePackageLine(String line) throws APIWeightException {
		float maxPackageWeithAllowed = 0;
		List<Item> items = new ArrayList<Item>();
		if (StringUtils.isNotBlank(line)) {
			String[] parts = line.split(":");
			if (parts.length > 1) {
				try {
					maxPackageWeithAllowed = NumberUtils.createFloat(parts[0]);
				} catch (NumberFormatException e) {
					throw new APIWeightException(e.getLocalizedMessage());
				}
				items = LineParser.splitLinePackages(parts[1]);
			}
		}
		logger.debug("maxPackageWeithAllowed: " + maxPackageWeithAllowed);
		logger.debug("items.size(): " + items.size());
		PackageLine packageLine = new PackageLine(maxPackageWeithAllowed, items);
		return packageLine;
	}

	// Return the list of object of an item
	// Item example (2,88.62,€98) will be converted as the Item Object
	public static List<Item> splitLinePackages(String str) {
		return Stream.of(str.trim().split(" ")).map(elem -> {
			elem = elem.replace("(", "").replace(")", "");
			String[] values = elem.split(",");
			try {
				return new Item(Integer.parseInt(values[0]), Float.parseFloat(values[1]), Float.parseFloat(values[2].substring(1)));
			} catch (Throwable e) {
				logger.warn("Error Parsing Item = |" + str + "|, item will be disregarded");
				return null;
			}
		}).collect(Collectors.toList());
	}

}

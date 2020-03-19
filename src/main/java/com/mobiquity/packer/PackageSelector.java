package com.mobiquity.packer;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.mobiquity.constants.PropertyConstants;
import com.mobiquity.exception.APIIndexException;
import com.mobiquity.model.Item;
import com.mobiquity.model.PackageLine;

public class PackageSelector {

	static final Logger logger = Logger.getLogger(PackageSelector.class);

	private static float maxAllowedWeigth;

	public static String getPackage(PackageLine packageLine) throws APIIndexException {
		PackageSelector.maxAllowedWeigth = preparePackageMaxWeight(packageLine); // Constrain 1
		return getPriciestPossiblePackage(packageLine.getPackages());
	}

	/**	 
	 * @param list
	 * @return Item list e.g. x,x,xx
	 * @throws APIIndexException
	 * <br/>
	 * Logic:<br/>
	 * <ul>
	 * <li>
	 * First <em>for</em> loop always get the first item<br/>
	 * - If it is over max weight, it will go to next item of first loop<br/>
	 * - If it is within max weight limit, it will then proceed to Second loop
	 * </li>
	 * <li>
	 * Second <em>for</em> loop is the 2nd item that will increment for that permutation<br/>
	 * - If its weight + current item is over the max weight it will call Third loop to traverse to all items<br/>
	 * - If its weight + current item is within the max weight, this will be added then it will call Third loop to traverse to all items<br/>
	 * </li>
	 * <li>
	 * Third <em>for</em> loop traverses to remaining items after the item of Second loop
	 * </li>
	 * </ul>
	 */
	private static String getPriciestPossiblePackage(List<Item> list) throws APIIndexException {

		float currentPriciestPackage = 0;
		float currentSelectedWeightPackage = 0;
		String currentPriciestPackageObjectList = "";
		int size = list.size();
		long start = System.currentTimeMillis();
		for (int i = 0; i < size; i++) {
			boolean lastRowFlag1 = (i + 1) == size ? true : false;
			Item currentItem = list.get(i);
			boolean isAllowed = isItemAllowed(currentItem); // Constrain 3
			if (isAllowed && (currentItem.getWeight() < PackageSelector.maxAllowedWeigth)) {
				if (!lastRowFlag1) {
					for (int k = i + 1; k < size; k++) {
						float totalWeight = currentItem.getWeight();
						float totalPrice = currentItem.getPrice();
						String tempList = currentItem.getIndex() + PropertyConstants.OUTPUT_DELIMITER;
						for (int j = k; j < size; j++) {
							boolean lastRowFlag2 = (j + 1) == size ? true : false;
							Item nextItem = list.get(j);
							boolean isNextAllowed = isItemAllowed(nextItem); // Constrain 3
							if (isNextAllowed
									&& ((totalWeight + nextItem.getWeight()) < PackageSelector.maxAllowedWeigth)) {
								// A line should not have the same index!
								if (tempList.contains(nextItem.getIndex() + PropertyConstants.OUTPUT_DELIMITER)) {
									throw new APIIndexException("1 or more items have the same index!");
								}
								tempList += nextItem.getIndex() + PropertyConstants.OUTPUT_DELIMITER;
								totalPrice += nextItem.getPrice();
								totalWeight += nextItem.getWeight();
							}
							if (lastRowFlag2) {
								if (totalPrice > currentPriciestPackage) {
									currentPriciestPackage = totalPrice;
									currentPriciestPackageObjectList = tempList;
									currentSelectedWeightPackage = totalWeight;
								} else if (totalPrice == currentPriciestPackage
										&& totalWeight < currentSelectedWeightPackage) {
									currentPriciestPackage = totalPrice;
									currentPriciestPackageObjectList = tempList;
									currentSelectedWeightPackage = totalWeight;

								}
							}
						}
					}
				}

			}
		}
		long end = System.currentTimeMillis();
		logger.debug("Line took to run: " + (end - start));
		return prepareReturnPackageList(currentPriciestPackageObjectList);
	}

	// Formatting purposes: remove last comma or make return as dash.
	private static String prepareReturnPackageList(String currentPriciestPackageObjectList) {
		if (StringUtils.isEmpty(currentPriciestPackageObjectList)) {
			return "-";
		} else {
			return currentPriciestPackageObjectList.substring(0, (currentPriciestPackageObjectList.length() - 1));
		}
	}

	// Constrain 1: Max weight that a package can take is <= 100
	private static float preparePackageMaxWeight(PackageLine packageLine) {
		boolean isOver = packageLine.getMaxWeightAllowed() > PropertyConstants.PACKAGE_MAX_WEIGHT;
		if (isOver) {
			logger.warn(
					"Package line has over 100 weight: " + packageLine.getMaxWeightAllowed() + " | Will set to 100.0");
			return PropertyConstants.PACKAGE_MAX_WEIGHT;
		} else {
			return packageLine.getMaxWeightAllowed();
		}
	}

	// Constrain 3: Max weight and cost of an item is <= 100
	private static boolean isItemAllowed(Item item) {
		boolean isAllowed = false;
		if (item != null) {
			if (item.getPrice() <= PropertyConstants.ITEM_MAX_PRICE
					&& item.getWeight() <= PropertyConstants.ITEM_MAX_WEIGHT) {
				isAllowed = true;

			} else {
				logger.warn("Item has over 100 weight / price: " + item.toString());
			}
		}
		return isAllowed;
	}
}

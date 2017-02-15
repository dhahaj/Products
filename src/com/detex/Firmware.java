package com.detex;

import com.detex.Product.Family;

/**
 * Class to handle the firmware associated with the products.
 *
 * @author dmh
 *
 */
public class Firmware {

	private final Family device;

	public Firmware(Family family, String variant) {
		this.device = family;
		selectFirmware();
	}

	private void selectFirmware() {
		switch (device) {
		case EAX300:
		}
	}

}

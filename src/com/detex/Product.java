package com.detex;

import java.awt.GridLayout;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

/**
 * Class representing the products which will be used on the tester.
 *
 * @author dmh
 *
 */
class Product {

	final Family family;
	private V40Models model;
	private Firmware firmware = null;
	static Map<Product.Family, List<String>> EAX300Firmwares, EAX500Firmwares, EAX2500Firmwares, EAX3500Firmwares,
			V40Firmwares;

	static {
		final List<String> l;
		EAX300Firmwares = new HashMap<>();
		// EAX300Firmwares. = getProductFirmware(Family.EAX300);

	}

	/**
	 * Default Constructor
	 *
	 * @param family
	 *            The product family
	 * @param variant
	 *            The model variant to select the correct firmware
	 */
	public Product() {
		family = null;
		model = null;
	}

	public Product(Family family, String variant) {
		this.family = family;
		this.firmware = Firmware.valueOf(variant);
		// Check if it's a V40 so we can set the model
		if (this.family == Family.V40) {
			showModelSelectionDialog();
		} else {
			model = null;
		}
	}

	/**
	 * Shows a dialog to select the V40 model
	 */
	void showModelSelectionDialog() {
		try {
			SwingUtilities.invokeAndWait(new Runnable() {
				@SuppressWarnings("unchecked")
				@Override
				public void run() {
					final ArrayList<Object> models = new ArrayList<>();
					for (final V40Models m : V40Models.values())
						models.add(m);
					@SuppressWarnings("rawtypes")
					final JComboBox box = new JComboBox(models.toArray());
					final JPanel panel = new JPanel(new GridLayout(2, 2));
					panel.add(new JLabel("V40 Models"));
					panel.add(box);
					JOptionPane.showConfirmDialog(null, panel, "Select V40 Model: ", JOptionPane.OK_CANCEL_OPTION);
					setModel(V40Models.values()[box.getSelectedIndex()]);
				}
			});
		} catch (InvocationTargetException | InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Get the current V40 Model, or null if the current Product is not in the
	 * V40 family.
	 *
	 * @return the model
	 */
	public V40Models getModel() {
		if (this.family != Family.V40)
			return null;
		return model;
	}

	/**
	 * @param model
	 *            the model to set
	 */
	private void setModel(V40Models model) {
		this.model = model;
	}

	/**
	 * @return the firmware
	 */
	public Firmware getFirmware() {
		return firmware;
	}

	public void setFirmware(Firmware fw) {
		this.firmware = fw;
	}

	enum Family {
		EAX300, EAX500, EAX2500, V40, EAX3500;
	}

	/**
	 * Enumeration of the V40 models.
	 *
	 * @author dmh
	 *
	 */
	enum V40Models {
		EHxR(1), EH(2), EBxW(3), EB(4), EHxRxLBM(5), EHxLBM(6), EBxLBM(7), EA(8), EBxW_L9SI(9), EH_L9SI(10);

		/** String containing the part number and name for this model. */
		final String part;

		/** Boolean variable which describe the features for each model. */
		boolean hasRelay = true;
		boolean hasMolex = false;
		boolean hasAC = true;
		boolean hasDC = true;
		boolean hasJack = false;
		boolean hasRemote = true;
		boolean hasWires = false;
		boolean hasPad = true;

		/**
		 * Constructor for the V40Models Enum which set the various differences
		 * between them.
		 *
		 * @param i
		 *            The Model ID
		 */
		private V40Models(int i) {

			this.part = "104010-" + i + ' ' + this.name();

			switch (i) {
			case 1: // EHxR selected
			case 8: // EA selected
				hasMolex = true;
				break;
			case 2: // EH selected
				hasRelay = false;
				break;
			case 3: // EBxW selected
				hasWires = true;
				hasAC = false;
				break;
			case 7: // EBxLBM selected
				hasJack = true; // no break, same as EB model
			case 4: // EB selected
				hasRelay = false;
				hasAC = false;
				break;
			case 5: // EHxRxLBM selected
				hasMolex = true;
				hasJack = true;
				break;
			case 6: // EHxLBM selected
				hasRelay = false;
				hasJack = true;
				break;
			case 9: // EBxW L9SI selected
				hasWires = true;
				hasAC = false;
				break;
			case 10: // EH L9SI selected
				hasRelay = false;
				hasDC = false;
				break;
			}
		}

		/**
		 * Get a list of of the available models.
		 *
		 * @return List of strings containing all V40 models.
		 */
		public List<String> getAllModels() {
			final ArrayList<String> al = new ArrayList<>();
			for (final V40Models models : values())
				al.add(models.name());
			return al;
		}
	}

	/**
	 * Enumeration of all the firmware for all products.
	 *
	 * @author dmh
	 *
	 */
	enum Firmware {
		EAX300, // 1
		EAX500, EAX503, EAX504, EAX505, EAX510, EAX513, EAX514, EAX515, EAX520, EAX523, EAX524, EAX525, // 13
		EAX2500, EAX2503, EAX2504, EAX2505, EAX2510, EAX2513, EAX2514, EAX2520, EAX2523, EAX2524, EAX2545, // 22
		V40xx00, V40xx03, V40xx04, V40xx10, V40xx13, V40xx14, V40xx20, V40xx23, V40xx24, // 33
		V40sa00, // 34
		EAX3500; // 35

		// The family that each firmware belongs to.
		Family family;

		private Firmware() {
			if (this.name().contains("EAX300")) {
				this.family = Family.EAX300;
			} else if (this.name().contains("EAX5")) {
				this.family = Family.EAX500;
			} else if (this.name().contains("EAX25")) {
				this.family = Family.EAX2500;
			} else if (this.name().contains("V40")) {
				this.family = Family.V40;
			} else if (this.name().contains("EAX3500")) {
				this.family = Family.EAX3500;
			}
		}
	}

	/**
	 * Get a list of firmware associated with a product family.
	 *
	 * @return List of strings containing the names of the firmware for the .
	 */
	static List<String> getProductFirmware(Family family) {
		final ArrayList<String> al = new ArrayList<>();
		for (final Firmware firmware : Firmware.values()) {
			if (firmware.family == family)
				al.add(firmware.name());
		}
		return al;
	}

	static List<String> getAllFamilies() {
		final ArrayList<String> al = new ArrayList<>();
		for (final Family f : Family.values())
			al.add(f.name());
		return al;
	}

}

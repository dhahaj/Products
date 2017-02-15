package com.detex;

import static java.lang.System.out;

import java.util.Iterator;
import java.util.List;

import com.detex.Product.Family;

public class Test {

	public static void main(String[] args) {

		List<String> al = Product.getProductFirmware(Family.EAX3500);

		Iterator<String> i = al.iterator();
		while (i.hasNext()) {
			out.println(i.next());
		}

		out.println();

		al = Product.getAllFamilies();
		i = al.iterator();
		while (i.hasNext()) {
			final String family = i.next();
			out.println("Family=" + family);
			final List<String> l = Product.getProductFirmware(Family.valueOf(family));
			final Iterator<String> i2 = l.iterator();
			out.println(" Firmwares:");
			while (i2.hasNext()) {
				out.print('\t');
				out.println(i2.next());
			}
		}
		// Family model = Family.EAX500;
		// Product product = new Product(Family.V40, "V40xx00");
		// Product.V40Models model = product.getModel();
		//
		// out.println(model);
		// out.println(product.getFirmware());
		//
		// for (Family f : Family.values()) {
		// out.println(f.toString());
		// }
	}
}

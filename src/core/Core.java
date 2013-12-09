/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 *
 * @author Fenix
 */
public class Core {

	private static Map<String, Double> probability(String[] echantillon) {
		Map<String, Double> result = new HashMap<>();
		double size = 0;
		for (String currentChar : echantillon) {
			if (!currentChar.equals("\n")) {
				if (result.containsKey(currentChar)) {
					result.put(currentChar, result.get(currentChar) + 1);
				} else {
					result.put(currentChar, new Double(1));
				}
				size++;
			}
		}
		for (Entry e : result.entrySet()) {
			e.setValue((Double) (e.getValue()) / size);
		}
		return result;
	}

	private static void entropie(String contexte, String[] echantillon) {
		Map<String, Double> probas;
		double proba;
		double entropie = 0;

		probas = probability(echantillon);
		for (Entry e : probas.entrySet()) {
			proba = (Double) (e.getValue());
			entropie += proba * (Math.log10(proba) / Math.log10(2));
		}
		System.out.println("Entropie " + contexte + " : " + (-entropie));
	}

	public static Map<String, String> codage(String contexte, String[] echantillon) {
		Map<String, Double> probas;
		Map<String, String> codage;
		List<ProbElems> constructList;
		ProbElems collapsed;
		double moyenne;
		
		probas = probability(echantillon);

		constructList = new ArrayList();
		for (Entry e : probas.entrySet()) {
			ProbElems charact = new ProbElems((double) e.getValue());
			charact.add((String) (e.getKey()));
			constructList.add(charact);
		}

		codage = new HashMap<>();

		while (constructList.size() > 1) {
			Collections.sort(constructList, new Comparator<Object>() {
				@Override
				public int compare(Object t, Object t1) {
					return ((ProbElems) t).getProba().compareTo(((ProbElems) t1).getProba());
				}
			});

			collapsed = new ProbElems(0.0);
			for (int i = 0; i <= 1; i++) {
				for (String e : constructList.get(0)) {
					if (!codage.containsKey(e)) {
						codage.put((String) e, "");
					}
					codage.put((String) e, i + codage.get(e));
				}
				//Collapsing
				collapsed.setProba(collapsed.getProba() + constructList.get(0).getProba());
				collapsed.addAll(constructList.get(0));
				constructList.remove(0);
			}

			constructList.add(collapsed);
		}

		moyenne = 0;
		for (Entry e : codage.entrySet()) {
			System.out.println(((double) probas.get((String) e.getKey())) + "\t|'" + e.getKey() + "'\t: " + e.getValue());
			moyenne += ((String) e.getValue()).length() * ((double) probas.get((String) e.getKey()));
		}

		entropie(contexte, echantillon);

		System.out.println("Moyenne : " + moyenne);

		return codage;
	}

	private static class ProbElems extends ArrayList<String> {

		private Double proba;

		public ProbElems(Double proba) {
			this.proba = proba;
		}

		public Double getProba() {
			return proba;
		}

		public void setProba(Double proba) {
			this.proba = proba;
		}
	}
}

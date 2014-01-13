package booba.skaya.escooba.util;

import java.util.ArrayList;

public class Combinator {

	public static <T> ArrayList<ArrayList<T>> permutations(ArrayList<T> array){
		return generateCombination(array.size(), array, new ArrayList<T>(), 0);
	}
	
	public static <T> ArrayList<ArrayList<T>> combinations(ArrayList<T> array){
		ArrayList<ArrayList<T>> combinations = new ArrayList<ArrayList<T>>();
		for(int size = 1; size <= array.size();size++){
			combinations.addAll(generateCombination(size, array, new ArrayList<T>(), 0));
		}
		return combinations;
	}
	
	private static <T> ArrayList<ArrayList<T>> generateCombination(int maxLength, ArrayList<T> allValues, ArrayList<T> current, int currentIndex){
		ArrayList<ArrayList<T>> result = new ArrayList<ArrayList<T>>();
		current.add(allValues.get(currentIndex));
		if(current.size() == maxLength){
			result.add(current);
		}else{
			for(int i = 0; currentIndex+i+1 < allValues.size();i++){
				ArrayList<T> copy = new ArrayList<T>(current);
				result.addAll(generateCombination(maxLength, allValues, copy, currentIndex+i+1));
			}
		}
		return result;
	}

}

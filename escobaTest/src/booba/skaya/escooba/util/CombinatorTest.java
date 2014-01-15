/**
 * 
 */
package booba.skaya.escooba.util;

import java.util.ArrayList;

import org.junit.Test;

public class CombinatorTest {

	@Test
	public void testCombinate() {
		ArrayList<Integer> test = new ArrayList<Integer>();
		test.add(1);
		test.add(2);
		test.add(3);
		test.add(4);
		for(ArrayList<Integer> t: Combinator.combinations(test)){
			System.out.println(Strings.join(",  ", t));
		}
	}
	
	
}

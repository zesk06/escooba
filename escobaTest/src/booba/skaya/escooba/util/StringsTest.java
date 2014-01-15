package booba.skaya.escooba.util;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

public class StringsTest {

	@Test
	public void testJoin() {
		ArrayList<Integer> s = new ArrayList<Integer>();
		s.add(1);
		s.add(2);
		s.add(3);
		
		assertEquals("1,2,3", Strings.join(",",s));
	}

}

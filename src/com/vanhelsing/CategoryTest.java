package com.vanhelsing;

import org.junit.Test;

import com.vanhelsing.contentProvider.Category;
import static junit.framework.Assert.*;

public class CategoryTest {

	@Test
	public void printsToString() {
		final Category goodCategory = new Category(Classification.GOOD, 10);
		
		assertEquals("GOOD", goodCategory.toString());
	}
}

package com.vanhelsing;

import static org.junit.Assert.assertThat;

import static org.hamcrest.core.IsNot.not;
import static org.hamcrest.core.Is.*;

import org.junit.Test;

public class FeatureTest {

	@Test
	public void two_features_are_not_equal_if_the_words_are_not_equal() {
		
		assertThat(new Feature("money"), not(new Feature("quick")));
	}
	
	@Test
	public void two_features_are_equal_if_the_words_are_equal() {
		
		assertThat(new Feature("money"), is(new Feature("money")));
	}
}

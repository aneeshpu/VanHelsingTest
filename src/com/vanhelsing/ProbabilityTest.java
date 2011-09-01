package com.vanhelsing;

import static org.junit.Assert.assertThat;

import org.junit.Test;
import static org.hamcrest.core.Is.*;

public class ProbabilityTest {

	@Test
	public void multiplies_probabilities() {
		
		assertThat(new Probability(0.5f).multiply(new Probability(0.25f)), is(new Probability(0.125f)));
	}
	
	@Test
	public void two_probabilities_with_same_primitive_values_are_equal() {
		assertThat(new Probability(0.5f), is(new Probability(0.5f)));
	}
	
	@Test(expected=InvalidProbabilityException.class)
	public void a_value_less_than_zero_is_invalid() {
		new Probability(-0.24f);
	}
	
	@Test(expected=InvalidProbabilityException.class)
	public void a_value_greater_than_1_is_invalid() {
		new Probability(1.3f);
	}
	
	@Test
	public void makes_a_string_representation() {
		Probability probability = new Probability(0.123f);
		assertThat(probability.toString(), is("0.123"));
	}
	
	@Test
	public void determines_if_one_probability_is_greater_than_another() {
		assertThat(new Probability(0.5f).isGreaterThan(new Probability(0.25f), 1), is(true));
	}
}

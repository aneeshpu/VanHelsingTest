package com.vanhelsing;

import static org.junit.Assert.assertThat;

import static org.hamcrest.core.IsNot.not;
import static org.hamcrest.core.Is.*;

import org.hamcrest.core.IsEqual;
import org.junit.Before;
import org.junit.Test;

public class FeatureTest {

	private TrainingData trainer;

	@Before
	public void setup() {
		trainer = new TrainingData();
		trainer.train(new Document("make quick money at the online casino", new FeatureFactory(), trainer), Classification.BAD).train(new Document("buy pharmaceuticals now", new FeatureFactory(), trainer), Classification.BAD)
				.train(new Document("Nobody owns the water", new FeatureFactory(), trainer), Classification.GOOD).train(new Document("the quick rabbit jumps fences", new FeatureFactory(), trainer), Classification.GOOD)
				.train(new Document("the quick brown fox jumps", new FeatureFactory(), trainer), Classification.GOOD);

	}

	
	@Test
	public void two_features_are_not_equal_if_the_words_are_not_equal() {
		
		assertThat(new Feature("money", trainer), not(new Feature("quick", trainer)));
	}
	
	@Test
	public void two_features_are_equal_if_the_words_are_equal() {
		
		assertThat(new Feature("money", trainer), is(new Feature("money", trainer)));
	}
	
	@Test
	public void calculates_conditional_probability_of_a_given_feature() {

		assertThat(new Feature("money", trainer).conditionalProbability(Classification.BAD), IsEqual.<Float> equalTo(0.5f));
		assertThat(new Feature("pharmaceuticals", trainer).conditionalProbability(Classification.BAD), IsEqual.<Float> equalTo(0.5f));
		assertThat(new Feature("casino", trainer).conditionalProbability(Classification.BAD), IsEqual.<Float> equalTo(0.5f));
		assertThat(new Feature("quick", trainer).conditionalProbability(Classification.BAD), IsEqual.<Float> equalTo(0.5f));
		assertThat(new Feature("quick", trainer).conditionalProbability(Classification.GOOD), IsEqual.<Float> equalTo(2 / 3f));
		assertThat(new Feature("jumps", trainer).conditionalProbability(Classification.GOOD), IsEqual.<Float> equalTo(2 / 3f));
		assertThat(new Feature("water", trainer).conditionalProbability(Classification.GOOD), IsEqual.<Float> equalTo(1 / 3f));
		assertThat(new Feature("nobody", trainer).conditionalProbability(Classification.GOOD), IsEqual.<Float> equalTo(1 / 3f));
		assertThat(new Feature("fox", trainer).conditionalProbability(Classification.GOOD), IsEqual.<Float> equalTo(1 / 3f));
		assertThat(new Feature("nonexistentword", trainer).conditionalProbability(Classification.GOOD), IsEqual.<Float> equalTo(0f));
		assertThat(new Feature("nonexistentword", trainer).conditionalProbability(Classification.BAD), IsEqual.<Float> equalTo(0f));
	}

}

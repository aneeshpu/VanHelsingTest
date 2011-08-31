package com.vanhelsing;

import static org.junit.Assert.assertThat;

import static org.hamcrest.core.Is.is;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNot.not;

import org.junit.Before;
import org.junit.Test;

public class WordTest {

	private TrainingData trainer;

	@Before
	public void setup() {
		trainer = new TrainingData();
		trainer.train(new Document("make quick money at the online casino", new FeatureFactory(), trainer), Classification.BAD)
				.train(new Document("buy pharmaceuticals now", new FeatureFactory(), trainer), Classification.BAD)
				.train(new Document("Nobody owns the water", new FeatureFactory(), trainer), Classification.GOOD)
				.train(new Document("the quick rabbit jumps fences", new FeatureFactory(), trainer), Classification.GOOD)
				.train(new Document("the quick brown fox jumps", new FeatureFactory(), trainer), Classification.GOOD)
				.train(new Document("buy cheap stuff for easy money", new FeatureFactory(), trainer), Classification.BAD);

	}

	@Test
	public void two_features_are_not_equal_if_the_words_are_not_equal() {

		assertThat(new Word("money", trainer), not(new Word("quick", trainer)));
	}

	@Test
	public void two_features_are_equal_if_the_words_are_equal() {

		assertThat(new Word("money", trainer), is(new Word("money", trainer)));
	}

	@Test
	public void calculates_conditional_probability_of_a_given_feature() {

		assertThat(new Word("money", trainer).conditionalProbability(Classification.BAD).round(), equalTo(new Probability(0.611f)));
		assertThat(new Word("pharmaceuticals", trainer).conditionalProbability(Classification.BAD).round(), equalTo(new Probability(0.417f)));
		assertThat(new Word("casino", trainer).conditionalProbability(Classification.BAD).round(), equalTo(new Probability(0.417f)));
		assertThat(new Word("quick", trainer).conditionalProbability(Classification.BAD).round(), equalTo(new Probability(0.417f)));
		assertThat(new Word("quick", trainer).conditionalProbability(Classification.GOOD).round(), equalTo(new Probability(0.611f)));
		assertThat(new Word("jumps", trainer).conditionalProbability(Classification.GOOD).round(), equalTo(new Probability(0.611f)));
		assertThat(new Word("water", trainer).conditionalProbability(Classification.GOOD).round(), equalTo(new Probability(0.417f)));
		assertThat(new Word("nobody", trainer).conditionalProbability(Classification.GOOD).round(), equalTo(new Probability(0.417f)));
		assertThat(new Word("fox", trainer).conditionalProbability(Classification.GOOD).round(), equalTo(new Probability(0.417f)));
		assertThat(new Word("nonexistentword", trainer).conditionalProbability(Classification.GOOD).round(), equalTo(new Probability(0.5f)));
		assertThat(new Word("nonexistentword", trainer).conditionalProbability(Classification.BAD).round(), equalTo(new Probability(0.5f)));
	}

	@Test
	public void gives_the_number_of_times_the_feature_occurred_in_the_training_document() {
		assertThat(new Word("money", trainer).numberOfOccurrencesInTheTrainingSet(Classification.BAD), equalTo(2f));
	}
	
	@Test
	public void gives_a_string_representation() {
		assertThat(new Word("money", trainer).toString(), is("money"));
	}

}

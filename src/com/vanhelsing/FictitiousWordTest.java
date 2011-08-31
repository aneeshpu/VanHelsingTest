package com.vanhelsing;

import static org.junit.Assert.assertThat;

import org.hamcrest.core.Is;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.core.Is.*;

public class FictitiousWordTest {

	private TrainingData trainingData;
	
	@Before
	public void setup() {
		trainingData = new TrainingData();
		trainingData.train(new Document("make quick money at the online casino", new FeatureFactory(), trainingData), Classification.BAD)
				.train(new Document("buy pharmaceuticals for cheap money now", new FeatureFactory(), trainingData), Classification.BAD)
				.train(new Document("Nobody owns the water", new FeatureFactory(), trainingData), Classification.GOOD)
				.train(new Document("the quick rabbit jumps fences", new FeatureFactory(), trainingData), Classification.GOOD)
				.train(new Document("the quick brown fox jumps", new FeatureFactory(), trainingData), Classification.GOOD)
				.train(new Document("Big discount at the superstore", new FeatureFactory(), trainingData), Classification.BAD);
	}


	@Test
	public void calculates_weighted_probability_of_a_feature() {
		FictitiousWord fictitiousWord = new FictitiousWord(new Word("money",trainingData));
		
		assertThat(fictitiousWord.conditionalProbability(Classification.BAD).round(), is(new Probability(0.611f).round()));
	}
}

package com.vanhelsing;

import static junit.framework.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class NaiveBayesianClassifierTest {

	private TrainingData trainer;

	@Before
	public void setup() {
		trainer = new TrainingData();
		trainer.train(new Document("make quick money at the online casino", new FeatureFactory(), trainer), Classification.BAD)
				.train(new Document("buy pharmaceuticals now", new FeatureFactory(), trainer), Classification.BAD)
				.train(new Document("Nobody owns the water", new FeatureFactory(), trainer), Classification.GOOD)
				.train(new Document("the quick rabbit jumps fences", new FeatureFactory(), trainer), Classification.GOOD)
				.train(new Document("the quick brown fox jumps", new FeatureFactory(), trainer), Classification.GOOD);
	}

	@Test
	public void classifies_a_document_as_bad() {

		Classification classification = new NaiveBayesianClassifier().classify(new Document("make quick money at the casino", new FeatureFactory(), trainer));

		assertEquals(Classification.BAD, classification);
	}

}
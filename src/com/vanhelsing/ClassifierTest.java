package com.vanhelsing;

import static junit.framework.Assert.assertEquals;

import org.junit.Test;


public class ClassifierTest {

	@Test
	public void classifies_a_document_as_bad() {

		Classification classification = new NaiveBayesianClassifier().classify(new Document("make quick money at the casino", new FeatureFactory()));

		assertEquals(Classification.BAD, classification);
	}

}

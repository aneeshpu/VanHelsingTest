package com.vanhelsing;

import static org.junit.Assert.assertThat;

import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import static org.hamcrest.core.Is.is;

public class FeatureFactoryTest {
	
	private TrainingData trainer;

	@Before
	public void setup() {
		trainer = new TrainingData();
		trainer.train(new Document("make quick money at the online casino", new FeatureFactory(), trainer), Classification.BAD).train(new Document("buy pharmaceuticals now", new FeatureFactory(), trainer), Classification.BAD)
				.train(new Document("Nobody owns the water", new FeatureFactory(), trainer), Classification.GOOD).train(new Document("the quick rabbit jumps fences", new FeatureFactory(), trainer), Classification.GOOD)
				.train(new Document("the quick brown fox jumps", new FeatureFactory(), trainer), Classification.GOOD);		
	}

	@Test
	public void creates_a_feature_from_a_string() {
		
		Set<String> features = new HashSet<String>();
		features.add("money");
		features.add("quick");
		Set<Feature> feature = new FeatureFactory().makeFeatures(features, trainer);
		
		assertThat(feature.size(), is(2));
		assertThat(feature.contains(new Word("money", trainer)), is(true));
		assertThat(feature.contains(new Word("quick", trainer)), is(true));
		
	}
}

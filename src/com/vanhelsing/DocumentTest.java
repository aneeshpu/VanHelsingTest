package com.vanhelsing;

import static org.junit.Assert.assertThat;

import static org.hamcrest.core.Is.is;

import java.util.Set;

import org.hamcrest.core.IsEqual;
import org.junit.Before;
import org.junit.Test;

public class DocumentTest {

	private Document document;
	private TrainingData trainer;

	@Before
	public void setup() {
		document = new Document("Make quick money At the casino", new FeatureFactory(), trainer);
		trainer = new TrainingData();
		trainer.train(new Document("make quick money at the online casino", new FeatureFactory(), trainer), Classification.BAD).train(new Document("buy pharmaceuticals now", new FeatureFactory(), trainer), Classification.BAD)
				.train(new Document("Nobody owns the water", new FeatureFactory(), trainer), Classification.GOOD).train(new Document("the quick rabbit jumps fences", new FeatureFactory(), trainer), Classification.GOOD)
				.train(new Document("the quick brown fox jumps", new FeatureFactory(), trainer), Classification.GOOD);		
	}

	@Test
	public void gives_individual_features_of_the_document() {

		Set<Feature> features = document.uniqueFeatures();

		assertThat(features.size(), is(6));

		for (Feature feature : new Feature[] { new Feature("make", trainer), new Feature("quick", trainer), new Feature("money", trainer), new Feature("at", trainer), new Feature("the", trainer), new Feature("casino", trainer) }) {
			assertThat(features.contains(feature), is(true));
		}
	}

	@Test
	public void all_features_get_converted_to_lower_case() {
		Set<Feature> features = document.uniqueFeatures();

		assertThat(features.contains("Make"), is(false));
		assertThat(features.contains("At"), is(false));
	}

	@Test
	public void two_docs_are_equal_if_their_contents_are_equal() {
		String contents = "quick brown fox jumps fences";
		assertThat(new Document(contents, new FeatureFactory(), trainer), is(new Document(contents, new FeatureFactory(), trainer)));
	}
	
	@Test
	public void determines_the_conditional_probability_of_a_given_document() {

		Document document = new Document("make quick money at the online casino", new FeatureFactory(), trainer);

		assertThat(document.conditionalProbability(Classification.BAD), IsEqual.<Float> equalTo(0.0078f));
	}
	
}

package com.vanhelsing;

import static org.junit.Assert.assertThat;

import static org.hamcrest.core.Is.is;

import java.util.Set;

import org.junit.Before;
import org.junit.Test;

public class DocumentTest {

	private Document document;

	@Before
	public void setup() {
		document = new Document("Make quick money At the casino", new FeatureFactory());
	}

	@Test
	public void gives_individual_features_of_the_document() {

		Set<Feature> features = document.uniqueFeatures();

		assertThat(features.size(), is(6));

		for (Feature feature : new Feature[] { new Feature("make"), new Feature("quick"), new Feature("money"), new Feature("at"), new Feature("the"), new Feature("casino") }) {
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
		assertThat(new Document(contents, new FeatureFactory()), is(new Document(contents, new FeatureFactory())));
	}
}

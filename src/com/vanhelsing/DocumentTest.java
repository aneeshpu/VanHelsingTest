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
		document = new Document("Make quick money At the casino");
	}

	@Test
	public void gives_individual_features_of_the_document() {

		Set<String> features = document.uniqueFeatures();

		assertThat(features.size(), is(6));

		for (String feature : new String[] { "make", "quick", "money", "at", "the", "casino" }) {
			assertThat(features.contains(feature), is(true));
		}
	}

	@Test
	public void all_features_get_converted_to_lower_case() {
		Set<String> features = document.uniqueFeatures();

		assertThat(features.contains("Make"), is(false));
		assertThat(features.contains("At"), is(false));
	}
}

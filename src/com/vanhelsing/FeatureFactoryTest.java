package com.vanhelsing;

import static org.junit.Assert.assertThat;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;
import static org.hamcrest.core.Is.is;

public class FeatureFactoryTest {

	@Test
	public void creates_a_feature_from_a_string() {
		
		Set<String> features = new HashSet<String>();
		features.add("money");
		features.add("quick");
		Set<Feature> feature = new FeatureFactory().makeFeatures(features );
		
		assertThat(feature.size(), is(2));
		assertThat(feature.contains(new Feature("money")), is(true));
		assertThat(feature.contains(new Feature("quick")), is(true));
		
	}
}

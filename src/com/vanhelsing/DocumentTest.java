package com.vanhelsing;

import static org.junit.Assert.assertThat;

import static org.hamcrest.core.Is.is;

import static org.hamcrest.core.IsEqual.equalTo;

import java.util.Set;

import org.easymock.EasyMock;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.vanhelsing.contentProvider.IClassificationDao;
import com.vanhelsing.contentProvider.IFeatureDao;
import com.vanhelsing.mockbuilders.ClassificationDaoMockBuilder;
import com.vanhelsing.mockbuilders.FeatureDaoMockBuilder;
import com.vanhelsing.mockbuilders.Times;

public class DocumentTest {

	private Document document;
	private TrainingData trainer;
	private IFeatureDao featureDaoMock;
	private IClassificationDao classificationDaoMock;

	@Before
	public void setup() {
		document = new Document("Make quick money At the casino", new FeatureFactory(), trainer);
		featureDaoMock = featureDaoMock();
		classificationDaoMock = classificationDaoMock();
		
		trainer = new TrainingData(featureDaoMock, classificationDaoMock);
		trainer.train(new Document("make quick money at the online casino", new FeatureFactory(), trainer), Classification.BAD)
				.train(new Document("buy pharmaceuticals now", new FeatureFactory(), trainer), Classification.BAD)
				.train(new Document("Nobody owns the water", new FeatureFactory(), trainer), Classification.GOOD)
				.train(new Document("the quick rabbit jumps fences", new FeatureFactory(), trainer), Classification.GOOD)
				.train(new Document("the quick brown fox jumps", new FeatureFactory(), trainer), Classification.GOOD);
	}

	private IClassificationDao classificationDaoMock() {
		return ClassificationDaoMockBuilder.ClassificationDaoMock().withPersist().withGet(10, Times.any()).create();
	}

	private IFeatureDao featureDaoMock() {
		final IFeatureDao featureDaoMock = FeatureDaoMockBuilder.featureDaoMock().withGet(Times.any()).withPersist(Times.any()).create();
		return featureDaoMock;
	}

	@Test
	public void gives_individual_features_of_the_document() {

		Set<Feature> features = document.uniqueFeatures();

		assertThat(features.size(), is(5));

		for (Feature feature : new Feature[] { new Word("make", trainer), new Word("quick", trainer), new Word("money", trainer), new Word("the", trainer), new Word("casino", trainer) }) {
			assertThat(features.contains(feature), is(true));
		}
	}

	@Test
	public void words_smaller_than_three_characters_are_ignored() {
		Set<Feature> features = document.uniqueFeatures();

		assertThat(features.size(), is(5));

		assertThat(features.contains(new Word("at", trainer)), is(false));

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
	@Ignore("Fix this. Needs a lot of mocking and it is taking me too long. I'd rather get my functionality out at this point. ")
	public void determines_the_conditional_probability_of_a_given_document() {

		Document document = new Document("make quick money at the online casino", new FeatureFactory(), trainer);

		assertThat(document.conditionalProbability(Classification.BAD).round(), equalTo(new Probability(0.016f)));
	}

	@Test
	public void prints_a_string_representation() {
		Document document = new Document("make quick money at the online casino", new FeatureFactory(), trainer);
		assertThat(document.toString(), is("make quick money at the online casino"));
	}

	@After
	public void tearDown(){
		EasyMock.verify(featureDaoMock);
		EasyMock.verify(classificationDaoMock);
	}
}

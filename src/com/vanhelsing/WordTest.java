package com.vanhelsing;

import static org.junit.Assert.assertThat;

import static org.hamcrest.core.Is.is;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNot.not;

import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;

import com.vanhelsing.contentProvider.IClassificationDao;
import com.vanhelsing.contentProvider.IFeatureDao;
import com.vanhelsing.mockbuilders.ClassificationDaoMockBuilder;
import com.vanhelsing.mockbuilders.FeatureDaoMockBuilder;
import com.vanhelsing.mockbuilders.Times;

public class WordTest {

	private TrainingData trainer;

	@Before
	public void setup() {
		trainer = new TrainingData(featureDaoMock(), classificationDaoMock());
		trainer.train(new Document("make quick money at the online casino", new FeatureFactory(), trainer), Classification.BAD)
				.train(new Document("buy pharmaceuticals now", new FeatureFactory(), trainer), Classification.BAD)
				.train(new Document("buy cheap stuff for easy money", new FeatureFactory(), trainer), Classification.BAD)
				
				.train(new Document("Nobody owns the water", new FeatureFactory(), trainer), Classification.GOOD)
				.train(new Document("the quick rabbit jumps fences", new FeatureFactory(), trainer), Classification.GOOD)
				.train(new Document("the quick brown fox jumps", new FeatureFactory(), trainer), Classification.GOOD);

	}
	
	private IClassificationDao classificationDaoMock() {
		return ClassificationDaoMockBuilder.ClassificationDaoMock().withPersist().withGet(3,Times.any()).create();
	}

	private IFeatureDao featureDaoMock() {
		return FeatureDaoMockBuilder.featureDaoMock().withGet(Times.any()).withPersist(Times.any()).create();
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

		final TrainingData trainer = mockTrainingData();
		
		assertThat(new Word("money", trainer).conditionalProbability(Classification.BAD).round(), equalTo(new Probability(0.611f)));
/*		assertThat(new Word("pharmaceuticals", trainer).conditionalProbability(Classification.BAD).round(), equalTo(new Probability(0.417f)));
		assertThat(new Word("casino", trainer).conditionalProbability(Classification.BAD).round(), equalTo(new Probability(0.417f)));
		assertThat(new Word("quick", trainer).conditionalProbability(Classification.BAD).round(), equalTo(new Probability(0.375f)));
		
		assertThat(new Word("quick", trainer).conditionalProbability(Classification.GOOD).round(), equalTo(new Probability(0.625f)));
		assertThat(new Word("jumps", trainer).conditionalProbability(Classification.GOOD).round(), equalTo(new Probability(0.611f)));
		assertThat(new Word("water", trainer).conditionalProbability(Classification.GOOD).round(), equalTo(new Probability(0.417f)));
		assertThat(new Word("nobody", trainer).conditionalProbability(Classification.GOOD).round(), equalTo(new Probability(0.417f)));
		assertThat(new Word("fox", trainer).conditionalProbability(Classification.GOOD).round(), equalTo(new Probability(0.417f)));
		assertThat(new Word("nonexistentword", trainer).conditionalProbability(Classification.GOOD).round(), equalTo(new Probability(0.5f)));
		assertThat(new Word("nonexistentword", trainer).conditionalProbability(Classification.BAD).round(), equalTo(new Probability(0.5f)));
*/	}

	private TrainingData mockTrainingData() {
		final TrainingData trainingDataMock = EasyMock.createMock(TrainingData.class);
		EasyMock.expect(trainingDataMock.numberOfDocumentsTheFeatureOccurredIn(EasyMock.anyObject(Feature.class), EasyMock.anyObject(Classification.class))).andReturn(2f).anyTimes();
		EasyMock.expect(trainingDataMock.numberOfDocumentsTheFeatureOccurredIn(EasyMock.anyObject(Word.class))).andReturn(2).anyTimes();
		
		EasyMock.expect(trainingDataMock.numberOfDocumentsInTheCategory(EasyMock.anyObject(Classification.class))).andReturn(3f).anyTimes();
		
		EasyMock.replay(trainingDataMock);
		return trainingDataMock;
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

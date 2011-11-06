package com.vanhelsing;

import static junit.framework.Assert.assertEquals;

import org.easymock.EasyMock;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.vanhelsing.contentProvider.FeatureDao;
import com.vanhelsing.contentProvider.IClassificationDao;
import com.vanhelsing.contentProvider.IFeatureDao;
import com.vanhelsing.mockbuilders.ClassificationDaoMockBuilder;
import com.vanhelsing.mockbuilders.FeatureDaoMockBuilder;
import com.vanhelsing.mockbuilders.Times;

public class NaiveBayesianClassifierTest {

	private TrainingData trainer;
	private IFeatureDao featureDaoMock;
	private IClassificationDao classificationDaoMock;

	@Before
	public void setup() {
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
	public void classifies_a_document_as_spam() {
		Classification classification = new NaiveBayesianClassifier(trainer).classify(new Document("make quick money at the online casino", new FeatureFactory(), trainer));
		assertEquals(Classification.BAD, classification);
	}

	
	@Test
	@Ignore
	public void foo() {

		Document document = new Document("make quick money at the online casino", new FeatureFactory(), trainer);
		System.out.println(document.conditionalProbability(Classification.BAD));
		System.out.println(document.conditionalProbability(Classification.GOOD));
	}
	
	@After
	public void tearDown(){
		EasyMock.verify(featureDaoMock);
		EasyMock.verify(classificationDaoMock);
	}
}
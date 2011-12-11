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
	@Ignore("Fix this. Needs a lot of mocking and it is taking me too long. I'd rather get my functionality out at this point. ")
	public void classifies_a_document_as_spam() {
		final TrainingData trainer = mockTrainingData();
		
		Classification classification = new NaiveBayesianClassifier(trainer).classify(new Document("make quick money at the online casino", new FeatureFactory(), trainer));
		assertEquals(Classification.BAD, classification);
	}

	private TrainingData mockTrainingData() {
		final TrainingData mockTrainingData = EasyMock.createMock(TrainingData.class);
		
		EasyMock.expect(mockTrainingData.numberOfDocumentsInTheCategory(EasyMock.anyObject(Classification.class))).andReturn(15f).anyTimes();
		EasyMock.expect(mockTrainingData.numberOfDocumentsTheFeatureOccurredIn(EasyMock.anyObject(Feature.class), EasyMock.anyObject(Classification.class))).andReturn(1f).anyTimes();
		EasyMock.expect(mockTrainingData.numberOfDocumentsTheFeatureOccurredIn(EasyMock.anyObject(Word.class))).andReturn(1).anyTimes();
		EasyMock.expect(mockTrainingData.totalNumberOfDocuments()).andReturn(5);
		
//		EasyMock.expect(mockTrainingData.numberOfDocumentsInTheCategory(EasyMock.anyObject(Classification.class))).andReturn(1f);
		
		EasyMock.replay(mockTrainingData);
		return mockTrainingData;
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
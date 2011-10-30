package com.vanhelsing;

import org.easymock.EasyMock;
import org.junit.Before;

import com.vanhelsing.contentProvider.FeatureDao;
import com.vanhelsing.contentProvider.IClassificationDao;

public class TrainerTest {

	private TrainingData trainer;

	@Before
	public void setup() {
		trainer = new TrainingData(featureDaoMock(), classificationDaoMock());
		trainer.train(new Document("make quick money at the online casino", new FeatureFactory(), trainer), Classification.BAD).train(new Document("buy pharmaceuticals now", new FeatureFactory(), trainer), Classification.BAD)
				.train(new Document("Nobody owns the water", new FeatureFactory(), trainer), Classification.GOOD).train(new Document("the quick rabbit jumps fences", new FeatureFactory(), trainer), Classification.GOOD)
				.train(new Document("the quick brown fox jumps", new FeatureFactory(), trainer), Classification.GOOD);

	}
	
	private IClassificationDao classificationDaoMock() {
		final IClassificationDao classificationDao = EasyMock.createMock(IClassificationDao.class);
		return classificationDao;
	}

	private FeatureDao featureDaoMock() {
		final FeatureDao featureDaoMock = EasyMock.createMock(FeatureDao.class);
		return featureDaoMock;
	}

/*	@Test
	public void calculates_conditional_probability_of_a_given_feature() {

		assertThat(trainer.conditionalProbability(new Feature("money", trainer), Classification.BAD), IsEqual.<Float> equalTo(0.5f));
		assertThat(trainer.conditionalProbability(new Feature("pharmaceuticals", trainer), Classification.BAD), IsEqual.<Float> equalTo(0.5f));
		assertThat(trainer.conditionalProbability(new Feature("casino", trainer), Classification.BAD), IsEqual.<Float> equalTo(0.5f));
		assertThat(trainer.conditionalProbability(new Feature("quick", trainer), Classification.BAD), IsEqual.<Float> equalTo(0.5f));
		assertThat(trainer.conditionalProbability(new Feature("quick", trainer), Classification.GOOD), IsEqual.<Float> equalTo(2 / 3f));
		assertThat(trainer.conditionalProbability(new Feature("jumps", trainer), Classification.GOOD), IsEqual.<Float> equalTo(2 / 3f));
		assertThat(trainer.conditionalProbability(new Feature("water", trainer), Classification.GOOD), IsEqual.<Float> equalTo(1 / 3f));
		assertThat(trainer.conditionalProbability(new Feature("nobody", trainer), Classification.GOOD), IsEqual.<Float> equalTo(1 / 3f));
		assertThat(trainer.conditionalProbability(new Feature("fox", trainer), Classification.GOOD), IsEqual.<Float> equalTo(1 / 3f));
		assertThat(trainer.conditionalProbability(new Feature("nonexistentword", trainer), Classification.GOOD), IsEqual.<Float> equalTo(0f));
		assertThat(trainer.conditionalProbability(new Feature("nonexistentword", trainer), Classification.BAD), IsEqual.<Float> equalTo(0f));
	}
*/
/*	@Test
	public void determines_the_conditional_probability_of_a_given_document() {

		Document document = new Document("make quick money at the online casino", new FeatureFactory(), trainer);

		assertThat(trainer.conditionalProbability(document, Classification.BAD), IsEqual.<Float> equalTo(0.0078f));
	}
*/}

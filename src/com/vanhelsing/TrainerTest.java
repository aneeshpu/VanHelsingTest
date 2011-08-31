package com.vanhelsing;

import static org.junit.Assert.assertThat;

import org.hamcrest.core.IsEqual;
import org.junit.Before;
import org.junit.Test;

public class TrainerTest {

	private Trainer trainer;

	@Before
	public void setup() {
		trainer = new Trainer();
		trainer.train(new Document("make quick money at the online casino", new FeatureFactory()), Classification.BAD).train(new Document("buy pharmaceuticals now", new FeatureFactory()), Classification.BAD)
				.train(new Document("Nobody owns the water", new FeatureFactory()), Classification.GOOD).train(new Document("the quick rabbit jumps fences", new FeatureFactory()), Classification.GOOD)
				.train(new Document("the quick brown fox jumps", new FeatureFactory()), Classification.GOOD);

	}

	@Test
	public void calculates_conditional_probability_of_a_given_feature() {

		assertThat(trainer.conditionalProbability(new Feature("money"), Classification.BAD), IsEqual.<Float> equalTo(0.5f));
		assertThat(trainer.conditionalProbability(new Feature("pharmaceuticals"), Classification.BAD), IsEqual.<Float> equalTo(0.5f));
		assertThat(trainer.conditionalProbability(new Feature("casino"), Classification.BAD), IsEqual.<Float> equalTo(0.5f));
		assertThat(trainer.conditionalProbability(new Feature("quick"), Classification.BAD), IsEqual.<Float> equalTo(0.5f));
		assertThat(trainer.conditionalProbability(new Feature("quick"), Classification.GOOD), IsEqual.<Float> equalTo(2 / 3f));
		assertThat(trainer.conditionalProbability(new Feature("jumps"), Classification.GOOD), IsEqual.<Float> equalTo(2 / 3f));
		assertThat(trainer.conditionalProbability(new Feature("water"), Classification.GOOD), IsEqual.<Float> equalTo(1 / 3f));
		assertThat(trainer.conditionalProbability(new Feature("nobody"), Classification.GOOD), IsEqual.<Float> equalTo(1 / 3f));
		assertThat(trainer.conditionalProbability(new Feature("fox"), Classification.GOOD), IsEqual.<Float> equalTo(1 / 3f));
		assertThat(trainer.conditionalProbability(new Feature("nonexistentword"), Classification.GOOD), IsEqual.<Float> equalTo(0f));
		assertThat(trainer.conditionalProbability(new Feature("nonexistentword"), Classification.BAD), IsEqual.<Float> equalTo(0f));
	}

	@Test
	public void determines_the_conditional_probability_of_a_given_document() {

		Document document = new Document("make quick money at the online casino", new FeatureFactory());

		assertThat(trainer.conditionalProbability(document, Classification.BAD), IsEqual.<Float> equalTo(0.0078f));
	}
}

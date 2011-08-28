package com.vanhelsing;

import static org.junit.Assert.assertThat;

import java.util.HashMap;

import org.hamcrest.core.Is;
import org.hamcrest.core.IsEqual;
import org.junit.Before;
import org.junit.Test;

public class TrainerTest {

	private Trainer trainer;

	@Before
	public void setup() {
		trainer = new Trainer();
		trainer.train(new Document("make quick money at the online casino"), Classification.BAD).train(new Document("buy pharmaceuticals now"), Classification.BAD)
				.train(new Document("Nobody owns the water"), Classification.GOOD).train(new Document("the quick rabbit jumps fences"), Classification.GOOD)
				.train(new Document("the quick brown fox jumps"), Classification.GOOD);

	}

	@Test
	public void calculates_conditional_probability_of_a_given_feature() {

		assertThat(trainer.conditionalProbability("money", Classification.BAD), IsEqual.<Float>equalTo(0.5f));
		assertThat(trainer.conditionalProbability("pharmaceuticals", Classification.BAD), IsEqual.<Float>equalTo(0.5f));
		assertThat(trainer.conditionalProbability("casino", Classification.BAD), IsEqual.<Float>equalTo(0.5f));
		assertThat(trainer.conditionalProbability("quick", Classification.BAD), IsEqual.<Float>equalTo(0.5f));
		assertThat(trainer.conditionalProbability("quick", Classification.GOOD), IsEqual.<Float>equalTo(2/3f));
		assertThat(trainer.conditionalProbability("jumps", Classification.GOOD), IsEqual.<Float>equalTo(2/3f));
		assertThat(trainer.conditionalProbability("water", Classification.GOOD), IsEqual.<Float>equalTo(1/3f));
		assertThat(trainer.conditionalProbability("nobody", Classification.GOOD), IsEqual.<Float>equalTo(1/3f));
		assertThat(trainer.conditionalProbability("fox", Classification.GOOD), IsEqual.<Float>equalTo(1/3f));
	}
}

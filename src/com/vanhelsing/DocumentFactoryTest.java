package com.vanhelsing;

import static org.junit.Assert.assertThat;

import static org.hamcrest.core.Is.is;

import org.junit.Before;
import org.junit.Test;

public class DocumentFactoryTest {
	
	private TrainingData trainer;

	@Before
	public void setup() {
		trainer = new TrainingData();
		trainer.train(new Document("make quick money at the online casino", new FeatureFactory(), trainer), Classification.BAD).train(new Document("buy pharmaceuticals now", new FeatureFactory(), trainer), Classification.BAD)
				.train(new Document("Nobody owns the water", new FeatureFactory(), trainer), Classification.GOOD).train(new Document("the quick rabbit jumps fences", new FeatureFactory(), trainer), Classification.GOOD)
				.train(new Document("the quick brown fox jumps", new FeatureFactory(), trainer), Classification.GOOD);		
	}


	@Test
	public void creates_a_document_from_a_sequence_of_words() {
		Document makeDocument = new DocumentFactory().makeDocument("quick brown fox jumps fences", trainer);
		assertThat(makeDocument == null, is(false));
	}
	
	@Test
	public void a_document_is_created_with_the_correct_string() {
		String string = "quick brown fox jumps fences";
		Document makeDocument = new DocumentFactory().makeDocument(string, trainer);
		assertThat(makeDocument, is(new Document(string, new FeatureFactory(), trainer)));
	}
}

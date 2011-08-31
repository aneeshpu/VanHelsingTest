package com.vanhelsing;

import static org.junit.Assert.assertThat;

import static org.hamcrest.core.Is.is;

import org.junit.Test;

public class DocumentFactoryTest {

	@Test
	public void creates_a_document_from_a_sequence_of_words() {
		Document makeDocument = new DocumentFactory().makeDocument("quick brown fox jumps fences");
		assertThat(makeDocument == null, is(false));
	}
	
	@Test
	public void a_document_is_created_with_the_correct_string() {
		String string = "quick brown fox jumps fences";
		Document makeDocument = new DocumentFactory().makeDocument(string);
		assertThat(makeDocument, is(new Document(string, new FeatureFactory())));
	}
}

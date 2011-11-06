package com.vanhelsing.mockbuilders;

import org.easymock.EasyMock;
import org.easymock.IExpectationSetters;

import com.vanhelsing.Classification;
import com.vanhelsing.contentProvider.Category;
import com.vanhelsing.contentProvider.IClassificationDao;

public class ClassificationDaoMockBuilder {

	private static final int ANY_TIMES = 0;
	private IClassificationDao classificationDaoMock;
	public static final int ANY = 0;

	private ClassificationDaoMockBuilder() {
		classificationDaoMock = EasyMock.createMock(IClassificationDao.class);
	}

	public static ClassificationDaoMockBuilder ClassificationDaoMock() {
		return new ClassificationDaoMockBuilder();
	}

	public ClassificationDaoMockBuilder withPersist(int times) {
		final Category anyObject = EasyMock.anyObject(Category.class);
		times(times, EasyMock.expect(classificationDaoMock.persist(anyObject)).andReturn(anyObject));
		return this;
	}

	private void times(int times, final IExpectationSetters<?> expectationSetters) {
		if (times == ANY)
			expectationSetters.anyTimes();
		else
			expectationSetters.times(times);
	}

	public ClassificationDaoMockBuilder withPersist() {
		return withPersist(ANY_TIMES);
	}

	public ClassificationDaoMockBuilder withGet(int documentCount, Times times) {
		final Classification anyObject = EasyMock.anyObject(Classification.class);
		final IExpectationSetters<Category> expectationSetters = EasyMock.expect(classificationDaoMock.get(anyObject)).andReturn(new Category(anyObject, documentCount));
		times.setUpCallNumber(expectationSetters);
		return this;
	}

	public IClassificationDao create() {
		EasyMock.replay(classificationDaoMock);
		return classificationDaoMock;
	}

}

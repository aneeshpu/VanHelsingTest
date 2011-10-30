package com.vanhelsing;

import org.easymock.EasyMock;
import org.easymock.IExpectationSetters;

import com.vanhelsing.contentProvider.IClassificationDao;

public class ClassificationDaoMockBuilder {

	private static final int ANY_TIMES = 0;
	private IClassificationDao classificationDaoMock;

	private ClassificationDaoMockBuilder() {
		classificationDaoMock = EasyMock.createMock(IClassificationDao.class);
	}

	public static ClassificationDaoMockBuilder ClassificationDaoMock() {
		return new ClassificationDaoMockBuilder();
	}

	public ClassificationDaoMockBuilder withPersist(int times) {
		final IExpectationSetters<Boolean> expectationSetters = EasyMock.expect(classificationDaoMock.persist(EasyMock.anyObject(Classification.class), EasyMock.anyInt())).andReturn(true);
		if (times == 0)
			expectationSetters.anyTimes();
		else
			expectationSetters.times(times);
		
		return this;
	}

	public ClassificationDaoMockBuilder withPersist() {
		return withPersist(ANY_TIMES);
	}

	public IClassificationDao create() {
		EasyMock.replay(classificationDaoMock);
		return classificationDaoMock;
	}
}

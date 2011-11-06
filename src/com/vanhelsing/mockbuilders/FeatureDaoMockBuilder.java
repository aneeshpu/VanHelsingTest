package com.vanhelsing.mockbuilders;

import org.easymock.EasyMock;
import org.easymock.IExpectationSetters;

import com.vanhelsing.Feature;
import com.vanhelsing.contentProvider.IFeatureDao;

public class FeatureDaoMockBuilder {

	private final IFeatureDao featureDaoMock;
	
	private FeatureDaoMockBuilder(){
		featureDaoMock = EasyMock.createMock(IFeatureDao.class);
	}

	public static FeatureDaoMockBuilder featureDaoMock() {
		return new FeatureDaoMockBuilder();
	}

	public IFeatureDao create() {
		EasyMock.replay(featureDaoMock);
		return featureDaoMock;
	}

	public FeatureDaoMockBuilder withGet(Times times) {
		final Feature anyObject = EasyMock.anyObject(Feature.class);
		final IExpectationSetters<Feature> expectationSetters = EasyMock.expect(featureDaoMock.get(anyObject)).andReturn(anyObject);
		times.setUpCallNumber(expectationSetters);
		return this;
	}

	public FeatureDaoMockBuilder withPersist(Times times) {
		final Feature anyObject = EasyMock.anyObject(Feature.class);
		final IExpectationSetters<Boolean> andReturn = EasyMock.expect(featureDaoMock.persist(anyObject)).andReturn(true);
		times.setUpCallNumber(andReturn);
		return this;
	}

}
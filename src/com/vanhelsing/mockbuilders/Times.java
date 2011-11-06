package com.vanhelsing.mockbuilders;

import org.easymock.IExpectationSetters;

import com.vanhelsing.Feature;

public abstract class Times {

	public abstract void setUpCallNumber(IExpectationSetters<?> expectationSetters);

	public static Times times(final int times) {
		return new Times() {

			@Override
			public void setUpCallNumber(IExpectationSetters<?> expectationSetters) {
				expectationSetters.times(times);
			}

		};
	}

	public static Times any() {
		return new Times() {

			@Override
			public void setUpCallNumber(IExpectationSetters<?> expectationSetters) {
				expectationSetters.anyTimes();
			}
		};
	}

}

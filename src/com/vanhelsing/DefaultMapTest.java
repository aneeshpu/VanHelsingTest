package com.vanhelsing;

import static org.junit.Assert.assertThat;

import static org.hamcrest.core.Is.is;

import java.util.HashMap;
import java.util.Map;

import org.hamcrest.core.Is;
import org.hamcrest.core.IsInstanceOf;
import org.junit.Test;

public class DefaultMapTest {

	@Test
	public void returns_default_value() {
		
		DefaultFunction<Map<Classification, Integer>> defaultFunction = new DefaultFunction<Map<Classification, Integer>>() {

			@Override
			public Map<Classification, Integer> initialize() {
				return new HashMap<Classification, Integer>();
			}
		};
		
		DefaultMap<String, Map<Classification,Integer>> defaultMap = new DefaultMap<String, Map<Classification,Integer>>(new HashMap<String, Map<Classification,Integer>>(), defaultFunction);
		Map<Classification, Integer> defaultValue = defaultMap.get("nonexistentKey");
		assertThat(defaultValue, IsInstanceOf.instanceOf(Map.class));
		assertThat(defaultValue.isEmpty(), is(true));
	}
	
	@Test
	public void default_value_is_initialized_each_time() {
		DefaultFunction<Map<Classification, Integer>> defaultFunction = new DefaultFunction<Map<Classification, Integer>>() {

			@Override
			public Map<Classification, Integer> initialize() {
				return new HashMap<Classification, Integer>();
			}
		};
		
		DefaultMap<String, Map<Classification,Integer>> defaultMap = new DefaultMap<String, Map<Classification,Integer>>(new HashMap<String, Map<Classification,Integer>>(), defaultFunction);
		Map<Classification, Integer> defaultValue = defaultMap.get("nonexistentKey");
		defaultValue.put(Classification.BAD, 1);
		
		Map<Classification, Integer> someOtherDefaultValue = defaultMap.get("someOtherNonexistentKey");
		assertThat(someOtherDefaultValue.isEmpty(), is(true));
		
		
	}
	
	@Test
	public void returns_values_for_existing_keys() {
		DefaultFunction<Map<Classification, Integer>> defaultFunction = new DefaultFunction<Map<Classification, Integer>>() {

			@Override
			public Map<Classification, Integer> initialize() {
				return new HashMap<Classification, Integer>();
			}
		};
		
		DefaultMap<String, Map<Classification,Integer>> defaultMap = new DefaultMap<String, Map<Classification,Integer>>(new HashMap<String, Map<Classification,Integer>>(), defaultFunction);
		
		HashMap<Classification, Integer> value = new HashMap<Classification, Integer>();
		value.put(Classification.GOOD, 1);
		defaultMap.put("key", value);
		
		Map<Classification, Integer> retrievedValue = defaultMap.get("key");
		
		assertThat(retrievedValue.isEmpty(), is(false));
		assertThat(retrievedValue.get(Classification.GOOD), is(1));
		
	}
	
	@Test
	public void values_added_to_returned_default_value_is_stored() {
		
		DefaultFunction<Map<Classification, Integer>> defaultFunction = new DefaultFunction<Map<Classification, Integer>>() {

			@Override
			public Map<Classification, Integer> initialize() {
				return new HashMap<Classification, Integer>();
			}
		};
		
		DefaultMap<String, Map<Classification,Integer>> defaultMap = new DefaultMap<String, Map<Classification,Integer>>(new HashMap<String, Map<Classification,Integer>>(), defaultFunction);
		Map<Classification, Integer> defaultValue = defaultMap.get("nonexistentKey");

		assertThat(defaultValue.isEmpty(), is(true));
		defaultValue.put(Classification.BAD, 1);
		
		Map<Classification, Integer> modifiedDefaultValue = defaultMap.get("nonexistentKey");
		assertThat(modifiedDefaultValue.isEmpty(), is(false));
		assertThat(modifiedDefaultValue.get(Classification.BAD), is(1));
		
	}
}

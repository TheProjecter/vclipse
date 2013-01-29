package org.vclipse.configscan.impl.model;

import java.util.List;

import com.google.common.collect.Lists;

public class TestGroup extends TestCase {

	private List<TestCase> testCases;
	
	public TestGroup(TestCase parent) {
		super(parent);
		testCases = Lists.newArrayList();
	}
	
	public List<TestCase> getTestCases() {
		return testCases;
	}
	
	public void addTestCase(TestCase testCase) {
		if(testCase != null) {
			testCases.add(testCase);
		}
	}
	
	public void removeTestCase(TestCase testCase) {
		if(testCase != null) {
			testCases.remove(testCase);
		}
	}
	
	public void removeTestCases() {
		testCases.clear();
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((testCases == null) ? 0 : testCases.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		TestGroup other = (TestGroup) obj;
		if (testCases == null) {
			if (other.testCases != null)
				return false;
		} else if (!testCases.equals(other.testCases))
			return false;
		return true;
	}
}

package org.torpay.util.spring;

public class RemoteBeanFactoryTestBeanImpl1 implements
		RemoteBeanFactoryTestBean {

	@Override
	public boolean test() {
		return true;
	}

}

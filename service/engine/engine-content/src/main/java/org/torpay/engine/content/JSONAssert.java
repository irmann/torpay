package org.torpay.engine.content;

import java.util.List;

import org.torpay.common.util.Parameter;

public class JSONAssert {

	public static void doAssert(List<Parameter> values,
			EngineContent serviceContent) {

		assert 7 == values.size();

		Parameter name1 = serviceContent.findParameter(values, "name1");
		assert "value1".equals(name1.getValue());

		List<Parameter> name1_2 = serviceContent
				.findParameters(values, "name1");
		assert "value1".equals(serviceContent.findParameter(name1_2, 1)
				.getValue());
		List<Parameter> name4 = serviceContent.findParameters(values, "name4");
		assert 3 == name4.size();

		List<Parameter> name5 = serviceContent.findParameters(values,
				"name4.name5");
		assert 2 == name5.size();
		assert "value5-2".equals(serviceContent.findParameter(name5, 2)
				.getValue());
		List<Parameter> name7 = serviceContent.findParameters(values,
				"name4.name7");
		assert 1 == name7.size();
		assert "value7".equals(serviceContent.findParameter(name7, 1)
				.getValue());
		assert "value7".equals(serviceContent.findParameter(values,
				"name4.name7").getValue());

		List<Parameter> name8 = serviceContent.findParameters(values, "name8");
		assert 2 == name8.size();
		List<Parameter> name9 = serviceContent.findParameters(values,
				"name8.name9");
		assert "value9".equals(serviceContent.findParameter(name9, 1)
				.getValue());
		List<Parameter> name10 = serviceContent.findParameters(values,
				"name8.name10");
		assert 2 == name10.size();
		List<Parameter> name11 = serviceContent.findParameters(values,
				"name8.name10.name11");
		assert 1 == name11.size();
		assert "value11".equals(serviceContent.findParameter(name11, 1)
				.getValue());
		List<Parameter> name12 = serviceContent.findParameters(values,
				"name8.name10.name12");
		assert 1 == name12.size();
		assert "value12".equals(serviceContent.findParameter(name12, 1)
				.getValue());
		List<Parameter> name13 = serviceContent
				.findParameters(values, "name13");
		assert 2 == name13.size();
		List<Parameter> name30 = serviceContent.findParameters(values,
				"name13.name30");
		assert 1 == name30.size();
		assert "value14".equals(serviceContent.findParameter(name30, 1)
				.getValue());
		List<Parameter> name31 = serviceContent.findParameters(values,
				"name13.name31");
		assert 1 == name31.size();
		List<Parameter> name15 = serviceContent.findParameters(values,
				"name13.name31.name15");
		assert 2 == name15.size();
		List<Parameter> name16 = serviceContent.findParameters(values,
				"name13.name31.name15.name16");
		assert 1 == name16.size();
		assert "value16".equals(serviceContent.findParameter(name16, 1)
				.getValue());
		List<Parameter> name18 = serviceContent
				.findParameters(values, "name18");
		assert 1 == name18.size();
		List<Parameter> name19 = serviceContent.findParameters(values,
				"name18.name19");
		assert 1 == name18.size();
		List<Parameter> name32 = serviceContent.findParameters(values,
				"name18.name19.name32");
		assert 1 == name32.size();
		List<Parameter> name20 = serviceContent.findParameters(values,
				"name18.name19.name32.name20");
		assert "value20".equals(serviceContent.findParameter(name20, 1)
				.getValue());
		List<Parameter> name33 = serviceContent.findParameters(values,
				"name18.name19.name33");
		assert 1 == name33.size();
		List<Parameter> name22 = serviceContent.findParameters(values,
				"name18.name19.name33.name22");
		assert 2 == name22.size();
		List<Parameter> name23 = serviceContent.findParameters(values,
				"name18.name19.name33.name22.name23");
		assert "value23".equals(serviceContent.findParameter(name23, 1)
				.getValue());
		List<Parameter> name24 = serviceContent.findParameters(values,
				"name18.name19.name33.name22.name24");
		assert "value24".equals(serviceContent.findParameter(name24, 1)
				.getValue());

	}

	public static void doAssert2(List<Parameter> values,
			EngineContent serviceContent) {
		assert 7 == values.size();
		Parameter name0 = serviceContent.findParameter(values, "name0");
		assert null == name0;
		List<Parameter> name5 = serviceContent.findParameters(values,
				"name4.name5");
		assert 0 == name5.size();
		Parameter name7 = serviceContent.findParameter(values, "name4.name7");
		assert null == name7;
		Parameter name9 = serviceContent.findParameter(values, "name8.name9");
		assert null == name9;
		Parameter name11 = serviceContent.findParameter(values,
				"name8.name10.name11");
		assert null == name11;
		Parameter name12 = serviceContent.findParameter(values,
				"name8.name10.name12");
		assert null == name12;
		Parameter name30 = serviceContent
				.findParameter(values, "name13.name30");
		assert null == name30;
		Parameter name16 = serviceContent.findParameter(values,
				"name13.name31.name15.name16");
		assert null == name16;
		Parameter name20 = serviceContent.findParameter(values,
				"name18.name19.name32.name20");
		assert null == name20;
		Parameter name23 = serviceContent.findParameter(values,
				"name18.name19.name33.name22.name23");
		assert null == name23;
		Parameter name24 = serviceContent.findParameter(values,
				"name18.name19.name33.name22.name24");
		assert null == name24;
	}

}

package org.torpay.common.test;

import java.util.List;

import org.junit.Assert;
import org.torpay.common.util.Parameter;
import org.torpay.common.util.ParameterUtil;

public class JSONAssert {

	public static void doAssert(List<Parameter> values) {
		Assert.assertEquals(7, values.size());
		Parameter name1 = ParameterUtil.findParameter(values, "name1");
		Assert.assertEquals("value1", name1.getValue());

		List<Parameter> name1_2 = ParameterUtil.findParameters(values, "name1");
		Assert.assertEquals("value1", ParameterUtil.findParameter(name1_2, 1)
				.getValue());
		List<Parameter> name4 = ParameterUtil.findParameters(values, "name4");
		Assert.assertEquals(3, name4.size());

		List<Parameter> name5 = ParameterUtil.findParameters(values,
				"name4.name5");
		Assert.assertEquals(2, name5.size());
		Assert.assertEquals("value5-2", ParameterUtil.findParameter(name5, 2)
				.getValue());
		List<Parameter> name7 = ParameterUtil.findParameters(values,
				"name4.name7");
		Assert.assertEquals(1, name7.size());
		Assert.assertEquals("value7", ParameterUtil.findParameter(name7, 1)
				.getValue());
		Assert.assertEquals("value7",
				ParameterUtil.findParameter(values, "name4.name7").getValue());

		List<Parameter> name8 = ParameterUtil.findParameters(values, "name8");
		Assert.assertEquals(2, name8.size());
		List<Parameter> name9 = ParameterUtil.findParameters(values,
				"name8.name9");
		Assert.assertEquals("value9", ParameterUtil.findParameter(name9, 1)
				.getValue());
		List<Parameter> name10 = ParameterUtil.findParameters(values,
				"name8.name10");
		Assert.assertEquals(2, name10.size());
		List<Parameter> name11 = ParameterUtil.findParameters(values,
				"name8.name10.name11");
		Assert.assertEquals(1, name11.size());
		Assert.assertEquals("value11", ParameterUtil.findParameter(name11, 1)
				.getValue());
		List<Parameter> name12 = ParameterUtil.findParameters(values,
				"name8.name10.name12");
		Assert.assertEquals(1, name12.size());
		Assert.assertEquals("value12", ParameterUtil.findParameter(name12, 1)
				.getValue());
		List<Parameter> name13 = ParameterUtil.findParameters(values, "name13");
		Assert.assertEquals(2, name13.size());
		List<Parameter> name30 = ParameterUtil.findParameters(values,
				"name13.name30");
		Assert.assertEquals(1, name30.size());
		Assert.assertEquals("value14", ParameterUtil.findParameter(name30, 1)
				.getValue());
		List<Parameter> name31 = ParameterUtil.findParameters(values,
				"name13.name31");
		Assert.assertEquals(1, name31.size());
		List<Parameter> name15 = ParameterUtil.findParameters(values,
				"name13.name31.name15");
		Assert.assertEquals(2, name15.size());
		List<Parameter> name16 = ParameterUtil.findParameters(values,
				"name13.name31.name15.name16");
		Assert.assertEquals(1, name16.size());
		Assert.assertEquals("value16", ParameterUtil.findParameter(name16, 1)
				.getValue());
		List<Parameter> name18 = ParameterUtil.findParameters(values, "name18");
		Assert.assertEquals(1, name18.size());
		List<Parameter> name19 = ParameterUtil.findParameters(values,
				"name18.name19");
		Assert.assertEquals(1, name18.size());
		List<Parameter> name32 = ParameterUtil.findParameters(values,
				"name18.name19.name32");
		Assert.assertEquals(1, name32.size());
		List<Parameter> name20 = ParameterUtil.findParameters(values,
				"name18.name19.name32.name20");
		Assert.assertEquals("value20", ParameterUtil.findParameter(name20, 1)
				.getValue());
		List<Parameter> name33 = ParameterUtil.findParameters(values,
				"name18.name19.name33");
		Assert.assertEquals(1, name33.size());
		List<Parameter> name22 = ParameterUtil.findParameters(values,
				"name18.name19.name33.name22");
		Assert.assertEquals(2, name22.size());
		List<Parameter> name23 = ParameterUtil.findParameters(values,
				"name18.name19.name33.name22.name23");
		Assert.assertEquals("value23", ParameterUtil.findParameter(name23, 1)
				.getValue());
		List<Parameter> name24 = ParameterUtil.findParameters(values,
				"name18.name19.name33.name22.name24");
		Assert.assertEquals("value24", ParameterUtil.findParameter(name24, 1)
				.getValue());

	}

	public static void doAssert2(List<Parameter> values) {
		Assert.assertEquals(7, values.size());
		Parameter name0 = ParameterUtil.findParameter(values, "name0");
		Assert.assertNull(name0);
		List<Parameter> name5 = ParameterUtil.findParameters(values,
				"name4.name5");
		Assert.assertEquals(0, name5.size());
		Parameter name7 = ParameterUtil.findParameter(values, "name4.name7");
		Assert.assertNull(name7);
		Parameter name9 = ParameterUtil.findParameter(values, "name8.name9");
		Assert.assertNull(name9);
		Parameter name11 = ParameterUtil.findParameter(values,
				"name8.name10.name11");
		Assert.assertNull(name11);
		Parameter name12 = ParameterUtil.findParameter(values,
				"name8.name10.name12");
		Assert.assertNull(name12);
		Parameter name30 = ParameterUtil.findParameter(values, "name13.name30");
		Assert.assertNull(name30);
		Parameter name16 = ParameterUtil.findParameter(values,
				"name13.name31.name15.name16");
		Assert.assertNull(name16);
		Parameter name20 = ParameterUtil.findParameter(values,
				"name18.name19.name32.name20");
		Assert.assertNull(name20);
		Parameter name23 = ParameterUtil.findParameter(values,
				"name18.name19.name33.name22.name23");
		Assert.assertNull(name23);
		Parameter name24 = ParameterUtil.findParameter(values,
				"name18.name19.name33.name22.name24");
		Assert.assertNull(name24);
	}

}

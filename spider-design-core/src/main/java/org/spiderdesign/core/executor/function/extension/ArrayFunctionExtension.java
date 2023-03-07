package org.spiderdesign.core.executor.function.extension;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.spiderdesign.annotation.Comment;
import org.spiderdesign.annotation.Example;
import org.spiderdesign.executor.FunctionExtension;
import org.springframework.stereotype.Component;

@Component
public class ArrayFunctionExtension implements FunctionExtension{

	@Override
	public Class<?> support() {
		return Object[].class;
	}

	@Comment("获取数组的长度")
	@Example("${arrayVar.size()}")
	public static int size(Object[] objs){
		return objs.length;
	}

	@Comment("将数组拼接起来")
	@Example("${arrayVar.join()}")
	public static String join(Object[] objs,String separator){
		return StringUtils.join(objs,separator);
	}

	@Comment("将数组用separator拼接起来")
	@Example("${arrayVar.join('-')}")
	public static String join(Object[] objs){
		return StringUtils.join(objs);
	}

	@Comment("将数组转为List")
	@Example("${arrayVar.toList()}")
	public static List<?> toList(Object[] objs){
		return Arrays.asList(objs);
	}

}

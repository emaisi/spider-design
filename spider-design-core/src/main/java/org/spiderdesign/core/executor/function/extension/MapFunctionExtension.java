package org.spiderdesign.core.executor.function.extension;

import org.spiderdesign.annotation.Comment;
import org.spiderdesign.annotation.Example;
import org.spiderdesign.executor.FunctionExtension;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class MapFunctionExtension implements FunctionExtension {

	@Override
	public Class<?> support() {
		return Map.class;
	}

	@Comment("将map转换为List")
	@Example("${mapmVar.toList('=')}")
	public static List<String> toList(Map<?,?> map,String separator){
		return map.entrySet().stream().map(entry-> entry.getKey() + separator + entry.getValue()).collect(Collectors.toList());
	}
}

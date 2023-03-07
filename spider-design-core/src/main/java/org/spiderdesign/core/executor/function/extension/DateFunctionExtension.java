package org.spiderdesign.core.executor.function.extension;

import java.util.Date;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.spiderdesign.annotation.Comment;
import org.spiderdesign.annotation.Example;
import org.spiderdesign.executor.FunctionExtension;
import org.springframework.stereotype.Component;

@Component
public class DateFunctionExtension implements FunctionExtension{

	@Override
	public Class<?> support() {
		return Date.class;
	}

	@Comment("格式化日期")
	@Example("${dateVar.format()}")
	public static String format(Date date){
		return format(date, "yyyy-MM-dd HH:mm:ss");
	}

	@Comment("格式化日期")
	@Example("${dateVar.format('yyyy-MM-dd HH:mm:ss')}")
	public static String format(Date date,String pattern){
		return DateFormatUtils.format(date,pattern);
	}
}

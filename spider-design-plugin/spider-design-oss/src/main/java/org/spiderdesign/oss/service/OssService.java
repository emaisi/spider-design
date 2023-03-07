package org.spiderdesign.oss.service;

import org.apache.commons.lang3.StringUtils;
import org.spiderdesign.oss.mapper.OssMapper;
import org.spiderdesign.oss.model.Oss;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

@Service
public class OssService extends ServiceImpl<OssMapper, Oss> {

	public Oss getOss(String ossId) {
		Oss oss = null;
		if(StringUtils.isNotBlank(ossId)) {
			oss = getBaseMapper().selectById(ossId);
		}else {
			oss = getBaseMapper().selectOne(new QueryWrapper<Oss>().orderByDesc("create_date"));
		}
		return oss;
	}

}

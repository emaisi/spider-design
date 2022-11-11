package org.spiderflow.proxypool.service;

import java.util.List;

import org.spiderflow.proxypool.mapper.ProxyMapper;
import org.spiderflow.proxypool.model.Proxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;

@Service
public class ProxyService {
	
	@Autowired
	private ProxyMapper proxyMapper;

	public boolean insert(Proxy proxy){
		try {
			proxyMapper.insert(proxy);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	public void remove(Proxy proxy){
		proxyMapper.deleteById(proxy.getId());
	}
	
	public List<Proxy> findAll(){
		return proxyMapper.selectList(Wrappers.emptyWrapper());
	}
}

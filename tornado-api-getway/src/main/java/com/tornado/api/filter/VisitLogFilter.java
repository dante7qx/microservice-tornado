package com.tornado.api.filter;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.tornado.api.service.IPRuleService;
import com.tornado.api.util.IPUtils;
import com.tornado.api.vo.IPRuleVO;
import com.tornado.commom.util.DateUtils;

@Component
public class VisitLogFilter extends ZuulFilter {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(VisitLogFilter.class);
	
	@Autowired
	private IPRuleService ipRuleService;
	@Value("${ip.filter}")
	private Boolean ipFilter;

	@Override
	public boolean shouldFilter() {
		return ipFilter;
	}

	@Override
	public Object run() {
		RequestContext ctx = RequestContext.getCurrentContext();
		HttpServletRequest request = ctx.getRequest();
		String ip = IPUtils.getIpAddr(request);
		if("127.0.0.1".equals(ip) || "0:0:0:0:0:0:0:1".equals(ip)) {
			return null;
		}
		final String requestUri = request.getRequestURI();
        final String method = request.getMethod();
        final String header = request.getHeader("Authorization");
        String date = DateUtils.getCurrentDatetime();
        LOGGER.debug("Request Header {}", header);
        LOGGER.info("IP [{}] at [{}] request [{}] method [{}]", ip, date, requestUri, method);
        if(!validClientIP(ip)) {
        	HttpServletResponse response = ctx.getResponse();
			try {
				response.setStatus(HttpServletResponse.SC_FORBIDDEN);
				response.setContentType("application/json;charset=utf-8");
				response.setCharacterEncoding("UTF-8");
				response.getOutputStream().write("Illegal client.".getBytes());
				response.flushBuffer();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
        }
        
		return null;
	}

	@Override
	public String filterType() {
		return "pre";
	}

	@Override
	public int filterOrder() {
		return 0;
	}
	
	/**
	 * 校验客户端的合法性
	 * 
	 * @param ip
	 * @return
	 */
	private boolean validClientIP(String ip) {
		if("127.0.0.1".equals(ip) || "0:0:0:0:0:0:0:1".equals(ip)) {
			return true;
		}
		List<IPRuleVO> ipRules = ipRuleService.findActiveIPRules();
		if(CollectionUtils.isEmpty(ipRules)) {
			return false;
		}
		for (IPRuleVO ipRuleVO : ipRules) {
			if(ip.equalsIgnoreCase(ipRuleVO.getIp())) {
				return true;
			}
		}
		return false;
	}

}

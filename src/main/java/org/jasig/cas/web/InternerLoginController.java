package org.jasig.cas.web;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jasig.cas.util.Client;
import org.jasig.cas.util.Constants;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

/**
 * 类描述:用于无法经过页面登陆的场景进行的登录操作
 *
 * @Author:WWG
 * @date:2018年6月10日
 * @Version:1.0
 */
public class InternerLoginController extends AbstractController {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.web.servlet.mvc.AbstractController#handleRequestInternal(
	 * javax.servlet.http.HttpServletRequest,
	 * javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// MappingJackson2JsonView view = new MappingJackson2JsonView();
		// Map<String,Object> attributes = new HashMap<String,Object>();
		// attributes.put("status", Boolean.TRUE);
		// attributes.put("reason", "操作成功");
		// view.setAttributesMap(attributes);
		// mav.setView(view); return mav;
		String service = request.getParameter("service");
		String type = request.getParameter("type");
		//貌似还会返回一个code用于抓取荣邦用户数据
		
		
		// 内登录操作
		final String server = "http://localhost:8080/cas/v1/tickets";
		final String username = "adminoue";
		final String password = "123456";
		// final String service = "http://localhost:8888/index";
		final String proxyValidate = "http://localhost:8080/cas/proxyValidate";
		String ticket = Client.getTicketGrantingTicket(server, username, password);
		//相应ticket到cookie中。
		Cookie tcookie = new Cookie(Constants.COOKIE_CAS_TGC,ticket);
		response.addCookie(tcookie);
		//对服务进行ST认证
		Client.intenerLogin(proxyValidate, server, ticket, service);
		ModelAndView mv = new ModelAndView("redirect:/login？service=" + service + "&type=" + type);
		Map<String, Object> attributes = new HashMap<String, Object>();
		attributes.put("status", Boolean.TRUE);
		attributes.put("reason", "操作成功");
		mv.addAllObjects(attributes);
		return mv;
	}

}

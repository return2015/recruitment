package com.returnsoft.recruitment.controller;

import java.io.IOException;
import java.util.Date;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.returnsoft.recruitment.util.SessionBean;

public class FilterSecurity implements Filter {

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res,
			FilterChain chain) throws IOException, ServletException {

		// HttpServletRequest request = (HttpServletRequest) req;
		// HttpServletResponse response = (HttpServletResponse) res;
		// HttpServletResponse resp = (HttpServletResponse) response;

		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
		// //////////////
		response.setHeader("Expires", "Tue, 03 Jul 2001 06:00:00 GMT");
		response.setDateHeader("Last-Modified", new Date().getTime());
		response.setHeader("Cache-Control",
				"no-store, no-cache, must-revalidate, max-age=0, post-check=0, pre-check=0");
		response.setHeader("Pragma", "no-cache");

		// /////////////////

		// String uri = request.getRequestURI();

		HttpSession session = request.getSession(false);

		String uri = request.getRequestURI();

		String url = request.getRequestURL().toString();

		//System.out.println("ACA::: " + uri);
		// System.out.println("ACA::: "+url);
		String path = url.substring(0, (url.length() - uri.length()));

		// InetAddress address = InetAddress.getLocalHost();
		// String ipServer = address.getHostAddress();

		/*
		 * if (uri.equals("/security/faces/login.xhtml")) { chain.doFilter(req,
		 * res); }else{ if (session!=null) { SessionBean sessionBean =
		 * (SessionBean)session.getAttribute("sessionBean"); if (null ==
		 * sessionBean) { response.sendRedirect(
		 * "http://192.168.23.15:8080/security/faces/login.xhtml"); }else{
		 * chain.doFilter(req, res); } }else{ response.sendRedirect(
		 * "http://192.168.23.15:8080/security/faces/login.xhtml"); }
		 * 
		 * }
		 */
		
		//System.out.println("uri"+uri);

		if (uri.equals("/resource/faces/add_person.xhtml")
				|| uri.equals("/resource/faces/edit_person.xhtml")
				|| uri.equals("/resource/faces/search_person.xhtml")
				|| uri.equals("/resource/faces/search_user.xhtml")
				|| uri.equals("/resource/faces/edit_user.xhtml")
				|| uri.equals("/resource/faces/add_user.xhtml")
				|| uri.equals("/resource/faces/resource.xhtml")) {
			//System.out.println("ingrEso");

			if (session != null) {
				//System.out.println("sesion != null");
				SessionBean sessionBean = (SessionBean) session
						.getAttribute("sessionBean");
				if (sessionBean != null) {
					/*if (sessionBean.getUserId() != null) {
						//System.out.println("sesionBean.useId != null");
						chain.doFilter(req, res);
					} else {
						//System.out.println("sesionBean.useId == null");
						response.sendRedirect(path
								+ "/resource/faces/login.xhtml");
					}*/

				} else {
					//System.out.println("sesionBean == null");
					response.sendRedirect(path + "/resource/faces/login.xhtml");
				}
			} else {
				//System.out.println("sesion == null");
				response.sendRedirect(path + "/resource/faces/login.xhtml");
			}

		} else {
			//System.out.println("no ingreso");
			chain.doFilter(req, res);
		}

	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub

	}

}

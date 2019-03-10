package jp.imanaga.web.server.filter;

import java.io.IOException;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ContentsFilter implements Filter {

	private static String CONTENTS_URL_PATTERN;
	private static final String TOP_PAGE = "/";

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		CONTENTS_URL_PATTERN = Arrays.asList(filterConfig.getInitParameter("contents-url-pattern").split(",")).stream()
				.map(uri -> String.format("^%s$", uri.trim().replace("/*", "(|/.*)"))).collect(Collectors.joining("|"));
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		if (request instanceof HttpServletRequest && response instanceof HttpServletResponse) {

			HttpServletRequest httpServletRequest = (HttpServletRequest) request;
			HttpServletResponse httpServletResponse = (HttpServletResponse) response;

			// コンテンツ配信が可能なURLかどうかをチェック
			String uriPath = httpServletRequest.getRequestURI().substring(httpServletRequest.getContextPath().length());
			Matcher matcher = Pattern.compile(CONTENTS_URL_PATTERN).matcher(uriPath);
			if (matcher.find()) {
				chain.doFilter(httpServletRequest, httpServletResponse);
			} else {
				RequestDispatcher dispatch = request.getRequestDispatcher(TOP_PAGE);
				httpServletResponse.setHeader("X-UA-Compatible", "IE=edge");
				dispatch.forward(httpServletRequest, httpServletResponse);
			}
		} else {
			chain.doFilter(request, response);
		}

	}

	@Override
	public void destroy() {
		Filter.super.destroy();
	}

}

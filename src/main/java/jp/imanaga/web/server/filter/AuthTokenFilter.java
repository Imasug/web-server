package jp.imanaga.web.server.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

import jp.imanaga.web.server.data.AuthTokenObject;
import jp.imanaga.web.server.request.CustomizeHttpHeadersRequesetWrapper;
import jp.imanaga.web.server.util.AuthTokenUtil;

public class AuthTokenFilter implements Filter {

	private static final String AUTH_TOKEN_HEADER_NAME = "authToken";

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		Filter.super.init(filterConfig);
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		if (request instanceof HttpServletRequest && response instanceof HttpServletResponse) {

			HttpServletRequest httpServletRequest = (HttpServletRequest) request;
			HttpServletResponse httpServletResponse = (HttpServletResponse) response;
			CustomizeHttpHeadersRequesetWrapper requestWrapper = new CustomizeHttpHeadersRequesetWrapper(
					httpServletRequest);
			HttpServletResponseWrapper responseWrapper = new HttpServletResponseWrapper(httpServletResponse);

			// 認証トークン検証
			String authToken = httpServletRequest.getHeader(AUTH_TOKEN_HEADER_NAME);
			AuthTokenObject authTokenObject = AuthTokenUtil.parse(authToken);
			if (!AuthTokenUtil.isValid(authTokenObject)) {
				// TODO
				throw new RuntimeException("error");
			}
			// TODO
			requestWrapper.setHeader("user-id", "user-id");

			// 認証トークン更新
			authTokenObject.setEpochMilliSecond(System.currentTimeMillis());
			responseWrapper.setHeader(AUTH_TOKEN_HEADER_NAME, AuthTokenUtil.create(authTokenObject));

			chain.doFilter(requestWrapper, responseWrapper);

		} else {
			chain.doFilter(request, response);
		}

	}

	@Override
	public void destroy() {
		Filter.super.destroy();
	}

}

package jp.imanaga.web.server.request;

import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

public class CustomizeHttpHeadersRequesetWrapper extends HttpServletRequestWrapper {

	private Map<String, String> customHeaders;

	public CustomizeHttpHeadersRequesetWrapper(HttpServletRequest request) {
		super(request);
		customHeaders = new HashMap<>();
	}

	@Override
	public String getHeader(String name) {
		if (customHeaders.containsKey(name)) {
			return customHeaders.get(name);
		}
		return super.getHeader(name);
	}

	@Override
	public Enumeration<String> getHeaderNames() {
		List<String> headerNames = Stream
				.concat(Collections.list(super.getHeaderNames()).stream(), customHeaders.keySet().stream())
				.collect(Collectors.toList());
		return Collections.enumeration(headerNames);
	}

	@Override
	public Enumeration<String> getHeaders(String name) {
		if (customHeaders.containsKey(name)) {
			return Collections.enumeration(Arrays.asList(customHeaders.get(name)));
		}
		return super.getHeaders(name);
	}

	public void setHeader(String headerName, String headerValue) {
		customHeaders.put(headerName, headerValue);
	}

}

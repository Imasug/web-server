package jp.imanaga.web.server.servlet;

import java.io.IOException;
import java.util.Collections;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

public class ProxyServlet extends HttpServlet {

	private static final long serialVersionUID = -3361443762007737868L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		dispatch(req, resp);
	}

	private void dispatch(HttpServletRequest req, HttpServletResponse resp) {

		System.out.println("dispatch");

		// HTTPヘッダー取得
		MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
		Collections.list(req.getHeaderNames()).forEach(headerName -> {
			map.addAll(headerName, Collections.list(req.getHeaders(headerName)));
		});
		System.out.println(map);

		// TODO

	}

}

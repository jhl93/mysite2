package com.javaex.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.javaex.dao.GuestBookDao;
import com.javaex.util.WebUtil;
import com.javaex.vo.GuestBookVo;

@WebServlet("/gb")
public class GuestBookServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("guestbookServlet 실행");
		request.setCharacterEncoding("UTF-8"); // post면 인코딩

		String actionName = request.getParameter("action");
		GuestBookDao dao = new GuestBookDao();

		if ("dform".equals(actionName)) {
			System.out.println("dform요청");

			WebUtil.forward(request, response, "/WEB-INF/views/guestbook/deleteform.jsp");
		} else if ("add".equals(actionName)) {
			System.out.println("add요청");

			String name = request.getParameter("name");
			String password = request.getParameter("password");
			String content = request.getParameter("content");

			GuestBookVo vo = new GuestBookVo();
			vo.setName(name);
			vo.setPassword(password);
			vo.setContent(content);

			dao.insert(vo);

			WebUtil.redirect(request, response, "./gb?action=list");

		} else if ("delete".equals(actionName)) {
			System.out.println("delete요청");

			int no = Integer.parseInt(request.getParameter("no"));
			String password = request.getParameter("password");

			List<GuestBookVo> list = dao.getList();
			for (GuestBookVo vo : list) {
				if (no == vo.getNo())
					if (password.equals(vo.getPassword()))
						dao.delete(vo);
			}

			WebUtil.redirect(request, response, "./gb?action=list");
		} else {
			System.out.println("list요청");

			List<GuestBookVo> list = dao.getList();
			System.out.println(list.toString());

			// request 안에 data 전달
			request.setAttribute("guestList", list);

			WebUtil.forward(request, response, "/WEB-INF/views/guestbook/addlist.jsp");
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}

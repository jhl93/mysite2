package com.javaex.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.javaex.dao.UserDao;
import com.javaex.util.WebUtil;
import com.javaex.vo.UserVo;

@WebServlet("/user")
public class UserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("user");
		UserDao dao = new UserDao();
		request.setCharacterEncoding("UTF-8"); // post면 인코딩

		String action = request.getParameter("action");

		if ("joinform".equals(action)) {
			System.out.println("joinform");
			WebUtil.forward(request, response, "/WEB-INF/views/user/joinform.jsp");
		} else if ("join".equals(action)) {
			String name = request.getParameter("name");
			String email = request.getParameter("email");
			String password = request.getParameter("password");
			String gender = request.getParameter("gender");

			UserVo uservo = new UserVo(name, email, password, gender);
			System.out.println(uservo.toString());

			dao.insert(uservo);

			WebUtil.forward(request, response, "/WEB-INF/views/user/joinsuccess.jsp");
		} else if ("loginform".equals(action)) {
			System.out.println("loginform");
			WebUtil.forward(request, response, "/WEB-INF/views/user/loginform.jsp");
		} else if ("login".equals(action)) {
			System.out.println("login");

			String email = request.getParameter("email");
			String password = request.getParameter("password");

			System.out.println(email + " / " + password);

			UserVo uservo = dao.getUser(email, password);
			// System.out.println(uservo.toString()); // 객체가 null인 경우 에러

			if (uservo == null) { // 로그인 실패
				WebUtil.redirect(request, response, "./user?action=loginform&result=fail");
			} else { // 로그인 성공
				HttpSession session = request.getSession();
				session.setAttribute("authUser", uservo);

				WebUtil.redirect(request, response, "./main");
			}
		} else if ("logout".equals(action)) {
			HttpSession session = request.getSession();
			session.removeAttribute("authUser");
			session.invalidate(); // 세션을 없애고 속해있는 값들까지 없앰

			WebUtil.redirect(request, response, "./main");
		} else if ("modifyform".equals(action)) {
			System.out.println("modifyform");

			HttpSession session = request.getSession();
			UserVo vo = (UserVo) session.getAttribute("authUser");

			vo = dao.getUser(vo.getNo());

			request.setAttribute("uservo", vo);
			WebUtil.forward(request, response, "/WEB-INF/views/user/modifyform.jsp");
		} else if ("modify".equals(action)) {
			System.out.println("modify");

			HttpSession session = request.getSession();
			UserVo vo = (UserVo) session.getAttribute("authUser");

			vo.setName(request.getParameter("name"));
			vo.setPassword(request.getParameter("password"));
			vo.setGender(request.getParameter("gender"));
			int count = dao.update(vo);
			System.out.println("성공횟수: " + count);
			
			WebUtil.redirect(request, response, "./main");
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}

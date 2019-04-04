package com.javaex.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.javaex.dao.BoardDao;
import com.javaex.util.WebUtil;
import com.javaex.vo.BoardVo;
import com.javaex.vo.UserVo;

@WebServlet("/board")
public class BoardServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("board");
		BoardDao dao = new BoardDao();
		request.setCharacterEncoding("UTF-8"); // post면 인코딩

		String action = request.getParameter("action");

		if ("wform".equals(action)) {
			System.out.println("wform");
			WebUtil.forward(request, response, "/WEB-INF/views/board/writeform.jsp");
		} else if ("write".equals(action)) {
			System.out.println("write");

			String title = request.getParameter("title");
			String content = request.getParameter("content");

			HttpSession session = request.getSession();
			UserVo uservo = (UserVo) session.getAttribute("authUser");
			int userNo = uservo.getNo();

			BoardVo vo = new BoardVo();
			vo.setTitle(title);
			vo.setContent(content);
			vo.setUserNo(userNo);
			dao.insert(vo);

			WebUtil.redirect(request, response, "./board?action=list");
		} else if ("delete".equals(action)) {
			System.out.println("delete");
			int no = Integer.parseInt(request.getParameter("no"));
			dao.delete(no);

			WebUtil.redirect(request, response, "./board?action=list");
		} else if ("mform".equals(action)) {
			System.out.println("mform");
			int no = Integer.parseInt(request.getParameter("no"));
			BoardVo boardvo = dao.getContent(no);

			request.setAttribute("boardvo", boardvo);

			WebUtil.forward(request, response, "/WEB-INF/views/board/modifyform.jsp");
		} else if ("modify".equals(action)) {
			System.out.println("modify");
			int no = Integer.parseInt(request.getParameter("no"));
			String title = request.getParameter("title");
			String content = request.getParameter("content");
			BoardVo boardvo = new BoardVo();
			boardvo.setNo(no);
			boardvo.setTitle(title);
			boardvo.setContent(content);
			System.out.println(boardvo.toString());
			dao.update(boardvo);
			
			WebUtil.redirect(request, response, "./board?action=read&no=" + no);
		} else if ("read".equals(action)) {
			System.out.println("read");

			int no = Integer.parseInt(request.getParameter("no"));
			BoardVo boardvo = dao.getContent(no);

			request.setAttribute("boardvo", boardvo);

			WebUtil.forward(request, response, "/WEB-INF/views/board/read.jsp");
		} else {
			System.out.println("boardlist");

			List<BoardVo> list = dao.getList();
			System.out.println(list.toString());

			// request 안에 data 전달
			request.setAttribute("boardlist", list);

			WebUtil.forward(request, response, "/WEB-INF/views/board/list.jsp");
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}

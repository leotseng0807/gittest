package com.event.controller;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.apache.tomcat.util.codec.binary.StringUtils;

import com.event.model.EventService;
import com.event.model.EventVO;

import oracle.sql.DATE;

@WebServlet("/event/EventServlet")
@MultipartConfig
public class EventServlet extends HttpServlet {
	


	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);

	}

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");


		if ("getOne_For_Display".equals(action)) { // 來自select_page.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			String strFront = null;
			String strEnd = null;
			try {
				/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 **********************/
				String str = req.getParameter("event_id");
				if (str == null || (str.trim()).length() == 0) {
					errorMsgs.add("請輸入活動編號");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/back-end/events/select_page.jsp");
					failureView.forward(req, res);
					return;// 程式中斷
				}

				if (str.length() > 5) {
					strFront = str.substring(0, 5);
					strEnd = str.substring(5);
				} else {
					strEnd = str;
				}

				if (strFront != null && strEnd != null) {
					if (!strFront.equalsIgnoreCase("EVENT") || strEnd.length() > 6) {
						errorMsgs.add("活動編號格式不正確");
					}
				}

				try {
					Integer event_id = new Integer(strEnd);
				} catch (Exception e) {
					errorMsgs.add("活動編號格式不正確");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/back-end/events/select_page.jsp");
					failureView.forward(req, res);
					return;// 程式中斷
				}

				/*************************** 2.開始查詢資料 *****************************************/
				EventService eventSvc = new EventService();
				EventVO eventVO = eventSvc.getOneEvent("EVENT" + strEnd);
				if (eventVO == null) {
					errorMsgs.add("查無資料");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/back-end/events/select_page.jsp");
					failureView.forward(req, res);
					return;// 程式中斷
				}

				/*************************** 3.查詢完成,準備轉交(Send the Success view) *************/
				req.setAttribute("eventVO", eventVO); // 資料庫取出的empVO物件,存入req
				String url = "/back-end/events/listOneEvent.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交 listOneEmp.jsp
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 *************************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得資料:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/events/select_page.jsp");
				failureView.forward(req, res);
			}
		}

		if ("getOne_For_Update".equals(action)) { // 來自listAllEmp.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				/***************************1.接收請求參數****************************************/
				String event_id = req.getParameter("event_id");
				
				/***************************2.開始查詢資料****************************************/
				EventService eventSvc = new EventService();
				EventVO eventVO = eventSvc.getOneEvent(event_id);
								
				/***************************3.查詢完成,準備轉交(Send the Success view)************/
				req.setAttribute("eventVO", eventVO);         // 資料庫取出的empVO物件,存入req
				String url = "/back-end/events/update_event_input.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// 成功轉交 update_emp_input.jsp
				successView.forward(req, res);

				/***************************其他可能的錯誤處理**********************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得要修改的資料:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/back-end/events/select_page.jsp");
				failureView.forward(req, res);
			}
		}
		
		
		if ("update".equals(action)) { // 來自update_emp_input.jsp的請求
			
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
		
			try {
				/***************************1.接收請求參數 - 輸入格式的錯誤處理**********************/
				String event_id = req.getParameter("event_id").trim();
				if (event_id == null || event_id.trim().length() == 0) {
					errorMsgs.add("出現未知錯誤");
				}
				
				String event_title = req.getParameter("event_title");
				String inputWord = "^[(\u4e00-\u9fa5)(a-zA-Z0-9_)]{2,10}$";
				if (event_title == null || event_title.trim().length() == 0) {
					errorMsgs.add("活動標題: 請勿空白");
				} else if (!event_title.trim().matches(inputWord)) { // 以下練習正則(規)表示式(regular-expression)
					errorMsgs.add("活動標題: 只能是中、英文字母、數字和_ , 且長度必需在2到10之間");
				}

				String band_id = req.getParameter("band_id").trim();
				if (band_id == null || band_id.trim().length() == 0) {
					errorMsgs.add("職位請勿空白");
				}

				java.sql.Timestamp event_start_time = null;
				try {
					event_start_time =  new java.sql.Timestamp(sdf.parse(req.getParameter("event_start_time").trim()).getTime());
				} catch (IllegalArgumentException e) {
					event_start_time = new java.sql.Timestamp(System.currentTimeMillis());
					errorMsgs.add("請輸入日期!");
				}

				Integer event_type = null;
				try {
					event_type = new Integer(req.getParameter("event_type").trim());
				} catch (NumberFormatException e) {
					event_type = 0;
					errorMsgs.add("請選擇活動類型.");
				}

				String event_detail = req.getParameter("event_detail").trim();
				if (event_detail == null || event_detail.trim().length() == 0) {
					errorMsgs.add("活動詳情不得空白");
				}

				Integer event_sort = null;
				try {
					event_sort = new Integer(req.getParameter("event_sort").trim());
				} catch (NumberFormatException e) {
					event_sort = 0;
					errorMsgs.add("活動排序不能空白.");
				}

				Part part1 = req.getPart("event_poster");
				byte[] event_poster = null;
				if (part1 == null) {
					errorMsgs.add("請選擇活動海報.");
				} else {
					InputStream in = part1.getInputStream();
					event_poster = new byte[in.available()];
					in.read(event_poster);
					in.close();
				}

				String event_place = req.getParameter("event_place").trim();
				if (event_place == null || event_place.trim().length() == 0) {
					errorMsgs.add("活動場地請勿空白");
				}

				Integer event_area = null;
				try {
					event_area = new Integer(req.getParameter("event_area").trim());
				} catch (NumberFormatException e) {
					event_area = 0;
					errorMsgs.add("請選擇活動類型.");
				}

				String event_city = req.getParameter("event_city").trim();
				if (event_city == null || event_city.trim().length() == 0) {
					errorMsgs.add("活動縣市不得空白");
				}

				String event_cityarea = req.getParameter("event_cityarea").trim();
				if (event_cityarea == null || event_cityarea.trim().length() == 0) {
					errorMsgs.add("活動縣市分區不得空白");
				}

				String event_address = req.getParameter("event_address").trim();
				if (event_address == null || event_address.trim().length() == 0) {
					errorMsgs.add("活動地址不得空白");
				}

				java.sql.Timestamp event_on_time = null;
				try {
					event_on_time =  new java.sql.Timestamp(sdf.parse(req.getParameter("event_on_time").trim()).getTime());
				} catch (IllegalArgumentException e) {
					event_on_time = new java.sql.Timestamp(System.currentTimeMillis());
					System.out.println("錯誤");
					errorMsgs.add("請輸入日期!");
				}

				Integer event_status = null;
				try {
					event_status = new Integer(req.getParameter("event_status").trim());
				} catch (NumberFormatException e) {
					event_status = 0;
					errorMsgs.add("請選擇活動上架狀態.");
				}

				Part part2 = req.getPart("event_seat");
				byte[] event_seat = null;
				if (part2 == null) {
					errorMsgs.add("請選擇座位圖.");
				} else {
					InputStream in = part2.getInputStream();
					event_seat = new byte[in.available()];
					in.read(event_seat);
					in.close();
				}

				java.sql.Timestamp event_last_edit_time = null;
				System.out.println(req.getParameter("event_last_edit_time"));
				;
				try {
					event_last_edit_time =  new java.sql.Timestamp(Long.parseLong(req.getParameter("event_last_edit_time")));
				} catch (IllegalArgumentException e) {
					event_on_time = new java.sql.Timestamp(System.currentTimeMillis());
					System.out.println("時間錯誤");
					errorMsgs.add("網頁出現未知錯誤!");
				}

				String event_last_editor = req.getParameter("event_last_editor").trim();
				if (event_last_editor == null || event_last_editor.trim().length() == 0) {
					System.out.println("使用者錯誤");
					errorMsgs.add("網頁出現未知錯誤");
				}

				EventVO eventVO = new EventVO();
				eventVO.setBand_id(band_id);
				eventVO.setEvent_type(event_type);
				eventVO.setEvent_sort(event_sort);
				eventVO.setEvent_title(event_title);
				eventVO.setEvent_detail(event_detail);
				eventVO.setEvent_poster(event_poster);
				eventVO.setEvent_area(event_area);
				eventVO.setEvent_place(event_place);
				eventVO.setEvent_city(event_cityarea);
				eventVO.setEvent_cityarea(event_cityarea);
				eventVO.setEvent_address(event_address);
				eventVO.setEvent_start_time(event_start_time);
				eventVO.setEvent_on_time(event_on_time);
				eventVO.setEvent_last_edit_time(event_last_edit_time);
				eventVO.setEvent_last_editor(event_last_editor);
				eventVO.setEvent_status(event_status);
				eventVO.setEvent_seat(event_seat);

				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("eventVO", eventVO); // 含有輸入格式錯誤的empVO物件,也存入req
					RequestDispatcher failureView = req.getRequestDispatcher("/back-end/events/addEvent.jsp");
					failureView.forward(req, res);
					return;
				}
				
				/***************************2.開始修改資料*****************************************/
				EventService eventSvc = new EventService();
				eventVO = eventSvc.updateEvent(event_id, band_id, event_type, event_sort, event_title, event_detail, event_poster, event_area, event_place, event_city, event_cityarea, event_address, event_start_time, event_on_time, event_last_edit_time, event_last_editor, event_status, event_seat);
				
				/***************************3.修改完成,準備轉交(Send the Success view)*************/
				req.setAttribute("eventVO", eventVO); // 資料庫update成功後,正確的的empVO物件,存入req
				String url = "/emp/listOneEmp.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 修改成功後,轉交listOneEmp.jsp
				successView.forward(req, res);

				/***************************其他可能的錯誤處理*************************************/
			} catch (Exception e) {
				errorMsgs.add("修改資料失敗:"+e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/emp/update_emp_input.jsp");
				failureView.forward(req, res);
			}
		}

		if ("insert".equals(action)) { // 來自addEmp.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*********************** 1.接收請求參數 - 輸入格式的錯誤處理 *************************/
				String event_title = req.getParameter("event_title");
				String inputWord = "^[(\u4e00-\u9fa5)(a-zA-Z0-9_)]{2,10}$";
				if (event_title == null || event_title.trim().length() == 0) {
					errorMsgs.add("活動標題: 請勿空白");
				} else if (!event_title.trim().matches(inputWord)) { // 以下練習正則(規)表示式(regular-expression)
					errorMsgs.add("活動標題: 只能是中、英文字母、數字和_ , 且長度必需在2到10之間");
				}

				String band_id = req.getParameter("band_id").trim();
				if (band_id == null || band_id.trim().length() == 0) {
					errorMsgs.add("職位請勿空白");
				}

				java.sql.Timestamp event_start_time = null;
				try {
					event_start_time =  new java.sql.Timestamp(sdf.parse(req.getParameter("event_start_time").trim()).getTime());
				} catch (IllegalArgumentException e) {
					event_start_time = new java.sql.Timestamp(System.currentTimeMillis());
					errorMsgs.add("請輸入日期!");
				}

				Integer event_type = null;
				try {
					event_type = new Integer(req.getParameter("event_type").trim());
				} catch (NumberFormatException e) {
					event_type = 0;
					errorMsgs.add("請選擇活動類型.");
				}

				String event_detail = req.getParameter("event_detail").trim();
				if (event_detail == null || event_detail.trim().length() == 0) {
					errorMsgs.add("活動詳情不得空白");
				}

				Integer event_sort = null;
				try {
					event_sort = new Integer(req.getParameter("event_sort").trim());
				} catch (NumberFormatException e) {
					event_sort = 0;
					errorMsgs.add("活動排序不能空白.");
				}

				Part part1 = req.getPart("event_poster");
				byte[] event_poster = null;
				if (part1 == null) {
					errorMsgs.add("請選擇活動海報.");
				} else {
					InputStream in = part1.getInputStream();
					event_poster = new byte[in.available()];
					in.read(event_poster);
					in.close();
				}

				String event_place = req.getParameter("event_place").trim();
				if (event_place == null || event_place.trim().length() == 0) {
					errorMsgs.add("活動場地請勿空白");
				}

				Integer event_area = null;
				try {
					event_area = new Integer(req.getParameter("event_area").trim());
				} catch (NumberFormatException e) {
					event_area = 0;
					errorMsgs.add("請選擇活動類型.");
				}

				String event_city = req.getParameter("event_city").trim();
				if (event_city == null || event_city.trim().length() == 0) {
					errorMsgs.add("活動縣市不得空白");
				}

				String event_cityarea = req.getParameter("event_cityarea").trim();
				if (event_cityarea == null || event_cityarea.trim().length() == 0) {
					errorMsgs.add("活動縣市分區不得空白");
				}

				String event_address = req.getParameter("event_address").trim();
				if (event_address == null || event_address.trim().length() == 0) {
					errorMsgs.add("活動地址不得空白");
				}

				java.sql.Timestamp event_on_time = null;
				try {
					event_on_time =  new java.sql.Timestamp(sdf.parse(req.getParameter("event_on_time").trim()).getTime());
				} catch (IllegalArgumentException e) {
					event_on_time = new java.sql.Timestamp(System.currentTimeMillis());
					System.out.println("錯誤");
					errorMsgs.add("請輸入日期!");
				}

				Integer event_status = null;
				try {
					event_status = new Integer(req.getParameter("event_status").trim());
				} catch (NumberFormatException e) {
					event_status = 0;
					errorMsgs.add("請選擇活動上架狀態.");
				}

				Part part2 = req.getPart("event_seat");
				byte[] event_seat = null;
				if (part2 == null) {
					errorMsgs.add("請選擇座位圖.");
				} else {
					InputStream in = part2.getInputStream();
					event_seat = new byte[in.available()];
					in.read(event_seat);
					in.close();
				}

				java.sql.Timestamp event_last_edit_time = null;
				System.out.println(req.getParameter("event_last_edit_time"));
				;
				try {
					event_last_edit_time =  new java.sql.Timestamp(Long.parseLong(req.getParameter("event_last_edit_time")));
				} catch (IllegalArgumentException e) {
					event_on_time = new java.sql.Timestamp(System.currentTimeMillis());
					System.out.println("時間錯誤");
					errorMsgs.add("網頁出現未知錯誤!");
				}

				String event_last_editor = req.getParameter("event_last_editor").trim();
				if (event_last_editor == null || event_last_editor.trim().length() == 0) {
					System.out.println("使用者錯誤");
					errorMsgs.add("網頁出現未知錯誤");
				}

				EventVO eventVO = new EventVO();
				eventVO.setBand_id(band_id);
				eventVO.setEvent_type(event_type);
				eventVO.setEvent_sort(event_sort);
				eventVO.setEvent_title(event_title);
				eventVO.setEvent_detail(event_detail);
				eventVO.setEvent_poster(event_poster);
				eventVO.setEvent_area(event_area);
				eventVO.setEvent_place(event_place);
				eventVO.setEvent_city(event_cityarea);
				eventVO.setEvent_cityarea(event_cityarea);
				eventVO.setEvent_address(event_address);
				eventVO.setEvent_start_time(event_start_time);
				eventVO.setEvent_on_time(event_on_time);
				eventVO.setEvent_last_edit_time(event_last_edit_time);
				eventVO.setEvent_last_editor(event_last_editor);
				eventVO.setEvent_status(event_status);
				eventVO.setEvent_seat(event_seat);

				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("eventVO", eventVO); // 含有輸入格式錯誤的empVO物件,也存入req
					RequestDispatcher failureView = req.getRequestDispatcher("/back-end/events/addEvent.jsp");
					failureView.forward(req, res);
					return;
				}

				/*************************** 2.開始新增資料 ***************************************/
				EventService eventSvc = new EventService();
				eventVO = eventSvc.addEvent(band_id, event_type, event_sort, event_title, event_detail, event_poster, event_area, event_place, event_city, event_cityarea, event_address, event_start_time, event_on_time, event_last_edit_time, event_last_editor, event_status, event_seat);

				/*************************** 3.新增完成,準備轉交(Send the Success view) ***********/
				String url = "/back-end/events/listAllEvents.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 新增成功後轉交listAllEmp.jsp
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 **********************************/
			} catch (Exception e) {
				e.printStackTrace();
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/events/addEvent.jsp");
				failureView.forward(req, res);
			}
		}

//		
//		if ("delete".equals(action)) { // 來自listAllEmp.jsp
//
//			List<String> errorMsgs = new LinkedList<String>();
//			// Store this set in the request scope, in case we need to
//			// send the ErrorPage view.
//			req.setAttribute("errorMsgs", errorMsgs);
//	
//			try {
//				/***************************1.接收請求參數***************************************/
//				Integer empno = new Integer(req.getParameter("empno"));
//				
//				/***************************2.開始刪除資料***************************************/
//				EmpService empSvc = new EmpService();
//				empSvc.deleteEmp(empno);
//				
//				/***************************3.刪除完成,準備轉交(Send the Success view)***********/								
//				String url = "/emp/listAllEmp.jsp";
//				RequestDispatcher successView = req.getRequestDispatcher(url);// 刪除成功後,轉交回送出刪除的來源網頁
//				successView.forward(req, res);
//				
//				/***************************其他可能的錯誤處理**********************************/
//			} catch (Exception e) {
//				errorMsgs.add("刪除資料失敗:"+e.getMessage());
//				RequestDispatcher failureView = req
//						.getRequestDispatcher("/emp/listAllEmp.jsp");
//				failureView.forward(req, res);
//			}
//		}
	}

}

package com.event.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EventJDBCDAO implements EventDAO {

	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String userid = "TEA102G6";
	String passwd = "123456";

	private static final String INSERT_STMT = "INSERT INTO event (event_id," + "band_id," + "event_type,"
			+ "event_sort," + "event_title," + "event_detail," + "event_poster," + "event_area," + "event_place,"
			+ "event_city," + "event_cityarea," + "event_address," + "event_start_time," + "event_on_time,"
			+ "event_last_edit_time," + "event_last_editor," + "event_status,"
			+ "event_seat) VALUES ('EVENT'||LPAD(EVENT_SEQ.NEXTVAL, 5, '0'),?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
	private static final String GET_ALL_STMT = "SELECT event_id, band_id, event_type, event_sort, event_title, event_detail, event_poster, event_area, event_place, event_city,event_cityarea, event_address, event_start_time, event_on_time, event_last_edit_time,event_last_editor, event_status, event_seat FROM event";
	private static final String GET_ONE_STMT = "SELECT " + "band_id," + "event_type," + "event_sort," + "event_title,"
			+ "event_detail," + "event_poster," + "event_area," + "event_place," + "event_city," + "event_cityarea,"
			+ "event_address," + "event_start_time," + "event_on_time," + "event_last_edit_time," + "event_last_editor,"
			+ "event_status," + "event_seat FROM event where event_id = ?";
	private static final String DELETE = "DELETE FROM event where event_id = ?";
	private static final String UPDATE = "UPDATE event set band_id=?," + "event_type=?," + "event_sort=?,"
			+ "event_title=?," + "event_detail=?," + "event_poster=?," + "event_area=?," + "event_place=?,"
			+ "event_city=?," + "event_cityarea=?," + "event_address=?," + "event_start_time=?," + "event_on_time=?,"
			+ "event_last_edit_time=?," + "event_last_editor=?," + "event_status=?,"
			+ "event_seat=? where event_id = ?";
	private static final String GET_LIST_BY_BANDID = "SELECT event_id, band_id, event_type, event_sort, event_title, event_detail, event_poster, event_area, event_place, event_city,event_cityarea, event_address, event_start_time, event_on_time, event_last_edit_time,event_last_editor, event_status, event_seat FROM event WHERE BAND_ID = ?  ORDER BY EVENT_SORT";

	@Override
	public void insert(EventVO eventVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, eventVO.getBand_id());
			pstmt.setInt(2, eventVO.getEvent_type());
			pstmt.setInt(3, eventVO.getEvent_sort());
			pstmt.setString(4, eventVO.getEvent_title());
			pstmt.setString(5, eventVO.getEvent_detail());
			pstmt.setBytes(6, eventVO.getEvent_poster());
			pstmt.setInt(7, eventVO.getEvent_area());
			pstmt.setString(8, eventVO.getEvent_place());
			pstmt.setString(9, eventVO.getEvent_city());
			pstmt.setString(10, eventVO.getEvent_cityarea());
			pstmt.setString(11, eventVO.getEvent_address());
			pstmt.setTimestamp(12, eventVO.getEvent_start_time());
			pstmt.setTimestamp(13, eventVO.getEvent_on_time());
			pstmt.setTimestamp(14, eventVO.getEvent_last_edit_time());
			pstmt.setString(15, eventVO.getEvent_last_editor());
			pstmt.setInt(16, eventVO.getEvent_status());
			pstmt.setBytes(17, eventVO.getEvent_seat());

			pstmt.executeUpdate();

			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
			// Clean up JDBC resources
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
	}

	@Override
	public void update(EventVO eventVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setString(1, eventVO.getBand_id());
			pstmt.setInt(2, eventVO.getEvent_type());
			pstmt.setInt(3, eventVO.getEvent_sort());
			pstmt.setString(4, eventVO.getEvent_title());
			pstmt.setString(5, eventVO.getEvent_detail());
			pstmt.setBytes(6, eventVO.getEvent_poster());
			pstmt.setInt(7, eventVO.getEvent_area());
			pstmt.setString(8, eventVO.getEvent_place());
			pstmt.setString(9, eventVO.getEvent_city());
			pstmt.setString(10, eventVO.getEvent_cityarea());
			pstmt.setString(11, eventVO.getEvent_address());
			pstmt.setTimestamp(12, eventVO.getEvent_start_time());
			pstmt.setTimestamp(13, eventVO.getEvent_on_time());
			pstmt.setTimestamp(14, eventVO.getEvent_last_edit_time());
			pstmt.setString(15, eventVO.getEvent_last_editor());
			pstmt.setInt(16, eventVO.getEvent_status());
			pstmt.setBytes(17, eventVO.getEvent_seat());
			pstmt.setString(18, eventVO.getEvent_id());

			pstmt.executeUpdate();

			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
			// Clean up JDBC resources
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}

	}

	@Override
	public void delete(String event_id) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(DELETE);

			pstmt.setString(1, event_id);

			pstmt.executeUpdate();

			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
			// Clean up JDBC resources
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}

	}

	@Override
	public EventVO findByPrimaryKey(String event_id) {
		EventVO eventVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setString(1, event_id);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				eventVO = new EventVO();
				
				eventVO.setEvent_id(event_id);
				eventVO.setBand_id(rs.getString("band_id"));
				eventVO.setEvent_type(rs.getInt("event_type"));
				eventVO.setEvent_sort(rs.getInt("event_sort"));
				eventVO.setEvent_title(rs.getString("event_title"));
				eventVO.setEvent_detail(rs.getString("event_detail"));
				eventVO.setEvent_poster(rs.getBytes("event_poster"));
				eventVO.setEvent_area(rs.getInt("event_area"));
				eventVO.setEvent_place(rs.getString("event_place"));
				eventVO.setEvent_city(rs.getString("event_city"));
				eventVO.setEvent_cityarea(rs.getString("event_cityarea"));
				eventVO.setEvent_address(rs.getString("event_address"));
				eventVO.setEvent_start_time(rs.getTimestamp("event_start_time"));
				eventVO.setEvent_on_time(rs.getTimestamp("event_on_time"));
				eventVO.setEvent_last_edit_time(rs.getTimestamp("event_last_edit_time"));
				eventVO.setEvent_last_editor(rs.getString("event_last_editor"));
				eventVO.setEvent_status(rs.getInt("event_status"));
				eventVO.setEvent_seat(rs.getBytes("event_seat"));
			}

			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
			// Clean up JDBC resources
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		return eventVO;
	}

	@Override
	public List<EventVO> getAll() {
		List<EventVO> list = new ArrayList<EventVO>();
		EventVO eventVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				// empVO �]�٬� Domain objects
				eventVO = new EventVO();
				eventVO.setEvent_id(rs.getString("event_id"));
				eventVO.setBand_id(rs.getString("band_id"));
				eventVO.setEvent_type(rs.getInt("event_type"));
				eventVO.setEvent_sort(rs.getInt("event_sort"));
				eventVO.setEvent_title(rs.getString("event_title"));
				eventVO.setEvent_detail(rs.getString("event_detail"));
				eventVO.setEvent_poster(rs.getBytes("event_poster"));
				eventVO.setEvent_area(rs.getInt("event_area"));
				eventVO.setEvent_place(rs.getString("event_place"));
				eventVO.setEvent_city(rs.getString("event_city"));
				eventVO.setEvent_cityarea(rs.getString("event_cityarea"));
				eventVO.setEvent_address(rs.getString("event_address"));
				eventVO.setEvent_start_time(rs.getTimestamp("event_start_time"));
				eventVO.setEvent_on_time(rs.getTimestamp("event_on_time"));
				eventVO.setEvent_last_edit_time(rs.getTimestamp("event_last_edit_time"));
				eventVO.setEvent_last_editor(rs.getString("event_last_editor"));
				eventVO.setEvent_status(rs.getInt("event_status"));
				eventVO.setEvent_seat(rs.getBytes("event_seat"));

				list.add(eventVO); // Store the row in the list
			}

			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
			// Clean up JDBC resources
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		return list;
	}

	public static void main(String[] args) {
		Date date = new Date();
		Timestamp timestamp = new Timestamp(date.getTime());
		EventJDBCDAO eventJDBCDAO = new EventJDBCDAO();
//      測試新增
//		EventVO eventVO1 = new EventVO();
//		eventVO1.setBand_id("band00001");
//		eventVO1.setEvent_type(3);
//		eventVO1.setEvent_sort(39);
//		eventVO1.setEvent_title("新增活動標題");
//		eventVO1.setEvent_detail("新增活動詳情");
//		eventVO1.setEvent_poster(null);
//		eventVO1.setEvent_area(3);
//		eventVO1.setEvent_place("活動場地名稱");
//		eventVO1.setEvent_city("活動縣市");
//		eventVO1.setEvent_cityarea("活動縣市區域");
//		eventVO1.setEvent_address("活動詳細地址");
//		eventVO1.setEvent_start_time(timestamp);
//		eventVO1.setEvent_on_time(timestamp);
//		eventVO1.setEvent_last_edit_time(timestamp);
//		eventVO1.setEvent_last_editor("band00001");
//		eventVO1.setEvent_status(0);
//		eventVO1.setEvent_seat(null);
//
//		eventJDBCDAO.insert(eventVO1);
//
//		// 測試修改
//		EventVO eventVO2 = new EventVO();
//		eventVO2.setEvent_id("EVENT00250");
//		eventVO2.setBand_id("band00001");
//		eventVO2.setEvent_type(3);
//		eventVO2.setEvent_sort(39);
//		eventVO2.setEvent_title("修改活動標題");
//		eventVO2.setEvent_detail("修改活動詳情");
//		eventVO2.setEvent_poster(null);
//		eventVO2.setEvent_area(3);
//		eventVO2.setEvent_place("修改活動場地名稱");
//		eventVO2.setEvent_city("修改活動縣市");
//		eventVO2.setEvent_cityarea("修改活動縣市區域");
//		eventVO2.setEvent_address("修改活動詳細地址");
//		eventVO2.setEvent_start_time(timestamp);
//		eventVO2.setEvent_on_time(timestamp);
//		eventVO2.setEvent_last_edit_time(timestamp);
//		eventVO2.setEvent_last_editor("band00001");
//		eventVO2.setEvent_status(0);
//		eventVO2.setEvent_seat(null);
//
		// eventJDBCDAO.update(eventVO2);
////
////		//測試刪除
//		eventJDBCDAO.delete(eventVO2.getEvent_id());
//			//測試查詢單筆
//		EventVO eventVO3 = eventJDBCDAO.findByPrimaryKey("EVENT00000");
//		System.out.println(eventVO3.toString());
////		測試查詢全部
//		List<EventVO> rs = eventJDBCDAO.getAll();
//
//		for (EventVO i : rs) {
//			System.out.println(i.toString());
//		}
		
//		測試查詢樂團活動		
		List<EventVO> rs = eventJDBCDAO.findByBandId("BAND00000");

		for (EventVO i : rs) {
			System.out.println(i.toString());
		}
	}

	@Override
	public List<EventVO> findByBandId(String band_id) {
		List<EventVO> list = new ArrayList<EventVO>();
		EventVO eventVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_LIST_BY_BANDID);
			pstmt.setString(1, band_id);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				eventVO = new EventVO();
				eventVO.setEvent_id(rs.getString("event_id"));
				eventVO.setBand_id(rs.getString("band_id"));
				eventVO.setEvent_type(rs.getInt("event_type"));
				eventVO.setEvent_sort(rs.getInt("event_sort"));
				eventVO.setEvent_title(rs.getString("event_title"));
				eventVO.setEvent_detail(rs.getString("event_detail"));
				eventVO.setEvent_poster(rs.getBytes("event_poster"));
				eventVO.setEvent_area(rs.getInt("event_area"));
				eventVO.setEvent_place(rs.getString("event_place"));
				eventVO.setEvent_city(rs.getString("event_city"));
				eventVO.setEvent_cityarea(rs.getString("event_cityarea"));
				eventVO.setEvent_address(rs.getString("event_address"));
				eventVO.setEvent_start_time(rs.getTimestamp("event_start_time"));
				eventVO.setEvent_on_time(rs.getTimestamp("event_on_time"));
				eventVO.setEvent_last_edit_time(rs.getTimestamp("event_last_edit_time"));
				eventVO.setEvent_last_editor(rs.getString("event_last_editor"));
				eventVO.setEvent_status(rs.getInt("event_status"));
				eventVO.setEvent_seat(rs.getBytes("event_seat"));

				list.add(eventVO); // Store the row in the list
			}

			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
			// Clean up JDBC resources
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		return list;
	}
}

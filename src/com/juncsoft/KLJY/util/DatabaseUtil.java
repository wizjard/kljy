package com.juncsoft.KLJY.util;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;


public class DatabaseUtil {
	private static SessionFactory sf;
	private static Properties prop;
	public static String Oracel_table_doc = "";
	public static String Oracle_table_patient = "";
	public static String juncsoft_pool ="java:comp/env/jdbc/juncsoftDB";//电子病历连接池数据源
	public static String his_pool="java:comp/env/jdbc/hisDB";//hospital information system 连接池数据源
	static {
		sf = new Configuration().configure().buildSessionFactory();
		prop = new Properties();
		InputStream is = DatabaseUtil.class.getClassLoader()
				.getResourceAsStream(
						"com/juncsoft/KLJY/config/database.conn.properties");
		try {
			prop.load(is);
			Class.forName(prop.getProperty("Local_driver_class"));
			Class.forName(prop.getProperty("Oracle_driver_class"));
			if (prop.getProperty("Oracle_test").equalsIgnoreCase("true")) {
				Oracel_table_doc = prop.getProperty("Oracel_table_doc_test");
				Oracle_table_patient = prop
						.getProperty("Oracle_table_patient_test");
			} else {
				Oracel_table_doc = prop.getProperty("Oracel_table_doc");
				Oracle_table_patient = prop.getProperty("Oracle_table_patient");
			}
			
			//连接池数据源初始化 2011-09-01 start
//			Context context = new InitialContext();//检索指定的对象
//			juncsoft_pool = (DataSource)context.lookup("java:comp/env/jdbc/juncsoftDB");
//			his_pool = (DataSource)context.lookup("java:comp/env/jdbc/hisDB");
//			if(juncsoft_pool == null){
//				System.out.println("current juncsoft_pool is not initial susccess");
//			}
//			if(his_pool == null){
//				System.out.println("current his_pool is not initial susccess");
//			}
			//连接池数据源初始化 2011-09-01 end
		}catch (Exception e) {
			e.printStackTrace();
		} 
	}

	public static Connection getConnection() throws Exception {
		/*
		Connection conn = null;
		try {
			if (prop.getProperty("Local_UserDataSource").equalsIgnoreCase(
					"true")) {
				Context ctx = new InitialContext();
				DataSource ds = (DataSource) ctx.lookup(prop
						.getProperty("Local_DataSource"));
				conn = ds.getConnection();
			} else {
				conn = getJdbcConnection();
			}
		} catch (Exception e) {
			throw e;
		}
		*/
		//连接池数据源初始化 2011-09-01 start
		Context context = new InitialContext();//检索指定的对象
		DataSource ds = (DataSource)context.lookup(juncsoft_pool);
		return ds.getConnection();
		//连接池数据源初始化 2011-09-01 end
	}

	public static Connection getJdbcConnection() throws Exception {
		/*
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(prop.getProperty("Local_url"),
					prop.getProperty("Local_username"), prop
							.getProperty("Local_password"));
		} catch (Exception e) {
			throw e;
		}
		*/
		//连接池数据源初始化 2011-09-01 start
		Context context = new InitialContext();//检索指定的对象
		DataSource ds = (DataSource)context.lookup(juncsoft_pool);
		return ds.getConnection();
		//连接池数据源初始化 2011-09-01 end
	}

	public static Session getSession() throws Exception {
		Session session = null;
		try {
			session = sf.openSession();
		} catch (Exception e) {
			throw e;
		}
		return session;
	}

	public static void closeResource(Session session) {
		if (session != null)
			session.close();
	}

	public static void closeResource(Connection conn, Statement sm, ResultSet rs)
			throws SQLException {
		try {
			if (rs != null)
				rs.close();
			if (sm != null)
				sm.close();
			if (conn != null)
				conn.close();
		} catch (SQLException e) {
			throw e;
		}
	}

	public static Connection getConnetion_His() throws Exception {
		/*
		Connection conn = null;
		try {
			if (prop.getProperty("Oracle_test").equalsIgnoreCase("true"))
				conn = DatabaseUtil.getConnection();
			else
				conn = DriverManager.getConnection(prop
						.getProperty("Oracle_url"), prop
						.getProperty("Oracle_username"), prop
						.getProperty("Oracle_password"));
		} catch (Exception e) {
			throw e;
		}
		*/
		
		//连接池数据源初始化 2011-09-01 start
		Context context = new InitialContext();//检索指定的对象
		DataSource ds = (DataSource)context.lookup(his_pool);
		return ds.getConnection();
		//连接池数据源初始化 2011-09-01 end
	}

	public static synchronized boolean excuteSQL(String sql) throws Exception {
		boolean bool = false;
		Connection conn = null;
		Statement sm = null;
		try {
			conn = getConnection();
			sm = conn.createStatement();
			bool = sm.execute(sql);
		} catch (Exception e) {
			throw e;
		} finally {
			closeResource(conn, sm, null);
		}
		return bool;
	}

	public static synchronized Long save(Object obj) throws Exception {
		Long id = null;
		Session session = null;
		Transaction tran = null;
		try {
			session = getSession();
			tran = session.beginTransaction();
			id = (Long) session.save(obj);
			tran.commit();
		} catch (Exception e) {
			e.printStackTrace();
			tran.rollback();
			throw e;
		} finally {
			closeResource(session);
		}
		return id;
	}

	public static synchronized void delete(Object obj) throws Exception {
		Session session = null;
		Transaction tran = null;
		try {
			session = getSession();
			tran = session.beginTransaction();
			session.delete(obj);
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			throw e;
		} finally {
			closeResource(session);
		}
	}

	@SuppressWarnings("unchecked")
	public static synchronized Object findById(Class cls, Long id)
			throws Exception {
		Object obj = null;
		Session session = null;
		Transaction tran = null;
		try {
			session = getSession();
			tran = session.beginTransaction();
			obj = session.get(cls, id);
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			throw e;
		} finally {
			closeResource(session);
		}
		return obj;
	}

	public static synchronized void update(Object obj) throws Exception {
		Session session = null;
		Transaction tran = null;
		try {
			session = getSession();
			tran = session.beginTransaction();
			session.update(obj);
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			throw e;
		} finally {
			closeResource(session);
		}
	}

	public static void main(String[] args) {
		try {
			System.out.println(getSession());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
//	public static void test(String DoctorID,String WardCode) throws Exception{
//		Connection conn = null;
//		Statement sm = null;
//		ResultSet rs = null;
//		try {
//			conn = DatabaseUtil.getConnetion_His();
//			String sql = "select * from \"VIEW_IPD_DOCTOR\" where DOCTORID='" + DoctorID + "' and ORGID='"
//					+ WardCode + "'";
//			conn = DatabaseUtil.getConnetion_His();
//			sm = conn.createStatement();
//			rs = sm.executeQuery(sql);
//			if (rs.next()) {
//				User user = new User();
//				user.setRole(rs.getString("TITLEID"));
//				user.setBelong(rs.getString("ORGID"));
//				user.setPassword(new MD5().getMD5ofStr("11111111"));
//				String name = rs.getString("DOCTORNAME");
//				user.setName(name);
//				user.setAccount("");
//				user.setValid(1);
//				Date date = new Date(Calendar.getInstance().getTimeInMillis());
//				user.setCreateDate(date);
//				user.setModifyDate(date);
//			}
//			}catch(Exception e){
//				throw e;
//			}finally{
//				DatabaseUtil.closeResource(conn, sm, rs);
//			}
//	}
	
	private static  String Remote_driver;
	private static  String Remote_url;
	private static  String Remote_username;
	private static  String Remote_password;
	
	private static  String Local_driver;
	private static  String Local_url;
	private static  String Local_username;
	private static  String Local_password;
	static {
		 InputStream is = DatabaseUtil.class.getClassLoader().getResourceAsStream(
		 "com/juncsoft/KLJY/config/database.conn.properties");
		 Properties p = new Properties();
		 try {
			 p.load(is);
			 Remote_driver = p.getProperty("Remote_driver");
			 Remote_url = p.getProperty("Remote_url");
			 Remote_username = p.getProperty("Remote_username");
			 Remote_password = p.getProperty("Remote_password");
			 
			 Local_driver = p.getProperty("Local_driver_class");
			 Local_url = p.getProperty("Local_url");
			 Local_username = p.getProperty("Local_username");
			 Local_password = p.getProperty("Local_password");	
		 } catch (IOException e) {
			 e.printStackTrace();
		 }
		try {
			Class.forName(Remote_driver);
			Class.forName(Local_driver);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			System.exit(-1);
		}
	}
	
	public static Connection getRemoteConn() throws SQLException {
		return DriverManager.getConnection(Remote_url, Remote_username,
				Remote_password);
	}
	public static Connection getLocalConn() throws SQLException {
		return DriverManager.getConnection(Local_url, Local_username,
				Local_password);
	}

	public static void closeConn(Connection connection) {
		try {
			if (connection != null) {
				connection.close();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void closePs(PreparedStatement preparedStatement) {
		try {
			if (preparedStatement != null) {
				preparedStatement.close();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void closeRs(ResultSet resultSet) {
		try {
			if (resultSet != null) {
				resultSet.close();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

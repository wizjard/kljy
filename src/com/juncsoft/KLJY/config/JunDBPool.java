package com.juncsoft.KLJY.config;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.hibernate.Session;

import com.juncsoft.KLJY.user.entity.User;
import com.juncsoft.KLJY.util.DatabaseUtil;

/**
 * 数据库连接池管理类
 * @author Administrator
 *
 */
public class JunDBPool {
	private static DataSource pool;
	static {
		Context context = null;
		try{
			context = new InitialContext();//检索指定的对象
			pool = (DataSource)context.lookup("java:comp/env/jdbc/juncsoftDB");
			if(pool == null){
				System.out.println("current juncsoftDB is an unknown DataSource");
			}
		}catch(NamingException nameEx){
			nameEx.printStackTrace();
		}
	}
	
	public static DataSource getPool(){
		return pool;
	}
	
	public void test(){
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Session session = null;
		try {
//			String sql = "select * from t_user where id = 807";
//			con = DatabaseUtil.getConnection();
//			ps  = con.prepareStatement(sql);
//			rs = ps.executeQuery();
//			if(rs.next()){
			session = DatabaseUtil.getSession();
			User user = (User)session.get(User.class, 807L);
				System.out.println(user.getId());
				System.out.println(user.getName());
//			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
				DatabaseUtil.closeResource(session);
		}
	}
}

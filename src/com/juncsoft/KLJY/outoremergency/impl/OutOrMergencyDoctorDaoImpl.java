package com.juncsoft.KLJY.outoremergency.impl;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.juncsoft.KLJY.outoremergency.dao.OutOrMergencyDoctorDao;
import com.juncsoft.KLJY.outoremergency.entity.OutOrMergencyDoctor;
import com.juncsoft.KLJY.patient.entity.Patient;
import com.juncsoft.KLJY.user.entity.User;
import com.juncsoft.KLJY.util.DatabaseUtil;
import com.juncsoft.KLJY.util.MD5;

/**
 * 加载医生所在的科室
 * @author liugang
 *
 */
public class OutOrMergencyDoctorDaoImpl implements OutOrMergencyDoctorDao {

	public OutOrMergencyDoctor executeHISDoctorByIdnumberAndPassword(
			String idnumber) {
		Connection con = null;
		CallableStatement cstmt = null;
		OutOrMergencyDoctor outOrMergencyDoctor = null;
		ResultSet rs = null;
		Session session = null;
		try {
			con = DatabaseUtil.getConnetion_His();//获取连接HIS的数据连接
			String procSql = "{call tw_hsp_pmpa.EMR.DRINFO(?,?)}";
			cstmt = con.prepareCall(procSql);
			cstmt.setString(1, idnumber);
			cstmt.registerOutParameter(2, oracle.jdbc.OracleTypes.CURSOR);
			cstmt.execute();
			rs = (ResultSet)cstmt.getObject(2);
			
			if(rs.next()){
//				System.out.print(rs.getString("idnumber"));
//				System.out.println(rs.getString("name"));
//				System.out.println(rs.getString("deptcodename"));
				outOrMergencyDoctor = new OutOrMergencyDoctor(rs.getString("idnumber"),rs.getString("name"),rs.getString("password"),rs.getString("buse"),rs.getString("gbipd"),rs.getString("gbjupsu"),rs.getString("deptcode"),rs.getString("deptcodename"),rs.getString("drdept1"),
						rs.getString("drdeptname1"),rs.getString("drdept2"),rs.getString("drdeptname2"),rs.getString("drdept3"),rs.getString("drdeptname3"),rs.getString("drdept4"),rs.getString("drdeptname4"),rs.getString("drdept5"),rs.getString("drdeptname5"),rs.getString("telno"),rs.getString("gbjaejik"),
						rs.getString("drcode"),rs.getString("drgrade"),rs.getString("VESTDEPT"),rs.getString("gbspc"),rs.getString("EMAIL"),rs.getString("REMARK"));
				//此段代码是为了增加医院医生的代码 2011-09-18 start
//				User user = new User();
//				user.setName(rs.getString("name"));
//				user.setAccount(rs.getString("name"));
//				user.setPassword(new MD5().getMD5ofStr("11111111"));
//				user.setValid(1);
//				user.setRole("3");
//				user.setBelong("S002");
//				user.setCreateDate(new Date());
//				user.setModifyDate(new Date());
//				user.setUnkown1(rs.getString("deptcode"));
//				user.setDeptcode(rs.getString("deptcodename"));
//				user.setDeptcodename(rs.getString("drdept1"));
//				user.setDrdept1(rs.getString("drdeptname1"));
//				user.setDrdeptname1(rs.getString("drdept2"));
//				user.setDrdept2(rs.getString("drdeptname2"));
//				user.setDrdeptname2(rs.getString("drdept3"));
//				user.setDrdept3(rs.getString("drdeptname3"));
//				user.setDrdeptname3(rs.getString("drdept4"));
//				user.setDrdept4(rs.getString("drdeptname4"));
//				user.setDrdeptname4(rs.getString("drdept5"));
//				user.setDrdept5(rs.getString("drdeptname5"));
//				user.setDrdeptname5(rs.getString("idnumber"));
//				user.setHisDoctorId(rs.getString("gbjaejik"));
//				user.setGbjaejik(rs.getString("drgrade"));
//				user.setDrgrade(rs.getString("VESTDEPT"));
//				user.setVestdept(rs.getString("gbspc"));
//				user.setGbspc(rs.getString("gbjupsu"));
//				user.setGbjupsu(rs.getString("password"));
//				session = DatabaseUtil.getSession();
//				Transaction tran = session.beginTransaction();
//				session.save(user);
//				tran.commit();
				//此段代码是为了增加医院医生的代码 2011-09-18 start
			}
			return outOrMergencyDoctor;
		} catch (Exception e) {
			throw new RuntimeException("执行查找医生当前科室存储过程出错",e);
		}finally{
			try {
				DatabaseUtil.closeResource(session);
				DatabaseUtil.closeResource(con, cstmt, rs);
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void main(String[] args){
		OutOrMergencyDoctorDaoImpl t = new OutOrMergencyDoctorDaoImpl();
		t.executeHISDoctorByIdnumberAndPassword("001807");
//		t.exex();
	}
}

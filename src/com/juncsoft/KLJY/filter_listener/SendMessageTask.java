
package com.juncsoft.KLJY.filter_listener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimerTask;

import javax.servlet.ServletContext;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.juncsoft.KLJY.InHospitalCase.CheckReport.impl.CopyDataImplforRemote;
import com.juncsoft.KLJY.biomedical.entity.MemberInfo;
import com.juncsoft.KLJY.message.dao.MessageDao;
import com.juncsoft.KLJY.message.entity.Message;
import com.juncsoft.KLJY.patient.dao.PatientDao;
import com.juncsoft.KLJY.patient.entity.Patient;
import com.juncsoft.KLJY.patient.impl.PatientDaoImpl;
import com.juncsoft.KLJY.plan.dao.PlanDao;
import com.juncsoft.KLJY.plan.entity.Plan;
import com.juncsoft.KLJY.plan.entity.PlanItem;
import com.juncsoft.KLJY.plan.impl.PlanDaoImpl;
import com.juncsoft.KLJY.user.entity.User;
import com.juncsoft.KLJY.util.DaoFactory;
import com.juncsoft.KLJY.util.DatabaseUtil;
import com.juncsoft.KLJY.util.DateUtil;
import com.stongnet.sms.http.Sms;
import com.stongnet.sms.http.SmsOperator;

public class SendMessageTask extends TimerTask {
	private static final int C_SCHEDULE_HOUR = 12; //几点发送短信  设置成中午12点
	private static boolean isRunning = false; 
	private ServletContext context = null; 
	private PatientDao dao = new PatientDaoImpl();
	private PlanDao planDao = new PlanDaoImpl();
	private PatientDao patientDao = (PatientDao) DaoFactory.InstanceImplement(PatientDao.class);
	private MessageDao messDao = (MessageDao) DaoFactory.InstanceImplement(MessageDao.class);
	public SendMessageTask(){} 
	  
	public SendMessageTask(ServletContext context){ 
		this.context = context; 
	} 
	public static void main(String[] args){
		Calendar cal = Calendar.getInstance(); 
		
		System.out.println(cal.get(Calendar.HOUR_OF_DAY));
	}
	public void run(){ 
		Calendar cal = Calendar.getInstance(); 
		if (!isRunning){
			if (C_SCHEDULE_HOUR == cal.get(Calendar.HOUR_OF_DAY)) { 
				/*
				 * 每天都执行这一段程序    定时        应在已设定随访误差天数时给予患者短信提醒，
				 * 如计划随访日期为2011-8-29，若随访误差天数设定为7天，则应在2011-8-22给患者自动发送短信提醒其按期来院就诊。
				 * 该功能为系统自动给所有病人如期发送短信。
				 */
				String sql = "select max(pt.crossd) as crossd,p.patientId,pt.planDate from t_planItem pt left join t_plan p on p.id=pt.planId where pt.messageFlag is null or pt.messageFlag<>1 group by p.patientId,pt.planDate"; 
				List planItemList = null;
				try {
					planItemList = planDao.isInPlan(sql);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				Object[] item = null;
				Date planDate = null;
				Session session =null;
				Transaction tran = null;
				try {
					session = DatabaseUtil.getSession();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				tran = session.beginTransaction();
				for(int i=0;i<planItemList.size();i++){
					item = (Object[])planItemList.get(i);
					if(item[0]==null||"".equals(item[0].toString())||item[1]==null||"".equals(item[1].toString())||item[2]==null||"".equals(item[2].toString())){  //如果前后来访天数 或者 病人Id或者 随访日期 有一个为空 则跳出此次循环 
						continue;
					}
					
					int crossDay = Integer.parseInt(item[0].toString());   //获得来访前后天数
					Long patientId = Long.parseLong(item[1].toString()); 
					/*
					 * 下面这段代码要获得会员Id memberNo
					 */
					Long memberNo = null;
					MemberInfo memInfo = null;
				    List memList = null;
					try {
						memList = dao.findMemberNoPatientId(patientId.toString(),session);
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
							 if(memList==null||memList.size()==0){  //如果该病人不是会员   则不给他发短信
								 continue;
							 }else if(memList!=null&&memList.size()>0){
							 memberNo = Long.parseLong(memList.get(0).toString());  //获得会员Id 标识
							 try {
								memInfo = patientDao.findMemberInfoByMemNo(memberNo.toString(),session);
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}  //获得会员实体
							 }
					//--------------------------------cheng jiangyu 获得会员实体结束
					
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
					try {
						planDate = sdf.parse(item[2].toString());
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}  //获得该条随访项目的访问日期
					String planDateStr = sdf.format(planDate);
					Date noticeDate = DateUtil.getBeforeAfterDate(planDateStr,crossDay);  //  获得该提醒的日期
					Date date = new Date();
					if(date.compareTo(noticeDate)<0||date.compareTo(noticeDate)==0){  //如果今天在该提醒的日期之前
							if (memInfo.getPatient()!=null&&memInfo.getPatient().getMobilePhone()!= null && memInfo.getPatient().getMobilePhone().length() > 0) {
								Sms sms = new Sms();
								sms.phone = memInfo.getPatient().getMobilePhone();
								String[] dateArr = planDateStr.split("-");
								sms.content = "温馨提示：为了您的身体健康，责任小组医师为您制定了随访计划，建议您于"+dateArr[0]+"年"+dateArr[1]+"月"+dateArr[2]+"日来院复诊。北京佑安医院。"	;							
								SmsOperator smsOp = SmsOperator.getInstance();
								smsOp.sendSms("101100-SJB-HUAX-566466", "ALQPPJJI",new Sms[] { sms });
								
								/*
								 * 每发一条短信  向短信表中插入一条数据
								 */
								Message message = new Message();
								message.setMessageContent(sms.content);
								message.setMessageDate(date);
								message.setMemberInfo(memInfo); 
								message.setUser(null); //发送人是系统  先置为空
								try {
									messDao.addMessage(message);
								} catch (Exception e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							} 
						}
			  }
				String updateSql ="update t_planItem set messageFlag=1 where exists(select pt.id from t_planItem pt left join t_plan p on pt.planId=p.id,(select p.patientId,pt.planDate from t_planItem pt left join t_plan p on p.id=pt.planId where pt.messageFlag is null or pt.messageFlag<>1  group by p.patientId,pt.planDate) m where p.patientId=m.patientId and pt.planDate=m.planDate)";
				try {
					planDao.updateModuleItem(updateSql);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}   //发送完短信后将该随访项目的短信提醒标识置1
				isRunning = false;
				context.log("发送短信执行结束"); 
			}
		}else{ 
			context.log("上一次任务执行还未结束"); 
		} 
	}
	
}

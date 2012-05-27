package com.juncsoft.KLJY.planhis.services.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.juncsoft.KLJY.patient.dao.PatientDao;
import com.juncsoft.KLJY.patient.entity.Patient;
import com.juncsoft.KLJY.patient.impl.PatientDaoImpl;
import com.juncsoft.KLJY.plan.entity.Plan;
import com.juncsoft.KLJY.plan.entity.PlanItem;
import com.juncsoft.KLJY.planhis.dao.PlanHisDao;
import com.juncsoft.KLJY.planhis.dao.impl.PlanHisDaoImpl;
import com.juncsoft.KLJY.planhis.services.PlanHisServices;
import com.juncsoft.KLJY.planhis.util.NearPlan;

public class PlanHisServicesImpl implements PlanHisServices {

	private PatientDao patientdao = new PatientDaoImpl();
	private PlanHisDao phdao = new PlanHisDaoImpl();

	/**
	 * 此处建立NearPlanlist 传递到Action处 苏勇
	 */
	public List<NearPlan> getNearPlanitemsByPId(String tenId) throws Exception {
		// 1.调用patient 由10位编码获取pid
		Patient p = patientdao.findByPatientId(tenId);
		
		//List<Plan> plans = phdao.findPlanByPlanIdAndTime("",p.getId());
		//2.根据病人的id获取到对应于此病人前后六个月的计划列表
		List<Plan> plansAgo = phdao.findPlanByPlanIdAndTime("前",p.getId());
		List<Plan> plansAfter = phdao.findPlanByPlanIdAndTime("后",p.getId());
		
		//3.根据当前时间，确定当前的随访计划（因为当前时间表示的是服务器时间，所以不可能为已经过去的时间，故不会出现当前计划前后都是已经执行的随访计划的情况）
		//（1）判断Ago的最后一次的计划是否执行了,如果执行了，则不用显示。by 容明才
		Long planid1 = 0L;//当前随访计划的id
		Long planid2 = 0L;//当前随访计划的id
		Long planid3 = 0L;//当前随访计划的id
		
		if(plansAgo==null||plansAgo.size()==0){//planAgo没有计划的情况
			if (plansAfter!=null&&plansAfter.size()>0) {
				planid1 = plansAfter.get(0).getId();
				if(plansAfter.size()>1){
				
					planid2 = plansAfter.get(1).getId();
					
				}
			}
		}else if (plansAgo!=null&&plansAgo.size()>0) {//plansAgo有计划且已经执行了的情况
			boolean flag = false ;//判断标志：false表示未执行，true表示已经执行
			Long planId = plansAgo.get(plansAgo.size()-1).getId();//获取最后一个plan的id
			List<PlanItem> pis = phdao.findPlanItemByPlanIdAndTime(planId);//获取对应于此planId的PlanItem
			for(PlanItem pi:pis){// 遍历pis，如果找到时间不为空的planitem 则说明最后一次plan已经执行过，则不需要显示
				if(pi.getVisitDate()!=null){
					flag  = true ;
					break;
				}
			}
			if (flag==true&&plansAfter!=null&&plansAfter.size()>0) {
				planid1 = plansAfter.get(0).getId();
				if(plansAfter.size()>1){
					
					planid2 = plansAfter.get(1).getId();
					
				}
			}else if(flag==false&&plansAfter!=null&&plansAfter.size()>0){
				Long dayGap0 = Math.abs((new Date().getTime() / 86400000)
						- (plansAgo.get(plansAgo.size()-1).getBeginDate()).getTime() / 86400000);
				Long dayGap1 = Math.abs((new Date().getTime() / 86400000)
						- (plansAfter.get(0).getBeginDate()).getTime() / 86400000);
				
				if(dayGap0>dayGap1){
					planid1=plansAgo.get(plansAgo.size()-1).getId();
					planid2=plansAfter.get(0).getId();
					planid3=plansAfter.get(1).getId();
					
				}else{
					planid1=plansAgo.get(plansAgo.size()-1).getId();
					planid2=plansAfter.get(0).getId();
					
				}
			}
		}
		
		/**
		List<Plan> plansBackup = new ArrayList(Arrays.asList(new Object[plans.size()]));
	//	plansBackup.addAll(plans);
		Collections.copy(plansBackup, plans);
		for(Plan planOld :plans){
			Plan planNew= new Plan();
			planNew= planOld;
			plansBackup.add(planNew);
			
			
			System.out.println(planNew.getId());
		}
		
		
		// 对list进行排序
		ComparatorTime comparator = new ComparatorTime();
		Collections.sort(plansBackup, comparator);
		for(Plan pla:plansBackup){
			System.out.println(pla.getId());
		}
		// 获取到排序后的list：plansBackup，并获取到最近的一次的PlanId
		Long planid = plansBackup.get(0).getId();
		**/
		List<Plan> plans= new ArrayList<Plan>();
		plans.addAll(plansAgo);
		plans.addAll(plansAfter);
		List<NearPlan> nps = new ArrayList<NearPlan>();
		for(Plan plan:plans){
			NearPlan np = new NearPlan ();
			List<PlanItem> planItems = phdao.findPlanItemByPlanIdAndTime(plan.getId());
			np.setPlanItems(planItems);
			np.setPlanDate(plan.getBeginDate());
			np.setVisitDate(plan.getModifyDate());
			
			if(plan.getId() == planid1||plan.getId()==planid2||plan.getId()==planid3){
				np.setCanChange(true);
			}else{
				np.setCanChange(false);
			}
			
			nps.add(np);
			
		}
		
		return nps;
		/*
		Map<Integer, List<PlanItem>> pimap = new HashMap();
		int j = 0;
		List<PlanItem> planItems = new ArrayList<PlanItem>();
		for (Plan plan : plans) {
			planItems = phdao.findPlanItemByPlanIdAndTime(plan.getId());
			pimap.put(j, planItems);
			j++;
		}

		List<PlanItem> planItemBackup = planItems;
		// 此处循环 加入与现在时间差
		int i = 0;
		Map<Integer, Long> map = new HashMap<Integer, Long>();
		
		 * Date nowdate = new Date(); DateFormat df = new
		 * SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		 
		// Date nowDate = df.parse(new Date());
		// 计算日期差 放到一个map中
		for (PlanItem pi : planItemBackup) {
			Date planDate = pi.getPlanDate();
			Long dayGap = Math.abs((new Date().getTime() / 86400000)
					- (planDate.getTime() / 86400000));

			map.put(i, dayGap);
			i++;
		}
		// 对这个map 遍历 对value排序，找到最小的一个

		Map<String, Integer> keyfreqs = new HashMap<String, Integer>();

		List arrayList = new ArrayList(map.entrySet());
		Collections.sort(arrayList, new TariffComparator.TariffMapComparator());
		int nearNumber = 0;
		for (Iterator it = arrayList.iterator(); it.hasNext();) {
			Map.Entry entry = (Map.Entry) it.next();
			nearNumber = Integer.parseInt(entry.getKey().toString());
		}

		planItems.get(nearNumber - 1).setCanChange("Y");
		for (PlanItem planitem : planItems) {

			Date planDate = planitem.getPlanDate();

		}*/
	}

}

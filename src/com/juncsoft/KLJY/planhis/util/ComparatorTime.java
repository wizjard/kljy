package com.juncsoft.KLJY.planhis.util;

import java.util.Comparator;
import java.util.Date;
import com.juncsoft.KLJY.plan.entity.Plan;

public class ComparatorTime implements Comparator {

	public int compare(Object arg0, Object arg1) {
		int flag = 0;
		try {
			Plan plan0 = (Plan) arg0;
			Long dayGap0 = Math.abs((new Date().getTime() / 86400000)
					- (plan0.getBeginDate()).getTime() / 86400000);
			System.out.println(dayGap0);
			Plan plan1 = (Plan) arg1;
			Long dayGap1 = Math.abs((new Date().getTime() / 86400000)
					- (plan1.getBeginDate()).getTime() / 86400000);

			System.out.println(dayGap1);
			// 首先比较

			if (dayGap0 < dayGap1) {
				flag = -1;
			} else if (dayGap0 == dayGap1) {
				// 如果两者的单位价值相等，则重量小的排前面
				flag = 0;
			} else {
				flag = 1;
			}

			// flag=dayGap0.compareTo(dayGap1);
		} catch (Exception e) {

		}
		return flag;

	}
}
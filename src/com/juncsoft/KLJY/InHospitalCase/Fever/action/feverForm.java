package com.juncsoft.KLJY.InHospitalCase.Fever.action;

import org.apache.struts.action.ActionForm;

import com.juncsoft.KLJY.InHospitalCase.Fever.entity.TInHspFeverFeverHistoryIllness;

public class feverForm extends ActionForm{

	/**
	 * 发热西医现病史
	 */
	private TInHspFeverFeverHistoryIllness feverIllness =new TInHspFeverFeverHistoryIllness();

	public TInHspFeverFeverHistoryIllness getFeverIllness() {
		return feverIllness;
	}

	public void setFeverIllness(TInHspFeverFeverHistoryIllness feverIllness) {
		this.feverIllness = feverIllness;
	}
	
}

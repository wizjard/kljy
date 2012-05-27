package com.juncsoft.KLJY.message.dao;

import java.util.List;

import org.hibernate.Session;

import com.juncsoft.KLJY.message.entity.Message;

import net.sf.json.JSONObject;

public interface MessageDao {
   public JSONObject getMessageByDid(Integer start, Integer limit,Long doctorId, String search, String flag, String orderBy, String descOrasc) throws Exception;
   public List<Message> getMessageByDid(Long doctorId, String search, String flag, String orderBy, String descOrasc) throws Exception;
   
   public JSONObject getMessageByManageDecode(Integer start, Integer limit,String doctorId, String search, String flag, String orderBy, String descOrasc) throws Exception;
   public List<Message> getMessageByManageDecode(String deptCode, String search, String flag, String orderBy, String descOrasc) throws Exception;
   public List<Message> getMessageByIds(String ids) throws Exception;
   
   public boolean addMessage(Message message) throws Exception ;
   
   public boolean addMessage(Message message,Session session)throws Exception ;
}

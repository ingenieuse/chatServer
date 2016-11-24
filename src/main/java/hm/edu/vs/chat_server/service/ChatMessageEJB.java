package hm.edu.vs.chat_server.service;

import javax.ejb.Stateless;

@Stateless
public class ChatMessageEJB {
	
	//persistence.xml, JPA
//	@PersistenceUnit(unitName="ingo")
//	EntityManager em;

	public void saveChatMessage(String object){
		System.out.println("Save to database here - text changed: "+object);
		//em.persist(arg0);
	
	}
	
}

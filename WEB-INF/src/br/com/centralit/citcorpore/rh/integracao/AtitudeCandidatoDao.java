package br.com.centralit.citcorpore.rh.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.rh.bean.AtitudeCandidatoDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.util.Constantes;

public class AtitudeCandidatoDao extends CrudDaoDefaultImpl {
	public AtitudeCandidatoDao() {
		super(Constantes.getValue("DATABASE_ALIAS"), null);
	}
	public Collection getFields() {
		Collection listFields = new ArrayList();
		listFields.add(new Field("idEntrevista" ,"idEntrevista", true, false, false, false));
		listFields.add(new Field("idAtitudeOrganizacional" ,"idAtitudeOrganizacional", true, false, false, false));
		listFields.add(new Field("avaliacao" ,"avaliacao", false, false, false, false));
		return listFields;
	}
	public String getTableName() {
		return this.getOwner() + "RH_AtitudeCandidato";
	}
	public Collection list() throws Exception {
		return null;
	}

	public Class getBean() {
		return AtitudeCandidatoDTO.class;
	}
	public Collection find(IDto arg0) throws Exception {
		return null;
	}
	public Collection findByIdEntrevista(Integer parm) throws Exception {
		List condicao = new ArrayList();
		List ordenacao = new ArrayList(); 
		condicao.add(new Condition("idEntrevista", "=", parm)); 
		ordenacao.add(new Order("idAtitudeOrganizacional"));
		return super.findByCondition(condicao, ordenacao);
	}
	public void deleteByIdEntrevista(Integer parm) throws Exception {
		List condicao = new ArrayList();
		condicao.add(new Condition("idEntrevista", "=", parm));
		super.deleteByCondition(condicao);
	}
}

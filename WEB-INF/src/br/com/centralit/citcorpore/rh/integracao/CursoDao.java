package br.com.centralit.citcorpore.rh.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.rh.bean.CursoDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.util.Constantes;

//Substituir NomeDaClasseDao
public class CursoDao extends CrudDaoDefaultImpl {
	
	public CursoDao() {
		super(Constantes.getValue("DATABASE_ALIAS"), null);
	}

	public Collection find(IDto arg0) throws Exception {
		return null;
	}

	public Collection getFields() {
		Collection listFields = new ArrayList();
	    

		listFields.add(new Field("idCurso", "idCurso", true, true, false, false));
		listFields.add(new Field("descricao", "descricao", false, false, false, false));
		listFields.add(new Field("detalhe", "detalhe", false, false, false, false));
		
		return listFields;
	}
	
	// Substituir NomeDaTabela
	public String getTableName() {
		return "RH_Curso";
	}

	
	// Substituir NomeDoDTO
	public Class getBean() {
		
		return CursoDTO.class;
	}


    public Collection list() throws Exception {
        List list = new ArrayList();
        list.add(new Order("descricao"));
        return super.list(list);
    }

}

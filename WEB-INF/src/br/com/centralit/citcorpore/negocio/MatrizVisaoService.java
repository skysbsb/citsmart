package br.com.centralit.citcorpore.negocio;
import java.util.Collection;

import br.com.citframework.service.CrudServicePojo;
public interface MatrizVisaoService extends CrudServicePojo {
	public Collection findByIdVisao(Integer parm) throws Exception;
	public void deleteByIdVisao(Integer parm) throws Exception;
}

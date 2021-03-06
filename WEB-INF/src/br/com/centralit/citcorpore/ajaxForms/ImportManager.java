package br.com.centralit.citcorpore.ajaxForms;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citajax.html.HTMLForm;
import br.com.centralit.citajax.util.CitAjaxWebUtil;
import br.com.centralit.citcorpore.bean.ComandoDTO;
import br.com.centralit.citcorpore.bean.ExternalConnectionDTO;
import br.com.centralit.citcorpore.bean.ImportConfigCamposDTO;
import br.com.centralit.citcorpore.bean.ImportConfigDTO;
import br.com.centralit.citcorpore.bean.ImportManagerDTO;
import br.com.centralit.citcorpore.metainfo.bean.CamposObjetoNegocioDTO;
import br.com.centralit.citcorpore.metainfo.bean.ObjetoNegocioDTO;
import br.com.centralit.citcorpore.metainfo.util.JSONUtil;
import br.com.centralit.citcorpore.negocio.ComandoService;
import br.com.centralit.citcorpore.negocio.ExternalConnectionService;
import br.com.centralit.citcorpore.negocio.ImportConfigCamposService;
import br.com.centralit.citcorpore.negocio.ImportConfigService;
import br.com.centralit.citcorpore.util.UtilI18N;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.Reflexao;

public class ImportManager extends AjaxFormAction {

	@Override
	public void load(DocumentHTML document, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ExternalConnectionService externalConnectionService = (ExternalConnectionService) ServiceLocator.getInstance().getService(ExternalConnectionService.class, null);
		Collection colExternalConnections = externalConnectionService.list();
		document.getSelectById("idExternalConnection").addOption("", UtilI18N.internacionaliza(request, "citcorpore.comum.selecione"));
		if (colExternalConnections != null){
			for (Iterator it = colExternalConnections.iterator(); it.hasNext();){
				ExternalConnectionDTO externalConnectionDTO = (ExternalConnectionDTO) it.next();
				document.getSelectById("idExternalConnection").addOption("" + externalConnectionDTO.getIdExternalConnection(), externalConnectionDTO.getNome());
			}
		}
		Collection colObjs = externalConnectionService.getLocalTables();
		document.getSelectById("tabelaDestino").addOption("", UtilI18N.internacionaliza(request, "citcorpore.comum.selecione"));
		if (colObjs != null){
			for (Iterator it = colObjs.iterator(); it.hasNext();){
				ObjetoNegocioDTO objetoNegocioDTO = (ObjetoNegocioDTO)it.next();
				document.getSelectById("tabelaDestino").addOption(objetoNegocioDTO.getNomeTabelaDB(), objetoNegocioDTO.getNomeTabelaDB());
			}
		}		
	}
	
	public void selecionaExternalConnection(DocumentHTML document, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ExternalConnectionService externalConnectionService = (ExternalConnectionService) ServiceLocator.getInstance().getService(ExternalConnectionService.class, null);
		ImportManagerDTO importManagerDTO = (ImportManagerDTO)document.getBean();
		document.getSelectById("tabelaOrigem").removeAllOptions();
		document.getSelectById("tabelaOrigem").addOption("", UtilI18N.internacionaliza(request, "citcorpore.comum.selecione"));
		if (importManagerDTO.getIdExternalConnection() != null){
			Collection colObjs = externalConnectionService.getTables(importManagerDTO.getIdExternalConnection());
			if (colObjs != null){
				for (Iterator it = colObjs.iterator(); it.hasNext();){
					ObjetoNegocioDTO objetoNegocioDTO = (ObjetoNegocioDTO)it.next();
					document.getSelectById("tabelaOrigem").addOption(objetoNegocioDTO.getNomeTabelaDB(), objetoNegocioDTO.getNomeTabelaDB());
				}
			}
		}
		document.getJanelaPopupById("JANELA_AGUARDE_MENU").hide();
	}
	
	public void getOrigemDestinoDados(DocumentHTML document, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ImportManagerDTO importManagerDTO = (ImportManagerDTO)document.getBean();
		ExternalConnectionService externalConnectionService = (ExternalConnectionService) ServiceLocator.getInstance().getService(ExternalConnectionService.class, null);
		if (importManagerDTO.getIdExternalConnection() != null){
			if (importManagerDTO.getTabelaOrigem() != null && !importManagerDTO.getTabelaOrigem().trim().equalsIgnoreCase("")){
				String cmdOrigem = "origem = [";
				Collection colObjs = externalConnectionService.getFieldsTable(importManagerDTO.getIdExternalConnection(), importManagerDTO.getTabelaOrigem());
				if (colObjs != null && colObjs.size() > 0){
					cmdOrigem += "{id:'##AUTO##',text:'--AUTO INCREMENT--'}";
					int i = 0;
					for (Iterator it = colObjs.iterator(); it.hasNext();){
						CamposObjetoNegocioDTO camposObjetoNegocioDTO = (CamposObjetoNegocioDTO)it.next();
						cmdOrigem += ",";
						cmdOrigem += "{id:'" + camposObjetoNegocioDTO.getNomeDB() + "',text:'" + camposObjetoNegocioDTO.getNomeDB() + "'}";
						i++;
					}
				}
				cmdOrigem += "];";
				document.executeScript(cmdOrigem);
			}
			if (importManagerDTO.getTabelaDestino() != null && !importManagerDTO.getTabelaDestino().trim().equalsIgnoreCase("")){
				String cmdDestino = "destino = [";
				Collection colObjs = externalConnectionService.getFieldsLocalTable(importManagerDTO.getTabelaDestino());
				if (colObjs != null && colObjs.size() > 0){
					int i = 0;
					for (Iterator it = colObjs.iterator(); it.hasNext();){
						CamposObjetoNegocioDTO camposObjetoNegocioDTO = (CamposObjetoNegocioDTO)it.next();
						if (i > 0){
							cmdDestino += ",";
						}
						cmdDestino += "{id:'" + camposObjetoNegocioDTO.getNomeDB() + "',text:'" + camposObjetoNegocioDTO.getNomeDB() + "'}";
						i++;
					}
				}
				cmdDestino += "];";
				document.executeScript(cmdDestino);
			}			
		}	
		document.executeScript("geraGrid()");
	}
	
	public void carregarDados(DocumentHTML document, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ExternalConnectionService externalConnectionService = (ExternalConnectionService) ServiceLocator.getInstance().getService(ExternalConnectionService.class, null);
		ImportManagerDTO importManagerDTO = (ImportManagerDTO)document.getBean();
		String jsonMatriz = importManagerDTO.getJsonMatriz();
		HashMap<String, Object> mapMatriz = null;
		try{
			mapMatriz = JSONUtil.convertJsonToMap(jsonMatriz, true);
		}catch (Exception e) {
			System.out.println("jsonMatriz: " + jsonMatriz);
			e.printStackTrace();
			throw e;
		}	
		ArrayList colMatrizTratada = (ArrayList) mapMatriz.get("MATRIZ");	

		externalConnectionService.processImport(importManagerDTO, colMatrizTratada);
		document.alert(UtilI18N.internacionaliza(request, "importmanager.ok"));
		document.getJanelaPopupById("JANELA_AGUARDE_MENU").hide();
	}
	
	public void gravar(DocumentHTML document, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ImportManagerDTO importManagerDTO = (ImportManagerDTO)document.getBean();
		ImportConfigDTO importConfigDTO = new ImportConfigDTO();
		Reflexao.copyPropertyValues(importManagerDTO, importConfigDTO);
		ImportConfigService importConfigService = (ImportConfigService) ServiceLocator.getInstance().getService(ImportConfigService.class, null);
		String jsonMatriz = importManagerDTO.getJsonMatriz();
		HashMap<String, Object> mapMatriz = null;
		try{
			mapMatriz = JSONUtil.convertJsonToMap(jsonMatriz, true);
		}catch (Exception e) {
			System.out.println("jsonMatriz: " + jsonMatriz);
			e.printStackTrace();
			throw e;
		}	
		ArrayList colMatrizTratada = (ArrayList) mapMatriz.get("MATRIZ");	
		Collection colDadosCampos = new ArrayList();
		for (Iterator it = colMatrizTratada.iterator(); it.hasNext();){
			ImportConfigCamposDTO importConfigCamposDTO = new ImportConfigCamposDTO();
			HashMap mapItem = (HashMap)it.next();
			String idOrigem = (String) mapItem.get("IDORIGEM");
			String idDestino = (String) mapItem.get("IDDESTINO");
			String script = (String) mapItem.get("SCRIPT");
			
			importConfigCamposDTO.setOrigem(idOrigem);
			importConfigCamposDTO.setDestino(idDestino);
			importConfigCamposDTO.setScript(script);
			
			colDadosCampos.add(importConfigCamposDTO);
		}
		importConfigDTO.setColDadosCampos(colDadosCampos);
		
		if (importConfigDTO.getIdImportConfig() == null || importConfigDTO.getIdImportConfig().intValue() == 0) {
			importConfigService.create(importConfigDTO);
		    document.alert(UtilI18N.internacionaliza(request, "MSG05"));
		} else {
			importConfigService.update(importConfigDTO);
		    document.alert(UtilI18N.internacionaliza(request, "MSG06"));
		}	
		document.getElementById("idImportConfig").setValue("" + importConfigDTO.getIdImportConfig());
	}
	
    public void restore(DocumentHTML document, HttpServletRequest request,
    	    HttpServletResponse response) throws Exception {
		ImportManagerDTO importManagerDTO = (ImportManagerDTO)document.getBean();
		ImportConfigDTO importConfigDTO = new ImportConfigDTO();
		Reflexao.copyPropertyValues(importManagerDTO, importConfigDTO);
    	ImportConfigService importConfigService = (ImportConfigService) ServiceLocator.getInstance().getService(ImportConfigService.class, null);
    	ImportConfigCamposService importConfigCamposService = (ImportConfigCamposService) ServiceLocator.getInstance().getService(ImportConfigCamposService.class, null);

    	importConfigDTO = (ImportConfigDTO) importConfigService.restore(importConfigDTO);
    	document.executeScript("limpaGrid()");
    	HTMLForm form = document.getForm("form");
    	form.clear();    	
    	if (importConfigDTO != null){
    		importManagerDTO.setIdExternalConnection(importConfigDTO.getIdExternalConnection());
    		importManagerDTO.setTabelaOrigem(importConfigDTO.getTabelaOrigem());
    		importManagerDTO.setTabelaDestino(importConfigDTO.getTabelaDestino());
    		document.setBean(importManagerDTO);
    		selecionaExternalConnection(document, request, response);
    		getOrigemDestinoDados(document, request, response);
    		if (importConfigDTO.getIdImportConfig() != null){
    			Collection colCampos = importConfigCamposService.findByIdImportConfig(importConfigDTO.getIdImportConfig());
    			if (colCampos != null){
	    			for (Iterator it = colCampos.iterator(); it.hasNext();){
	    				ImportConfigCamposDTO importConfigCamposDTO = (ImportConfigCamposDTO)it.next();
	    				document.getElementById("auxData").setValue("");
	    				if (importConfigCamposDTO.getScript() != null && !importConfigCamposDTO.getScript().trim().equalsIgnoreCase("")){
	    					document.executeScript("HTMLUtils.setValue('auxData', ObjectUtils.decodificaAspasApostrofe(ObjectUtils.decodificaEnter('" + CitAjaxWebUtil.codificaAspasApostrofe(CitAjaxWebUtil.codificaEnter(importConfigCamposDTO.getScript())) + "')))");
	    				}
	    				document.executeScript("adicionaLinha('" + importConfigCamposDTO.getOrigem() + "', '" + importConfigCamposDTO.getDestino() + "', document.form.auxData.value)");
	    			}
    			}
    		}
    	}
    	
    	if(importConfigDTO != null){
    		form.setValues(importConfigDTO);
    	}
    	document.getJanelaPopupById("JANELA_AGUARDE_MENU").hide();
    }	

	@Override
	public Class getBeanClass() {
		return ImportManagerDTO.class;
	}

}

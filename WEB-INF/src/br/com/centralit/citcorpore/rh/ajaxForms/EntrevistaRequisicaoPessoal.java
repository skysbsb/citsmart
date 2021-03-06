package br.com.centralit.citcorpore.rh.ajaxForms;

import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citajax.html.HTMLForm;
import br.com.centralit.citajax.html.HTMLSelect;
import br.com.centralit.citajax.html.HTMLTable;
import br.com.centralit.citajax.util.Constantes;
import br.com.centralit.citcorpore.bean.UploadDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.negocio.CargosService;
import br.com.centralit.citcorpore.negocio.CentroResultadoService;
import br.com.centralit.citcorpore.negocio.ProjetoService;
import br.com.centralit.citcorpore.negocio.UnidadeService;
import br.com.centralit.citcorpore.rh.bean.CurriculoDTO;
import br.com.centralit.citcorpore.rh.bean.EntrevistaCandidatoDTO;
import br.com.centralit.citcorpore.rh.bean.RequisicaoPessoalDTO;
import br.com.centralit.citcorpore.rh.bean.TriagemRequisicaoPessoalDTO;
import br.com.centralit.citcorpore.rh.negocio.CurriculoService;
import br.com.centralit.citcorpore.rh.negocio.EntrevistaCandidatoService;
import br.com.centralit.citcorpore.rh.negocio.JornadaEmpregadoService;
import br.com.centralit.citcorpore.rh.negocio.RequisicaoPessoalService;
import br.com.centralit.citcorpore.rh.negocio.TriagemRequisicaoPessoalService;
import br.com.centralit.citcorpore.util.UtilI18N;
import br.com.centralit.citcorpore.util.WebUtil;
import br.com.centralit.citcorpore.util.Enumerados.TipoEntrevista;
import br.com.centralit.citged.bean.ControleGEDDTO;
import br.com.centralit.citged.negocio.ControleGEDService;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.Reflexao;

public class EntrevistaRequisicaoPessoal extends RequisicaoPessoal {

	private String acao = "";

    @SuppressWarnings("rawtypes")
	public void load(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
        UsuarioDTO usuario = WebUtil.getUsuario(request);
        if (usuario == null) {
            document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.sessaoExpirada"));
            document.executeScript("window.location = '" + Constantes.getValue("SERVER_ADDRESS") + request.getContextPath() + "'");
            return;
        }
        
        CargosService cargoService = (CargosService) ServiceLocator.getInstance().getService(CargosService.class, WebUtil.getUsuarioSistema(request));
        HTMLSelect idCargo = (HTMLSelect) document.getSelectById("idCargo");
        idCargo.removeAllOptions();
        idCargo.addOption("", "--- Selecione ---");
        Collection colCargo = cargoService.list();
        if(colCargo != null && !colCargo.isEmpty())
            idCargo.addOptions(colCargo, "idCargo", "nomeCargo", null);
        
        ProjetoService projetoService = (ProjetoService) ServiceLocator.getInstance().getService(ProjetoService.class, WebUtil.getUsuarioSistema(request));
        HTMLSelect idProjeto = (HTMLSelect) document.getSelectById("idProjeto");
        idProjeto.removeAllOptions();
        idProjeto.addOption("", "--- Selecione ---");
        Collection colProjeto = projetoService.list();
        if(colProjeto != null && !colProjeto.isEmpty())
            idProjeto.addOptions(colProjeto, "idProjeto", "nomeProjeto", null);
        
        JornadaEmpregadoService jornadaDeTrabalhoService = (JornadaEmpregadoService) ServiceLocator.getInstance().getService(JornadaEmpregadoService.class, WebUtil.getUsuarioSistema(request));
        HTMLSelect idJornada = (HTMLSelect) document.getSelectById("idJornada");
        idJornada.removeAllOptions();
        idJornada.addOption("", "--- Selecione ---");
        Collection colJornada = jornadaDeTrabalhoService.list();
        if(colJornada != null && !colJornada.isEmpty())
            idJornada.addOptions(colJornada, "idJornada", "identificacao", null);
        
        UnidadeService unidadeService = (UnidadeService) ServiceLocator.getInstance().getService(UnidadeService.class, WebUtil.getUsuarioSistema(request));
        HTMLSelect idUnidade = (HTMLSelect) document.getSelectById("idUnidade");
        idUnidade.removeAllOptions();
        idUnidade.addOption("", "--- Selecione ---");
        Collection colUnidade = unidadeService.listHierarquia();
        if(colUnidade != null && !colUnidade.isEmpty())
            idUnidade.addOptions(colUnidade, "idUnidade", "nome", null);
        
        CentroResultadoService centroResultadoService = (CentroResultadoService) ServiceLocator.getInstance().getService(CentroResultadoService.class, WebUtil.getUsuarioSistema(request));
        HTMLSelect idCentroCusto = (HTMLSelect) document.getSelectById("idCentroCusto");
        idCentroCusto.removeAllOptions();
        idCentroCusto.addOption("", "--- Selecione ---");
        Collection colCentroCusto = centroResultadoService.list();
        if(colCentroCusto != null && !colCentroCusto.isEmpty())
            idCentroCusto.addOptions(colCentroCusto, "idCentroResultado", "nomeCentroResultado", null);
        
        HTMLSelect comboTipo = (HTMLSelect) document.getSelectById("tipoContratacao");
        
        comboTipo.removeAllOptions();
		comboTipo.addOption("",UtilI18N.internacionaliza(request, "citcorpore.comum.selecione"));
		comboTipo.addOption("C", UtilI18N.internacionaliza(request, "colaborador.contratoEmpresaPJ"));
		comboTipo.addOption("E", UtilI18N.internacionaliza(request, "colaborador.empregadoCLT"));
		comboTipo.addOption("T", UtilI18N.internacionaliza(request, "colaborador.estagio"));
		comboTipo.addOption("F", UtilI18N.internacionaliza(request, "colaborador.freeLancer"));
		comboTipo.addOption("O", UtilI18N.internacionaliza(request, "colaborador.outros"));
		comboTipo.addOption("X", UtilI18N.internacionaliza(request, "colaborador.socio"));
		comboTipo.addOption("S", UtilI18N.internacionaliza(request, "colaborador.solicitante"));

		preencherComboPais(document, request, response);
        
        HTMLTable tblCurriculos = document.getTableById("tblCurriculos");
        tblCurriculos.deleteAllRows();
        
        RequisicaoPessoalDTO requisicaoPessoalDto = (RequisicaoPessoalDTO) document.getBean();
        exibeTriagens(document, request, requisicaoPessoalDto);
        restore(document, request, response);
    }
    
    public void exibeTriagens(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
        RequisicaoPessoalDTO requisicaoPessoalDto = (RequisicaoPessoalDTO) document.getBean();
        exibeTriagens(document, request, requisicaoPessoalDto);
    }

    @SuppressWarnings("unchecked")
	private void exibeTriagens(DocumentHTML document, HttpServletRequest request, RequisicaoPessoalDTO requisicaoPessoalDto) throws Exception {
        HTMLTable tblCurriculos = document.getTableById("tblCurriculos");
        tblCurriculos.deleteAllRows();
        
        TriagemRequisicaoPessoalService triagemRequisicaoPessoalService = (TriagemRequisicaoPessoalService) ServiceLocator.getInstance().getService(TriagemRequisicaoPessoalService.class, WebUtil.getUsuarioSistema(request));
        Collection<TriagemRequisicaoPessoalDTO> triagens = triagemRequisicaoPessoalService.findByIdSolicitacaoServicoAndIdTarefa(requisicaoPessoalDto.getIdSolicitacaoServico(), requisicaoPessoalDto.getIdTarefa());
       // adicionarCurriculosPorColecao(document, request, null,triagens);
        
        if (triagens != null && !triagens.isEmpty()) {
        	EntrevistaCandidatoService entrevistaCandidatoService = (EntrevistaCandidatoService) ServiceLocator.getInstance().getService(EntrevistaCandidatoService.class, null);
        	CurriculoService curriculoService = (CurriculoService) ServiceLocator.getInstance().getService(CurriculoService.class, null);
    		ControleGEDService controleGedService = (ControleGEDService) ServiceLocator.getInstance().getService(ControleGEDService.class, null);
        	for (TriagemRequisicaoPessoalDTO triagemDto : triagens) {
				CurriculoDTO curriculoDto = new CurriculoDTO();
				curriculoDto.setIdCurriculo(triagemDto.getIdCurriculo());
	        	curriculoDto = (CurriculoDTO) curriculoService.restore(curriculoDto);
	        	Reflexao.copyPropertyValues(curriculoDto, triagemDto);
	        	if (acao.equals("")) {
	        		if (triagemDto.getTipoEntrevista().equals(TipoEntrevista.RH.name()))
	        			acao = RequisicaoPessoalDTO.ACAO_ENTREVISTA_RH;
	        		else
	        			acao = RequisicaoPessoalDTO.ACAO_ENTREVISTA_GESTOR;
	        	}
	        	EntrevistaCandidatoDTO entrevistaDto = entrevistaCandidatoService.findByIdTriagemAndIdCurriculo(triagemDto.getIdTriagem(), 
																									        	  triagemDto.getIdCurriculo());	
	        	triagemDto.setExisteEntrevistaRH("N");
	        	if (entrevistaDto != null && entrevistaDto.getIdEntrevistadorRH() != null)
	        		triagemDto.setExisteEntrevistaRH("S");
	        	
    			Collection<ControleGEDDTO> colAnexos = controleGedService.listByIdTabelaAndID(ControleGEDDTO.FOTO_CURRICULO, curriculoDto.getIdCurriculo());
    			String caminhoFoto = "";
    			if (colAnexos != null && colAnexos.size() > 0) {
    				List<UploadDTO> colAnexosUploadDTO = (List<UploadDTO>) controleGedService.convertListControleGEDToUploadDTO(colAnexos);
    				caminhoFoto = colAnexosUploadDTO.get(0).getCaminhoRelativo();
    			}
    			triagemDto.setCaminhoFoto(caminhoFoto);
			}
        	
        	adicionarCurriculosPorColecao(document, request, null,triagens);
            
        }   	
    }

	public void restore(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
        UsuarioDTO usuario = WebUtil.getUsuario(request);
        if (usuario == null){
              document.alert("Sess�o expirada! Favor efetuar logon novamente!");
              return;
        }
        
        RequisicaoPessoalDTO requisicaoPessoalDto = (RequisicaoPessoalDTO) document.getBean();
        
        if (requisicaoPessoalDto.getIdSolicitacaoServico() != null) {
        	
        	Integer idTarefa = requisicaoPessoalDto.getIdTarefa();
        	
            RequisicaoPessoalService requisicaoPessoalService = (RequisicaoPessoalService) ServiceLocator.getInstance().getService(RequisicaoPessoalService.class, null);
            requisicaoPessoalDto = (RequisicaoPessoalDTO) requisicaoPessoalService.restore(requisicaoPessoalDto);
            requisicaoPessoalDto.setIdTarefa(idTarefa);
            
            this.preencherComboUfs(document, request, requisicaoPessoalDto);
            this.preencherComboCidade(document, request, requisicaoPessoalDto);
        }
        
        requisicaoPessoalDto.setAcao(getAcao());
        HTMLForm form = document.getForm("form");
        form.setValues(requisicaoPessoalDto);
        
  }
  
    
	 public String getAcao() {
		 return acao; 
	}
	 
		public void adicionarCurriculosPorColecao(DocumentHTML document, HttpServletRequest request, HttpServletResponse response, Collection<TriagemRequisicaoPessoalDTO> col) throws Exception{
			 UsuarioDTO usuario = WebUtil.getUsuario(request);
		        if (usuario == null) {
		            document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.sessaoExpirada"));
		            document.executeScript("window.location = '" + Constantes.getValue("SERVER_ADDRESS") + request.getContextPath() + "'");
		            return;
		        }
		        //document.executeScript("limparDadostableCurriculo()");
		        CurriculoService curriculoService = (CurriculoService) ServiceLocator.getInstance().getService(CurriculoService.class, null);
		        for (TriagemRequisicaoPessoalDTO triagemRequisicaoPessoalDTO : col) {
	        		if (triagemRequisicaoPessoalDTO != null) {
	        			   document.executeScript("incluirCurriculo('" + br.com.citframework.util.WebUtil.serializeObject(triagemRequisicaoPessoalDTO) + "');");
	        			
	        		}
				}    
		        
		}
		
		public void htmlDetalhamenesCurriculosTriados(DocumentHTML document, HttpServletRequest request, HttpServletResponse response, Collection<TriagemRequisicaoPessoalDTO> triagens) throws Exception{
			  for (TriagemRequisicaoPessoalDTO dto: triagens) {
				   StringBuilder str = new StringBuilder();
				   str.append(" <div class='row-fluid'>' ");
				   str.append(" <h3>'"+dto.getNome()+"'</h3> ");
				   str.append(" '<label><b>CPF:</b>&nbsp; '"+dto.getCpfFormatado()+"'</label>' ");
				   str.append(" '<label><b>Data de nascimento:</b>&nbsp;'"+dto.getDataNascimentoStr()+"'</label>' ");
				   str.append(" '<label><b>Estado civil:</b>&nbsp;'"+dto.getEstadoCivilExtenso()+"'</label>' ");
				   str.append(" '</div>' ");
				   
				   
			  }
		}
}

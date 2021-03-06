package br.com.centralit.citgerencial.generateservices.requisicoesProdutos;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import br.com.centralit.citcorpore.bean.EmpregadoDTO;
import br.com.centralit.citcorpore.bean.HistoricoItemRequisicaoDTO;
import br.com.centralit.citcorpore.bean.ItemRequisicaoProdutoDTO;
import br.com.centralit.citcorpore.bean.RequisicaoProdutoDTO;
import br.com.centralit.citcorpore.integracao.EmpregadoDao;
import br.com.centralit.citcorpore.integracao.HistoricoItemRequisicaoDao;
import br.com.centralit.citcorpore.integracao.ItemRequisicaoProdutoDao;
import br.com.centralit.citcorpore.integracao.RequisicaoProdutoDao;
import br.com.centralit.citcorpore.util.Enumerados.SituacaoItemRequisicaoProduto;
import br.com.centralit.citgerencial.bean.GerencialGenerateServiceBuffer;
import br.com.citframework.util.UtilDatas;
import br.com.citframework.util.UtilFormatacao;
import br.com.citframework.util.UtilStrings;

public class CopyOfGenerateServiceRequisicoesProdGeral extends GerencialGenerateServiceBuffer {

	private HashMap novoParametro = new HashMap();
	
	public String execute(HashMap parametersValues, Collection paramtersDefinition) throws Exception {
		
	    Set set = parametersValues.entrySet();
	    Iterator i = set.iterator();

	    while(i.hasNext()){
	      Map.Entry entrada = (Map.Entry)i.next();
	      getNovoParametro().put(entrada.getKey(), entrada.getValue());
	    }
	    
		String datainicial = (String) getNovoParametro().get("PARAM.dataInicial");
		String datafinal = (String) getNovoParametro().get("PARAM.dataFinal");
		
		Date datafim = null;
		Date datainicio = null;
		SimpleDateFormat formatoBanco = new SimpleDateFormat("yyyy-MM-dd");
		
		if (datainicial != null) {
    		try {
    			datainicio = new SimpleDateFormat("dd/MM/yyyy").parse(datainicial);
    		} catch (ParseException e) {
    			e.printStackTrace();
    		}
		}

        if (datafinal != null) {
    		try {
                datafim = new SimpleDateFormat("dd/MM/yyyy").parse(datafinal); 
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
		
        String numero = (String) getNovoParametro().get("PARAM.numero");
        if ((numero == null || numero.trim().length() == 0) && datainicio == null && datafim == null)
            throw new Exception("Per�odo e/ou N�mero devem ser informados");
        
        String produto = (String) getNovoParametro().get("PARAM.produto");
        String situacaoItem = (String) getNovoParametro().get("PARAM.situacaoItem");

        Calendar calendar = Calendar.getInstance();  
		calendar.setTime(datafim);  
		calendar.add(GregorianCalendar.DATE, 1);  
		
        if (datainicio != null)
    		getNovoParametro().put("PARAM.dataInicial", new java.sql.Date(datainicio.getTime()));
        if (datafim != null)
    		getNovoParametro().put("PARAM.dataFinal", new java.sql.Date(datafim.getTime()));		    
		
		Collection col = new ArrayList();
		List listaRetorno = null;
		RequisicaoProdutoDao requisicaoDao = new RequisicaoProdutoDao();
		
	    String strTexto = "";
		Collection<RequisicaoProdutoDTO> colRequisicoes = requisicaoDao.consultaRequisicoesPorCCusto(getNovoParametro());
		if (colRequisicoes != null) {
            strTexto += "<table border='1' width='100%' cellpadding='0' cellspacing='0'>";
            strTexto += "  <tr>";
            strTexto += "    <th width='5%' style='background:#eee;font-size:11px'><b>N�mero</b></th>";
            strTexto += "    <th width='10%' style='background:#eee;font-size:11px'><b>Centro custo</b></th>";
            strTexto += "    <th width='10%' style='background:#eee;font-size:11px'><b>Projeto</b></th>";
            strTexto += "    <th width='10%' style='background:#eee;font-size:11px'><b>Solicitante</b></th>";
            strTexto += "    <th style='background:#eee;font-size:11px'><b>Item</b></th>";
            strTexto += "    <th width='5%' style='background:#eee;font-size:11px'><b>Qtde</b></th>";
            strTexto += "    <th width='8%' style='background:#eee;font-size:11px'><b>Data/Hora</b></th>";
            strTexto += "    <th width='8%' style='background:#eee;font-size:11px'><b>Situa��o</b></th>";
            strTexto += "    <th width='20%' style='background:#eee;font-size:11px'><b>Detalhes</b></th>";
            strTexto += "  </tr>";
		    ItemRequisicaoProdutoDao itemRequisicaoProdutoDao = new ItemRequisicaoProdutoDao();
            HistoricoItemRequisicaoDao historicoItemRequisicaoDao = new HistoricoItemRequisicaoDao();
            EmpregadoDao empregadoDao = new EmpregadoDao();
		    
		    for (RequisicaoProdutoDTO requisicaoDto : colRequisicoes) {
		        Collection<ItemRequisicaoProdutoDTO> colItens = itemRequisicaoProdutoDao.findTodosByIdSolicitacaoServico(requisicaoDto.getIdSolicitacaoServico());
		        if (colItens == null)
		            continue;
		        
		        int contI = 0;
                requisicaoDto.setDataHoraSolicitacao(requisicaoDto.getDataHoraSolicitacao());
                for (ItemRequisicaoProdutoDTO itemDto : colItens) {
                    if (produto != null && produto.trim().length() > 0) {
                        if (itemDto.getDescricaoItem().toUpperCase().indexOf(produto.toUpperCase().trim()) < 0)
                            continue;
                    }
                    if (situacaoItem != null && !situacaoItem.trim().equals("*")) {
                        if (!itemDto.getSituacao().equalsIgnoreCase(situacaoItem))
                            continue;
                    }
                    strTexto += "<tr>";
                    if (contI == 0) {
                        strTexto += "   <td style='padding:2px 5px;font-size:11px'>"+requisicaoDto.getIdSolicitacaoServico()+"</td>";
                        strTexto += "   <td style='padding:2px 5px;font-size:11px'>"+requisicaoDto.getCentroCusto()+"</td>";
                        strTexto += "   <td style='padding:2px 5px;font-size:11px'>"+requisicaoDto.getProjeto()+"</td>";
                        strTexto += "   <td style='padding:2px 5px;font-size:11px'>"+requisicaoDto.getSolicitante()+"</td>";
                    }else
                        strTexto += "   <td colspan='4' style='border:none'>&nbsp;</td>";
                    strTexto += "   <td style='padding:2px 5px;font-size:11px'>"+itemDto.getDescricaoItem()+"</td>";
                    strTexto += "   <td style='padding:2px 5px;font-size:11px'>"+UtilFormatacao.formatDouble(itemDto.getQtdeAprovada(),2)+"</td>";
                    Collection<HistoricoItemRequisicaoDTO> colHistorico = historicoItemRequisicaoDao.findByIdItemRequisicao(itemDto.getIdItemRequisicaoProduto());
                    if (colHistorico == null) {
                        strTexto += "   <td style='padding:2px 5px;font-size:11px'>"+requisicaoDto.getDataHoraSolicitacaoStr()+"</td>";
                        strTexto += "   <td style='padding:2px 5px;font-size:11px'>"+itemDto.getDescrSituacao()+"</td>";
                        strTexto += "   <td>&nbsp;</td>";
                        strTexto += "<tr>";
                        continue;
                    }

                    int contH = 0;
                    for (HistoricoItemRequisicaoDTO historicoDto : colHistorico) {
                        EmpregadoDTO empregadoDto = empregadoDao.restoreByIdEmpregado(historicoDto.getIdResponsavel());
                        String nome = "";
                        if (empregadoDto != null)
                            nome = empregadoDto.getNome();
                        if (contH > 0) {
                            strTexto += "<tr>";
                            strTexto += "   <td colspan='6' style='border:none'>&nbsp;</td>";
                        }
                        strTexto += "   <td style='padding:2px 5px;font-size:11px'>"+UtilDatas.formatTimestamp(historicoDto.getDataHora())+"</td>";
                        strTexto += "   <td style='padding:2px 5px;font-size:11px'>"+SituacaoItemRequisicaoProduto.valueOf(historicoDto.getSituacao()).getDescricao()+"</td>";
                        strTexto += "   <td style='padding:2px 5px;font-size:11px'>"+UtilStrings.nullToVazio(historicoDto.getComplemento())+"</td>";
                        strTexto += "</tr>";
                        contH ++;
                    }
                    strTexto += "</tr>";
                    contI ++;
                }
            }
            strTexto += "</table>";
		}
        return strTexto;
        //return UtilHTML.encodeHTML(strTexto);
	}
	
	public HashMap getNovoParametro() {
		return novoParametro;
	}

	public void setNovoParametro(HashMap novoParametro) {
		this.novoParametro = novoParametro;
	}

}

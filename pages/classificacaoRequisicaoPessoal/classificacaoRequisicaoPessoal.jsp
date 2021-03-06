<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="/tags/cit" prefix="cit"%>
<%@page import="br.com.centralit.citcorpore.util.WebUtil"%>
<%@page import="br.com.centralit.citcorpore.bean.UsuarioDTO"%>
<%@page import="br.com.citframework.dto.Usuario"%>
<%@page import="br.com.centralit.citcorpore.util.CitCorporeConstantes"%>
<%@ taglib uri="/tags/i18n" prefix="i18n"%>


<html>
	<head>	
		<%
	response.setHeader( "Cache-Control", "no-cache");
	response.setHeader( "Pragma", "no-cache");
	response.setDateHeader ( "Expires", -1);
	String id = request.getParameter("id");

	%>
		<%@include file="/novoLayout/common/include/libCabecalho.jsp" %>
		<!-- <script src="../../novoLayout/common/include/js/entrevistaRequisicaoPessoal2.js"></script> -->
		<!-- <link type="text/css" rel="stylesheet" href="../../novoLayout/common/include/css/entrevistaRequisicaoPessoal2.css"/> -->
		<link type="text/css" rel="stylesheet" href="../../novoLayout/common/include/css/template.css"/>
		 <script type="text/javascript" src="<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/cit/objects/ClassificacaoRequisicaoPessoalDTO.js"></script> 
		 <script type="text/javascript" src="<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/cit/objects/TriagemRequisicaoPessoalDTO.js"></script> 
		
		<style type="text/css">
			#modal_fichaMovimentacaoColaborador{
				width: 1009px!important;
				margin-left: -49%; 
				top: 50%!important;
			}
			#modal_fichaMovimentacaoColaborador .modal-body{
				max-height: 590px;
				overflow: auto!important;
			}
		</style>
		<script type="text/javascript">
		
		  
		  function gerarImgExclusaoCurriculo(row, obj) {
		   		
			     row.className = "box-generic";
			     row.cells[4].innerHTML = '<a class="btn-action glyphicons ok_2 btn-success titulo" title="<i18n:message key="rh.admitirCandidato" />" onclick="atualizaClassificaoEntrevista('+row.rowIndex+','+obj.idEntrevistaCurriculo+','+obj.idCurriculo+','+obj.idSolicitacao+', \'A\')"><i></i></a> ' 
		         row.cells[4].innerHTML += '<a class="btn-action glyphicons circle_exclamation_mark btn-warning titulo" title="<i18n:message key="rh.admitirCandidato" />"  onclick="atualizaClassificaoEntrevista('+row.rowIndex+','+obj.idEntrevistaCurriculo+','+obj.idCurriculo+','+obj.idSolicitacao+', \'D\')"><i></i></a> | ' ;
		         row.cells[4].innerHTML += '<a href="#" class="btn-action glyphicons remove_2 btn-danger titulo" title="<i18n:message key="rh.reclassificar" />"  onclick="atualizaClassificaoEntrevista('+row.rowIndex+','+obj.idEntrevistaCurriculo+','+obj.idCurriculo+','+obj.idSolicitacao+', \'R\')"><i></i></a>';
				
		       if (obj.caminhoFoto != "")
		       	row.cells[0].innerHTML = '<img src="' + obj.caminhoFoto + '" border=0 width="128px" />';

		       else
		    	row.cells[0].innerHTML = '<div class="col_100"><img src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/novoLayout/common/theme/images/avatar.jpg" border=0  /></div>';
		  }

		  function abrePopupCurriculo(idCurriculo){
			  	document.getElementById('frameCurriculo').src = URL_SISTEMA+"pages/templateCurriculo/templateCurriculo.load?iframe=true&idCurriculo=" + idCurriculo ;
				$("#modal_curriculo").modal("show");
			}

		  function abrePopupEntrevista(idCurriculo, idTriagem, tipoEntrevista) {
				var idSolicitacao = document.form.idSolicitacaoServico.value;
				document.getElementById('frameFicha').src =URL_SISTEMA+"pages/entrevistaCandidato2/entrevistaCandidato2.load?idCurriculo=" + idCurriculo + '&idTriagem=' + idTriagem + '&tipoEntrevista=' + tipoEntrevista + '&idSolicitacaoServico=' + idSolicitacao;
				$("#modal_ficha").modal("show");
			}

		  function abrePopupHistoricoCandidato(idCurriculo) { 
	    	  /* document.formSugestaoCurriculos.idSolicitacaoServico.value = document.form.idSolicitacaoServico.value; */
	    	  var URL_SISTEMA = '<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/';
	    	  document.getElementById("frameHistoricoCandidato").src =  URL_SISTEMA+'pages/historicoCandidato/historicoCandidato.load?iframe=true&idCurriculo=' + idCurriculo;
	    	  $("#modal_historicoCandidato").modal("show"); 
	      }

		  function incluirCurriculo(curriculoStr) {
		    	var curriculo = new CIT_CurriculoDTO();
		    	curriculo = ObjectUtils.deserializeObject(curriculoStr);
	    
	            
		    	curriculo.idCurriculo = curriculo.idCurriculo;
		    	curriculo.strDetalhamento = '<div class="row-fluid">';
		    	curriculo.strDetalhamento += '<h3>'+curriculo.nome+'</h3>';
		    	curriculo.strDetalhamento += '<label><b><i18n:message key="rh.cpf" />:</b>&nbsp;' +curriculo.cpfFormatado+ '</label>';
		    	curriculo.strDetalhamento += '<label><b><i18n:message key="citcorpore.comum.dataNascimento" />:</b>&nbsp;' +curriculo.dataNascimentoStr+ '</label>';
		    	curriculo.strDetalhamento += '<label><b><i18n:message key="citcorpore.comum.estadoCivil" />:</b>&nbsp;' +curriculo.estadoCivilExtenso+ '</label>';
		    	curriculo.strDetalhamento += '</div>';
		    	curriculo.caminhoFoto = curriculo.caminhoFoto;

	            if(curriculo.classificacaoCandidato == 'D'){
	            	HTMLUtils.addRow('tblCurriculos', null, '', curriculo, ["","strDetalhamento","notaAvaliacaoEntrevista","",""], ["idCurriculo"], '', [gerarImgExclusaoCurriculo, corLinhaCandidatoDesistente], null, null, false);
	            }else if(curriculo.classificacaoCandidato == 'A'){
	            	HTMLUtils.addRow('tblCurriculos', null, '', curriculo, ["","strDetalhamento","notaAvaliacaoEntrevista","",""], ["idCurriculo"], '', [gerarImgExclusaoCurriculo, corLinhaCandidatoAdmitido], null, null, false);
	            }
	            else{
	            	HTMLUtils.addRow('tblCurriculos', null, '', curriculo, ["","strDetalhamento","notaAvaliacaoEntrevista","",""], ["idCurriculo"], '', [gerarImgExclusaoCurriculo], null, null, false);
		            }
		    }
			excluirLinhaTable = function(indice, table) {
				if (indice > 0 && confirm('<i18n:message key="citcorpore.ui.confirmacao.mensagem.Confirma_exclusao" />')) {
					HTMLUtils.deleteRow(table, indice);
				}
			}

			function atualizaClassificaoEntrevista(row, idEntrevista, idCurriculo, idSolicitacao, classificacao){
			/* 	JANELA_AGUARDE_MENU.show(); */
				if(classificacao == 'A'){
					document.getElementById("frameFichaMovimentacaoColaborador").src = URL_SISTEMA+"pages/movimentacaoColaborador/movimentacaoColaborador.load?&iframe=true&idCurriculo=" + idCurriculo + '&idSolicitacaoServico=' + idSolicitacao + '&idEntrevista=' +idEntrevista;
					$('#modal_fichaMovimentacaoColaborador').modal('show');
					
				}
					JANELA_AGUARDE_MENU.show(); 
					document.form.idEntrevistaClassificacao.value = idEntrevista;
					document.form.classificacaoEntrevista.value = classificacao;
					document.form.fireEvent('atualizarClassificaoEntrevista');
				
				
				}
			 corLinhaCandidatoDesistente = function (row, obj){
				   row.cells[0].style.backgroundColor = '#f2bbc4';
				   /* row.style.color = 'white' */
				   row.title = '<i18n:message key="rh.candidatoDesistiuProcessoSeletivo" />'
				   row.cells[3].innerHTML = '<i18n:message key="rh.candidatoDesistiu" />';  
				   row.cells[3].style.color = 'red'
			   }

			 corLinhaCandidatoAdmitido = function(row){
				   row.cells[0].style.backgroundColor = '#e7fde2';
				   /* row.style.color = 'white' */
				   row.cells[3].innerHTML = '<i18n:message key="rh.candidatoAdmitido" />';
				   row.cells[3].style.color = 'green'
				  

				   }
			 
		</script>
	
	</head>
	<cit:janelaAguarde id="JANELA_AGUARDE_MENU"  title="" style="display:none;top:325px;width:300px;left:500px;height:50px;position:absolute;">
</cit:janelaAguarde>
	<body>
		<div class="container-fluid fixed ">
	
			<div id="wrapper">
					
				<!-- Inicio conteudo -->
				
					<div class="box-generic">
						<form name='form' action='<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/classificacaoRequisicaoPessoal/classificacaoRequisicaoPessoal'>
				      	<input type='hidden' name='idSolicitacaoServico' id='idSolicitacaoServico'/>
						<input type='hidden' name='acao' id='acao'/> 
						<input type='hidden' name='idTarefa' id='idTarefa'/>
						<input type='hidden' name='serializeTriagem' id='serializeTriagem'/>
						<input type='hidden' name='preRequisitoEntrevistaGestor' id='preRequisitoEntrevistaGestor' />
						<input type='hidden' name='idEntrevistaClassificacao' id='idEntrevistaClassificacao' />  
						<input type='hidden' name='classificacaoEntrevista' id='classificacaoEntrevista' /> 
						<input type='hidden' name='qtdCandidatosAprovados' id='qtdCandidatosAprovados' /> 
						<div class="row-fluid">
							<div class='span4'>
								<label ><i18n:message key="cargo.cargo" /></label>
								 <select name='idCargo' id='idCargo' class="Valid[Required] Description[Cargo] "  disabled="disabled"></select>
							</div>
							<div class='span4'>
								<label ><i18n:message key="requisicaoPessoal.numeroVagas" /></label>
								<input type='text' name='vagas' size='10' maxlength="100" readonly="readonly"/>
							</div>
							<div class='span4'>
								<label ><i18n:message key="requisicaoPessoal.vagaConfidencial" /></label>
								 <select name='confidencial' class="Valid[Required] Description[Vaga confidencial]" disabled="disabled">
	                             	<option value=" "><i18n:message key="citcorpore.comum.selecione" /></option>
	                                <option value="S"><i18n:message key="citcorpore.comum.sim" /></option>
	                                <option value="N"><i18n:message key="citcorpore.comum.nao" /></option>
                                 </select>
							</div>
						</div>
						<div class="row-fluid">
							<div class='span4'>
								<label  ><i18n:message key="requisicaoPessoal.tipoContratacao" /></label>
								<select name='tipoContratacao' class="Valid[Required] Description[Tipo de contrata��o]" disabled="disabled"></select>
							</div>
							<div class='span4'>
								<label ><i18n:message key="requisicaoPessoal.motivoContratacao" /></label>
								<select name='motivoContratacao' class="Valid[Required] Description[Tipo de contrata��o]" disabled="disabled">
                               	      <option value=''><i18n:message key="citcorpore.comum.selecione" /></option>
                               	      <option value='N'><i18n:message key="rh.novoCargo" /></option>
                               	      <option value='D'><i18n:message key="rh.demissaoPessoal" /></option>
                               	      <option value='A'><i18n:message key="rh.aumentoDemanda" /></option>
                               	      <option value='R'><i18n:message key="rh.requisicaoCliente" /></option>
                                </select>
							</div>
							<div class='span4'>
							 <label ><i18n:message key="requisicaoPessoal.salario" /></label>
								<div class='row-fluid'>
									<div class='span6'>
										<input style="cursor: pointer" type="checkbox" name="salarioACombinar" value="S" disabled="disabled">
		                                <i18n:message key="requisicaoPessoal.salarioACombinar" />
	                                 </div>
	                                 <div class='span6'>
		                                 <div id='divPainelSalario' style='display:block'>
		                                      <div class="input-prepend input-append">
													<span class="add-on"><i18n:message key="citcorpore.comum.simboloMonetario" /></span>
													<input type='text' name='salario' size='10' maxlength="100" class='Format[Moeda] span10' readonly="readonly"/>
											  </div>
			                         	</div>
		                         	</div>
	                         	</div>
							</div>
						</div>
						<div class="row-fluid">
							<div class='span4'>
								<label><i18n:message key="unidade.pais" /></label>
								<select name='idPais' id="idPais"  onchange="document.form.fireEvent('preencherComboUfs');" class="Description[unidade.pais]"  disabled="disabled"></select>
							</div>
							<div class='span4'>
								<label ><i18n:message key="localidade.uf" /></label>
								<select name='idUf' id="idUf" onchange="document.form.fireEvent('preencherComboCidade');" class="Description[uf]"  disabled="disabled"></select>
							</div>
							<div class='span4'>
								<label ><i18n:message key="localidade.cidade" /></label>
								<select id="idCidade" name='idCidade'  class="Description[Cidade]" disabled="disabled"></select>
							</div>
						</div>
						
						<div class="row-fluid">
							<div class='span4'>
								<label ><i18n:message key="Lota��o" /></label>
								<select name='idUnidade' class="Valid[Required] Description[Lota��o]" disabled="disabled"></select>
							</div>
							<div class='span4'>
								<label><i18n:message key="requisicaoPessoal.beneficios" /></label>
								<textarea name='beneficios' rows="4"  class='span8' readonly="readonly"></textarea>
							</div>
							<div class='span4'>
								<label ><i18n:message key="requisicaoPessoal.horarios" /></label>
								<select name='idJornada' id='idJornada'  disabled="disabled"></select>
							</div>
						</div>
						<div class="row-fluid">
							<div class='span4'>
								<label ><i18n:message key="requisicaoPessoal.centroCusto" /></label>
								<select name='idCentroCusto' class="Valid[Required] Description[Centro de custo]" disabled="disabled"></select>
							</div>
							<div class='span4'>
								<label ><i18n:message key="requisicaoPessoal.projeto" /></label>
								<select name='idProjeto' class="Valid[Required] Description[Projeto]" disabled="disabled"></select>
							</div>
							<div class='span4'>
								<label ><i18n:message key="requisicaoPessoal.folgas" /></label>
								<input type='text' name='folgas' size='10' maxlength="100" readonly="readonly"/>
							</div>
						</div>
						
						<div class="widget">
							<div class="widget-head">
								<h4 id="tituloEntrevistasPendentes" class="heading"><i18n:message key="Entrevistas Pendentes"/></h4>
								</div>
							<!-- Tabela de resultados -->
							<div class="widget-body">
								
									 <table id="tblCurriculos" class="table table-bordered table-condensed" style="overflow: auto">
					                       <tr>
					                           <th width="130px"><i18n:message key="rh.foto"/></th>
					                           <th><i18n:message key="rh.detalhes"/></th>
					                           <th width="100px"><i18n:message key="rh.notaAvaliacao"/></th>
					                           <th width="100px"><i18n:message key="rh.notaAvaliacao"/></th>
					                           <th ><i18n:message key="citcorpore.comum.classificacao"/></th>
					                       </tr>
			                   		</table>
								
							</div>
						</div>
					</form>
				  </div>
				  <div class="modal hide fade in" id="modal_ficha" aria-hidden="false">
						<!-- Modal heading -->
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal" aria-hidden="true">�</button>
							<h3></h3>
						</div>
						<!-- // Modal heading END -->
						<!-- Modal body -->
						<div class="modal-body">
							<form name='formSugestaoCurriculos' id='formSugestaoCurriculos'>
		        					<input type='hidden' name='idSolicitacaoServico' id='idSolicitacaoServico'/>
		           				<div>
		           					<iframe width="99%" height="700" id='frameFicha' name='frameFicha' src=""></iframe>
		           				</div>
	    					</form>
						</div>
						<!-- // Modal body END -->
						<!-- Modal footer -->
						<div class="modal-footer">
							<div style="margin: 0;" class="form-actions">
								<a href="#" class="btn " data-dismiss="modal"><i18n:message key="citcorpore.comum.fechar" /></a> 
							</div>
						<!-- // Modal footer END -->
					</div>
				</div>
				
				  <div class="modal hide fade in" id="modal_curriculo" aria-hidden="false">
						<!-- Modal heading -->
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal" aria-hidden="true">�</button>
							<h3></h3>
						</div>
						<!-- // Modal heading END -->
						<!-- Modal body -->
						<div class="modal-body">
							<form name='formCurriculo' id='formCurriculo'>
		        					<input type='hidden' name='idSolicitacaoServico' id='idSolicitacaoSugestao'/>
		           				<div>
		           					<iframe width="99%" height="700" id='frameCurriculo' name='frameCurriculo' src=""></iframe>
		           				</div>
	    					</form>
						</div>
						<!-- // Modal body END -->
						<!-- Modal footer -->
						<div class="modal-footer">
							<div style="margin: 0;" class="form-actions">
								<a href="#" class="btn " data-dismiss="modal"><i18n:message key="citcorpore.comum.fechar" /></a> 
							</div>
						<!-- // Modal footer END -->
					</div>
				</div>
				
				<div class="modal hide fade in" id="modal_historicoCandidato" aria-hidden="false">
						<!-- Modal heading -->
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal" aria-hidden="true">�</button>
							<h3></h3>
						</div>
						<!-- // Modal heading END -->
						<!-- Modal body -->
						<div class="modal-body">
							<form name='formHistoricoCandidato' action='<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/historicoCandidato/historicoCandidato'>
		        					<input type='hidden' name='idSolicitacaoServico' id='idSolicitacaoSugestao'/>
		           				<div>
		           					<iframe width="99%" height="700" id='frameHistoricoCandidato' name='frameHistoricoCandidato' src=""></iframe>
		           				</div>
	    					</form>
						</div>
						<!-- // Modal body END -->
						<!-- Modal footer -->
						<div class="modal-footer">
							<div style="margin: 0;" class="form-actions">
								<a href="#" class="btn " data-dismiss="modal"><i18n:message key="citcorpore.comum.fechar" /></a> 
							</div>
						<!-- // Modal footer END -->
					</div>
				</div>
				
					<div class="modal hide fade in" id="modal_fichaMovimentacaoColaborador" aria-hidden="false">
						<!-- Modal heading -->
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal" aria-hidden="true">�</button>
							<h3></h3>
						</div>
						<!-- // Modal heading END -->
						<!-- Modal body -->
						<div class="modal-body">
							<form name='formMovimentacaoColaborador' action='<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/movimentacaoColaborador/movimentacaoColaborador'>
		        					<input type='hidden' name='idSolicitacaoServico' id='idSolicitacaoSugestao'/>
		           				<div>
		           					<iframe width="99%" height="580" id='frameFichaMovimentacaoColaborador' name='frameHistoricoCandidato' src=""></iframe>
		           				</div>
	    					</form>
						</div>
						<!-- // Modal body END -->
						<!-- Modal footer -->
						<div class="modal-footer">
							<div style="margin: 0;" class="form-actions">
								<a href="#" class="btn " data-dismiss="modal"><i18n:message key="citcorpore.comum.fechar" /></a> 
							</div>
						<!-- // Modal footer END -->
					</div>
				</div>
			
				</div>
				<!--  Fim conteudo-->
				
				 <%@include file="/novoLayout/common/include/libRodape.jsp" %> 
				<script type="text/javascript" src="<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/js/ValidacaoUtils.js"></script>
    			<script type="text/javascript" src="<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/js/PopupManager.js"></script>
   				<script type="text/javascript" src="<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/cit/objects/RequisicaoPessoalDTO.js"></script>
   				
   				<script type="text/javascript" src="<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/cit/objects/CurriculoDTO.js"></script>
				
			</div>
	
	</body>
</html>
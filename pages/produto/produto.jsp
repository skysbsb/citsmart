<%@ taglib uri="/tags/cit" prefix="cit"%>
<%@page import="br.com.centralit.citcorpore.util.WebUtil"%>
<%@page import="br.com.centralit.citcorpore.bean.UsuarioDTO"%>
<%@page import="br.com.citframework.dto.Usuario"%>
<%@ taglib uri="/tags/i18n" prefix="i18n"%>

<!doctype html public "✰">
<html>
<head>
<%
    response.setHeader("Cache-Control", "no-cache");
	response.setHeader("Pragma", "no-cache");
	response.setDateHeader("Expires", -1);
	String iframe = "";
	iframe = request.getParameter("iframe");
%>
<%@include file="/include/security/security.jsp"%>
<script type="text/javascript" src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/js/UploadUtils.js"></script>
<html lang="en-us" class="no-js">
<!--<![endif]-->
<title><i18n:message key="citcorpore.comum.title"/></title>
<%@include file="/include/noCache/noCache.jsp"%>
<%@include file="/include/header.jsp"%>
<%@include file="/include/javaScriptsComuns/javaScriptsComuns.jsp"%>
<script type="text/javascript" src="<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/js/PopupManager.js?nocache=<%=new java.util.Date().toString()%>"></script>
    <link href="<%=br.com.citframework.util.Constantes.getValue("SERVER_ADDRESS")%><%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/novoLayout/common/theme/css/atualiza-antigo.css" rel="stylesheet" />

<script>
	var objTab = null;

	addEvent(window, "load", load, false);
	function load() {
		document.form.afterRestore = function() {
			$('.tabs').tabs('select', 0);
		}
	}

	function LOOKUP_PRODUTO_select(id, desc) {
		document.form.restore({
			idProduto : id
		});
	}
	
	function excluir() {
		if (document.getElementById("idProduto").value != "") {
			if (confirm(i18n_message("citcorpore.comum.deleta"))) {
				document.form.fireEvent("delete");
			}
		}
	}
	
	 var popup; 
	 
	$(function() {
		
		popup = new PopupManager(1100, 800, "<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/");
		
		$("#POPUP_MARCA").dialog({
			autoOpen : false,
			width : 500,
			height : 400,
			modal : true
		});
		
		$("#POPUP_TIPOPRODUTO").dialog({
			autoOpen : false,
			width : 500,
			height : 400,
			modal : true
		});
		
		
		$("#nomeMarca").click(function() {
			$("#POPUP_MARCA").dialog("open");
		});

		$("#nomeProduto").click(function() {
			$("#POPUP_TIPOPRODUTO").dialog("open");
		});

	});
	
	
	function LOOKUP_MARCA_select(id, desc){
		document.form.idMarca.value = id;
		document.form.nomeMarca.value  = desc;
		fecharNomeMarca();
	}
	
	function LOOKUP_TIPOPRODUTO_select(id, desc){
		document.form.idTipoProduto.value = id;
		document.form.nomeProduto.value  = desc;
		fecharTipoProduto();
	}
		

	function fecharNomeMarca(){
		$("#POPUP_MARCA").dialog('close');
	}
	
	function fecharTipoProduto(){
		$("#POPUP_TIPOPRODUTO").dialog('close');
	}
	
	function limparForm(){
		document.getElementById('file_uploadAnexos').value = '';
		document.form.fireEvent("clear");		
	}
	
	function chamaPopupCadastroTipoProduto(){
		popup.abrePopupParms('tipoProduto', '', '');
	}
	
	
</script>
<%//se for chamado por iframe deixa apenas a parte de cadastro da p�gina
			if (iframe != null) {%>
<style>
div#main_container {
	margin: 10px 10px 10px 10px;
}
</style>
<%}%>
</head>
<body>
	<div id="wrapper">
		<%if (iframe == null) {%>
		<%@include file="/include/menu_vertical.jsp"%>
		<%}%>
		<div id="main_container" class="main_container container_16 clearfix">
		<%if (iframe == null) {%>
			<%@include file="/include/menu_horizontal.jsp"%>
			<%}%>

			<div class="flat_area grid_16">
				<h2>
					<i18n:message key="produto.produto" />
				</h2>
			</div>
			<div class="box grid_16 tabs">
				<ul class="tab_header clearfix">
					<li><a href="#tabs-1"><i18n:message key="produto.cadastro" />
					</a></li>
					<li><a href="#tabs-2" class="round_top"><i18n:message key="produto.pesquisa" />
					</a></li>
				</ul>
				<a href="#" class="toggle">&nbsp;</a>
				<div class="toggle_container">
					<div id="tabs-1" class="block">
						<div class="section">
							<form name='form'
								action='<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/produto/produto'>
  								<input type='hidden' name='idCategoria' />
    							<input type='hidden' name='idUnidadeMedida' />
    							<input type='hidden' name='idMarca' />
								<input type='hidden' name='idTipoProduto' /> 
								<input type='hidden' name='idProduto' /> 
								
								<div class="columns clearfix">
								<div class="col_100">
									<div class="col_20">
										<fieldset>
											<label><i18n:message key="citcorpore.comum.codigo" /></label>
											<div>
												<input type='text' name="codigoProduto" maxlength="25" class="Description[produto.codigoProduto]" readonly="readonly"/>
											</div>
										</fieldset>
									</div>
									<div class="col_60">
										<fieldset>
											<label class="campoObrigatorio"><i18n:message key="tipoProduto" /></label>
											<div>
												<input type='text' id="nomeProduto" name="nomeProduto" readonly="readonly" maxlength="256"	class="Valid[Required] Description[tipoProduto] col_100" />
												<%-- <img src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/magnifying_glass.png" onclick='$("#POPUP_TIPOPRODUTO").dialog("open")'>
												<img src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/imagens/add.png" onclick="popup.abrePopup('tipoProduto', '')"> --%>
											</div>
											
										</fieldset>
									</div>
									<div class="col_20">
										<fieldset>
										<label >&nbsp;</label>
											<div>
												<img src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/magnifying_glass.png" onclick='$("#POPUP_TIPOPRODUTO").dialog("open")'>
												<img src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/imagens/add.png" onclick="popup.abrePopup('tipoProduto', '')">
											</div>
											
										</fieldset>
									</div>
								</div>									
								<div class="col_100">
									<div class="col_60">
										<fieldset>
											<label><i18n:message key="produto.complemento" /></label>
											<div>
												<input type='text' id="complemento" name="complemento" maxlength="100" />
											</div>
										</fieldset>
									</div>
									<div class="col_40">
										<div class="col_70">
											<fieldset>
												<label ><i18n:message key="marca" /></label>
												<div>
													<input type='text' id="nomeMarca" name="nomeMarca" readonly="readonly"  maxlength="256"	/>
												</div>
											</fieldset>
										</div>
										<div class="col_30">
												<div style='margin-top:35px !important; padding:0px 0px 0px 0px !important'>
													<img src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/magnifying_glass.png" onclick='$("#POPUP_MARCA").dialog("open")'>
													<img src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/imagens/add.png" 	onclick="popup.abrePopup('marca', '')">
												</div>
										</div>
									</div>	
								</div>									
								<div class="col_100">
									<div class="col_60">
										<fieldset>
											<label ><i18n:message key="produto.modelo" /></label>
											<div>
												<input type='text' name="modelo" maxlength="25" 	/>
											</div>
										</fieldset>
									</div>
									<div class="col_40">
										<fieldset>
											<label ><i18n:message key="citcorpore.comum.valorAproximado" /></label>
											<div>
												<input type='text' name="precoMercado" maxlength="6" class="Format[Moeda] " />
											</div>
										</fieldset>
									</div>	
								</div>									
								<div class="col_100">
									<div class="col_60">
										<fieldset>
											<label><i18n:message key="produto.detalhes" /></label>
											<div>
												<textarea name="detalhes" id="detalhes" rows="5" cols="4"> </textarea> 
											</div>
										</fieldset>
									</div>	
									<div class="col_40">
										<fieldset>
											<label class="campoObrigatorio"><i18n:message key="citcorpore.comum.situacao" /></label>
											<div class="inline" >
												<label > <input type="radio" name="situacao" value="A" 	class="Valid[Required] Description[citcorpore.comum.situacao]" /><i18n:message key="citcorpore.comum.ativo" />  </label>
												<label ><input type='radio' name="situacao" value="I"	class="Valid[Required] Description[citcorpore.comum.situacao]" /><i18n:message key="citcorpore.comum.inativo" /> </label>
											</div>
										</fieldset>
									</div>	
								</div>									
								<div class="col_100">
									<!-- <div class="col_66"> -->
										<!-- <div class="col_100"> -->
												<fieldset>
												<label ><i18n:message key="baseConhecimento.anexos"/></label>	
													<cit:uploadControl  style="height:50%;width:100%; border-bottom:1px solid #DDDDDD ; border-top:1px solid #DDDDDD"  title="<i18n:message key='citcorpore.comum.anexos' />" id="uploadAnexos" form="document.form" action="/pages/upload/upload.load" disabled="false" />
												</fieldset>
											<!-- </div>	 -->
									<!-- </div>	 -->			
								</div>
								<div>
									<div class='col_100'>
										<label>&nbsp;</label>
									</div>
								</div>
								<div class="col_100">
									<fieldset>
										<button type='button' name='btnGravar' class="light"
											onclick='document.form.save();'>
											<img
												src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/pencil.png">
											<span><i18n:message key="citcorpore.comum.gravar" />
											</span>
										</button>
										<button type='button' name='btnLimpar' class="light"
											onclick='limparForm();'>
											<img
												src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/clear.png">
											<span><i18n:message key="citcorpore.comum.limpar" />
											</span>
										</button>
										<button type='button' name='btnExcluir' id="btnExcluir"
											class="light" onclick='excluir();'>
											<img
												src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/trashcan.png">
											<span><i18n:message key="citcorpore.comum.excluir" />
											</span>
										</button>
									</fieldset>
							</div>								
							<div id="popupCadastroRapido" >
			                	<iframe id="frameCadastroRapido" name="frameCadastroRapido" width="100%" height="100%"></iframe>
			            	</div>	
							</form>
						</div>
					</div>
					<div id="tabs-2" class="block">
						<div class="section">
							<i18n:message key="citcorpore.comum.pesquisa" />
							<form name='formPesquisa'>
								<cit:findField formName='formPesquisa'lockupName='LOOKUP_PRODUTO' id='LOOKUP_PRODUTO' top='0'left='0' len='550' heigth='400' javascriptCode='true' htmlCode='true' />
							</form>
						</div>
					</div>
					<!-- ## FIM - AREA DA APLICACAO ## -->
				</div>
			</div>
		</div>
		<!-- Fim da Pagina de Conteudo -->
	</div>
	<%@include file="/include/footer.jsp"%>

	
	<div id="POPUP_MARCA" title="<i18n:message key="marca" />">
		<div class="box grid_16 tabs">
			<div class="toggle_container">
				<div id="tabs-2" class="block">
					<div class="section">
						<form name='formPesquisaMarca' style="width: 440px">
							<cit:findField formName='formPesquisaMarca'
								lockupName='LOOKUP_MARCA' id='LOOKUP_MARCA' top='0'
								left='0' len='550' heigth='400' javascriptCode='true'
								htmlCode='true' />
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>
	
	<div id="POPUP_TIPOPRODUTO" title="<i18n:message key="citcorpore.comum.pesquisa" />">
		<div class="box grid_16 tabs">
			<div class="toggle_container">
				<div id="tabs-2" class="block">
					<div class="section">
						<form name='formPesquisaTipoProduto' style="width: 440px">
							<cit:findField formName='formPesquisaTipoProduto'
								lockupName='LOOKUP_TIPOPRODUTO' id='LOOKUP_TIPOPRODUTO' top='0'
								left='0' len='550' heigth='400' javascriptCode='true'
								htmlCode='true' />
						</form>
					</div>
				</div>
			</div>
		</div>
	</div> 




</body>
</html>

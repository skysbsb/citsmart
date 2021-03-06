<%@ taglib uri="/tags/cit" prefix="cit"%>
<%@page import="br.com.centralit.citcorpore.util.WebUtil"%>
<%@page import="br.com.citframework.util.UtilDatas"%>
<%@page import="br.com.centralit.citcorpore.bean.UsuarioDTO"%>
<%@page import="br.com.centralit.citcorpore.bean.CaracteristicaDTO"%>
<%@ taglib uri="/tags/i18n" prefix="i18n" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<%
			response.setHeader( "Cache-Control", "no-cache");
			response.setHeader( "Pragma", "no-cache");
			response.setDateHeader ( "Expires", -1);
		%>
		<%@include file="/include/security/security.jsp" %>
		<title><i18n:message key="citcorpore.comum.title"/></title>
		<%@include file="/include/noCache/noCache.jsp" %>
		<%@include file="/include/header.jsp" %>
		<%@include file="/include/javaScriptsComuns/javaScriptsComuns.jsp"%>
		
    	<script type="text/javascript" src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/fckeditor/fckeditor.js"></script>		
		<script type="text/javascript">
		
			var objTab = null;
			
			addEvent(window, "load", load, false);
	
			function load() {
				document.form.afterRestore = function() {
				}
			}
		
			function pesquisarProjetos(){
				document.getElementById('divInfoProjetos').innerHTML = i18n_message("resumoProjeto.aguarde");
				document.form.fireEvent('pesquisa');
			}
			
			$(function() {
			});
			
		</script>
		
	</head>
	<body>	
		<div id="wrapper">
			<%@include file="/include/menu_vertical.jsp"%>
			<div id="main_container" class="main_container container_16 clearfix">
				<%@include file="/include/menu_horizontal.jsp"%>
							
				<div class="flat_area grid_16">
					<h2><i18n:message key="resumoProjeto.resumoProjeto"/></h2>						
				</div>
				
				<div class="box grid_16 tabs">
					<ul class="tab_header clearfix">
						<li>
							<a href="#tabs-1"><i18n:message key="resumoProjeto.resumoProjeto"/></a>
						</li>
					</ul>				
					<a href="#" class="toggle">&nbsp;</a>
					<div class="toggle_container">
						<div id="tabs-1" class="block">
							<div class="section">		
								<form name="form" action='<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/resumoProjetos/resumoProjetos'>
									<div class="columns clearfix">
										<div class="columns clearfix">
											<div class="col_50">		
												<fieldset>
													<label class="campoObrigatorio" ><i18n:message key="resumoProjeto.condicaoProjeto"/></label>
														<div>
														  	<select name='condicaoProjeto' id='condicaoProjeto'>
														  		<option value=''><i18n:message key="resumoProjeto.todos"/></option>
														  		<option value='A'><i18n:message key="resumoProjeto.somenteAtivos"/></option>
														  		<option value='I'><i18n:message key="resumoProjeto.somenteInativos"/></option>
														  	</select>
														</div>
												</fieldset>
											</div>
											<div class="col_50">
												<button type='button' name='btnGravar' class="light"  onclick='pesquisarProjetos();'>
													<span><i18n:message key="citcorpore.comum.pesquisar"/></span>
												</button>												
											</div>
										</div>
										
										<div class="columns clearfix">
											<div class="col_100">		
												<div id='divInfoProjetos'>
												</div>
											</div>
										</div>	
										
									</div>
								</form>							
							</div>		
						</div>
					</div>
				</div>		
			</div>
		</div>
		
		<%@include file="/include/footer.jsp"%>
	</body>
</html>
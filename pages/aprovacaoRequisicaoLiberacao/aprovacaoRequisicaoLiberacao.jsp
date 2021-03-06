<%@page import="br.com.citframework.util.Constantes"%>
<%@ taglib uri="/tags/cit" prefix="cit"%>
<%@ taglib uri="/tags/i18n" prefix="i18n" %>
<%@page import="br.com.centralit.citcorpore.util.WebUtil"%>
<%@page import="br.com.centralit.citcorpore.bean.UsuarioDTO"%>
<!doctype html public "">
<html>
<head>
    <script type="text/javascript" src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/fckeditor/fckeditor.js"></script>
    <%
        response.setHeader("Cache-Control", "no-cache"); 
        response.setHeader("Pragma", "no-cache");
        response.setDateHeader("Expires", -1);
        String id = request.getParameter("id");
    %>
    <%@include file="/include/internacionalizacao/internacionalizacao.jsp"%>
    <%@include file="/include/security/security.jsp" %>
    <title><i18n:message key="citcorpore.comum.title"/></title>
    <%@include file="/include/noCache/noCache.jsp" %>
    <%@include file="/include/menu/menuConfig.jsp" %>
    <%@include file="/include/header.jsp" %>
    <%@include file="/include/javaScriptsComuns/javaScriptsComuns.jsp" %>   
    <script type="text/javascript" src="<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/js/ValidacaoUtils.js"></script>
    <script type="text/javascript" src="<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/js/PopupManager.js"></script>
    <script type="text/javascript" src="<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/cit/objects/AprovacaoSolicitacaoServicoDTO.js"></script>

    <script>
        addEvent(window, "load", load, false);
        function load(){        
            document.form.afterLoad = function () {
            	parent.escondeJanelaAguarde(); 
            }    
        }        
        function validar() {
            return document.form.validate();
        }

        function getObjetoSerializado() {
            var obj = new CIT_AprovacaoSolicitacaoServicoDTO();
            HTMLUtils.setValuesObject(document.form, obj);
            return ObjectUtils.serializeObject(obj);
        }

        function configuraJustificativa(aprovacao) {
            document.getElementById('divJustificativa').style.display = 'none';
            if (aprovacao == 'N')
            	document.getElementById('divJustificativa').style.display = 'block';
        }
        
    </script>
<style>
    div#main_container {
        margin: 10px 10px 10px 10px;
        width: 100%;
    }
</style> 
</head>

<body>  
<div class="box grid_16 tabs">
     <form name='form' action='<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/aprovacaoRequisicaoLiberacao/aprovacaoRequisicaoLiberacao'>
            <input type='hidden' name='idRequisicaoLiberacao' id='idRequisicaoLiberacao' /> 
            <input type='hidden' name='editar' id='editar' /> 
            <input type='hidden' name='acao' id='acao'/> 
            <div class="columns clearfix">
			                   <div class="col_100">
			                       <h2 class="section">
			                           <i18n:message key="citcorpore.comum.aprovacao" />
			                       </h2>
			                   </div>
					           <div class="col_100">
					               <div class="col_25" >
					                   <fieldset style="height: 90px;">
					                       <label style='cursor:pointer'><input type='radio' name="aprovacao" value="A" onclick='configuraJustificativa("A");' checked="checked"><i18n:message key="citcorpore.comum.aprovada"/></label><br>
					                       <label style='cursor:pointer'><input type='radio' name="aprovacao" value="N" onclick='configuraJustificativa("N");' ><i18n:message key="citcorpore.comum.naoAprovada"/></label>
					                   </fieldset>
                                   </div>
                                   <div class="col_75">
                                       <fieldset style="height: 90px;">
                                           <label><i18n:message key="citcorpore.comum.observacoes" /></label>
                                           <div>
                                                <textarea id="observacoes" name="observacoes" cols='100' rows='2'></textarea>                               
                                           </div>
                                       </fieldset>
                                   </div>
                                </div>
				                <div id="divJustificativa" class="col_100" style='display:none'>
				                   <div class="col_50">
				                        <fieldset style="height: 90px;">
				                            <label><i18n:message key="itemRequisicaoProduto.justificativa" /></label>
				                            <div>
				                                <select id='idJustificativa'  name='idJustificativa'></select>
				                            </div>
				                        </fieldset>
				                   </div>
				                   <div class="col_50">
				                       <fieldset style="height: 90px;">
				                           <label><i18n:message key="itemRequisicaoProduto.complementoJustificativa" /></label>
				                           <div>
				                                <textarea id="complementoJustificativa" name="complementoJustificativa" cols='60' rows='2'></textarea>                               
				                           </div>
				                       </fieldset>
				                   </div>
				               </div>   
            </div>         
        </form>
</div>

</body>

</html>

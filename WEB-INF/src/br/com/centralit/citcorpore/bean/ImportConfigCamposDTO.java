package br.com.centralit.citcorpore.bean;

import br.com.citframework.dto.IDto;

public class ImportConfigCamposDTO implements IDto {
	private Integer idImportConfigCampo;
	private Integer idImportConfig;
	private String origem;
	private String destino;
	private String script;

	public Integer getIdImportConfigCampo(){
		return this.idImportConfigCampo;
	}
	public void setIdImportConfigCampo(Integer parm){
		this.idImportConfigCampo = parm;
	}

	public Integer getIdImportConfig(){
		return this.idImportConfig;
	}
	public void setIdImportConfig(Integer parm){
		this.idImportConfig = parm;
	}

	public String getOrigem(){
		return this.origem;
	}
	public void setOrigem(String parm){
		this.origem = parm;
	}

	public String getDestino(){
		return this.destino;
	}
	public void setDestino(String parm){
		this.destino = parm;
	}

	public String getScript(){
		return this.script;
	}
	public void setScript(String parm){
		this.script = parm;
	}

}

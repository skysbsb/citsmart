SELECT * FROM citcorpore.CamposObjetoNegocio WHERE idObjetoNegocio IN (SELECT idObjetoNegocio FROM citcorpore.ObjetoNegocio WHERE nomeTabelaDB = 'EMPREGADOS')
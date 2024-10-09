valida:
	$(call teste_variables)
	echo "so mostra se tudo ok"


help: # ajuda
	@echo ""
	@echo " make build - prepara yamls para deploy com kubectl"
	@echo " make build name versao_app versao_chart versao_srv namespace"
	@echo " 	onde:"
	@echo " 		versao_app - versão da aplicação: ex - 1.0.0"
	@echo " 		versao_chart - versão do pacote chart: ex - 0.1.0"
	@echo " 		versao_srv - versão do serviço - primeiro digito da versao: ex - 1"
	@echo ""
	@echo "##### OBS.: deve ser executado antes de subir o código para o gitlab #####"
	@echo ""
	@echo "----------------------------------------------------------------------------------------"
	@echo ""
	@echo " make list - lista todos os pacotes instalados"
	@echo "----------------------------------------------------------------------------------------"
	@echo ""
	@echo " make install - lista pacote helm - uso local somente"
	@echo " make install name versao_app versao_chart versao_srv namespace"
	@echo " 	onde:"
	@echo " 		name - name da aplicação: ex - agenda-api"
	@echo " 		versao_app - versão da aplicação: ex - 1.0.0"
	@echo " 		versao_chart - versão do pacote chart: ex - 0.1.0"
	@echo " 		versao_srv - versão do serviço - primeiro digito da versao: ex - 1"
	@echo "----------------------------------------------------------------------------------------"

build: # monta artefatos para deploy no k8s
	@ $(call teste_variables)
	@sed -i -e "s| APP_VERSION| $(versao_app)|g" -e "s| CHART_VERSION| $(versao_chart)|g" helm/Chart.yaml 
	@sed -i -e "s| SRV_VERSION| $(versao_srv)|g" -e "s| NS| $(namespace)|g" helm/values.yaml
	@helm template ./helm > k8s-deploy/agenda-api.yaml
	@sed -i -e "s| $(versao_app)| APP_VERSION|g" -e "s| $(versao_chart)| CHART_VERSION|g" helm/Chart.yaml 
	@sed -i -e "s| $(versao_srv)| SRV_VERSION|g" -e "s| $(namespace)| NS|g" helm/values.yaml
	@cat k8s-deploy/agenda-api.yaml

install: # instala pacote com helm
	@sed -i -e "s| APP_VERSION| $(versao_app)|g" -e "s| CHART_VERSION| $(versao_chart)|g" helm/Chart.yaml 
	@sed -i "s| SRV_VERSION| $(versao_srv)|g" -e "s| NS| $(namespace)|g" helm/values.yaml
	@helm install $(name)  ./helm 
	@sed -i -e "s| $(versao_app)| APP_VERSION|g" -e "s| $(versao_chart)| CHART_VERSION|g" helm/Chart.yaml 
	@sed -i -e "s| $(versao_srv)| SRV_VERSION|g" -e "s| $(namespace)| NS|g" helm/values.yaml

list: # lista todos os charts instalados
	@helm list -a

.ONESHELL:

define teste_variables
	@echo "it's testing required variables ..."
	@if [ -z $(name) ]; then
		@ echo "variavel name obrigatoria"
		@ exit
	@fi
	@if [ -z $(versao_app) ]; then 
		@ echo "variavel versao_app obrigatoria"
		@ exit
	@fi
	@if [ -z $(versao_chart) ]; then 
		@echo "variavel versao_chart obrigatoria"
		@ exit
	@fi
	@if [ -z $(versao_srv) ]; then 
		@echo "variavel versao_srv obrigatoria" 
		@ exit
	@fi
	@if [ -z $(namespace) ]; then 
		@echo "variavel namespace obrigatoria"
		@ exit
	@fi
endef	


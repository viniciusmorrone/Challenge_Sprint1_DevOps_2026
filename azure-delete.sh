RG="rg-atomo-sprint1"
az group delete --name $RG --yes --no-wait
az group wait --name $RG --deleted
az group exists --name $RG   # false = excluído
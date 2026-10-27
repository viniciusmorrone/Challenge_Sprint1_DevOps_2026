#!/bin/bash
set -e

RG="rg-atomo-sprint1"
LOCATION="northcentralus"
VM_NAME="vm-atomo"
ADMIN="azureuser"
IMAGE="Ubuntu2204"
SIZE="Standard_B2als_v2"
PORT=8080
REPO_URL="https://github.com/viniciusmorrone/Challenge_Sprint1_DevOps_2026.git"

# 1. Grupo de recursos
az group create --name $RG --location $LOCATION -o table

# 2. VM
az vm create \
  --resource-group $RG \
  --name $VM_NAME \
  --image $IMAGE \
  --size $SIZE \
  --admin-username $ADMIN \
  --generate-ssh-keys \
  --public-ip-sku Standard -o table

# 3. Abrir porta da API
az vm open-port --resource-group $RG --name $VM_NAME --port $PORT --priority 1010 -o none

# 4. Comprovar provisionamento
az vm show -d -g $RG -n $VM_NAME \
  --query "{Nome:name, Estado:powerState, IP:publicIps, Local:location}" -o table

# 5. Docker + clone + build + run dentro da VM
az vm run-command invoke -g $RG -n $VM_NAME --command-id RunShellScript \
  --query "value[0].message" -o tsv --scripts "
  apt-get -o DPkg::Lock::Timeout=300 update -y &&
  apt-get -o DPkg::Lock::Timeout=300 install -y docker.io git &&
  systemctl enable --now docker &&
  rm -rf /tmp/app && git clone $REPO_URL /tmp/app &&
  cd /tmp/app &&
  docker build -t atomo-api:1.0 . &&
  (docker rm -f atomo-api 2>/dev/null || true) &&
  docker run -d --name atomo-api -p $PORT:8080 atomo-api:1.0 &&
  docker ps
"

IP=$(az vm show -d -g $RG -n $VM_NAME --query publicIps -o tsv)
echo "Swagger:   http://$IP:$PORT/swagger-ui.html"
echo "Perguntas: http://$IP:$PORT/perguntas"
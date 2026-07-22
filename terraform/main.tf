# Provedor da nuvem AWS
provider "aws" {
  region = "us-east-1"
}

# Criação fictícia da rede (VPC) para o cluster rodar seguro
resource "aws_vpc" "veloz_vpc" {
  cidr_block           = "10.0.0.0/16"
  enable_dns_hostnames = true

  tags = {
    Name = "loja-veloz-vpc"
  }
}

# Declaração do Cluster Kubernetes EKS gerenciado
resource "aws_eks_cluster" "veloz_cluster" {
  name     = "loja-veloz-prod-cluster"
  role_arn = "arn:aws:iam::123456789012:role/eks-service-role"

  vpc_config {
    subnet_ids = ["subnet-12345", "subnet-67890"]
  }

  tags = {
    Environment = "Production"
    Project     = "Loja Veloz"
  }
}
terraform {
  required_version = ">= 1.5"
  required_providers {
    aws = {
      source  = "hashicorp/aws"
      version = "~> 5.0"
    }
    random = {
      source  = "hashicorp/random"
      version = "~> 3.5"
    }
    tls = {
      source  = "hashicorp/tls"
      version = "~> 4.0"
    }
  }
}

provider "aws" {
  region = var.region
}

locals {
  cluster_name = "${var.environment}-${var.project_name}-eks"
  common_tags = {
    Environment = var.environment
    Project     = var.project_name
    ManagedBy   = "Terraform"
  }
}

module "vpc" {
  source = "../../modules/vpc"

  name                 = "${var.environment}-${var.project_name}"
  cidr                 = var.vpc_cidr
  public_subnet_cidrs  = var.public_subnet_cidrs
  private_subnet_cidrs = var.private_subnet_cidrs
  cluster_name         = local.cluster_name
  tags                 = local.common_tags
}

module "eks" {
  source = "../../modules/eks"

  cluster_name                        = local.cluster_name
  kubernetes_version                  = var.kubernetes_version
  vpc_id                              = module.vpc.vpc_id
  private_subnet_ids                  = module.vpc.private_subnet_ids
  public_subnet_ids                   = module.vpc.public_subnet_ids
  cluster_endpoint_public_access_cidrs = var.cluster_endpoint_public_access_cidrs
  
  node_group_desired_size = var.node_group_desired_size
  node_group_max_size     = var.node_group_max_size
  node_group_min_size     = var.node_group_min_size
  node_instance_types     = var.node_instance_types
  node_capacity_type      = var.node_capacity_type
  
  tags = local.common_tags
}

module "rds" {
  source = "../../modules/rds"

  name                    = "${var.environment}-${var.project_name}"
  vpc_id                  = module.vpc.vpc_id
  subnet_ids              = module.vpc.private_subnet_ids
  allowed_security_groups = [module.eks.cluster_security_group_id]
  
  instance_class    = var.rds_instance_class
  engine_version    = var.rds_engine_version
  allocated_storage = var.rds_allocated_storage
  db_name           = var.rds_db_name
  db_username       = var.rds_db_username
  
  skip_final_snapshot = var.rds_skip_final_snapshot
  deletion_protection = var.rds_deletion_protection
  
  tags = local.common_tags
}


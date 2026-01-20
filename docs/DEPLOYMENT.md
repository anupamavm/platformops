# PlatformOps Deployment Guide

Complete guide to deploy user-service to AWS EKS.

## Prerequisites

- AWS CLI configured with credentials
- Terraform >= 1.5
- kubectl
- Helm >= 3
- Docker
- eksctl (for ALB controller setup)

## Step-by-Step Deployment

### 1. Deploy Infrastructure

Deploy VPC, EKS cluster, and RDS database:

```powershell
.\scripts\deploy-infrastructure.ps1 -Environment dev
```

This will:

- Create VPC with public/private subnets
- Provision EKS cluster with managed node groups
- Create RDS PostgreSQL instance
- Set up all necessary IAM roles and security groups

**Time:** ~20-25 minutes

### 2. Configure kubectl

```powershell
aws eks update-kubeconfig --region ap-south-1 --name dev-platformops-eks
```

Verify:

```powershell
kubectl get nodes
```

### 3. Install AWS Load Balancer Controller

```powershell
.\scripts\setup-alb-controller.ps1 -ClusterName dev-platformops-eks
```

Verify:

```powershell
kubectl get deployment -n kube-system aws-load-balancer-controller
```

### 4. Build and Push Docker Image

```powershell
.\scripts\build-and-push-image.ps1 -ServiceName user-service -Tag v1.0.0
```

This will:

- Create ECR repository if needed
- Build Docker image
- Push to ECR

### 5. Deploy Service to EKS

```powershell
.\scripts\deploy-service.ps1 -ServiceName user-service -ImageTag v1.0.0
```

This will:

- Get RDS credentials from Secrets Manager
- Deploy user-service using Helm
- Create Application Load Balancer via Ingress

### 6. Verify Deployment

```powershell
# Check pods
kubectl get pods

# Check service
kubectl get svc

# Check ingress and get ALB URL
kubectl get ingress
```

Get the ALB DNS name from the ingress ADDRESS column.

### 7. Test the Service

```powershell
# Get ALB URL
$AlbUrl = (kubectl get ingress user-service -o jsonpath='{.status.loadBalancer.ingress[0].hostname}')

# Test health endpoint
curl "http://$AlbUrl/actuator/health"
```

## Quick Commands

### Update Service

After making code changes:

```powershell
# Build new image
.\scripts\build-and-push-image.ps1 -ServiceName user-service -Tag v1.0.1

# Deploy update
.\scripts\deploy-service.ps1 -ServiceName user-service -ImageTag v1.0.1
```

### View Logs

```powershell
kubectl logs -l app.kubernetes.io/name=user-service --tail=100 -f
```

### Scale Service

```powershell
kubectl scale deployment user-service --replicas=3
```

### Access RDS

Get credentials:

```powershell
aws secretsmanager get-secret-value --secret-id dev-platformops-db-password --region ap-south-1
```

### Destroy Everything

```powershell
# Delete service first
helm uninstall user-service

# Destroy infrastructure
.\scripts\deploy-infrastructure.ps1 -Environment dev -Destroy -AutoApprove
```

## Troubleshooting

### Pods not starting

```powershell
kubectl describe pod <pod-name>
kubectl logs <pod-name>
```

### Database connection issues

Check security groups and RDS endpoint:

```powershell
kubectl get configmap user-service-config -o yaml
kubectl get secret user-service-db-secret -o yaml
```

### ALB not created

Check ALB controller logs:

```powershell
kubectl logs -n kube-system deployment/aws-load-balancer-controller
```

## Cost Considerations

**Dev Environment Estimated Cost:**

- EKS Cluster: ~$73/month
- EC2 Nodes (2x t3.medium): ~$60/month
- RDS (db.t3.micro): ~$15/month
- ALB: ~$20/month
- NAT Gateway (2x): ~$65/month

**Total: ~$233/month**

To minimize costs:

- Stop nodes when not in use
- Use SPOT instances for dev
- Use single NAT Gateway
- Delete ALB when not testing

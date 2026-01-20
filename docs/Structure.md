platformops/
│
├── .git/
├── .gitignore
├── README.md
│
├── .github/
│ └── workflows/
│ ├── ci-user-service.yml
│ ├── ci-payment-service.yml
│ ├── ci-risk-service.yml
│ ├── security-scans.yml
│ └── release-images.yml
│
├── docs/
│ ├── architecture.md
│ ├── infra.md
│ ├── ci-cd.md
│ ├── security.md
│ ├── observability.md
│ └── runbook.md
│
├── services/ # Application layer (builds images only)
│ ├── user-service/ # Spring Boot
│ │ ├── src/
│ │ ├── pom.xml
│ │ ├── Dockerfile
│ │ ├── Makefile
│ │ ├── application.yml
│ │ └── README.md
│ │
│ ├── payment-service/ # Golang
│ │ ├── cmd/
│ │ ├── internal/
│ │ ├── go.mod
│ │ ├── Dockerfile
│ │ ├── Makefile
│ │ └── README.md
│ │
│ └── risk-service/ # FastAPI
│ ├── app/
│ ├── requirements.txt
│ ├── Dockerfile
│ ├── Makefile
│ └── README.md
│
├── deploy/ # Deployment layer (GitOps-friendly)
│ ├── helm/
│ │ ├── library/ # Shared Helm library (golden path)
│ │ │ ├── Chart.yaml
│ │ │ └── templates/
│ │ │ ├── \_deployment.tpl
│ │ │ ├── \_service.tpl
│ │ │ ├── \_hpa.tpl
│ │ │ ├── \_ingress.tpl
│ │ │ └── \_security.tpl
│ │ │
│ │ ├── user-service/
│ │ │ ├── Chart.yaml
│ │ │ ├── values.yaml
│ │ │ └── templates/
│ │ │ └── deployment.yaml
│ │ │
│ │ ├── payment-service/
│ │ └── risk-service/
│ │
│ └── environments/
│ ├── dev/
│ │ ├── user-service-values.yaml
│ │ ├── payment-service-values.yaml
│ │ └── risk-service-values.yaml
│ │
│ ├── staging/
│ └── prod/
│
├── platform/ # Cluster-level services
│ ├── ingress/
│ │ └── nginx/
│ │
│ ├── security/
│ │ ├── cert-manager/
│ │ ├── network-policies/
│ │ └── pod-security/
│ │
│ ├── observability/
│ │ ├── prometheus/
│ │ ├── grafana/
│ │ ├── loki/
│ │ └── tempo/
│ │
│ └── autoscaling/
│ └── metrics-server/
│
├── infra/ # Infrastructure as Code
│ ├── terraform/
│ │ ├── modules/
│ │ │ ├── vpc/
│ │ │ ├── eks/
│ │ │ ├── ecr/
│ │ │ ├── rds/
│ │ │ ├── iam/
│ │ │ └── monitoring/
│ │ │
│ │ └── envs/
│ │ ├── dev/
│ │ │ ├── main.tf
│ │ │ ├── variables.tf
│ │ │ └── outputs.tf
│ │ └── prod/
│ │
│ └── ansible/
│ ├── inventories/
│ └── playbooks/
│ ├── bootstrap.yml
│ ├── install-helm.yml
│ ├── install-argocd.yml
│ └── install-tools.yml
│
├── gitops/ # Optional but powerful
│ ├── argocd/
│ │ ├── app-of-apps.yaml
│ │ └── applications/
│ │ ├── user-service.yaml
│ │ ├── payment-service.yaml
│ │ └── risk-service.yaml
│
├── docker-compose.yml # Local development
│
└── scripts/
├── build-all.sh
├── deploy-dev.sh
└── destroy-dev.sh

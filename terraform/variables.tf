variable "aws_region" {
  type        = string
  default     = "us-east-1"
  description = "AWS Account region"
}

variable "eks_cluster_name" {
  type        = string
  default     = "fiap-tech-challenge-eks-cluster"
  description = "EKS Cluster name"
}

variable "ecr_repository_name" {
  type        = string
  default     = "order-api"
  description = "AWS ECR repository name"
}

variable "server_port" {
  type        = number
  default     = 8090
  description = "Order App server port"
}

variable "rds_instance_name" {
  type        = string
  default     = "order_db"
  description = "Rds instance name"
}

variable "AWS_ACCESS_KEY_ID" {
  type        = string
  description = "aws_access_key_id"
}

variable "AWS_SECRET_ACCESS_KEY" {
  type        = string
  description = "aws_secret_access_key"
}

variable "AWS_SESSION_TOKEN" {
  type        = string
  description = "aws_session_token"
}

variable "secret_name" {
  type        = string
  default     = "tech-challenge-order-database-credentials"
  description = "secret_name"
}

variable "database_port" {
  type        = string
  default     = "5432"
  description = "value of the database port"
}


variable "queue_name_producer_payment" {
  type        = string
  default     = "order-payment"
  description = "Order payment create MQ Queue"
}

variable "queue_name_producer_production" {
  type        = string
  default     = "order-production"
  description = "Order production MQ Queue"
}

variable "queue_name_consumer" {
  type        = string
  default     = "payment-order"
  description = "Payment order consumer MQ Queue"
}

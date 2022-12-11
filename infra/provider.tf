terraform {
  required_providers {
    aws = {
      source  = "hashicorp/aws"
      version = "4.40.0"
    }
  }

  backend "s3" {
    bucket = "1033-terraform-state"
    key    = "1033.state"
    region = "eu-west-1"
  }
}
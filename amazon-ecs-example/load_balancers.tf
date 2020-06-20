resource "aws_lb" "example" {
  name                       = "example"
  load_balancer_type         = "application"
  internal                   = false
  idle_timeout               = 60
  enable_deletion_protection = false
  subnets = [
    aws_subnet.public_0.id,
    aws_subnet.public_1.id,
  ]
  security_groups = [
    aws_security_group.example.id,
  ]
}

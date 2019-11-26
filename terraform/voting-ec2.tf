provider "aws" {
  region = "us-east-1"
}

resource "aws_instance" "voting-ec2" {
  ami           	 = "ami-04b9e92b5572fa0d1"
  instance_type 	 = "${instance.type}"
  vpc_security_group_ids = [aws_security_group.voting-sg.id]
  key_name 		 = "${key.name}"
  
  tags = {
    Name = "voting-application-ec2"
  }
}

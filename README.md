"# cust-mgn" 

1. PREREQUISITE
---------------
Please prepare a computer with jdk1.8, apache-maven-3.2.3(+), eclipse 4(+) and a git client tool installed.

2. HOW TO IMPORT PROJECT INTO YOUR ECLIPSE
------------------------------------------
(1) Using the git client tool, clone the project from https://github.com/Simon-ZhangJ/cust-mgn to your own environment.
(2) Open eclipse IDE and import project cust-mgn using the option of Existing Maven Projects.
(3) Wait a moment, your environment will be prepared.

3. HOW TO RUN THE PROJECT
-------------------------
There are some ways to run the project. I suggest a easiest way for you.

Start up com.simon.Application.main in your eclipse IDE, then you can do some test with a restful client.

4. HOW TO TEST
--------------
You can use either a command line tool or a GUI tool to test the restful APIs.
Below are CURL commands that can access a demo web site of this project deployed on AWS.

(1)Query all email templates

curl -i http://52.64.184.30:9090/cust-mgn/templates

(2)Add an email template

curl -i -H "Content-type:application/json" -X POST -d "{\"name\":\"test template\",\"subject\":\"${first_name}!, please validate your email address\",\"body\":\"Click ${email} to validate your email.\"}" http://52.64.184.30:9090/cust-mgn/templates

(3)Activate a template (There can be more than one template and should be a active template to send email)

curl -i -X PUT http://52.64.184.30:9090/cust-mgn/templates/activate?id=2

(4) Update an email template

curl -i -H "Content-type:application/json" -X PUT -d "{\"name\":\"new template\",\"subject\":\"${first_name}!, please validate your email address - new\"}" http://52.64.184.30:9090/cust-mgn/templates/1

(5) Query all customers

curl -i http://52.64.184.30:9090/cust-mgn/custs

(6) Add a customer

curl -i -H "Content-type:application/json" -X POST -d "{\"firstName\":\"Simon\",\"lastName\":\"Yang\",\"emailAddressList\":[{\"email\":\"simon.yang@gmail.com\"}]}" http://52.64.184.30:9090/cust-mgn/custs

(7) Validate the email address of a customer

curl -i -X PUT http://52.64.184.30:9090/cust-mgn/email/verify?id=2c9f875f5f2fa72d015f2facb5ed0000

(8) Update information of a customer

curl -i -H "Content-type:application/json" -X POST -d "{\"firstName\":\"Simon\",\"lastName\":\"Yangon\"}" http://52.64.184.30:9090/cust-mgn/custs/1

(9) Add an email address to a customer

curl -i -H "Content-type:application/json" -X POST -d "{\"email\": \"test1@msn.com\"}" http://52.64.184.30:9090/cust-mgn/custs/1/emails

(10) Delete an email address from a customer

curl -i -H "Content-type:application/json" -X DELETE -d "{\"email\": \"test1@msn.com\"}" http://52.64.184.30:9090/cust-mgn/custs/1/emails

(11) Query customers by name

curl -i http://52.64.184.30:9090/cust-mgn/custs/query?name=si

(12) Query all sent emails

curl -i http://52.64.184.30:9090/cust-mgn/emaillogs

(13) Query a paginated list of all sent emails, ordered from the latest.

curl -i http://52.64.184.30:9090/cust-mgn/emaillogs/page/1

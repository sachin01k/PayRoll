**Payroll Management App**

**Description:** The Employee Payroll Management App simplifies salary processes, automating calculations, tax deductions, and benefits tracking. With an intuitive interface, it facilitates easy data input for generating accurate pay stubs and financial reports. Secure payment options and insightful analytics make payroll administration efficient, reducing errors, and enhancing financial transparency for streamlined business operations.

**A Few Things to Consider Before You Begin**

Before you begin using the app, there are a few changes, configurations, and settings that you must make to run the app.

**Configure Database:** The application uses MySQL as a database. Since the app is hosted on your local machine and doesn’t create memory on your machine automatically, you need to follow some steps to provide memory on your machine manually.
1.	Create a database named “payrolldb” in MySQL or you can create a database with any name you like. But in second case, you will need to put your database name in the JDBC connector URL in database configurations written in application.properties file.
2.	Provide your MySQL username and password in database configurations written in application.properties file.
 
**Configure Port:** Spring Boot runs the app on port 8080 by default. Therefore, the base URL for calling endpoints is http://localhost:8080. So, make sure you don’t change the port. 
Configure SMTP Server: The application has some useful mailing features to automate things. For example, the app sends an email to every newly registered employee containing employee’s details, and OTP to reset passwords. In order to use this feature, you need to configure SMTP settings. However, you don’t have to worry on this part. The app does it by default. But, in case you want to use a different email for sending mails, you can make a few changes in the SMTP server configurations written in the application.properties file. See the picture: 
 
**JDK Compatibility:** The app uses the latest version of JAVA FX for GUI so, make sure you have a minimum version of JDK-20 on your machine to run the app.
So far, you are done with the configurations. Now you can run the app on your machine and use it.

**Configure SMTP Server:** The Application has some useful mailing features to automate things. For example, the app sends an email to every newly registered employee containing employee’s 
details, and OTP to reset passwords. To use this feature, you need to configure SMTP settings. However, you don’t have to worry on this part. The app does it by default. 
But, in case you want to use a different email for sending emails, you can make a few changes in the SMTP server configurations written in the application.properties file. 

**Code Structure**

The business logic of the app is written following the MVC architecture. It has multiple packages to categorize the relevant classes and enums. We have separate packages for model classes, controller, service classes, DTOs, Exceptions, enums, UI, and other utility classes.
Standard naming conventions have been followed in naming the variables, functions and classes that describe their uses and functionalities. 
Comprehensive and detailed in-line and document comments have been added at the code level to better understand the functionalities of functions, what input they take, what output they give and how they achieve things. You can refer to the code for detailed technical documentations.

**Contributors**

Sachin Kamble	 (sachin01k):	
Project design, backend, documentation


Salik Anwar (imSalik-Anwar):	
Project design and structure, backend, documentation


Akshansh Chaudhary (Akshansh3011) :	
Project design, frontend, GUI, documentation


Akkash Karthikeyan (Akkash2000K): 	
Project design, testing, documentation

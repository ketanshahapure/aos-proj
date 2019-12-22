# Advanced Operating Systems Project
Developed a distributed system which implements Service Discovery, Load Balancing, Handling of partial failures, 
Recovery from total failures as part of the coursework using Java and Apache Axis2.
The project consists of four application servers which have multiple services replicated four times. There are two gateway or intermediate
servers which as a backup for one another. These gateway servers accept request from client program and route the requests to application
server using the round robin algorithm.

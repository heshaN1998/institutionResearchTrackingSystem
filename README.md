The project BackEnd RESTAPI System dveloped for institutions.This project is a  real world project,production ready application that demonstrates modern backend development.I included Security,Scalability and also use good architecture to solve actual problems faced by educational institutions in managing research activities.

I used Spring Boot framework to develop this project and Tested project using Postman.Used pure java 17 and database technology is MySql. As a build Tool i used Maven.For the security and authentication i have to use JWT Spring security and JWT Json web token.

PROJECT STRUCTURE-

    reposirory(ENTRY POINT)
        repository{UserRepository/ProjectRepository/DocumentRepository/MilestoneRepository}
    config(DATABASE CONFIGURATION)
        config{DBConfig}
    model/dtos(DATA TRANSFER OBJECTS)
        model{Authentication/user/project/milestone/document}
    controller()
        controller{AuthenticationController/DocumentController/ProjectController/UserController/MilestoneController}
    service(LOGIC LAYER)
        service{AuthenticationService/DocumentService/MilestoneService/ProjectService/UserService}
    common{exception/response}
    config{corsConfig/JwtAuthenticationFilter/JwtTokenProvider/SecurityConfig}
    role{admin/pi/member}

MAIN FEATURES:
 Authentication
 UserManagement
 ProjectManagement
 MilestoneTrackin

Admin can manage all things
pi can create and manage own projects
member can add milestone and documents


    

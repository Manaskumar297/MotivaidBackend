package com.manas.motivaid.motivaid.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.manas.motivaid.motivaid.dto.connection.GetConnectionsResponse;
import com.manas.motivaid.motivaid.enums.ConnectionStatus;
import com.manas.motivaid.motivaid.model.Connection;
import com.manas.motivaid.motivaid.model.Role;
import com.manas.motivaid.motivaid.model.User;
import com.manas.motivaid.motivaid.repository.ConnectionRepository;
import com.manas.motivaid.motivaid.repository.RoleRepository;
import com.manas.motivaid.motivaid.repository.UserRepository;

@Service
public class ConnectionService {
	
	@Autowired
	private ConnectionRepository connectionRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private AuthService authService;
	
	
	public List<GetConnectionsResponse> getAllUsers(){
		User currentUser=authService.getAuthenticatedUser();
		
				List<User>users=userRepository.findAll();
			
				List<Connection> connectionStatus=connectionRepository.findByFromUserOrToUser(currentUser, currentUser);
				List<GetConnectionsResponse> response = new ArrayList<>();
				 for (User user : users) {

			            // Skip logged-in user
			            if (user.getId().equals(currentUser.getId())) {
			                continue;
			            }

			            String status = "NONE";

			            for (Connection connection :connectionStatus ) {
			                System.out.println("connectionStatusData1");

			                // Current user sent request
			                if (connection.getFromUser().getId().equals(currentUser.getId())
			                        && connection.getToUser().getId().equals(user.getId())) {
				                System.out.println("connectionStatusData2");


			                    if (connection.getStatus() == ConnectionStatus.PENDING) {
					                System.out.println("connectionStatusData3");

			                        status = "REQUEST_SENT";
			                    }

			                    else if (connection.getStatus() == ConnectionStatus.ACCEPTED) {
					                System.out.println("connectionStatusData4");

			                        status = "CONNECTED";
			                    }
			                }

			                // Current user received request
			                else if (connection.getToUser().getId().equals(currentUser.getId())
			                        && connection.getFromUser().getId().equals(user.getId())) {
				                System.out.println("connectionStatusData5");


			                    if (connection.getStatus() == ConnectionStatus.PENDING) {
					                System.out.println("connectionStatusData6");

			                        status = "REQUEST_RECEIVED";
			                    }

			                    else if (connection.getStatus() == ConnectionStatus.ACCEPTED) {
					                System.out.println("connectionStatusData7");

			                        status = "CONNECTED";
			                    }
			                }
			                System.out.println("connectionStatusData"+status.toString());
			            }

			            GetConnectionsResponse dto = new GetConnectionsResponse();

			            dto.setUser_id(user.getId());
			            dto.setUser_role(user.getRoles().iterator()
			                    .next()
			                    .getName());
			            dto.setFirst_name(user.getFirst_name());
			            dto.setLast_name(user.getLast_name());
			            dto.setConnection_status(status);

			            response.add(dto);
			        }

			        return response;
		
		
		
	}

}

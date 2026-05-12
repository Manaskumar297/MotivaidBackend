package com.manas.motivaid.motivaid.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.manas.motivaid.motivaid.dto.CommonPaginationRequest;
import com.manas.motivaid.motivaid.dto.CommonPaginationResponse;
import com.manas.motivaid.motivaid.dto.CommonResponse;
import com.manas.motivaid.motivaid.dto.connection.GetConnectionsResponse;
import com.manas.motivaid.motivaid.enums.ConnectionStatus;
import com.manas.motivaid.motivaid.model.Connection;
import com.manas.motivaid.motivaid.model.Role;
import com.manas.motivaid.motivaid.model.User;
import com.manas.motivaid.motivaid.repository.ConnectionRepository;
import com.manas.motivaid.motivaid.repository.RoleRepository;
import com.manas.motivaid.motivaid.repository.UserRepository;
import com.manas.motivaid.motivaid.utils.PaginationUtil;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
	
	
	public CommonPaginationResponse<GetConnectionsResponse> getAllUsers(CommonPaginationRequest commonPaginationRequest){
		
		Pageable pageable=PaginationUtil.createPageable(commonPaginationRequest.getPage(), commonPaginationRequest.getSize());
		User currentUser=authService.getAuthenticatedUser();
		String currentUserRole=currentUser.getRoles().iterator().next().getName();
		
		Page<User> users;
		if (currentUserRole.equals("STUDENT")) {
			
			Role counselorRole= roleRepository.findByName("COUNSELOR")
					.orElseThrow(()-> new ResponseStatusException(HttpStatus.BAD_REQUEST,"Role not found"));
			
			users= userRepository.findByRolesContaining(counselorRole,pageable);
			
		}else if (currentUserRole.equals("COUNSELOR")) {
			users=userRepository.findAll(pageable);
			
		}else {
			users=userRepository.findAll(pageable);
		}
					
				List<Connection> connectionStatus=connectionRepository.findByFromUserOrToUser(currentUser, currentUser);
				
				List<GetConnectionsResponse> response = new ArrayList<>();
				 for (User user : users.getContent() ){

			            // Skip logged-in user
			            if (user.getId().equals(currentUser.getId())) {
			                continue;
			            }

			            String status = "NONE";

			            for (Connection connection :connectionStatus ) {

			                // Current user sent request
			                if (connection.getFromUser().getId().equals(currentUser.getId())
			                        && connection.getToUser().getId().equals(user.getId())) {


			                    if (connection.getStatus() == ConnectionStatus.PENDING) {

			                        status = "REQUEST_SENT";
			                    }

			                    else if (connection.getStatus() == ConnectionStatus.ACCEPTED) {

			                        status = "CONNECTED";
			                    }
			                }

			                // Current user received request
			                else if (connection.getToUser().getId().equals(currentUser.getId())
			                        && connection.getFromUser().getId().equals(user.getId())) {


			                    if (connection.getStatus() == ConnectionStatus.PENDING) {

			                        status = "REQUEST_RECEIVED";
			                    }

			                    else if (connection.getStatus() == ConnectionStatus.ACCEPTED) {

			                        status = "CONNECTED";
			                    }
			                }
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
			CommonPaginationResponse<GetConnectionsResponse> result=new CommonPaginationResponse<GetConnectionsResponse>();
			result.setContent(response);
			result.setPage(users.getNumber());
			result.setSize(users.getSize());
			result.setTotalEiments(users.getTotalElements());
			result.setTotalpages(users.getTotalPages());
			result.setLast(users.isLast());
			       
	return result;
		
	}
	
	public CommonResponse sendConnectionRequest(Long toUserId) {
		User currentUser=authService.getAuthenticatedUser();
		
		User toUser= userRepository.findById(toUserId)
				.orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"User not found."));
		
		if (currentUser.getId().equals(toUser.getId())) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"You cannot connect with yoursel");
			
		}
		
		Optional<Connection> extistingConnection=connectionRepository.findByFromUserAndToUserOrFromUserAndToUser(currentUser, toUser,toUser,currentUser);
		
		if (extistingConnection.isPresent()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Connection already exists");
			
		}
		
		Connection connection=new Connection();
		connection.setFromUser(currentUser);
		connection.setToUser(toUser);
		connection.setStatus(ConnectionStatus.PENDING);
		connectionRepository.save(connection);
		
		return CommonResponse.success("Connection reuest sent sucessfully");
		
		
		}
	
	public CommonResponse acceptConnectionRequest(Long fromUserId) {
		User currentUser=authService.getAuthenticatedUser();
		
		User fromUser=userRepository.findById(fromUserId)
				.orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"User not found"));
		
		Connection connection=connectionRepository.findOptionalByFromUserAndToUser(fromUser, currentUser)
				.orElseThrow(()-> new ResponseStatusException(HttpStatus.BAD_REQUEST,"Connection no found."));
		
		if (connection.getStatus()==ConnectionStatus.ACCEPTED) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Connection already accepted");
		}
		connection.setStatus(ConnectionStatus.ACCEPTED);
		connectionRepository.save(connection);
		return CommonResponse.success("Connection request accepted sucessfully");
	}

	public CommonPaginationResponse<GetConnectionsResponse>getAllConnectionRequest(CommonPaginationRequest commonPaginationRequest){
		
		Pageable pageable=PaginationUtil.createPageable(commonPaginationRequest.getPage(), commonPaginationRequest.getSize());
		
		User currentUser= authService.getAuthenticatedUser();
		
		Page<Connection>connectionPage=connectionRepository.findByToUserAndStatus(currentUser, ConnectionStatus.PENDING, pageable);
		
		List<GetConnectionsResponse> response=new ArrayList<>();
		for(Connection connection:connectionPage.getContent()) {
			User fromUser=connection.getFromUser();
			GetConnectionsResponse dto=new GetConnectionsResponse();
			
			dto.setUser_id(connection.getId());
			dto.setUser_role(fromUser.getRoles().iterator().next().getName());
			dto.setFirst_name(fromUser.getFirst_name());
			dto.setLast_name(fromUser.getLast_name());
			dto.setConnection_status(connection.getStatus().name());
			
			response.add(dto);
		}
		
	CommonPaginationResponse<GetConnectionsResponse> result= new CommonPaginationResponse<GetConnectionsResponse>();
	
	result.setContent(response);
	result.setPage(connectionPage.getNumber());
	result.setSize(connectionPage.getSize());
	result.setTotalEiments(connectionPage.getTotalElements());
	result.setTotalpages(connectionPage.getTotalPages());
	result.setLast(connectionPage.isLast());
	return result;
		
	}

}

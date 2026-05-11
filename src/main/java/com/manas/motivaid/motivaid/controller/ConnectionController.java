package com.manas.motivaid.motivaid.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.manas.motivaid.motivaid.dto.CommonPaginationRequest;
import com.manas.motivaid.motivaid.dto.CommonPaginationResponse;
import com.manas.motivaid.motivaid.dto.connection.GetConnectionsResponse;
import com.manas.motivaid.motivaid.service.ConnectionService;

@RestController
@RequestMapping("api/user/connections")
public class ConnectionController {
	
	@Autowired
    private ConnectionService connectionService;	
	@GetMapping("/get-all-connections")
	public CommonPaginationResponse<GetConnectionsResponse>  getAllConnections(@RequestBody CommonPaginationRequest commonPaginationRequest) {
		return connectionService.getAllUsers(commonPaginationRequest);
		
	}
	
	
	
}

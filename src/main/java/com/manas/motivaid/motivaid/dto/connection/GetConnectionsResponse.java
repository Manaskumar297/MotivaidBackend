package com.manas.motivaid.motivaid.dto.connection;


import lombok.Data;

@Data
public class GetConnectionsResponse {
	
	private Long user_id;
	private String user_role ;
	private String first_name;
	private String last_name;
	private String profile_img;
	private String connection_status;
	
}

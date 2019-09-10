package com.scb.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.scb.entity.Student;

@RestController
public class AdminController {
	
	@Autowired
	private RestTemplate restTemplate;
	
	@PostMapping("/create")
	public ResponseEntity<String> create(@RequestBody Student student){
		final String uri ="http://localhost:2022/student-app/student/create";
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<Object> reqEntity = new HttpEntity<Object>(student,headers);
		ResponseEntity<String> responseEntity=restTemplate.postForEntity(uri, reqEntity, String.class);
		return responseEntity;
		
	}
	
	@PostMapping("/update")
	public ResponseEntity<String> update(@RequestBody Student student){
		final String uri = "http://localhost:2022/student-app/student/update";
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<Object> entityRequest = new HttpEntity<Object>(student,httpHeaders);
		ResponseEntity<String> responseEntity = restTemplate.postForEntity(uri, entityRequest, String.class);
		return responseEntity;
	}
	
	@DeleteMapping("/delete")
	public ResponseEntity<String> delete(@RequestParam("id") Integer id){
		final String uri = "http://localhost:2022/student-app/student/delete?id="+id;
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<Object> entityRequest = new HttpEntity<Object>(id,httpHeaders);
		ResponseEntity<String> responseEntity = restTemplate.exchange(uri, HttpMethod.DELETE, entityRequest,String.class, id);
		return responseEntity;
	}
	
	
	  @GetMapping("searchById") 
	  public ResponseEntity<Student> findStudentById(@RequestParam("id") Integer id){ 
		  final String uri ="http://localhost:2022/student-app/student/searchById?id="+id; 
		  HttpHeaders httpHeaders = new HttpHeaders();
		  httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		  HttpEntity<Object> entityRequest = new HttpEntity<Object>(id,httpHeaders);
		  ResponseEntity<Student> responseEntity = restTemplate.exchange(uri, HttpMethod.GET, entityRequest,Student.class, id);
	      return responseEntity;
	  }
	 
	  @GetMapping("/findAll")
	  public ResponseEntity<?> findAll(@RequestParam("stream") String stream){
		  final String uri ="http://localhost:2022/student-app/student/findAll?stream="+stream;
		  HttpHeaders httpHeaders = new HttpHeaders();
		  httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		/*
		 * HttpEntity<Object> entityRequest = new
		 * HttpEntity<Object>(stream,httpHeaders); ResponseEntity<Object[]>
		 * responseEntity = restTemplate.exchange(uri,HttpMethod.GET,
		 * entityRequest,Object[].class, stream); return new
		 * ResponseEntity<List>(Arrays.asList(responseEntity),HttpStatus.OK);
		 */
		  HttpHeaders headers = new HttpHeaders();
			 headers.setContentType(MediaType.APPLICATION_JSON);
		  MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
		  	 map.add("stream", stream);
		  HttpEntity<MultiValueMap<String, String>> request = new  HttpEntity<MultiValueMap<String, String>>(map,headers);
		  Object[] response = restTemplate.getForObject(uri, Object[].class, request);
			 return new ResponseEntity<List>(Arrays.asList(response),HttpStatus.OK);
	  }
	  
	
	
	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}
	

}

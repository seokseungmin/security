package com.springboot.security.service;

import com.springboot.security.data.dto.SignInResultDto;
import com.springboot.security.data.dto.SignUpResultDto;

public interface SignService {

    SignUpResultDto signup(String id, String password, String name, String role);

    SignInResultDto signin(String id, String password) throws RuntimeException;
}
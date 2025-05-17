package com.system.core.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

// Ajouter ce contrôleur
@RestController
public class HomeController {

  @GetMapping("/")
  public String home() {
    return "Backend operational - " + new Date();
  }

  @GetMapping("/health")
  public ResponseEntity<String> healthCheck() {
    return ResponseEntity.ok("OK");
  }
}

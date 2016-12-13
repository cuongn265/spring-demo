package com.eugene.controller;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.*;
import com.eugene.domain.User;
import com.eugene.repository.UserRepository;
import com.eugene.service.CustomUserDetails;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Eugene on 12/12/2016.
 */
@Controller
public class UserController {

  final
  UserRepository userRepository;

  @Autowired
  public UserController(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @RequestMapping("/profile")
  public String profile(Model model) {
    CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    Long userId = userDetails.getUserId();
    User user = userRepository.findOne(userId);
    model.addAttribute("user", user);
    return "user/profile";
  }

  @RequestMapping(value = "/profile/update", method = RequestMethod.POST)
  public String updateUser(@ModelAttribute User user,
                           @RequestParam("file") MultipartFile file,
                           @RequestParam("name") String name,
                           RedirectAttributes redirectAttributes) {

    AWSCredentials credentials = new ProfileCredentialsProvider().getCredentials();
    AmazonS3 s3client = new AmazonS3Client(credentials);
    String bucketName = "cuongngo-lms";
    boolean exist = false;
    for (Bucket bucket : s3client.listBuckets()) {
      if (bucketName.equals(bucket.getName())) {
        exist = true;
      }
    }
    if (!exist) {
      s3client.createBucket(bucketName);
    }
    try {
      InputStream is = file.getInputStream();
      if (is.available() > 0) {
        s3client.putObject(new PutObjectRequest(bucketName, name, is, new ObjectMetadata()).withCannedAcl(CannedAccessControlList.PublicRead));
        S3Object s3Object = s3client.getObject(new GetObjectRequest(bucketName, name));
        user.setUserImageUrl(s3Object.getObjectContent().getHttpRequest().getURI().toString());
      } else if(user.getUserImageUrl() == null) {
        user.setUserImageUrl("https://cuongngo-lms.s3.amazonaws.com/no-image.jpg");
      }
    } catch (IOException e) {
      e.printStackTrace();
    }

    SecurityContext context = SecurityContextHolder.getContext();
    Authentication auth = context.getAuthentication();
    CustomUserDetails userDetails = (CustomUserDetails) auth.getPrincipal();
    userDetails.setUserImageUrl(user.getUserImageUrl());
    redirectAttributes.addFlashAttribute("message", "Your profile was successfully updated!!");
    userRepository.save(user);
    return "redirect:/profile";
  }
}

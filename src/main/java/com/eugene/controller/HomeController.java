package com.eugene.controller;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.*;
import com.eugene.domain.Course;
import com.eugene.repository.CourseRepository;
import com.eugene.repository.UserRepository;
import com.eugene.service.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.io.*;
import java.security.Principal;
import java.util.List;

/**
 * Created by Eugene on 11/26/2016.
 */
@Controller
public class HomeController {

  private final CourseRepository courseRepository;
  @Autowired
  private final UserRepository userRepository;

  @Autowired
  public HomeController(CourseRepository courseRepository, UserRepository userRepository) {
    this.courseRepository = courseRepository;
    this.userRepository = userRepository;
  }

  @RequestMapping("/")
  public String home(Model model) {
    CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    Long userId = userDetails.getUserId();
    List<Course> courseList = courseRepository.findCourseByUserId(userId);
    model.addAttribute("courseList", courseList);
    return "home";
  }


  @RequestMapping("/course/new")
  public String newCourse(Model model) {
    CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    Long userId = userDetails.getUserId();
    Course course = new Course();
    course.setUser(userDetails);
    model.addAttribute("course", course);
    return "course_form";
  }


  @RequestMapping(value = "/course", method = RequestMethod.POST)
  public String saveCourse(@Valid Course course,
                           BindingResult bindingResult,
                           @RequestParam("file") MultipartFile file,
                           @RequestParam("name") String name) {
    if (bindingResult.hasErrors()) {
      return "course_form";
    }
    AWSCredentials credentials =
      new BasicAWSCredentials("AKIAISUSR5JXVBOYEFGQ", "9LUgeUAWvd9A2m3JZj6D7Nbr9nq0G5vf5SwiJ0bs");
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
      s3client.putObject(new PutObjectRequest(bucketName, name, is, new ObjectMetadata()).withCannedAcl(CannedAccessControlList.PublicRead));
      S3Object s3Object = s3client.getObject(new GetObjectRequest(bucketName, name));
      course.setCourseImageUrl(s3Object.getObjectContent().getHttpRequest().getURI().toString());
      System.out.print(s3Object.getObjectContent().getHttpRequest().getURI().toString());
    } catch (IOException e) {
      e.printStackTrace();
    }

    courseRepository.save(course);
    return "redirect:/courses/" + course.getCourseId();
  }

  @RequestMapping("/courses/{courseId}")
  public String showCourse(@PathVariable Integer courseId, Model model) {
    Course course = courseRepository.findOne(courseId.longValue());
    model.addAttribute("course", course);
    return "course";
  }

  @RequestMapping(value = "/courses/{courseId}/remove", method = RequestMethod.DELETE)
  public String removeCourse(@PathVariable Integer courseId) {
    courseRepository.delete(courseId.longValue());
    return "redirect:/";
  }

  @RequestMapping("/hello")
  public String hello() {
    return "hello";
  }

  @RequestMapping("/login")
  public String login() {
    return "login";
  }

  @RequestMapping("/403")
  public String error() {
    return "403";
  }

  @RequestMapping(value = "/singleUpload")
  public String singleUpload() {
    return "single_upload";
  }

  @RequestMapping(value = "/singleSave", method = RequestMethod.POST)
  public
  @ResponseBody
  String singleSave(@RequestParam("file") MultipartFile file, @RequestParam("desc") String desc) {
    System.out.println("File Description:" + desc);
    String fileName = null;
    if (!file.isEmpty()) {
      try {
        fileName = file.getOriginalFilename();
        byte[] bytes = file.getBytes();
        File location = new File("F:\\Hoc\\HK1-N4\\CNPMM\\final\\upload\\");
        BufferedOutputStream buffStream =
          new BufferedOutputStream(new FileOutputStream(location + fileName));
        buffStream.write(bytes);
        buffStream.close();
        return "You have successfully uploaded " + fileName;
      } catch (Exception e) {
        return "You failed to upload " + fileName + ": " + e.getMessage();
      }
    } else {
      return "Unable to upload. File is empty.";
    }
  }


  @RequestMapping(value = "/singleUploadAWS", method = RequestMethod.POST)
  public String handleFileUploadAWS(@RequestParam("file") MultipartFile file,
                                    @RequestParam("desc") String desc,
                                    RedirectAttributes redirectAttributes) {
    AWSCredentials credentials =
      new BasicAWSCredentials("AKIAISUSR5JXVBOYEFGQ", "9LUgeUAWvd9A2m3JZj6D7Nbr9nq0G5vf5SwiJ0bs");
    AmazonS3 s3client = new AmazonS3Client(credentials);
    String bucketName = "cuongngo.lms";
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

      //
      s3client.putObject(new PutObjectRequest(bucketName, desc, is, new ObjectMetadata()).withCannedAcl(CannedAccessControlList.PublicRead));

      S3Object s3Object = s3client.getObject(new GetObjectRequest(bucketName, desc));

      redirectAttributes.addAttribute("picUrl", s3Object.getObjectContent().getHttpRequest().getURI().toString());
      System.out.print(s3Object.getObjectContent().getHttpRequest().getURI().toString());
    } catch (IOException e) {
      e.printStackTrace();
    }

    return "redirect:/singleUpload";
  }
}

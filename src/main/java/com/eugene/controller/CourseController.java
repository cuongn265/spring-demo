package com.eugene.controller;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.*;
import com.eugene.domain.Course;
import com.eugene.domain.Unit;
import com.eugene.repository.CourseRepository;
import com.eugene.repository.UnitRepository;
import com.eugene.repository.UserRepository;
import com.eugene.service.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * Created by Eugene on 12/4/2016.
 */
@Controller
public class CourseController {
  private final CourseRepository courseRepository;
  private final UserRepository userRepository;
  private final UnitRepository unitRepository;

  @Autowired
  public CourseController(CourseRepository courseRepository, UserRepository userRepository, UnitRepository unitRepository) {
    this.courseRepository = courseRepository;
    this.userRepository = userRepository;
    this.unitRepository = unitRepository;
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
        course.setCourseImageUrl(s3Object.getObjectContent().getHttpRequest().getURI().toString());
      } else {
        course.setCourseImageUrl("https://cuongngo-lms.s3.amazonaws.com/no-image.jpg");
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    System.out.println("COURSE CONTROLLER:" + course);
    courseRepository.save(course);
    return "redirect:/courses/" + course.getCourseId();
  }

  @RequestMapping("/courses/{courseId}")
  public String showCourse(@PathVariable Integer courseId, Model model) {
    Course course = courseRepository.findOne(courseId.longValue());
    List<Unit> units = unitRepository.findAllByCourseOrderByUnitPositionAsc(course);
    for (Unit unit: units) {
      System.out.println("UNIT:" + unit.getUnitName());
    }

    model.addAttribute("course", course);
    model.addAttribute("units", units);
    return "course";
  }

  @RequestMapping(value = "/courses/{courseId}/remove", method = RequestMethod.DELETE)
  public String removeCourse(@PathVariable Integer courseId) {
    courseRepository.delete(courseId.longValue());
    return "redirect:/";
  }
}

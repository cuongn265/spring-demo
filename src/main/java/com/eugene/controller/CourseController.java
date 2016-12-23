package com.eugene.controller;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.*;
import com.eugene.domain.Assignment;
import com.eugene.domain.Course;
import com.eugene.domain.Unit;
import com.eugene.domain.User;
import com.eugene.inter.CreateCourse;
import com.eugene.repository.AssignmentRepository;
import com.eugene.repository.CourseRepository;
import com.eugene.repository.UnitRepository;
import com.eugene.repository.UserRepository;
import com.eugene.service.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.groups.Default;
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
  private final AssignmentRepository assignmentRepository;

  @Autowired
  public CourseController(CourseRepository courseRepository, UserRepository userRepository, UnitRepository unitRepository, AssignmentRepository assignmentRepository) {
    this.courseRepository = courseRepository;
    this.userRepository = userRepository;
    this.unitRepository = unitRepository;
    this.assignmentRepository = assignmentRepository;
  }

  //GET new page
  @RequestMapping("/courses/new")
  public String newCourse(Model model) {
    CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    Long userId = userDetails.getUserId();
    Course course = new Course();
    course.setUser(userDetails);
    model.addAttribute("course", course);
    return "courses/new";
  }

  // POST new page
  @RequestMapping(value = "/course/create", method = RequestMethod.POST)
  public String saveCourse(@ModelAttribute @Validated({CreateCourse.class, Default.class}) Course course,
                           BindingResult bindingResult,
                           @RequestParam("file") MultipartFile file,
                           @RequestParam("name") String name,
                           RedirectAttributes redirectAttributes) {

    if (bindingResult.hasErrors()) {
      return "/courses/new";
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
    uploadFile(s3client, bucketName, file, name, course);

    if (course.getCourseId() == null) {
      redirectAttributes.addFlashAttribute("message", "Course was successfully created!!");
    } else {
      redirectAttributes.addFlashAttribute("message", "Course was successfully updated!!");
    }

    courseRepository.save(course);

    return "redirect:/courses/" + course.getCourseId() + "/weeks/1" ;
  }

  // GET show page
  @RequestMapping("/courses/{courseId}/weeks/{weekId}")
  public String showCourse(@PathVariable Integer courseId, @PathVariable Integer weekId, Model model) {
    Course course = courseRepository.findOne(courseId.longValue());
    Unit unit = unitRepository.findFirstByUnitWeekAndCourse(weekId, course);
    List<Assignment> assignments = assignmentRepository.findAllByUnit(unit);
    model.addAttribute("course", course);
    model.addAttribute("unit", unit);
    model.addAttribute("week", weekId);
    model.addAttribute("assignments", assignments);
    return "courses/show";
  }

  //DELETE page
  //  TODO: Make response body when deleting fail
  @RequestMapping(value = "courses/delete/{courseId}", method = RequestMethod.DELETE,
    produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
  @ResponseBody
  public void deleteCourse(@PathVariable Integer courseId) {
    courseRepository.delete(courseId.longValue());
  }

  //GET edit page
  @RequestMapping("courses/{courseId}/edit")
  public String editCourse(@PathVariable Integer courseId, Model model) {
    Course course = courseRepository.findOne(courseId.longValue());
    model.addAttribute("course", course);
    return "courses/edit";
  }

  //UPDATE
  @RequestMapping(value = "courses/{courseId}/edit", method = RequestMethod.POST)
  public String updateCourse(@ModelAttribute @Validated({Default.class}) Course course,
                             BindingResult bindingResult,
                             @RequestParam("file") MultipartFile file,
                             @RequestParam("name") String name,
                             RedirectAttributes redirectAttributes) {
    if (bindingResult.hasErrors()) {
      return "/courses/edit";
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

    uploadFile(s3client, bucketName, file, name, course);

    if (course.getCourseId() == null) {
      redirectAttributes.addFlashAttribute("message", "Course was successfully created!!");
    } else {
        redirectAttributes.addFlashAttribute("message", "Course was successfully updated!!");
    }

    courseRepository.save(course);

    return "redirect:/courses/" + course.getCourseId() + "/weeks/1";
  }

  private void uploadFile(AmazonS3 s3client, String bucketName, MultipartFile file, String name, Course course) {
    try {
      InputStream is = file.getInputStream();
      if (is.available() > 0) {
        s3client.putObject(new PutObjectRequest(bucketName, name, is, new ObjectMetadata()).withCannedAcl(CannedAccessControlList.PublicRead));
        S3Object s3Object = s3client.getObject(new GetObjectRequest(bucketName, name));
        course.setCourseImageUrl(s3Object.getObjectContent().getHttpRequest().getURI().toString());
      } else if (course.getCourseImageUrl() == null) {
        course.setCourseImageUrl("https://cuongngo-lms.s3.amazonaws.com/no-image.jpg");
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @RequestMapping("/courses/{courseId}/summary")
  public String showCourse(@PathVariable Integer courseId, Model model) {
    Course course = courseRepository.findOne(courseId.longValue());
    User teacher = userRepository.findOne(course.getUser().getUserId());
    model.addAttribute("course", course);
    model.addAttribute("teacher", teacher);
    return "/courses/summary";
  }
}

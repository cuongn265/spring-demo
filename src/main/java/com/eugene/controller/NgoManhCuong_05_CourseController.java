package com.eugene.controller;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.*;
import com.eugene.domain.NgoManhCuong_05_Assignment;
import com.eugene.domain.NgoManhCuong_05_Course;
import com.eugene.domain.NgoManhCuong_05_Unit;
import com.eugene.domain.NgoManhCuong_05_User;
import com.eugene.group.NgoManhCuong_05_CreateCourse;
import com.eugene.repository.NgoManhCuong_05_AssignmentRepository;
import com.eugene.repository.NgoManhCuong_05_CourseRepository;
import com.eugene.repository.NgoManhCuong_05_UnitRepository;
import com.eugene.repository.NgoManhCuong_05_UserRepository;
import com.eugene.service.NgoManhCuong_05_CustomUserDetails;
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
 * Created by Ngô Mạnh Cường on 12/4/2016.
 */

/**
 * Controller cho khóa học
 * Thêm sửa xóa
 */

@Controller
public class NgoManhCuong_05_CourseController {
  private final NgoManhCuong_05_CourseRepository courseRepository;
  private final NgoManhCuong_05_UserRepository userRepository;
  private final NgoManhCuong_05_UnitRepository unitRepository;
  private final NgoManhCuong_05_AssignmentRepository assignmentRepository;

  @Autowired
  public NgoManhCuong_05_CourseController(NgoManhCuong_05_CourseRepository courseRepository, NgoManhCuong_05_UserRepository userRepository, NgoManhCuong_05_UnitRepository unitRepository, NgoManhCuong_05_AssignmentRepository assignmentRepository) {
    this.courseRepository = courseRepository;
    this.userRepository = userRepository;
    this.unitRepository = unitRepository;
    this.assignmentRepository = assignmentRepository;
  }

  /*Gọi trang tạo mới khóa học*/
  @RequestMapping("/courses/new")
  public String newCourse(Model model) {
    NgoManhCuong_05_CustomUserDetails userDetails = (NgoManhCuong_05_CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    Long userId = userDetails.getUserId();
    NgoManhCuong_05_Course course = new NgoManhCuong_05_Course();
    course.setUser(userDetails);
    model.addAttribute("course", course);
    return "courses/new";
  }

  /*Tạo mới khóa học*/
  /*Upload file dừng AWS S3*/
  @RequestMapping(value = "/course/create", method = RequestMethod.POST)
  public String saveCourse(@Validated({NgoManhCuong_05_CreateCourse.class, Default.class}) @ModelAttribute("course") NgoManhCuong_05_Course course,
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

  /*Gọi trang xem khóa học gồm nhiều bài học, bài tập, thông tin khóa học*/
  @RequestMapping("/courses/{courseId}/weeks/{weekId}")
  public String showCourse(@PathVariable Integer courseId, @PathVariable Integer weekId, Model model) {
    NgoManhCuong_05_Course course = courseRepository.findOne(courseId.longValue());
    NgoManhCuong_05_Unit unit = unitRepository.findFirstByUnitWeekAndCourse(weekId, course);
    List<NgoManhCuong_05_Assignment> assignments = assignmentRepository.findAllByUnit(unit);
    model.addAttribute("course", course);
    model.addAttribute("unit", unit);
    model.addAttribute("week", weekId);
    model.addAttribute("assignments", assignments);
    return "courses/show";
  }

  /*Xóa khóa học sử dụng AJAX*/
  //  TODO: Make response body when deleting fail
  @RequestMapping(value = "courses/delete/{courseId}", method = RequestMethod.DELETE,
    produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
  @ResponseBody
  public void deleteCourse(@PathVariable Integer courseId) {
    courseRepository.delete(courseId.longValue());
  }

  /*Gọi trang sửa khóa học*/
  @RequestMapping("courses/{courseId}/edit")
  public String editCourse(@PathVariable Integer courseId, Model model) {
    NgoManhCuong_05_Course course = courseRepository.findOne(courseId.longValue());
    model.addAttribute("course", course);
    return "courses/edit";
  }

  /*Sửa khóa học*/
  @RequestMapping(value = "courses/{courseId}/edit", method = RequestMethod.POST)
  public String updateCourse(@ModelAttribute("course") @Validated({Default.class}) NgoManhCuong_05_Course course,
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

  /*Upload file lên amazon*/
  private void uploadFile(AmazonS3 s3client, String bucketName, MultipartFile file, String name, NgoManhCuong_05_Course course) {
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

  /*Lấy thông tin tổng quan về khóa học*/
  @RequestMapping("/courses/{courseId}/summary")
  public String showCourse(@PathVariable Integer courseId, Model model) {
    NgoManhCuong_05_Course course = courseRepository.findOne(courseId.longValue());
    NgoManhCuong_05_User teacher = userRepository.findOne(course.getUser().getUserId());
    model.addAttribute("course", course);
    model.addAttribute("teacher", teacher);
    return "/courses/summary";
  }
}

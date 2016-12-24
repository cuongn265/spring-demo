package com.eugene.controller;

import com.eugene.domain.NgoManhCuong_05_Assignment;
import com.eugene.domain.NgoManhCuong_05_Unit;
import com.eugene.repository.NgoManhCuong_05_AssignmentRepository;
import com.eugene.repository.NgoManhCuong_05_CourseRepository;
import com.eugene.repository.NgoManhCuong_05_UnitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import javax.validation.groups.Default;

/**
 * Created by Ngô Mạnh Cường on 12/23/2016.
 */

/**
 * Controller cho bài tập
 * Thêm sửa xóa
 */
@Controller
@RequestMapping("/courses/{courseId}/weeks/{weekId}")
public class NgoManhCuong_05_AssignmentController {
  private final NgoManhCuong_05_CourseRepository courseRepository;
  private final NgoManhCuong_05_AssignmentRepository assignmentRepository;
  private final NgoManhCuong_05_UnitRepository unitRepository;

  @Autowired
  public NgoManhCuong_05_AssignmentController(NgoManhCuong_05_CourseRepository courseRepository, NgoManhCuong_05_AssignmentRepository assignmentRepository, NgoManhCuong_05_UnitRepository unitRepository) {
    this.courseRepository = courseRepository;
    this.assignmentRepository = assignmentRepository;
    this.unitRepository = unitRepository;
  }

  /*Gọi trang thêm bài tập*/
  @RequestMapping("assignments/new")
  public String newAssignment(Model model, @PathVariable Integer courseId, @PathVariable Integer weekId) {
    NgoManhCuong_05_Unit unit = unitRepository.findFirstByUnitWeekAndCourse(weekId, courseRepository.findOne(courseId.longValue()));

    NgoManhCuong_05_Assignment assignment = new NgoManhCuong_05_Assignment();
    assignment.setUnit(unit);
    model.addAttribute("assignment", assignment);
    return "assignments/new";
  }

  /*Thêm bài tập*/
  @RequestMapping(value = "assignments/add", method = RequestMethod.POST)
  public String addAssignment(@ModelAttribute("assignment") @Validated NgoManhCuong_05_Assignment assignment, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
    if (bindingResult.hasErrors()) {
      return "assignments/new";
    }
    assignmentRepository.save(assignment);
    redirectAttributes.addFlashAttribute("message", "Assignment was successfully created!");
    return "redirect:/courses/{courseId}/weeks/{weekId}";
  }

  /*Gọi trang sửa bài tập*/
  @RequestMapping("assignments/{assignmentId}/edit")
  public String editAssignment(Model model, @PathVariable Integer assignmentId) {
    NgoManhCuong_05_Assignment assignment= assignmentRepository.findOne(assignmentId.longValue());
    model.addAttribute("assignment", assignment);
    return "assignments/edit";
  }

  /*Sửa bài tập*/
  @RequestMapping(value = "assignments/{assignmentId}/update", method = RequestMethod.POST)
  public String updateAssignment(@ModelAttribute("assignment") @Validated({Default.class}) NgoManhCuong_05_Assignment assignment,
                               BindingResult bindingResult,
                               RedirectAttributes redirectAttributes) {
    if (bindingResult.hasErrors()) {
      return "assignments/edit";
    }
    assignmentRepository.save(assignment);
    redirectAttributes.addFlashAttribute("message", "Assignment was successfully updated!");
    return "redirect:/courses/{courseId}/weeks/{weekId}";
  }

  /*Xóa bài tập*/
  //  TODO: Make response body when deleting fail
  @RequestMapping(value="assignments/delete/{assignmentId}", method=RequestMethod.DELETE,
    produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
  @ResponseBody
  public void deleteAssignment(@PathVariable Integer assignmentId) {
    assignmentRepository.delete(assignmentId.longValue());
  }

}

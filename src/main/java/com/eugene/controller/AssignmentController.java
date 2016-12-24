package com.eugene.controller;

import com.eugene.domain.Assignment;
import com.eugene.domain.Unit;
import com.eugene.inter.CreateCourse;
import com.eugene.repository.AssignmentRepository;
import com.eugene.repository.CourseRepository;
import com.eugene.repository.UnitRepository;
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
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Eugene on 12/23/2016.
 */
@Controller
@RequestMapping("/courses/{courseId}/weeks/{weekId}")
public class AssignmentController {
  private final CourseRepository courseRepository;
  private final AssignmentRepository assignmentRepository;
  private final UnitRepository unitRepository;

  @Autowired
  public AssignmentController(CourseRepository courseRepository, AssignmentRepository assignmentRepository, UnitRepository unitRepository) {
    this.courseRepository = courseRepository;
    this.assignmentRepository = assignmentRepository;
    this.unitRepository = unitRepository;
  }

  @RequestMapping("assignments/new")
  public String newAssignment(Model model, @PathVariable Integer courseId, @PathVariable Integer weekId) {
    Unit unit = unitRepository.findFirstByUnitWeekAndCourse(weekId, courseRepository.findOne(courseId.longValue()));

    Assignment assignment = new Assignment();
    assignment.setUnit(unit);
    model.addAttribute("assignment", assignment);
    return "assignments/new";
  }

  @RequestMapping(value = "assignments/add", method = RequestMethod.POST)
  public String addAssignment(@Valid Assignment assignment, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
    if (bindingResult.hasErrors()) {
      return "assignments/new";
    }
    assignmentRepository.save(assignment);
    redirectAttributes.addFlashAttribute("message", "Assignment was successfully created!");
    return "redirect:/courses/{courseId}/weeks/{weekId}";
  }

  @RequestMapping("assignments/{assignmentId}/edit")
  public String editAssignment(Model model, @PathVariable Integer assignmentId) {
    Assignment assignment= assignmentRepository.findOne(assignmentId.longValue());
    model.addAttribute("assignment", assignment);
    return "assignments/edit";
  }

  @RequestMapping(value = "assignments/{assignmentId}/update", method = RequestMethod.POST)
  public String updateAssignment(@ModelAttribute @Validated({Default.class}) Assignment assignment,
                               BindingResult bindingResult,
                               RedirectAttributes redirectAttributes) {
    if (bindingResult.hasErrors()) {
      return "assignments/edit";
    }
    assignmentRepository.save(assignment);
    redirectAttributes.addFlashAttribute("message", "Assignment was successfully updated!");
    return "redirect:/courses/{courseId}/weeks/{weekId}";
  }

  //  TODO: Make response body when deleting fail
  @RequestMapping(value="assignments/delete/{assignmentId}", method=RequestMethod.DELETE,
    produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
  @ResponseBody
  public void deleteAssignment(@PathVariable Integer assignmentId) {
    assignmentRepository.delete(assignmentId.longValue());
  }

}

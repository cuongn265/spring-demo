package com.eugene.controller;

import com.eugene.domain.Assignment;
import com.eugene.domain.Unit;
import com.eugene.repository.AssignmentRepository;
import com.eugene.repository.CourseRepository;
import com.eugene.repository.UnitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
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
//    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
//    Date parsed = format.parse(new Date());
//    assignment.setAssignmentStartDate(parsed);
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

}

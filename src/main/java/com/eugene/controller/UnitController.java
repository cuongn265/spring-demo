package com.eugene.controller;

import com.eugene.domain.Unit;
import com.eugene.repository.CourseRepository;
import com.eugene.repository.UnitRepository;
import com.eugene.validator.UnitNameValidator;
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
 * Created by Eugene on 12/5/2016.
 */
@Controller
@RequestMapping("/courses/{courseId}/weeks/{weekId}")
public class UnitController {

  private final UnitRepository unitRepository;
  private final CourseRepository courseRepository;
  @Autowired
  private final UnitNameValidator unitNameValidator;

  @Autowired
  public UnitController(UnitRepository unitRepository, CourseRepository courseRepository, UnitNameValidator unitNameValidator) {
    this.unitRepository = unitRepository;
    this.courseRepository = courseRepository;
    this.unitNameValidator = unitNameValidator;
  }

  @RequestMapping(value = "units/new")
  public String addUnit(Model model, @PathVariable Integer courseId, @PathVariable Integer weekId) {
    Unit unit = new Unit();
    unit.setCourse(courseRepository.findOne(courseId.longValue()));
    unit.setUnitWeek(weekId);
    model.addAttribute("unit", unit);
    return "courses/units/new";
  }

  @RequestMapping(value = "units/add", method = RequestMethod.POST)
  public String addUnit(@Valid Unit unit,BindingResult bindingResult) {
    unitNameValidator.validate(unit, bindingResult);
    if (bindingResult.hasErrors()) {
      return "courses/units/new";
    }
    unitRepository.save(unit);
    return "redirect:/courses/{courseId}/weeks/{weekId}";
  }

  //  TODO: Make response body when deleting fail
  @RequestMapping(value="delete/{unitId}", method=RequestMethod.DELETE,
    produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
  @ResponseBody
  public void deleteUnit(@PathVariable Integer unitId) {
    unitRepository.delete(unitId.longValue());
  }

  @RequestMapping(value = "units/{unitId}/edit")
  public String editUnit(@PathVariable Integer courseId, @PathVariable Integer unitId, Model model) {
    Unit unit = unitRepository.findOne(unitId.longValue());
    model.addAttribute("unit", unit);
    return "courses/units/edit";
  }

  @RequestMapping(value = "units/{unitId}/edit", method = RequestMethod.POST)
  public String editUnitPost(@ModelAttribute @Validated({Default.class}) Unit unit,
                             BindingResult bindingResult,
                             RedirectAttributes redirectAttributes) {
    if (bindingResult.hasErrors()) {
      return "/courses/units/edit";
    }
    unitRepository.save(unit);
    redirectAttributes.addFlashAttribute("message", "Course was successfully updated!!");
    return "redirect:/courses/{courseId}/weeks/{weekId}";
  }
}

package com.eugene.controller;

import com.eugene.domain.Unit;
import com.eugene.repository.CourseRepository;
import com.eugene.repository.UnitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Created by Eugene on 12/5/2016.
 */
@Controller
@RequestMapping("/courses/{courseId}")
public class UnitController {

  private final UnitRepository unitRepository;
  private final CourseRepository courseRepository;

  @Autowired
  public UnitController(UnitRepository unitRepository, CourseRepository courseRepository) {
    this.unitRepository = unitRepository;
    this.courseRepository = courseRepository;
  }

  @RequestMapping(value = "/units/new")
  public String addUnit(Model model, @PathVariable Integer courseId) {
    Unit unit = new Unit();
    unit.setCourse(courseRepository.findOne(courseId.longValue()));
    model.addAttribute("unit", unit);
    return "unit";
  }

  @RequestMapping(value = "/units/add", method = RequestMethod.POST)
  public String addUnit(@Valid Unit unit,BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      return "unit";
    }
    unitRepository.save(unit);
    return "redirect:/courses/{courseId}";
  }

  //  TODO: Make response body when deleting fail
  @RequestMapping(value="units/delete/{unitId}", method=RequestMethod.DELETE,
    produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
  @ResponseBody
  public void deleteUnit(@PathVariable Integer unitId) {
    unitRepository.delete(unitId.longValue());
  }
}

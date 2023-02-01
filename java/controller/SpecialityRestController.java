package controller;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import dto.SpecialityDTO;
import service.RatingListService;

@RestController
public class SpecialityRestController {
	@Autowired
	private RatingListService ratingListService;

	@GetMapping("/specialitiesByApplicant")
	public Set<SpecialityDTO> viewSpecialitiesByApplicant(@RequestParam("id") Integer applicantId) {
		return ratingListService.parseSpecialitiesAppliedByApplicant(applicantId);
	}
}
